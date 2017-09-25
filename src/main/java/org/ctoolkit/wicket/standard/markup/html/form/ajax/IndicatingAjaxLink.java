package org.ctoolkit.wicket.standard.markup.html.form.ajax;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.model.IModel;
import org.ctoolkit.wicket.standard.markup.html.basic.ajax.AjaxFloatRightIndicatorAppender;
import org.ctoolkit.wicket.standard.markup.html.basic.ajax.AjaxStandardIndicatorAppender;

/**
 * Generic AJAX Link progress indicator visible during background task execution.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class IndicatingAjaxLink<T>
        extends AjaxFallbackLink<T>
        implements IAjaxIndicatorAware
{
    private static final long serialVersionUID = 1L;

    private final AjaxIndicatorAppender indicatorAppender;

    public IndicatingAjaxLink( String id )
    {
        this( id, false );
    }

    public IndicatingAjaxLink( String id, IModel<T> model )
    {
        this( id, false, model );
    }

    public IndicatingAjaxLink( String id, boolean floatRight )
    {
        super( id );
        if ( floatRight )
        {
            indicatorAppender = new AjaxFloatRightIndicatorAppender();
        }
        else
        {
            indicatorAppender = new AjaxStandardIndicatorAppender();
        }
        add( indicatorAppender );
    }

    public IndicatingAjaxLink( String id, boolean floatRight, IModel<T> model )
    {
        super( id, model );
        if ( floatRight )
        {
            indicatorAppender = new AjaxFloatRightIndicatorAppender();
        }
        else
        {
            indicatorAppender = new AjaxStandardIndicatorAppender();
        }
        add( indicatorAppender );
    }

    @Override
    public String getAjaxIndicatorMarkupId()
    {
        return indicatorAppender.getMarkupId();
    }
}
