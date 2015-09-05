/**
 * 
 */
$(document).ready(function() {
	$("div#mytest").click(function(e){
        
        //get the form data and then serialize that
        dataString = "nothingToSend";
        
        //get the form data using another method 		
//        var countryCode = $("input#countryCode").val(); 
//        dataString = "countryCode=" + countryCode;
        
        //make the AJAX request, dataType is set to json
        //meaning we are expecting JSON data in response from the server
        $.ajax({
            type: "POST",
            url: "Filestructure",
            data: dataString,
            dataType: "json",
            
            //if received a response from the server
            success: function( data, textStatus, jqXHR) {
                //our country code was correct so we have some information to display
                 if(data.success){
                     $("#ajaxResponse").html("");
                     $("#ajaxResponse").append("<p><b>Path:</b> " + data.filestructure.path + "<br/>");
                     $("#ajaxResponse").append("<b>FirstEntry:</b> " + data.filestructure.firstEntry + "<br/>");
                     $("#ajaxResponse").append("<b>personalNote:</b> " + data.filestructure.personalnote + "<br/>");
                     $("#ajaxResponse").append('<b>theimage:</b><img alt="'+data.filestructure.theimageasString+'" src="data:image/jpg;base64,' + data.filestructure.theimageasString +'"/>');
//                     $("#ajaxResponse").append("<b>IMAGE STRING:</b> " + data.filestructure.theimageasString + "<br/><br/><br/>");
                     var theimage = data.filestructure.theimage;
//                     $("#ajaxResponse").append('<b>theimage:</b><img alt="it did not really work" src="data:image/jpeg;base64{' + theimage +'}"/>');
//                     $("#ajaxResponse").append("<b>Region:</b> " + data.countryInfo.region + "");
//                     $("#ajaxResponse").append("<b>Life Expectancy:</b> " + data.countryInfo.lifeExpectancy + "");
//                     $("#ajaxResponse").append("<b>GNP:</b> " + data.countryInfo.gnp + "");
                 } 
                 //display error message
                 else {
                     $("#ajaxResponse").html("<div><b>We could not receive any usefull data!</b></div>");
                 }
            },
            
            //If there was no resonse from the server
            error: function(jqXHR, textStatus, errorThrown){
                 console.log("Something really bad happened " + textStatus);
                  $("#ajaxResponse").html(jqXHR.responseText);
            },
            
            //capture the request before it was sent to server
            beforeSend: function(jqXHR, settings){
                //adding some Dummy data to the request
                settings.data += "&dummyData=whatever";
                //disable the button until we get the response
                $('#myButton').attr("disabled", true);
            },
            
            //this is called after the response or error functions are finsihed
            //so that we can take some action
            complete: function(jqXHR, textStatus){
                //enable the button 
                $('#myButton').attr("disabled", false);
            }
  
        });  
    });        
});