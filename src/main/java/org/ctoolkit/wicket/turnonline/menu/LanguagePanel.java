package org.ctoolkit.wicket.turnonline.menu;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Url;
import org.ctoolkit.wicket.standard.model.I18NResourceModel;
import org.ctoolkit.wicket.turnonline.markup.html.page.Skeleton;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * The component renders a language panel
 * Expected i18 resource bundle, its based on the given list of locale.
 * <ul>
 * <li>language.en</li>
 * </ul>
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class LanguagePanel
        extends Panel
{
    private static final long serialVersionUID = 764336739357423363L;

    /**
     * Construct a new language panel
     *
     * @param id    wicket id
     * @param items list of supported languages
     */
    public LanguagePanel( String id, final List<Locale> items )
    {
        super( id );
        setOutputMarkupId( false );
        setRenderBodyOnly( true );

        WebMarkupContainer language = new WebMarkupContainer( "language" );
        add( language );

        IModel<List<Locale>> itemsModel = Model.ofList( items );
        ListView<Locale> listView = new ListView<Locale>( "flag-repeater", itemsModel )
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isVisible()
            {
                return !( items.size() < 2 );
            }

            @Override
            protected void populateItem( ListItem<Locale> item )
            {
                Locale locale = item.getModelObject();
                item.add( AttributeModifier.replace( "class", "flag flag-" + locale.getLanguage() ) );

                String url = createLocalizationUrl( locale );

                I18NResourceModel labelModel = new I18NResourceModel( "language." + locale.getLanguage() );
                ExternalLink flag = new ExternalLink( "flag", Model.of( url ), labelModel );
                item.add( flag );
            }
        };
        language.add( listView );
    }

    /**
     * Create url with lang parameter
     *
     * @param item language
     * @return url with lang parameter
     */
    private String createLocalizationUrl( Locale item )
    {
        Url url = getRequest().getUrl();
        List<Url.QueryParameter> params = url.getQueryParameters();

        Iterator<Url.QueryParameter> iterator = params.iterator();
        while ( iterator.hasNext() )
        {
            Url.QueryParameter next = iterator.next();
            if ( Skeleton.PARAM_LANG.equals( next.getName() ) )
            {
                iterator.remove();
            }
        }

        Url.QueryParameter langParam;
        langParam = new Url.QueryParameter( Skeleton.PARAM_LANG, item.getLanguage() );
        params.add( langParam );

        String done = url.toString();

        return url.isContextAbsolute() ? done : "/" + done;
    }
}
