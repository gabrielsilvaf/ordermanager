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

import net.gabrielsilvaf.ordermanager.controller.form.ItemForm;
import net.gabrielsilvaf.ordermanager.model.Item;
import net.gabrielsilvaf.ordermanager.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemController {
	
	@Autowired
	ItemService itemService;
	

	@GetMapping("/")
	@ResponseBody
	public List<Item> listItems() {

		return itemService.listAll();			
	}

	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Item> listItemById(@PathVariable Long id) {

		Optional<Item> item =  itemService.findById(id);

		if (item.isPresent()) {
			return ResponseEntity.ok(item.get());

		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<Item> create(@RequestBody(required = true) ItemForm form, UriComponentsBuilder uriBuilder) {
		
		Item item = itemService.create(form);		
		URI uri = uriBuilder.path("/items/{id}").buildAndExpand(item.getId()).toUri();		
		
		return ResponseEntity.created(uri).body(item);
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Item> delete(@PathVariable Long id) {

		Optional<Item> item =  itemService.findById(id);

		if (item.isPresent()) {
			
			itemService.deleteById(id);
			
			return ResponseEntity.ok().build();

		} else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@PutMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Item> update(@PathVariable Long id,  @RequestBody(required = true) ItemForm form, UriComponentsBuilder uriBuilder) {
		
		Item item =  itemService.update(form, id);
		
		if (item == null )
			return ResponseEntity.notFound().build();
		
		
		URI uri = uriBuilder.path("/items/{id}").buildAndExpand(item.getId()).toUri();		
		
		return ResponseEntity.created(uri).body(item);

	}
	
	

}
