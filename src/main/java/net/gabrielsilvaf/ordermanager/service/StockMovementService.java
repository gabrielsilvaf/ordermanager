package net.gabrielsilvaf.ordermanager.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import net.gabrielsilvaf.ordermanager.controller.form.StockMovementForm;
import net.gabrielsilvaf.ordermanager.model.Item;
import net.gabrielsilvaf.ordermanager.model.StockMovement;
import net.gabrielsilvaf.ordermanager.repository.ItemRepository;
import net.gabrielsilvaf.ordermanager.repository.StockMovementRepository;

@Service
@Transactional
public class StockMovementService {
	
    private static final Logger logger = LogManager.getLogger(StockMovementService.class);

	
	@Autowired
	StockMovementRepository stockRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	@Autowired
	OrderService orderService;
	
	public List<StockMovement> listAll() {
		return stockRepository.findAll();
	}

	
	public StockMovement create(StockMovementForm form) throws IOException {
		
		StockMovement stock = new StockMovement();
		
		stock.setQuantity(form.getQuantity());
		stock.setCreationDate(form.getCreationDate());
		
		stock.setCompleted(false);
		
		
		Optional<Item> item = itemRepository.findById(form.getItem());

		if(item.isPresent()) {
			stock.setItem(item.get());		
			
			stockRepository.save(stock);
			
			//Try to complete the pending orders with this item
			orderService.findAndTryToCompletedPendingOrdersByItem(stock.getItem().getId());
			
			return stock;
		
		} else {
			throw new IOException("Item not found");
		}
	}


	public Optional<StockMovement> findById(Long id) {		
		
		return stockRepository.findById(id);
	}


	public void deleteById(Long id) throws IOException {
		Optional<StockMovement> stock = stockRepository.findById(id);
		
		if (stock.isPresent()) {
			if (!CollectionUtils.isEmpty(stock.get().getOrders()) && stock.get().getOrders().size() > 0) {
				throw new IOException("You can't delete a stock movement associated with a completed order");
			}
			
			stockRepository.deleteById(id);

		} else {
			
			throw new IOException("Stock Movement not found");
		}		
	}
	
	
	public StockMovement update(StockMovementForm form, Long id) throws IOException {
		
		Optional<StockMovement> stock = stockRepository.findById(id);		
		
		
		if(!stock.isPresent())
			return null;
		
		if(stock.get().getCompleted()) {
			throw new IOException("You can't update a completed stock movement");

		}
		
		// Verify if there is a completed order associated to the stock movement
		// In that case, prevent the update if the item is changed or quantity is lower than the used on the orders
		Boolean hasCompletedOrder = !CollectionUtils.isEmpty(stock.get().getOrders()) && stock.get().getOrders().size() > 0;
				
		
		//Item changed
		if(stock.get().getItem().getId() != form.getItem()) {
			
			if (hasCompletedOrder) {
				throw new IOException("Can't change the item if there is a completed order associated with the stock movement");

			}
			
			Optional<Item> item = itemRepository.findById(form.getItem());

			if(item.isPresent()) {
				stock.get().setItem(item.get());		
							
			} else {
				throw new IOException("Item not found");
			}
		}
		
		stock.get().setQuantity(form.getQuantity());
		
		//if available Quantity is zero than the stock movement is completed
		if(stock.get().getAvailableQuantity() == 0) {
			stock.get().setCompleted(true);
			
			logger.info("Stock Movement " + stock.get().getId() + " completed");

		
		//if its less than zero the order can't be updated because of the completed order(s)
		} else if (stock.get().getAvailableQuantity() < 0) {
			throw new IOException("The stock movement quantity cannot be less than the used on completed orders");

		}
		
	
		return stockRepository.save(stock.get());
	}
	
	 
}
