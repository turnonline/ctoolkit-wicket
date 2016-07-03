package org.ctoolkit.turnonline.wicket.markup.autocomplete;

import org.apache.wicket.AttributeModifier;

/**
 * The autocomplete off singleton implementation.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AutocompleteOff
        extends AttributeModifier
{
    private static final long serialVersionUID = -2374551670941613390L;

    private static AutocompleteOff INSTANCE = new AutocompleteOff();

    private AutocompleteOff()
    {
        super( AutocompleteBehavior.AUTOCOMPLETE, "off" );
    }

    public static AutocompleteOff get()
    {
        return INSTANCE;
    }
}
