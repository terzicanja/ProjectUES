$(document).ready(function(){
	console.log('uplodovanje');
	
	
	$('#btnSubmit').on('click', function(event){
		event.preventDefault();
		console.log('kliknuto dugme za upload');
		
		var form = $('#fileUploadForm')[0];
		var data = new FormData(form);
		console.log(data);
		$("#btnSubmit").prop("disabled", true);
		
//		var title = $('#title').val();
//		var file = $('#uploadFile')[0].files[0];
//		console.log(file);
//		var data = new FormData();
//		data.set("model", file);
//		data.set("title", title);
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