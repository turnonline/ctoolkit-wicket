package org.ctoolkit.wicket.standard.upload;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.FileInfo;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.common.eventbus.EventBus;
import com.google.common.html.HtmlEscapers;
import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.upload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The AppEngine specific blob upload resource handler. In the response the uploaded blob serving URL
 * is being evaluated and sent back to the client together with blob key as JSON.
 * <p/>
 * It processes only the single (the first found) blob key from the request.
 * Fires BlobUploadSuccessEvent via {@link EventBus} as standard wicket event mechanism is not available here.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 * @see BlobstoreService#getUploads(HttpServletRequest)
 */
public class BlobUploadResource
        extends AbstractResource
{
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger( BlobUploadResource.class );

    @Inject
    private BlobstoreService blobstoreService;

    @Inject
    private ImagesService imageService;

    @Inject
    private EventBus eventBus;

    public BlobUploadResource()
    {
        Injector.get().inject( this );
    }

    @Override
    protected ResourceResponse newResourceResponse( Attributes attributes )
    {
        ResourceResponse response = new ResourceResponse();
        ServletWebRequest webRequest = ( ServletWebRequest ) attributes.getRequest();

        try
        {
            prepareResponse( response, webRequest );
        }
        catch ( FileUploadException | IOException e )
        {
            logger.error( "An error occurred while uploading a blob", e );
            throw new AbortWithHttpErrorCodeException( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage() );
        }

        return response;
    }

    private void prepareResponse( ResourceResponse response, ServletWebRequest webRequest )
            throws FileUploadException, IOException
    {
        final String responseContent;
        String accept = webRequest.getHeader( "Accept" );

        if ( !Strings.isEmpty( accept ) && accept.contains( "text/html" ) )
        {
            // for Internet Explorer
            response.setContentType( "text/html" );
            responseContent = generateHtmlResponse( webRequest );
        }
        else
        {
            response.setContentType( "application/json" );
            responseContent = generateJsonResponse( webRequest );
        }

        response.setWriteCallback( new WriteCallback()
        {
            @Override
            public void writeData( Attributes attributes ) throws IOException
            {
                attributes.getResponse().write( responseContent );
            }
        } );
    }

    private String generateHtmlResponse( ServletWebRequest webRequest )
    {
        String json = generateJsonResponse( webRequest );
        return HtmlEscapers.htmlEscaper().escape( json );
    }

    private String generateJsonResponse( ServletWebRequest webRequest )
    {
        JSONArray json = new JSONArray();
        JSONObject jsonEntry = new JSONObject();

        try
        {
            HttpServletRequest request = webRequest.getContainerRequest();
            Map<String, List<FileInfo>> fileInfos = blobstoreService.getFileInfos( request );
            List<FileInfo> list = fileInfos.get( BlobUploadBehavior.PARAMETER_FILE_FIELD );
            FileInfo info = list.get( 0 );

            Map<String, List<BlobKey>> blobs = blobstoreService.getUploads( request );
            BlobKey blobKey = blobs.get( BlobUploadBehavior.PARAMETER_FILE_FIELD ).get( 0 );

            String uploadName = request.getParameter( BlobUploadBehavior.UPLOAD_NAME );
            String gStorageName = info.getGsObjectName();

            logger.info( "Upload name: " + uploadName );
            logger.info( "BlobKey: " + blobKey );
            logger.info( "StorageName: " + gStorageName );

            ServingUrlOptions options;
            options = ServingUrlOptions.Builder.withBlobKey( blobKey ).crop( false ).secureUrl( true );
            String servingUrl = imageService.getServingUrl( options );

            logger.info( "Thumbnail URL: " + servingUrl );

            eventBus.post( new BlobUploadSuccessEvent( blobKey, info, servingUrl, uploadName ) );

            jsonEntry.put( "thumbnail_url", servingUrl );
            json.put( jsonEntry );
        }
        catch ( Exception e )
        {
            try
            {
                jsonEntry.put( "error", e.getMessage() );
            }
            catch ( JSONException e1 )
            {
                logger.error( "An error occurred while generating a response", e1 );
            }
            logger.error( "An error occurred while generating a response", e );
        }

        return json.toString();
    }
}
