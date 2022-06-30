package net.gabrielsilvaf.ordermanager.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.gabrielsilvaf.ordermanager.controller.form.UserForm;
import net.gabrielsilvaf.ordermanager.model.User;
import net.gabrielsilvaf.ordermanager.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public List<User> listAll() {
		return userRepository.findAll();
	}

	
	public User create(UserForm form) {
		
		User user= form.converter();
		userRepository.save(user);
		
		return user;
	}


	public Optional<User> findById(Long id) {		
		
		return userRepository.findById(id);
	}


	public void deleteById(Long id) {
		
		userRepository.deleteById(id);
	}
	
	
	public User update(UserForm form, Long id) {
		
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent())
			return null;
		
		user.get().setEmail(form.getEmail());		
		user.get().setName(form.getName());		
		
		return userRepository.save(user.get());
	}
	
	 
}
