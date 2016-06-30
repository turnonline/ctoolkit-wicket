package org.ctoolkit.turnonline.wicket.markup.html.page;

import java.util.Date;

/**
 * SiteMap item is used to fill site map page
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 * @see <a href="http://www.sitemaps.org/protocol.html">Sitemap protocol definition</a>
 */
public class SiteMapItem
{
    /**
     * URL of the page. This URL must begin with the protocol (such as http) and end with a trailing slash,
     * if your web server requires it. This value must be less than 2,048 characters.
     */
    private String loc;

    /**
     * The date of last modification of the file. This date should be in W3C Datetime format.
     * This format allows you to omit the time portion, if desired, and use YYYY-MM-DD.
     * Note that this tag is separate from the If-Modified-Since (304) header the server can return,
     * and search engines may use the information from both sources differently.
     * The default value is now
     */
    private Date lastmod = new Date();

    /**
     * How frequently the page is likely to change. This value provides general information to
     * search engines and may not correlate exactly to how often they crawl the page.
     * The default value is MONTHLY
     */
    private ChangeFrequency changefreq = ChangeFrequency.MONTHLY;

    /**
     * The priority of this URL relative to other URLs on your site.
     * Valid values range from 0.0 to 1.0. This value does not affect how your pages are compared
     * to pages on other sites—it only lets the search engines know which pages you deem most
     * important for the crawlers. The default priority of a page is 0.5.
     */
    private double priority = 0.5;

    /**
     * Create new site map item with specified location
     *
     * @param loc location parameter, e.g. http://foo.com
     */
    public SiteMapItem( String loc )
    {
        this.loc = loc;
    }

    public String getLoc()
    {
        return loc;
    }

    public void setLoc( String loc )
    {
        this.loc = loc;
    }

    public Date getLastmod()
    {
        return lastmod;
    }

    public void setLastmod( Date lastmod )
    {
        this.lastmod = lastmod;
    }

    public ChangeFrequency getChangefreq()
    {
        return changefreq;
    }

    public void setChangefreq( ChangeFrequency changefreq )
    {
        this.changefreq = changefreq;
    }

    public double getPriority()
    {
        return priority;
    }

    public void setPriority( double priority )
    {
        this.priority = priority;
    }

    @Override
    public String toString()
    {
        return "SiteMapItem{" +
                "loc='" + loc + '\'' +
                ", lastmod=" + lastmod +
                ", changefreq=" + changefreq +
                ", priority=" + priority +
                '}';
    }

    /**
     * The value "always" should be used to describe documents that change each time they are accessed.
     * The value "never" should be used to describe archived URLs.
     */
    public enum ChangeFrequency
    {
        ALWAYS,
        HOURLY,
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY,
        NEVER
    }
}
