// Google Test Ad: https://developers.google.com/publisher-tag/samples/display-test-ad

window.googletag = window.googletag || {cmd: []};

googletag.cmd.push(function() {
    googletag
        .defineSlot(
            '/6355419/Travel/Europe/France/Paris', [300, 250], 'banner-ad')
        .addService(googletag.pubads());
    googletag.enableServices();
});

document.addEventListener('DOMContentLoaded', function() {
    googletag.cmd.push(function() {
        googletag.display('banner-ad');
    });
});
