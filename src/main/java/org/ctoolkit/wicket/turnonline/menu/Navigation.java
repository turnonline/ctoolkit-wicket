package org.ctoolkit.wicket.turnonline.menu;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.ctoolkit.wicket.turnonline.util.CookiesUtil;

import java.util.List;

/**
 * The component renders a list of static {@link NavigationItem}. It also add a css class to navigation item if that
 * item class is equal to parent page class.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class Navigation
        extends Panel
{
    private static final long serialVersionUID = -1063460846957044329L;

    private RepeatingView repeater;

    private IModel<List<NavigationItem>> items;

    /**
     * Construct a new navigation panel
     *
     * @param id    the wicket component id
     * @param items the static list of navigation items
     */
    public Navigation( String id, IModel<List<NavigationItem>> items )
    {
        this( id, items, null );
    }

    /**
     * Construct a new navigation panel
     *
     * @param id     the wicket component id
     * @param items  the static list of navigation items
     * @param filter the filter to filter out navigation items
     */
    public Navigation( String id, IModel<List<NavigationItem>> items, IModel<NavigationItem.Filter> filter )
    {
        super( id, filter );
        setOutputMarkupId( false );
        setRenderBodyOnly( true );

        this.items = items;
        repeater = new RepeatingView( "nav" );
        add( repeater );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    protected void onBeforeRender()
    {
        Class<? extends Page> actualPage = this.getPage().getPageClass();

        NavigationItem.Filter filter = ( NavigationItem.Filter ) getDefaultModelObject();
        boolean showPrivatePage = false;

        if ( filter != null )
        {
            showPrivatePage = filter.showPrivatePage();
        }

        repeater.removeAll();

        for ( NavigationItem item : items.getObject() )
        {
            // filter page links to be shown
            if ( filter != null )
            {
                if ( showPrivatePage )
                {
                    if ( !item.isPrivatePage() )
                    {
                        continue;
                    }
                }
                else
                {
                    if ( item.isPrivatePage() )
                    {
                        continue;
                    }
                }
            }

            WebMarkupContainer navItem = new WebMarkupContainer( repeater.newChildId() );
            repeater.add( navItem );

            AbstractLink link;
            if ( item.getUrlModel() != null )
            {
                link = new ExternalLink( "link-nav-item", item.getUrlModel() );
            }
            else if ( item.getPageClass() != null )
            {
                link = new BookmarkablePageLink<Page>( "link-nav-item", item.getPageClass() );
                if ( item.getPageClass().equals( actualPage ) )
                {
                    link.add( AttributeModifier.replace( "class", "selected" ) );
                }
                else
                {
                    // use case when actual page class is set by client (javascript)
                    // set only if actual page class was not selected by wicket
                    if ( !actualPageClassSelected( filter, showPrivatePage, actualPage ) )
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
                throw new IllegalArgumentException( "You must specify either link url or page class to be able render navigation item link: " + item );
            }

            navItem.add( link );

            Label linkLabel = new Label( "link-nav-item-label", item.getLabelModel() );
            linkLabel.setEscapeModelStrings( false );
            link.add( linkLabel );

            if ( item.getCssClass() != null )
            {
                link.add( AttributeModifier.append( "class", item.getCssClass() ) );
            }
        }

        super.onBeforeRender();
    }

    private boolean actualPageClassSelected( NavigationItem.Filter filter, boolean showPrivatePage, Class<? extends Page> actualPage )
    {
        for ( NavigationItem item : items.getObject() )
        {
            // filter page links to be shown
            if ( filter != null )
            {
                if ( showPrivatePage )
                {
                    if ( !item.isPrivatePage() )
                    {
                        continue;
                    }
                }
                else
                {
                    if ( item.isPrivatePage() )
                    {
                        continue;
                    }
                }
            }

            if ( item.getPageClass() != null && item.getPageClass().equals( actualPage ) )
            {
                return true;
            }
        }

        return false;
    }
}
