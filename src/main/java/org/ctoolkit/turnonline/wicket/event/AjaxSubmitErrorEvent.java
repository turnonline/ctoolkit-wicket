package org.ctoolkit.turnonline.wicket.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * The AJAX submit error event.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AjaxSubmitErrorEvent
        extends AjaxRequestTargetEvent
{
    private static final long serialVersionUID = 4682829871660484266L;

    public AjaxSubmitErrorEvent()
    {
    }

    public AjaxSubmitErrorEvent( AjaxRequestTarget target )
    {
        super( target );
    }
}
