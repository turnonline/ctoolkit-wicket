package org.ctoolkit.wicket.turnonline.menu;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.ctoolkit.wicket.turnonline.menu.SearchResponse.ItemSearchResponse;
import org.ctoolkit.wicket.turnonline.menu.SearchResponse.SectionSearchResponse;
import org.ctoolkit.wicket.turnonline.util.FormatUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * The header search box component.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public abstract class SearchBox
        extends AutoCompleteTextField<SearchResponse>
{
    private static final long serialVersionUID = 1723221946022273137L;

    private static final AutoCompleteSettings SETTINGS = new AutoCompleteSettings();

    private static final SearchBoxRenderer RENDERER = new SearchBoxRenderer();

    static
    {
        SETTINGS.setMinInputLength( 3 );
        SETTINGS.setAdjustInputWidth( false );
        SETTINGS.setShowCompleteListOnFocusGain( true );
    }

    public SearchBox( String id )
    {
        super( id, null, null, RENDERER, SETTINGS );
    }

    @Override
    public void renderHead( final IHeaderResponse response )
    {
        response.render( OnDomReadyHeaderItem.forScript( getScript( this ) ) );
    }

    private String getScript( Component component )
    {
        PackageTextTemplate template = new PackageTextTemplate( SearchBox.class, "search-box.js" );

        Map<String, Object> variables = new HashMap<>();
        variables.put( "markupId", component.getMarkupId() );

        return template.asString( variables );
    }

    private static class SearchBoxRenderer
            extends AbstractAutoCompleteRenderer<SearchResponse>
    {
        private static final long serialVersionUID = -1661088661471669616L;

        @Override
        protected void renderChoice( SearchResponse object, Response response, String criteria )
        {
            StringBuilder sb = new StringBuilder();

            if ( object instanceof SectionSearchResponse ) // render group section
            {
                SectionSearchResponse section = ( SectionSearchResponse ) object;

                sb.append( "<div class='search-response-section " ).append( section.getIcon().getCssName() ).append( "'>" );
                sb.append( section.getTitle() );
                sb.append( "</div>" );
            }
            else if ( object instanceof ItemSearchResponse ) // render result item
            {
                ItemSearchResponse item = ( ItemSearchResponse ) object;

                sb.append( "<a href='" ).append( item.getUrl() ).append( "'" ).append( " class='search-response-item'>" );

                sb.append( "<div class='search-response-title'>" );
                sb.append( FormatUtil.highlight( object.getTitle(), criteria ) );
                sb.append( "</div>" );

                sb.append( "<div class='search-response-description'>" );
                sb.append( FormatUtil.highlight( item.getDescription(), criteria ) );
                sb.append( "</div>" );

                sb.append( "</a>" );
            }
            else
            {
                throw new IllegalArgumentException( "Unknown object type: " + object.getClass() );
            }

            response.write( sb.toString() );
        }

        @Override
        protected String getTextValue( SearchResponse object )
        {
            return object.getTitle();
        }
    }
}
