package org.ctoolkit.wicket.turnonline.identity.page;

import org.apache.wicket.model.IModel;
import org.ctoolkit.wicket.standard.model.I18NResourceModel;
import org.ctoolkit.wicket.turnonline.identity.IdentityOptions;
import org.ctoolkit.wicket.turnonline.identity.behavior.FirebaseAuthInit;
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
    private IdentityOptions identityOptions;

    public IdentityLogin()
    {
        add( new FirebaseAuthInit( identityOptions ) );
    }

    @Override
    public IModel<String> getPageTitle()
    {
        return new I18NResourceModel( "title.login" );
    }
}
