package org.ctoolkit.wicket.turnonline.identity.page;

import org.apache.wicket.model.IModel;
import org.ctoolkit.wicket.standard.model.I18NResourceModel;
import org.ctoolkit.wicket.turnonline.identity.IdentityOptions;
import org.ctoolkit.wicket.turnonline.identity.markup.IdentityLoginPanel;
import org.ctoolkit.wicket.turnonline.markup.html.page.DecoratedPage;

import javax.inject.Inject;

/**
 * The sign up page that implements Google Identity Toolkit's widget to sign up.
 * <p>
 * Expected i18 resource bundle:
 * <ul>
 * <li>title.sign-up</li>
 * </ul>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class SignUp<T>
        extends DecoratedPage<T>
{
    private static final long serialVersionUID = -3913612276352697428L;

    private I18NResourceModel titleModel = new I18NResourceModel( "title.sign-up" );

    @Inject
    private IdentityOptions identityOptions;

    public SignUp()
    {
        // login panel configured as sign up panel
        add( new IdentityLoginPanel( "login-panel", identityOptions, true ) );
    }

    @Override
    protected IModel<?> getPageH1Header()
    {
        return getPageTitle();
    }

    @Override
    public IModel<String> getPageTitle()
    {
        return titleModel;
    }
}
