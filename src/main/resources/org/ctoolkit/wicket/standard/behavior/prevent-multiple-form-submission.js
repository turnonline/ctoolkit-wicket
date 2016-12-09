$('#${markupId}').on('click', function(e){
    var button = $(this);

    if (button.data('submitted') === true)
    {
        // Previously submitted - don't submit again
        e.preventDefault();
    }
    else
    {
        // Mark it so that the next submit can be ignored
        button.data('submitted', true);
    }
});