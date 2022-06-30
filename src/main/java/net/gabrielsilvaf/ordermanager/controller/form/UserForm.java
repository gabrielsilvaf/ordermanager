package net.gabrielsilvaf.ordermanager.controller.form;

import net.gabrielsilvaf.ordermanager.model.User;

public class UserForm {

	private String name;

	private String email;

	public User converter() {
		User user = new User();
		user.setEmail(this.email);
		user.setName(this.name);

		return user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
