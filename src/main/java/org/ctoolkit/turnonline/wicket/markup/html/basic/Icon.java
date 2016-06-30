package org.ctoolkit.turnonline.wicket.markup.html.basic;

/**
 * Icon enum.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public enum Icon
{
    ALERT( "alert" ),
    ALERT2( "alert2" ),
    ALERT3( "alert3" ),
    ARROWS( "arrows" ),
    BLOCK( "block" ),
    BOOK( "book" ),
    BRIEFCASE( "briefcase" ),
    CALCULATOR( "calculator" ),
    CALENDAR( "calendar" ),
    CARDS( "cards" ),
    CART( "cart" ),
    CHART( "chart" ),
    CHAT( "chat" ),
    CLOCK( "clock" ),
    CLOSE( "close" ),
    CLOSE2( "close2" ),
    CONNECT( "connect" ),
    CONTACT( "contact" ),
    DISLIKE( "dislike" ),
    DOT( "dot" ),
    DOT2( "dot2" ),
    DOWN( "down" ),
    EDIT( "edit" ),
    EURO( "euro" ),
    EURO_SMALL( "euro-small" ),
    EXCLAMATION( "exclamation" ),
    HOME( "home" ),
    HOME2( "home2" ),
    INFO( "info" ),
    LAPTOP( "laptop" ),
    LEFT( "left" ),
    LEFT2( "left2" ),
    LIKE( "like" ),
    LOCATION( "location" ),
    LOCK( "lock" ),
    LOGOUT( "logout" ),
    MENU( "menu" ),
    MINUS( "minus" ),
    PAPER( "paper" ),
    PLUS( "plus" ),
    QUESTION( "question" ),
    RETURN( "return" ),
    RIGHT( "right" ),
    RIGHT2( "right2" ),
    SEARCH( "search" ),
    SETTINGS( "settings" ),
    STAR( "star" ),
    STAR2( "star2" ),
    TICK( "tick" ),
    TICK2( "tick2" ),
    TICK3( "tick3" ),
    TRASH( "trash" ),
    UP( "up" ),
    USER( "user" ),
    WALLET( "wallet" ),
    UPLOAD( "upload" ),
    DOWNLOAD( "download" ),
    DOWNLOAD_ERROR( "download-error" ),
    UPLOAD_ERROR( "upload-error" ),
    DOWNLOAD_ERROR_2( "download-error2" ),
    UPLOAD_ERROR_2( "upload-error2" ),
    DOWNLOAD_3( "download3" ),
    UPLOAD_3( "upload3" ),
    COPY( "copy" ),
    NEW( "new" ),
    INVOICE( "invoice" ),
    INVOICE_NEW( "invoice-new" ),
    DETAIL_1( "detail1" ),
    DETAIL_2( "detail2" ),
    CHANGE_COLOR( "change-color" ),
    CHANGE_COLOR2( "change-color2" ),
    CALENDAR_2( "calendar2" ),
    EVENT( "event" ),
    CALENDAR_3( "calendar3" ),
    EVENT_2( "event2" ),
    ACTIVE_ARROW( "active-arrow" ),
    DASHBOARD( "dashboard" ),
    NEW_CONTACT( "new-contact" ),
    ORDERS( "orders" ),
    ORDERS_2( "orders2" ),
    ORDERS_3( "orders3" );

    private String cssName;

    Icon( String cssName )
    {
        this.cssName = cssName;
    }

    public String getCssName()
    {
        return "icon icon-" + cssName;
    }
}
