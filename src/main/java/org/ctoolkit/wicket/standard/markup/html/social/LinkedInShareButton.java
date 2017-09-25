package org.ctoolkit.wicket.standard.markup.html.social;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.encoding.UrlEncoder;

import java.text.MessageFormat;

/**
 * The LinkedIn share button web markup implementation.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class LinkedInShareButton
        extends WebMarkupContainer
{
    private static final long serialVersionUID = 1L;

    private static final String SCRIPT =
            "<script type=''text/javascript'' src=''{0}''> lang: en_US </script>";

    private static final String SRC = "//platform.linkedin.com/in.js";

    private String counter;

    /**
     * Creates LinkedIn share button web markup.
     *
     * @param id    the non-null id of this component
     * @param model the URL model
     */
    public LinkedInShareButton( String id, IModel<String> model )
    {
        super( id );
        setDefaultModel( model );

        add( new Behavior()
        {
            private static final long serialVersionUID = 1L;

            public void renderHead( Component component, IHeaderResponse response )
            {
                response.render( StringHeaderItem.forString( MessageFormat.format( SCRIPT, SRC ) ) );
            }
        } );
    }

    @Override
    protected void onComponentTag( ComponentTag tag )
    {
        super.onComponentTag( tag );

        UrlEncoder encoder = UrlEncoder.FULL_PATH_INSTANCE;
        String url = encoder.encode( getDefaultModelObjectAsString(), "UTF-8" );

        tag.setName( "script" );
        tag.put( "type", "IN/Share" );
        tag.put( "data-url", url );

        if ( counter != null )
        {
            tag.put( "data-counter", counter );
        }
    }

    public String getCounter()
    {
        return counter;
    }

    public void setCounter( String counter )
    {
        this.counter = counter;
    }
}
