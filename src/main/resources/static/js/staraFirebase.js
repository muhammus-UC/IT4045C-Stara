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

    // Initialize Firebase Analytics
    firebase.analytics();
})
