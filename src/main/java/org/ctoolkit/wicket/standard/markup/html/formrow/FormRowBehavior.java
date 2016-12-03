package org.ctoolkit.wicket.standard.markup.html.formrow;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.string.Strings;

/**
 * Behavior wraps FormComponent with label and div:
 * <pre>
 *     &lt;div&gt;
 *         &lt;label&gt;${label}&lt;/label&gt;
 *         [component]
 *     &lt;/div&gt;
 * </pre>
 * <p/>
 *
 * @author <a href="mailto:jozef.pohorelec@ctoolkit.org">Jozef Pohorelec</a>
 */
public class FormRowBehavior
        extends Behavior
{
    private static final long serialVersionUID = -8684400463659951337L;

    private boolean componentFirst;

    private Variant variant;

    private IModel<String> label;

    public FormRowBehavior()
    {
        this( Variant.INLINE_BLOCK, null, false );
    }

    /**
     * Constructor.
     *
     * @param componentFirst if true the component will be rendered before label element
     */
    public FormRowBehavior( boolean componentFirst )
    {
        this( Variant.INLINE_BLOCK, null, componentFirst );
    }

    public FormRowBehavior( Variant variant )
    {
        this( variant, null, false );
    }

    public FormRowBehavior( IModel<String> label )
    {
        this( Variant.INLINE_BLOCK, label, false );
    }

    public FormRowBehavior( Variant variant, IModel<String> label, boolean componentFirst )
    {
        this.variant = variant;
        this.label = label;
        this.componentFirst = componentFirst;
    }

    @Override
    public void beforeRender( Component component )
    {
        Response response = component.getResponse();
        response.write( "<div class='" + getCssClass( component ) + "'>" );
        if ( !componentFirst )
        {
            renderLabel( component, response );
        }

    }

    @Override
    public void onComponentTag( Component component, ComponentTag tag )
    {
        super.onComponentTag( component, tag );

        if ( component.hasErrorMessage() )
        {
            tag.append( "class", "error", " " );
        }
    }

    private void renderLabel( Component component, Response response )
    {
        String label = getLabel( component ).getObject();

        if ( !Strings.isEmpty( label ) )
        {
            response.write( "<label for='" + component.getMarkupId( true ) + "'>" );
            response.write( label );
            response.write( "</label>" );
        }
    }

    protected IModel<String> getLabel( Component component )
    {
        if ( component instanceof FormComponent )
        {
            FormComponent<?> fc = ( FormComponent<?> ) component;
            if ( fc.getLabel() == null )
            {
                throw new RuntimeException( "Label must be set via component.setLabel(new I18ResourceBundle('label.componentName'))" );
            }

            return fc.getLabel();
        }

        return label;
    }

    protected boolean isRequired( Component component )
    {
        if ( component instanceof FormComponent )
        {
            FormComponent<?> fc = ( FormComponent<?> ) component;
            return fc.isRequired();
        }

        return false;
    }

    @Override
    public void afterRender( Component component )
    {
        Response response = component.getResponse();
        if ( componentFirst )
        {
            renderLabel( component, response );
        }
        response.write( "</div>" );
    }

    protected String getCssClass( Component component )
    {
        StringBuilder sb = new StringBuilder();

        if ( componentFirst )
        {
            sb.append( "component-first " );
        }
        else
        {
            if ( component instanceof CheckBox )
            {
                sb.append( "component-checkbox " );
            }
        }

        sb.append( "form-row" );

        if ( isRequired( component ) )
        {
            sb.append( " required" );
        }

        if ( variant == Variant.BLOCK )
        {
            sb.append( " block" );
        }
        if ( variant == Variant.INLINE )
        {
            sb.append( " inline" );
        }

        return sb.toString();
    }

    public enum Variant
    {
        /**
         * <p>Row is rendered inline, label and component are rendered inline.</p>
         * <p>| label_1: | component_1 | label_2: | component_2 |</p>
         */
        INLINE,

        /**
         * <p>Row is rendered as block, label and component are rendered as block</p>
         * <p>| label_1: |</p>
         * <p>| component_1 |</p>
         * <p>| label_2: |</p>
         * <p>| component_2 |</p>
         */
        BLOCK,

        /**
         * <p>Row is rendered as block, label and component are rendered inline</p>
         * <p>| label_1: | component_1 |</p>
         * <p>| label_2: | component_2 |</p>
         */
        INLINE_BLOCK;
    }
}
