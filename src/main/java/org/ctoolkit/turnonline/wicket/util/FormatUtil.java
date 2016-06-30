package org.ctoolkit.turnonline.wicket.util;

import java.text.Normalizer;

/**
 * // TODO: move to more appropriate library
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class FormatUtil
{
    public static String highlight( String textToHighlight, String highlightedText )
    {
        int index = normalize( textToHighlight ).indexOf( normalize( highlightedText ) );
        if ( index != -1 )
        {
            StringBuilder sb = new StringBuilder();
            sb.append( textToHighlight.substring( 0, index ) );
            sb.append( "<b>" );
            sb.append( textToHighlight.substring( index, index + highlightedText.length() ) );
            sb.append( "</b>" );
            sb.append( textToHighlight.substring( index + highlightedText.length() ) );

            return sb.toString();
        }

        return textToHighlight;
    }

    public static String normalize( String value )
    {
        if ( value == null )
        {
            return null;
        }

        return Normalizer.normalize( value.toLowerCase(), Normalizer.Form.NFD ).replaceAll( "[^\\p{ASCII}]", "" );
    }
}