$(document).ready(function(){
	
	
	
	var token = localStorage.getItem('token');
	
	if(token == null){
		console.log("token je null");
		window.location.replace("http://localhost:8080/login.html");
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