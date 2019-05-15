package rs.ac.uns.ftn.ues.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.ues.entity.EBook;

public interface EBookRepository extends JpaRepository<EBook, Integer> {
	
	List<EBook> findAll();
	
	List<EBook> findAllByCategory_Id(Integer id);
	

}
