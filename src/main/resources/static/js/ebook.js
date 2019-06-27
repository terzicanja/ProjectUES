$(document).ready(function(){
	
	var a = window.location.search;
	
	if(a==null || a==""){
//		window.location.replace("/category.html?doing=add&id=0");
	}else{
		var u = a.slice(1).split('&');
		var id = u[0].split('=')[1];
		console.log("id: " + id);
		
		
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
		
		
			$.ajax({
				url: 'http://localhost:8080/api/ebooks/'+id,
				type: 'GET',
				headers: {'Authorization': 'Bearer ' + token},
				contentType: 'application/json',
				crossDomain: true,
				dataType: 'json',
				success:function(data){
					console.log(data);
					$("#title").val(data.title);
					$("#keywords").val(data.keywords);
					$("#selectLang").val(data.language.id);
					$("#selectCat").val(data.category.id);
					
					
					$("#btnSubmit").on('click', function(event){
						var title = $('#title').val();
						var keywords = $('#keywords').val();
						var lang = $('#selectLang').val();
						var cat = $('#selectCat').val();
						
							var book = {
									'title': title,
									'keywords': keywords
							}
						    
						    $.ajax({
								type : "PUT",
								contentType : "application/json",
								url :"http://localhost:8080/api/ebooks/update/"+id,
								data : JSON.stringify(book),
								dataType : 'json',
								success : function(data) {
									console.log("uspesno izmenjen");
									window.location.replace("http://localhost:8080/");
								},
								error : function(e) {
									alert("Error!")
									console.log("ERROR: ", e);
								}
							});
						
						event.preventDefault();
						return false;
					});
					
					
					
					
					$("#deleteCat").on('click', function(event){
						console.log("brisem knjigu");
						
					    $.ajax({
							type : "DELETE",
							contentType : "application/json",
							url :"http://localhost:8080/api/ebooks/"+id,
							success : function(data) {
								console.log("uspesno obrisan");
								window.location.replace("http://localhost:8080/");
							},
							error : function(e) {
								alert("Error!")
								console.log("ERROR: ", e);
							}
						});
					    
						event.preventDefault();
						return false;
					});
					
					
					
					
					$("#downloadCat").on('click', function(event){
						console.log("skidam knjigu");
						
					    $.ajax({
							type : "GET",
//							contentType : "application/json",
							url :"http://localhost:8080/api/ebooks/download/"+data.filename,
							headers: {'Authorization': 'Bearer ' + token},
							contentType: 'application/pdf',
							crossDomain: true,
							xhrFields: {
				                responseType: 'blob'
				            },
//							dataType: 'json',
							success : function(dat) {
								console.log("uspesno skinut");
								
								var blob=new Blob([dat]);
							    var link=document.createElement('a');
							    link.href=window.URL.createObjectURL(blob);
							    link.download=data.filename+".pdf";
							    link.click();
								
							},
							error : function(e) {
								alert("Error!")
								console.log("ERROR: ", e);
							}
						});
					    
						event.preventDefault();
						return false;
					});
					
					
				}
			});
			
	}
	
	
	var token = localStorage.getItem('token');
	
	if(token == null){
		console.log("token je null");
		$("#loginLink").text("Login");
		$("#loginLink").attr("href", "/login.html");
		alert("You have to be logged in for this action!");
		window.location.replace("http://localhost:8080");
//		window.location.replace("http://localhost:8080/html/login.html");
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
			}
		});
	}
	
	
	

});