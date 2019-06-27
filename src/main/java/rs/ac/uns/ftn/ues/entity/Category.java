package rs.ac.uns.ftn.ues.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "name", unique = false, nullable = true)
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="category")
	private List<EBook> ebooks = new ArrayList<EBook>();
	
	public Category() {
		
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

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
	

}
