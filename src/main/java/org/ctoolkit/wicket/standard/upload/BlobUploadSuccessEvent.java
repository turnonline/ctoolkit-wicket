package org.ctoolkit.wicket.standard.upload;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.FileInfo;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The successful blob upload to the cloud storage event.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class BlobUploadSuccessEvent
{
    private BlobKey blobKey;

    private FileInfo fileInfo;

    private String thumbnailUrl;

    private String uploadName;

    public BlobUploadSuccessEvent( @Nonnull BlobKey blobKey,
                                   @Nonnull FileInfo fileInfo,
                                   @Nonnull String thumbnailUrl,
                                   String uploadName )
    {
        this.blobKey = checkNotNull( blobKey );
        this.fileInfo = checkNotNull( fileInfo );
        this.thumbnailUrl = checkNotNull( thumbnailUrl );
        this.uploadName = uploadName;
    }

    public BlobKey getBlobKey()
    {
        return blobKey;
    }

    public FileInfo getFileInfo()
    {
        return fileInfo;
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
