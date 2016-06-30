package org.ctoolkit.turnonline.wicket.identity.page;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.StringValue;
import org.ctoolkit.turnonline.wicket.identity.IdentityOptions;
import org.ctoolkit.turnonline.wicket.identity.markup.IdentityLoginPanel;
import org.ctoolkit.turnonline.wicket.markup.html.page.DecoratedPage;
import org.ctoolkit.turnonline.wicket.model.I18NResourceModel;

import javax.inject.Inject;


/**
 * The login page that implements Google Identity Toolkit's widget.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class IdentityLogin<T>
        extends DecoratedPage<T>
{
    private static final long serialVersionUID = 1L;

    private LoginHeaderTitleResourceModel titleModel = new LoginHeaderTitleResourceModel();

    @Inject
    private IdentityOptions identityOptions;

    public IdentityLogin()
    {
        add( new IdentityLoginPanel( "login-panel", identityOptions ) );
    }

    @Override
    public IModel<String> getPageTitle()
    {
        return new I18NResourceModel( "title.login" );
    }

    @Override
    protected IModel<?> getPageH1Header()
    {
        return titleModel;
    }

    private static class LoginHeaderTitleResourceModel
            extends Model<String>
    {
        private static final long serialVersionUID = 1L;

        private final I18NResourceModel loginModel;

        private final I18NResourceModel resetModel;

        LoginHeaderTitleResourceModel()
        {
            this.loginModel = new I18NResourceModel( "text.login" );
            this.resetModel = new I18NResourceModel( "label.resetPassword" );
        }

        @Override
        public String getObject()
        {
            Request request = RequestCycle.get().getRequest();
            IRequestParameters parameters = request.getQueryParameters();
            StringValue resetPassword = parameters.getParameterValue( "mode" );

            if ( resetPassword.toString( "none" ).equals( "resetPassword" ) )
            {
                return resetModel.getObject();
            }
            else
            {
                return loginModel.getObject();
            }
        }
    }
}
