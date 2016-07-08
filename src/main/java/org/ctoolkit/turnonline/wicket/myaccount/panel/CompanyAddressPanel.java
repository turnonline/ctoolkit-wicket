package org.ctoolkit.turnonline.wicket.myaccount.panel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.ctoolkit.turnonline.wicket.markup.autofill.AutofillAddress;
import org.ctoolkit.turnonline.wicket.markup.autofill.AutofillCity;
import org.ctoolkit.turnonline.wicket.markup.autofill.AutofillCountry;
import org.ctoolkit.turnonline.wicket.markup.autofill.AutofillPostalCode;
import org.ctoolkit.turnonline.wicket.markup.html.formrow.FormRowBehavior;
import org.ctoolkit.turnonline.wicket.model.I18NResourceModel;
import org.ctoolkit.turnonline.wicket.validator.ZipValidator;

/**
 * The company address form panel. Must be located within HTML form to post data.
 * Expected model properties:
 * <ul>
 * <li>street &lt;String&gt;</li>
 * <li>city &lt;String&gt;</li>
 * <li>zip &lt;String&gt;</li>
 * <li>state &lt;String&gt;</li>
 * </ul>
 * The model instance is being wrapped by {@link CompoundPropertyModel}.
 * <p>
 * Expected i18 resource bundle:
 * <ul>
 * <li>title.companyAddress</li>
 * <li>label.street</li>
 * <li>label.city</li>
 * <li>label.zip</li>
 * <li>label.state</li>
 * </ul>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@SuppressWarnings( "WeakerAccess" )
public abstract class CompanyAddressPanel<T>
        extends GenericPanel<T>
{
    private static final long serialVersionUID = -4846773836417022305L;

    private String streetPath;

    private String cityPath;

    private String zipPath;

    private String statePath;

    /**
     * Constructor of company address form panel instance.
     * By default fields are editable and required.
     *
     * @param id    the component id
     * @param model the component model
     */
    public CompanyAddressPanel( String id, final IModel<T> model )
    {
        this( id, model, false, true );
    }

    /**
     * Constructor of company address form panel instance.
     * By default fields are required.
     *
     * @param id       the component id
     * @param model    the component model
     * @param readOnly the boolean indication whether to render this component as read only
     */
    public CompanyAddressPanel( String id, final IModel<T> model, boolean readOnly )
    {
        this( id, model, readOnly, true );
    }

    /**
     * Constructor of personal address form panel instance.
     *
     * @param id       the component id
     * @param model    the component model
     * @param readOnly the boolean indication whether to render this component as read only
     * @param required the boolean indication whether to render this component as required
     */
    public CompanyAddressPanel( String id,
                                final IModel<T> model,
                                boolean readOnly,
                                boolean required )
    {
        super( id, new CompoundPropertyModel<>( model ) );
        setOutputMarkupId( true );
        add( AttributeModifier.replace( "class", "billing-address-panel" ) );

        // street
        TextField<String> street = new TextField<>( "street" );
        street.setLabel( new I18NResourceModel( "label.street" ) );
        street.setEnabled( !readOnly );
        street.setRequired( required );
        add( street );

        street.add( new FormRowBehavior() );
        street.add( AutofillAddress.get() );

        // city
        TextField<String> city = new TextField<>( "city" );
        city.setLabel( new I18NResourceModel( "label.city" ) );
        city.setEnabled( !readOnly );
        city.setRequired( required );
        add( city );

        city.add( new FormRowBehavior() );
        city.add( AutofillCity.get() );

        // zip
        TextField<String> zip = new TextField<>( "zip" );
        zip.setLabel( new I18NResourceModel( "label.zip" ) );
        zip.setEnabled( !readOnly );
        zip.setRequired( required );
        add( zip );

        zip.add( new FormRowBehavior() );
        zip.add( ZipValidator.get() );
        zip.add( AutofillPostalCode.get() );

        // state
        DropDownChoice state = provideCountry( "state" );
        state.setRequired( required );
        state.setLabel( new I18NResourceModel( "label.state" ) );
        state.setEnabled( !readOnly );
        add( state );

        state.add( new FormRowBehavior() );
        state.add( AutofillCountry.get() );

        this.streetPath = street.getPageRelativePath();
        this.cityPath = city.getPageRelativePath();
        this.zipPath = zip.getPageRelativePath();
        this.statePath = state.getPageRelativePath();
    }

    /**
     * Provides custom implementation of country drop down select box.
     *
     * @param componentId the country component id
     * @return the drop down instance
     */
    protected abstract DropDownChoice provideCountry( String componentId );

    /**
     * Adds a behavior modifier to the company address street text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the company address street text field instance
     */
    public TextField addCompanyAddressStreet( Behavior... behaviors )
    {
        return ( TextField ) getCompanyAddressStreet().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the company address city text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the company address city text field instance
     */
    public TextField addCompanyAddressCity( Behavior... behaviors )
    {
        return ( TextField ) getCompanyAddressCity().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the company address zip text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the company address zip text field instance
     */
    public TextField addCompanyAddressZip( Behavior... behaviors )
    {
        return ( TextField ) getCompanyAddressZip().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the company address state drop down select box component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the company address state drop down select box instance
     */
    public DropDownChoice addCompanyAddressState( Behavior... behaviors )
    {
        return ( DropDownChoice ) getCompanyAddressState().add( behaviors );
    }

    /**
     * Returns the company address street text field instance.
     *
     * @return the company address street text field instance
     */
    public TextField getCompanyAddressStreet()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.streetPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the company address city text field instance.
     *
     * @return the company address city text field instance
     */
    public TextField getCompanyAddressCity()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.cityPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the company address zip text field instance.
     *
     * @return the company address zip text field instance
     */
    public TextField getCompanyAddressZip()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.zipPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the company address state drop down select box instance.
     *
     * @return the company address state drop down select box instance
     */
    public DropDownChoice getCompanyAddressState()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.statePath;
        Component component = getPage().get( componentPath );
        return ( DropDownChoice ) component;
    }
}