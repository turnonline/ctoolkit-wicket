package org.ctoolkit.turnonline.wicket.markup.html.page;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.http.WebResponse;
import org.ctoolkit.turnonline.wicket.markup.html.basic.ULabel;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A site map (or sitemap) is a list of pages of a web site accessible to crawlers or users.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 * @see <a href="http://www.sitemaps.org/protocol.html">Sitemap protocol definition</a>
 */
public abstract class BaseSiteMap
        extends WebPage
{
    /**
     * SiteMap base page.
     */
    public BaseSiteMap()
    {
        RepeatingView rv = new RepeatingView( "urlList" );
        add( rv );

        for ( SiteMapItem item : getItems() )
        {
            WebMarkupContainer urlNode = new WebMarkupContainer( rv.newChildId() );
            rv.add( urlNode );

            urlNode.add( new ULabel( "loc", item.getLoc() ) );
            urlNode.add( new ULabel( "lastmod", formatSiteMapDate( item.getLastmod() ) ) );
            urlNode.add( new ULabel( "changefreq", item.getChangefreq().name().toLowerCase() ) );
            urlNode.add( new ULabel( "priority", Double.valueOf( item.getPriority() ).toString() ) );
        }
    }

    /**
     * Format date to 'yyyy-MM-dd' format.
     *
     * @param date date to format
     * @return formatted date
     */
    private String formatSiteMapDate( Date date )
    {
        checkNotNull( date, "Date cannot be null!" );

        String pattern = "yyyy-MM-dd";
        Format formatter = new SimpleDateFormat( pattern );

        return formatter.format( date );
    }

    @Override
    protected void onAfterRender()
    {
        super.onAfterRender();

        ( ( WebResponse ) getResponse() ).setContentType( "application/xml" );
    }

    /**
     * Return list of {@link SiteMapItem}
     *
     * @return list of {@link SiteMapItem}
     */
    protected abstract List<SiteMapItem> getItems();

}
