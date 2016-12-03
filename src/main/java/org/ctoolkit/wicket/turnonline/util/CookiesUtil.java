package org.ctoolkit.wicket.turnonline.util;

import org.apache.wicket.Page;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;

import javax.servlet.http.Cookie;
import java.util.List;

/**
 * Cookies utility
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class CookiesUtil
{
    public static final String PAGE_CLASS = "__actualPageClass";

    /**
     * Return page class from cookie
     *
     * @return page class
     */
    @SuppressWarnings( "unchecked" )
    public static Class<? extends Page> getPageClassFromCookie()
    {
        String pageClass = getCookie( PAGE_CLASS );
        if ( pageClass != null )
        {
            try
            {
                return ( Class<? extends Page> ) Class.forName( pageClass );
            }
            catch ( ClassNotFoundException e )
            {
                // ignore
            }
        }

        return null;
    }

    /**
     * Return cookie value by name
     *
     * @param name cookie name
     * @return cookie value
     */

    public static String getCookie( String name )
    {
        String cookie = null;

        List<Cookie> cookies = ( ( WebRequest ) RequestCycle.get().getRequest() ).getCookies();
        if ( cookies != null )
        {
            for ( Cookie c : cookies )
            {
                if ( c.getName().equals( name ) )
                {
                    cookie = c.getValue();
                    break;
                }
            }
        }

        return cookie;
    }
}
