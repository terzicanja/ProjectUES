package rs.ac.uns.ftn.ues.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.ues.dto.CategoryDTO;
import rs.ac.uns.ftn.ues.dto.EBookDTO;
import rs.ac.uns.ftn.ues.entity.Category;
import rs.ac.uns.ftn.ues.entity.EBook;
import rs.ac.uns.ftn.ues.service.CategoryServiceInterface;
import rs.ac.uns.ftn.ues.service.EBookServiceInterface;

@RestController
@RequestMapping(value = "api/category")
public class CategoryController {
	
	@Autowired
	private CategoryServiceInterface categoryService;
	
	@Autowired
	private EBookServiceInterface ebookService;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> getCategories(){
		List<Category> categories = categoryService.findAll();
		List<CategoryDTO> categoryDTO = new ArrayList<CategoryDTO>();
		for(Category c : categories) {
			categoryDTO.add(new CategoryDTO(c));
		}
		return new ResponseEntity<List<CategoryDTO>>(categoryDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> getOne(@PathVariable("id") Integer id){
		Category c = categoryService.findOne(id);
		if (c == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<CategoryDTO>(new CategoryDTO(c), HttpStatus.OK);
		}
	}
	
	
	@GetMapping(value = "/{id}/books")
	public ResponseEntity<List<EBookDTO>> getBooksByCategory(@PathVariable("id") Integer id){
		List<EBook> books = ebookService.findAllByCategory_Id(id);
		List<EBookDTO> booksDTO = new ArrayList<EBookDTO>();
		for(EBook e : books) {
			booksDTO.add(new EBookDTO(e));
		}
		return new ResponseEntity<List<EBookDTO>>(booksDTO, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDTO){
		Category category = new Category();
		category.setName(categoryDTO.getName());
		
		category = categoryService.save(category);
		return new ResponseEntity<CategoryDTO>(new CategoryDTO(category), HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "/update/{id}", consumes = "application/json")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable("id") Integer id){
		Category category = categoryService.findOne(id);

		if (category == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			category.setName(categoryDTO.getName());
			
			category = categoryService.save(category);
			return new ResponseEntity<CategoryDTO>(new CategoryDTO(category), HttpStatus.CREATED);
		}
		
//		category.setName(categoryDTO.getName());
//		
//		category = categoryService.save(category);
//		return new ResponseEntity<CategoryDTO>(new CategoryDTO(category), HttpStatus.CREATED);
	}
	
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable("id") Integer id){
		Category category = categoryService.findOne(id);
		
		if(category != null) {
			categoryService.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
