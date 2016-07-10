package org.ctoolkit.turnonline.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.IModel;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The behavior to control visibility of binded component.
 * The component is visible if component's model object is true.
 * In case of {@link #VisibleIfModelTrue(IModel)} the given model is being evaluated
 * to control visibility of the binded component.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class VisibleIfModelTrue
        extends Behavior
{
    private static final long serialVersionUID = 1L;

    private static VisibleIfModelTrue INSTANCE = new VisibleIfModelTrue();

    private final IModel<Boolean> extModel;

    private VisibleIfModelTrue()
    {
        this.extModel = null;
    }

    public VisibleIfModelTrue( @Nonnull IModel<Boolean> extModel )
    {
        this.extModel = checkNotNull( extModel );
    }

    public static VisibleIfModelTrue get()
    {
        return INSTANCE;
    }

    @Override
    public void onConfigure( Component component )
    {
        Boolean visible;
        if ( extModel == null )
        {
            visible = ( Boolean ) component.getDefaultModelObject();
        }
        else
        {
            visible = extModel.getObject();
        }

        checkNotNull( visible, "The model cannot return null value." );
        component.setVisible( visible );
    }
}
