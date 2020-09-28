function handleSubmit(args, dialog) {
    var jqDialog = jQuery('#' + dialog);
    if (args.validationFailed) {
        jqDialog.effect('shake', {times: 3}, 100);
    } else {
        PF(dialog).hide();
    }
}

function successmmFn2(result) {

    if (result[1] != null && result[0] != null) {
        var blob = b64toBlob(result[1], result[0]);
        var fileURL = URL.createObjectURL(blob);
        openWindow(fileURL, "file." + result[2], "", "");
    } else {
        alert("reload file please");
        // alert("<spring:message code="reloading.file"/>");
    }
}

function b64toBlob(b64Data, contentType, sliceSize) {

    var contentType2 = contentType + "";
    sliceSize = sliceSize || 512;

    var byteCharacters = atob(b64Data);
    var byteArrays = [];

    for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
        var slice = byteCharacters.slice(offset, offset + sliceSize);

        var byteNumbers = new Array(slice.length);
        for (var i = 0; i < slice.length; i++) {
            byteNumbers[i] = slice.charCodeAt(i);
        }

        var byteArray = new Uint8Array(byteNumbers);

        byteArrays.push(byteArray);
    }
    var blob = new Blob(byteArrays, {type: contentType2});
    return blob;
}

function openWindow(url, name, width, height) {
    var defaultWidth = screen.width * 2 / 3;
    var defaultHeight = screen.height * 2 / 3;
    if (width != "") {
        defaultWidth = width;
    }
    if (height != "") {
        defaultHeight = height;
    }
    var left = (screen.width / 2) * (1 / 2);
    var features = "";
    // default values
    features += "toolbar=0,";
    features += "location=0,";
    features += "directories=0,";
    features += "status=0,";
    features += "menubar=0,";
    features += "scrollbars=1,";
    features += "resizable=1,";
    features += "width=" + defaultWidth + ",";
    features += "height=" + defaultHeight + ",";
    features += "top=" + top + ",";
    features += "left=" + left;
    var myWindow = document.open(url, name, features);
    myWindow.focus();
}
// qaysar added this to validate input to enter number only

function validateNumberOnly(evt) {
	var theEvent = evt || window.event;
	var key = theEvent.keyCode || theEvent.which;
	key = String.fromCharCode(key);
	var regex = /[0-9]|\./;
	if (!regex.test(key)) {
		theEvent.returnValue = false;
		if (theEvent.preventDefault)
			theEvent.preventDefault();
	}
}

function openImage(p1) {
	alert("1-------"+p1);
	var contextPath = "${pageContext.request.contextPath}";
	newwindow = window.open(contextPath + getContextPath()
			+ "/DisplayImage?imagPath=C:" + p1, "Doc window",
			'height=700,width=900');
	if (window.focus) {
		newwindow.focus();
	}
	return false;
}
function getContextPath() {
	return window.location.pathname.substring(0,
			window.location.pathname.indexOf("/", 2));
}
function mmy(){
	alert("HAllow yaghi");
}