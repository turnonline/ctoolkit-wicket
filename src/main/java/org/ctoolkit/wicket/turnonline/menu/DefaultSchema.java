package org.ctoolkit.wicket.turnonline.menu;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The default schema.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class DefaultSchema
        implements MenuSchema
{
    public static final ListModel<NavItem> EMPTY = new ListModel<>( new ArrayList<NavItem>() );

    private static final long serialVersionUID = -3699216716579329067L;

    private Roles roles;

    public DefaultSchema()
    {
    }

    public DefaultSchema( Roles roles )
    {
        this.roles = roles;
    }

    /**
     * Returns the default list of swipe menu items.
     *
     * @return the empty list
     */
    @Override
    public IModel<List<NavItem>> getSwipeMenuItems()
    {
        return EMPTY;
    }

    /**
     * Returns the default list of menu items.
     *
     * @return the empty list
     */
    @Override
    public IModel<List<NavItem>> getMenuItems()
    {
        return EMPTY;
    }

    /**
     * Returns the default list of footer menu items.
     *
     * @return the empty list
     */
    @Override
    public IModel<List<NavItem>> getFooterMenuItems()
    {
        return EMPTY;
    }

    protected Roles roles()
    {
        return roles;
    }
}
