/**
 * Configure Feature Tour for Stara
 * Uses Intro.js library (https://github.com/usablica/intro.js)
 */
document.addEventListener("DOMContentLoaded", function () {
    // Show Feature Tour on initial load if tourCookie is not set
    var tourCookie = getCookie("tour");
    if (tourCookie !== "true") {
        introJs().start();
        setCookie("tour", "true");
    }

    // Show Feature Tour if user clicks #btnTour.
    document.getElementById("btnTour").addEventListener("click", function () {
        introJs().start();
    });
});

/**
 * JS Functions to make handling cookies easier
 * Reference: https://www.w3schools.com/js/js_cookies.asp
 */
// Set a cookie that is removed when the user closes the browser
function setCookie(cname, cvalue) {
    document.cookie = cname + "=" + cvalue + ";path=/";
}

// Get cookie with the given name
function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
