package org.ctoolkit.turnonline.wicket.markup.html.basic.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.request.Response;

/**
 * The AJAX indicator appender that renders customized span class and indicator URL by default.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AjaxStandardIndicatorAppender
        extends AjaxIndicatorAppender
{
    private static final long serialVersionUID = 3836616568180565913L;

    private final String spanClass;

    /**
     * Construct.
     */
    public AjaxStandardIndicatorAppender()
    {
        this( "progress progress-mini progress-indicator-checkbox-fix" );
    }

    /**
     * Construct.
     *
     * @param spanClass the customized indicator class name
     */
    protected AjaxStandardIndicatorAppender( String spanClass )
    {
        this.spanClass = spanClass;
    }

    @Override
    protected CharSequence getIndicatorUrl()
    {
        return null;
    }

    @Override
    public void afterRender( final Component component )
    {
        final Response r = component.getResponse();

        r.write( "<span style=\"display:none;\" class=\"" );
        r.write( getSpanClass() );
        r.write( "\" " );
        r.write( "id=\"" );
        r.write( getMarkupId() );
        r.write( "\">" );
        r.write( "</span>" );
    }

    @Override
    protected String getSpanClass()
    {
        return spanClass;
    }
}
