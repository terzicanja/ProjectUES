package rs.ac.uns.ftn.ues.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.ues.repository.EBookRepository;
import rs.ac.uns.ftn.ues.entity.EBook;

@Service
public class EBookService implements EBookServiceInterface {
	
	@Autowired
	EBookRepository ebookRepository;
	
	@Override
	public List<EBook> findAll(){
		return ebookRepository.findAll();
	}
	
	@Override
	public List<EBook> findAllByCategory_Id(Integer id) {
		return ebookRepository.findAllByCategory_Id(id);
	}

	@Override
	public EBook findOne(Integer id) {
//		return ebookRepository.getOne(id);
		return ebookRepository.findById(id).orElse(null);
	}

	@Override
	public EBook save(EBook ebook) {
		return ebookRepository.save(ebook);
	}

	@Override
	public void remove(Integer id) {
		ebookRepository.deleteById(id);
	}

	@Override
	public EBook findByFilename(String filename) {
		return ebookRepository.findByFilename(filename);
	}

	@Override
	public List<EBook> findAllByUser_Id(Integer id) {
		return ebookRepository.findAllByUser_Id(id);
	}

	@Override
	public List<EBook> findAllByLanguage_Id(Integer id) {
		return ebookRepository.findAllByLanguage_Id(id);
	}

}
