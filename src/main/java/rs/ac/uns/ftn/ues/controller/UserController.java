package rs.ac.uns.ftn.ues.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.ues.dto.UserDTO;
import rs.ac.uns.ftn.ues.entity.Category;
import rs.ac.uns.ftn.ues.entity.User;
import rs.ac.uns.ftn.ues.service.CategoryServiceInterface;
import rs.ac.uns.ftn.ues.service.UserServiceInterface;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
	
	@Autowired
	private UserServiceInterface userService;
	
	@Autowired
	private CategoryServiceInterface catService;
	
	
	
	@GetMapping(value = "/me")
    public ResponseEntity<User> whoAmI(Principal user) {
		User logged = userService.findByUsername(user.getName());
		
		return new ResponseEntity<>(logged, HttpStatus.OK);
    }
	
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers(){
		List<User> users = userService.findAll();
		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		for(User u : users) {
			usersDTO.add(new UserDTO(u));
		}
		return new ResponseEntity<List<UserDTO>>(usersDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{username}")
	public ResponseEntity<UserDTO> getOne(@PathVariable("username") String username){
		User user = userService.findByUsername(username);
		UserDetails u = userService.findByUsername(username);
		System.out.println("korisnik sa prezimenom: "+user.getLastname());
		return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setName(userDTO.getName());
		user.setLastname(userDTO.getLastname());
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		user.setPassword(bc.encode(userDTO.getPassword()));
//		user.setPassword(userDTO.getPassword());
		
		user = userService.save(user);
		
		return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/update/{username}", consumes = "application/json")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable("username") String username){
		User user = userService.findByUsername(username);
		user.setName(userDTO.getName());
		user.setLastname(userDTO.getLastname());
		user.setPassword(userDTO.getPassword());
		
		user = userService.save(user);
		return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
	}
	
	@PutMapping(value = "/update/{username}/{category}", consumes = "application/json")
	public ResponseEntity<User> followCategoryUser(@PathVariable("username") String username, @PathVariable("category") Integer category){
		User user = userService.findByUsername(username);
		Category cat = catService.findOne(category);
		user.setCategory(cat);
//		user.setName(userDTO.getName());
//		user.setLastname(userDTO.getLastname());
//		user.setPassword(userDTO.getPassword());
		
		user = userService.save(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{username}")
	public ResponseEntity<Void> deleteUser(@PathVariable("username") String username){
		User user = userService.findByUsername(username);
		userService.remove(user.getId());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	
	
	
	

}
