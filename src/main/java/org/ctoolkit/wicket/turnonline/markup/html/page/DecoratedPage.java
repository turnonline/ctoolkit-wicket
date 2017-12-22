package org.ctoolkit.wicket.turnonline.markup.html.page;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.https.RequireHttps;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.ctoolkit.wicket.standard.markup.html.basic.HtmlBottomJavaScriptDecorator;
import org.ctoolkit.wicket.standard.markup.html.basic.ImageComponent;
import org.ctoolkit.wicket.standard.markup.html.basic.ULabel;
import org.ctoolkit.wicket.standard.model.ExternalLinkModel;
import org.ctoolkit.wicket.standard.model.I18NResourceModel;
import org.ctoolkit.wicket.turnonline.AppEngineApplication;
import org.ctoolkit.wicket.turnonline.menu.MenuSchema;
import org.ctoolkit.wicket.turnonline.menu.Navigation;
import org.ctoolkit.wicket.turnonline.model.IModelFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Decorated page that includes feedback panel, title, header and footer.
 * The main navigation bar is being placed at top of the page.
 * <p>
 * The page employs {@link HeaderResponseContainer} in cooperation with {@link HtmlBottomJavaScriptDecorator}
 * in order to put all JS references and script at HTML bottom for better performance.
 * <p>
 * <b>Expected wicket components to be part of the HTML</b>
 * <pre>
 * {@code
 * wicket:id="logo-link"
 *  wicket:id="link-login"
 *  wicket:id="link-signUp"
 *
 *  wicket:id="wrapper-myAccount"
 *    wicket:id="picture"
 *    wicket:id="label-myAccount"
 *    wicket:id="link-myAccount"
 *    wicket:id="link-settings"
 *    wicket:id="link-logout"
 *
 *  wicket:id="navigation"
 *    wicket:id="link-nav-item"
 *      wicket:id="link-nav-item-label"
 *
 *  wicket:id="feedback"
 *
 *  wicket:id="html-bottom-container"
 * }
 * </pre>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@RequireHttps
public abstract class DecoratedPage<T>
        extends Skeleton<T>
{
    private static final long serialVersionUID = 1L;

    public DecoratedPage()
    {
        super();
    }

    public DecoratedPage( IModel<T> model )
    {
        super( model );
    }

    public DecoratedPage( PageParameters parameters )
    {
        super( parameters );
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        IModelFactory factory = modelFactory();

        final IModel<Boolean> loggedInModel;
        loggedInModel = checkNotNull( factory.isLoggedInModel(), "LoggedInModel cannot be null!" );
        Class loginPage = checkNotNull( factory.getLoginPage(), "The login page class is mandatory!" );
        Class signUpPage = checkNotNull( factory.getSignUpPage(), "The sign up page class is mandatory!" );
        Class myAccountPage = checkNotNull( factory.getMyAccountPage(), "The my account page class is mandatory!" );
        Class settingsPage = checkNotNull( factory.getAccountSettingsPage(), "The account settings page class is" +
                " mandatory!" );

        final Roles roles = factory.getRoles();
        final String accountRole = factory.getAccountRole();
        IModel<String> myAccountLabelModel = factory.getMyAccountLabelModel();
        IModel loggedInAccountModel = factory.getLoggedInAccountModel();

        Application application = Application.get();
        RuntimeConfigurationType type = application.getConfigurationType();

        // add application wide behaviors
        Behavior[] behaviors = factory.getBehaviors( type, null );
        if ( behaviors != null )
        {
            for ( Behavior next : behaviors )
            {
                add( next );
            }
        }

        // set page title
        add( new ULabel( "title", getPageTitle() ) );

        add( new BookmarkablePageLink( "logo-link", application.getHomePage() ) );

        FeedbackPanel feedbackPanel = newFeedbackPanel();
        feedbackPanel.setEscapeModelStrings( false );
        feedbackPanel.setOutputMarkupId( true );
        add( feedbackPanel );

        // login link
        Link login = new BookmarkablePageLink( "link-login", loginPage )
        {
            private static final long serialVersionUID = -6308037382669267883L;

            @Override
            public boolean isVisible()
            {
                Boolean modelObject = loggedInModel.getObject();
                return !( modelObject == null ? Boolean.FALSE : modelObject );
            }
        };
        add( login );

        // sign up link
        Link signUp = new BookmarkablePageLink( "link-signUp", signUpPage )
        {
            private static final long serialVersionUID = -6003066496511986670L;

            @Override
            public boolean isVisible()
            {
                Boolean modelObject = loggedInModel.getObject();
                return !( modelObject == null ? Boolean.FALSE : modelObject );
            }
        };
        add( signUp );

        // my account dropdown
        WebMarkupContainer myAccountContainer = new WebMarkupContainer( "wrapper-myAccount" )
        {
            private static final long serialVersionUID = 8956879706072490572L;

            @Override
            public boolean isVisible()
            {
                Boolean modelObject = loggedInModel.getObject();
                return modelObject == null ? Boolean.FALSE : modelObject;
            }
        };
        myAccountContainer.add( new ULabel( "label-myAccount", PropertyModel.of( loggedInAccountModel, "email" ) ) );
        add( myAccountContainer );

        ImageComponent imageComponent = new ImageComponent( "picture", PropertyModel.of( loggedInAccountModel, "picture" ) );
        myAccountContainer.add( imageComponent );

        // my account link
        Link myAccount = new BookmarkablePageLink( "link-myAccount", myAccountPage );
        myAccountContainer.add( myAccount );

        // logout link
        ExternalLink logout = new ExternalLink( "link-logout", new ExternalLinkModel( AppEngineApplication.LOGOUT ) );
        myAccountContainer.add( logout );

        String script = "firebase.auth().signOut().then(function(){window.location.href='"
                + AppEngineApplication.LOGOUT + "'});";
        logout.add( new AttributeAppender( "onclick", script, ";" ) );

        // settings link
        Link settings = new BookmarkablePageLink( "link-settings", settingsPage )
        {
            private static final long serialVersionUID = 3764066608838121794L;

            @Override
            public boolean isVisible()
            {
                return roles != null && roles.hasRole( accountRole );
            }
        };
        myAccountContainer.add( settings );

        Page page = getPage();
        MenuSchema schema = factory.provideMenuSchema( page, roles );
        add( new Navigation( "navigation", schema.getMenuItems() ) );

        add( new HeaderResponseContainer( "html-bottom-container", HTML_BOTTOM_FILTER_NAME ) );
    }

    /**
     * Get page title model. Default title.changeit to be changed.
     *
     * @return the page title model
     */
    public IModel<?> getPageTitle()
    {
        return new I18NResourceModel( "title.changeit" );
    }
}