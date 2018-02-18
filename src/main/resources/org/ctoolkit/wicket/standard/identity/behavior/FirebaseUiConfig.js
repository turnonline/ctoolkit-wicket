// FirebaseUI config.
var uiConfig = {
    callbacks: {
        // Called when the user has been successfully signed in.
        signInSuccess: function ( currentUser, credential, redirectUrl ) {
            currentUser.getIdToken().then( function ( accessToken ) {
                // ftoken cookie at server will be used to validate signed in user
                setCookie( "ftoken", accessToken, 3600000 );
            } );
            // true to continue the redirect automatically
            return true;
        }
    },
    credentialHelper: [${credentialHelper}],
    signInFlow: '${signInFlow}',
    signInSuccessUrl: '${signInSuccessUrl}',
    signInOptions: [
        ${signInOptions}
    ],
    // Terms of service url.
    tosUrl: '${termsUrl}'
};

// Initialize the FirebaseUI Widget using Firebase.
var ui = new firebaseui.auth.AuthUI( firebase.auth() );
// The start method will wait until the DOM is loaded.
ui.start( '#firebaseui-auth-container', uiConfig );

function setCookie( cname, cvalue, exdays )
{
    var d = new Date();
    d.setTime( d.getTime() + exdays );
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}
