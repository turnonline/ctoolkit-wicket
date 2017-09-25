package org.ctoolkit.wicket.standard.behavior;

import org.apache.wicket.behavior.AttributeAppender;

/**
 * The attribute 'class' appender with 'readonly' value.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class ReadOnlyAttributeAppender
        extends AttributeAppender
{
    private static final long serialVersionUID = 1L;

    /**
     * Appender constructor.
     */
    private ReadOnlyAttributeAppender()
    {
        super( "class", "readonly" );
        setSeparator( " " );
    }

    /**
     * Creates a new instance and returns a 'class' appender with 'readonly' value.
     *
     * @return the 'readonly' appender
     */
    public static AttributeAppender create()
    {
        return new ReadOnlyAttributeAppender();
    }
}
