package org.ctoolkit.wicket.standard.upload;

import com.google.appengine.api.appidentity.AppIdentityService;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.UploadOptions;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.util.template.PackageTextTemplate;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * The AppEngine specific behaviour that contributes CSS and JS resources from CDN needed by
 * http://hayageek.com/docs/jquery-upload-file.php (version 4.0.1) incl. plugin initialization script.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 * @see BlobstoreService#createUploadUrl(String, UploadOptions)
 */
public class BlobUploadBehavior
        extends Behavior
{
    public static final String PARAMETER_FILE_FIELD = "__file_field";

    public static final String UPLOAD_NAME = "upload-name";

    private static final long serialVersionUID = 1L;

    private final String thumbnailMarkupId;

    private final String uploadName;

    @Inject
    private AppIdentityService appIdentityService;

    @Inject
    private BlobstoreService blobstoreService;

    /**
     * Constructor.
     *
     * @param thumbnailMarkupId the thumbnail component Id, component's 'src' will be updated by current thumbnail URL
     * @param uploadName        the provided name will come back as part of the upload event notification
     */
    public BlobUploadBehavior( String thumbnailMarkupId, String uploadName )
    {
        this.thumbnailMarkupId = thumbnailMarkupId;
        this.uploadName = uploadName;

        Injector.get().inject( this );
    }

    /**
     * Configures the connected component to render its markup id
     * because it is needed to initialize the JavaScript widget.
     */
    @Override
    public void bind( Component component )
    {
        super.bind( component );
        component.setOutputMarkupId( true );
    }

    @Override
    public void renderHead( Component component, IHeaderResponse response )
    {
        String script = getScript( component );

        response.render( OnDomReadyHeaderItem.forScript( script ) );

        Url jsCdn = Url.parse( "https://hayageek.github.io/jQuery-Upload-File/4.0.1/jquery.uploadfile.min.js" );
        UrlResourceReference jsCdnReference = new UrlResourceReference( jsCdn );
        response.render( JavaScriptHeaderItem.forReference( jsCdnReference ) );
    }

    /**
     * upload-file-init.js file properties population
     */
    private String getScript( Component component )
    {
        String componentUrl = component.urlFor( BlobUploadResourceReference.get(), null ).toString();
        String bucketName = appIdentityService.getDefaultGcsBucketName();
        UploadOptions uploadOptions = UploadOptions.Builder.withGoogleStorageBucketName( bucketName );

        String url = blobstoreService.createUploadUrl( componentUrl, uploadOptions );

        PackageTextTemplate template = new PackageTextTemplate( BlobUploadBehavior.class, "upload-file-init.js" );
        Map<String, Object> variables = new HashMap<>();

        variables.put( "markupId", component.getMarkupId() );
        variables.put( "thumbnailMarkupId", thumbnailMarkupId );
        variables.put( "actionUrl", url );
        variables.put( "fileField", PARAMETER_FILE_FIELD );
        variables.put( "uploadStr", component.getString( "button.upload" ) );
        variables.put( "cancelStr", component.getString( "button.cancel" ) );
        variables.put( "buttonClass", "button" );
        variables.put( UPLOAD_NAME, uploadName );

        return template.asString( variables );
    }
}
