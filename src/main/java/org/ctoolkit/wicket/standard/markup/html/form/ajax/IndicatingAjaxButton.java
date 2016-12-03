package org.ctoolkit.wicket.standard.markup.html.form.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxChannel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.ctoolkit.wicket.standard.event.AjaxSubmitErrorEvent;
import org.ctoolkit.wicket.standard.event.AjaxSubmitEvent;
import org.ctoolkit.wicket.standard.markup.html.basic.ajax.AjaxStandardIndicatorAppender;

/**
 * The AJAX fallback submit button that renders progress info while AJAX request is in progress.
 * On form error entire form is being added to target + non null feedback panel
 * {@link #FEEDBACK_MARKUP_ID}.
 * Fires following events:
 * <ul>
 * <li>onSubmit: {@link AjaxSubmitEvent}</li>
 * <li>onError: {@link AjaxSubmitErrorEvent}</li>
 * </ul>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class IndicatingAjaxButton
        extends AjaxFallbackButton
        implements IAjaxIndicatorAware
{
    public static final String FEEDBACK_MARKUP_ID = "feedback";

    private static final long serialVersionUID = 1L;

    private final AjaxStandardIndicatorAppender indicatorAppender = new AjaxStandardIndicatorAppender();

    public IndicatingAjaxButton( String id, Form<?> form )
    {
        super( id, form );
        add( indicatorAppender );
    }

    public IndicatingAjaxButton( String id, IModel<String> model, Form<?> form )
    {
        super( id, model, form );
        add( indicatorAppender );
    }

    @Override
    protected void onSubmit( AjaxRequestTarget target, Form<?> form )
    {
        send( getPage(), Broadcast.BREADTH, new AjaxSubmitEvent( target ) );

        Component feedback = getPage().get( FEEDBACK_MARKUP_ID );
        //noinspection ConstantConditions
        if ( feedback != null && target != null )
        {
            target.add( feedback );
        }
    }

    @Override
    protected void onError( AjaxRequestTarget target, Form<?> form )
    {
        send( getPage(), Broadcast.BREADTH, new AjaxSubmitErrorEvent( target ) );

        Component feedback = getPage().get( FEEDBACK_MARKUP_ID );
        //noinspection ConstantConditions
        if ( target != null )
        {
            if ( feedback != null )
            {
                target.add( feedback );
            }

            target.add( form );
        }
    }

    @Override
    protected void updateAjaxAttributes( AjaxRequestAttributes attributes )
    {
        //the ajax channel used by the link to drop requests if one is already in progress, it's called an "active" channel.
        attributes.setChannel( new AjaxChannel( "blocking", AjaxChannel.Type.ACTIVE ) );
    }

    @Override
    public String getAjaxIndicatorMarkupId()
    {
        return indicatorAppender.getMarkupId();
    }
}
