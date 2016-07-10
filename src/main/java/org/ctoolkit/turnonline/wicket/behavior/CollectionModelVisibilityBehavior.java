package org.ctoolkit.turnonline.wicket.behavior;

import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.IModel;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * The behavior to control visibility of binded component with collection based model.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class CollectionModelVisibilityBehavior
        extends Behavior
{
    private static final long serialVersionUID = 1L;

    /**
     * Returns true if model or model object is null or collection is empty.
     *
     * @param model the collection model of the binded component
     * @return true if collection is empty.
     */
    protected boolean isEmpty( @Nullable IModel<? extends Collection<?>> model )
    {
        if ( model == null )
        {
            return true;
        }

        Collection<?> collection = model.getObject();
        return collection == null || collection.isEmpty();
    }
}
