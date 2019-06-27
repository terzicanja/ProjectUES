$(document).ready(function(){
	console.log('login');
	
//	var proba = document.getElementById("inputEmail").value;
//	console.log(proba);
//	var bla = $('#txt_name').val();

	$('#login').on('click', function(event){
		
		console.log('kliknuto dugme');
		//var userNameInput = $("input[name='username']");
		//var passwordInput = $("input[name='password']");
		//var username = userNameInput.val();
		//var password = passwordInput.val();
		
		var usernameInput = $('#inputEmail').val();
		var passwordInput = $('#inputPassword').val();
		
		
		console.log("username i pass su: " + usernameInput + passwordInput);
		
		var user = {
				'username': usernameInput,
				'password': passwordInput
		}
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url :"http://localhost:8080/auth/login",
			data : JSON.stringify(user),
			dataType : 'json',
			success : function(data) {
				alert('uspesno ste se ulogovali');
				console.log("token valjda: "+data.access_token);
//				$window.localStorage.token = JSON.stringify(data.access_token);
				localStorage.setItem('token', data.access_token);
				
				window.location.replace("http://localhost:8080/");
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