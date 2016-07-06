package org.ctoolkit.turnonline.wicket.markup.autofill;

import org.apache.wicket.AttributeModifier;

/**
 * The autocomplete off singleton implementation.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AutofillOff
        extends AttributeModifier
{
    private static final long serialVersionUID = -2374551670941613390L;

    private static AutofillOff INSTANCE = new AutofillOff();

    private AutofillOff()
    {
        super( AutofillBehavior.AUTOFILL, "off" );
    }

    public static AutofillOff get()
    {
        return INSTANCE;
    }
}
