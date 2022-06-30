package net.gabrielsilvaf.ordermanager.controller.form;

import java.util.Date;

public class OrderForm {
	
	private Date creationDate;
	
	private Long item;
	
	private Integer quantity;
	
	private Long user;

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

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}
	
	
	
	

}
