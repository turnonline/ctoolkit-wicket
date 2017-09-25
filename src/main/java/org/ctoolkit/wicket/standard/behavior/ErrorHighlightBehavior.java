package org.ctoolkit.wicket.standard.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;

/**
 * The error highlight behavior for {@link FormComponent} components.
 * Adds 'error' to class attribute as an additional value if component is invalid.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class ErrorHighlightBehavior
        extends Behavior
{
    private static final long serialVersionUID = 1L;

    public void onComponentTag( Component c, ComponentTag tag )
    {
        FormComponent fc = ( FormComponent ) c;
        if ( !fc.isValid() )
        {
            tag.put( "class", tag.getAttribute( "class" ) + " error" );
        }
    }
}