package net.gabrielsilvaf.ordermanager.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.gabrielsilvaf.ordermanager.controller.form.ItemForm;
import net.gabrielsilvaf.ordermanager.model.Item;
import net.gabrielsilvaf.ordermanager.repository.ItemRepository;

@Service
@Transactional
public class ItemService {
	
	@Autowired
	ItemRepository itemRepository;
	
	public List<Item> listAll() {
		return itemRepository.findAll();
	}

	
	public Item create(ItemForm form) {
		
		Item item= form.converter();
		itemRepository.save(item);
		
		return item;
	}


	public Optional<Item> findById(Long id) {		
		
		return itemRepository.findById(id);
	}


	public void deleteById(Long id) {
		
		itemRepository.deleteById(id);
	}
	
	
	public Item update(ItemForm form, Long id) {
		
		Optional<Item> item = itemRepository.findById(id);
		
		if(!item.isPresent())
			return null;
		
		item.get().setName(form.getName());		
		
		return itemRepository.save(item.get());
	}
	
	 
}
