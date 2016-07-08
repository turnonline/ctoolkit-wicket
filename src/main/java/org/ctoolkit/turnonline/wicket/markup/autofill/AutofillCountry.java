package org.ctoolkit.turnonline.wicket.markup.autofill;

/**
 * The autofill {@link Autofill#COUNTRY} singleton behavior implementation.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AutofillCountry
        extends AutofillBehavior
{
    private static final long serialVersionUID = 8781041403643540807L;

    private static AutofillCountry INSTANCE = new AutofillCountry();

    private AutofillCountry()
    {
        super( Autofill.COUNTRY );
    }

    public static AutofillCountry get()
    {
        return INSTANCE;
    }
}
