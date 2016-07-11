package org.ctoolkit.turnonline.wicket.myaccount.panel;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.ctoolkit.turnonline.wicket.markup.autofill.AutofillOff;
import org.ctoolkit.turnonline.wicket.markup.html.form.ajax.IndicatingAjaxCheckBox;
import org.ctoolkit.turnonline.wicket.markup.html.formrow.FormRowBehavior;
import org.ctoolkit.turnonline.wicket.model.I18NResourceModel;

/**
 * The simplified contact person fieldset. Must be located within HTML form to post data.
 * Expected model properties:
 * <ul>
 * <li>contactPrefix &lt;String&gt;</li>
 * <li>contactPersonName  &lt;String&gt;</li>
 * <li>contactPersonSurname &lt;String&gt;</li>
 * <li>contactSuffix &lt;String&gt;</li>
 * <li>contactCellPhoneNumber &lt;String&gt;</li>
 * <li>contactEmail &lt;Boolean&gt;</li>
 * </ul>
 * The model instance is being wrapped by {@link CompoundPropertyModel}.
 * <p>
 * Expected i18 resource bundle:
 * <ul>
 * <li>title.contactPerson</li>
 * <li>label.show</li>
 * <li>label.prefix</li>
 * <li>label.name</li>
 * <li>label.surname</li>
 * <li>label.suffix</li>
 * <li>label.cellPhoneNumber</li>
 * <li>label.email</li>
 * </ul>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
@SuppressWarnings( "WeakerAccess" )
public class SimplifiedContactFieldSet<T>
        extends GenericPanel<T>
{
    private static final long serialVersionUID = 1L;

    private String visibilityPath;

    private String prefixPath;

    private String namePath;

    private String surnamePath;

    private String suffixPath;

    private String cellPhoneNumberPath;

    private String emailPath;

    private boolean expanded;

    public SimplifiedContactFieldSet( String id, final IModel<T> model )
    {
        super( id, new CompoundPropertyModel<>( model ) );
        setOutputMarkupId( true );

        // check box to expand/collapse fieldset
        PropertyModel<Boolean> expandedModel = PropertyModel.of( this, "expanded" );
        CheckBox visibility = new IndicatingAjaxCheckBox( "expanded", expandedModel )
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isVisible()
            {
                return SimplifiedContactFieldSet.this.isEnabled();
            }
        };

        visibility.setLabel( new I18NResourceModel( "label.show" ) );
        visibility.add( new OnChangeAjaxBehavior()
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onUpdate( AjaxRequestTarget target )
            {
                target.add( SimplifiedContactFieldSet.this );
            }
        } );

        add( visibility );

        visibility.add( new FormRowBehavior( true ) );
        visibility.add( AutofillOff.get() );

        // contact prefix
        TextField<String> contactPrefix = new TextField<String>( "contactPrefix" )
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isVisible()
            {
                return isExpanded();
            }
        };

        contactPrefix.setLabel( new I18NResourceModel( "label.prefix" ) );
        add( contactPrefix );

        contactPrefix.add( new FormRowBehavior() );
        contactPrefix.add( AutofillOff.get() );

        // contact person name
        TextField<String> name = new TextField<String>( "contactPersonName" )
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isVisible()
            {
                return isExpanded();
            }
        };

        name.setLabel( new I18NResourceModel( "label.name" ) );
        add( name );

        name.add( new FormRowBehavior() );
        name.add( AutofillOff.get() );

        // contact person surname
        TextField<String> surname = new TextField<String>( "contactPersonSurname" )
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isVisible()
            {
                return isExpanded();
            }
        };

        surname.setLabel( new I18NResourceModel( "label.surname" ) );
        add( surname );

        surname.add( new FormRowBehavior() );
        surname.add( AutofillOff.get() );

        // contact suffix
        TextField<String> contactSuffix = new TextField<String>( "contactSuffix" )
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isVisible()
            {
                return isExpanded();
            }
        };

        contactSuffix.setLabel( new I18NResourceModel( "label.suffix" ) );
        add( contactSuffix );

        contactSuffix.add( new FormRowBehavior() );
        contactSuffix.add( AutofillOff.get() );

        // cell phone number
        TextField<String> cellPhoneNumber = new TextField<String>( "contactCellPhoneNumber" )
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isVisible()
            {
                return isExpanded();
            }
        };

        cellPhoneNumber.setLabel( new I18NResourceModel( "label.cellPhoneNumber" ) );
        add( cellPhoneNumber );

        cellPhoneNumber.add( new FormRowBehavior() );
        cellPhoneNumber.add( AutofillOff.get() );

        // contact email
        TextField<String> contactEmail = new TextField<String>( "contactEmail" )
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isVisible()
            {
                return isExpanded();
            }
        };

        contactEmail.setLabel( new I18NResourceModel( "label.email" ) );
        add( contactEmail );

        contactEmail.add( new FormRowBehavior() );
        contactEmail.add( EmailAddressValidator.getInstance() );
        contactEmail.add( AutofillOff.get() );

        this.visibilityPath = visibility.getPageRelativePath();
        this.prefixPath = contactPrefix.getPageRelativePath();
        this.namePath = name.getPageRelativePath();
        this.surnamePath = surname.getPageRelativePath();
        this.suffixPath = contactSuffix.getPageRelativePath();
        this.cellPhoneNumberPath = cellPhoneNumber.getPageRelativePath();
        this.emailPath = contactEmail.getPageRelativePath();
    }

    public boolean isExpanded()
    {
        return expanded;
    }

    public void setExpanded( boolean expanded )
    {
        this.expanded = expanded;
    }

    /**
     * Returns the boolean indication whether this form has any value.
     *
     * @return true if at least one the input has a value
     */
    public boolean isEdited()
    {
        boolean edited = true;
        if ( getContactPersonName().getModelObject() == null
                && getContactPersonSurname().getModelObject() == null
                && getContactCellPhoneNumber().getModelObject() == null
                && getContactEmail().getModelObject() == null )
        {
            edited = false;
        }

        return edited;
    }

    /**
     * Adds a behavior modifier to the visibility check box component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the visibility check box instance
     */
    public CheckBox addVisibility( Behavior... behaviors )
    {
        return ( CheckBox ) getVisibility().add( behaviors );
    }


    /**
     * Adds a behavior modifier to the contact prefix text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the contact prefix text field instance
     */
    public TextField addContactPrefix( Behavior... behaviors )
    {
        return ( TextField ) getContactPrefix().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the contact person name text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the contact person name text field instance
     */
    public TextField addContactPersonName( Behavior... behaviors )
    {
        return ( TextField ) getContactPersonName().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the contact person surname text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the contact person surname text field instance
     */
    public TextField addContactPersonSurname( Behavior... behaviors )
    {
        return ( TextField ) getContactPersonSurname().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the contact suffix text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the contact suffix text field instance
     */
    public TextField addContactSuffix( Behavior... behaviors )
    {
        return ( TextField ) getContactSuffix().add( behaviors );
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
     * Adds a behavior modifier to the contact email text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the contact email text field instance
     */
    public TextField addContactEmail( Behavior... behaviors )
    {
        return ( TextField ) getContactEmail().add( behaviors );
    }

    /**
     * Returns the visibility check box instance.
     *
     * @return the visibility check box instance
     */
    public CheckBox getVisibility()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.visibilityPath;
        Component component = getPage().get( componentPath );
        return ( CheckBox ) component;
    }

    /**
     * Returns the contact prefix text field instance.
     *
     * @return the contact prefix text field instance
     */
    public TextField getContactPrefix()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.prefixPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the contact person name text field instance.
     *
     * @return the contact person name text field instance
     */
    public TextField getContactPersonName()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.namePath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the contact person surname text field instance.
     *
     * @return the contact person surname text field instance
     */
    public TextField getContactPersonSurname()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.surnamePath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the contact suffix text field instance.
     *
     * @return the contact suffix text field instance
     */
    public TextField getContactSuffix()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.suffixPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the contact cell phone number text field instance.
     *
     * @return the contact cell phone number text field instance
     */
    public TextField getContactCellPhoneNumber()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.cellPhoneNumberPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the contact email text field instance.
     *
     * @return the contact email text field instance
     */
    public TextField getContactEmail()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.emailPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }
}
