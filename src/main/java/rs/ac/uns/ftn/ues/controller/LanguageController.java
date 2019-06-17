package rs.ac.uns.ftn.ues.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.ues.dto.LanguageDTO;
import rs.ac.uns.ftn.ues.entity.Language;
import rs.ac.uns.ftn.ues.service.LanguageServiceInterface;

@RestController
@RequestMapping(value = "/api/languages")
public class LanguageController {
	
	@Autowired
	private LanguageServiceInterface langService;
	
	@GetMapping
	public ResponseEntity<List<LanguageDTO>> getAll(){
		List<Language> langs = langService.findAll();
		List<LanguageDTO> langDTOs = new ArrayList<>();
		for(Language l : langs) {
			langDTOs.add(new LanguageDTO(l));
		}
		return new ResponseEntity<List<LanguageDTO>>(langDTOs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<LanguageDTO> getOne(@PathVariable("id") Integer id){
		Language l = langService.findOne(id);
		
		if(l==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<LanguageDTO>(new LanguageDTO(l), HttpStatus.OK);
		}
	}
	
	
	

}
