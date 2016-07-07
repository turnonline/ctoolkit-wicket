package org.ctoolkit.turnonline.wicket.myaccount.panel;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.ctoolkit.turnonline.wicket.markup.autofill.AutofillBehavior;
import org.ctoolkit.turnonline.wicket.markup.html.formrow.FormRowBehavior;
import org.ctoolkit.turnonline.wicket.model.I18NResourceModel;

/**
 * Personal data form. Must be located within HTML form.
 * Expected model properties:
 * <ul>
 * <li>name<String></li>
 * <li>surname<String></li>
 * </ul>
 * The model instance is being wrapped by {@link CompoundPropertyModel}.
 * <p>
 * Expected i18 resource bundle:
 * <ul>
 * <li>title.basicInfo</li>
 * <li>label.name</li>
 * <li>label.surname</li>
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

        // name
        TextField<String> name = new TextField<>( "name" );
        name.setRequired( required );
        name.setEnabled( !readOnly );
        name.setLabel( new I18NResourceModel( "label.name" ) );
        add( name );

        name.add( new FormRowBehavior() );
        name.add( new AutofillBehavior( AutofillBehavior.Autofill.GIVEN_NAME ) );

        // surname
        TextField<String> surname = new TextField<>( "surname" );
        surname.setRequired( required );
        surname.setEnabled( !readOnly );
        surname.setLabel( new I18NResourceModel( "label.surname" ) );
        add( surname );

        surname.add( new FormRowBehavior() );
        surname.add( new AutofillBehavior( AutofillBehavior.Autofill.SURNAME ) );
    }
}
