package org.ctoolkit.wicket.standard.identity;

import mockit.Tested;
import org.testng.annotations.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit testing {@link FirebaseConfig}.
 *
 * @author <a href="mailto:aurel.medvegy@ctoolkit.org">Aurel Medvegy</a>
 */
public class FirebaseConfigTest
{
    @Tested
    private FirebaseConfig tested;

    @Test
    public void getSignInOptionsAsString()
    {
        String clientId = "xxx.apps.googleusercontent.com";
        String scope = "https://www.googleapis.com/auth/plus.login";

        tested.oneTapSignUp( clientId ).google().parameter( "prompt", "select_account" );
        tested.scope( scope );
        tested.facebook().scope( "public_profile" ).scope( "user_friends" );
        tested.github();

        String optionsAsString = tested.getSignInOptionsAsString();

        // testing providers
        assertThat( optionsAsString ).contains( FirebaseConfig.Provider.Google.getValue() );
        assertThat( optionsAsString ).contains( FirebaseConfig.Provider.Facebook.getValue() );
        assertThat( optionsAsString ).contains( FirebaseConfig.Provider.Github.getValue() );
        assertThat( optionsAsString ).doesNotContain( FirebaseConfig.Provider.Twitter.getValue() );
        assertThat( optionsAsString ).doesNotContain( FirebaseConfig.Provider.Email.getValue() );

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