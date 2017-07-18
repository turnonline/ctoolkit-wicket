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
 * Used by GWT widgets to authorize REST calls. Timer will refresh the token based on the token expiration time.
 * The initial update interval is set to 30 minutes.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 */
public abstract class RestServiceProfileTimer
        extends WebMarkupContainer
{
    private static final long serialVersionUID = 4297419133376026538L;

    private static String PROFILE_OBO_EMAIL = "CTOOLKIT_OBO_EMAIL";

    private static String PROFILE_TOKEN = "CTOOLKIT_TOKEN";

    private static String script;

    static
    {
        script = "var RestServiceProfile  = '{'CTOOLKIT_SERVICE_ROOT: ''{0}'''}';";
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

                String script = MessageFormat.format( RestServiceProfileTimer.script, serviceRootUrl );
                response.render( JavaScriptHeaderItem.forScript( script, component.getMarkupId() ) );
            }
        } );
    }

    public void updateCookies( HttpServletResponse response )
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

        int oneDay = 86400;
        Cookie cookie = new Cookie( PROFILE_OBO_EMAIL, email );
        cookie.setPath( "/" );
        cookie.setMaxAge( oneDay );
        response.addCookie( cookie );

        cookie = new Cookie( PROFILE_TOKEN, accessToken );
        cookie.setPath( "/" );
        cookie.setMaxAge( oneDay );
        response.addCookie( cookie );
    }

    /**
     * Returns the logged in email.
     *
     * @return the logged in email
     */
    protected abstract String getEmail();

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

    private class Timer
            extends AjaxSelfUpdatingTimerBehavior
    {
        private static final long serialVersionUID = 1L;


        /**
         * Construct with initial update interval.
         */
        Timer()
        {
            super( Duration.seconds( 1800 ) );
        }

        @Override
        protected void onPostProcessTarget( AjaxRequestTarget target )
        {
            Date expirationTime = getExpirationTime();
            if ( expirationTime != null )
            {
                long time = expirationTime.getTime();
                // convert to remaining seconds and deduct 30s for reserve
                long seconds = ( time - new Date().getTime() ) / 1000 - 30;
                if ( seconds > 0 )
                {
                    setUpdateInterval( Duration.seconds( seconds ) );
                }
            }

            Response response = target.getHeaderResponse().getResponse();
            updateCookies( ( HttpServletResponse ) response.getContainerResponse() );
        }
    }
}
