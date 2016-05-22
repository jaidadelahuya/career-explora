function populateTalentList(data, list, hasMore) {
	
	if(data === undefined) {
		return;
	}
	
	var button = $("#show-talents-report", opener.document);
	button.hide();
	var butt = $("#show-me-more-talents", opener.document);
	if (hasMore) {
		button.css("display","inherit");
		$("#talent-footer").css("padding","2%");
		butt.removeClass("hidden")
	} else {
		button.css("display","none");
		butt.addClass("hidden");
	}
	
	var parent = list.parent();
	list.children().remove();
	for (i = 0; i < data.length; i++) {
		var s = '<li class="list-group-item indigo-list tlist">' + '<div>'
				+ '<span>' + data[i] + '</span></div></li>';
		list.append(s);

	}
	
}



function populateStylesList(data, list, hasMore) {
	
	if(data === undefined) {
		return;
	}
	
	var footer = $("#styles-footer", opener.document);
	footer.hide();
	if (hasMore) {
		footer.css("display","inherit");
		footer.css("padding","2%");
	} else {
		footer.css("display","none");
	}
	
	var parent = list.parent();
	list.children().remove();
	for (i = 0; i < data.length; i++) {
		var s = '<li class="list-group-item blue-list slist">' + '<div>'
				+ '<span>' + data[i] + '</span></div></li>';
		list.append(s);

	}

}

function populateValuesList(data, list, hasMore) {
	if(data === undefined) {
		return;
	}
	
	var footer = $("#values-footer", opener.document);
	footer.hide();
	if (hasMore) {
		footer.css("display","inherit");
		footer.css("padding","2%");
	} else {
		footer.css("display","none");
	}
	var parent = list.parent();
	list.children().remove();
	for (i = 0; i < data.length; i++) {
		var s = '<li class="list-group-item violet-list vlist">' + '<div>'
				+ '<span>' + data[i] + '</span></div></li>';
		list.append(s);

	}
	

}





function populateSkillsBuiltList(data, list, hasMore) {
	
	if(data === undefined) {
		return;
	}
	
	var footer = $("#skills-built-footer", opener.document);
	footer.hide();
	if (hasMore) {
		footer.css("display","inherit");
		footer.css("padding","2%");
	} else {
		footer.css("display","none");
	}
	
	var parent = list.parent();
	list.children().remove();
	for (i = 0; i < data.length; i++) {
		var s = '<li class="list-group-item green-list sllist">' + '<div>'
				+ '<span>' + data[i] + '</span></div></li>';
		list.append(s);

	}
	

}



function populateSkillsToBuildList(data, list, hasMore) {
	
	if(data === undefined) {
		return;
	}
	
	var footer = $("#skills-to-learn-footer", opener.document);
	footer.hide();
	if (hasMore) {
		footer.css("display","inherit");
		footer.css("padding","2%");
	} else {
		footer.css("display","none");
	}
	
	var parent = list.parent();
	list.children().remove();
	for (i = 0; i < data.length; i++) {
		var s = '<li class="list-group-item orange-list stllist">' + '<div>'
				+ '<span>' + data[i] + '</span></div></li>';
		list.append(s);

	}
	
}


function populateMITList(data, list, hasMore) {
	if(data === undefined) {
		return;
	}
	
	var button = $("#show-mits-report", opener.document);
	button.hide();
	var butt = $("#show-me-more-mits", opener.document);
	if (hasMore) {
		button.css("display","inherit");
		$("#mits-footer").css("padding","2%");
		butt.removeClass("hidden");
	} else {
		button.css("display","none");
		butt.addClass("hidden");
	}
	
	var parent = list.parent();
	list.children().remove();
	for (i = 0; i < data.length; i++) {
		var s = '<li class="list-group-item red-list mlist">' + '<div>'
				+ '<span>' + data[i] + '</span></div></li>';
		list.append(s);

	}
	

}

function populateCareerClusterList(data, list, hasMore) {
	
	if(data === undefined) {
		return;
	}
	
	var button = $("#show-career-cluster-report", opener.document);
	button.hide();
	var butt = $("#show-me-more-clusters", opener.document);
	
	if (hasMore) {
		button.css("display","inherit");
		$("#clusters-footer").css("padding","2%");
		butt.removeClass("hidden");
		
	} else {
		button.css("display","none");
		butt.addClass("hidden");
	}
	var parent = list.parent();
	list.children().remove();
	for (i = 0; i < data.length; i++) {
		var s = '<li class="list-group-item yellow-list clist">' + '<div>'
				+ '<span>' + data[i] + '</span></div></li>';
		list.append(s);

	}
	
}

