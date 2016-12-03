package org.ctoolkit.wicket.turnonline.menu;

import org.ctoolkit.wicket.turnonline.markup.html.basic.Icon;

import java.io.Serializable;

/**
 * Search response value object is used in conjunction with {@link SearchBox}
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public abstract class SearchResponse
        implements Serializable
{
    private static final long serialVersionUID = 8600434605339065346L;

    private String title;

    public SearchResponse( String title )
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public static class SectionSearchResponse
            extends SearchResponse
    {

        private static final long serialVersionUID = 2413173090348066481L;

        private Icon icon;

        public SectionSearchResponse( Icon icon, String title )
        {
            super( title );
            this.icon = icon;
        }

        public Icon getIcon()
        {
            return icon;
        }
    }

    public static class ItemSearchResponse
            extends SearchResponse
    {

        private static final long serialVersionUID = 4575354886524434200L;

        private String description;

        private String url;

        public ItemSearchResponse( String title, String description, String url )
        {
            super( title );
            this.description = description;
            this.url = url;
        }

        public String getDescription()
        {
            return description;
        }

        public String getUrl()
        {
            return url;
        }
    }
}
