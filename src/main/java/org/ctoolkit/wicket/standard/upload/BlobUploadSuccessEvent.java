package org.ctoolkit.wicket.standard.upload;

import com.google.appengine.api.blobstore.BlobKey;

/**
 * The blob upload on success event.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class BlobUploadSuccessEvent
{
    private BlobKey blobKey;

    private String thumbnailUrl;

    private String uploadName;

    public BlobUploadSuccessEvent( String uploadName, BlobKey blobKey, String thumbnailUrl )
    {
        this.uploadName = uploadName;
        this.blobKey = blobKey;
        this.thumbnailUrl = thumbnailUrl;
    }

    public BlobKey getBlobKey()
    {
        return blobKey;
    }

    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }

    public String getUploadName()
    {
        return uploadName;
    }

    @Override
    public String toString()
    {
        return "BlobUploadSuccessEvent{" +
                "blobKey=" + blobKey +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", uploadName='" + uploadName + '\'' +
                '}';
    }
}
