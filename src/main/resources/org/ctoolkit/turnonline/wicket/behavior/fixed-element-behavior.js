$( window ).scroll( function ( e )
{
    var elm = $( "#${markupId}" );
    var treshold = ${treshold};
    var scrollTop = $( "body" )[0].scrollTop;

    if ( scrollTop >= treshold )
    {
        elm.addClass( "fixed" );
    }
    else
    {
        if ( scrollTop == 0 )
        {
            elm.removeClass( "fixed" );
        }
    }
} )