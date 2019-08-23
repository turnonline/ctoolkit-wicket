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
import org.ctoolkit.wicket.standard.identity.FirebaseConfig;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Firebase UI Auth widget initialization. Resources are being configured to be served via static CDN URL reference.
 * Locale sensitive. Locale will be taken from the binded component.
 * The properties from {@link FirebaseConfig} will be used to render Firebase initialization script.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 * @see FirebaseConfig
 */
public class FirebaseAppInit
        extends Behavior
{
    private static final long serialVersionUID = 1L;

    private static final String FIREBASE_BASE_URL = "https://www.gstatic.com/firebasejs/";

    private static final String FIREBASE_UI_BASE_URL = FIREBASE_BASE_URL + "ui/";

    private static final String JS_AUTH_CDN_URL = FIREBASE_UI_BASE_URL + "{0}/firebase-ui-auth{1}.js";

    private final String uiWidgetVersion;

    private final String firebaseVersion;

    private final boolean uiOn;

    private UrlResourceReference cssAuthCdnReference;

    private UrlResourceReference jsFirebaseAuthCdnReference;

    private UrlResourceReference jsFirebaseAppCdnReference;

    private String firebaseInitScript;

    private FirebaseConfig options;

    private Class<?> scope;

    private String fileName;

    private Arguments callback;

    public FirebaseAppInit( @Nonnull FirebaseConfig options )
    {
        this( options, false );
    }

    public FirebaseAppInit( @Nonnull FirebaseConfig options, boolean uiOn )
    {
        String errorMessage = "FirebaseUI for Web - Auth widget version is mandatory";
        this.uiWidgetVersion = checkNotNull( options.getUiWidgetVersion(), errorMessage );

        errorMessage = "Firebase version is mandatory";
        this.firebaseVersion = checkNotNull( options.getFirebaseVersion(), errorMessage );

        this.options = checkNotNull( options );
        this.uiOn = uiOn;
    }

    @Override
    public void bind( Component component )
    {
        Url url = Url.parse( FIREBASE_BASE_URL + firebaseVersion + "/firebase-app.js" );
        jsFirebaseAppCdnReference = new UrlResourceReference( url );

        url = Url.parse( FIREBASE_BASE_URL + firebaseVersion + "/firebase-auth.js" );
        jsFirebaseAuthCdnReference = new UrlResourceReference( url );

        // JavaScript firebase init script preparation
        PackageTextTemplate template;
        template = new PackageTextTemplate( FirebaseAppInit.class, "FirebaseAppInit.js" );
        Map<String, Object> variables = new HashMap<>();

        variables.put( "apiKey", options.getApiKey() );
        variables.put( "projectId", options.getProjectId() );
        variables.put( "databaseName", options.getDatabaseName() );
        variables.put( "bucketName", options.getBucketName() );
        variables.put( "senderId", options.getSenderId() );

        firebaseInitScript = template.asString( variables );

        if ( uiOn )
        {
            // JavaScript firebase UI config script preparation
            template = new PackageTextTemplate( FirebaseAppInit.class, "FirebaseUiConfig.js" );
            variables = new HashMap<>();

            variables.put( "signInSuccessUrl", options.getSignInSuccessUrl() );
            variables.put( "credentialHelper", options.getCredentialHelper() );
            variables.put( "signInOptions", options.getSignInOptionsAsString() );
            variables.put( "termsUrl", options.getTermsUrl() );

            firebaseInitScript = firebaseInitScript + template.asString( variables );
        }

        // CSS reference preparation
        if ( this.uiOn )
        {
            url = Url.parse( FIREBASE_UI_BASE_URL + uiWidgetVersion + "/firebase-ui-auth.css" );
            cssAuthCdnReference = new UrlResourceReference( url );
        }
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
        UrlResourceReference jsAuthCdnReference = new UrlResourceReference( url );

        // CSS header contribution
        if ( this.uiOn )
        {
            response.render( CssHeaderItem.forReference( cssAuthCdnReference ) );
        }

        // JavaScript contribution, order is important. Added at the bottom of HTML, before other script tags.
        JavaScriptReferenceHeaderItem headerItem = JavaScriptHeaderItem.forReference( jsFirebaseAppCdnReference );
        response.render( new PriorityHeaderItem( headerItem ) );

        headerItem = JavaScriptHeaderItem.forReference( jsFirebaseAuthCdnReference );
        response.render( new PriorityHeaderItem( headerItem ) );

        headerItem = JavaScriptHeaderItem.forReference( jsAuthCdnReference );
        response.render( new PriorityHeaderItem( headerItem ) );

        String firebaseInit;
        if ( scope != null && fileName != null )
        {
            Map<String, Object> arguments = null;
            if ( callback != null )
            {
                arguments = new HashMap<>();
                callback.onRequest( arguments );
            }

            PackageTextTemplate template = new PackageTextTemplate( scope, fileName );
            firebaseInit = firebaseInitScript + "\n" + template.asString( arguments );
        }
        else
        {
            firebaseInit = firebaseInitScript;
        }

        JavaScriptHeaderItem scriptItem = JavaScriptHeaderItem.forScript( firebaseInit, "firebase_init" );
        response.render( new PriorityHeaderItem( scriptItem ) );
    }

    /**
     * Specify JavaScript file name to be rendered right after the FirebaseAppInit.js.
     *
     * @param scope    the <code>Class</code> package to be used for loading the specified file name
     * @param fileName the name of the file, relative to the <code>scope</code> position
     */
    public FirebaseAppInit fileName( @Nonnull Class<?> scope, @Nonnull String fileName )
    {
        this.scope = checkNotNull( scope, "Scope cannot be null" );
        this.fileName = checkNotNull( fileName, "fileName cannot be null" );
        return this;
    }

    /**
     * Callback that provides request specific arguments to be rendered
     * by script specified by {@link #fileName(Class, String)}.
     *
     * @param args the arguments callback
     */
    public FirebaseAppInit fileNameArguments( Arguments args )
    {
        this.callback = args;
        return this;
    }

    /**
     * {@code Map<String, Object>} type callback.
     */
    public interface Arguments
            extends Serializable
    {
        void onRequest( @Nonnull Map<String, Object> arguments );
    }
}
