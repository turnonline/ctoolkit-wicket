package org.ctoolkit.wicket.turnonline.menu;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.ctoolkit.wicket.standard.behavior.FixedElementBehavior;
import org.ctoolkit.wicket.standard.event.AjaxRequestTargetEvent;
import org.ctoolkit.wicket.standard.markup.html.basic.ULabel;
import org.ctoolkit.wicket.standard.model.ExternalLinkModel;
import org.ctoolkit.wicket.standard.model.I18NResourceModel;
import org.ctoolkit.wicket.turnonline.AppEngineApplication;
import org.ctoolkit.wicket.turnonline.event.RecalculateRequestEvent;
import org.ctoolkit.wicket.turnonline.model.IModelFactory;

import javax.annotation.Nullable;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The header that renders menu items based on the contextual page and role.
 * <p>
 * Expected i18 resource bundle:
 * <ul>
 * <li>title.login</li>
 * <li>title.sign-up</li>
 * <li>title.shopping-cart</li>
 * </ul>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class Header
        extends Panel
{
    private static final long serialVersionUID = -6101446612087943943L;

    private final IModel<Boolean> loggedInModel;

    private Label updateByAjaxItemCount;

    /**
     * The header constructor.
     *
     * @param id        the non-null id of this component
     * @param page      the parent page as an owner of the header component
     * @param factory   the model factory service
     * @param pageModel the parent page default model
     */
    public Header( String id, Page page, final IModelFactory factory, @Nullable IModel<?> pageModel )
    {
        super( id );
        setRenderBodyOnly( true );
        checkNotNull( page, "Page cannot be null!" );
        checkNotNull( factory, "IModelFactory cannot be null!" );

        this.loggedInModel = checkNotNull( factory.isLoggedInModel(), "LoggedInModel cannot be null!" );

        final IModel<Boolean> cartVisibilityModel = factory.getShoppingCartVisibilityModel();
        final IModel<Boolean> searchVisibilityModel = factory.getSearchBoxVisibilityModel();
        final Roles roles = factory.getRoles();
        final String accountRole = factory.getAccountRole();

        checkNotNull( cartVisibilityModel, "IModelFactory#getShoppingCartVisibilityModel() cannot return null!" );

        final IModel<String> logoUrl = factory.getLogoUrlModel( pageModel );
        final IModel<Long> itemsCountModel = factory.getCartItemsCountModel();
        IModel<String> myAccountLabelModel = factory.getMyAccountLabelModel();

        Class cartPage = checkNotNull( factory.getShoppingCartPage(), "The ShoppingCart class is mandatory!" );
        Class loginPage = checkNotNull( factory.getLoginPage(), "The login page class is mandatory!" );
        Class signUpPage = checkNotNull( factory.getSignUpPage(), "The sign up page class is mandatory!" );
        Class myAccountPage = checkNotNull( factory.getMyAccountPage(), "The my account page class is mandatory!" );
        Class settingsPage = checkNotNull( factory.getAccountSettingsPage(), "The account settings page class is" +
                " mandatory!" );

        MenuSchema schema = factory.provideMenuSchema( page, roles );

        // logo
        BookmarkablePageLink logoLink = new BookmarkablePageLink( "logo-link", AppEngineApplication.get().getHomePage() )
        {
            private static final long serialVersionUID = 8483837553974307293L;

            @Override
            public boolean isVisible()
            {
                return logoUrl != null;
            }

            @Override
            protected void onComponentTag( ComponentTag tag )
            {
                super.onComponentTag( tag );
                tag.put( "style", "background-image: url('" + logoUrl.getObject() + "');" );
            }
        };
        add( logoLink );

        // navigation
        add( new Navigation( "navigation", schema.getMenuItems() ) );
        add( new SwipeMenu( "swipe-menu", schema.getSwipeMenuItems() ) );

        // top menu
        WebMarkupContainer topMenu = new WebMarkupContainer( "top-menu" );
        topMenu.add( new FixedElementBehavior() );
        add( topMenu );

        // toggle menu button
        topMenu.add( new ToggleMenuButton( "toggle-menu-btn" ) );

        // shopping cart button
        updateByAjaxItemCount = new Label( "cart-items", itemsCountModel );
        updateByAjaxItemCount.setOutputMarkupId( true );

        Link shoppingCartButton = new BookmarkablePageLink( "shopping-cart-button", cartPage )
        {
            private static final long serialVersionUID = -145642660154420823L;

            @Override
            public boolean isVisible()
            {
                return cartVisibilityModel.getObject();
            }
        };

        shoppingCartButton.add( new AttributeModifier( "class", new Model<String>()
        {
            private static final long serialVersionUID = 2701477344465727149L;

            @Override
            public String getObject()
            {
                String css = "shopping-cart-button";
                if ( itemsCountModel != null )
                {
                    Long itemsCount = itemsCountModel.getObject();
                    if ( itemsCount != null && itemsCount > 0 )
                    {
                        css += " shopping-cart-button-not-empty";
                    }
                }

                return css;
            }
        } ) );
        topMenu.add( shoppingCartButton );
        shoppingCartButton.add( updateByAjaxItemCount );

        // login link
        Link login = new BookmarkablePageLink( "link-login", loginPage )
        {
            private static final long serialVersionUID = -6308037382669267883L;

            @Override
            public boolean isVisible()
            {
                return !loggedInModel.getObject();
            }
        };
        login.add( new Label( "loginLabel", new I18NResourceModel( "title.login" ) ) );
        topMenu.add( login );

        // sign up link
        Link signUp = new BookmarkablePageLink( "link-signUp", signUpPage )
        {
            private static final long serialVersionUID = -6003066496511986670L;

            @Override
            public boolean isVisible()
            {
                return !loggedInModel.getObject();
            }
        };
        signUp.add( new Label( "signUpLabel", new I18NResourceModel( "title.sign-up" ) ) );
        topMenu.add( signUp );

        // logout link
        ExternalLink logout = new ExternalLink( "link-logout", new ExternalLinkModel( AppEngineApplication.LOGOUT ) )
        {
            private static final long serialVersionUID = -3494821802546531665L;

            @Override
            public boolean isVisible()
            {
                return loggedInModel.getObject();
            }
        };
        topMenu.add( logout );

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
        topMenu.add( settings );

        // my account link
        Link myAccount = new BookmarkablePageLink( "link-myAccount", myAccountPage )
        {
            private static final long serialVersionUID = 8956879706072490572L;

            @Override
            public boolean isVisible()
            {
                return loggedInModel.getObject();
            }
        };
        myAccount.add( new ULabel( "myAccountLabel", myAccountLabelModel ) );
        myAccount.add( new AttributeAppender( "class", new IconInClassModel(), " " ) );
        topMenu.add( myAccount );

        // search box
        WebMarkupContainer searchVisibility = new WebMarkupContainer( "searchVisibility" )
        {
            private static final long serialVersionUID = -2626117427164560433L;

            @Override
            public boolean isVisible()
            {
                Boolean visible = searchVisibilityModel == null ? Boolean.TRUE : searchVisibilityModel.getObject();
                checkNotNull( visible, "Model object of the search visibility model cannot be null!" );
                return visible;
            }
        };
        SearchBox search = new SearchBox( "search" )
        {
            private static final long serialVersionUID = -5522734332756207784L;

            @Override
            protected Iterator<SearchResponse> getChoices( String input )
            {
                return factory.getSearchResponseList( input ).iterator();
            }
        };
        searchVisibility.add( search );
        topMenu.add( searchVisibility );
    }

    @Override
    protected void onBeforeRender()
    {
        super.onBeforeRender();
    }

    @Override
    public void onEvent( IEvent<?> event )
    {
        super.onEvent( event );

        if ( event.getPayload() instanceof RecalculateRequestEvent )
        {
            AjaxRequestTargetEvent payload = ( AjaxRequestTargetEvent ) event.getPayload();
            if ( payload.getTarget() != null )
            {
                payload.getTarget().add( updateByAjaxItemCount );
            }
        }
    }

    @Override
    public void renderHead( HtmlHeaderContainer container )
    {
        String pageClassNameUpper = getPage().getClass().getSimpleName();
        String pageClassName = "page";
        // change PageClassName to page-class-name
        for ( char c : pageClassNameUpper.toCharArray() )
        {
            if ( !pageClassName.isEmpty() && Character.isUpperCase( c ) )
            {
                pageClassName += "-";
            }
            pageClassName += c;
        }

        // this will add css class to body element based on page name
        final String javascript = "$(document).ready(function() {$('body').addClass('" + pageClassName.toLowerCase() + "')});";
        container.getHeaderResponse().render( new JavaScriptContentHeaderItem( javascript, "body-class-name", null ) );

        // setup tooltip
        // TODO: tooltip disabled
        //container.getHeaderResponse().render( new OnDomReadyHeaderItem( "$('[title]').tooltip({track:true});" ) );

        super.renderHead( container );
    }

    private class IconInClassModel
            extends AbstractReadOnlyModel<String>
    {
        private static final long serialVersionUID = -1391404463462397836L;

        @Override
        public String getObject()
        {
            return loggedInModel.getObject() ? "icon-userin" : "icon-user";
        }
    }
}
