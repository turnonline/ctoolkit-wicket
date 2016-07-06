package org.ctoolkit.turnonline.wicket.markup.autofill;

import org.apache.wicket.AttributeModifier;

/**
 * The browser support for autocomplete behavior implementation.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AutofillBehavior
        extends AttributeModifier
{
    public static final String AUTOFILL = "autocomplete";

    private static final long serialVersionUID = 4724667967280946822L;

    public AutofillBehavior( Autofill type )
    {
        super( AUTOFILL, type.getAttribute() );
    }

    /**
     * TODO look at the autocomplete link below and implement it as much as make a sense.
     *
     * @see <a href="https://html.spec.whatwg.org/multipage/forms.html#autofill">Autofill</a>
     */
    public enum Autofill
    {
        EMAIL( "email" ),
        GIVEN_NAME( "given-name" ),
        FULL_NAME( "name" ),
        NICKNAME( "nickname" ),
        SURNAME( "family-name" ),
        ORGANIZATION( "organization" ),
        ADDRESS( "street-address" ),
        ADDRESS_LINE1( "address-line1" ),
        ADDRESS_LINE2( "address-line2" ),
        CITY( "address-level2" ),
        POSTAL_CODE( "postal-code" ),
        PHONE_NUMBER( "tel" ),
        COUNTRY( "country" );

        private String attribute;

        Autofill( String attribute )
        {
            this.attribute = attribute;
        }

        public String getAttribute()
        {
            return attribute;
        }
    }
}
