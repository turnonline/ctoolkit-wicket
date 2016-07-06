package org.ctoolkit.turnonline.wicket.markup.html.form.ajax;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.IModel;
import org.ctoolkit.turnonline.wicket.markup.html.basic.ajax.AjaxStandardIndicatorAppender;

/**
 * The AJAX check box that renders progress info while AJAX request is in progress.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class IndicatingAjaxCheckBox
        extends CheckBox
        implements IAjaxIndicatorAware
{
    private static final long serialVersionUID = 3895538827706619199L;

    private final AjaxStandardIndicatorAppender indicatorAppender = new AjaxStandardIndicatorAppender();

    /**
     * Constructor.
     *
     * @param id the component id
     */
    public IndicatingAjaxCheckBox( String id )
    {
        this( id, null );
    }

    /**
     * Constructor.
     *
     * @param id    the component id
     * @param model the component model
     */
    public IndicatingAjaxCheckBox( String id, IModel<Boolean> model )
    {
        super( id, model );
        add( indicatorAppender );
    }

    @Override
    public String getAjaxIndicatorMarkupId()
    {
        return indicatorAppender.getMarkupId();
    }
}
