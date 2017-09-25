package org.ctoolkit.wicket.standard.markup.html.basic.ajax;

/**
 * The AJAX indicator appender that renders customized span class (float fight) and indicator URL.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AjaxFloatRightIndicatorAppender
        extends AjaxStandardIndicatorAppender
{
    private static final long serialVersionUID = 1L;

    /**
     * Construct.
     */
    public AjaxFloatRightIndicatorAppender()
    {
        super( "wicket-ajax-indicator" );
    }
}
