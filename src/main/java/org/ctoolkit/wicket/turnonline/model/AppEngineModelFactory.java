package org.ctoolkit.wicket.turnonline.model;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The model factory method implementations that are specific to AppEngine.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public abstract class AppEngineModelFactory
        implements IModelFactory
{
    @Override
    public String getCountryOriginRequest( @Nonnull HttpServletRequest request )
    {
        checkNotNull( request, "Http Servlet Request cannot be null!" );
        return request.getHeader( "X-AppEngine-Country" );
    }

    @Override
    public String getCityOriginRequest( @Nonnull HttpServletRequest request )
    {
        checkNotNull( request, "Http Servlet Request cannot be null!" );
        return request.getHeader( "X-AppEngine-City" );
    }
}
