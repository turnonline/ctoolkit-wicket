package org.ctoolkit.wicket.turnonline.myaccount.panel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.ctoolkit.wicket.standard.markup.autofill.AutofillAddress;
import org.ctoolkit.wicket.standard.markup.autofill.AutofillCity;
import org.ctoolkit.wicket.standard.markup.autofill.AutofillCountry;
import org.ctoolkit.wicket.standard.markup.autofill.AutofillPostalCode;
import org.ctoolkit.wicket.standard.markup.html.formrow.FormRowBehavior;
import org.ctoolkit.wicket.standard.model.I18NResourceModel;
import org.ctoolkit.wicket.turnonline.validator.ZipValidator;

/**
 * The company address form panel. Must be located within HTML form to post data.
 * Expected model properties:
 * <ul>
 * <li>street &lt;String&gt;</li>
 * <li>city &lt;String&gt;</li>
 * <li>postcode &lt;String&gt;</li>
 * <li>country &lt;String&gt;</li>
 * </ul>
 * The model instance is being wrapped by {@link CompoundPropertyModel}.
 * <p>
 * Expected i18 resource bundle:
 * <ul>
 * <li>title.companyAddress</li>
 * <li>label.street</li>
 * <li>label.city</li>
 * <li>label.postcode</li>
 * <li>label.country</li>
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

    private String postcodePath;

    private String countryPath;

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

        // postcode
        TextField<String> postcode = new TextField<>( "postcode" );
        postcode.setLabel( new I18NResourceModel( "label.postcode" ) );
        postcode.setEnabled( !readOnly );
        postcode.setRequired( required );
        add( postcode );

        postcode.add( new FormRowBehavior() );
        postcode.add( ZipValidator.get() );
        postcode.add( AutofillPostalCode.get() );

        // country
        DropDownChoice country = provideCountry( "country" );
        country.setRequired( required );
        country.setLabel( new I18NResourceModel( "label.country" ) );
        country.setEnabled( !readOnly );
        add( country );

        country.add( new FormRowBehavior() );
        country.add( AutofillCountry.get() );

        this.streetPath = street.getPageRelativePath();
        this.cityPath = city.getPageRelativePath();
        this.postcodePath = postcode.getPageRelativePath();
        this.countryPath = country.getPageRelativePath();
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
    public TextField addStreet( Behavior... behaviors )
    {
        return ( TextField ) getStreet().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the company address city text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the company address city text field instance
     */
    public TextField addCity( Behavior... behaviors )
    {
        return ( TextField ) getCity().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the company address postcode text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the company address postcode text field instance
     */
    public TextField addPostcode( Behavior... behaviors )
    {
        return ( TextField ) getPostcode().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the company address country drop down select box component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the company address country drop down select box instance
     */
    public DropDownChoice addCountry( Behavior... behaviors )
    {
        return ( DropDownChoice ) getCountry().add( behaviors );
    }

    /**
     * Returns the company address street text field instance.
     *
     * @return the company address street text field instance
     */
    public TextField getStreet()
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
    public TextField getCity()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.cityPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the company address postcode text field instance.
     *
     * @return the company address postcode text field instance
     */
    public TextField getPostcode()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.postcodePath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the company address country drop down select box instance.
     *
     * @return the company address country drop down select box instance
     */
    public DropDownChoice getCountry()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.countryPath;
        Component component = getPage().get( componentPath );
        return ( DropDownChoice ) component;
    }
}