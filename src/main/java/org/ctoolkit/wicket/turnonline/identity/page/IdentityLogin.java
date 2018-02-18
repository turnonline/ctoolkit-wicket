package org.ctoolkit.wicket.turnonline.identity.page;

import org.apache.wicket.model.IModel;
import org.ctoolkit.wicket.standard.identity.FirebaseConfig;
import org.ctoolkit.wicket.standard.identity.behavior.FirebaseAppInit;
import org.ctoolkit.wicket.standard.model.I18NResourceModel;
import org.ctoolkit.wicket.turnonline.markup.html.page.DecoratedPage;

import javax.inject.Inject;


/**
 * The login page that implements Google Firebase App widget.
 * <p>
 * Expected i18 resource bundle:
 * <ul>
 * <li>title.login</li>
 * </ul>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class IdentityLogin<T>
        extends DecoratedPage<T>
{
    private static final long serialVersionUID = -6264709084901970501L;

    @Inject
    private FirebaseConfig firebaseConfig;

    public IdentityLogin()
    {
        add( new FirebaseAppInit( firebaseConfig, true ) );
    }

    @Override
    public IModel<String> getPageTitle()
    {
        return new I18NResourceModel( "title.login" );
    }
}
