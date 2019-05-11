package org.ctoolkit.wicket.standard.markup.html.basic;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.IWrappedHeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.head.ResourceAggregator;
import org.apache.wicket.markup.head.filter.AbstractHeaderResponseFilter;
import org.apache.wicket.markup.head.filter.FilteringHeaderResponse;
import org.apache.wicket.markup.head.filter.OppositeHeaderResponseFilter;
import org.apache.wicket.markup.html.IHeaderResponseDecorator;
import org.apache.wicket.util.lang.Args;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

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
    private final List<FilteringHeaderResponse.IHeaderResponseFilter> filters;

    /**
     * Constructor.
     *
     * @param filterName the name of the footer container
     */
    public HtmlBottomJavaScriptDecorator( @Nonnull String filterName )
    {
        Args.notEmpty( filterName, "filterName" );

        filters = new ArrayList<>();

        final AbstractHeaderResponseFilter jsAcceptingFilter = new AbstractHeaderResponseFilter( filterName )
        {
            public boolean accepts( HeaderItem item )
            {
                HeaderItem wrapped;
                if ( item instanceof IWrappedHeaderItem )
                {
                    wrapped = ( ( IWrappedHeaderItem ) item ).getWrapped();
                }
                else
                {
                    wrapped = item;
                }

                return wrapped instanceof JavaScriptHeaderItem ||
                        wrapped instanceof OnDomReadyHeaderItem ||
                        wrapped instanceof OnLoadHeaderItem;
            }
        };

        filters.add( jsAcceptingFilter );
        filters.add( new OppositeHeaderResponseFilter( "headBucket", jsAcceptingFilter ) );
    }

    /**
     * decorates the original {@link IHeaderResponse}
     *
     * @param response original {@link IHeaderResponse}
     * @return decorated {@link IHeaderResponse}
     */
    public IHeaderResponse decorate( final IHeaderResponse response )
    {
        return new ResourceAggregator( new FilteringHeaderResponse( response, "headBucket", filters ) );
    }
}
