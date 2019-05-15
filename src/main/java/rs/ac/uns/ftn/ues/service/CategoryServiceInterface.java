package rs.ac.uns.ftn.ues.service;

import java.util.List;

import rs.ac.uns.ftn.ues.entity.Category;

public interface CategoryServiceInterface {
	
	List<Category> findAll();
	
	Category findOne(Integer id);
	
	Category save(Category category);
	
	void remove(Integer id);

}
