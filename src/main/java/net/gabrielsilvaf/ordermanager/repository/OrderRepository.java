package net.gabrielsilvaf.ordermanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.gabrielsilvaf.ordermanager.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query("select o from Order o where o.item.id = :#{#itemId} and o.completed = false  ")
	List<Order> getPendingOrdersByItem(Long itemId);
	
}
