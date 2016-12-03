package org.ctoolkit.wicket.standard.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * The AJAX submit event.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AjaxSubmitEvent
        extends AjaxRequestTargetEvent
{
    private static final long serialVersionUID = 9161251022831947161L;

    public AjaxSubmitEvent()
    {
    }

    public AjaxSubmitEvent( AjaxRequestTarget target )
    {
        super( target );
    }
}
