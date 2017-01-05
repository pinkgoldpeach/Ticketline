$(document).ready(function() {
	console.log("Initializing login page");

	$("#submit").click(function() {
		var user = $("#username").val();
		var password = $("#password").val();
		
		$.ajax({
			type: "POST",
			url: "/ticketline/login",
			mimeType: "application/x-www-form-urlencoded",
			data: {
				user: user,
				password: password
			},
			success: function(data, status, error) {
				if (data.event == "AUTH_SUCCESS") {
					window.location.assign("/ticketline/swagger-ui/");
				} else {
					alert("Login failed");
				}
			},
			error: function(xhr, status, error) {
				alert("Login failed: " + status);
			},
			dataType : "json"
		});
	});
});