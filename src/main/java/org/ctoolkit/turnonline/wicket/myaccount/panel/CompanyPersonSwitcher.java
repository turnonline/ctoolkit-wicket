package org.ctoolkit.turnonline.wicket.myaccount.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.ctoolkit.turnonline.wicket.markup.autocomplete.AutocompleteOff;
import org.ctoolkit.turnonline.wicket.markup.html.form.ajax.IndicatingAjaxRadioGroup;
import org.ctoolkit.turnonline.wicket.myaccount.event.ToggleCompanyPersonChangeEvent;

/**
 * Company/Person switcher implemented as radio button group. Must be located within HTML form.
 * Switching fires {@link ToggleCompanyPersonChangeEvent} event.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class CompanyPersonSwitcher
        extends GenericPanel<Boolean>
{
    private static final long serialVersionUID = 1L;

    public CompanyPersonSwitcher( String id, IModel<Boolean> model )
    {
        super( id, model );
        setRenderBodyOnly( true );

        // company radio button
        String indicatorId = "company-switcher-indicator";
        RadioGroup<Boolean> companyRadioGroup = new IndicatingAjaxRadioGroup<>( "switcher", indicatorId );

        companyRadioGroup.setModel( model );
        final Radio<Boolean> company = new Radio<>( "1", new Model<>( Boolean.TRUE ) );
        company.add( AutocompleteOff.get() );
        companyRadioGroup.add( company );
        companyRadioGroup.setRenderBodyOnly( false );
        company.add( new AjaxFormSubmitBehavior( "click" )
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit( AjaxRequestTarget target )
            {
                // must be set manually as getDefaultProcessing returns false
                IModel<Boolean> iModel = getModel();
                iModel.setObject( true );
                send( getPage(), Broadcast.BREADTH, new ToggleCompanyPersonChangeEvent( target ) );
            }

            @Override
            public boolean getDefaultProcessing()
            {
                return false;
            }
        } );

        // person radio button
        final Radio<Boolean> person = new Radio<>( "2", new Model<>( Boolean.FALSE ) );
        person.add( AutocompleteOff.get() );
        companyRadioGroup.add( person );
        person.add( new AjaxFormSubmitBehavior( "click" )
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit( AjaxRequestTarget target )
            {
                // must be set manually as getDefaultProcessing returns false
                IModel<Boolean> iModel = getModel();
                iModel.setObject( false );
                send( getPage(), Broadcast.BREADTH, new ToggleCompanyPersonChangeEvent( target ) );
            }

            @Override
            public boolean getDefaultProcessing()
            {
                return false;
            }
        } );

        WebMarkupContainer radioGroup = new WebMarkupContainer( "row-switcher" );
        radioGroup.add( companyRadioGroup );
        add( radioGroup );
    }
}
