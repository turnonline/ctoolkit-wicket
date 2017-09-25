package org.ctoolkit.wicket.standard.markup.html.social;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.encoding.UrlEncoder;

import java.text.MessageFormat;

/**
 * The Facebook like button web markup implementation.
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class FacebookLikeButton
        extends WebMarkupContainer
{
    private static final long serialVersionUID = 1L;

    private static final String FACEBOOK_LIKE = "//www.facebook.com/plugins/like.php?locale={0}";

    private static final String AND = "&";

    protected String width = "150";

    protected String height = "20";

    protected String layout = "button";

    protected String action = "like";

    protected boolean showFaces = false;

    protected boolean share = true;

    protected String appId;

    public FacebookLikeButton( String id, IModel<String> model )
    {
        super( id, model );
    }

    @Override
    protected void onComponentTag( ComponentTag tag )
    {
        super.onComponentTag( tag );

        tag.setName( "iframe" );
        tag.put( "src", src() );
        tag.put( "scrolling", "no" );
        tag.put( "frameborder", 0 );
        tag.put( "style", "border:none; overflow:hidden; width:" + width + "px; height:" + height + "px;" );
        tag.put( "allowTransparency", true );
    }

    private String src()
    {
        UrlEncoder encoder = UrlEncoder.FULL_PATH_INSTANCE;
        String lang = getLocale().getLanguage();
        String likeLink = MessageFormat.format( FACEBOOK_LIKE, lang + "_" + lang.toUpperCase() );

        StringBuilder builder = new StringBuilder( likeLink );

        builder.append( AND );
        builder.append( "href=" );
        builder.append( encoder.encode( getDefaultModelObjectAsString(), "UTF-8" ) );
        builder.append( AND );
        builder.append( "width=" );
        builder.append( width );
        builder.append( AND );
        builder.append( "layout=" );
        builder.append( layout );
        builder.append( AND );
        builder.append( "action=" );
        builder.append( action );
        builder.append( AND );
        builder.append( "show_faces=" );
        builder.append( showFaces );
        builder.append( AND );
        builder.append( "share=" );
        builder.append( share );
        builder.append( AND );
        builder.append( "height=" );
        builder.append( height );

        if ( appId != null )
        {
            builder.append( AND );
            builder.append( "appId=" );
            builder.append( appId );
        }

        return builder.toString();
    }

    public String getWidth()
    {
        return width;
    }

    public void setWidth( String width )
    {
        this.width = width;
    }

    public String getHeight()
    {
        return height;
    }

    public void setHeight( String height )
    {
        this.height = height;
    }

    public String getLayout()
    {
        return layout;
    }

    public void setLayout( String layout )
    {
        this.layout = layout;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction( String action )
    {
        this.action = action;
    }

    public boolean isShowFaces()
    {
        return showFaces;
    }

    public void setShowFaces( boolean showFaces )
    {
        this.showFaces = showFaces;
    }

    public boolean isShare()
    {
        return share;
    }

    public void setShare( boolean share )
    {
        this.share = share;
    }

    public String getAppId()
    {
        return appId;
    }

    public void setAppId( String appId )
    {
        this.appId = appId;
    }
}
