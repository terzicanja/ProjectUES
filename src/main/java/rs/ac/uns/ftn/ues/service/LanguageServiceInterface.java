package rs.ac.uns.ftn.ues.service;

import java.util.List;

import rs.ac.uns.ftn.ues.entity.Language;

public interface LanguageServiceInterface {
	
	List<Language> findAll();
	
	Language findOne(Integer id);

}
