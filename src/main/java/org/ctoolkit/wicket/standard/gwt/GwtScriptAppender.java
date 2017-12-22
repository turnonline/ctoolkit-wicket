package org.ctoolkit.wicket.standard.gwt;

import com.google.common.base.Strings;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.MetaDataHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The GWT script and locale property renderer. The meta tag for locale will be rendered
 * if locale model has provided and bind component (Page) has model attached.
 * <p>
 * <b>Polymer / Web Components</b>
 * If defined via setter the Web Components JavaScript URL reference will be rendered as first.
 * <p>
 * This behavior is temporary and is going to be removed at the end of request and never reattached.
 * <p>
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

    private String polymerImportPrefix;

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
     * @param sources     the array of the GWT widget source paths, relative to the webapp folder
     */
    public GwtScriptAppender( IModel<String> localeModel, String... sources )
    {
        this.sources = checkNotNull( sources );
        this.localeModel = localeModel;
    }

    /**
     * Sets the javascript GWT app path prefix, the path where web components import is being placed.
     * If set, the standard GWT generated path to the web components javascript
     * will be appended to be added as a head scrip tag.
     *
     * @param prefix the GWT app path prefix to be set
     */
    public void setWebComponentsImportPrefix( String prefix )
    {
        this.polymerImportPrefix = prefix;
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

        // Polymer javascript import comes as first if defined
        if ( !Strings.isNullOrEmpty( polymerImportPrefix ) )
        {
            r.write( "<script src=\"" );
            r.write( polymerImportPrefix );
            r.write( "/bower_components/webcomponentsjs/webcomponents.js" );
            r.write( "\">" );
            r.write( "</script>" );
        }

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
