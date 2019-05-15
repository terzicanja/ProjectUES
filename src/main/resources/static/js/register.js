$(document).ready(function(){
	console.log('registracija');
	
	
	$('#register').on('click', function(event){
		
		console.log('kliknuto dugme za registraciju');
		
		var nameInput = $('#inputName').val();
		var surnameInput = $('#inputSurname').val();
		var usernameInput = $('#inputUsername').val();
		var passwordInput = $('#inputPassword').val();
		
		var user = {
				'name': nameInput,
				'lastname': surnameInput,
				'username': usernameInput,
				'password': passwordInput
		}
		
		
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url :"http://localhost:8080/api/users/create",
			data : JSON.stringify(user),
			dataType : 'json',
			success : function(data) {
				alert('uspesno ste se registrovali');
				console.log("token valjda: "+data.access_token);
//				$window.localStorage.token = JSON.stringify(data.access_token);
//				localStorage.setItem('token', data.access_token);
				
//				window.location.replace("http://localhost:8080/");
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		
			
		});
		
		
		
		
		console.log(localStorage.getItem('token'));
		
		event.preventDefault();
		return false;
	});
	
	
	
});