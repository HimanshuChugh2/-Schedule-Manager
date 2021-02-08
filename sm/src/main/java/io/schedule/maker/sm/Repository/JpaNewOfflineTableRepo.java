package io.schedule.maker.sm.Repository;

 
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.schedule.maker.sm.model.newofflinetable;

public interface JpaNewOfflineTableRepo extends JpaRepository<newofflinetable,Integer> {

 
	// for inserting the above, we must delete the previous schedule details of this person
		@Modifying		// THIS IS IMPORTANT FOR INSERT QUERY
		@Transactional  // THIS IS IMPORTANT FOR INSERT QUERY
		@Query(value = "delete from newofflinetable where username = :username", nativeQuery = true)
		void deleteUser (@Param("username") String username);
		// this is a select statement, it will return something, so we have to store the returned value into something, so now for this we dont have an entity to store the values, we will create an interface with the field value being same as defined in the query
		@Query(value="select label as label, value as value,username as username from newofflinetable where username= :username", nativeQuery = true)
		public List<TimeFactorLabelValueInterface> getLabelAndValueByUsername(@Param("username") String username);
		
		
		@Query(value="select * from newofflinetable", nativeQuery = true)
		public List<TimeFactorLabelValueInterface> getAllLabelAndValue();	
		 
		@Query(value="select * from newofflinetable where username = :username", nativeQuery = true)
		public List<TimeFactorLabelValueInterface> getByUsername(@Param("username") String username );	
	
}
