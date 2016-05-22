function required($input){  
	var val = $input.val().trim();	
    	if (val.length == 0) {           
         	return false;   
      	}else {
		return true; 
	}      
} 

function allLetter($input) {
	var val = $input.val().trim();
   	var letters = /^[A-Za-z]+$/;
   	if(val.match(letters)) {
      		return true;
    	} else {
     		return false;
     	}
}

function allNumeric($input) {
	var val = $input.val().trim(); 
      	var numbers = /^[0-9]+$/;
      	if(val.match(numbers)) {
      		return true;
      	} else {
      		return false;
      	}
} 

function validateEmail($input) {
	var val = $input.val().trim();
 	if (/^\S+@\S+\.\S+/.test(val)) {
    		return (true)
  	} else {
		return false;
	} 
}

function validateGender($male, $female) {
	var m = $male.is(':checked');
	var f = $female.is(':checked');

	if(m | f) {
		return true;
	} else {
		return false;
	}
}

function lengthRange($input, minlength, maxlength) {  	
	var val = $input.val().trim();  
	if(val.length >= minlength && val.length <= maxlength) {  	
        return true;  	
	} else {  	
        return false;  	
	}  
}

//password between 7 to 21 characters which contain at least one numeric digit and a special character
function checkPassword($input) { 
	var val = $input.val();
	var paswd=  /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{7,21}$/;

	if(val.match(paswd)) { 
		return true;
	} else { 
		return false;
	}
}  


function regInputOk($input) {
	var errDiv = $("#reg-error-div");
	$input.removeClass("reg-input-error");
	errDiv.removeClass("alert alert-danger");
	errDiv.text("");
}

function regInputBad($input, msg) {
	var errDiv = $("#reg-error-div");
	$input.addClass("reg-input-error");
	errDiv.addClass("alert alert-danger");
	errDiv.text(msg);
}


	



   

