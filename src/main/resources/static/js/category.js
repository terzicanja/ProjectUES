$(document).ready(function(){
	
	var a = window.location.search;
	
	if(a==null || a==""){
		window.location.replace("/category.html?doing=add&id=0");
	}else{
		var u = a.slice(1).split('&');
		var doing = u[0].split('=')[1];
		var id = u[1].split('=')[1];
		console.log("doing: " + doing + " id: " + id);
		
		if(doing == "add"){
			console.log("doingujem add");
			
			$('#deleteCat').hide();
			
			$("#btnSubmit").on('click', function(event){
				console.log("cuvam knjigu");
				
				var name = $('#name').val();
			    
			    var category = {
			    		'id': id,
						'name': name
				}
			    
			    $.ajax({
					type : "POST",
					contentType : "application/json",
					url :"http://localhost:8080/api/category/create",
					data : JSON.stringify(category),
					dataType : 'json',
					success : function(data) {
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
			
			
		}else if(doing == "edit"){
			console.log("doingujem edit");
			
			$.ajax({
				url: 'http://localhost:8080/api/category/'+id,
				type: 'GET',
				headers: {'Authorization': 'Bearer ' + token},
				contentType: 'application/json',
				crossDomain: true,
				dataType: 'json',
				success:function(data){
					$("#name").val(data.name);
					
					$("#btnSubmit").on('click', function(event){
						var name = $('#name').val();
					    
					    var category = {
					    		'id': id,
								'name': name
						}
					    
					    $.ajax({
							type : "PUT",
							contentType : "application/json",
							url :"http://localhost:8080/api/category/update/"+id,
							data : JSON.stringify(category),
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
						console.log("brisem kategoriju");
						
					    $.ajax({
							type : "DELETE",
							contentType : "application/json",
							url :"http://localhost:8080/api/category/"+id,
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
					
					
					
				}
			});
			
			
		}
		
	}
	
	
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
				console.log(data);
				$("#username").text(data.username);
				for(var i=0; i<data.authorities.length; i++){
					if(data.authorities[i].name != "ROLE_ADMIN"){
						console.log("ja nisam adminnn");
						alert("You have to be admin for this action!");
						window.location.replace("http://localhost:8080");
					}
				}
			}
		});
	}
	
	
	

});