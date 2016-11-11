package org.ctoolkit.turnonline.wicket.menu;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Url;
import org.ctoolkit.turnonline.wicket.markup.html.page.Skeleton;
import org.ctoolkit.turnonline.wicket.model.I18NResourceModel;

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
    public LanguagePanel( String id, List<Locale> items )
    {
        super( id );
        setOutputMarkupId( false );
        setRenderBodyOnly( true );

        WebMarkupContainer language = new WebMarkupContainer( "language" );
        add( language );

        RepeatingView rv = new RepeatingView( "flag-repeater" );
        language.add( rv );

        if ( items.size() < 2 )
        {
            rv.setVisible( false );
        }

        for ( final Locale item : items )
        {
            WebMarkupContainer wrapper = new WebMarkupContainer( rv.newChildId() );
            wrapper.add( AttributeModifier.replace( "class", "flag flag-" + item.getLanguage() ) );
            rv.add( wrapper );

            String url = createLocalizationUrl( item );

            I18NResourceModel labelModel = new I18NResourceModel( "language." + item.getLanguage() );
            ExternalLink flag = new ExternalLink( "flag", Model.of( url ), labelModel );
            wrapper.add( flag );
        }
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

        return url.toString();
    }
}
