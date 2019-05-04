package org.ctoolkit.wicket.standard.gwt;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.template.PackageTextTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * The behavior responsible to render custom JavaScript snippet in HTML header.
 * <p>
 * <strong>Example</strong>
 * <pre>
 * {@code
 * var Configuration = {
 *     LOGIN_ID: '${LOGIN_ID}',
 *     DOMICILE: '${DOMICILE}',
 *     CURRENCY: '${CURRENCY}',
 *     VAT: 'STANDARD',
 * };
 * }
 * </pre>
 * In case of using no arg constructor a file 'BootConfiguration.js' will be loaded from default
 * {@code /org/ctoolkit/wicket/standard/gwt} location.
 * <p>
 * {@link PackageTextTemplate} is used to render JavaScript content that is responsible also
 * to replace variables with custom specified values.
 *
 * @author <a href="mailto:medvegy@turnonline.biz">Aurel Medvegy</a>
 * @see PackageTextTemplate
 */
public abstract class BootConfiguration
        extends Behavior
{
    private static final long serialVersionUID = 7181505491763594021L;

    private Class<?> scope;

    private String fileName;

    private Map<String, Object> variables;

    private PackageTextTemplate template;

    /**
     * Constructor. Initialized with default scope and file name.
     */
    public BootConfiguration()
    {
        this( BootConfiguration.class );
    }

    /**
     * Constructor. Initialized with default file name 'BootConfiguration.js'.
     *
     * @param scope the <code>Class</code> package to be used for loading the default file
     */
    public BootConfiguration( @Nonnull Class<?> scope )
    {
        this( scope, "BootConfiguration.js" );
    }

    /**
     * Constructor.
     *
     * @param scope    the <code>Class</code> package to be used for loading the default file
     * @param fileName the name of the file, relative to the <code>scope</code> position
     */
    public BootConfiguration( @Nonnull Class<?> scope, @Nonnull String fileName )
    {
        this.scope = Args.notNull( scope, "scope" );
        this.fileName = Args.notNull( fileName, "fileName" );
    }

    public BootConfiguration variables( @Nullable Map<String, Object> variables )
    {
        this.variables = variables;
        return this;
    }

    @Override
    public void bind( Component component )
    {
        template = new PackageTextTemplate( scope, fileName );
    }

    @Override
    public void renderHead( Component component, IHeaderResponse response )
    {
        Map<String, Object> args = getArgs();
        String specific;

        if ( args != null && !args.isEmpty() )
        {
            Map<String, Object> all;
            if ( variables == null )
            {
                all = new HashMap<>();
            }
            else
            {
                all = new HashMap<>( variables );
            }

            all.putAll( args );
            specific = template.asString( all );
        }
        else
        {
            specific = template.asString( variables );
        }
        JavaScriptContentHeaderItem item = JavaScriptHeaderItem.forScript( specific, component.getMarkupId() );
        response.render( new PriorityHeaderItem( item ) );
    }

    /**
     * Override in order to provide request specific variables.
     *
     * @return the request specific variables or {@code null} if none
     */
    protected Map<String, Object> getArgs()
    {
        return null;
    }
}
