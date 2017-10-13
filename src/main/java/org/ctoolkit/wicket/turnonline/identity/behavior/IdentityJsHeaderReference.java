package org.ctoolkit.wicket.turnonline.identity.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.util.string.Strings;

import javax.annotation.concurrent.ThreadSafe;
import java.text.MessageFormat;

/**
 * Contributes Google Identity Toolkit JavaScript static CDN URL reference to the page header.
 * Locale sensitive, taken from the session.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@ThreadSafe
public class IdentityJsHeaderReference
        extends Behavior
{
    private static final long serialVersionUID = 1L;

    private static IdentityJsHeaderReference INSTANCE = new IdentityJsHeaderReference();

    private IdentityJsHeaderReference()
    {
    }

    public static IdentityJsHeaderReference get()
    {
        return INSTANCE;
    }


    @Override
    public void renderHead( Component component, IHeaderResponse response )
    {
        String language = Session.get().getLocale().getLanguage();

        MessageFormat fmt = new MessageFormat( "//www.gstatic.com/authtoolkit/{0}js/gitkit.js" );
        Object[] args = new Object[1];
        String stringUrl;

        if ( Strings.isEmpty( language ) )
        {
            args[0] = "";
            stringUrl = fmt.format( args );
        }
        else
        {
            args[0] = language.toLowerCase() + "/";
            stringUrl = fmt.format( args );
        }

        // render gitkit JavaScript
        Url url = Url.parse( stringUrl );

        UrlResourceReference jsCdnReference = new UrlResourceReference( url );
        response.render( JavaScriptHeaderItem.forReference( jsCdnReference ) );
    }
}
