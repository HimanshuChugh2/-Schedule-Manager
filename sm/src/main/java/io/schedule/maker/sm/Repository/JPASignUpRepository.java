package io.schedule.maker.sm.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.schedule.maker.sm.model.SignUpUserDetails;
import io.schedule.maker.sm.model.User;

public interface JPASignUpRepository extends JpaRepository<SignUpUserDetails,Integer> {
	
	Optional<SignUpUserDetails> findByUsername(String username);
	
	
	
}
