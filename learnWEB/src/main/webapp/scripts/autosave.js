/*alert("Hallo Michael");*/

function buttonfunc() {
    var counterField = document.getElementById("page:counter");
    var counter = parseInt(counterField.innerText);
    if (counter > 0 ) {
        var myLink = document.getElementById("page:showAutosave");
        var short1 = document.getElementById("page:shortDescr1");
        var short2 = document.getElementById("page:shortDescr2");
        /*var myTextarea = document.getElementById("page:lngDescription::field")
        if (myOut.innerText == "Autosave")
            myOut.innerText = " ";
        else
            myOut.innerText="Autosave";*/

        /*window.documents.forms[0].submit();*/
        if (short1 != null || short2 != null) {
            myLink.innerText = "Autosave";
            myLink.click();
        }
    }
}

/*alert("Ciao Michael");*/

/*var intervalID = setInterval(function(){alert("Interval reached");}, 5000);*/
var myTimeout = 590000;
var intervalID = setInterval(buttonfunc, myTimeout);