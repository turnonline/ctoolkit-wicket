package org.ctoolkit.wicket.standard.cache;

import com.google.common.base.Strings;
import net.sf.jsr107cache.Cache;
import org.apache.wicket.core.util.resource.locator.ResourceStreamLocator;
import org.apache.wicket.util.file.IResourceFinder;
import org.apache.wicket.util.resource.IResourceStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * External resource locator that manages resource stream retrieval (HTML markups etc.) from the App Engine (standard)
 * memcache that is shared between instances. With this locator a newly started App Engine instance will experience
 * a better Wicket cold start up time as Wicket markups and another resources does not have to be loaded again
 * from the disk that is very slow comparing to other possibilities.
 * <p>
 * This locator has a local cache as additional cache to the memcache represented by java {@link Map}.
 * This map has the best performance but it's only local to every instances. Once local cache does not have
 * requested resource, algorithm will fallback to the App Engine memcache retrieval that is slower then local {@link Map}
 * but still have a much better performance (approx. 1-3 milliseconds) then a disk operation.
 * If memcache does not have requested resource the standard local disk resource retrieval will be executed.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class MemcacheResourceLocator
        extends ResourceStreamLocator
{
    private static final Logger logger = LoggerFactory.getLogger( MemcacheResourceLocator.class );

    private final Cache memcache;

    private final Map<String, IResourceStream> localCache;

    private final Map<String, String> pathMapper;

    /**
     * Constructor.
     *
     * @param finders  the default wicket resource finders
     * @param memcache the App Engine cache shared between instances
     */
    public MemcacheResourceLocator( @Nonnull List<IResourceFinder> finders, @Nonnull Cache memcache )
    {
        this( finders, memcache, null );
    }

    /**
     * Constructor.
     *
     * @param finders  the default wicket resource finders
     * @param memcache the App Engine cache shared between instances
     * @param mapper   the map of: overriding a path with a new path value
     */
    public MemcacheResourceLocator( @Nonnull List<IResourceFinder> finders,
                                    @Nonnull Cache memcache,
                                    @Nullable Map<String, String> mapper )
    {
        super( checkNotNull( finders ) );
        this.memcache = checkNotNull( memcache );
        this.localCache = new ConcurrentHashMap<>();

        if ( mapper == null )
        {
            this.pathMapper = null;
        }
        else
        {
            this.pathMapper = new ConcurrentHashMap<>( mapper );
        }
    }

    @Override
    public IResourceStream locate( Class<?> clazz,
                                   String path,
                                   final String style,
                                   final String variation,
                                   final Locale locale,
                                   String extension,
                                   boolean strict )
    {
        String cacheKey = createCacheKey( path, extension, locale );
        IResourceStream resourceStream = localCache.get( cacheKey );
        if ( resourceStream != null )
        {
            return resourceStream;
        }

        try
        {
            resourceStream = ( IResourceStream ) memcache.get( cacheKey );
            logger.info( "ResourceStream taken from memcache for key: '" + cacheKey + "'" );

            if ( resourceStream != null && !localCache.containsKey( cacheKey ) )
            {
                localCache.put( cacheKey, resourceStream );
            }
        }
        catch ( Exception e )
        {
            resourceStream = null;
            logger.warn( "Retrieval from the memcache has failed for key: '" + cacheKey + "'", e );
        }

        if ( resourceStream != null )
        {
            return resourceStream;
        }

        if ( this.pathMapper != null && this.pathMapper.containsKey( path ) )
        {
            String newPath = this.pathMapper.get( path );
            resourceStream = super.locate( clazz, newPath, null, null, locale, extension, strict );
        }

        if ( resourceStream != null )
        {
            resourceStream = new MemcacheResourceStream( resourceStream );
            memcache.put( cacheKey, resourceStream );
            localCache.put( cacheKey, resourceStream );

            logger.info( "ResourceStream has been put to the memcache with key: '" + cacheKey + "'" );
        }
        else
        {
            // if not found, try to load requested resource from the disk
            resourceStream = super.locate( clazz, path, null, null, locale, extension, strict );

            if ( resourceStream != null )
            {
                resourceStream = new MemcacheResourceStream( resourceStream );
                memcache.put( cacheKey, resourceStream );
                localCache.put( cacheKey, resourceStream );

                logger.info( "ResourceStream has been put to the memcache with key: '" + cacheKey + "'" );
            }
        }

        return resourceStream;
    }

    /**
     * Returns single cache key string as a composition from the input params.
     *
     * @param path      the path of the resource
     * @param extension a comma separate list of extensions
     * @param locale    the locale of the resource to load
     * @return the cache key
     */
    private String createCacheKey( @Nonnull final String path,
                                   @Nullable final String extension,
                                   @Nullable final Locale locale )
    {
        StringBuilder buffer = new StringBuilder( path );

        if ( !Strings.isNullOrEmpty( extension ) )
        {
            buffer.append( "_" );
            buffer.append( extension );
        }
        if ( locale != null )
        {
            buffer.append( "_" );
            buffer.append( locale.getLanguage() );
        }

        return buffer.toString();
    }
}
