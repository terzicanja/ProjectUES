$(document).ready(function(){
	
	var a = window.location.search;
	
	
	var token = localStorage.getItem('token');
	
	if(token == null){
		console.log("token je null");
		$("#loginLink").text("Login");
		$("#loginLink").attr("href", "/login.html")
		window.location.replace("http://localhost:8080/");
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
				console.log(data);
				
				if(a==null || a==""){
					window.location.replace("/profile.html?id="+data.id);
				}else{
					var u = a.slice(1).split('&');
					var id = u[0].split('=')[1];
					console.log("id: " + id);
					
					
					$('#edit').on('click', function(event){
						
						window.location.replace('http://localhost:8080/edit.html?id='+data.username);
						
						event.preventDefault();
						return false;
						
					});
					
					
					$("#korisnicko").text(data.username);
					$("#name").text(data.name + " " + data.lastname);
					if(data.category != null){
						$("#categ").text(" "+data.category.name);
						$("#categ").attr("href", "/index.html?category="+data.category.id);
					}
					
					
					$.ajax({
						url: 'http://localhost:8080/api/ebooks/user/'+data.id,
						type: 'GET',
						headers: {'Authorization': 'Bearer ' + token},
						contentType: 'application/json',
						crossDomain: true,
						dataType: 'json',
						success:function(d){
							console.log(d);
							
							for(var i=0; i<d.length; i++){
								$("#txtForUploaded").after('<article class="search-result row">'+
										'<div class="col-xs-12 col-sm-12 col-md-3 knjiga">'+
										'<ul class="meta-search">'+
											'<li><label>Category: </label> <span>'+d[i].category.name+'</span></li>'+
											'<li><label>Author: </label> <span>'+d[i].author+'</span></li>'+
											'<li><label>Language: </label> <span>'+d[i].language.name+'</span></li>'+
										'</ul>'+
									'</div>'+
									'<span class="clearfix borda"></span>'+
									'<div class="col-xs-12 col-sm-12 col-md-8 excerpet">'+
										'<h3><a href="#" title="">'+d[i].title+'</a></h3>'+
										'<p></p>	'+					
					                	'<span class="plus"><a href="#" title="Download book"><button class="btndwnl"><i class="fa fa-download"></i></button></a></span>'+
									'</div>'+
								'</article>');
							}
							
							
						}
					});
					
				}
				
			}
		});
	}
	
	
	

});