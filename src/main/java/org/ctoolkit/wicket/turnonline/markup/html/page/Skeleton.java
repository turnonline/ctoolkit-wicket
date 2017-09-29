package org.ctoolkit.wicket.turnonline.markup.html.page;

import org.apache.wicket.Component;
import org.apache.wicket.RuntimeConfigurationType;
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
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.util.string.Strings;
import org.ctoolkit.wicket.turnonline.AppEngineApplication;
import org.ctoolkit.wicket.turnonline.model.IModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The abstract and generic base page class with no associated HTML resource.
 * It's intended to be a base class of all page implementation across application.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class Skeleton<T>
        extends GenericWebPage<T>
{
    public static final String PARAM_LANG = "lang";

    static final String DEFAULT_HEADER_DESCRIPTION_EXPRESSION = "headerDescription";

    static final String DEFAULT_HEADER_KEYWORDS_EXPRESSION = "headerKeywords";

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger( Skeleton.class );

    private boolean checkDefaultModelObjectIsNull = false;

    @Inject
    private IModelFactory modelFactory;

    public Skeleton()
    {
        super();
    }

    public Skeleton( IModel<T> model )
    {
        super( model );
    }

    public Skeleton( PageParameters parameters )
    {
        super( parameters );
    }

    @Override
    public void renderHead( IHeaderResponse response )
    {
        IModel<?> model = getDefaultModel();
        RuntimeConfigurationType configurationType = AppEngineApplication.get().getConfigurationType();
        ResourceReference[] stylesheetReference = modelFactory.getStylesheetReference( model, configurationType );

        for ( ResourceReference next : stylesheetReference )
        {
            response.render( CssHeaderItem.forReference( next ) );
        }
    }

    protected IModelFactory modelFactory()
    {
        return modelFactory;
    }

    private String getLangParam()
    {
        // override locale with lang parameter
        Request request = RequestCycle.get().getRequest();
        return request.getRequestParameters().getParameterValue( PARAM_LANG ).toOptionalString();
    }

    /**
     * Setting the boolean indication whether to check if default model object is <tt>null</tt>.
     * If set to <tt>true</tt> and {@link #getModelObject()} returns <tt>null</tt> on {@link #onInitialize()}
     * the {@link AbortWithHttpErrorCodeException} exception will be thrown.
     * The default value is <tt>false</tt>.
     *
     * @param check set <tt>true</tt> to check
     */
    protected void checkDefaultModelObjectIsNull( boolean check )
    {
        this.checkDefaultModelObjectIsNull = check;
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
        if ( checkDefaultModelObjectIsNull )
        {
            if ( getModelObject() == null )
            {
                throw new AbortWithHttpErrorCodeException( 404, "Default model object is null!" );
            }
        }

        super.onInitialize();

        String langParam = getLangParam();
        if ( langParam != null )
        {
            getSession().setLocale( new Locale( langParam ) );
        }

        final IModel<T> model = getModel();

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
        if ( descriptionExpression != null && model != null )
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
        if ( keywordsExpression != null && model != null )
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
     * Return HttpServletRequest request
     *
     * @return {@link HttpServletRequest}
     */
    protected HttpServletRequest getContainerRequest()
    {
        return ( ( ServletWebRequest ) getRequest() ).getContainerRequest();
    }

    /**
     * Return HttpServletResponse request
     *
     * @return {@link HttpServletResponse}
     */
    protected HttpServletResponse getContainerResponse()
    {
        return ( HttpServletResponse ) getResponse().getContainerResponse();
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
     * Returns the variation as a server name where app operates plus product name. Composed as Domain format expects,
     * excluding '/p' prefix
     * <p/>
     * This value is being used to alternate the HTML markup for wicket web page (components reuse this value as well
     * unless won't be overridden directly in component).
     *
     * @return the variation of the server name and product name (if any).
     */
    public String getVariation()
    {
        String fullVariation = getContainerRequest().getServerName();
        // product name taken as parameter from '/p/${PARAM_PRODUCT_NAME}'
        String productName = getPageParameters().get( AppEngineApplication.PARAM_PRODUCT_NAME ).toOptionalString();

        if ( productName != null )
        {
            // composed as Domain format expects, excluding '/p' prefix
            fullVariation = fullVariation + "/" + productName;
        }
        return fullVariation;
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
