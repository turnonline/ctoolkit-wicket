Wicket.Event.add( Wicket.$( ${markupId} ), 'keydown', function ( jqEvent )
{
    var KEY_ENTER = 13;

    switch ( Wicket.Event.keyCode( jqEvent ) )
    {
        case KEY_ENTER:
            var link = $( "#${markupId}-autocomplete ul li.selected a" );
            if ( link.length > 0 )
            {
                window.location.href = link.attr( "href" );
            }

            return true;

        default:
    }
} );

$( "#${markupId}" ).focus( function ()
{
    var overlay = $( "#search-box-overlay" );
    if ( overlay.length == 0 )
    {
        overlay = $( "html" ).append( "<div id='search-box-overlay'></div>" );
    }

    $( this ).parent().addClass( "search-box-focus" );

    overlay.fadeIn( 100 );
} );

$( "#${markupId}" ).blur( function ()
{
    $( this ).parent().removeClass( "search-box-focus" );
    $( "#search-box-overlay" ).fadeOut( 100 );
} );