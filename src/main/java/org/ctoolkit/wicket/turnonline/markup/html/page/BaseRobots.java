package org.ctoolkit.wicket.turnonline.markup.html.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.http.WebResponse;

import java.util.List;

/**
 * The Robot Exclusion Standard, also known as the Robots Exclusion Protocol or robots.txt protocol,
 * is a convention to prevent cooperating web crawlers and other web robots from accessing all or
 * part of a website which is otherwise publicly viewable.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 * @see <a href="http://en.wikipedia.org/wiki/Robots.txt">Robots wiki</a>
 */
public abstract class BaseRobots
        extends WebPage
{
    /**
     * Robots base page
     */
    public BaseRobots()
    {
    }

    @Override
    protected void onAfterRender()
    {
        super.onAfterRender();

        ( ( WebResponse ) getResponse() ).setContentType( "text/plain" );

        renderRow( "User-agent", getUserAgent() );
        for ( String disallowUl : getDisallowUrls() )
        {
            renderRow( "Disallow", disallowUl );
        }
        renderRow( "Sitemap", getSiteMapUrl() );
    }

    /**
     * Render row in format <i>key: value</i>
     *
     * @param key   row key
     * @param value row value
     */
    private void renderRow( String key, String value )
    {
        getResponse().write( "\n" + key + ": " + value );
    }

    /**
     * Return googlebot, msnbot, slurp... For all boots use <code>*</code>
     *
     * @return user agent
     */
    protected abstract String getUserAgent();

    /**
     * Return list of disallow urls
     *
     * @return list of disallow urls
     */
    protected abstract List<String> getDisallowUrls();

    /**
     * Return absolute location of sitemap.xml
     *
     * @return location of sitemap.xml
     */
    protected abstract String getSiteMapUrl();
}
