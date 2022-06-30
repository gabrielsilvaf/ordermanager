package net.gabrielsilvaf.ordermanager.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import net.gabrielsilvaf.ordermanager.controller.form.UserForm;
import net.gabrielsilvaf.ordermanager.model.User;
import net.gabrielsilvaf.ordermanager.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService userService;
	

	@GetMapping("/")
	@ResponseBody
	public List<User> listUsers() {

		return userService.listAll();			
	}

	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<User> listUserById(@PathVariable Long id) {

		Optional<User> user =  userService.findById(id);

		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());

		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<User> create(@RequestBody(required = true) UserForm form, UriComponentsBuilder uriBuilder) {
		
		User user = userService.create(form);		
		URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();		
		
		return ResponseEntity.created(uri).body(user);
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<User> delete(@PathVariable Long id) {

		Optional<User> user =  userService.findById(id);

		if (user.isPresent()) {
			userService.deleteById(id);

			return ResponseEntity.ok().build();

		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	@ResponseBody
	public ResponseEntity<User> update(@PathVariable Long id,  @RequestBody(required = true) UserForm form, UriComponentsBuilder uriBuilder) {
		
		User user =  userService.update(form, id);
		
		if (user == null )
			return ResponseEntity.notFound().build();
		
		
		URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();		
		
		return ResponseEntity.created(uri).body(user);

	}
	
	

}
