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
    public static final ListModel<NavigationItem> EMPTY = new ListModel<>( new ArrayList<NavigationItem>() );

    private static final long serialVersionUID = -3699216716579329067L;

    protected IModel<Roles> roles;

    public DefaultSchema()
    {
    }

    public DefaultSchema( IModel<Roles> roles )
    {
        this.roles = roles;
    }

    /**
     * Returns the default list of swipe menu items.
     *
     * @return the empty list
     */
    public IModel<List<NavigationItem>> getSwipeMenuItems()
    {
        return EMPTY;
    }

    /**
     * Returns the default list of menu items.
     *
     * @return the empty list
     */
    public IModel<List<NavigationItem>> getMenuItems()
    {
        return EMPTY;
    }
}
