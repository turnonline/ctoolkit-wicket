package org.ctoolkit.wicket.standard.markup.html.social;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

import java.text.MessageFormat;

/**
 * The Google Plus share button web markup implementation.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class GooglePlusButton
        extends WebMarkupContainer
{
    private static final long serialVersionUID = 1L;

    private static final String GOOGLE_SCRIPT =
            "<script type=''text/javascript'' src=''{0}'' async defer>'{'lang:''{1}'' '}'</script>";

    private static final String GOOGLE_PLUS_SDK = "https://apis.google.com/js/platform.js";

    protected String size = "medium";

    protected String annotation = "none";

    public GooglePlusButton( String id, IModel<String> model )
    {
        super( id );
        setDefaultModel( model );

        final String lang = getLocale().getLanguage();

        add( new Behavior()
        {
            private static final long serialVersionUID = 1L;

            public void renderHead( Component component, IHeaderResponse response )
            {
                response.render( StringHeaderItem.forString( MessageFormat.format( GOOGLE_SCRIPT, GOOGLE_PLUS_SDK,
                        lang + "_" + lang.toUpperCase() ) ) );
            }
        } );
    }

    @Override
    protected void onComponentTag( ComponentTag tag )
    {
        super.onComponentTag( tag );

        tag.put( "class", "g-plusone" );
        tag.put( "data-href", getDefaultModelObjectAsString() );
        tag.put( "data-size", size );
        tag.put( "data-annotation", annotation );
    }

    public String getSize()
    {
        return size;
    }

    public void setSize( String size )
    {
        this.size = size;
    }

    public String getAnnotation()
    {
        return annotation;
    }

    public void setAnnotation( String annotation )
    {
        this.annotation = annotation;
    }
}
