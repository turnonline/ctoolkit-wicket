package org.ctoolkit.turnonline.wicket.page;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.MetaDataHeaderItem;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The abstract and generic base page class with no associated HTML resource.
 * Provides common helpers impl.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class GenericBasePage<T>
        extends GenericWebPage<T>
{
    public static final String PARAM_LANG = "lang";

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger( GenericBasePage.class );

    public GenericBasePage()
    {
        super();

        init();
    }

    public GenericBasePage( IModel<T> model )
    {
        super( model );

        init();
    }

    public GenericBasePage( PageParameters parameters )
    {
        super( parameters );

        init();
    }

    private void init()
    {
        // override locale with lang parameter
        Request request = RequestCycle.get().getRequest();
        String language = request.getRequestParameters().getParameterValue( PARAM_LANG ).toOptionalString();

        if ( language != null )
        {
            getSession().setLocale( new Locale( language ) );
        }
    }

    /**
     * The Tracking ID for google analytics is taken from {@link #getGoogleAnalyticsTrackingId()}
     * If no Tracking ID is being found, the analytics script is not rendered.
     *
     * @see #getGoogleAnalyticsScript(String)
     */
    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        final IModel<T> model = getModel();

        if ( model == null )
        {
            log.info( "The model is null!" );
        }

        // set google analytics script if any
        final String trackingId = getGoogleAnalyticsTrackingId();
        if ( !Strings.isEmpty( trackingId ) )
        {
            add( new Behavior()
            {
                private static final long serialVersionUID = 1L;

                public void renderHead( Component component, IHeaderResponse response )
                {
                    response.render( StringHeaderItem.forString( getGoogleAnalyticsScript( trackingId ) ) );
                }
            } );
        }

        // meta tag description setup if any
        final String descriptionExpression = getHeaderDescriptionExpression();
        if ( descriptionExpression != null )
        {
            add( new Behavior()
            {
                private static final long serialVersionUID = 1L;

                public void renderHead( Component component, IHeaderResponse response )
                {
                    PropertyModel<String> content = new PropertyModel<>( model, descriptionExpression );
                    response.render( MetaDataHeaderItem.forMetaTag( new Model<>( "description" ), content ) );
                }
            } );
        }

        // meta tag keywords setup if any
        final String keywordsExpression = getHeaderKeywordsExpression();
        if ( keywordsExpression != null )
        {
            add( new Behavior()
            {
                private static final long serialVersionUID = 1L;

                public void renderHead( Component component, IHeaderResponse response )
                {
                    PropertyModel<String> content = new PropertyModel<>( model, keywordsExpression );
                    response.render( MetaDataHeaderItem.forMetaTag( new Model<>( "keywords" ), content ) );
                }
            } );
        }
    }

    /**
     * Add custom style to web page
     *
     * @param style name of style ( without <i>.css</i> suffix )
     */
    protected void addStyle( final String style )
    {
        add( new Behavior()
        {
            private static final long serialVersionUID = 1L;

            public void renderHead( Component component, IHeaderResponse response )
            {
                response.render( CssHeaderItem.forReference( new UrlResourceReference( Url.parse( style ) ) ) );
            }
        } );
    }

    /**
     * Add custom javascript to web page
     *
     * @param script name of javascript ( without <i>.js</i> suffix)
     */
    protected void addScript( final String script )
    {
        add( new Behavior()
        {
            private static final long serialVersionUID = 1L;

            public void renderHead( Component component, IHeaderResponse response )
            {
                response.render( JavaScriptHeaderItem.forReference( new UrlResourceReference( Url.parse( script ) ) ) );
            }
        } );
    }

    /**
     * Provides the header description expression to get 'content' from the associated model
     * in order to be rendered in meta tag 'description'.
     * <p/>
     * Unless overridden method returns null and is not going to be rendered.
     *
     * @return the header description expression
     */
    protected String getHeaderDescriptionExpression()
    {
        return null;
    }

    /**
     * Provides the header keywords expression to get 'content' from the associated model
     * in order to be rendered in meta tag 'keywords'.
     * <p/>
     * Unless overridden method returns null and is not going to be rendered.
     *
     * @return the header keywords expression
     */
    protected String getHeaderKeywordsExpression()
    {
        return null;
    }

    /**
     * Returns the Google Analytics Tracking ID. Called during render phase.
     * By default it returns <tt>null</tt>.
     *
     * @return the Google Analytics Tracking ID
     */
    protected String getGoogleAnalyticsTrackingId()
    {
        return null;
    }

    /**
     * Get google analytics script. If you want different script just override this method.
     *
     * @return google analytics script
     */
    protected String getGoogleAnalyticsScript( String trackingId )
    {
        checkNotNull( trackingId );

        return "<script>\n" +
                "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){\n" +
                "(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),\n" +
                "m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)\n" +
                "})(window,document,'script','//www.google-analytics.com/analytics.js','ga');\n" +
                "\n" +
                "ga('create', '" + trackingId + "', 'auto');\n" +
                "ga('send', 'pageview');\n" +
                "\n" +
                "</script>";
    }
}
