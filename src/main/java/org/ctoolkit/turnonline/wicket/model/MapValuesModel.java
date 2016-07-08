package org.ctoolkit.turnonline.wicket.model;

import org.apache.wicket.model.ChainingModel;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The model that takes map's values from the given map.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class MapValuesModel<T>
        extends ChainingModel<List<T>>
{
    private static final long serialVersionUID = 1L;

    public MapValuesModel( IModel<Map<String, T>> model )
    {
        super( checkNotNull( model ) );
    }

    @Override
    public List<T> getObject()
    {
        Map countries = ( Map ) super.getObject();

        if ( countries == null )
        {
            return null;
        }

        @SuppressWarnings( "unchecked" )
        ArrayList<T> arrayList = new ArrayList<>( countries.values() );

        return arrayList;
    }
}