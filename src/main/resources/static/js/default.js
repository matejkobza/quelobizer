jQuery(document).ready(function () {
    setInterval(function() {
        let url = "/lyrics";
        if (isTest) {
            url = "/test/lyrics"
        }
        jQuery.get(url, function(data) {
            jQuery('.content').html(data);
        });
    }, refreshInterval);
});