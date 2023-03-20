jQuery(function($) {
	$('#btn-update').click(function(event) {
		updateUser();
	});
	
	$('#btn-delete').click(function(event) {
		deleteUser();
	});
});

function updateUser() {
	let formData = $('#user-detail-form').serializeArray();
	
	$.ajax({
		type: "PUT",
		cache: false,
		url: "/user/update",
		data: formData,
		dataType: "json"
	}).done(function(data) {
		alert("Updated user");
		window.location.href = "/user/list";
	}).fail(function(jqXHR, textStatus, errorThrown) {
		alert("Failed to update user");
	}).always(function() {
		// Process to always execute
	});
}

function deleteUser() {
	let formData = $('#user-detail-form').serializeArray();
	
	$.ajax({
		type: "DELETE",
		cache: false,
		url: "/user/delete",
		data: formData,
		dataType: "json"
	}).done(function() {
		alert("Deleted user");
		window.location.href = "/user/list";
	}).fail(function(jqXHR, textStatus, errorThrown) {
		alert("Failed to delete user");
	}).always(function() {
		// Process to always execute
	})
}