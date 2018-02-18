package org.ctoolkit.wicket.turnonline.identity.page;

import org.apache.wicket.model.IModel;
import org.ctoolkit.wicket.standard.identity.FirebaseConfig;
import org.ctoolkit.wicket.standard.identity.behavior.FirebaseAppInit;
import org.ctoolkit.wicket.standard.model.I18NResourceModel;
import org.ctoolkit.wicket.turnonline.markup.html.page.DecoratedPage;

import javax.inject.Inject;

/**
 * The sign up page that implements Firebase App widget to sign up.
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
    private FirebaseConfig firebaseConfig;

    public SignUp()
    {
        // login panel configured as sign up panel
        add( new FirebaseAppInit( firebaseConfig, true ) );
    }

    @Override
    public IModel<String> getPageTitle()
    {
        return titleModel;
    }
}
