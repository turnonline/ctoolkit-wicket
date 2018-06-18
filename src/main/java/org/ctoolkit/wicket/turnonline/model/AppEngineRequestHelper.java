package org.ctoolkit.wicket.turnonline.model;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Request helper that is specific to Google App Engine.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class AppEngineRequestHelper
{
    public static String getCountryOriginRequest( @Nonnull HttpServletRequest request )
    {
        checkNotNull( request, "Http Servlet Request cannot be null!" );
        return request.getHeader( "X-AppEngine-Country" );
    }

    public static String getCityOriginRequest( @Nonnull HttpServletRequest request )
    {
        checkNotNull( request, "Http Servlet Request cannot be null!" );
        return request.getHeader( "X-AppEngine-City" );
    }
}
