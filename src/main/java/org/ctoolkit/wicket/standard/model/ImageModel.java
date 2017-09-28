package org.ctoolkit.wicket.standard.model;

import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.model.IModel;

/**
 * The image model that renders value for src attribute of the img tag. For null value renders '/images/no-image.png'.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class ImageModel
        extends AbstractPropertyModel<String>
{
    private static final long serialVersionUID = -5861163872653591143L;

    private final String expression;

    public ImageModel( IModel<?> model, String expression )
    {
        super( model );
        this.expression = expression;
    }

    @Override
    public String getObject()
    {
        String url = super.getObject();

        if ( url == null )
        {
            url = "/images/no-image.png";
        }

        return url;
    }

    @Override
    public String toString()
    {
        return super.toString() + ":expression=[" + expression + "]";
    }

    @Override
    protected String propertyExpression()
    {
        return expression;
    }
}
