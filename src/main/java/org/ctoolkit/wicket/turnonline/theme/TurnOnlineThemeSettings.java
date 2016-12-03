package org.ctoolkit.wicket.turnonline.theme;

import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

import java.util.HashMap;
import java.util.Map;

/**
 * TurnOnline theme {@link CssResourceReference} settings.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class TurnOnlineThemeSettings
{
    public static final String STANDARD = "turnonline-ui";

    static final String MIN_CSS_SUFFIX = ".min.css";

    private static TurnOnlineThemeSettings INSTANCE = new TurnOnlineThemeSettings();

    private Map<String, ResourceReference> themeMap = new HashMap<>();

    /**
     * Default constructor.
     */
    private TurnOnlineThemeSettings()
    {
    }

    public static TurnOnlineThemeSettings get()
    {
        return INSTANCE;
    }

    /**
     * Returns the default stylesheet reference under name {@link #STANDARD}.
     *
     * @return the default stylesheet reference
     */
    public ResourceReference getDefaultStylesheetReference()
    {
        ResourceReference reference = themeMap.get( STANDARD );

        if ( reference == null )
        {
            String msg = "First default stylesheet reference must be initialized, employ "
                    + TurnOnlineThemeInitializer.class.getSimpleName();

            throw new NullPointerException( msg );
        }

        return reference;
    }

    /**
     * Initialize the default stylesheet reference under name {@link #STANDARD}.
     *
     * @return the newly initialized stylesheet reference
     */
    CssResourceReference initDefaultStylesheetReference()
    {
        CssResourceReference cssRef = new CssResourceReference( TurnOnlineThemeInitializer.class, getDefaultMountResourceName() );
        this.themeMap.put( STANDARD, cssRef );

        return cssRef;
    }

    /**
     * Returns the default mount resource name.
     *
     * @return the default mount resource name
     */
    String getDefaultMountResourceName()
    {
        return STANDARD + MIN_CSS_SUFFIX;
    }

    /**
     * Returns the stylesheet reference.
     *
     * @param theme the theme name of the requested stylesheet reference
     * @return the stylesheet reference
     */
    public ResourceReference getStylesheetReference( String theme )
    {
        return themeMap.get( theme );
    }

    /**
     * Sets the stylesheet reference.
     *
     * @param theme the theme name of the stylesheet reference as a key
     * @return the stylesheet reference to be set
     */
    void setStylesheetReference( String theme, ResourceReference stylesheetReference )
    {
        this.themeMap.put( theme, stylesheetReference );
    }
}
