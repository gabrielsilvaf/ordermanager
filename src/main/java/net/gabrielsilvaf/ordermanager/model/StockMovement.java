package net.gabrielsilvaf.ordermanager.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "OM_STOCK_MOVEMENT")
public class StockMovement implements Serializable {

	private static final long serialVersionUID = -16401735070679237L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private Date creationDate;

	@ManyToOne(optional = false)
	private Item item;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false)
	private Boolean completed;

	@JsonIgnore
	@OneToMany(mappedBy = "stockMovement",fetch = FetchType.LAZY)
	private List<OrderStockMovement> orders;
	
	public Integer getAvailableQuantity() {
		
		if (orders != null) {
			Optional<Integer> used =  orders.stream().map(a->a.getQuantityUsed()).reduce(Integer::sum);
			
			if(used.isPresent()) {
				return this.quantity - used.get();
			} 
		}
		
		
		return this.quantity;		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public List<OrderStockMovement> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderStockMovement> orders) {
		this.orders = orders;
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
		StockMovement other = (StockMovement) obj;
		return Objects.equals(id, other.id);
	}

}
