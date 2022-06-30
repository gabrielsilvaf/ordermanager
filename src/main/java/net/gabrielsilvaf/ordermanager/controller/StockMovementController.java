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
import net.gabrielsilvaf.ordermanager.controller.form.StockMovementForm;
import net.gabrielsilvaf.ordermanager.model.StockMovement;
import net.gabrielsilvaf.ordermanager.service.StockMovementService;

@RestController
@RequestMapping("/stocks")
public class StockMovementController {
	
	@Autowired
	StockMovementService stockService;
	

	@GetMapping("/")
	@ResponseBody
	public List<StockMovement> listStockMovements() {

		return stockService.listAll();			
	}

	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<StockMovement> listStockMovementById(@PathVariable Long id) {

		Optional<StockMovement> stock =  stockService.findById(id);

		if (stock.isPresent()) {
			return ResponseEntity.ok(stock.get());

		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<StockMovement> create(@RequestBody(required = true) StockMovementForm form, UriComponentsBuilder uriBuilder) throws IOException {
		
		StockMovement stock = stockService.create(form);
		
		URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(stock.getId()).toUri();		
		
		return ResponseEntity.created(uri).body(stock);	
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<StockMovement> delete(@PathVariable Long id) throws IOException {
				
		stockService.deleteById(id);
		
		return ResponseEntity.ok().build();

	}
	
	@PutMapping("/{id}")
	@ResponseBody
	public ResponseEntity<StockMovement> update(@PathVariable Long id,  @RequestBody(required = true) StockMovementForm form, UriComponentsBuilder uriBuilder) throws IOException {
		
		StockMovement stock =  stockService.update(form, id);
		
		if (stock == null )
			return ResponseEntity.notFound().build();
		
		
		URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(stock.getId()).toUri();		
		
		return ResponseEntity.created(uri).body(stock);

	}
	
	@GetMapping("/{id}/trace-orders")
	@ResponseBody
	public ResponseEntity<List<OrderStockMovementTraceDto>> traceStockMovements(@PathVariable Long id) {

		Optional<StockMovement> stock =  stockService.findById(id);
					
		if (stock.isPresent() && stock.get().getOrders() != null) {
			
			List<OrderStockMovementTraceDto> dtos =  stock.get().getOrders().stream().map(s->{
													OrderStockMovementTraceDto dto = new OrderStockMovementTraceDto();
													dto.setId(s.getOrder().getId());
													dto.setQuantity(s.getQuantityUsed());
													
													return dto;				
												}).collect(Collectors.toList());
			
			
			return ResponseEntity.ok(dtos);

		} else {
			return ResponseEntity.notFound().build();
		}		
	}
	
	

}
