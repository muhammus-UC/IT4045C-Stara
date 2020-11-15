// Reference: https://firebase.google.com/docs/auth/web/firebaseui

document.addEventListener("DOMContentLoaded", function() {
    // Your web app's Firebase configuration
    var firebaseConfig = {
        apiKey: "AIzaSyDrsgSPBCnkDey0H7nSeNHfkmhJGSuYGos",
        authDomain: "stara-it3048c.firebaseapp.com",
        databaseURL: "https://stara-it3048c.firebaseio.com",
        projectId: "stara-it3048c",
        storageBucket: "stara-it3048c.appspot.com",
        messagingSenderId: "230202569952",
        appId: "1:230202569952:web:67f58ed630334ffc39cc85"
    };

    // Initialize Firebase
    firebase.initializeApp(firebaseConfig);

    // Initialize the FirebaseUI Widget using Firebase.
    var ui = new firebaseui.auth.AuthUI(firebase.auth());

    var uiConfig = {
        callbacks: {
            signInSuccessWithAuthResult: function(authResult, redirectUrl) {
                // User successfully signed in.
                // Redirect to page with user's favorites by setting UID cookie first
                window.location.replace("/set-uid/?uid=" + authResult.user.uid);
                // Return type determines whether we continue the redirect automatically
                // or whether we leave that to developer to handle.
                return false;
            },
            uiShown: function() {
                // The widget is rendered.
                // Hide the loader.
                document.getElementById('loader').style.display = 'none';
            }
        },
        // Prevent redirection to https://www.accountchooser.com
        credentialHelper: 'none',
        // Will use popup for IDP Providers sign-in flow instead of the default, redirect.
        signInFlow: 'popup',
        signInOptions: [
            firebase.auth.EmailAuthProvider.PROVIDER_ID
        ]
    };

    // The start method will wait until the DOM is loaded.
    ui.start('#firebaseui-auth-container', uiConfig);
})
