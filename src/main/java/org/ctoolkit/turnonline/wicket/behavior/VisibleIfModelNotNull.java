package org.ctoolkit.turnonline.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.IModel;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The behavior to control visibility of binded component.
 * The component is visible if component's model object is not null.
 * In case of {@link #VisibleIfModelNotNull(IModel)} the given model is being evaluated
 * to control visibility of the binded component.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class VisibleIfModelNotNull
        extends Behavior
{
    private static final long serialVersionUID = 8251320963734875048L;

    private static VisibleIfModelNotNull INSTANCE = new VisibleIfModelNotNull();

    private final IModel<?> extModel;

    private VisibleIfModelNotNull()
    {
        extModel = null;
    }

    public VisibleIfModelNotNull( @Nonnull IModel<?> model )
    {
        this.extModel = checkNotNull( model );
    }

    public static VisibleIfModelNotNull get()
    {
        return INSTANCE;
    }

    @Override
    public void onConfigure( Component component )
    {
        if ( extModel == null )
        {
            component.setVisible( component.getDefaultModelObject() != null );
        }
        else
        {
            component.setVisible( extModel.getObject() != null );
        }
    }
}
