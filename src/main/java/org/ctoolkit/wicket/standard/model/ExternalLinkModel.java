package org.ctoolkit.wicket.standard.model;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.ctoolkit.wicket.standard.util.WicketUtil;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The model that renders given path prepended with request's server URL.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class ExternalLinkModel
        extends AbstractReadOnlyModel<String>
{
    private static final long serialVersionUID = 1L;

    private final String path;

    /**
     * Constructor.
     *
     * @param path the relative URL path including '/'
     */
    public ExternalLinkModel( String path )
    {
        this.path = checkNotNull( path );
    }

    @Override
    public String getObject()
    {
        return WicketUtil.getServerUrl() + path;
    }
}
