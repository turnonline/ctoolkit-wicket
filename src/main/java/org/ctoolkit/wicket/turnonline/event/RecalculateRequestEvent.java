package org.ctoolkit.wicket.turnonline.event;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.ctoolkit.wicket.standard.event.AjaxRequestTargetEvent;

/**
 * The event if recalculate request has been fired.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class RecalculateRequestEvent
        extends AjaxRequestTargetEvent
{
    private static final long serialVersionUID = 1L;

    private boolean refreshChildren;

    public RecalculateRequestEvent()
    {
    }

    public RecalculateRequestEvent( AjaxRequestTarget target )
    {
        super( target );
    }

    public boolean isRefreshChildren()
    {
        return refreshChildren;
    }

    public void setRefreshChildren( boolean refreshChildren )
    {
        this.refreshChildren = refreshChildren;
    }
}
