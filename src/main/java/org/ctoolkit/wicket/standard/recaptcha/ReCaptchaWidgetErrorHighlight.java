package org.ctoolkit.wicket.standard.recaptcha;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;

/**
 * The error highlight behavior for {@link ReCaptchaWidget}.
 * Adds 'error' to class attribute as an additional value if ReCaptcha widget is invalid.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class ReCaptchaWidgetErrorHighlight
        extends Behavior
{
    private static final long serialVersionUID = 1L;

    public void onComponentTag( Component component, ComponentTag tag )
    {
        if ( component instanceof ReCaptchaWidget )
        {
            ReCaptchaWidget captcha = ( ReCaptchaWidget ) component;

            if ( !captcha.isValid() )
            {
                tag.put( "class", tag.getAttribute( "class" ) + " error" );
            }
        }
    }

    @Override
    public boolean isTemporary( Component component )
    {
        return true;
    }
}
