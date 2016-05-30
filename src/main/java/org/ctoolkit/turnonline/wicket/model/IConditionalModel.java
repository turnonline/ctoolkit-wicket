package org.ctoolkit.turnonline.wicket.model;

import com.google.common.base.Optional;
import org.apache.wicket.model.IModel;

/**
 * The model declaration where target value {@link IModel#getObject()} will be evaluated only for positive value of
 * {@link #isEvaluate()}.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public interface IConditionalModel<T>
        extends IModel<T>
{
    /**
     * Returns optional target object.
     *
     * @return the target object reference if any
     */
    Optional<T> getConditionalObject();

    /**
     * Returns boolean indication whether target object should be evaluated or not.
     *
     * @return true to evaluate target object
     */
    boolean isEvaluate();
}
