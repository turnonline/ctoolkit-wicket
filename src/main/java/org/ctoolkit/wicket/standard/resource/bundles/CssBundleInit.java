package org.ctoolkit.wicket.standard.resource.bundles;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.bundles.ConcatResourceBundleReference;
import org.apache.wicket.util.string.Strings;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * It mounts given CSS files as {@link CssResourceReference}.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class CssBundleInit
        extends ConcatResourceBundleReference<CssReferenceHeaderItem>
{
    public static final String NAME = "theme-bundle.css";

    private static final long serialVersionUID = 1L;

    private final String name;

    private Class<?> scope;

    /**
     * Constructor, referenced with default name {@link #NAME}.
     *
     * @param cssFiles the list of CSS file names,
     *                 either relative to the resources folder (prefixed with '/') or scope's package
     */
    public CssBundleInit( @Nonnull Set<String> cssFiles )
    {
        this( CssBundleInit.class, NAME, cssFiles );
    }

    /**
     * Constructor, referenced with default name {@link #NAME}.
     *
     * @param scope    the class package used as a path to the CSS files
     * @param cssFiles the list of CSS file names,
     *                 either relative to the resources folder (prefixed with '/') or scope's package
     */
    public CssBundleInit( @Nonnull Class<?> scope, @Nonnull Set<String> cssFiles )
    {
        this( scope, NAME, cssFiles );
    }

    /**
     * Constructor.
     *
     * @param scope    the class package used as a path to the CSS files
     * @param name     the referenced bundle name
     * @param cssFiles the list of CSS file names,
     *                 either relative to the resources folder (prefixed with '/') or scope's package
     */
    public CssBundleInit( @Nonnull Class<?> scope, @Nonnull String name, @Nonnull Set<String> cssFiles )
    {
        super( scope, checkNotNull( name ), getHeaderItems( scope, cssFiles ) );
        this.scope = checkNotNull( scope );
        this.name = name;
    }

    private static List<CssReferenceHeaderItem> getHeaderItems( @Nonnull Class<?> scope, @Nonnull Set<String> cssFiles )
    {
        List<CssReferenceHeaderItem> resources = new ArrayList<>();

        for ( String file : cssFiles )
        {
            if ( !Strings.isEmpty( file ) )
            {
                CssResourceReference reference;
                reference = new CssResourceReference( scope, file );

                resources.add( CssHeaderItem.forReference( reference ) );
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
        List<CssReferenceHeaderItem> resources = getProvidedResources();
        if ( !resources.isEmpty() )
        {
            ConcatResourceBundleReference<CssReferenceHeaderItem> concatenated;
            concatenated = new ConcatResourceBundleReference<>( this.scope, this.name, resources );

            application.mountResource( "/" + this.name, concatenated );
            application.getResourceBundles().addBundle( CssReferenceHeaderItem.forReference( concatenated ) );
        }

        return application.getResourceReferenceRegistry().registerResourceReference( this );
    }
}
