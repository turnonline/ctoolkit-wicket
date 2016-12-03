package org.ctoolkit.wicket.turnonline.validator;

import org.apache.wicket.validation.validator.PatternValidator;

import java.util.regex.Pattern;

/**
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class TaxIdValidator
        extends PatternValidator
{
    private static final long serialVersionUID = 1L;

    /**
     * singleton instance
     */
    private static final TaxIdValidator INSTANCE = new TaxIdValidator();

    /**
     * Protected constructor to force use of static singleton accessor. Override this constructor to
     * implement resourceKey(Component).
     */
    protected TaxIdValidator()
    {
        super( "^\\d{8,10}$", Pattern.CASE_INSENSITIVE );
    }

    /**
     * Retrieves the singleton instance of <code>ZipValidator</code>.
     *
     * @return the singleton instance of <code>ZipValidator</code>
     */
    public static TaxIdValidator get()
    {
        return INSTANCE;
    }
}
