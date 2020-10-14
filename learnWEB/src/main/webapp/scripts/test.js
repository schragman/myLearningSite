/*alert("Hallo Michael");*/

function buttonfunc() {
    var myOut = document.getElementById("testPage:testForm:testOut");
    var myButton = document.getElementById("testPage:testForm:testButton");
    var myLink = document.getElementById("testPage:testForm:testLink");
    /*if (myOut.innerText == "Ausgabe Changed")
        myOut.innerText = "";
    else
        myOut.innerText="Ausgabe Changed";*/
    /*location.reload();*/
    /*alert(myOut.innerText);*/
    myLink.innerText="Autosave";
    myLink.click();
}

/*alert("Ciao Michael");*/

/*var intervalID = setInterval(function(){alert("Interval reached");}, 5000);*/
var intervalID = setInterval(buttonfunc, 10000);