package org.ctoolkit.wicket.turnonline.model;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ResourceReference;
import org.ctoolkit.wicket.turnonline.menu.MenuSchema;
import org.ctoolkit.wicket.turnonline.menu.SearchResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The {@link IModel} factory consumed by wicket components.
 * Implementation must be thread-safe.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public interface IModelFactory
{
    /**
     * Returns the concrete class type for shopping cart page.
     *
     * @return the concrete page class type
     */
    Class<? extends Page> getShoppingCartPage();

    /**
     * Returns the concrete class type for login page.
     *
     * @return the concrete page class type
     */
    Class<? extends Page> getLoginPage();

    /**
     * Returns the concrete class type for sign-up page.
     *
     * @return the concrete page class type
     */
    Class<? extends Page> getSignUpPage();

    /**
     * Returns the concrete class type for my account page.
     *
     * @return the concrete page class type
     */
    Class<? extends Page> getMyAccountPage();

    /**
     * Returns the concrete class type for advanced account settings page.
     *
     * @return the concrete page class type
     */
    Class<? extends Page> getAccountSettingsPage();

    /**
     * Returns the terms URL model or <tt>null</tt> if not specified.
     *
     * @param pageModel the model as an optional source
     * @return the terms URL model or <tt>null</tt>
     */
    IModel<String> getTermsUrlModel( @Nullable IModel<?> pageModel );

    /**
     * Returns the logo URL model or <tt>null</tt> if not specified.
     *
     * @param pageModel the model as an optional source
     * @return the logo URL model or <tt>null</tt>
     */
    @Deprecated
    IModel<String> getLogoUrlModel( @Nullable IModel<?> pageModel );

    /**
     * Returns the model representation whether an user is logged in or not.
     * Cannot return <tt>null</tt>.
     *
     * @return the logged in model
     */
    IModel<Boolean> isLoggedInModel();

    /**
     * Returns the model representation of current number of items in the shopping cart.
     * Returns <tt>null</tt> if not specified.
     *
     * @return the current number of items in the shopping cart model
     */
    @Deprecated
    IModel<Long> getCartItemsCountModel();

    /**
     * Returns the roles of currently logged in user. The null value means no roles.
     *
     * @return the roles of currently logged in user
     */
    Roles getRoles();

    /**
     * The authenticated user profile with following expected properties:
     * <ul>
     * <li>email</li>
     * <li>name</li>
     * <li>picture</li>
     * </ul>
     *
     * @return the logged in user model, {@code null} if there is no authenticated user
     */
    IModel getLoggedInAccountModel();

    /**
     * Returns the model representation whether page header's search box will be rendered or not.
     * If returns <tt>null</tt> the search box will be rendered.
     *
     * @return the search box visibility model
     */
    IModel<Boolean> getSearchBoxVisibilityModel();

    /**
     * Returns application default stylesheet resource reference array or empty array.
     * It may take in to account current server name and {@link Component#getVariation()}.
     *
     * @param pageModel the model as an optional source
     * @param type      the wicket runtime configuration type
     * @return the resolved stylesheet resource reference array or empty array
     */
    ResourceReference[] getStylesheetReference( @Nullable IModel<?> pageModel, @Nonnull RuntimeConfigurationType type );

    /**
     * Returns the array of application wide behaviors or {@code null}.
     *
     * @param type      the wicket runtime configuration type
     * @param pageModel the optional model as an optional source
     * @return the array of behaviors or {@code null}
     */
    Behavior[] getBehaviors( @Nonnull RuntimeConfigurationType type, @Nullable IModel<?> pageModel );

    /**
     * Returns the Google Analytics Tracking ID or <tt>null</tt> to not render.
     *
     * @param pageModel the model as an optional source
     * @return the Google Analytics Tracking ID or <tt>null</tt>
     */
    String getGoogleAnalyticsTrackingId( @Nullable IModel<?> pageModel );

    /**
     * Returns the menu schema instance that is based on current roles.
     * In case no roles are specified returns default menus schema.
     * Cannot be <tt>null</tt>.
     *
     * @param context the page that menu is intended for
     * @param roles   the set of roles to be evaluated
     * @return the menu schema instance that is based on current roles
     */
    MenuSchema provideMenuSchema( @Nonnull Page context, @Nullable Roles roles );

    /**
     * Returns either a new or existing shopping mall model.
     *
     * @param request HTTP request
     * @return the shopping mall model
     */
    IModel<?> getShoppingMallModel( @Nonnull HttpServletRequest request );

    /**
     * Returns the country from which the request originated, as an ISO 3166-1 alpha-2 country code.
     * Determined from the client's IP address.
     *
     * @param request HTTP request
     * @return the country code
     * @see <a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2">ISO 3166-1 alpha-2</a>
     */
    String getCountryOriginRequest( @Nonnull HttpServletRequest request );

    /**
     * Returns the account role string representation or <tt>null</tt> if none.
     *
     * @return the account role
     */
    String getAccountRole();

    /**
     * Returns the name of the city from which the request originated. For example, a request from the city
     * of Mountain View might have the header value 'mountain view'.
     *
     * @param request HTTP request
     * @return the name of the city
     */
    String getCityOriginRequest( @Nonnull HttpServletRequest request );

    /**
     * Search response list provider
     *
     * @param input query input
     * @return list of {@link SearchResponse}
     */
    List<SearchResponse> getSearchResponseList( String input );
}
