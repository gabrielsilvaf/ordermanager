package net.gabrielsilvaf.ordermanager.model.id;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class OrderStockMovementId implements Serializable {

	private static final long serialVersionUID = -2202139287592376237L;

	private Long orderId;

	private Long stockMovementId;

	public OrderStockMovementId() {

	}

	public OrderStockMovementId(Long orderId, Long stockMovementId) {
		this.orderId = orderId;
		this.stockMovementId = stockMovementId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getStockMovementId() {
		return stockMovementId;
	}

	public void setStockMovementId(Long stockMovementId) {
		this.stockMovementId = stockMovementId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, stockMovementId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderStockMovementId other = (OrderStockMovementId) obj;
		return Objects.equals(orderId, other.orderId) && Objects.equals(stockMovementId, other.stockMovementId);
	}

}
