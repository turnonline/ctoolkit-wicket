package org.ctoolkit.turnonline.wicket.markup.html.formrow;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
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

    private Variant variant;

    private IModel<String> label;

    public FormRowBehavior()
    {
        this( Variant.INLINE_BLOCK, null );
    }

    public FormRowBehavior( Variant variant )
    {
        this( variant, null );
    }

    public FormRowBehavior( IModel<String> label )
    {
        this( Variant.INLINE_BLOCK, label );
    }

    public FormRowBehavior( Variant variant, IModel<String> label )
    {
        this.variant = variant;
        this.label = label;
    }

    @Override
    public void beforeRender( Component component )
    {
        String label = getLabel( component ).getObject();

        Response r = component.getResponse();
        r.write( "<div class='" + getCssClass( component ) + "'>" );

        if ( !Strings.isEmpty( label ) )
        {
            r.write( "<label for='" + component.getMarkupId( true ) + "'>" );
            r.write( label );
            r.write( "</label>" );
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
        component.getResponse().write( "</div>" );
    }

    public String getCssClass( Component component )
    {
        StringBuilder sb = new StringBuilder();

        if ( component instanceof CheckBox )
        {
            sb.append( "component-checkbox " );
        }

        sb.append( "form-row" );

        if ( isRequired( component ) )
        {
            sb.append( " required" );
        }

        if ( component.hasErrorMessage() )
        {
            sb.append( " error" );
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
