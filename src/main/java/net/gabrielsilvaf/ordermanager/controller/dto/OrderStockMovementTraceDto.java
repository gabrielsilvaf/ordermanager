package net.gabrielsilvaf.ordermanager.controller.dto;

public class OrderStockMovementTraceDto {

	private Long id;
	private Integer quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
