package net.gabrielsilvaf.ordermanager.controller.form;

import java.util.Date;

public class StockMovementForm {
	
	private Date creationDate;
	
	private Long item;
	
	private Integer quantity;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getItem() {
		return item;
	}

	public void setItem(Long item) {
		this.item = item;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	

}
