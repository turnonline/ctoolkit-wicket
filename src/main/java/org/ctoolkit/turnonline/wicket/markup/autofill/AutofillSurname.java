package org.ctoolkit.turnonline.wicket.markup.autofill;

/**
 * The autofill {@link Autofill#SURNAME} singleton behavior implementation.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AutofillSurname
        extends AutofillBehavior
{
    private static final long serialVersionUID = 5717052338284182656L;

    private static AutofillSurname INSTANCE = new AutofillSurname();

    private AutofillSurname()
    {
        super( Autofill.SURNAME );
    }

    public static AutofillSurname get()
    {
        return INSTANCE;
    }
}
