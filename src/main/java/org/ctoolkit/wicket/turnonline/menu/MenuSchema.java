package org.ctoolkit.wicket.turnonline.menu;

import org.apache.wicket.model.IModel;

import java.io.Serializable;
import java.util.List;

/**
 * The interface to define menu schema.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public interface MenuSchema
        extends Serializable
{
    /**
     * Returns the list of swipe menu items.
     *
     * @return the list of {@link NavItem}
     */
    IModel<List<NavItem>> getSwipeMenuItems();

    /**
     * Returns the list of menu items.
     *
     * @return the list of {@link NavItem}
     */
    IModel<List<NavItem>> getMenuItems();

    /**
     * Returns the list of footer menu items.
     *
     * @return the list of {@link NavItem}
     */
    IModel<List<NavItem>> getFooterMenuItems();
}
