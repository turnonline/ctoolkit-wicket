package org.ctoolkit.wicket.standard.gwt;

import com.google.common.base.Strings;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.time.Duration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Date;

/**
 * The component responsible to update JS Object 'RestServiceProfile' in HTML header and related cookies with:
 * <ul>
 * <li>CTOOLKIT_OBO_EMAIL (cookie)</li>
 * <li>CTOOLKIT_TOKEN (cookie)</li>
 * <li>CTOOLKIT_SERVICE_ROOT (RestServiceProfile)</li>
 * </ul>
 * And JS Object 'UserProfile' in HTML header with:
 * <ul>
 * <li>EMAIL (same as CTOOLKIT_OBO_EMAIL - logged in user)</li>
 * <li>DOMICILE (UserProfile - seller's domicile)</li>
 * </ul>
 * Used by GWT widgets to authorize REST calls. Timer will refresh the token based on the token expiration time.
 * The initial update interval is set to 20 seconds. The first timer's call will setup next update interval
 * based on the token expiration time. If unknown default 1 hour will be used.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@Deprecated
public abstract class RestServiceProfileTimer
        extends WebMarkupContainer
{
    private static final long serialVersionUID = 4297419133376026538L;

    private static String PROFILE_OBO_EMAIL = "CTOOLKIT_OBO_EMAIL";

    private static String PROFILE_TOKEN = "CTOOLKIT_TOKEN";

    private static String script;

    static
    {
        script = "var RestServiceProfile  = '{'CTOOLKIT_SERVICE_ROOT: ''{0}'''}';"
                + " var UserProfile  = '{'EMAIL: ''{1}'',DOMICILE: ''{2}'''}';";
    }

    public RestServiceProfileTimer( String id )
    {
        super( id );
        this.add( new Timer() );
    }

    /**
     * Delete ctoolkit related cookies.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     */
    public static void deleteCookies( HttpServletRequest request, HttpServletResponse response )
    {
        Cookie[] cookies = request.getCookies();

        if ( cookies == null )
        {
            return;
        }

        for ( Cookie cookie : cookies )
        {
            //the zero value causes the cookie will be deleted
            if ( PROFILE_OBO_EMAIL.equals( cookie.getName() ) )
            {
                cookie.setMaxAge( 0 );
                cookie.setValue( "" );
                cookie.setPath( "/" );

                response.addCookie( cookie );
            }
            else if ( PROFILE_TOKEN.equals( cookie.getName() ) )
            {
                cookie.setMaxAge( 0 );
                cookie.setValue( "" );
                cookie.setPath( "/" );

                response.addCookie( cookie );
            }
        }
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        getPage().add( new Behavior()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void renderHead( Component component, IHeaderResponse response )
            {
                String serviceRootUrl = getServiceRootUrl();
                if ( Strings.isNullOrEmpty( serviceRootUrl ) )
                {
                    throw new NullPointerException( "REST service URL cannot be null or empty!" );
                }
                String email = getEmail();
                if ( Strings.isNullOrEmpty( email ) )
                {
                    throw new NullPointerException( "Email cannot be null or empty!" );
                }
                String domicile = getDomicile();
                if ( Strings.isNullOrEmpty( domicile ) )
                {
                    throw new NullPointerException( "Seller's domicile cannot be null or empty!" );
                }

                String script = MessageFormat.format( RestServiceProfileTimer.script, serviceRootUrl, email, domicile );
                response.render( JavaScriptHeaderItem.forScript( script, component.getMarkupId() ) );
            }
        } );
    }

    /**
     * Update cookies {@link #PROFILE_OBO_EMAIL} and {@link #PROFILE_TOKEN} with current values.
     *
     * @param response the HTTP response to be updated
     */
    public void updateCookies( HttpServletResponse response )
    {
        updateCookies( response, null );
    }

    private void updateCookies( HttpServletResponse response, Long seconds )
    {
        String email = getEmail();
        if ( Strings.isNullOrEmpty( email ) )
        {
            throw new NullPointerException( "Email cannot be null or empty!" );
        }

        String accessToken = getAccessToken();
        if ( Strings.isNullOrEmpty( accessToken ) )
        {
            throw new NullPointerException( "Access token cannot be null or empty!" );
        }

        Long age;
        if ( seconds == null )
        {
            age = getRemainingSeconds();
        }
        else
        {
            age = seconds;
        }

        // including reserve 30 seconds
        age = age + 30;

        Cookie cookie = new Cookie( PROFILE_OBO_EMAIL, email );
        cookie.setPath( "/" );
        cookie.setMaxAge( age.intValue() );
        response.addCookie( cookie );

        cookie = new Cookie( PROFILE_TOKEN, accessToken );
        cookie.setPath( "/" );
        cookie.setMaxAge( age.intValue() );
        response.addCookie( cookie );
    }

    /**
     * Returns the logged in email.
     *
     * @return the logged in email
     */
    protected abstract String getEmail();

    /**
     * Returns the seller's domicile.
     *
     * @return the seller's domicile
     */
    protected abstract String getDomicile();

    /**
     * Returns valid access token.
     *
     * @return the access token
     */
    protected abstract String getAccessToken();

    /**
     * Returns the token remaining lifetime.
     *
     * @return the token remaining lifetime
     */
    protected abstract Date getExpirationTime();

    /**
     * Returns the REST service URL.
     *
     * @return the REST service URL
     */
    protected abstract String getServiceRootUrl();

    private Long getRemainingSeconds()
    {
        Date expirationTime = getExpirationTime();
        // default validity in seconds
        Long seconds = 3600L;
        if ( expirationTime != null )
        {
            long time = expirationTime.getTime();
            // convert to remaining seconds and deduct 30s for reserve
            seconds = ( time - new Date().getTime() ) / 1000 - 30;
        }
        return seconds;
    }

    private class Timer
            extends AjaxSelfUpdatingTimerBehavior
    {
        private static final long serialVersionUID = 1L;


        /**
         * Construct with initial update interval.
         */
        Timer()
        {
            super( Duration.seconds( 20 ) );
        }

        @Override
        protected void onPostProcessTarget( AjaxRequestTarget target )
        {
            Long seconds = getRemainingSeconds();
            setUpdateInterval( Duration.seconds( seconds ) );

            Response response = target.getHeaderResponse().getResponse();
            updateCookies( ( HttpServletResponse ) response.getContainerResponse(), seconds );
        }
    }
}
