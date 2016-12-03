package org.ctoolkit.wicket.standard.model;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.core.util.lang.PropertyResolver;
import org.apache.wicket.core.util.lang.PropertyResolverConverter;
import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The wicket model to set selected drop down choice in to target model instance and vise versa.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class DropDownListBridgeModel<CHOICE, T>
        extends AbstractPropertyModel<CHOICE>
{
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger( DropDownListBridgeModel.class );

    private final String expression;

    private final IModel<List<CHOICE>> listModel;

    /**
     * Constructor.
     *
     * @param targetModel the model as a target instance as a source of default choice
     *                    or to be updated by selected choice
     * @param expression  the expression as path to target model property
     * @param listModel   the list model of the choices
     */
    public DropDownListBridgeModel( IModel<?> targetModel, String expression, IModel<List<CHOICE>> listModel )
    {
        super( checkNotNull( targetModel ) );
        this.expression = checkNotNull( expression );
        this.listModel = checkNotNull( listModel );
    }

    @Override
    public CHOICE getObject()
    {
        @SuppressWarnings( "unchecked" )
        T target = ( T ) super.getObject();

        for ( CHOICE next : listModel.getObject() )
        {
            checkNotNull( next, "A choice from the list cannot be null." );
            if ( equals( next, target ) )
            {
                return next;
            }
        }

        logger.warn( "No code book instance has been found for evaluated code: " + target );

        return null;
    }

    @Override
    public void setObject( CHOICE object )
    {
        String propertyExpression = propertyExpression();
        if ( propertyExpression.startsWith( "." ) )
        {
            throw new IllegalArgumentException(
                    "Property expressions cannot start with a '.' character" );
        }

        PropertyResolverConverter prc;
        prc = new PropertyResolverConverter( Application.get().getConverterLocator(),
                Session.get().getLocale() );
        PropertyResolver.setValue( propertyExpression, getInnermostModelOrObject(), getValue( object ), prc );
    }

    /**
     * Returns the boolean indication whether given choice represents a same value
     * as a value from the target instance.
     *
     * @param choice the selected choice
     * @param target the resolved value from the target instance
     * @return true if same
     */
    @SuppressWarnings( "WeakerAccess" )
    protected abstract boolean equals( @Nonnull CHOICE choice, @Nullable T target );

    /**
     * Returns the value from the choice instance to be set to the target model.
     *
     * @param choice the selected choice as a source for a value
     * @return the value from the choice instance to be set to the target model
     */
    @SuppressWarnings( "WeakerAccess" )
    protected abstract T getValue( @Nullable CHOICE choice );

    @Override
    protected String propertyExpression()
    {
        return expression;
    }

    @Override
    public void detach()
    {
        listModel.detach();
        super.detach();
    }
}
