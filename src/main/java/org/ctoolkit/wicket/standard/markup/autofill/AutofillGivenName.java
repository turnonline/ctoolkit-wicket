package org.ctoolkit.wicket.standard.markup.autofill;

/**
 * The autofill {@link Autofill#GIVEN_NAME} singleton behavior implementation.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AutofillGivenName
        extends AutofillBehavior
{
    private static final long serialVersionUID = 6963501782850761047L;

    private static AutofillGivenName INSTANCE = new AutofillGivenName();

    private AutofillGivenName()
    {
        super( Autofill.GIVEN_NAME );
    }

    public static AutofillGivenName get()
    {
        return INSTANCE;
    }
}
