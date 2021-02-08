package io.schedule.maker.sm.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.schedule.maker.sm.model.TimeFactors;

public interface RepositoryTimeFactors extends JpaRepository<TimeFactors,Integer> {
	Optional<TimeFactors> findByUsername(String username);

	 
	// we can make the old table which contains only entity item, a table which holds the details of the usernames who currently have any schedule made in the database
	/*
	 * @Modifying // THIS IS IMPORTANT FOR INSERT QUERY
	 * 
	 * @Transactional // THIS IS IMPORTANT FOR INSERT QUERY
	 * 
	 * @Query( value =
	 * "insert into newofflinetable values :label, :value, :username", nativeQuery =
	 * true) void insertUser(@Param("label") String label, @Param("value") Long
	 * value,
	 * 
	 * @Param("username") String username);
	 */
	
	
	
	
}
