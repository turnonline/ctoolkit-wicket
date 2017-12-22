package org.ctoolkit.wicket.standard.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.util.template.PackageTextTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * The behavior that renders a JavaScript snippet from 'fixed-element-behavior.js' (on dom ready).
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class FixedElementBehavior
        extends Behavior
{
    private static final long serialVersionUID = -5005234457849521854L;

    @Override
    public void renderHead( Component component, IHeaderResponse response )
    {
        String script = getScript( component );

        response.render( OnDomReadyHeaderItem.forScript( script ) );
    }

    /**
     * Configures the connected component to render its markup id
     * because it is needed to initialize the JavaScript widget.
     */
    @Override
    public void bind( Component component )
    {
        super.bind( component );
        component.setOutputMarkupId( true );
    }

    private String getScript( Component component )
    {
        PackageTextTemplate template = new PackageTextTemplate( FixedElementBehavior.class, "fixed-element-behavior.js" );

        Map<String, Object> variables = new HashMap<>();
        variables.put( "markupId", component.getMarkupId() );
        variables.put( "treshold", 100 );

        return template.asString( variables );
    }
}
