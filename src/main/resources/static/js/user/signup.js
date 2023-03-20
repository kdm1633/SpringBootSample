
jQuery(function($) {
	$('#btn-signup').click(function(event) {
		signupUser();
	});
});

function signupUser() {
	removeValidResult();
	
	let formData = $('#signup-form').serializeArray();
	
	$.ajax({
		type: "POST",
		cache: false,
		url: "/user/signup/rest",
		data: formData,
		dataType: "json"
	}).done(function(data) {
		console.log(data);
		
		if (data.result === 90) {
			$.each(data.errors, function(key, value) {
				reflectValidResult(key, value);
			})
		}
		else if (data.result === 0) {
			alert("Signed up user");
			window.location.href = "/login";
		}
	}).fail(function(jqXHR, textStatus, errorThrown) {
		alert("User signup failed");
	}).always(function() {
		// Process to always execute
	});
}

function removeValidResult(key, value) {
	$('.is-invalid').removeClass('is-invalid');
	$('.invalid-feedback').remove();
	$('.text-danger').remove();
}

function reflectValidResult(key, value) {
	if (key == "gender") {
		$('input[name=' + key + ']').addClass('is-invalid');
		$('input[name=' + key + ']')
			.parent().parent()
			.append('<div class="text-danger">' + value + '</div>');
	}
	else {
		$('input[id=' + key + ']').addClass('is-invalid');
		$('input[id=' + key + ']')
			.after('<div class="invalid-feedback">' + value + '</div>');
	}
}