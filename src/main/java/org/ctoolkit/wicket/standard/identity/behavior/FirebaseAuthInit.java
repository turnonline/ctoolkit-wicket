package org.ctoolkit.wicket.standard.identity.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.ctoolkit.wicket.turnonline.identity.IdentityOptions;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Firebase UI Auth widget initialization. Resources are being configured to be served via static CDN URL reference.
 * Locale sensitive, locale taken from the binded component.
 * The properties from {@link IdentityOptions} will be used to render Firebase initialization script.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class FirebaseAuthInit
        extends Behavior
{
    private static final long serialVersionUID = 1L;

    private static final String JS_AUTH_CDN_URL = "https://www.gstatic.com/firebasejs/ui/{0}/firebase-ui-auth{1}.js";

    private static UrlResourceReference cssAuthCdnReference;

    private static UrlResourceReference jsFirebaseCdnReference;

    private static UrlResourceReference jsAuthCdnReference;

    private static String firebaseInitScript;

    private final String uiWidgetVersion;

    private final String firebaseVersion;

    private IdentityOptions options;

    public FirebaseAuthInit( @Nonnull IdentityOptions options )
    {
        this( "2.4.0", "4.5.1", options );
    }

    public FirebaseAuthInit( @Nonnull String uiWidgetVersion,
                             @Nonnull String firebaseVersion,
                             @Nonnull IdentityOptions options )
    {
        this.uiWidgetVersion = checkNotNull( uiWidgetVersion );
        this.firebaseVersion = checkNotNull( firebaseVersion );
        this.options = checkNotNull( options );
    }

    @Override
    public void bind( Component component )
    {
        Url url = Url.parse( "https://www.gstatic.com/firebasejs/" + firebaseVersion + "/firebase.js" );
        jsFirebaseCdnReference = new UrlResourceReference( url );

        // JavaScript firebase init script preparation
        PackageTextTemplate template;
        template = new PackageTextTemplate( FirebaseAuthInit.class, "FirebaseAuthConfig.js" );
        Map<String, Object> variables = new HashMap<>();

        variables.put( "signInSuccessUrl", options.getSignInSuccessUrl() );
        variables.put( "credentialHelper", options.getCredentialHelper() );
        variables.put( "signInOptions", options.getSignInOptionsAsString() );
        variables.put( "termsUrl", options.getTermsUrl() );
        variables.put( "apiKey", options.getApiKey() );
        variables.put( "projectId", options.getProjectId() );
        variables.put( "databaseName", options.getDatabaseName() );
        variables.put( "bucketName", options.getBucketName() );
        variables.put( "senderId", options.getSenderId() );

        firebaseInitScript = template.asString( variables );

        // CSS reference preparation
        url = Url.parse( "https://www.gstatic.com/firebasejs/ui/" + uiWidgetVersion + "/firebase-ui-auth.css" );
        cssAuthCdnReference = new UrlResourceReference( url );
    }

    @Override
    public void renderHead( Component component, IHeaderResponse response )
    {
        String language = component.getLocale().getLanguage();

        MessageFormat fmt = new MessageFormat( JS_AUTH_CDN_URL );
        Object[] args = new Object[2];
        args[0] = uiWidgetVersion;
        String stringUrl;

        if ( Strings.isEmpty( language ) )
        {
            args[1] = "";
            stringUrl = fmt.format( args );
        }
        else
        {
            args[1] = "__" + language.toLowerCase();
            stringUrl = fmt.format( args );
        }

        // JavaScript reference preparation
        Url url = Url.parse( stringUrl );
        jsAuthCdnReference = new UrlResourceReference( url );

        // CSS header contribution
        response.render( CssHeaderItem.forReference( cssAuthCdnReference ) );

        // JavaScript contribution, order is important. Added at the bottom of HTML, before other script tags.
        JavaScriptReferenceHeaderItem headerItem = JavaScriptHeaderItem.forReference( jsFirebaseCdnReference );
        response.render( new PriorityHeaderItem( headerItem ) );

        headerItem = JavaScriptHeaderItem.forReference( jsAuthCdnReference );
        response.render( new PriorityHeaderItem( headerItem ) );

        // config script to be loaded by JS
        JavaScriptHeaderItem scriptItem = JavaScriptHeaderItem.forScript( firebaseInitScript, "firebase_init" );
        response.render( new PriorityHeaderItem( scriptItem ) );
    }
}
