$(document).ready(function(){
	
	
	
	var token = localStorage.getItem('token');
	
	if(token == null){
		console.log("token je null");
		window.location.replace("http://localhost:8080/html/login.html");
//		$('#loginbtn')
	}
	
	
	$.ajax({
		url: 'http://localhost:8080/api/ebooks',
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
	        var postPhoto;
	        var userPhoto;
	        var s = "/img/noimage.png";
			var u = "/img/nouser.jpg";
			
//			for(var i=0; i<data.length; i++){
////				if(data[i].active==true){
//					$('.posts').append('<div class="post">'+
//							'<img class="pic" id="pic" src="data:image/gif;base64,'+data[i].user.photo+'" onError="this.src=\x27'+u+'\x27;">'+
//							'<a href="http://localhost:8080/html/profile.html?id='+data[i].user.username+'" class="username">'+
//							data[i].user.username+'</a><br>'+
//							'<p id="date">'+data[i].date+'</p>'+
//							'<a href="http://localhost:8080/html/post.html?id='+data[i].id+'" id="title"><h3>'+data[i].title+'</h3></a><br>'+
//							'<img id="img" src="data:image/gif;base64,'+data[i].photo+'" onError="this.src=\x27'+s+'\x27;">'+
//						'</div>')
////				}
//				
//			}
		}
	});
	
	
	
	
	
	$('#searchbtn').on('click', function(event){
//		var srchinput = $('.srchinput');
//		var search = srchinput.val();
//		console.log('searchujem po: ');
		
//		window.location.replace('http://localhost:8080/html/search.html?search='+search);
		window.location.replace('http://localhost:8080/home.html');
		
//		event.preventDefault();
//		return false;
		
	});
	
	
	
	

});