package org.ctoolkit.wicket.turnonline.myaccount.panel;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.ctoolkit.wicket.standard.markup.autofill.AutofillBehavior;
import org.ctoolkit.wicket.standard.markup.autofill.AutofillOff;
import org.ctoolkit.wicket.standard.markup.html.form.ajax.IndicatingAjaxCheckBox;
import org.ctoolkit.wicket.standard.markup.html.formrow.FormRowBehavior;
import org.ctoolkit.wicket.standard.model.I18NResourceModel;
import org.ctoolkit.wicket.turnonline.validator.CompanyIdValidator;
import org.ctoolkit.wicket.turnonline.validator.TaxIdValidator;
import org.ctoolkit.wicket.turnonline.validator.VatIdValidator;

/**
 * The company basic info form. Must be located within HTML form to post data.
 * Expected model properties:
 * <ul>
 * <li>businessName &lt;String&gt;</li>
 * <li>legalForm  &lt;String&gt;</li>
 * <li>companyId &lt;String&gt;</li>
 * <li>taxId &lt;String&gt;</li>
 * <li>vatId &lt;String&gt;</li>
 * <li>vatPayer &lt;Boolean&gt;</li>
 * </ul>
 * The model instance is being wrapped by {@link CompoundPropertyModel}.
 * <p>
 * Expected i18 resource bundle:
 * <ul>
 * <li>title.companyBasicInfo</li>
 * <li>label.businessName</li>
 * <li>label.legalForm</li>
 * <li>label.companyId</li>
 * <li>label.taxId</li>
 * <li>label.vatId</li>
 * <li>label.vatPayer</li>
 * </ul>
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

    /**
     * Constructor of company basic info panel instance.
     * By default fields are editable and not required.
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
     * By default fields are not required.
     *
     * @param id       the component id
     * @param model    the component model
     * @param readOnly the boolean indication whether to render this component as read only
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
     * @param readOnly the boolean indication whether to render this component as read only
     * @param required the boolean indication whether to render this component as required
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
        businessName.add( new AutofillBehavior( AutofillBehavior.Autofill.ORGANIZATION ) );

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
        companyId.add( AutofillOff.get() );

        // tax id
        final TextField<String> taxId = new TextField<>( "taxId" );
        taxId.setRequired( required );
        taxId.setLabel( new I18NResourceModel( "label.taxId" ) );
        taxId.setEnabled( !readOnly );
        add( taxId );

        taxId.add( new FormRowBehavior() );
        taxId.add( TaxIdValidator.get() );
        taxId.add( AutofillOff.get() );

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
        vatId.add( AutofillOff.get() );

        // vat payer checkbox
        final CheckBox vatPayer = new IndicatingAjaxCheckBox( "vatPayer" );
        vatPayer.add( new FormRowBehavior() );
        vatPayer.setLabel( new I18NResourceModel( "label.vatPayer" ) );
        vatPayer.setVisible( !readOnly );
        add( vatPayer );

        this.businessNamePath = businessName.getPageRelativePath();
        this.legalFormPath = legalForm.getPageRelativePath();
        this.companyIdPath = companyId.getPageRelativePath();
        this.taxIdPath = taxId.getPageRelativePath();
        this.vatIdPath = vatId.getPageRelativePath();
        this.vatPayerPath = vatPayer.getPageRelativePath();
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
}
