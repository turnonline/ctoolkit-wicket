package org.ctoolkit.turnonline.wicket.upload;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * The static resource reference of the {@link BlobUploadResource}.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class BlobUploadResourceReference
        extends ResourceReference
{
    public static final String REFERENCE_ID = "jquery-file-upload-reference";

    private static final long serialVersionUID = 1L;

    private static final BlobUploadResourceReference INSTANCE = new BlobUploadResourceReference();

    protected BlobUploadResourceReference()
    {
        super( BlobUploadResourceReference.class, REFERENCE_ID );
    }

    public static BlobUploadResourceReference get()
    {
        return INSTANCE;
    }

    @Override
    public IResource getResource()
    {
        return new BlobUploadResource();
    }
}
