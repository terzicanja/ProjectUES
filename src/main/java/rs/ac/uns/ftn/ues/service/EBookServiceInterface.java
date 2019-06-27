package rs.ac.uns.ftn.ues.service;

import java.util.List;
import rs.ac.uns.ftn.ues.entity.EBook;

public interface EBookServiceInterface {
	
	List<EBook> findAll();
	
	List<EBook> findAllByCategory_Id(Integer id);
	
	List<EBook> findAllByLanguage_Id(Integer id);
	
	List<EBook> findAllByUser_Id(Integer id);
	
	EBook findOne(Integer id);
	
	EBook findByFilename(String filename);
	
	EBook save(EBook ebook);
	
	void remove(Integer id);

}
