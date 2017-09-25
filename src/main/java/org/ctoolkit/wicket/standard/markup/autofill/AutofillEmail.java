package org.ctoolkit.wicket.standard.markup.autofill;

/**
 * The autofill {@link Autofill#EMAIL} singleton behavior implementation.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AutofillEmail
        extends AutofillBehavior
{
    private static final long serialVersionUID = -2300291176369197965L;

    private static AutofillEmail INSTANCE = new AutofillEmail();

    private AutofillEmail()
    {
        super( Autofill.EMAIL );
    }

    public static AutofillEmail get()
    {
        return INSTANCE;
    }
}
