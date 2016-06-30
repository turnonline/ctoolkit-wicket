package org.ctoolkit.turnonline.wicket.model;

import org.apache.wicket.Component;
import org.apache.wicket.model.AbstractPropertyModel;

/**
 * The model implementation that stores target model object into wicket session once the object instance has been loaded.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class SessionLoaderModel<T>
        extends AbstractPropertyModel<T>
{
    private static final long serialVersionUID = 1L;

    private final String expression;

    /**
     * Constructor.
     *
     * @param component  the parent component to access session
     * @param expression the property expression as path to access custom value at wicket session
     */
    public SessionLoaderModel( Component component, String expression )
    {
        super( component );
        this.expression = "session." + expression;
    }

    @Override
    public T getObject()
    {
        T loadedObject = super.getObject();

        if ( loadedObject == null )
        {
            loadedObject = load();
            setObject( loadedObject );
        }

        return loadedObject;
    }

    /**
     * Returns the loaded object instance to store in to wicket session.
     *
     * @return the loaded object instance
     */
    protected abstract T load();

    @Override
    protected String propertyExpression()
    {
        return expression;
    }

    /**
     * It calls {@link AbstractPropertyModel#setObject(Object)} as it is required because of GAE session handling.
     * At this point the session is being stored into the underlying datastore.
     */
    @Override
    public void detach()
    {
        setObject( super.getObject() );
        super.detach();
    }
}
