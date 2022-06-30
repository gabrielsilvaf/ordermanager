package net.gabrielsilvaf.ordermanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.gabrielsilvaf.ordermanager.model.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long>{

	@Query("select coalesce(sum(s.quantity), 0) from StockMovement s where s.item.id = :#{#itemId} and s.completed = false ")
	Integer getQuantityByItem(Long itemId);

	@Query("select coalesce(sum(s.quantityUsed), 0) from OrderStockMovement s join s.stockMovement m where m.item.id = :#{#itemId} and m.completed = false ")
	Integer getQuantityUsedOnCompletedOrderByItem(Long itemId);
	
	@Query("select s from StockMovement s where s.item.id = :#{#itemId} and s.completed = false  ")
	List<StockMovement> getStockMovementsNotCompletedByItem(Long itemId);
}
