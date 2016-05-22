$(document).ready(function() {
	
	// for adding new fields
	function addNewHandler(elem, event) {

		var grandParent = elem.parent().parent().parent();
		//alert(grandParent.nodeName);
		if (elem.prop('value') == 'add') {
			var newGrandParent = grandParent.clone(true);
			newGrandParent.find("input[type=text]").val("");
			newGrandParent.insertAfter(grandParent);
		} else if (elem.prop('value') == "del") {
			grandParent.remove();
		}

	}

	$(".butt").click(function(event) {
		addNewHandler($(this), event);
	});
});

