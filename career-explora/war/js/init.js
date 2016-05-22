var url = null;
var username = null;
var email = null;
var response = null;
var upComingTests = [];
//var info = null;
var counter = 0;
var topics = [];
var id = '';
var upLoadUrl = null;
var hasTakenTalentTest = false;
var look = "love";

//this function should replace editLocationText
function editSchoolName(sch, attd) {

	if (attd === undefined) {
		attd = '';
	}
	if (sch.length > 20) {
		sch = sch.substring(0, 18);
		sch = sch + "...";
	}
	
	return attd + " " + sch + "<br/>";
	
}


