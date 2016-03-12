package org.ctoolkit.turnonline.wicket.gwt;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.MetaDataHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Response;

/**
 * The GWT script and locale property renderer. It renders iframe to support history and script for source.
 * The meta tag for locale will be rendered if locale model has provided and bind component (Page)
 * has model attached.
 * <p/>
 * This behavior is temporary and is going to be removed at the end of request and never reattached.
 * <p/>
 * It should be added in to a page instance.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class GwtScriptAppender
        extends Behavior
{
    private static final long serialVersionUID = 1L;

    private final String[] sources;

    private IModel<String> localeModel;

    /**
     * Constructor.
     *
     * @param source the GWT source path
     */
    public GwtScriptAppender( String... source )
    {
        this( null, source );
    }

    /**
     * Constructor.
     *
     * @param localeModel the gwt locale model in order to render meta tag 'gwt:property' as gwt locale.
     * @param sources     the array of GWT source path
     */
    public GwtScriptAppender( IModel<String> localeModel, String... sources )
    {
        this.sources = sources;
        this.localeModel = localeModel;
    }

    @Override
    public void renderHead( Component component, IHeaderResponse response )
    {
        final IModel<?> model = component.getDefaultModel();

        // meta tag gwt locale setup if any
        if ( localeModel != null && model != null )
        {
            response.render( MetaDataHeaderItem.forMetaTag( new Model<>( "gwt:property" ), localeModel ) );
        }
    }

    @Override
    public void afterRender( final Component component )
    {
        super.afterRender( component );
        Response r = component.getResponse();

        //iframe to support GWT history
        r.write( "<iframe src=\"javascript:''\" id=\"__gwt_historyFrame\" tabIndex='-1'\n" +
                "            style=\"position:absolute;width:0;height:0;border:0\"></iframe>" );

        // GWT source script snippet
        for ( String src : sources )
        {
            r.write( "<script type=\"text/javascript\" language=\"javascript\" src=\"" );
            r.write( src );
            r.write( "\">" );
            r.write( "</script>" );
        }

    }

    @Override
    public boolean isTemporary( Component component )
    {
        return true;
    }
}
