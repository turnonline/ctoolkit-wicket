package org.ctoolkit.wicket.turnonline.myaccount.panel;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.ctoolkit.wicket.standard.markup.autofill.AutofillBehavior;
import org.ctoolkit.wicket.standard.markup.html.formrow.FormRowBehavior;
import org.ctoolkit.wicket.standard.model.I18NResourceModel;

/**
 * Personal data form. Must be located within HTML form.
 * Expected model properties:
 * <ul>
 * <li>firstName<String></li>
 * <li>lastName<String></li>
 * </ul>
 * The model instance is being wrapped by {@link CompoundPropertyModel}.
 * <p>
 * Expected i18 resource bundle:
 * <ul>
 * <li>title.basicInfo</li>
 * <li>label.firstName</li>
 * <li>label.lastName</li>
 * </ul>
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class PersonalDataPanel<T>
        extends GenericPanel<T>
{
    private static final long serialVersionUID = 1149426367926037047L;

    /**
     * Constructor of personal address form panel instance.
     * By default fields are editable and not required.
     *
     * @param id    the component id
     * @param model the component model
     */
    public PersonalDataPanel( String id, IModel<T> model )
    {
        this( id, model, false, false );
    }

    /**
     * Constructor of personal address form panel instance.
     *
     * @param id       the component id
     * @param model    the component model
     * @param readOnly the boolean indication whether to render this component as read only
     * @param required the boolean indication whether to render this component as required
     */
    public PersonalDataPanel( String id, IModel<T> model, boolean readOnly, boolean required )
    {
        super( id, new CompoundPropertyModel<>( model ) );

        // firstName
        TextField<String> firstName = new TextField<>( "firstName" );
        firstName.setRequired( required );
        firstName.setEnabled( !readOnly );
        firstName.setLabel( new I18NResourceModel( "label.firstName" ) );
        add( firstName );

        firstName.add( new FormRowBehavior() );
        firstName.add( new AutofillBehavior( AutofillBehavior.Autofill.GIVEN_NAME ) );

        // lastName
        TextField<String> lastName = new TextField<>( "lastName" );
        lastName.setRequired( required );
        lastName.setEnabled( !readOnly );
        lastName.setLabel( new I18NResourceModel( "label.lastName" ) );
        add( lastName );

        lastName.add( new FormRowBehavior() );
        lastName.add( new AutofillBehavior( AutofillBehavior.Autofill.SURNAME ) );
    }
}
