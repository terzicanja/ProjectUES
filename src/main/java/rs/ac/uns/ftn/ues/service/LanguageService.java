package rs.ac.uns.ftn.ues.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.ues.entity.Language;
import rs.ac.uns.ftn.ues.repository.LanguageRepository;

@Service
public class LanguageService implements LanguageServiceInterface {
	
	@Autowired
	LanguageRepository langRepo;

	@Override
	public List<Language> findAll() {
		return langRepo.findAll();
	}

	@Override
	public Language findOne(Integer id) {
		return langRepo.findById(id).orElse(null);
	}

}
