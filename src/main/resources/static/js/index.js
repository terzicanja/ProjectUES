$(document).ready(function(){
	
	var token = localStorage.getItem('token');
	
	
	
	if(token == null){
		console.log("token je null");
		$("#loginLink").text("Login");
		$("#loginLink").attr("href", "/login.html")
//		window.location.replace("http://localhost:8080/login.html");
		$("#follow").attr("data-toggle", "modal");
		$("#follow").attr("data-target", "#exampleModal");
		$('#goToLogin').on('click', function () {
			window.location.replace("/login.html");
		})
		
//		$("#SearchForm").on('click', ".btndwnl", function() {
//		    console.log("aj sad skidam ovo plsss");
//		    
////		    var cat = $('.kategorija').val();
////		    var val = $(this).attr('value');
////		    console.log("val od kat je: " + val);
////		    
////		    $(this).addClass('selected');
////		    
////		    window.location.replace('http://localhost:8080/index.html?category='+val);
//		    
//		    event.preventDefault();
//			return false;
//		});
		
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
				$("#username").attr("href", "/profile.html");
				
				if(data.category==null){
					console.log("korisnik nije pretplacen");
				}else{
					console.log("korisnik jeste pretplacen");
				}
				
				
				
				console.log('ja sam: ' + data);
				console.log(data);
				console.log("kategorija u whoami: "+cat);
				
				for(var i=0; i<data.authorities.length; i++){
					if(data.authorities[i].name == "ROLE_ADMIN" && cat!=""){
						console.log("ja sam adminnn");
						$( "#follow" ).after('<button class="btn btn-primary col-md-2" value="" id="edit">Edit</button>');
						$( "#follow" ).after('<button class="btn btn-primary col-md-2" value="" id="add">Add</button>');
					}
				}
				
				
				if(data.category != null && cat==data.category.id){
					$( "#follow" ).css({"color": "#ffffff", "background": "#f0ad4e none repeat scroll 0 0", "border-color": "#f0ad4e"});
					$( "#follow" ).text("Following");
				}
				
				if(data.category !=null && cat!=data.category.id){
					console.log("ne folujem ovu kat");
					$( ".btndwnl" ).hide();
					$(".btndwnl").css("height","0px");
				}
				
				$('#follow').on('click', function(event){
					
					$.ajax({
						url: 'http://localhost:8080/api/users/update/'+data.username+'/'+cat,
						type: 'PUT',
						headers: {'Authorization': 'Bearer ' + token},
						contentType: 'application/json',
						crossDomain: true,
						dataType: 'json',
						success:function(data){
							
							location.reload();
						}
					});
					
					event.preventDefault();
					return false;
				});
				
				
				$('#edit').on('click', function(event){
					window.location.replace("/category.html?doing=edit&id="+cat);
					
					event.preventDefault();
					return false;
				});
				
				
				$('#add').on('click', function(event){
					window.location.replace("/category.html?doing=add&id=0");
					
					event.preventDefault();
					return false;
				});
				
				
				
				$("#SearchForm").on('click', ".btndwnl", function() {
				    console.log("aj sad skidam ovo plsss");
				    
				    var val = $(this).attr('value');
				    console.log("val od knjige je: " + val);
				    
				    $.ajax({
						type : "GET",
//						contentType : "application/json",
						url :"http://localhost:8080/api/ebooks/download/"+val,
						headers: {'Authorization': 'Bearer ' + token},
						contentType: 'application/pdf',
						crossDomain: true,
						xhrFields: {
			                responseType: 'blob'
			            },
//						dataType: 'json',
						success : function(dat) {
							console.log("uspesno skinut");
							
							var blob=new Blob([dat]);
						    var link=document.createElement('a');
						    link.href=window.URL.createObjectURL(blob);
						    link.download=val+".pdf";
						    link.click();
							
//							localStorage.removeItem('token');
//							window.location.replace("http://localhost:8080/");
						},
						error : function(e) {
							alert("Error!")
							console.log("ERROR: ", e);
						}
					});
//				    
//				    $(this).addClass('selected');
//				    
//				    window.location.replace('http://localhost:8080/index.html?category='+val);
				    
				    event.preventDefault();
					return false;
				});
				
				
				
				
			}
		});
	}
	
	
	
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
			
			var $dropdown = $("#zaKategorije");
			
			$.each(data, function() {
				if(cat==this.id){
					$dropdown.append($('<button class="kategorija btn btn-primary col-md-2" style="color:#ffffff; background: #f0ad4e none repeat scroll 0 0; border-color: #f0ad4e;" value="'+this.id+'" id="kategorija'+this.id+'">'+this.name+'</button>'));
				}else{
					$dropdown.append($('<button class="kategorija btn btn-primary col-md-2" value="'+this.id+'" id="kategorija'+this.id+'">'+this.name+'</button>'));
				}
			    
			});
		}
	});
	
	
	var url = window.location.search;
	var cat = "";
	
	if(url==null || url==""){
		console.log("ovo je prazno");
		$( "#follow" ).hide();
	}else{
		cat = url.slice(1).split('&')[0].split('=')[1];
		console.log("ovo je kat iz urla: " + cat);
		
		
		$.ajax({
			url: 'http://localhost:8080/api/ebooks/category/'+cat,
			type: 'GET',
			headers: {'Authorization': 'Bearer ' + token},
			contentType: 'application/json',
			crossDomain: true,
			dataType: 'json',
			success:function(data){
				
				console.log('knjige su: ' + data);
				console.log(data);
				var request = new XMLHttpRequest();
		        var method = 'GET';
		        var async = true;
		        
		        
		        if(token!=null){
		        	$.ajax({
		    			url: 'http://localhost:8080/api/users/me',
		    			type: 'GET',
		    			headers: {'Authorization': 'Bearer ' + token},
		    			contentType: 'application/json',
		    			crossDomain: true,
		    			dataType: 'json',
		    			success:function(logged){
		    				
		    				for(var i=0; i<data.length; i++){
		    					
		    					
//								if(token!=null){
									if(logged.category==null || logged.category.id == cat){
										console.log("gledam kategoriju na koju sam pretplacena");
										$('.part').append('<article class="search-result row">'+
												'<div class="col-xs-12 col-sm-12 col-md-3 knjiga">'+
													'<ul class="meta-search">'+
														'<li><label>Category: </label> <span>'+data[i].category.name+'</span></li>'+
														'<li><label>Author: </label> <span>'+data[i].author+'</span></li>'+
														'<li><label>Language: </label> <span>'+data[i].language.name+'</span></li>'+
													'</ul>'+
												'</div>'+
												'<span class="clearfix borda"></span>'+
												'<div class="col-xs-12 col-sm-12 col-md-8 excerpet">'+
													'<h3><a href="#" title="">'+data[i].title+'</a></h3>'+
													'<p id="highlighter"></p>	'+					
								                	'<span class="plus"><button class="btndwnl" value="'+data[i].filename+'" id="skidaj"><i class="fa fa-download"></i></button></span>'+
												'</div>'+
											'</article>')
									}else{
										$('.part').append('<article class="search-result row">'+
												'<div class="col-xs-12 col-sm-12 col-md-3 knjiga">'+
													'<ul class="meta-search">'+
														'<li><label>Category: </label> <span>'+data[i].category.name+'</span></li>'+
														'<li><label>Author: </label> <span>'+data[i].author+'</span></li>'+
														'<li><label>Language: </label> <span>'+data[i].language.name+'</span></li>'+
													'</ul>'+
												'</div>'+
												'<span class="clearfix borda"></span>'+
												'<div class="col-xs-12 col-sm-12 col-md-8 excerpet">'+
													'<h3><a href="#" title="">'+data[i].title+'</a></h3>'+
													'<p></p>	'+	
												'</div>'+
											'</article>')
									}
									
							}
		    				
		    			}
		    		});
		        }else{
		        	for(var i=0; i<data.length; i++){
//						if(token!=null){
//							
//						}else{
							
							$('.part').append('<article class="search-result row">'+
									'<div class="col-xs-12 col-sm-12 col-md-3 knjiga">'+
										'<ul class="meta-search">'+
											'<li><label>Category: </label> <span>'+data[i].category.name+'</span></li>'+
											'<li><label>Author: </label> <span>'+data[i].author+'</span></li>'+
											'<li><label>Language: </label> <span>'+data[i].language.name+'</span></li>'+
										'</ul>'+
									'</div>'+
									'<span class="clearfix borda"></span>'+
									'<div class="col-xs-12 col-sm-12 col-md-8 excerpet">'+
										'<h3><a href="#" title="">'+data[i].title+'</a></h3>'+
										'<p></p>	'+	
									'</div>'+
								'</article>')
//						}
					}
		        }
		        
		        
				
			}
		});
	}
	
	
	
	
	
	$("#SearchForm").on('click', ".kategorija", function() {
	    console.log("aj sad");
	    
	    var cat = $('.kategorija').val();
	    var val = $(this).attr('value');
	    console.log("val od kat je: " + val);
	    
	    $(this).addClass('selected');
	    
	    window.location.replace('http://localhost:8080/index.html?category='+val);
	    
	    event.preventDefault();
		return false;
	});
	
	
	
	
	
	

});