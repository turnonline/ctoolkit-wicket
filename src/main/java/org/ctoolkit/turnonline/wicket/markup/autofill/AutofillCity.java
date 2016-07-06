package org.ctoolkit.turnonline.wicket.markup.autofill;

/**
 * The autofill {@link Autofill#CITY} singleton behavior implementation.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AutofillCity
        extends AutofillBehavior
{
    private static final long serialVersionUID = 6408461241196281360L;

    private static AutofillCity INSTANCE = new AutofillCity();

    private AutofillCity()
    {
        super( Autofill.CITY );
    }

    public static AutofillCity get()
    {
        return INSTANCE;
    }
}
