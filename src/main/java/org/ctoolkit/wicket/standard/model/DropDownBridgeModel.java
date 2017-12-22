package org.ctoolkit.wicket.standard.model;

import org.apache.wicket.model.ChainingModel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The drop down model that is a bridge between choices model (map of code and choice)
 * and and target model.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class DropDownBridgeModel<CHOICE, T>
        extends ChainingModel<CHOICE>
{
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger( DropDownBridgeModel.class );

    /**
     * The map of Code - Choice
     */
    private IModel<Map<String, CHOICE>> choicesModel;

    /**
     * Constructor.
     *
     * @param model        the target model as a source of default choice code
     *                     or to be updated by selected choice
     * @param choicesModel the map of choices as a source for drop down list
     */
    public DropDownBridgeModel( IModel<T> model, IModel<Map<String, CHOICE>> choicesModel )
    {
        super( checkNotNull( model ) );
        this.choicesModel = checkNotNull( choicesModel );
    }

    @Override
    public CHOICE getObject()
    {
        @SuppressWarnings( "unchecked" )
        T target = ( T ) super.getObject();
        checkNotNull( target, "The target instance of the model cannot be null." );

        String value = getCode( target );

        // the target instance does not have a value set yet
        if ( value == null )
        {
            return null;
        }

        Map<String, CHOICE> choices = choicesModel.getObject();
        checkNotNull( choices, "The choices map cannot be null." );

        if ( choices.isEmpty() )
        {
            logger.warn( "The choices map is empty." );
            return null;
        }

        return choices.get( value );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public void setObject( CHOICE object )
    {
        T target = ( T ) super.getObject();
        checkNotNull( target, "The target instance of the model cannot be null." );

        updateCode( object, target );
    }

    /**
     * Returns the code from the target object as a key to take a value from the choice map.
     * It may return <tt>null</tt> value.
     *
     * @param target the instance taken from the target model
     * @return the code as a key fro the choice map
     */
    @SuppressWarnings( "WeakerAccess" )
    protected abstract String getCode( @Nonnull T target );

    /**
     * Updates the selected code value from the choice to the target instance.
     *
     * @param choice the selected choice
     * @param target the target instance to be updated by selected value
     */
    @SuppressWarnings( "WeakerAccess" )
    protected abstract void updateCode( @Nullable CHOICE choice, @Nonnull T target );

    @Override
    public void detach()
    {
        choicesModel.detach();
        super.detach();
    }
}