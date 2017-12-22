package org.ctoolkit.wicket.turnonline.menu;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.ctoolkit.wicket.standard.model.I18NResourceModel;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The target page navigation item configuration to be rendered by {@link Navigation}.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class NavItem
        implements Serializable
{
    private static final long serialVersionUID = 4429514372684701048L;

    private Class<? extends WebPage> pageClass;

    private IModel<String> urlModel;

    private IModel<?> labelModel;

    private String cssClass;

    /**
     * Constructs a new navigation item. Represents a public page.
     *
     * @param url   the URL where to navigate
     * @param label the label for given URL
     */
    public NavItem( IModel<String> url, IModel<?> label )
    {
        this.urlModel = url;
        this.labelModel = label;
    }

    /**
     * Constructs a new navigation item.
     *
     * @param url      the URL where to navigate
     * @param label    the label for given URL
     * @param cssClass css class
     */
    public NavItem( IModel<String> url, IModel<?> label, String cssClass )
    {
        this.urlModel = url;
        this.labelModel = label;
        this.cssClass = cssClass;
    }

    /**
     * Constructs a new navigation item.
     *
     * @param pageClass the page class where to navigate
     * @param label     the label for given URL
     */
    public NavItem( Class<? extends WebPage> pageClass, IModel<?> label )
    {
        this.pageClass = pageClass;
        this.labelModel = label;
    }

    /**
     * Constructs a new navigation item.
     *
     * @param pageClass the page class where to navigate
     * @param label     the label for given URL
     * @param cssClass  css class
     */
    public NavItem( Class<? extends WebPage> pageClass, IModel<?> label, String cssClass )
    {
        this.pageClass = pageClass;
        this.labelModel = label;
        this.cssClass = cssClass;
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

    @Override
    public String toString()
    {
        return "NavItem{" +
                "pageClass=" + pageClass +
                ", urlModel=" + urlModel +
                ", labelModel=" + labelModel +
                ", cssClass='" + cssClass + '\'' +
                '}';
    }
}
