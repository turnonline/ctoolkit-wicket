package org.ctoolkit.wicket.standard.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

import javax.annotation.Nonnull;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The behavior to control visibility of binded component.
 * The component is visible if component's collection model is empty.
 * In case of {@link #VisibleIfCollectionModelEmpty(IModel)} the given model is being evaluated
 * to control visibility of the binded component.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class VisibleIfCollectionModelEmpty
        extends CollectionModelVisibilityBehavior
{
    private static final long serialVersionUID = 1870124491684948750L;

    private static VisibleIfCollectionModelEmpty INSTANCE = new VisibleIfCollectionModelEmpty();

    private final IModel<? extends Collection<?>> extModel;

    private VisibleIfCollectionModelEmpty()
    {
        extModel = null;
    }

    public VisibleIfCollectionModelEmpty( @Nonnull IModel<? extends Collection<?>> model )
    {
        this.extModel = checkNotNull( model );
    }

    public static VisibleIfCollectionModelEmpty get()
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
        component.setVisible( isEmpty( model ) );
    }
}
