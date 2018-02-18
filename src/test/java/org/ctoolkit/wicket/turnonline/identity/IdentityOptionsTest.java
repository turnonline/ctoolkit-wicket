package org.ctoolkit.wicket.turnonline.identity;

import org.testng.annotations.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit testing {@link IdentityOptions}.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class IdentityOptionsTest
{
    @Test
    public void getSignInOptionsAsString()
    {
        IdentityOptions config = new IdentityOptions();

        String clientId = "xxx.apps.googleusercontent.com";
        String scope = "https://www.googleapis.com/auth/plus.login";

        config.oneTapSignUp( clientId ).google().parameter( "prompt", "select_account" );
        config.scope( scope );
        config.facebook().scope( "public_profile" ).scope( "user_friends" );
        config.github();

        String optionsAsString = config.getSignInOptionsAsString();

        // testing providers
        assertThat( optionsAsString ).contains( IdentityOptions.Provider.Google.getValue() );
        assertThat( optionsAsString ).contains( IdentityOptions.Provider.Facebook.getValue() );
        assertThat( optionsAsString ).contains( IdentityOptions.Provider.Github.getValue() );
        assertThat( optionsAsString ).doesNotContain( IdentityOptions.Provider.Twitter.getValue() );
        assertThat( optionsAsString ).doesNotContain( IdentityOptions.Provider.Email.getValue() );

        // testing clientId
        assertThat( optionsAsString ).contains( clientId );

        // testing custom parameters
        assertThat( optionsAsString ).contains( "prompt" );
        assertThat( optionsAsString ).contains( "select_account" );

        // testing scopea
        assertThat( optionsAsString ).contains( scope );
        assertThat( optionsAsString ).contains( "public_profile" );
        assertThat( optionsAsString ).contains( "user_friends" );
    }
}