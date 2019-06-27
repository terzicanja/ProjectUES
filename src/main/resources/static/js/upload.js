$(document).ready(function(){
	console.log('uplodovanje');
	
//	var $dropdown = $("#selectLang");
//	$.each(result, function() {
//	    $dropdown.append($("<option />").val(this.ImageFolderID).text(this.Name));
//	});
	var user;
	var token = localStorage.getItem('token');
	if(token == null){
		console.log("token je null");
		$("#loginLink").text("Login");
		$("#loginLink").attr("href", "/login.html")
		alert("You have to be logged in for this action!");
		window.location.replace("http://localhost:8080");
	}else{
		$("#loginLink").text("Logout");
		$('#loginLink').on('click', function(){
			localStorage.removeItem('token');
			location.reload();
		});
		
		
		$.ajax({
			url: 'http://localhost:8080/api/users/me',
			type: 'GET',
			headers: {'Authorization': 'Bearer ' + token},
			contentType: 'application/json',
			crossDomain: true,
			dataType: 'json',
			success:function(data){
				$("#username").text(data.username);
				user = data.username;
			}
		});
		
		
	}
	
	$.ajax({
		url: 'http://localhost:8080/api/languages',
		type: 'GET',
		headers: {'Authorization': 'Bearer ' + token},
		contentType: 'application/json',
		crossDomain: true,
		dataType: 'json',
		success:function(data){
			
			console.log('jezici su: ' + data);
			console.log(data);
			
			var $dropdown = $("#selectLang");
			$.each(data, function() {
			    $dropdown.append($("<option />").val(this.id).text(this.name));
			});
		}
	});
	
	$.ajax({
		url: 'http://localhost:8080/api/category',
		type: 'GET',
		headers: {'Authorization': 'Bearer ' + token},
		contentType: 'application/json',
		crossDomain: true,
		dataType: 'json',
		success:function(data){
			
			console.log('kategorije su: ' + data);
			console.log(data);
			
			var $dropdown = $("#selectCat");
			$.each(data, function() {
			    $dropdown.append($("<option />").val(this.id).text(this.name));
			});
		}
	});
	
	
	
	
	$('#btnSubmit').on('click', function(event){
		event.preventDefault();
		console.log('kliknuto dugme za upload');
		
		var lang = $('#selectLang').val();
		var cat = $('#selectCat').val();
		console.log("lang i cat: " + lang + cat);
		
		var title = $('#title').val();
		var keywords = $('#keywords').val();
		console.log("title i key: " + title + keywords);
		
		var file = $('#uploadFile')[0].files;
		var form = $('#fileUploadForm')[0];
		console.log(form);
		var data = new FormData(form);
//		data.set("language", lang);
		console.log(data);
		$("#btnSubmit").prop("disabled", true);
		
		console.log(file);
		
//		var title = $('#title').val();
//		var file = $('#uploadFile')[0].files[0];
//		console.log(file);
//		var data = new FormData();
//		data.set("files", file);
//		data.set("title", title);
		data.set("user", user);
		console.log("ovo ispod ce biti user");
		console.log(user);
		console.log(data);
		
		for (var key of data.entries()) {
	        console.log(key[1]);
	    }
		
		
		$.ajax({
			type : "POST",
//			contentType : "application/json",
			enctype: 'multipart/form-data',
			url :"http://localhost:8080/api/ebooks/save",
			data : data,//sve ovo proveriti sta je sta tacno
			processData: false, //prevent jQuery from automatically transforming the data into a query string
	        contentType: false,
	        cache: false,
	        timeout: 600000,
//			dataType : 'json',
			success : function(data) {
				alert('uspesno ste uradili upload');
				console.log("token valjda: "+data.access_token);
//				$window.localStorage.token = JSON.stringify(data.access_token);
//				localStorage.setItem('token', data.access_token);
				
//				window.location.replace("http://localhost:8080/");
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		
			
		});
		
		
		
		
		console.log(localStorage.getItem('token'));
		
		event.preventDefault();
		return false;
	});
	
	
	
});