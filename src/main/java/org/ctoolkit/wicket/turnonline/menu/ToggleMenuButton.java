package org.ctoolkit.wicket.turnonline.menu;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.Model;
import org.ctoolkit.wicket.standard.model.I18NResourceModel;

/**
 * The toggle menu button. Contributes a JavaScript snippet to the header.
 * <p>
 * Expected i18 resource bundle:
 * <ul>D
 * <li>label.menu</li>
 * </ul>
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class ToggleMenuButton
        extends ExternalLink
{
    private static final long serialVersionUID = 63632730942266970L;

    /**
     * Constructor
     *
     * @param id the component id
     */
    public ToggleMenuButton( String id )
    {
        super( id, new Model() );

        setOutputMarkupId( true );
        setBody( new I18NResourceModel( "label.menu" ) );

        add( new Behavior()
        {
            private static final long serialVersionUID = 7275881438093638172L;

            @Override
            public void renderHead( Component component, IHeaderResponse response )
            {
                response.render( OnDomReadyHeaderItem.forScript( "$('#" + getMarkupId()
                        + "').click(function(){$('.swipe-menu').toggleClass('swipe-menu-on')})" ) );
            }
        } );
    }
}
