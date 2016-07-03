package org.ctoolkit.turnonline.wicket.markup.autocomplete;

import org.apache.wicket.AttributeModifier;

/**
 * The browser support for autocomplete behavior implementation.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AutocompleteBehavior
        extends AttributeModifier
{
    public static final String AUTOCOMPLETE = "autocomplete";

    private static final long serialVersionUID = 4724667967280946822L;

    public AutocompleteBehavior( Autocomplete type )
    {
        super( AUTOCOMPLETE, type.getAttribute() );
    }

    /**
     * TODO look at the autocomplete link below and implement it as much as make a sense.
     *
     * @see <a href="http://www.whatwg.org/specs/web-apps/current-work/multipage/association-of-controls-and-forms.html#attr-fe-autocomplete">Autocomplete</a>
     */
    public enum Autocomplete
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
        CITY( "locality" ),
        POSTAL_CODE( "postal-code" ),
        PHONE_NUMBER( "tel" ),
        COUNTRY( "country" );

        private String attribute;

        Autocomplete( String attribute )
        {
            this.attribute = attribute;
        }

        public String getAttribute()
        {
            return attribute;
        }
    }
}
