$(document).ready(function(){
	
	var a = window.location.search;
	
	
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
			success:function(logged){
				$("#username").text(logged.username);
				
				
				if(a==null || a==""){
					window.location.replace("/category.html?doing=add&id=0");
				}else{
					var u = a.slice(1).split('&');
					var id = u[0].split('=')[1];
					console.log("id: " + id);
					console.log("doingujem edit");
						
						$.ajax({
							url: 'http://localhost:8080/api/users/'+id,
							type: 'GET',
							headers: {'Authorization': 'Bearer ' + token},
							contentType: 'application/json',
							crossDomain: true,
							dataType: 'json',
							success:function(data){
								console.log(data);
								$("#name").val(data.name);
								$("#lastname").val(data.lastname);
								
								
								for(var i=0; i<logged.authorities.length; i++){
									if(logged.authorities[i].name == "ROLE_ADMIN"){
										console.log("ja sam adminnn");
//										$( "#follow" ).after('<button class="btn btn-primary col-md-2" value="" id="edit">Edit</button>');
//										$( "#follow" ).after('<button class="btn btn-primary col-md-2" value="" id="add">Add</button>');
									}else if(logged.authorities[i].name != "ROLE_ADMIN" && logged.username == id){
										console.log("nisam admin al gledam svoj profil");
									}else{
										console.log("nisam admin i ne gledam svoj profil");
										alert("Nije dozvoljeno menjati tudje profile!!!");
										window.location.replace("http://localhost:8080/profile.html?id="+logged.id);
									}
								}
								
								
								
								$("#btnSubmit").on('click', function(event){
									var name = $('#name').val();
									var lastname = $('#lastname').val();
									var password = $('#password').val();
									var password2 = $('#password2').val();
									
									if(password != password2){
										$('#obavestenje').text("Passwords not matching, try again!");
									}else{
										var category = {
												'name': name,
												'username': data.username,
												'lastname': lastname,
												'password': password
										}
									    
									    $.ajax({
											type : "PUT",
											contentType : "application/json",
											url :"http://localhost:8080/api/users/update/"+id,
											data : JSON.stringify(category),
											dataType : 'json',
											success : function(data) {
												console.log("uspesno izmenjen");
												window.location.replace("http://localhost:8080/profile.html");
											},
											error : function(e) {
												alert("Error!")
												console.log("ERROR: ", e);
											}
										
										});
									}
								    
									event.preventDefault();
									return false;
								});
								
								
								
								
								$("#deleteCat").on('click', function(event){
									console.log("brisem korisnika");
									
								    $.ajax({
										type : "DELETE",
										contentType : "application/json",
										url :"http://localhost:8080/api/users/"+id,
										success : function(data) {
											console.log("uspesno obrisan");
											localStorage.removeItem('token');
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
		});
	}
	
	
	

});