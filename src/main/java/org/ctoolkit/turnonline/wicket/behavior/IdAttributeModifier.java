package org.ctoolkit.turnonline.wicket.behavior;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

/**
 * The 'id' attribute modifier.to be replaced with value or model.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class IdAttributeModifier
        extends AttributeModifier
{
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new 'id' modifier.
     *
     * @param value the value for the attribute
     */
    public IdAttributeModifier( Serializable value )
    {
        super( "id", value );
    }

    /**
     * Creates a new 'id' modifier
     *
     * @param replaceModel the model to replace the value with
     */
    public IdAttributeModifier( IModel<?> replaceModel )
    {
        super( "id", replaceModel );
    }

    public String getMarkupId()
    {
        return getReplaceModel().getObject().toString();
    }
}
