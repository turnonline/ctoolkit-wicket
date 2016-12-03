package org.ctoolkit.wicket.standard.markup.html.form.ajax;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.ctoolkit.wicket.standard.markup.html.basic.ajax.AjaxStandardIndicatorAppender;
import org.ctoolkit.wicket.standard.model.MapValuesModel;

import java.util.List;
import java.util.Map;

/**
 * The AJAX select (drop down) that renders progress info while AJAX request is in progress.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class IndicatingAjaxDropDown<T>
        extends DropDownChoice<T>
        implements IAjaxIndicatorAware
{
    private static final long serialVersionUID = 1677660119976442829L;

    private final AjaxStandardIndicatorAppender indicatorAppender = new AjaxStandardIndicatorAppender();

    /**
     * Constructor.
     *
     * @param id the component id
     */
    public IndicatingAjaxDropDown( String id )
    {
        super( id );
        add( indicatorAppender );
    }

    /**
     * Constructor.
     *
     * @param id      the component id
     * @param choices the drop down choices
     */
    public IndicatingAjaxDropDown( String id, List<? extends T> choices )
    {
        super( id, choices );
        add( indicatorAppender );
    }

    /**
     * Constructor.
     *
     * @param id       the component id
     * @param choices  the drop down choices
     * @param renderer the rendering engine
     */
    public IndicatingAjaxDropDown( String id, List<? extends T> choices, IChoiceRenderer<? super T> renderer )
    {
        super( id, choices, renderer );
        add( indicatorAppender );
    }

    /**
     * Constructor.
     *
     * @param id      the component id
     * @param model   the component model as a source of the default value (preselected)
     * @param choices the drop down choices
     */
    public IndicatingAjaxDropDown( String id, IModel<T> model, List<? extends T> choices )
    {
        super( id, model, choices );
        add( indicatorAppender );
    }

    /**
     * Constructor.
     *
     * @param id       the component id
     * @param model    the component model as a source of the default value (preselected)
     * @param choices  the drop down choices
     * @param renderer the rendering engine
     */
    public IndicatingAjaxDropDown( String id,
                                   IModel<T> model,
                                   List<? extends T> choices,
                                   IChoiceRenderer<? super T> renderer )
    {
        super( id, model, choices, renderer );
        add( indicatorAppender );
    }

    /**
     * Constructor.
     *
     * @param id      the component id
     * @param choices the drop down model of choices
     */
    public IndicatingAjaxDropDown( String id, IModel<? extends List<? extends T>> choices )
    {
        super( id, choices );
        add( indicatorAppender );
    }

    /**
     * Constructor.
     *
     * @param id      the component id
     * @param model   the component model as a source of the default value (preselected)
     * @param choices the drop down model of choices
     */
    public IndicatingAjaxDropDown( String id, IModel<T> model, IModel<? extends List<? extends T>> choices )
    {
        super( id, model, choices );
        add( indicatorAppender );
    }

    /**
     * Constructor.
     *
     * @param id       the component id
     * @param choices  the drop down model of choices
     * @param renderer the rendering engine
     */
    public IndicatingAjaxDropDown( String id,
                                   IModel<? extends List<? extends T>> choices,
                                   IChoiceRenderer<? super T> renderer )
    {
        super( id, choices, renderer );
        add( indicatorAppender );
    }

    /**
     * Constructor.
     *
     * @param id       the component id
     * @param model    the component model as a source of the default value (preselected)
     * @param choices  the drop down model of choices
     * @param renderer the rendering engine
     */
    public IndicatingAjaxDropDown( String id,
                                   IModel<T> model,
                                   IModel<? extends List<? extends T>> choices,
                                   IChoiceRenderer<? super T> renderer )
    {
        super( id, model, choices, renderer );
        add( indicatorAppender );
    }

    /**
     * Constructor.
     *
     * @param id       the component id
     * @param model    the component model as a source of the default value (preselected)
     * @param renderer the rendering engine
     * @param choices  the drop down map model of choices
     */
    public IndicatingAjaxDropDown( String id,
                                   IModel<T> model,
                                   IChoiceRenderer<? super T> renderer,
                                   IModel<Map<String, T>> choices )
    {
        super( id, model, new MapValuesModel<>( choices ), renderer );
        add( indicatorAppender );
    }

    @Override
    public String getAjaxIndicatorMarkupId()
    {
        return indicatorAppender.getMarkupId();
    }
}
