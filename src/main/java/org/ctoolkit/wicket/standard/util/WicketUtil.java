package org.ctoolkit.wicket.standard.util;

import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.cycle.RequestCycle;

import javax.servlet.http.HttpServletRequest;

/**
 * Wicket related utility methods.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class WicketUtil
{
    public static HttpServletRequest getHttpServletRequest()
    {
        return ( ( ServletWebRequest ) RequestCycle.get().getRequest() ).getContainerRequest();
    }

    /**
     * Returns the server url meaning protocol + request server name excluding context
     * for example 'https://adp.turnonline.biz'.
     *
     * @return the server url
     */
    public static String getServerUrl()
    {
        HttpServletRequest request = getHttpServletRequest();

        String protocol = request.isSecure() ? "https://" : "http://";
        String hostname = request.getServerName();
        int port = request.getServerPort();

        StringBuilder url = new StringBuilder( 128 );

        url.append( protocol );
        url.append( hostname );

        if ( ( port != 80 ) && ( port != 443 ) )
        {
            url.append( ":" );
            url.append( port );
        }

        return url.toString();
    }
}
