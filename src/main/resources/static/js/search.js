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
	
	
	
	$('#advancedBtn').on('click', function(event){
		console.log("pretrazujem advanced");
		
//		var field1 = $('#field1').find(":selected").text();
		var field1 = $('#field1').val();
		var value1 = $('#value1').val();
		var operator1 = $('#operator1').val();
		var field2 = $('#field2').val();
		var value2 = $('#value2').val();
		var operator2 = $('#operator2').val();
		console.log("field: " + field1 + " value: " + value1 + " operator1: " + operator1);
		console.log("field: " + field2 + " value: " + value2 + " operator1: " + operator2);
		
		window.location.replace('http://localhost:8080/home.html?search=boolean&field1='+field1+'&value1='+value1+'&operator='+operator1+'&field2='+field2+'&value2='+value2);
		
		event.preventDefault();
		return false;
		
	});
	
	
	
	

});