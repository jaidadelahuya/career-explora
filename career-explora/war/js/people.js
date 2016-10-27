$(document).ready(function() {
	$(".follow").click(function(e){
		e.preventDefault();
		var me = $(this);
		var x = me.prop("href");
		$.ajax({
			url:x,
			success:function(data) {
				me.fadeOut();
			}
		});
	});
});