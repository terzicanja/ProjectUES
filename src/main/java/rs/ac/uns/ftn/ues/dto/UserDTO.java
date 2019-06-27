package rs.ac.uns.ftn.ues.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import rs.ac.uns.ftn.ues.entity.Authority;
import rs.ac.uns.ftn.ues.entity.Category;
import rs.ac.uns.ftn.ues.entity.User;

public class UserDTO implements Serializable {
	
	private Integer id;
	private String name;
	private String lastname;
	private String username;
	private String password;
	private Category category;
	private Collection<? extends GrantedAuthority> authorities = new HashSet<>();
	
	public UserDTO() {
		super();
	}
	
	
	

	
	public UserDTO(Integer id, String name, String lastname, String username, String password, Category category,
			Set<Authority> authorities) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.category = category;
		this.authorities = authorities;
	}
	
	public UserDTO(Integer id, String name, String lastname, String username, String password, Category category,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.category = category;
		this.authorities = authorities;
	}



	public UserDTO(Integer id, String name, String lastname, String username, String password, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.category = category;
	}



	public UserDTO(User user) {
		this(user.getId(), user.getName(), user.getLastname(), user.getUsername(), user.getPassword(), user.getCategory(), user.getAuthorities());
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


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}





	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}





	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	
	
	

}
