package net.gabrielsilvaf.ordermanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.gabrielsilvaf.ordermanager.model.OrderStockMovement;
import net.gabrielsilvaf.ordermanager.model.id.OrderStockMovementId;

public interface OrderStockMovementRepository extends JpaRepository<OrderStockMovement, OrderStockMovementId>{

	
	
}
