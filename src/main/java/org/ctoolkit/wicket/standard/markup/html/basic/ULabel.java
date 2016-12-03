package org.ctoolkit.wicket.standard.markup.html.basic;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

/**
 * ULabel is label which does not escape values.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org>Jozef Pohorelec</a>"
 */
public class ULabel
        extends Label
{
    private static final long serialVersionUID = 1L;

    public ULabel( String id )
    {
        super( id );
        setEscapeModelStrings( false );
    }

    public ULabel( String id, String label )
    {
        super( id, label );
        setEscapeModelStrings( false );
    }

    public ULabel( String id, IModel<?> model )
    {
        super( id, model );
        setEscapeModelStrings( false );
    }
}
