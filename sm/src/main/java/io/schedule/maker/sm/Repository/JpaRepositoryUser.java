
package io.schedule.maker.sm.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.schedule.maker.sm.model.User;

 
public interface JpaRepositoryUser  extends JpaRepository<User,Integer>{
	
	Optional<User> findByUserName(String UserName);

}
