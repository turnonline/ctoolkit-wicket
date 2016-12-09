package org.ctoolkit.wicket.standard.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.util.template.PackageTextTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Behavior disable multiple form submission
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class PreventMultipleFormSubmission extends Behavior {

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        PackageTextTemplate template = new PackageTextTemplate( PreventMultipleFormSubmission.class, "prevent-multiple-form-submission.js" );
        Map<String, Object> variables = new HashMap<>();
        variables.put( "markupId", component.getMarkupId() );

        response.render(OnDomReadyHeaderItem.forScript(template.asString( variables )));
    }
}
