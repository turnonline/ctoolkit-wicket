package org.ctoolkit.wicket.standard.markup.html.panel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.ctoolkit.wicket.standard.markup.html.basic.ULabel;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The panel to render a static message with a level {@link Level} as an indicator how to visualize to the end user.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class MessagePanel
        extends GenericPanel<String>
{
    private static final long serialVersionUID = 6887752734845883174L;

    public MessagePanel( String id, IModel<String> message, Level level )
    {
        this( id, message, new Model<>( level ) );
    }

    public MessagePanel( String id, IModel<String> message, IModel<Level> level )
    {
        super( id, message );

        WebMarkupContainer ul = new WebMarkupContainer( "ul" );
        add( ul );

        WebMarkupContainer li = new WebMarkupContainer( "li" );
        li.add( AttributeModifier.replace( "class", new ClassModel( level ) ) );
        ul.add( li );

        ULabel span = new ULabel( "span", getModel() );
        span.add( AttributeModifier.replace( "class", new ClassModel( level ) ) );
        li.add( span );
    }

    public void setMessage( Model<String> message )
    {
        get( "ul" ).get( "li" ).get( "span" ).setDefaultModel( message );
    }

    protected String getCss( String levelAsString )
    {
        return "feedbackPanel" + levelAsString;
    }

    public enum Level
    {
        /**
         * Constant for info level message.
         */
        INFO,

        /**
         * Constant for success level  message (it indicates the outcome of an operation)
         */
        SUCCESS,

        /**
         * Constant for warning level message.
         */
        WARNING,

        /**
         * Constant for error level message.
         */
        ERROR
    }

    private class ClassModel
            extends AbstractReadOnlyModel<String>
    {
        private static final long serialVersionUID = 6935818251136100844L;

        private final IModel<Level> level;

        ClassModel( IModel<Level> level )
        {
            this.level = checkNotNull( level );
        }

        @Override
        public String getObject()
        {
            return getCss( level.getObject().name() );
        }
    }
}
