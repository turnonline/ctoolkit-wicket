package org.ctoolkit.wicket.turnonline.theme;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.Strings;
import org.ctoolkit.wicket.turnonline.AppEngineApplication;

import java.util.Set;

import static org.ctoolkit.wicket.turnonline.theme.TurnOnlineThemeSettings.MIN_CSS_SUFFIX;


/**
 * TurnOnline theme wicket initializer. It mounts theme names taken from {@link AppEngineApplication#getThemeNames()}
 * as {@link CssResourceReference} + default one 'turnonline-ui' mounted under '/turnonline-ui.min.css'.
 * The referenced package for CSS is 'org/ctoolkit/turnonline/wicket/theme/turnonline-ui.min.css',
 * the package of this {@link TurnOnlineThemeInitializer}.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class TurnOnlineThemeInitializer
        implements IInitializer
{
    @Override
    public void init( Application application )
    {
        AppEngineApplication gaeApp = ( AppEngineApplication ) application;
        Set<String> themeNames = gaeApp.getThemeNames();

        for ( String theme : themeNames )
        {
            if ( !Strings.isEmpty( theme ) )
            {
                CssResourceReference cssRef;
                cssRef = new CssResourceReference( TurnOnlineThemeInitializer.class, theme + MIN_CSS_SUFFIX );
                TurnOnlineThemeSettings.get().setStylesheetReference( theme, cssRef );

                gaeApp.mountResource( "/" + theme + MIN_CSS_SUFFIX, cssRef );
                gaeApp.getResourceBundles().addBundle( CssHeaderItem.forReference( cssRef ) );
            }
        }

        // init default theme
        ResourceReference defaultRef = TurnOnlineThemeSettings.get().initDefaultStylesheetReference();
        String resourceName = TurnOnlineThemeSettings.get().getDefaultMountResourceName();

        gaeApp.mountResource( "/" + resourceName, defaultRef );
        gaeApp.getResourceBundles().addBundle( CssHeaderItem.forReference( defaultRef ) );

    }

    @Override
    public void destroy( Application application )
    {
        // noop
    }
}
