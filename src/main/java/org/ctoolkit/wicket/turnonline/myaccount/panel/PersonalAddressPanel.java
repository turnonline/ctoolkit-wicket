package org.ctoolkit.wicket.turnonline.myaccount.panel;

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
 * The personal address form panel. Must be located within HTML form to post data.
 * Expected model properties:
 * <ul>
 * <li>personalAddressStreet &lt;String&gt;</li>
 * <li>personalAddressCity &lt;String&gt;</li>
 * <li>personalAddressZip &lt;String&gt;</li>
 * <li>personalAddressState &lt;String&gt;</li>
 * </ul>
 * The model instance is being wrapped by {@link CompoundPropertyModel}.
 * <p>
 * Expected i18 resource bundle:
 * <ul>
 * <li>title.address</li>
 * <li>label.street</li>
 * <li>label.city</li>
 * <li>label.zip</li>
 * <li>label.state</li>
 * </ul>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@SuppressWarnings( "WeakerAccess" )
public abstract class PersonalAddressPanel<T>
        extends GenericPanel<T>
{
    private static final long serialVersionUID = 3308357904788269745L;

    private String personalAddressStreetPath;

    private String personalAddressCityPath;

    private String personalAddressZipPath;

    private String personalAddressStatePath;

    /**
     * Constructor of personal address form panel instance.
     * By default fields are editable and not required.
     *
     * @param id    the component id
     * @param model the component model
     */
    public PersonalAddressPanel( String id, IModel<T> model )
    {
        this( id, model, false, false );
    }

    /**
     * Constructor of personal address form panel instance.
     * By default fields are not required.
     *
     * @param id       the component id
     * @param model    the component model
     * @param readOnly the boolean indication whether to render this component as read only
     */
    public PersonalAddressPanel( String id, IModel<T> model, boolean readOnly )
    {
        this( id, model, readOnly, false );
    }

    /**
     * Constructor of personal address form panel instance.
     *
     * @param id       the component id
     * @param model    the component model
     * @param readOnly the boolean indication whether to render this component as read only
     * @param required the boolean indication whether to render this component as required
     */
    public PersonalAddressPanel( String id,
                                 IModel<T> model,
                                 boolean readOnly,
                                 boolean required )
    {
        super( id, new CompoundPropertyModel<>( model ) );

        // street
        TextField<String> street = new TextField<>( "personalAddressStreet" );
        street.setRequired( required );
        street.setLabel( new I18NResourceModel( "label.street" ) );
        street.setEnabled( !readOnly );
        add( street );

        street.add( new FormRowBehavior() );
        street.add( AutofillAddress.get() );

        // city
        TextField<String> city = new TextField<>( "personalAddressCity" );
        city.setRequired( required );
        city.setLabel( new I18NResourceModel( "label.city" ) );
        city.setEnabled( !readOnly );
        add( city );

        city.add( new FormRowBehavior() );
        city.add( AutofillCity.get() );

        // zip
        TextField<String> zip = new TextField<>( "personalAddressZip" );
        zip.setRequired( required );
        zip.setLabel( new I18NResourceModel( "label.zip" ) );
        zip.setEnabled( !readOnly );
        add( zip );

        zip.add( new FormRowBehavior() );
        zip.add( ZipValidator.get() );
        zip.add( AutofillPostalCode.get() );

        // state
        DropDownChoice state = provideCountry( "personalAddressState" );
        state.setRequired( required );
        state.setLabel( new I18NResourceModel( "label.state" ) );
        state.setEnabled( !readOnly );
        state.setNullValid( false );
        add( state );

        state.add( new FormRowBehavior() );
        state.add( AutofillCountry.get() );

        this.personalAddressStreetPath = street.getPageRelativePath();
        this.personalAddressCityPath = city.getPageRelativePath();
        this.personalAddressZipPath = zip.getPageRelativePath();
        this.personalAddressStatePath = state.getPageRelativePath();
    }

    /**
     * Provides custom implementation of country drop down select box.
     *
     * @param componentId the country component id
     * @return the drop down instance
     */
    protected abstract DropDownChoice provideCountry( String componentId );

    /**
     * Adds a behavior modifier to the personal address street text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the personal address street text field instance
     */
    public TextField addPersonalAddressStreet( Behavior... behaviors )
    {
        return ( TextField ) getPersonalAddressStreet().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the personal address city text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the personal address city text field instance
     */
    public TextField addPersonalAddressCity( Behavior... behaviors )
    {
        return ( TextField ) getPersonalAddressCity().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the personal address zip text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the personal address zip text field instance
     */
    public TextField addPersonalAddressZip( Behavior... behaviors )
    {
        return ( TextField ) getPersonalAddressZip().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the personal address state drop down select box component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the personal address state drop down select box instance
     */
    public DropDownChoice addPersonalAddressState( Behavior... behaviors )
    {
        return ( DropDownChoice ) getPersonalAddressState().add( behaviors );
    }

    /**
     * Returns the personal address street text field instance.
     *
     * @return the personal address street text field instance
     */
    public TextField getPersonalAddressStreet()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.personalAddressStreetPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the personal address city text field instance.
     *
     * @return the personal address city text field instance
     */
    public TextField getPersonalAddressCity()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.personalAddressCityPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the personal address zip text field instance.
     *
     * @return the personal address zip text field instance
     */
    public TextField getPersonalAddressZip()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.personalAddressZipPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the personal address state drop down select box instance.
     *
     * @return the personal address state drop down select box instance
     */
    public DropDownChoice getPersonalAddressState()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.personalAddressStatePath;
        Component component = getPage().get( componentPath );
        return ( DropDownChoice ) component;
    }
}

