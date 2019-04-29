package org.ctoolkit.wicket.standard.model;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.model.StringResourceModel;

/**
 * Internationalization resource model.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class I18NResourceModel
        extends StringResourceModel
{
    private static final long serialVersionUID = 1L;

    public I18NResourceModel( String resourceKey, Object... parameters )
    {
        super( resourceKey, ( Component ) null );
        setDefaultValue( notFoundProperty( Session.get().getLocale().getLanguage(), resourceKey ) );
        setParameters( parameters );
    }

    public I18NResourceModel( Builder builder, Object... parameters )
    {
        super( builder.resourceKey, ( Component ) null );
        setDefaultValue( builder.defaultValue );
        setParameters( parameters );
    }

    /**
     * Return not found property string
     *
     * @param language property language
     * @param key      property key
     * @return not found property string in format ##{language}|{key}##
     */
    private static String notFoundProperty( String language, String key )
    {
        return "##" + language.toLowerCase() + "|" + key + "##";
    }

    public static class Builder
    {
        private String resourceKey;

        private String defaultValue;

        public Builder setResourceKey( String resourceKey )
        {
            this.resourceKey = resourceKey;
            return this;
        }

        public Builder setDefaultValue( String defaultValue )
        {
            this.defaultValue = defaultValue;
            return this;
        }
    }
}
