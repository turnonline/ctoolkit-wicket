package org.ctoolkit.turnonline.wicket.model;

import org.apache.wicket.Component;
import org.apache.wicket.model.AbstractReadOnlyModel;

import java.util.Locale;

/**
 * The model implementation that takes a language as string from the session locale.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class LanguageModel
        extends AbstractReadOnlyModel<String>
{
    private static final long serialVersionUID = 1L;

    private Component component;

    /**
     * Constructor
     *
     * @param component the parent component to access session
     */
    public LanguageModel( Component component )
    {
        this.component = component;
    }

    @Override
    public String getObject()
    {
        Locale locale = component.getSession().getLocale();
        return locale.getLanguage();
    }
}