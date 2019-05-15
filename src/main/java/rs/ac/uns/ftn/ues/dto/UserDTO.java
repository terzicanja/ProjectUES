package rs.ac.uns.ftn.ues.dto;

import java.io.Serializable;

import rs.ac.uns.ftn.ues.entity.User;

public class UserDTO implements Serializable {
	
	private Integer id;
	private String name;
	private String lastname;
	private String username;
	private String password;
	
	public UserDTO() {
		super();
	}

	public UserDTO(Integer id, String name, String lastname, String username, String password) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
	}
	
	public UserDTO(User user) {
		this(user.getId(), user.getName(), user.getLastname(), user.getUsername(), user.getPassword());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
