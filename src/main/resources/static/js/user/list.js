
let userData;
let table;

jQuery(function($) {
	createDataTables();
	
	$('#btn-search').click(function(event) {
		search();
	});
});

function createDataTables() {
	if (table != undefined) table.destroy();
	
	table = $('#user-list-table').DataTable({
		data: userData,
		columns: [
			{data: 'userId'},
			{data: 'userName'},
			{
				data: 'birthday',
				render: function(data, type, row) {
					let date = new Date(data);
					let year = date.getFullYear();
					let month = date.getMonth() + 1;
					let day = date.getDate();
					
					return year + '/' + month + '/' + day; 
				}
			},
			{data: 'age'},
			{
				data: 'gender',
				render: function(data, type, row) {
					let gender = (data === 1) ? 'Male' : 'Female';
					
					return gender;
				}
			},
			{
				data: 'userId',
				render: function(data, type, row) {
					let url = '<a href="/user/detail/' + data + '">Detail</a>';
					
					return url;
				}
			}
		]
	});
}

function search() {
	let formData = $('#user-search-form').serialize();
	
	$.ajax({
		method: "GET",
		url: "/user/get/list",
		data: formData,
		dataType: "json",
		contentType: "application/json; charset=UTF-8",
		cache: false,
		timeout: 5000
	}).done(function(data) {
		console.log(data);
		
		userData = data;
		
		createDataTables();
	}).fail(function(jqXHR, textStatus, errorThrown) {
		alert('Search process failed');
	}).always(function() {
		// Process to always execute
	})
}