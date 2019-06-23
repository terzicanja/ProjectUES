$(document).ready(function(){
	
	var url = window.location.search.slice(1).split('&');
	var search = url[0].split('=')[1];
	var s = window.location.search.slice(1).split('&')[0].split('=')[1];
	var field = url[1].split('=')[1];
	var value = url[2].split('=')[1];
	console.log("ovo je parametar iz urla: " + search);
	console.log("field: " + field);
	console.log("value: " + value);
	
	var token = localStorage.getItem('token');
	
	if(token == null){
		console.log("token je null");
		$("#loginLink").text("Login");
		$("#loginLink").attr("href", "/login.html")
//		window.location.replace("http://localhost:8080/html/login.html");
//		$('#loginbtn')
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
	
//	var field = $('#luceneTermQuery input[name=field]').val();
//    var value = $('#luceneTermQuery input[name=value]').val();
    var data = JSON.stringify({"field":field, "value":value});
    $("#btnSubmitLuceneQueryLanguage").prop("disabled", true);
	
	$.ajax({
//		url: 'http://localhost:8080/api/ebooks',
//		type: 'GET',
		url: 'http://localhost:8080/search/'+search,
		type: 'POST',
		data: data,
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
	        var postPhoto;
	        var userPhoto;
	        var s = "/img/noimage.png";
			var u = "/img/nouser.jpg";
			
			for(var i=0; i<data.length; i++){
//				if(data[i].active==true){
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
		                	'<span class="plus"><a href="#" title="Lorem ipsum"><i class="material-icons">get_app</i></a></span>'+
						'</div>'+
					'</article>')
							
			}
		}
	});
	
	
	
	
	$("#searchedBooks").on('click', ".download", function() {
	    console.log("plss radi");
	    
//	    var cat = $('.kategorija').val();
	    var id = $(this).attr('id');
	    console.log("id od knjige je: " + id);
//	    
//	    $(this).addClass('selected');
//	    
//	    window.location.replace('http://localhost:8080/index.html?category='+val);
	    
	    event.preventDefault();
		return false;
	});
	
	
	
	

});