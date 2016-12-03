package org.ctoolkit.wicket.standard.markup.autofill;

/**
 * The autofill {@link Autofill#ORGANIZATION} singleton behavior implementation.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AutofillOrganization
        extends AutofillBehavior
{
    private static final long serialVersionUID = -7278221563548907328L;

    private static AutofillOrganization INSTANCE = new AutofillOrganization();

    private AutofillOrganization()
    {
        super( Autofill.ORGANIZATION );
    }

    public static AutofillOrganization get()
    {
        return INSTANCE;
    }
}
