package net.gabrielsilvaf.ordermanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.gabrielsilvaf.ordermanager.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	
	
}
