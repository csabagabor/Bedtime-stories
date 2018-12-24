$(function () {
    //only save email between different pages
    var ref = document.referrer.toUpperCase();
    //alert(ref);
    //alert(ref.indexOf("LOGIN"));
    if (ref.indexOf("LOGIN") < 0 && ref!=="") {
		setReferrerHeader("") ;
        var url = window.location.href;
        var email = "";
        var position = 0;
        position = url.indexOf('user');
        if (position > 0) {
            email = url.substring(position + 6);
            $('#UserName').val(email);
        }
    }


	function setReferrerHeader(referrerName) {
	  Object.defineProperty(document, "referrer", {
		get: function () { return referrerName; },
	});
}
});
