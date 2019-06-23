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
	
	
	$('#searchTerm').on('click', function(event){
		console.log("pretrazujem term");
		
		var termField = $('#termField').find(":selected").text();
		var field = $('#termField').val();
		var value = $('#termValue').val();
		
		window.location.replace('http://localhost:8080/home.html?search=term&field='+field+'&value='+value);
		
		event.preventDefault();
		return false;
		
	});
	
	
	$('#searchFuzzy').on('click', function(event){
		console.log("pretrazujem fuzzy");
		
		var fuzzyField = $('#fuzzyField').find(":selected").text();
		var field = $('#fuzzyField').val();
		var value = $('#fuzzyValue').val();
		
		window.location.replace('http://localhost:8080/home.html?search=fuzzy&field='+field+'&value='+value);
		
		event.preventDefault();
		return false;
		
	});
	
	
	$('#searchPhrase').on('click', function(event){
		console.log("pretrazujem phrase");
		
		var phraseField = $('#phraseField').find(":selected").text();
		var field = $('#phraseField').val();
		var value = $('#phraseValue').val();
		
		window.location.replace('http://localhost:8080/home.html?search=phrase&field='+field+'&value='+value);
		
		event.preventDefault();
		return false;
		
	});
	
	
	
	

});