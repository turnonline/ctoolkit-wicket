package org.ctoolkit.turnonline.wicket.markup.autofill;

/**
 * The autofill {@link Autofill#POSTAL_CODE} singleton behavior implementation.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AutofillPostalCode
        extends AutofillBehavior
{
    private static final long serialVersionUID = -2884548701518233814L;

    private static AutofillPostalCode INSTANCE = new AutofillPostalCode();

    private AutofillPostalCode()
    {
        super( Autofill.POSTAL_CODE );
    }

    public static AutofillPostalCode get()
    {
        return INSTANCE;
    }
}
