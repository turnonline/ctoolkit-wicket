package org.ctoolkit.turnonline.wicket.markup.html.page;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.https.RequireHttps;
import org.apache.wicket.util.cookies.CookieUtils;
import org.ctoolkit.turnonline.wicket.behavior.IdAttributeModifier;
import org.ctoolkit.turnonline.wicket.markup.html.basic.ULabel;
import org.ctoolkit.turnonline.wicket.menu.DefaultSchema;
import org.ctoolkit.turnonline.wicket.menu.Footer;
import org.ctoolkit.turnonline.wicket.menu.Header;
import org.ctoolkit.turnonline.wicket.menu.NavigationItem;
import org.ctoolkit.turnonline.wicket.model.I18NResourceModel;
import org.ctoolkit.turnonline.wicket.util.CookiesUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Decorated page that includes feedback panel, title, header and footer.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@RequireHttps
public abstract class DecoratedPage<T>
        extends Skeleton<T>
{
    public static final String FEEDBACK_MARKUP_ID = "feedback";

    private static final long serialVersionUID = 1L;

    public DecoratedPage()
    {
        super();
    }

    public DecoratedPage( IModel<T> model )
    {
        super( model );
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        IModel<T> model = getModel();
        CookieUtils cookieUtils = new CookieUtils();
        cookieUtils.remove( CookiesUtil.PAGE_CLASS );

        // set page title
        add( new ULabel( "title", getPageTitle() ) );

        // header
        add( new Header( "header", getPage(), modelFactory(), model ) );

        final IModel<?> titleModel = getPageH1Header();
        add( new ULabel( "headerTitle", titleModel )
        {
            private static final long serialVersionUID = -5623163063482071122L;

            @Override
            public boolean isVisible()
            {
                return titleModel != null;
            }
        } );

        // footer
        add( new Footer( "footer", getNavigationPages(), Arrays.asList( Locale.US, new Locale( "sk" ) ) ) );

        FeedbackPanel feedbackPanel = new FeedbackPanel( FEEDBACK_MARKUP_ID )
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected Component newMessageDisplayComponent( String id, FeedbackMessage message )
            {
                Component reporter = message.getReporter();
                String markupId = reporter.getMarkupId();
                for ( IdAttributeModifier am : reporter.getBehaviors( IdAttributeModifier.class ) )
                {
                    markupId = am.getMarkupId();
                }

                Component component = super.newMessageDisplayComponent( id, message );

                if ( reporter instanceof FormComponent && message.getLevel() == FeedbackMessage.ERROR )
                {
                    component.add( AttributeModifier.append( "onclick", "document.getElementById('" + markupId + "').focus()" ) );
                    component.add( AttributeModifier.append( "class", "feedbackPanelAnchor" ) );
                }

                return component;
            }
        };
        feedbackPanel.setEscapeModelStrings( false );
        feedbackPanel.setOutputMarkupId( true );
        add( feedbackPanel );
    }

    protected IModel<List<NavigationItem>> getNavigationPages()
    {
        return DefaultSchema.EMPTY;
    }

    public final void showError( Exception exception )
    {
        error( exception.getMessage() );
    }

    /**
     * Returns the header title model. It will be rendered into <code>h1</code> element.
     * By default returns null and won't be rendered.
     *
     * @return the page header title model
     */
    protected IModel<?> getPageH1Header()
    {
        return null;
    }

    /**
     * Get page title model. Default title.changeit to be changed.
     *
     * @return the page title model
     */
    public IModel<?> getPageTitle()
    {
        return new I18NResourceModel( "title.changeit" );
    }
}