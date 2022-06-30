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

import net.gabrielsilvaf.ordermanager.controller.form.OrderForm;
import net.gabrielsilvaf.ordermanager.model.Item;
import net.gabrielsilvaf.ordermanager.model.Order;
import net.gabrielsilvaf.ordermanager.model.OrderStockMovement;
import net.gabrielsilvaf.ordermanager.model.StockMovement;
import net.gabrielsilvaf.ordermanager.model.User;
import net.gabrielsilvaf.ordermanager.repository.ItemRepository;
import net.gabrielsilvaf.ordermanager.repository.OrderRepository;
import net.gabrielsilvaf.ordermanager.repository.OrderStockMovementRepository;
import net.gabrielsilvaf.ordermanager.repository.StockMovementRepository;
import net.gabrielsilvaf.ordermanager.repository.UserRepository;

@Service
@Transactional
public class OrderService {
	
    private static final Logger logger = LogManager.getLogger(OrderService.class);

	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StockMovementRepository stockRepository;
	
	@Autowired
	private OrderStockMovementRepository orderStockRepository;
	
	@Autowired
	EmailService emailService;
	
	
	public List<Order> listAll() {
		
		return orderRepository.findAll();
	}

	
	public Order create(OrderForm form) throws IOException {
		
		Order order = new Order();
		
		order.setQuantity(form.getQuantity());
		order.setCreationDate(form.getCreationDate());
		
		
		Optional<Item> item = itemRepository.findById(form.getItem());

		if(item.isPresent()) {
			order.setItem(item.get());
			
		} else {
			throw new IOException("Item not found");
		}
		
		Optional<User> user = userRepository.findById(form.getUser());

		if(user.isPresent()) {
			order.setUser(user.get());		
			
		} else {
			throw new IOException("Item not found");
		}
		
		order.setCompleted(false);
		orderRepository.save(order);

		//Try to complete the order
		completeOrder(order);
		
		
		return order;
	}
	
	private void completeOrder(Order order) {
		
		if (order.getQuantity() <= stockAvaiableByItem(order.getItem().getId())) {
			
			List<StockMovement> stocks = stockRepository.getStockMovementsNotCompletedByItem(order.getItem().getId());
			
			Integer orderQuantity = order.getQuantity();
			
			for (StockMovement stock : stocks) {
				//if order quantity is bigger than the current stock quantity, continues to the next until the order is completed
				Integer stockAvailableQuantity = stock.getAvailableQuantity();
				if (orderQuantity > stockAvailableQuantity){
					OrderStockMovement movement = new OrderStockMovement(order, stock, stockAvailableQuantity);
					orderStockRepository.save(movement);

					//Complete the stockMovement					
					stock.getOrders().add(movement);
					stock.setCompleted(true);
					stockRepository.save(stock);
					
					orderQuantity = orderQuantity - stockAvailableQuantity;
					
				} else {
					
					OrderStockMovement movement = new OrderStockMovement(order, stock, orderQuantity);
					orderStockRepository.save(movement);
					
					//if the stock quantity is equals the remaining order quantity, than it has been completed
					if (orderQuantity == stockAvailableQuantity ) {
						stock.setCompleted(true);
						logger.info("Stock Movement " + stock.getId() + " completed");

						stockRepository.save(stock);
					}
					
					order.setCompleted(true);
						
					logger.info("Order " + order.getId() + " completed");

					
					//Send email to the user 
					emailService.sendEmailOrderConfirmation(order);
					
					break;
				}				
			}			
		} 
	}
	
	private Integer stockAvaiableByItem(Long itemId) {
		
		Integer total = stockRepository.getQuantityByItem(itemId);		
		Integer used = stockRepository.getQuantityUsedOnCompletedOrderByItem(itemId);
		
		return total - used;
	}
	
	public void findAndTryToCompletedPendingOrdersByItem(Long itemId) {
		
		List<Order> orders =  orderRepository.getPendingOrdersByItem(itemId);
		
		if(!CollectionUtils.isEmpty(orders)) {
			orders.stream().forEach(order->{
				//Try to complete every pending order
				completeOrder(order);
			});
		}
		
	}


	public Optional<Order> findById(Long id) {		
		
		return orderRepository.findById(id);
	}


	public void deleteById(Long id) throws IOException {
		
		Optional<Order> order = orderRepository.findById(id);
		
		if(order.isPresent()) {
			if (order.get().getCompleted()) {
				throw new IOException("You can't delete a completed order");
			}
			
			orderRepository.deleteById(id);			
			
		} else {
			throw new IOException("Order not found");
		}		
	}
	
	
	public Order update(OrderForm form, Long id) throws IOException {
		
		Optional<Order> order = orderRepository.findById(id);
		
		
		if(!order.isPresent())
			return null;
		
		if (order.get().getCompleted()) {
			throw new IOException("You can't edit a completed order");
		}
		
		//Item changed
		if(order.get().getItem().getId() != form.getItem()) {
			Optional<Item> item = itemRepository.findById(form.getItem());

			if(item.isPresent()) {
				order.get().setItem(item.get());		
							
			} else {
				throw new IOException("Item not found");
			}
		}
		
		order.get().setQuantity(form.getQuantity());
		
		orderRepository.save(order.get());
		
		//as the quantity and item changed, try to complete the order with the new values
		completeOrder(order.get());
	
		return order.get();
	}
	
	 
}
