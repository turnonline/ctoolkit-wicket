package org.ctoolkit.wicket.turnonline.identity.markup;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.ctoolkit.wicket.standard.util.WicketUtil;
import org.ctoolkit.wicket.turnonline.AppEngineApplication;
import org.ctoolkit.wicket.turnonline.identity.IdentityOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * The configurable identity login panel as reusable component.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class IdentityLoginPanel
        extends Panel
{
    private static final long serialVersionUID = -7065142979784824937L;

    private IdentityOptions options;

    private boolean emailFirst;

    public IdentityLoginPanel( String id, IdentityOptions options )
    {
        this( id, options, false );
    }

    public IdentityLoginPanel( String id, IdentityOptions options, boolean emailFirst )
    {
        super( id );
        setRenderBodyOnly( true );

        this.options = options;
        this.emailFirst = emailFirst;
    }

    @Override
    public void renderHead( IHeaderResponse response )
    {
        super.renderHead( response );

        // config script to be loaded by JS
        String script = getScript();
        response.render( OnLoadHeaderItem.forScript( script ) );

        // render google apis client.js as last
        Url url = Url.parse( "//apis.google.com/js/client.js?onload=load" );

        UrlResourceReference jsCdnReference = new UrlResourceReference( url );
        response.render( JavaScriptHeaderItem.forReference( jsCdnReference ) );
    }

    /**
     * IdentityLoginPanelConfig.js file properties population
     */
    private String getScript()
    {
        String configFileName = IdentityLoginPanel.class.getSimpleName() + "Config.js";

        PackageTextTemplate template = new PackageTextTemplate( IdentityLoginPanel.class, configFileName );
        Map<String, Object> variables = new HashMap<>();

        variables.put( "widgetUrl", WicketUtil.getServerUrl() + AppEngineApplication.LOGIN );
        variables.put( "signInSuccessUrl", options.getSignInSuccessUrl() );
        variables.put( "signOutUrl", options.getSignOutUrl() );
        variables.put( "oobActionUrl", options.getOobActionUrl() );
        variables.put( "apiKey", options.getApiKey() );
        variables.put( "siteName", options.getSiteName() );

        if ( emailFirst )
        {
            variables.put( "displayMode", IdentityOptions.DisplayMode.EMAIL_FIRST.getValue() );
        }
        else
        {
            variables.put( "displayMode", options.getDisplayMode().getValue() );
        }
        variables.put( "signInOptions", options.getSignInOptionsAsString() );

        return template.asString( variables );
    }
}
