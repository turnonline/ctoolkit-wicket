package org.ctoolkit.turnonline.wicket.model;

import com.google.common.base.Optional;
import org.apache.wicket.model.AbstractPropertyModel;

/**
 * The conditional chaining property model.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class ConditionalPropertyModel<T>
        extends AbstractPropertyModel<T>
        implements IConditionalModel<T>
{
    /**
     * Constructor
     *
     * @param modelObject The nested model object
     */
    public ConditionalPropertyModel( Object modelObject )
    {
        super( modelObject );
    }

    public Optional<T> getConditionalObject()
    {
        if ( isEvaluate() )
        {
            return Optional.fromNullable( super.getObject() );
        }
        return Optional.absent();
    }
}
