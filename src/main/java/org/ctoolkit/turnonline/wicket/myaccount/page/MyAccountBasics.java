package org.ctoolkit.turnonline.wicket.myaccount.page;

import org.apache.wicket.model.IModel;
import org.ctoolkit.turnonline.wicket.markup.html.page.DecoratedPage;
import org.ctoolkit.turnonline.wicket.model.I18NResourceModel;

/**
 * The page dedicated for basic account settings.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class MyAccountBasics<T>
        extends DecoratedPage<T>
{
    private static final long serialVersionUID = -1303189991396080065L;

    private I18NResourceModel titleModel = new I18NResourceModel( "title.my-account" );

    @Override
    protected IModel<?> getPageH1Header()
    {
        return getPageTitle();
    }

    @Override
    public IModel<String> getPageTitle()
    {
        return titleModel;
    }
}
