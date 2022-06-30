package net.gabrielsilvaf.ordermanager.controller.form;

import net.gabrielsilvaf.ordermanager.model.Item;

public class ItemForm {

	private String name;


	public Item converter() {
		Item Item = new Item();
		Item.setName(this.name);

		return Item;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
