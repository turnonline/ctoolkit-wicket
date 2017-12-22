package org.ctoolkit.wicket.turnonline.menu;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.ctoolkit.wicket.turnonline.util.CookiesUtil;

import java.util.List;

/**
 * The component renders a list of static {@link NavItem}. It also adds a css class to navigation item if that
 * item class is equal to parent page class.
 * The expected HTML might have for examplea following wicket components.
 * {@link NavItem} wicket IDs ('link-nav-item' and 'link-nav-item-label') must match:
 * <p>
 * <b>For example</b>
 * <pre>
 * {@code
 * <li wicket:id="navigation">
 *      <a wicket:id="link-nav-item">
 *          <span wicket:id="link-nav-item-label"></span>
 *      </a>
 * </li>
 * }
 * </pre>
 * This will render repeated list of {@code <li>..<li/>} HTML tags.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class Navigation
        extends ListView<NavItem>
{
    private static final long serialVersionUID = 7770930801430140492L;

    /**
     * Constructs a new navigation bar.
     *
     * @param id the wicket component id
     */
    public Navigation( String id )
    {
        super( id );
    }

    /**
     * Constructs a new navigation bar.
     *
     * @param id    the wicket component id
     * @param model the model of the static list of navigation items
     */
    public Navigation( String id, IModel<? extends List<? extends NavItem>> model )
    {
        super( id, model );
    }

    /**
     * Constructs a new navigation bar.
     *
     * @param id   the wicket component id
     * @param list the static list of navigation items
     */
    public Navigation( String id, List<? extends NavItem> list )
    {
        super( id, list );
    }

    private boolean actualPageClassSelected( Class<? extends Page> actualPage )
    {
        List<? extends NavItem> items = getList();
        for ( NavItem item : items )
        {
            if ( item.getPageClass() != null && item.getPageClass().equals( actualPage ) )
            {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void populateItem( ListItem<NavItem> populated )
    {
        IModel<NavItem> model = populated.getModel();
        NavItem item = model.getObject();

        AbstractLink link;
        if ( item.getUrlModel() != null )
        {
            link = new ExternalLink( "link-nav-item", item.getUrlModel() );
        }
        else if ( item.getPageClass() != null )
        {
            Class<? extends Page> currentPage = this.getPage().getPageClass();
            link = new BookmarkablePageLink<Page>( "link-nav-item", item.getPageClass() );

            if ( item.getPageClass().equals( currentPage ) )
            {
                link.add( AttributeModifier.replace( "class", "selected" ) );
            }
            else
            {
                // use case when actual page class is set by client (javascript)
                // set only if actual page class was not selected by wicket
                if ( !actualPageClassSelected( currentPage ) )
                {
                    if ( item.getPageClass().equals( CookiesUtil.getPageClassFromCookie() ) )
                    {
                        link.add( AttributeModifier.replace( "class", "selected" ) );
                    }
                }
            }
        }
        else
        {
            String message = "You must specify either link url or page class to be able render navigation item link: ";
            throw new IllegalArgumentException( message + item );
        }


        Label linkLabel = new Label( "link-nav-item-label", item.getLabelModel() );
        linkLabel.setEscapeModelStrings( false );
        linkLabel.setRenderBodyOnly( true );
        link.add( linkLabel );

        if ( item.getCssClass() != null )
        {
            link.add( AttributeModifier.append( "class", item.getCssClass() ) );
        }

        populated.add( link );
    }
}
