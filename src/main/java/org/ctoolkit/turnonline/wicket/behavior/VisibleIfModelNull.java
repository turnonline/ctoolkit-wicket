package org.ctoolkit.turnonline.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.IModel;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The behavior to control visibility of binded component.
 * The component is visible if component's model object is null.
 * In case of {@link #VisibleIfModelNull(IModel)} the given model is being evaluated
 * to control visibility of the binded component.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class VisibleIfModelNull
        extends Behavior
{
    private static final long serialVersionUID = -1962017767728949618L;

    private static VisibleIfModelNull INSTANCE = new VisibleIfModelNull();

    private final IModel<?> extModel;

    private VisibleIfModelNull()
    {
        extModel = null;
    }

    public VisibleIfModelNull( IModel<?> model )
    {
        this.extModel = checkNotNull( model );
    }

    public static VisibleIfModelNull get()
    {
        return INSTANCE;
    }

    @Override
    public void onConfigure( Component component )
    {
        if ( extModel == null )
        {
            component.setVisible( component.getDefaultModelObject() == null );
        }
        else
        {
            component.setVisible( extModel.getObject() == null );
        }
    }
}
