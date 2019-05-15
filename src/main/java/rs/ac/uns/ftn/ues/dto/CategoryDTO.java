package rs.ac.uns.ftn.ues.dto;

import java.io.Serializable;

import rs.ac.uns.ftn.ues.entity.Category;

public class CategoryDTO implements Serializable {
	
	private Integer id;
	private String name;
	
	public CategoryDTO() {
		super();
	}

	public CategoryDTO(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public CategoryDTO(Category category) {
		this(category.getId(), category.getName());
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
		return "CategoryDTO [id=" + id + ", name=" + name + "]";
	}
	
	
	
	

}
