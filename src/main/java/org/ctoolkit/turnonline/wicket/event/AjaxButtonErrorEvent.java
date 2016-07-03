package org.ctoolkit.turnonline.wicket.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * The AJAX fallback button error event.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AjaxButtonErrorEvent
        extends AjaxRequestTargetEvent
{
    private static final long serialVersionUID = 4682829871660484266L;

    public AjaxButtonErrorEvent()
    {
    }

    public AjaxButtonErrorEvent( AjaxRequestTarget target )
    {
        super( target );
    }
}
