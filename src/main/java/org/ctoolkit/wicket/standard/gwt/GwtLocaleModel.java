package org.ctoolkit.wicket.standard.gwt;

import org.apache.wicket.Component;
import org.ctoolkit.wicket.standard.model.LanguageModel;

/**
 * The property model to render expression's value as <code>"locale=${gwtLocaleExpression}"</code>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class GwtLocaleModel
        extends LanguageModel
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param component the parent component to access session
     */
    public GwtLocaleModel( Component component )
    {
        super( component );
    }

    @Override
    public String getObject()
    {
        String language = super.getObject();

        if ( language == null )
        {
            return null;
        }
        else
        {
            return "locale=" + language.toLowerCase();
        }
    }
}
