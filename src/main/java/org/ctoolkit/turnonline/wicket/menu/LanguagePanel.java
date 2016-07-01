package org.ctoolkit.turnonline.wicket.menu;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.ctoolkit.turnonline.wicket.markup.html.page.Skeleton;
import org.ctoolkit.turnonline.wicket.model.I18NResourceModel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * The component renders a language panel
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class LanguagePanel
        extends Panel
{
    private static final long serialVersionUID = 1L;

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
        StringBuilder url = new StringBuilder();
        try
        {
            URL u = new URL( ( ( ServletWebRequest ) getRequest() ).getContainerRequest().getRequestURL().toString() );
            url.append( u.getPath() );
        }
        catch ( MalformedURLException e )
        {
            url.append( "/" );
        }

        url.append( "?" );
        url.append( Skeleton.PARAM_LANG );
        url.append( "=" );
        url.append( item.getLanguage() );

        return url.toString();
    }
}