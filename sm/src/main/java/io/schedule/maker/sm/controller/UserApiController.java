package io.schedule.maker.sm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.schedule.maker.sm.Repository.JPASignUpRepository;
import io.schedule.maker.sm.Repository.RepositoryTimeFactors;
import io.schedule.maker.sm.Repository.TimeFactorLabelValueInterface;
import io.schedule.maker.sm.model.SignUpUserDetails;
import io.schedule.maker.sm.model.TimeFactors;


@RestController
@RequestMapping("user-us/user")
public class UserApiController {
	
	@Autowired
	RepositoryTimeFactors object;
	
	@Autowired
	JPASignUpRepository JPASignUpRepositoryObject;
	@GetMapping
	public List<SignUpUserDetails> getString()
	{
		return JPASignUpRepositoryObject.findAll();
	}
	
	
	@GetMapping("/{username}")
	public Optional<SignUpUserDetails> returnFactorLabelValueInterfaces (@PathVariable("username") String username)
	{
		
		return JPASignUpRepositoryObject.findByUsername(username);
	}
	
	 
 

	@PostMapping(consumes = "application/json")
	 public void reTimeFactorLabelValueInterfaces(@RequestBody SignUpUserDetails signUpUserDetails)
	{
   		
		 JPASignUpRepositoryObject.save(signUpUserDetails);
		 
		
	}
}
