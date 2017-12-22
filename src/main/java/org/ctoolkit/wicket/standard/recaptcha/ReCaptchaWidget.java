package org.ctoolkit.wicket.standard.recaptcha;

import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.Strings;
import org.ctoolkit.wicket.standard.util.WicketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The Google reCAPTCHA widget wrapper.
 * <p>
 * Once a single captcha instance has been successfully validated the value will be shared
 * among other captcha instances via session thus no further validation will be required till session invalidation.
 * A visibility of the reCAPTCHA widget must be handled by the client, override {@link #isVisible()}.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class ReCaptchaWidget
        extends WebMarkupContainer
{
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger( ReCaptchaWidget.class );

    private String dataSiteKey;

    private String reCaptchaSecret;

    private Boolean validToken;

    private Date timer;

    private int millisToExpire = 60000;

    private boolean validationRequired = true;

    /**
     * Constructs a single reCAPTCHA wicket component instance.
     *
     * @param id              the component ID
     * @param dataSiteKey     the public captcha data site key
     * @param reCaptchaSecret the secrete captcha key
     * @param model           the language model to localize captcha
     */
    public ReCaptchaWidget( String id, String dataSiteKey, String reCaptchaSecret, IModel<String> model )
    {
        super( id );
        add( new ReCaptchaBehavior( model, dataSiteKey ) );

        this.dataSiteKey = dataSiteKey;
        this.reCaptchaSecret = reCaptchaSecret;

        setOutputMarkupId( true );
    }

    private static boolean fetchResponse( HTTPRequest request )
    {
        HTTPResponse fetched;

        try
        {
            URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();
            fetched = urlFetchService.fetch( request );
        }
        catch ( IOException e )
        {
            logger.error( "", e );
            return false;
        }

        int responseCode = fetched.getResponseCode();
        boolean success = false;

        if ( responseCode == HttpURLConnection.HTTP_OK )
        {
            try
            {
                JSONObject json = new JSONObject( new String( fetched.getContent() ) );
                success = json.getBoolean( "success" );
            }
            catch ( JSONException e )
            {
                logger.error( "Response code: " + responseCode, e );
            }
        }
        else
        {
            logger.error( "Response code: " + responseCode );
        }
        return success;
    }

    private String getToken()
    {
        HttpServletRequest request = ( ( ServletWebRequest ) RequestCycle.get().getRequest() ).getContainerRequest();
        return request.getParameter( "g-recaptcha-response" );
    }

    @Override
    protected void onComponentTag( ComponentTag tag )
    {
        super.onComponentTag( tag );

        tag.put( "data-sitekey", dataSiteKey );
    }

    /**
     * Returns the boolean value whether captcha has been successfully validated.
     *
     * @return true if validation has passed, otherwise false
     */
    public final boolean isValid()
    {
        if ( validToken != null && validToken )
        {
            return true;
        }

        String token = getToken();
        if ( Strings.isEmpty( token ) )
        {
            validToken = null;
            return false;
        }

        validToken = checkToken( token );

        return validToken;
    }

    /**
     * Starts timer by current time.
     */
    public void startTimer()
    {
        timer = new Date();
    }

    /**
     * Returns the time how long will take once validation won't be required.
     * The default value is 60 seconds.
     *
     * @return the time in milliseconds
     * @see #isTimerExpired() ()
     */
    public int getTimeToExpire()
    {
        return millisToExpire;
    }

    /**
     * Sets the time how long will take once validation won't be required. Only positive values are allowed.
     * {@link #startTimer()} must be called to take this value into account.
     *
     * @param millisToExpire the time in milliseconds to be set
     * @see #isTimerExpired() ().
     */
    public void setTimeToExpire( int millisToExpire )
    {
        if ( millisToExpire < 0 )
        {
            throw new IllegalArgumentException( "Only positive values are allowed" );
        }
        this.millisToExpire = millisToExpire;
    }

    /**
     * Returns the boolean value whether timer has expired or not. Consequent call may return different value
     * as it is evaluated against current date time.
     * If {@link #startTimer()} hasn't been started, it will always return false.
     *
     * @return true, if timer has expired, otherwise false
     */
    public final boolean isTimerExpired()
    {
        if ( timer == null )
        {
            return false;
        }

        Date now = new Date();
        now.setTime( now.getTime() - millisToExpire );

        return now.after( timer );
    }

    /**
     * Resets the timer, then validation will be always required till successful token validation will be done.
     */
    public void resetTimer()
    {
        timer = null;
    }

    /**
     * The value is being evaluated during render time (default value is true).
     * Once evaluated, returns the boolean value whether validation is required or not, however it's client
     * responsibility to handle the captcha visibility on its own.
     *
     * @return true if validation will be required, otherwise returns false
     */
    public final boolean isValidationRequired()
    {
        return validationRequired;
    }

    @Override
    protected void onBeforeRender()
    {
        if ( validToken == null )
        {
            HttpSession session = WicketUtil.getHttpServletRequest().getSession();
            validToken = ( Boolean ) session.getAttribute( ReCaptchaWidget.class.getName() );
        }

        if ( validToken != null && validToken )
        {
            // if token is valid the validation is definitely will not be required any more for any captcha instances
            validationRequired = false;
            resetTimer();
        }
        else
        {
            // there is no valid any token
            // if timer is still ticking (not expired) then validation is required
            validationRequired = !isTimerExpired();
        }

        super.onBeforeRender();
    }

    @Override
    protected void onAfterRender()
    {
        super.onAfterRender();

        if ( validToken != null && validToken )
        {
            HttpSession session = WicketUtil.getHttpServletRequest().getSession();
            session.setAttribute( ReCaptchaWidget.class.getName(), validToken );
        }
    }

    /**
     * The Google reCAPTCHA token checker method implementation.
     */
    private boolean checkToken( String token )
    {
        checkNotNull( reCaptchaSecret, "reCAPTCHA secret key cannot be null." );

        FetchOptions options = FetchOptions.Builder.withDefaults();

        options.disallowTruncate();
        options.validateCertificate();
        options.followRedirects();

        Url url = Url.parse( "https://www.google.com/recaptcha/api/siteverify" );
        url.addQueryParameter( "secret", reCaptchaSecret );
        url.addQueryParameter( "response", token );

        String fullUrl = "https://www.google.com" + url;

        HTTPRequest request;
        try
        {
            request = new HTTPRequest( new URL( fullUrl ), HTTPMethod.GET, options );
        }
        catch ( IOException e )
        {
            logger.error( "URL: " + url, e );
            return false;
        }

        return fetchResponse( request );
    }
}
