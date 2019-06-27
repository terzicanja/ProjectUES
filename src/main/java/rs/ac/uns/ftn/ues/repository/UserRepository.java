package rs.ac.uns.ftn.ues.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.ues.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUsername(String username);
	
	List<User> findAllByCategory_id(Integer id);

}
