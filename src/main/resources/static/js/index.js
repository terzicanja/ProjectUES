$(document).ready(function(){
	
	
	var token = localStorage.getItem('token');
	
	if(token == null){
		console.log("token je null");
		$("#loginLink").text("Login");
		$("#loginLink").attr("href", "/login.html")
//		window.location.replace("http://localhost:8080/login.html");
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
	
	
	
	
//	var url = window.location.search.slice(1).split('&');
	var url = window.location.search;
//	console.log("url je: "+url);
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
				
				for(var i=0; i<data.length; i++){
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
							'<p>ovo bi mogao biti highlight ako skontam kako</p>	'+					
		                	'<span class="plus"><a href="#" title="Download book"><button class="btndwnl"><i class="fa fa-download"></i></button></a></span>'+
						'</div>'+
					'</article>')
				}
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
	
	
	
	
	
	
	$.ajax({
		url: 'http://localhost:8080/api/users/me',
		type: 'GET',
		headers: {'Authorization': 'Bearer ' + token},
		contentType: 'application/json',
		crossDomain: true,
		dataType: 'json',
		success:function(data){
			
			console.log('ja sam: ' + data);
			console.log(data);
			console.log("kategorija u whoami: "+cat);
			
			
			if(data.category != null && cat==data.category.id){
				$( "#follow" ).css({"color": "#ffffff", "background": "#f0ad4e none repeat scroll 0 0", "border-color": "#f0ad4e"});
				$( "#follow" ).text("Following");
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
			
		}
	});
	
	
	
	
	
	
	
	

});