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
		window.location.replace("http://localhost:8080/html/login.html");
//		$('#loginbtn')
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
							'<div class="col-xs-12 col-sm-12 col-md-2">'+
								'<ul class="meta-search">'+
									'<li><i class="glyphicon glyphicon-calendar"></i> <span>'+data[i].year+'</span></li>'+
									'<li><i class="glyphicon glyphicon-time"></i> <span>'+data[i].author+'</span></li>'+
									'<li><i class="material-icons">language</i></i> <span>language</span></li>'+
								'</ul>'+
							'</div>'+
							'<div class="col-xs-12 col-sm-12 col-md-7 excerpet">'+
								'<h3><a href="#" title="">'+data[i].title+'</a></h3>'+
								'<p>ovo bi mogao biti highlight ako skontam kako</p>	'+					
	                			'<span class="plus"><a href="#" title="Lorem ipsum"><i class="glyphicon glyphicon-plus"></i></a></span>'+
							'</div>'+
							'<span class="clearfix borda"></span>'+
						'</article>')
							
							
							
							
			}
		}
	});
	
	
	
	
	
	

});