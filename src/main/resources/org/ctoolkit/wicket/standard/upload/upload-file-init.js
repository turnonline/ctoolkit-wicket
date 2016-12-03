$( function ()
{
    $( '#${markupId}' ).uploadFile( {
        url: '${actionUrl}',
        multiple: false,
        dragDrop: false,
        showAbort: false,
        acceptFiles: 'image/*',
        fileName: '${fileField}',
        uploadStr: '${uploadStr}',
        cancelStr: '${cancelStr}',
        abortStr: '${cancelStr}',
        formData: {'upload-name': '${upload-name}'},
        returnType: 'json',
        uploadButtonClass: '${buttonClass}',
        abortButtonClass: '${buttonClass}',
        cancelButtonClass: '${buttonClass}',

        onSuccess: function ( files, data, xhr, pd )
        {
            $( "#${thumbnailMarkupId}" ).attr( 'src', data[0].thumbnail_url );
        }
    } );
} );
