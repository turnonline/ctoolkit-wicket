package org.ctoolkit.turnonline.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.IModel;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The behavior to control visibility of binded component.
 * The component is visible if component's model object is false.
 * In case of {@link #VisibleIfModelFalse(IModel)} the given model is being evaluated
 * to control visibility of the binded component.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class VisibleIfModelFalse
        extends Behavior
{
    private static final long serialVersionUID = 1L;

    private static VisibleIfModelFalse INSTANCE = new VisibleIfModelFalse();

    private final IModel<Boolean> extModel;

    private VisibleIfModelFalse()
    {
        extModel = null;
    }

    public VisibleIfModelFalse( @Nonnull IModel<Boolean> extModel )
    {
        this.extModel = checkNotNull( extModel );
    }

    public static VisibleIfModelFalse get()
    {
        return INSTANCE;
    }

    @Override
    public void onConfigure( Component component )
    {
        Boolean value;
        if ( extModel == null )
        {
            value = ( Boolean ) component.getDefaultModelObject();
        }
        else
        {
            value = extModel.getObject();
        }
        checkNotNull( value, "The model cannot return null value." );
        component.setVisible( !value );
    }
}
