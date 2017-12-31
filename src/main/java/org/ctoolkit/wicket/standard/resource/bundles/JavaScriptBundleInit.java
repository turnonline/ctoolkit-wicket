package org.ctoolkit.wicket.standard.resource.bundles;

import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.bundles.ConcatResourceBundleReference;
import org.apache.wicket.util.string.Strings;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * It mounts given JavaScript file names as {@link JavaScriptResourceReference}.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class JavaScriptBundleInit
        extends ConcatResourceBundleReference<JavaScriptReferenceHeaderItem>
{
    public static final String NAME = "javascript-bundle.js";

    private static final long serialVersionUID = 1L;

    private final String name;

    private Class<?> scope;

    /**
     * Constructor, referenced with default name {@link #NAME}.
     *
     * @param jsFiles the list of JS file names,
     *                either relative to the resources folder (prefixed with '/') or scope's package
     */
    public JavaScriptBundleInit( @Nonnull List<String> jsFiles )
    {
        this( JavaScriptBundleInit.class, NAME, jsFiles );
    }

    /**
     * Constructor, referenced with default name {@link #NAME}.
     *
     * @param scope   the class package used as a path to the JS files
     * @param jsFiles the list of JS file names,
     *                either relative to the resources folder (prefixed with '/') or scope's package
     */
    public JavaScriptBundleInit( @Nonnull Class<?> scope, @Nonnull List<String> jsFiles )
    {
        this( scope, NAME, jsFiles );
    }

    /**
     * Constructor.
     *
     * @param scope   the class package used as a path to the JS files
     * @param name    the referenced bundle name
     * @param jsFiles the list of JS file names,
     *                either relative to the resources folder (prefixed with '/') or scope's package
     */
    public JavaScriptBundleInit( @Nonnull Class<?> scope, @Nonnull String name, @Nonnull List<String> jsFiles )
    {
        super( JavaScriptBundleInit.class, checkNotNull( name ), getHeaderItems( scope, jsFiles ) );
        this.scope = checkNotNull( scope );
        this.name = name;
    }

    private static List<JavaScriptReferenceHeaderItem> getHeaderItems( @Nonnull Class<?> scope,
                                                                       @Nonnull List<String> jsFiles )
    {
        List<JavaScriptReferenceHeaderItem> resources = new ArrayList<>();

        for ( String file : jsFiles )
        {
            if ( !Strings.isEmpty( file ) )
            {
                JavaScriptResourceReference reference;
                reference = new JavaScriptResourceReference( scope, file );

                resources.add( JavaScriptHeaderItem.forReference( reference ) );
            }
        }

        return resources;
    }

    /**
     * Registers this {@link ResourceReference}.
     *
     * @param application the application where to register this resource reference
     * @return {@code true} if the resource was registered successfully or has been registered previously already.
     */
    public boolean register( @Nonnull WebApplication application )
    {
        List<JavaScriptReferenceHeaderItem> resources = getProvidedResources();
        if ( !resources.isEmpty() )
        {
            ConcatResourceBundleReference<JavaScriptReferenceHeaderItem> concatenated;
            concatenated = new ConcatResourceBundleReference<>( this.scope, this.name, resources );

            application.mountResource( "/" + this.name, concatenated );
            application.getResourceBundles().addBundle( JavaScriptReferenceHeaderItem.forReference( concatenated ) );
        }

        return application.getResourceReferenceRegistry().registerResourceReference( this );
    }
}
