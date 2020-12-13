package io.schedule.maker.sm.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.schedule.maker.sm.model.Feedback;
import io.schedule.maker.sm.model.SignUpUserDetails;

public interface JpaFeedbackRepository extends JpaRepository<Feedback,Integer>{

  	 
    	
  	@Query(value="select *from feedback where username= :username", nativeQuery = true)
  	List<Feedback> findByUsername(@Param("username") String username);
  	@Modifying		// THIS IS IMPORTANT FOR delete QUERY
	@Transactional  // THIS IS IMPORTANT FOR delete QUERY
  	@Query(value="delete from feedback where username = :username", nativeQuery = true)
  	void deleteByUsername (@Param("username") String username);
  	
  	
   	
}
