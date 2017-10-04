package org.ctoolkit.wicket.standard.cache;

import net.sf.jsr107cache.Cache;
import org.apache.wicket.resource.IPropertiesFactoryContext;
import org.apache.wicket.resource.Properties;
import org.apache.wicket.resource.PropertiesFactory;
import org.apache.wicket.util.collections.ConcurrentHashSet;
import org.apache.wicket.util.value.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The properties factory that manages Wicket properties retrieval (resource bundle etc) from the App Engine (standard)
 * memcache that is shared between instances. With this factory a newly started App Engine instance will experience
 * a better wicket cold start up time as wicket properties does not have to be loaded again from the disk
 * that is very slow comparing to other possibilities.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class MemcachePropertiesFactory
        extends PropertiesFactory
{
    private static final Logger logger = LoggerFactory.getLogger( MemcachePropertiesFactory.class );

    private final Cache memcache;

    private Set<String> include = new ConcurrentHashSet<>();

    /**
     * Constructor.
     * <p>
     * The param 'include' is here in order to optimize the size of the cache to avoid too many
     * {@link Properties#EMPTY_PROPERTIES} in it. It would be possible to have 1000s of empty items what may cause
     * on App Engine very poor performance.
     * The example of the list:
     * <ul>
     * <li>include.add( "org/apache/wicket/Application" );</li>
     * <li>include.add( "org/apache/wicket/Application_de" );</li>
     * <li>include.add( "org/apache/wicket/extensions/Initializer" );</li>
     * </ul>
     * With this list only presented paths will be considered for caching.
     * Note, empty list means nothing would be cached.
     *
     * @param context  the environment required for properties factory
     * @param memcache the App Engine cache shared between instances
     * @param include  the list of resource paths that will be included for caching
     */
    public MemcachePropertiesFactory( @Nonnull IPropertiesFactoryContext context,
                                      @Nonnull Cache memcache,
                                      @Nonnull Set<String> include )
    {
        super( checkNotNull( context ) );
        this.memcache = checkNotNull( memcache );
        this.include = new ConcurrentHashSet<>( checkNotNull( include ) );
    }

    @Override
    public Properties load( Class<?> clazz, String path )
    {
        if ( !include.contains( path ) )
        {
            return null;
        }

        Map<String, Properties> localCache = getCache();
        Properties properties = localCache.get( path );
        if ( properties == Properties.EMPTY_PROPERTIES )
        {
            // for this path an empty properties placeholder has been found thus translate to null
            properties = null;
        }

        if ( properties != null )
        {
            return properties;
        }

        ValueMap valueMap = ( ValueMap ) memcache.get( path );
        if ( valueMap != null )
        {
            properties = new Properties( path, valueMap );
            logger.info( "ValueMap taken from memcache for key: '" + path + "'" );
        }

        // if not found, try to load requested properties from the disk
        if ( properties == null )
        {
            properties = super.load( clazz, path );
            if ( properties != null )
            {
                ValueMap map = properties.getAll();
                if ( map != null )
                {
                    memcache.put( path, map );
                    logger.info( "Properties ValueMap has been put to the memcache with key: '" + path + "'" );
                }
                else
                {
                    logger.warn( "Retrieved ValueMap is null for key: '" + path + "'" );
                }
            }
        }

        return properties;
    }
}
