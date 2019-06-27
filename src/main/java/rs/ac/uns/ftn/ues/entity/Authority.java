package rs.ac.uns.ftn.ues.entity;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "authority")
public class Authority implements GrantedAuthority {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name="name")
	String name;
	
	public Authority() {}
	
	@Override
	public String getAuthority() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
