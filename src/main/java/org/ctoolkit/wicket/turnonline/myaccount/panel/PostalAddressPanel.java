package org.ctoolkit.wicket.turnonline.myaccount.panel;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.ctoolkit.wicket.standard.behavior.VisibleIfModelFalse;
import org.ctoolkit.wicket.standard.markup.autofill.AutofillAddress;
import org.ctoolkit.wicket.standard.markup.autofill.AutofillCity;
import org.ctoolkit.wicket.standard.markup.autofill.AutofillCountry;
import org.ctoolkit.wicket.standard.markup.autofill.AutofillGivenName;
import org.ctoolkit.wicket.standard.markup.autofill.AutofillOff;
import org.ctoolkit.wicket.standard.markup.autofill.AutofillOrganization;
import org.ctoolkit.wicket.standard.markup.autofill.AutofillPostalCode;
import org.ctoolkit.wicket.standard.markup.autofill.AutofillSurname;
import org.ctoolkit.wicket.standard.markup.html.form.ajax.IndicatingAjaxCheckBox;
import org.ctoolkit.wicket.standard.markup.html.formrow.FormRowBehavior;
import org.ctoolkit.wicket.standard.model.I18NResourceModel;
import org.ctoolkit.wicket.turnonline.myaccount.event.ToggleCompanyPersonChangeEvent;
import org.ctoolkit.wicket.turnonline.validator.ZipValidator;

/**
 * Postal address form panel. Must be located within HTML form to post data.
 * Expected model properties:
 * <ul>
 * <li>company &lt;Boolean&gt;</li>
 * <li>businessName &lt;String&gt;</li>
 * <li>firstName &lt;String&gt;</li>
 * <li>lastName &lt;String&gt;</li>
 * <li>street &lt;String&gt;</li>
 * <li>city &lt;String&gt;</li>
 * <li>postcode &lt;String&gt;</li>
 * <li>country &lt;String&gt;</li>
 * </ul>
 * The model instance is being wrapped by {@link CompoundPropertyModel}.
 * <p>
 * Expected i18 resource bundle:
 * <ul>
 * <li>title.postalAddress</li>
 * <li>label.businessName</li>
 * <li>label.firstName</li>
 * <li>label.lastName</li>
 * <li>label.street</li>
 * <li>label.city</li>
 * <li>label.postcode</li>
 * <li>label.country</li>
 * </ul>
 * There is a dedicated property model that renders label based
 * on the evaluated <code>boolean</code> value of company.
 * <ul>
 * <li>true: label.sameCompanyAddress</li>
 * <li>false: label.sameAddress</li>
 * </ul>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@SuppressWarnings( "WeakerAccess" )
public abstract class PostalAddressPanel<T>
        extends GenericPanel<T>
{
    private static final long serialVersionUID = 2507614442540217563L;

    private String businessNamePath;

    private String firstNamePath;

    private String lastNamePath;

    private String streetPath;

    private String cityPath;

    private String postcodePath;

    private String countryPath;

    /**
     * Constructor of postal address form panel instance.
     * By default fields are editable.
     *
     * @param id    the component id
     * @param model the component model
     */
    public PostalAddressPanel( String id, IModel<T> model, IModel<Boolean> visible )
    {
        this( id, model, visible, false );
    }

    /**
     * Constructor of postal address form panel instance.
     *
     * @param id       the component id
     * @param model    the component model
     * @param readOnly the boolean indication whether to render this component as read only
     */
    public PostalAddressPanel( String id,
                               IModel<T> model,
                               final IModel<Boolean> visible,
                               boolean readOnly )
    {
        super( id, new CompoundPropertyModel<>( model ) );
        setOutputMarkupId( true );

        final IModel<Boolean> company = new PropertyModel<>( model, "company" );

        // checkbox to check postal address same
        CheckBox hasPostalAddress = new IndicatingAjaxCheckBox( "hasPostalAddress", visible );
        hasPostalAddress.setLabel( new TitleModel( company ) );
        hasPostalAddress.setVisible( !readOnly );
        hasPostalAddress.add( new OnChangeAjaxBehavior()
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onUpdate( AjaxRequestTarget target )
            {
                target.add( PostalAddressPanel.this );
            }
        } );
        add( hasPostalAddress );

        hasPostalAddress.add( new FormRowBehavior( true ) );
        hasPostalAddress.add( AutofillOff.get() );

        // business firstName
        TextField<String> businessName = new TextField<String>( "businessName" )
        {
            private static final long serialVersionUID = 7086952516840223357L;

            @Override
            protected void onConfigure()
            {
                super.onConfigure();
                Boolean visibleObject = visible.getObject();
                boolean isVisible = visibleObject == null ? false : visibleObject;

                Boolean companyObject = company.getObject();
                boolean isCompany = companyObject == null ? false : companyObject;

                this.setVisible( !isVisible && isCompany );
            }
        };
        businessName.setLabel( new I18NResourceModel( "label.businessName" ) );
        businessName.setEnabled( !readOnly );
        businessName.setRequired( !readOnly );
        add( businessName );

        businessName.add( new FormRowBehavior() );
        businessName.add( AutofillOrganization.get() );

        // first name
        TextField<String> firstName = new TextField<String>( "firstName" )
        {
            private static final long serialVersionUID = 6959658578636824879L;

            @Override
            protected void onConfigure()
            {
                super.onConfigure();
                Boolean visibleObject = visible.getObject();
                boolean isVisible = visibleObject == null ? false : visibleObject;

                Boolean companyObject = company.getObject();
                boolean isCompany = companyObject == null ? false : companyObject;

                this.setVisible( !isVisible && !isCompany );
            }
        };
        firstName.setLabel( new I18NResourceModel( "label.firstName" ) );
        firstName.setEnabled( !readOnly );
        firstName.setRequired( !readOnly );
        add( firstName );

        firstName.add( new FormRowBehavior() );
        firstName.add( AutofillGivenName.get() );

        // last name
        TextField<String> lastName = new TextField<String>( "lastName" )
        {
            private static final long serialVersionUID = 4172820888920886458L;

            @Override
            protected void onConfigure()
            {
                super.onConfigure();
                Boolean visibleObject = visible.getObject();
                boolean isVisible = visibleObject == null ? false : visibleObject;

                Boolean companyObject = company.getObject();
                boolean isCompany = companyObject == null ? false : companyObject;

                this.setVisible( !isVisible && !isCompany );
            }
        };
        lastName.setLabel( new I18NResourceModel( "label.lastName" ) );
        lastName.setEnabled( !readOnly );
        lastName.setRequired( !readOnly );
        add( lastName );

        lastName.add( new FormRowBehavior() );
        lastName.add( AutofillSurname.get() );

        // street
        TextField<String> street = new TextField<>( "street" );
        street.setLabel( new I18NResourceModel( "label.street" ) );
        street.setEnabled( !readOnly );
        street.setRequired( !readOnly );
        add( street );

        street.add( new FormRowBehavior() );
        street.add( AutofillAddress.get() );
        street.add( new VisibleIfModelFalse( visible ) );

        // city
        TextField<String> city = new TextField<>( "city" );
        city.setLabel( new I18NResourceModel( "label.city" ) );
        city.setEnabled( !readOnly );
        city.setRequired( !readOnly );
        add( city );

        city.add( new FormRowBehavior() );
        city.add( AutofillCity.get() );
        city.add( new VisibleIfModelFalse( visible ) );

        // postcode
        TextField<String> postcode = new TextField<>( "postcode" );
        postcode.setLabel( new I18NResourceModel( "label.postcode" ) );
        postcode.setEnabled( !readOnly );
        postcode.setRequired( !readOnly );
        add( postcode );

        postcode.add( new FormRowBehavior() );
        postcode.add( ZipValidator.get() );
        postcode.add( AutofillPostalCode.get() );
        postcode.add( new VisibleIfModelFalse( visible ) );

        // country
        DropDownChoice country = provideCountry( "country" );
        country.setLabel( new I18NResourceModel( "label.country" ) );
        country.setEnabled( !readOnly );
        country.setRequired( !readOnly );
        add( country );
        country.add( new FormRowBehavior() );
        country.add( AutofillCountry.get() );
        country.add( new VisibleIfModelFalse( visible ) );

        this.businessNamePath = businessName.getPageRelativePath();
        this.firstNamePath = firstName.getPageRelativePath();
        this.lastNamePath = lastName.getPageRelativePath();
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

    @Override
    public void onEvent( IEvent<?> event )
    {
        super.onEvent( event );

        if ( event.getPayload() instanceof ToggleCompanyPersonChangeEvent )
        {
            ToggleCompanyPersonChangeEvent payload = ( ToggleCompanyPersonChangeEvent ) event.getPayload();
            payload.getTarget().add( PostalAddressPanel.this );
        }
    }

    /**
     * Adds a behavior modifier to the postal address business name text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the postal address business name text field instance
     */
    public TextField addBusinessName( Behavior... behaviors )
    {
        return ( TextField ) getBusinessName().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the postal address first name text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the postal address first name text field instance
     */
    public TextField addFirstName( Behavior... behaviors )
    {
        return ( TextField ) getFirstName().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the postal address last name text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the postal address last name text field instance
     */
    public TextField addLastName( Behavior... behaviors )
    {
        return ( TextField ) getLastName().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the postal address street text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the postal address street text field instance
     */
    public TextField addStreet( Behavior... behaviors )
    {
        return ( TextField ) getStreet().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the postal address city text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the postal address city text field instance
     */
    public TextField addCity( Behavior... behaviors )
    {
        return ( TextField ) getCity().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the postal address postcode text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the postal address postcode text field instance
     */
    public TextField addPostcode( Behavior... behaviors )
    {
        return ( TextField ) getPostcode().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the postal address country drop down select box component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the postal address country drop down select box instance
     */
    public DropDownChoice addCountry( Behavior... behaviors )
    {
        return ( DropDownChoice ) getCountry().add( behaviors );
    }

    /**
     * Returns the postal address business name text field instance.
     *
     * @return the postal address business name text field instance
     */
    public TextField getBusinessName()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.businessNamePath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the postal person first name text field instance.
     *
     * @return the postal person first name text field instance
     */
    public TextField getFirstName()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.firstNamePath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the postal person last name text field instance.
     *
     * @return the postal person last name text field instance
     */
    public TextField getLastName()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.lastNamePath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the postal address street text field instance.
     *
     * @return the postal address street text field instance
     */
    public TextField getStreet()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.streetPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the postal address city text field instance.
     *
     * @return the postal address city text field instance
     */
    public TextField getCity()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.cityPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the postal address postcode text field instance.
     *
     * @return the postal address postcode text field instance
     */
    public TextField getPostcode()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.postcodePath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the postal address country drop down select box instance.
     *
     * @return the postal address country drop down select box instance
     */
    public DropDownChoice getCountry()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.countryPath;
        Component component = getPage().get( componentPath );
        return ( DropDownChoice ) component;
    }

    private static class TitleModel
            extends AbstractReadOnlyModel<String>
    {
        private static final long serialVersionUID = 4067237872815260428L;

        private final I18NResourceModel falseLabelModel;

        private final I18NResourceModel trueLabelModel;

        private final IModel<Boolean> model;

        TitleModel( IModel<Boolean> model )
        {
            this.model = model;
            this.falseLabelModel = new I18NResourceModel( "label.sameAddress" );
            this.trueLabelModel = new I18NResourceModel( "label.sameCompanyAddress" );
        }

        @Override
        public String getObject()
        {
            Boolean modelObject = model.getObject();
            boolean is = modelObject == null ? false : modelObject;
            return is ? trueLabelModel.getString() : falseLabelModel.getString();
        }

        @Override
        public void detach()
        {
            model.detach();
            falseLabelModel.detach();
            trueLabelModel.detach();

            super.detach();
        }
    }
}