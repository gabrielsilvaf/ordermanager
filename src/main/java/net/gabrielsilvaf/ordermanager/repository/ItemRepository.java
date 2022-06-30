package net.gabrielsilvaf.ordermanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.gabrielsilvaf.ordermanager.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{

	
	
}
