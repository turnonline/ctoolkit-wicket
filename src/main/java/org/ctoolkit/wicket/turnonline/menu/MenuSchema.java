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
     * @return the list of {@link NavigationItem}
     */
    IModel<List<NavigationItem>> getSwipeMenuItems();

    /**
     * Returns the list of menu items.
     *
     * @return the list of {@link NavigationItem}
     */
    IModel<List<NavigationItem>> getMenuItems();
}
