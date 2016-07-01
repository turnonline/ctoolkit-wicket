package org.ctoolkit.turnonline.wicket;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.protocol.http.servlet.ServletWebResponse;
import org.apache.wicket.protocol.https.HttpsConfig;
import org.apache.wicket.protocol.https.HttpsMapper;
import org.apache.wicket.protocol.https.Scheme;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.settings.IRequestCycleSettings;
import org.wicketstuff.gae.GaeApplication;

import javax.servlet.http.HttpServletResponse;

/**
 * The base wicket application intended to run on Google AppEngine.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public abstract class AppEngineApplication
        extends AuthenticatedWebApplication
        implements GaeApplication
{
    public static final String SHOPPING_CART = "/shopping-cart";

    public static final String SIGNUP = "/sign-up";

    public static final String LOGIN = "/login";

    public static final String LOGOUT = "/logout";

    public static final String MY_ACCOUNT = "/my-account";

    public static final String LOGIN_TROUBLE_HANDLER = "/login-trouble-handler";

    public static final String PARAM_PRODUCT_NAME = "_productName";

    private static final String[] botAgents = {
            "googlebot", "msnbot", "slurp", "jeeves", "yadex", "baidu", "bing",
            "appie", "architext", "jeeves", "bjaaland", "ferret", "gulliver",
            "harvest", "htdig", "linkwalker", "lycos_", "moget", "muscatferret",
            "myweb", "nomad", "scooter", "yahoo!\\sslurp\\schina", "slurp",
            "weblayers", "antibot", "bruinbot", "digout4u", "echo!", "ia_archiver",
            "jennybot", "mercator", "netcraft", "msnbot", "petersnews",
            "unlost_web_crawler", "voila", "webbase", "webcollage", "cfetch",
            "zyborg", "wisenutbot", "robot", "crawl", "spider",
            "ahrefsbot", "aihitbot",
            "seznam"
    };

    @Override
    protected void init()
    {
        super.init();

        // set default markup encoding
        getMarkupSettings().setDefaultMarkupEncoding( "UTF-8" );

        // disable wicket tags rendering
        getMarkupSettings().setStripWicketTags( true );

        // disable wickets redirect after post mechanism because it does not work well in GAE
        getRequestCycleSettings().setRenderStrategy( IRequestCycleSettings.RenderStrategy.ONE_PASS_RENDER );

        getPageSettings().setVersionPagesByDefault( false );

        // mount sitemap
        mountPage( "/sitemap.xml", getSiteMapPage() );

        // mount robots
        mountPage( "/robots.txt", getRobotsPage() );
    }

    protected HttpsMapper getHttpsMapper()
    {
        return new HttpsMapper( getRootRequestMapper(), new HttpsConfig( 80, 443 ) )
        {
            @Override
            protected Scheme getDesiredSchemeFor( Class<? extends IRequestablePage> pageClass )
            {
                if ( getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT )
                {
                    return Scheme.HTTP;
                }
                else
                {
                    return super.getDesiredSchemeFor( pageClass );
                }
            }
        };
    }

    /**
     * Return sitemap page which will be mapped to /sitemap.xml
     *
     * @return sitemap page
     */
    protected abstract Class<? extends WebPage> getSiteMapPage();

    /**
     * Return robots page which will be mapped to /robots.txt
     *
     * @return robots page
     */
    protected abstract Class<? extends WebPage> getRobotsPage();


    @Override
    protected WebResponse newWebResponse( final WebRequest webRequest, final HttpServletResponse httpServletResponse )
    {
        return new ServletWebResponse( ( ServletWebRequest ) webRequest, httpServletResponse )
        {
            @Override
            public String encodeURL( CharSequence url )
            {
                return isAgent( webRequest.getHeader( "User-Agent" ) ) ? url.toString() : super.encodeURL( url );
            }

            @Override
            public String encodeRedirectURL( CharSequence url )
            {
                return isAgent( webRequest.getHeader( "User-Agent" ) ) ? url.toString() : super.encodeRedirectURL( url );
            }
        };
    }

    /**
     * Return <code>true</code> if request comes from bot agent, <code>false</code> if it is regular request
     *
     * @param agent agent
     * @return <code>true</code> if request comes from bot agent, <code>false</code> if it is regular request
     */
    private boolean isAgent( final String agent )
    {
        if ( agent != null )
        {
            for ( final String bot : botAgents )
            {
                if ( agent.toLowerCase().contains( bot.toLowerCase() ) )
                {
                    return true;
                }
            }
        }

        return false;
    }
}
