package org.ctoolkit.turnonline.wicket.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * The AJAX fallback button submit event.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AjaxButtonSubmitEvent
        extends AjaxRequestTargetEvent
{
    private static final long serialVersionUID = 9161251022831947161L;

    public AjaxButtonSubmitEvent()
    {
    }

    public AjaxButtonSubmitEvent( AjaxRequestTarget target )
    {
        super( target );
    }
}
