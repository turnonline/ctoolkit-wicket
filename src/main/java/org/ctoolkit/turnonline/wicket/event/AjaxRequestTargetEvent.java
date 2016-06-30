package org.ctoolkit.turnonline.wicket.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

import java.io.Serializable;

/**
 * The wrapper class with {@link AjaxRequestTarget} payload.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public abstract class AjaxRequestTargetEvent
        implements Serializable
{
    private static final long serialVersionUID = 1L;

    private AjaxRequestTarget target;

    protected AjaxRequestTargetEvent()
    {
    }

    public AjaxRequestTargetEvent( AjaxRequestTarget target )
    {
        this.target = target;
    }

    public AjaxRequestTarget getTarget()
    {
        return target;
    }
}
