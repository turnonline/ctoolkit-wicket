package org.ctoolkit.wicket.standard.markup.html.form.ajax;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.IModel;

/**
 * Component used to connect instances of Radio components into a group
 * that renders progress info while AJAX request is in progress.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class IndicatingAjaxRadioGroup<T>
        extends RadioGroup<T>
        implements IAjaxIndicatorAware
{
    private static final long serialVersionUID = 1L;

    private final String markupId;

    public IndicatingAjaxRadioGroup( String id, String markupId )
    {
        this( id, null, markupId );
    }

    public IndicatingAjaxRadioGroup( String id, IModel<T> model, String markupId )
    {
        super( id, model );
        this.markupId = markupId;
    }

    @Override
    public String getAjaxIndicatorMarkupId()
    {
        return markupId;
    }
}
