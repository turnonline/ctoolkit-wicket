package org.ctoolkit.wicket.standard.cache;

import com.google.common.io.ByteStreams;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.time.Time;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The ResourceStream intended to be cached by App Engine (standard) memcache.
 * It does not keep any instance related metadata (full directory path as for example
 * {@link org.apache.wicket.core.util.resource.UrlResourceStream} does) that might cause
 * a runtime exception after application redeployment.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class MemcacheResourceStream
        extends AbstractResourceStream
{
    private static final long serialVersionUID = -1317280662500181698L;

    private byte[] data;

    private String contentType;

    private int length;

    /**
     * Constructor.
     *
     * @param resourceStream the resource stream to be wrapped.
     */
    MemcacheResourceStream( IResourceStream resourceStream )
    {
        setLocale( resourceStream.getLocale() );
        setStyle( resourceStream.getStyle() );
        setVariation( resourceStream.getVariation() );

        this.contentType = resourceStream.getContentType();
        try
        {
            this.data = ByteStreams.toByteArray( resourceStream.getInputStream() );
            length = data.length;
        }
        catch ( Exception e )
        {
            throw new RuntimeException( "ResourceStream loading error: " + resourceStream, e );
        }
    }

    @Override
    public String getContentType()
    {
        return this.contentType;
    }

    @Override
    public Bytes length()
    {
        return Bytes.bytes( length );
    }

    @Override
    public InputStream getInputStream() throws ResourceStreamNotFoundException
    {
        return new ByteArrayInputStream( data );
    }

    @Override
    public void close() throws IOException
    {
    }

    @Override
    public Time lastModifiedTime()
    {
        return null;
    }
}
