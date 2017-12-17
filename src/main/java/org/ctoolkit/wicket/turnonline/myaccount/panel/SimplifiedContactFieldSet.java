package org.ctoolkit.wicket.turnonline.myaccount.panel;

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
import org.ctoolkit.wicket.standard.markup.autofill.AutofillOff;
import org.ctoolkit.wicket.standard.markup.html.form.ajax.IndicatingAjaxCheckBox;
import org.ctoolkit.wicket.standard.markup.html.formrow.FormRowBehavior;
import org.ctoolkit.wicket.standard.model.I18NResourceModel;

/**
 * The simplified contact person fieldset. Must be located within HTML form to post data.
 * Expected model properties:
 * <ul>
 * <li>prefix &lt;String&gt;</li>
 * <li>firstName  &lt;String&gt;</li>
 * <li>lastName &lt;String&gt;</li>
 * <li>suffix &lt;String&gt;</li>
 * <li>phone &lt;String&gt;</li>
 * <li>email &lt;Boolean&gt;</li>
 * </ul>
 * The model instance is being wrapped by {@link CompoundPropertyModel}.
 * <p>
 * Expected i18 resource bundle:
 * <ul>
 * <li>title.contactPerson</li>
 * <li>label.show</li>
 * <li>label.prefix</li>
 * <li>label.firstName</li>
 * <li>label.lastName</li>
 * <li>label.suffix</li>
 * <li>label.phone</li>
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

    private String firstNamePath;

    private String lastNamePath;

    private String suffixPath;

    private String phonePath;

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
        TextField<String> contactPrefix = new TextField<String>( "prefix" )
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

        // contact person first name
        TextField<String> firstName = new TextField<String>( "firstName" )
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isVisible()
            {
                return isExpanded();
            }
        };

        firstName.setLabel( new I18NResourceModel( "label.firstName" ) );
        add( firstName );

        firstName.add( new FormRowBehavior() );
        firstName.add( AutofillOff.get() );

        // contact person last last name
        TextField<String> lastName = new TextField<String>( "lastName" )
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isVisible()
            {
                return isExpanded();
            }
        };

        lastName.setLabel( new I18NResourceModel( "label.lastName" ) );
        add( lastName );

        lastName.add( new FormRowBehavior() );
        lastName.add( AutofillOff.get() );

        // contact suffix
        TextField<String> contactSuffix = new TextField<String>( "suffix" )
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
        TextField<String> phoneNumber = new TextField<String>( "phone" )
        {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isVisible()
            {
                return isExpanded();
            }
        };

        phoneNumber.setLabel( new I18NResourceModel( "label.phoneNumber" ) );
        add( phoneNumber );

        phoneNumber.add( new FormRowBehavior() );
        phoneNumber.add( AutofillOff.get() );

        // contact email
        TextField<String> contactEmail = new TextField<String>( "email" )
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
        this.firstNamePath = firstName.getPageRelativePath();
        this.lastNamePath = lastName.getPageRelativePath();
        this.suffixPath = contactSuffix.getPageRelativePath();
        this.phonePath = phoneNumber.getPageRelativePath();
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
        if ( getFirstName().getModelObject() == null
                && getLastName().getModelObject() == null
                && getPhoneNumber().getModelObject() == null
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
    public TextField addPrefix( Behavior... behaviors )
    {
        return ( TextField ) getPrefix().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the contact person first name text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the contact person first name text field instance
     */
    public TextField addFirstName( Behavior... behaviors )
    {
        return ( TextField ) getFirstName().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the contact person last name text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the contact person last name text field instance
     */
    public TextField addLastName( Behavior... behaviors )
    {
        return ( TextField ) getLastName().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the contact suffix text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the contact suffix text field instance
     */
    public TextField addSuffix( Behavior... behaviors )
    {
        return ( TextField ) getSuffix().add( behaviors );
    }

    /**
     * Adds a behavior modifier to the contact phone number text field component.
     *
     * @param behaviors the behavior modifier(s) to be added
     * @return the contact phone number text field instance
     */
    public TextField addPhoneNumber( Behavior... behaviors )
    {
        return ( TextField ) getPhoneNumber().add( behaviors );
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
    public TextField getPrefix()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.prefixPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the contact person first name text field instance.
     *
     * @return the contact person first name text field instance
     */
    public TextField getFirstName()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.firstNamePath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the contact person last name text field instance.
     *
     * @return the contact person last name text field instance
     */
    public TextField getLastName()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.lastNamePath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the contact suffix text field instance.
     *
     * @return the contact suffix text field instance
     */
    public TextField getSuffix()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.suffixPath;
        Component component = getPage().get( componentPath );
        return ( TextField ) component;
    }

    /**
     * Returns the contact phone number text field instance.
     *
     * @return the contact phone number text field instance
     */
    public TextField getPhoneNumber()
    {
        String componentPath = this.getPageRelativePath() + ":" + this.phonePath;
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
