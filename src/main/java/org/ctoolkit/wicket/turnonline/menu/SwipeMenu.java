package org.ctoolkit.wicket.turnonline.menu;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;

/**
 * Swipe menu is located on right and can be enabled by either clicking on top left button for menu,
 * or by swiping from right edge of mobile device
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class SwipeMenu
        extends Panel
{
    private static final long serialVersionUID = -3928863090779279417L;

    private IModel<List<NavigationItem>> items;

    private RepeatingView repeater;

    public SwipeMenu( String id, IModel<List<NavigationItem>> items )
    {
        this( id, items, null );
    }

    public SwipeMenu( String id, IModel<List<NavigationItem>> items, IModel<NavigationItem.Filter> filter )
    {
        super( id, filter );

        setOutputMarkupId( true );
        setRenderBodyOnly( false );

        add( AttributeModifier.replace( "class", "swipe-menu" ) );

        this.items = items;

        final ExternalLink closeButton = new ExternalLink( "close-btn", new Model<String>() );
        closeButton.setOutputMarkupId( true );
        closeButton.add( new Behavior()
        {
            private static final long serialVersionUID = 392822691174061858L;

            @Override
            public void renderHead( Component component, IHeaderResponse response )
            {
                // close swipe menu
                response.render( OnDomReadyHeaderItem.forScript( "$('#" + closeButton.getMarkupId()
                        + "').click(function(){$('#" + getMarkupId() + "').removeClass('swipe-menu-on')})" ) );
            }
        } );
        add( closeButton );

        repeater = new RepeatingView( "swipe-menu" );
        add( repeater );

        add( new Behavior()
        {
            private static final long serialVersionUID = -6125102892966321239L;

            @Override
            public void renderHead( Component component, IHeaderResponse response )
            {
                // make menu item selected after click
                response.render( OnDomReadyHeaderItem.forScript( "$('#" + getMarkupId()
                        + " ul li a').click(function(){$('.swipe-menu ul li a.selected').removeClass('selected');$(this).addClass('selected')})" ) );
            }
        } );
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
            }
            else
            {
                link = new ExternalLink( "link-nav-item", "" );
                link.add( AttributeModifier.append( "class", "swipe-menu-item-root-element" ) );
                link.setEnabled( false );
            }

            link.setBody( item.getLabelModel() );
            navItem.add( link );

            if ( item.getCssClass() != null )
            {
                link.add( AttributeModifier.append( "class", item.getCssClass() ) );
            }
        }

        super.onBeforeRender();
    }
}
