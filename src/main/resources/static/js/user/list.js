
let userData;
let table;

jQuery(function($) {
	createDataTables();
	
	$('#btn-search').click(function() {
		search();
	});
	
	$('#download-javascript').click(function(event) {
		event.preventDefault();
		
		let xhr = new XMLHttpRequest();
		
		xhr.open("POST", "/user/list/download", true);
		xhr.responseType = "blob";
		xhr.onload = function() {
			let filename = "userlistJavascript.csv";
			saveFile(filename, xhr.response);
		}
		
		// If you only want to download a file (GET method), remove this.
		let csrfHeader = "X-CSRF-TOKEN";
		let token = $('input[name="_csrf"]').val();
		xhr.setRequestHeader(csrfHeader, token);
		
		xhr.send();
	});
	
	$('#download-jquery').click(function(event) {
		event.preventDefault();
		
		// If you only want to download a file (GET method), remove this.
		let formData = $('#download-form').serializeArray();
		
		$.ajax({
			method: "POST",
			url: "/user/list/download",
			data: formData,
			xhrFields: {
				responseType: "blob"
			}
		}).done(function(data, status, jqXHR) {
			let filename = "userlistJquery.csv";
			const blob = new Blob([data], {type: data.type});
			
			saveFile(filename, blob);
		}).fail(function(jqXHR, status, errorThrown) {
			alert("File download failure");
		}).always(function(data, status, errorThrown) {
			// None
		});
	});
	
	$('#download-zip').click(function(event) {
		event.preventDefault();
		
		// If you only want to download a file (GET method), remove this.
		let formData = $('#download-form').serializeArray();
		
		$.ajax({
			method: "POST",
			url: "/user/list/download/zip",
			data: formData,
			xhrFields: {
				responseType: "blob"
			}
		}).done(function(data, status, jqXHR) {
			let filename = "sample.zip";
			const blob = new Blob([data], {type: data.type});
			
			saveFile(filename, blob);
		}).fail(function(jqXHR, status, errorThrown) {
			alert("File download failure");
		}).always(function(data, status, errorThrown) {
			// None
		});
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

function saveFile(filename, blob) {
	if (window.navigator.msSaveBlob) {
		window.navigator.msSaveBlob(blob, filename);
	}
	else {
		let a = document.createElement('a');
		let blobUrl = window.URL.createObjectURL(blob);
		
		document.body.appendChild(a);
		a.style = "display: none";
		a.href = blobUrl;
		a.download = filename;
		a.click();
	}
}