package org.ctoolkit.wicket.standard.recaptcha;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.model.IModel;

/**
 * The Google reCAPTCHA behaviour that contributes related JavaScript resources from CDN to the </head> tag.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class ReCaptchaBehavior
        extends Behavior
{
    private static final long serialVersionUID = 1L;

    private IModel<String> languageModel;

    private String dataSiteKey;

    public ReCaptchaBehavior( String key )
    {
        this( null, key );
    }

    public ReCaptchaBehavior( IModel<String> languageModel, String key )
    {
        this.languageModel = languageModel;
        this.dataSiteKey = key;
    }

    @Override
    public void renderHead( Component component, IHeaderResponse response )
    {
        response.render( JavaScriptHeaderItem.forScript( getScript( component ), "grecaptcha-id" ) );
    }

    @Override
    public void afterRender( Component component )
    {
        String url = "https://www.google.com/recaptcha/api.js?onload=onloadCallback";
        String language = languageModel == null ? null : languageModel.getObject();

        if ( !com.google.common.base.Strings.isNullOrEmpty( language ) )
        {
            url = url + "&hl=" + language.toLowerCase();
        }
        component.getResponse().write( "<script src=\"" + url + "\"" + " async defer>\n" + "</script>" );
    }

    private String getScript( Component component )
    {
        return "function onloadCallback() {grecaptcha.render('" + component.getMarkupId()
                + "',{sitekey:'" + dataSiteKey + "'})}";
    }
}
