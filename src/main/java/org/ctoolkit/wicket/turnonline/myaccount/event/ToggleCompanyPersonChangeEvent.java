package org.ctoolkit.wicket.turnonline.myaccount.event;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.ctoolkit.wicket.standard.event.AjaxRequestTargetEvent;

/**
 * The AJAX toggle company person change event.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class ToggleCompanyPersonChangeEvent
        extends AjaxRequestTargetEvent
{
    private static final long serialVersionUID = 1L;

    public ToggleCompanyPersonChangeEvent( AjaxRequestTarget target )
    {
        super( target );
    }
}
