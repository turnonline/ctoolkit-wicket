package org.ctoolkit.turnonline.wicket.menu;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.ctoolkit.turnonline.wicket.markup.html.basic.ULabel;
import org.ctoolkit.turnonline.wicket.model.I18NResourceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Footer of a page. It renders footer links based on list of navigation items.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class Footer
        extends Panel
{
    private static final long serialVersionUID = 3162740870500163825L;

    public Footer( String id, IModel<List<NavigationItem>> items )
    {
        this( id, items, new ArrayList<Locale>() );
    }

    public Footer( String id, IModel<List<NavigationItem>> items, List<Locale> languages )
    {
        this( id, items, languages, new I18NResourceModel( "title.copyright" ), new I18NResourceModel( "title.trademarks" ) );
    }

    /**
     * Construct new footer
     *
     * @param id wicket id
     */
    public Footer( String id, IModel<List<NavigationItem>> items, List<Locale> languages, IModel<?> copyright, IModel<?> trademarks )
    {
        super( id );
        setOutputMarkupId( false );
        setRenderBodyOnly( true );

        RepeatingView rv = new RepeatingView( "footer" );
        add( rv );

        int i = 0;
        for ( NavigationItem item : items.getObject() )
        {
            WebMarkupContainer navItem = new WebMarkupContainer( rv.newChildId() );
            rv.add( navItem );

            AbstractLink link;
            if ( item.getUrlModel() != null )
            {
                link = new ExternalLink( "link-footer-item", item.getUrlModel() );
            }
            else if ( item.getPageClass() != null )
            {
                link = new BookmarkablePageLink<Page>( "link-footer-item", item.getPageClass() );
            }
            else
            {
                String msg = "You must specify either link url or page class to be able render footer item link: "
                        + item;

                throw new IllegalArgumentException( msg );
            }
            navItem.add( link );

            Label linkLabel = new Label( "link-footer-item-label", item.getLabelModel() );
            link.add( linkLabel );

            i++;
        }

        add( new LanguagePanel( "languages", languages ) );

        add( new ULabel( "copyright", copyright ) );
        add( new ULabel( "trademarks", trademarks ) );
    }
}
