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
 * <li>postalAddressSame &lt;Boolean&gt;</li>
 * <li>postalAddressBusinessName &lt;String&gt;</li>
 * <li>postalAddressName &lt;String&gt;</li>
 * <li>postalAddressSurname &lt;String&gt;</li>
 * <li>postalAddressStreet &lt;String&gt;</li>
 * <li>postalAddressCity &lt;String&gt;</li>
 * <li>postalAddressZip &lt;String&gt;</li>
 * <li>postalAddressState &lt;String&gt;</li>
 * </ul>
 * The model instance is being wrapped by {@link CompoundPropertyModel}.
 * <p>
 * Expected i18 resource bundle:
 * <ul>
 * <li>title.postalAddress</li>
 * <li>label.businessName</li>
 * <li>label.name</li>
 * <li>label.surname</li>
 * <li>label.street</li>
 * <li>label.city</li>
 * <li>label.zip</li>
 * <li>label.state</li>
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

    private String namePath;

    private String surnamePath;

    private String streetPath;

    private String cityPath;

    private String zipPath;

    private String statePath;

    /**
     * Constructor of postal address form panel instance.
     * By default fields are editable.
     *
     * @param id    the component id
     * @param model the component model
     */
    public PostalAddressPanel( String id, IModel<T> model )
    {
        this( id, model, false );
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
                               boolean readOnly )
    {
        super( id, new CompoundPropertyModel<>( model ) );
        setOutputMarkupId( true );

        final IModel<Boolean> company = new PropertyModel<>( model, "company" );
        final IModel<Boolean> visible = new PropertyModel<>( model, "postalAddressSame" );

        // checkbox to check postal address same
        CheckBox postalAddressSame = new IndicatingAjaxCheckBox( "postalAddressSame" );
        postalAddressSame.setLabel( new TitleModel( company ) );
        postalAddressSame.setVisible( !readOnly );
        postalAddressSame.add( new OnChangeAjaxBehavior()
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onUpdate( AjaxRequestTarget target )
            {
                target.add( PostalAddressPanel.this );
            }
        } );
        add( postalAddressSame );

        postalAddressSame.add( new FormRowBehavior( true ) );
        postalAddressSame.add( AutofillOff.get() );

        // business name
        TextField<String> businessName = new TextField<String>( "postalAddressBusinessName" )
        {
            private static final long serialVersionUID = 7086952516840223357L;

            @Override
            protected void onConfigure()
            {
                super.onConfigure();
                this.setVisible( !visible.getObject() && company.getObject() );
            }
        };
        businessName.setLabel( new I18NResourceModel( "label.businessName" ) );
        businessName.setEnabled( !readOnly );
        businessName.setRequired( !readOnly );
        add( businessName );

        businessName.add( new FormRowBehavior() );
        businessName.add( AutofillOrganization.get() );

        // name
        TextField<String> name = new TextField<String>( "postalAddressName" )
        {
            private static final long serialVersionUID = 6959658578636824879L;

            @Override
            protected void onConfigure()
            {
                super.onConfigure();
                this.setVisible( !visible.getObject() && !company.getObject() );
            }
        };
        name.setLabel( new I18NResourceModel( "label.name" ) );
        name.setEnabled( !readOnly );
        name.setRequired( !readOnly );
        add( name );

        name.add( new FormRowBehavior() );
        name.add( AutofillGivenName.get() );

        // surname
        TextField<String> surname = new TextField<String>( "postalAddressSurname" )
        {
            private static final long serialVersionUID = 4172820888920886458L;

            @Override
            protected void onConfigure()
            {
                super.onConfigure();
                this.setVisible( !visible.getObject() && !company.getObject() );
            }
        };
        surname.setLabel( new I18NResourceModel( "label.surname" ) );
        surname.setEnabled( !readOnly );
        surname.setRequired( !readOnly );
        add( surname );

        surname.add( new FormRowBehavior() );
        surname.add( AutofillSurname.get() );

        // street
        TextField<String> street = new TextField<>( "postalAddressStreet" );
        street.setLabel( new I18NResourceModel( "label.street" ) );
        street.setEnabled( !readOnly );
        street.setRequired( !readOnly );
        add( street );

        street.add( new FormRowBehavior() );
        street.add( AutofillAddress.get() );
        street.add( new VisibleIfModelFalse( visible ) );

        // city
        TextField<String> city = new TextField<>( "postalAddressCity" );
        city.setLabel( new I18NResourceModel( "label.city" ) );
        city.setEnabled( !readOnly );
        city.setRequired( !readOnly );
        add( city );

        city.add( new FormRowBehavior() );
        city.add( AutofillCity.get() );
        city.add( new VisibleIfModelFalse( visible ) );

        // zip
        TextField<String> zip = new TextField<>( "postalAddressZip" );
        zip.setLabel( new I18NResourceModel( "label.zip" ) );
        zip.setEnabled( !readOnly );
        zip.setRequired( !readOnly );
        add( zip );

        zip.add( new FormRowBehavior() );
        zip.add( ZipValidator.get() );
        zip.add( AutofillPostalCode.get() );
        zip.add( new VisibleIfModelFalse( visible ) );

        // state
        DropDownChoice state = provideCountry( "postalAddressState" );
        state.setLabel( new I18NResourceModel( "label.state" ) );
        state.setEnabled( !readOnly );
        state.setRequired( !readOnly );
        add( state );
        state.add( new FormRowBehavior() );
        state.add( AutofillCountry.get() );
        state.add( new VisibleIfModelFalse( visible ) );

        this.businessNamePath = businessName.getPageRelativePath();
        this.namePath = name.getPageRelativePath();
        this.surnamePath = surname.getPageRelativePath();
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
    public TextField addPostalAddressBusinessName( Behavior... behaviors )
    {
        return ( TextField ) getPostalAddressBusinessName().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the postal address name text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the postal address name text field instance
     */
    public TextField addPostalAddressName( Behavior... behaviors )
    {
        return ( TextField ) getPostalAddressName().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the postal address surname text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the postal address surname text field instance
     */
    public TextField addPostalAddressSurname( Behavior... behaviors )
    {
        return ( TextField ) getPostalAddressSurname().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the postal address street text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the postal address street text field instance
     */
    public TextField addPostalAddressStreet( Behavior... behaviors )
    {
        return ( TextField ) getPostalAddressStreet().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the postal address city text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the postal address city text field instance
     */
    public TextField addPostalAddressCity( Behavior... behaviors )
    {
        return ( TextField ) getPostalAddressCity().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the postal address zip text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the postal address zip text field instance
     */
    public TextField addPostalAddressZip( Behavior... behaviors )
    {
        return ( TextField ) getPostalAddressZip().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the postal address state drop down select box component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the postal address state drop down select box instance
     */
    public DropDownChoice addPostalAddressState( Behavior... behaviors )
    {
        return ( DropDownChoice ) getPostalAddressState().add( behaviors );
    }

    /**
     * Returns the postal address business name text field instance.
     *
     * @return the postal address business name text field instance
     */
    public TextField getPostalAddressBusinessName()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.businessNamePath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the postal person name text field instance.
     *
     * @return the postal person name text field instance
     */
    public TextField getPostalAddressName()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.namePath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the postal person surname text field instance.
     *
     * @return the postal person surname text field instance
     */
    public TextField getPostalAddressSurname()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.surnamePath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the postal address street text field instance.
     *
     * @return the postal address street text field instance
     */
    public TextField getPostalAddressStreet()
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
    public TextField getPostalAddressCity()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.cityPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the postal address zip text field instance.
     *
     * @return the postal address zip text field instance
     */
    public TextField getPostalAddressZip()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.zipPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the postal address state drop down select box instance.
     *
     * @return the postal address state drop down select box instance
     */
    public DropDownChoice getPostalAddressState()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.statePath;
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
            return model.getObject() ? trueLabelModel.getString() : falseLabelModel.getString();
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