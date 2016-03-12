package org.ctoolkit.turnonline.wicket.gwt;

import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.model.IModel;

/**
 * The property model to render expression's value as <code>"locale=${gwtLocaleExpression}"</code>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class GwtLocaleModel
        extends AbstractPropertyModel<String>
{
    private static final long serialVersionUID = 1L;

    private final String gwtLocaleExpression;

    public GwtLocaleModel( IModel<?> model, String gwtLocaleExpression )
    {
        super( model );
        this.gwtLocaleExpression = gwtLocaleExpression;
    }

    @Override
    public String getObject()
    {
        String locale = super.getObject();

        if ( locale == null )
        {
            return null;
        }
        else
        {
            return "locale=" + locale.toLowerCase();
        }
    }

    @Override
    protected String propertyExpression()
    {
        return gwtLocaleExpression;
    }
}
