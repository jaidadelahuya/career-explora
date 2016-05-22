var butt = $('#verify-access-token');
var msg = $("#message-div");

$(document).ready(function() {
	butt.click(function() {
		verifyToken();
		butt.prop('disabled',true);
	});
});



function verifyToken() {

	var key = $("#access-token").val();
	var auth = $("#authorization").val();
	msg.removeClass('alert alert-danger');
	msg.addClass('alert alert-success');
	msg.text("Verifying Authorization Code, Please wait...");
	
	$.ajax({
		url : $("#authorization-form").prop("action"),
		type : 'POST',
		data : {
			key : key,
			authorization : auth
		},
		success : function(data) {
	
			console.log("return");
			console.log(data.validity);
			msg.removeClass('alert alert-danger');
			msg.addClass('alert alert-success');
			msg.html("Token verified</br>You have subscribed for "+data.validity+"month(s) " +"<a href='/azure/success' id='cont'>Continue</a>...");
			
			var ref = $("#continue").prop('href');
			$("#cont").prop('href',ref);
			
			//$('#simple-pay-custom-id').prop('value',response.authResponse.userID); use an app identifier for a user not thier fb id 
		},
		error : function(jqXHR, textStatus, errorThrown) {
			butt.prop('disabled',false);
			msg.removeClass('alert alert-success');
			msg.addClass('alert alert-danger');
			if (errorThrown == "Not Acceptable") {
				msg.text("The token you entered in invalid");
			} else {
				msg.text(errorThrown);
			}
			
		}
	});
}
