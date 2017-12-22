package org.ctoolkit.wicket.standard.markup.autofill;

/**
 * The autofill {@link AutofillBehavior.Autofill#ADDRESS} singleton behavior implementation.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AutofillAddress
        extends AutofillBehavior
{
    private static final long serialVersionUID = -326564017439415175L;

    private static AutofillAddress INSTANCE = new AutofillAddress();

    private AutofillAddress()
    {
        super( AutofillBehavior.Autofill.ADDRESS );
    }

    public static AutofillAddress get()
    {
        return INSTANCE;
    }
}
