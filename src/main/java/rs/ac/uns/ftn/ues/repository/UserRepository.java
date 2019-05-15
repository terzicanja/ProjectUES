package rs.ac.uns.ftn.ues.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.ues.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUsername(String username);

}
