package org.ctoolkit.turnonline.wicket.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import javax.annotation.Nonnull;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The behavior to control visibility of binded component.
 * The component is visible if component's collection model is NOT empty.
 * In case of {@link #VisibleIfCollectionModelNotEmpty(IModel)} the given model is being evaluated
 * to control visibility of the binded component.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class VisibleIfCollectionModelNotEmpty
        extends CollectionModelVisibilityBehavior
{
    private static final long serialVersionUID = -8830062669395543921L;

    private static VisibleIfCollectionModelNotEmpty INSTANCE = new VisibleIfCollectionModelNotEmpty();

    private final IModel<? extends Collection<?>> extModel;

    private VisibleIfCollectionModelNotEmpty()
    {
        extModel = null;
    }

    public VisibleIfCollectionModelNotEmpty( @Nonnull IModel<? extends Collection<?>> model )
    {
        this.extModel = checkNotNull( model );
    }

    public static VisibleIfCollectionModelNotEmpty get()
    {
        return INSTANCE;
    }

    @Override
    public void onConfigure( Component component )
    {
        IModel<? extends Collection<?>> model;
        if ( extModel == null )
        {
            //noinspection unchecked
            model = ( IModel<? extends Collection<?>> ) component.getDefaultModel();
        }
        else
        {
            model = extModel;
        }
        component.setVisible( !isEmpty( model ) );
    }
}
