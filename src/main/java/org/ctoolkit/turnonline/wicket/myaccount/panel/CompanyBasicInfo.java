package org.ctoolkit.turnonline.wicket.myaccount.panel;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.ctoolkit.turnonline.wicket.markup.autocomplete.AutocompleteBehavior;
import org.ctoolkit.turnonline.wicket.markup.autocomplete.AutocompleteOff;
import org.ctoolkit.turnonline.wicket.markup.html.form.ajax.IndicatingAjaxCheckBox;
import org.ctoolkit.turnonline.wicket.markup.html.formrow.FormRowBehavior;
import org.ctoolkit.turnonline.wicket.model.I18NResourceModel;
import org.ctoolkit.turnonline.wicket.validator.CompanyIdValidator;
import org.ctoolkit.turnonline.wicket.validator.TaxIdValidator;
import org.ctoolkit.turnonline.wicket.validator.VatIdValidator;

/**
 * Company basic info form. Must be located within HTML form to post data.
 * Expected model properties:
 * <ul>
 * <li>businessName</li>
 * <li>legalForm</li>
 * <li>companyId</li>
 * <li>taxId</li>
 * <li>vatId</li>
 * <li>vatPayer</li>
 * <li>contactCellPhoneNumber</li>
 * </ul>
 * The model instance is being wrapped by {@link CompoundPropertyModel}.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@SuppressWarnings( "WeakerAccess" )
public abstract class CompanyBasicInfo<T>
        extends GenericPanel<T>
{
    private static final long serialVersionUID = -1540918786954705782L;

    private String businessNamePath;

    private String legalFormPath;

    private String companyIdPath;

    private String taxIdPath;

    private String vatIdPath;

    private String vatPayerPath;

    private String contactCellPhoneNumberPath;

    /**
     * Constructor of company basic info panel instance.
     *
     * @param id    the component id
     * @param model the component model
     */
    public CompanyBasicInfo( String id, final IModel<T> model )
    {
        this( id, model, false, false );
    }

    /**
     * Constructor of company basic info panel instance.
     *
     * @param id       the component id
     * @param model    the component model
     * @param readOnly the boolean indication whether this component to be rendered as read only
     */
    public CompanyBasicInfo( String id, final IModel<T> model, boolean readOnly )
    {
        this( id, model, readOnly, false );
    }

    /**
     * Constructor of company basic info panel instance.
     *
     * @param id       the component id
     * @param model    the component model
     * @param readOnly the boolean indication whether this component to be rendered as read only
     * @param required the boolean indication whether this component to be rendered as required
     */
    public CompanyBasicInfo( String id, final IModel<T> model, boolean readOnly, boolean required )
    {
        super( id, new CompoundPropertyModel<>( model ) );

        // business name
        TextField<String> businessName = new TextField<>( "businessName" );
        businessName.setRequired( required );
        businessName.setLabel( new I18NResourceModel( "label.businessName" ) );
        businessName.setEnabled( !readOnly );
        add( businessName );

        businessName.add( new FormRowBehavior() );
        businessName.add( new AutocompleteBehavior( AutocompleteBehavior.Autocomplete.ORGANIZATION ) );

        // legal form
        DropDownChoice legalForm = provideLegalForm( "legalForm" );

        legalForm.setRequired( required );
        legalForm.setLabel( new I18NResourceModel( "label.legalForm" ) );
        legalForm.setEnabled( !readOnly );
        legalForm.setNullValid( false );
        add( legalForm );
        legalForm.add( new FormRowBehavior() );

        // company id
        TextField<String> companyId = new TextField<>( "companyId" );
        companyId.setRequired( required );
        companyId.setLabel( new I18NResourceModel( "label.companyId" ) );
        companyId.setEnabled( !readOnly );
        add( companyId );

        companyId.add( new FormRowBehavior() );
        companyId.add( CompanyIdValidator.get() );
        companyId.add( AutocompleteOff.get() );

        // tax id
        final TextField<String> taxId = new TextField<>( "taxId" );
        taxId.setRequired( required );
        taxId.setLabel( new I18NResourceModel( "label.taxId" ) );
        taxId.setEnabled( !readOnly );
        add( taxId );

        taxId.add( new FormRowBehavior() );
        taxId.add( TaxIdValidator.get() );
        taxId.add( AutocompleteOff.get() );

        // vat id
        final WebMarkupContainer wrapperVatId = new WebMarkupContainer( "wrapper.vatId" );
        final TextField<String> vatId = new TextField<>( "vatId" );
        vatId.setLabel( new I18NResourceModel( "label.vatId" ) );
        vatId.setEnabled( !readOnly );
        wrapperVatId.setOutputMarkupId( true );
        wrapperVatId.add( vatId );

        add( wrapperVatId );
        vatId.add( new FormRowBehavior() );
        vatId.add( VatIdValidator.get() );
        vatId.add( AutocompleteOff.get() );

        // vat payer checkbox
        final CheckBox vatPayer = new IndicatingAjaxCheckBox( "vatPayer" );
        vatPayer.add( new FormRowBehavior() );
        vatPayer.setLabel( new I18NResourceModel( "label.vatPayer" ) );
        vatPayer.setVisible( !readOnly );
        add( vatPayer );

        // contact cell phone number
        TextField<String> contactCellPhoneNumber = new TextField<>( "contactCellPhoneNumber" );
// FIXME contactCellPhoneNumber.setXAutocompleteType( AutocompleteType.X_PHONE_NUMBER );
        contactCellPhoneNumber.setLabel( new I18NResourceModel( "label.contactPhoneNumber" ) );
        contactCellPhoneNumber.setEnabled( !readOnly );
        add( contactCellPhoneNumber );

        contactCellPhoneNumber.add( new FormRowBehavior() );
        contactCellPhoneNumber.add( new AutocompleteBehavior( AutocompleteBehavior.Autocomplete.PHONE_NUMBER ) );

        this.businessNamePath = businessName.getPageRelativePath();
        this.legalFormPath = legalForm.getPageRelativePath();
        this.companyIdPath = companyId.getPageRelativePath();
        this.taxIdPath = taxId.getPageRelativePath();
        this.vatIdPath = vatId.getPageRelativePath();
        this.vatPayerPath = vatPayer.getPageRelativePath();
        this.contactCellPhoneNumberPath = contactCellPhoneNumber.getPageRelativePath();
    }

    /**
     * Provides custom implementation of drop down select box.
     *
     * @param componentId the legal form component id
     * @return the drop down instance
     */
    protected abstract DropDownChoice provideLegalForm( String componentId );

    /**
     * Adds a behavior modifier to the business name text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the business name text field instance
     */
    public TextField addBusinessName( Behavior... behaviors )
    {
        return ( TextField ) getBusinessName().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the legal form drop down select box component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the legal form drop down select box instance
     */
    public DropDownChoice addLegalForm( Behavior... behaviors )
    {
        return ( DropDownChoice ) getLegalForm().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the company id text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the company id text field instance
     */
    public TextField addCompanyId( Behavior... behaviors )
    {
        return ( TextField ) getCompanyId().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the tax id text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the tax id text field instance
     */
    public TextField addTaxId( Behavior... behaviors )
    {
        return ( TextField ) getTaxId().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the vat id text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the vat id text field instance
     */
    public TextField addVatId( Behavior... behaviors )
    {
        return ( TextField ) getVatId().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the vat payer check box component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the vat payer check box instance
     */
    public CheckBox addVatPayer( Behavior... behaviors )
    {
        return ( CheckBox ) getVatPayer().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the contact cell phone number text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the contact cell phone number text field instance
     */
    public TextField addContactCellPhoneNumber( Behavior... behaviors )
    {
        return ( TextField ) getContactCellPhoneNumber().add( behaviors );
    }

    /**
     * Returns the business name text field instance.
     *
     * @return the business name text field instance
     */
    public TextField getBusinessName()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.businessNamePath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the legal form drop down select box instance.
     *
     * @return the legal form drop down select box instance
     */
    public DropDownChoice getLegalForm()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.legalFormPath;
        Component component = getPage().get( componentPath );
        return ( DropDownChoice ) component;
    }

    /**
     * Returns the company id text field instance.
     *
     * @return the company id text field instance
     */
    public TextField getCompanyId()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.companyIdPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the tax id text field instance.
     *
     * @return the tax id text field instance
     */
    public TextField getTaxId()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.taxIdPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the vat id text field instance.
     *
     * @return the vat id text field instance
     */
    public TextField getVatId()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.vatIdPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the vat payer check box instance.
     *
     * @return the vat payer check box instance
     */
    public CheckBox getVatPayer()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.vatPayerPath;
        Component component = getPage().get( componentPath );
        return ( CheckBox ) component;
    }

    /**
     * Returns the contact cell phone number text field instance.
     *
     * @return the contact cell phone number text field instance
     */
    public TextField getContactCellPhoneNumber()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.contactCellPhoneNumberPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }
}
