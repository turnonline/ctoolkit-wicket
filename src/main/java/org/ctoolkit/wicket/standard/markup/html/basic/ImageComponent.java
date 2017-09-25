package org.ctoolkit.wicket.standard.markup.html.basic;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * The image component that accepts relative or absolute URL.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class ImageComponent
        extends WebComponent
{
    private static final long serialVersionUID = 1L;

    public ImageComponent( String id )
    {
        super( id );
    }

    public ImageComponent( String id, String imageUrl )
    {
        this( id, new Model<>( imageUrl ) );
    }

    public ImageComponent( final String id, IModel<?> model )
    {
        super( id, model );
    }

    protected void onComponentTag( ComponentTag tag )
    {
        super.onComponentTag( tag );
        checkComponentTag( tag, "img" );

        tag.put( "src", getDefaultModelObjectAsString() );
    }
}
