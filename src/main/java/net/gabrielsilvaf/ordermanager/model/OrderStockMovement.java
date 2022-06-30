package net.gabrielsilvaf.ordermanager.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import net.gabrielsilvaf.ordermanager.model.id.OrderStockMovementId;

@Entity
@Table(name = "OM_ORDER_STOCKMOVEMENT")
public class OrderStockMovement implements Serializable {

	private static final long serialVersionUID = -6053874733936208596L;

	@EmbeddedId
	private OrderStockMovementId id;

	@ManyToOne
	@MapsId("orderId")
	private Order order;

	@ManyToOne
	@MapsId("stockMovementId")
	private StockMovement stockMovement;

	@Column(nullable = false)
	private Integer quantityUsed;
	
	

	public OrderStockMovement() {
	}
	
	public OrderStockMovement(Long orderId, Long stockMovementId, Integer quantityUsed) {
		this.id = new OrderStockMovementId(orderId, stockMovementId);
		this.quantityUsed = quantityUsed;
	}
	
	public OrderStockMovement(Order order, StockMovement stockMovement, Integer quantityUsed) {
		this.id = new OrderStockMovementId(order.getId(), stockMovement.getId());
		this.order = order;
		this.stockMovement = stockMovement;
		this.quantityUsed = quantityUsed;
	}

	public OrderStockMovementId getId() {
		return id;
	}

	public void setId(OrderStockMovementId id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public StockMovement getStockMovement() {
		return stockMovement;
	}

	public void setStockMovement(StockMovement stockMovement) {
		this.stockMovement = stockMovement;
	}

	public Integer getQuantityUsed() {
		return quantityUsed;
	}

	public void setQuantityUsed(Integer quantityUsed) {
		this.quantityUsed = quantityUsed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderStockMovement other = (OrderStockMovement) obj;
		return Objects.equals(id, other.id);
	}

}
