package org.ctoolkit.wicket.turnonline.menu;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.ctoolkit.wicket.standard.model.I18NResourceModel;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The <code>NavigationItem</code> object with payloads for {@link Navigation} renderer.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org>Jozef Pohorelec</a>"
 */
public class NavigationItem
        implements Serializable
{
    private static final long serialVersionUID = 4429514372684701048L;

    private Class<? extends WebPage> pageClass;

    private IModel<String> urlModel;

    private IModel<?> labelModel;

    private String cssClass;

    private boolean privatePage;

    /**
     * Constructs a new navigation item. Represents a public page.
     *
     * @param url   the URL where to navigate
     * @param label the label for given URL
     */
    public NavigationItem( IModel<String> url, IModel<?> label )
    {
        this.urlModel = url;
        this.labelModel = label;
        this.privatePage = false;
    }

    /**
     * Constructs a new navigation item.
     *
     * @param url      the URL where to navigate
     * @param label    the label for given URL
     * @param cssClass css class
     */
    public NavigationItem( IModel<String> url, IModel<?> label, String cssClass )
    {
        this.urlModel = url;
        this.labelModel = label;
        this.cssClass = cssClass;
    }

    /**
     * Constructs a new navigation item.
     *
     * @param url         the URL where to navigate
     * @param label       the label for given URL
     * @param privatePage true to setup navigation for private purpose
     */
    public NavigationItem( IModel<String> url, IModel<?> label, boolean privatePage )
    {
        this.urlModel = url;
        this.labelModel = label;
        this.privatePage = privatePage;
    }

    /**
     * Constructs a new navigation item.
     *
     * @param pageClass   the page class where to navigate
     * @param label       the label for given URL
     * @param privatePage true to setup navigation for private purpose
     */
    public NavigationItem( Class<? extends WebPage> pageClass, IModel<?> label, boolean privatePage )
    {
        this.pageClass = pageClass;
        this.labelModel = label;
        this.privatePage = privatePage;
    }

    /**
     * Constructs a new navigation item.
     *
     * @param pageClass the page class where to navigate
     * @param label     the label for given URL
     * @param cssClass  css class
     */
    public NavigationItem( Class<? extends WebPage> pageClass, IModel<?> label, String cssClass )
    {
        this.pageClass = pageClass;
        this.labelModel = label;
        this.cssClass = cssClass;
    }

    /**
     * Constructs a new navigation item.
     *
     * @param pageClass   the page class where to navigate
     * @param label       the label for given URL
     * @param cssClass    css class
     * @param privatePage true to setup navigation for private purpose
     */
    public NavigationItem( Class<? extends WebPage> pageClass, IModel<?> label, String cssClass, boolean privatePage )
    {
        this.pageClass = pageClass;
        this.labelModel = label;
        this.cssClass = cssClass;
        this.privatePage = privatePage;
    }

    /**
     * Creates I18N resource model for given key.
     *
     * @param key property key
     * @return the I18N resource model
     */
    public static I18NResourceModel i18n( String key )
    {
        checkNotNull( key );
        return new I18NResourceModel( key );
    }

    public Class<? extends WebPage> getPageClass()
    {
        return pageClass;
    }

    public IModel<String> getUrlModel()
    {
        return urlModel;
    }

    public IModel<?> getLabelModel()
    {
        return labelModel;
    }

    public String getCssClass()
    {
        return cssClass;
    }

    public boolean isPrivatePage()
    {
        return privatePage;
    }

    @Override
    public String toString()
    {
        return "NavigationItem{" +
                "pageClass=" + pageClass +
                ", urlModel=" + urlModel +
                ", labelModel=" + labelModel +
                ", cssClass='" + cssClass + '\'' +
                ", privatePage=" + privatePage +
                '}';
    }

    /**
     * The filter to filter out provided static list of navigation items.
     */
    public abstract static class Filter
            implements Serializable
    {
        /**
         * Override to manage visibility of either public or private pages.
         *
         * @return true to show public pages only. False to show private pages only.
         */
        public abstract boolean showPrivatePage();
    }
}
