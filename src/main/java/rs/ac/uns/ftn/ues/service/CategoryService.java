package rs.ac.uns.ftn.ues.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.ues.entity.Category;
import rs.ac.uns.ftn.ues.repository.CategoryRepository;

@Service
public class CategoryService implements CategoryServiceInterface {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category findOne(Integer id) {
		return categoryRepository.findById(id).orElse(null);
	}

	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public void remove(Integer id) {
		categoryRepository.deleteById(id);
	}
	
	

}
