
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/style/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/style/dashboard.css">
<link rel="stylesheet" type="text/css" href="/style/waitMe.css">
</head>
<body>
	<input type="button" value="upload picture" id="upload" />

	<!-- profile name modal -->
	<div id="picture-dialog" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header" style="background-color: orange;">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel" style="color: white">Upload
						your picture</h4>
				</div>
				<div class="modal-body">

					<img alt="" src="" class="img img-responsive img-rounded" id="img" />
					
						<canvas id="my-can" width="100%" height="100%"></canvas>
					
					<form action="${up}" enctype="multipart/form-data" id="myForm"
						method="post">
						<input type="file" name="picture" id="pic"  />
				</div>
				<div class="modal-footer" style="background-color: orange;">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="submit" class="btn btn-primary" id="submit">submit</button>
				</div>
				</form>
			</div>
		</div>
	</div>

</body>

<script src="/js/jquery-1.11.2.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/validators.js"></script>
<script type="text/javascript" src="/js/index.js"></script>
<script type="text/javascript" src="/js/waitMe.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		$("#pic").change(function(e) {
			var url = $(this).val();
			
		});

		$("#myForm").on('submit', function(e) {
			var x = $(this).attr('action');
			
			$.ajax({
				url : x,
				type : 'POST',
				data : new FormData(this),
				processData : false,
				contentType : false,
				success : function(data) {
					console.log(data);
					$("#img").prop('src',data);
					drawImage();
					$("#img").addClass('img-rounded');
				}
			});
			e.preventDefault();
		});

		$("#upload").click(function(e) {
			e.preventDefault();
			jqxhr = $.get("/getuploadurl").done(function(data) {
				$("#picture-dialog").modal();
				$("#myForm").prop('action', data);

			}).error(function() {
				alert("error");
			});

		});
	});
	
	function drawImage() {
		var c = document.getElementById("my-can");
		var ctx = c.getContext("2d");
		var img = document.getElementById("img");
		ctx.drawImage(img,10,10);
		console.log("done");
	}
</script>
</html>