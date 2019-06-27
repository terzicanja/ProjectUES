package rs.ac.uns.ftn.ues.service;

import java.util.List;

import rs.ac.uns.ftn.ues.entity.User;

public interface UserServiceInterface {
	
	List<User> findAll();
	
	List<User> findAllByCategory_id(Integer id);
	
//	User findOne(Integer id);
	
	User findByUsername(String username);
	
	User save(User user);
	
	void remove(Integer id);

//	User findOne(String username);

}
