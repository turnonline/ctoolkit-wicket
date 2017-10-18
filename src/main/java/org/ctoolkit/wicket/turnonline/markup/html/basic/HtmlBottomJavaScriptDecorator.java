package org.ctoolkit.wicket.turnonline.markup.html.basic;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.filter.JavaScriptFilteredIntoFooterHeaderResponse;
import org.apache.wicket.markup.html.IHeaderResponseDecorator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Decorator to manage JS contribution.
 * JavaScript {@link JavaScriptReferenceHeaderItem} should be placed at the bottom of the page
 * for a better performance and faster loading times.
 * http://wicketinaction.com/2012/07/wicket-6-resource-management
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class HtmlBottomJavaScriptDecorator
        implements IHeaderResponseDecorator
{
    private final String filterName;

    /**
     * Constructor.
     *
     * @param footerName the name of the bucket that you will use for your footer container
     */
    public HtmlBottomJavaScriptDecorator( String footerName )
    {
        this.filterName = checkNotNull( footerName );
    }

    @Override
    public IHeaderResponse decorate( IHeaderResponse response )
    {
        return new JavaScriptFilteredIntoFooterHeaderResponse( response, filterName );
    }
}
