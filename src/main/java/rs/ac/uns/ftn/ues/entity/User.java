package rs.ac.uns.ftn.ues.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users")
public class User implements Serializable, UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "name", unique = false, nullable = true)
	private String name;
	
	@Column(name = "lastname", unique = false, nullable = true)
	private String lastname;

	@Column(name = "username", unique = false, nullable = false)
	private String username;

	@Column(name = "password", unique = false, nullable = false)
	private String password;
	
	@ManyToMany(cascade=CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name="user_authority",
			joinColumns=@JoinColumn(name="user_id", referencedColumnName="id"),
			inverseJoinColumns = @JoinColumn(name="authority_id", referencedColumnName="id"))
	private Set<Authority> user_authorities = new HashSet<>();
	
//	@ManyToOne
//	@JoinColumn(name="authority_id", referencedColumnName="id")
//	private Authority authority;
	
	@ManyToOne
	@JoinColumn(name="category_id", referencedColumnName="category_id")
	private Category category;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
	private List<EBook> ebooks = new ArrayList<EBook>();
	
	public User() {
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
	
	public Set<Authority> getUser_authorities() {
		return user_authorities;
	}

	public void setUser_authorities(Set<Authority> user_authorities) {
		this.user_authorities = user_authorities;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<EBook> getEbooks() {
		return ebooks;
	}

	public void setEbooks(List<EBook> ebooks) {
		this.ebooks = ebooks;
	}
	
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user_authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", lastname=" + lastname + ", username=" + username + ", password="
				+ password + "]";
	}
	
	

}
