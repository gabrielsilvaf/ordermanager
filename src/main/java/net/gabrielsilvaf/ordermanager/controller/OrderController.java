package net.gabrielsilvaf.ordermanager.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import net.gabrielsilvaf.ordermanager.controller.dto.OrderStockMovementTraceDto;
import net.gabrielsilvaf.ordermanager.controller.form.OrderForm;
import net.gabrielsilvaf.ordermanager.model.Order;
import net.gabrielsilvaf.ordermanager.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	

	@GetMapping("/")
	@ResponseBody
	public List<Order> listOrders() {

		return orderService.listAll();			
	}

	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Order> listOrderById(@PathVariable Long id) {

		Optional<Order> order =  orderService.findById(id);

		if (order.isPresent()) {
			return ResponseEntity.ok(order.get());

		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<Order> create(@RequestBody(required = true) OrderForm form, UriComponentsBuilder uriBuilder) throws IOException {
		
		Order order = orderService.create(form);		
		URI uri = uriBuilder.path("/orders/{id}").buildAndExpand(order.getId()).toUri();		
		
		return ResponseEntity.created(uri).body(order);
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Order> delete(@PathVariable Long id) throws IOException {

		orderService.deleteById(id);
		
		return ResponseEntity.ok().build();

	}
	
	@PutMapping("/{id}")
	@ResponseBody
	public ResponseEntity<Order> update(@PathVariable Long id,  @RequestBody(required = true) OrderForm form, UriComponentsBuilder uriBuilder) throws IOException {
		
		Order order =  orderService.update(form, id);
		
		if (order == null )
			return ResponseEntity.notFound().build();
		
		
		URI uri = uriBuilder.path("/orders/{id}").buildAndExpand(order.getId()).toUri();		
		
		return ResponseEntity.created(uri).body(order);

	}
	
	@GetMapping("/{id}/trace-stocks")
	@ResponseBody
	public ResponseEntity<List<OrderStockMovementTraceDto>> traceStockMovements(@PathVariable Long id) {

		Optional<Order> order =  orderService.findById(id);
		
		if (order.isPresent() && order.get().getStocks() != null) {
			
			List<OrderStockMovementTraceDto> dtos =  order.get().getStocks().stream().map(s->{
													OrderStockMovementTraceDto dto = new OrderStockMovementTraceDto();
													dto.setId(s.getStockMovement().getId());
													dto.setQuantity(s.getQuantityUsed());
													
													return dto;				
												}).collect(Collectors.toList());
			
			
			return ResponseEntity.ok(dtos);

		} else {
			return ResponseEntity.notFound().build();
		}		
	}
}
