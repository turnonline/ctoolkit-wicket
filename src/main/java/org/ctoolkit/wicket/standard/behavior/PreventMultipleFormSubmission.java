package org.ctoolkit.wicket.standard.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.util.template.PackageTextTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * The behavior to avoid multiple form submission.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class PreventMultipleFormSubmission
        extends Behavior
{
    private static final long serialVersionUID = 1L;

    @Override
    public void renderHead( Component component, IHeaderResponse response )
    {
        String fileName = "prevent-multiple-form-submission.js";
        PackageTextTemplate template = new PackageTextTemplate( PreventMultipleFormSubmission.class, fileName );
        Map<String, Object> variables = new HashMap<>();
        variables.put( "markupId", component.getMarkupId() );

        response.render( OnDomReadyHeaderItem.forScript( template.asString( variables ) ) );
    }
}
