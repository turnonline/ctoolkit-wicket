package org.ctoolkit.turnonline.wicket.identity.page;

import org.apache.wicket.model.IModel;
import org.ctoolkit.turnonline.wicket.identity.IdentityOptions;
import org.ctoolkit.turnonline.wicket.identity.markup.IdentityLoginPanel;
import org.ctoolkit.turnonline.wicket.markup.html.page.DecoratedPage;
import org.ctoolkit.turnonline.wicket.model.I18NResourceModel;

import javax.inject.Inject;

/**
 * The sign up page that implements Google Identity Toolkit's widget to sign up.
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
