package io.schedule.maker.sm.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.schedule.maker.sm.Repository.JPASignUpRepository;
import io.schedule.maker.sm.model.SignUpUserDetails;

@Controller
public class SignUpController {
	@Autowired
	JPASignUpRepository jpaSignUpRepository;
	@GetMapping("/sign-up")
	public String signUp(SignUpUserDetails signUpUser,Model model) {
		SignUpUserDetails objDetails=new SignUpUserDetails();
		model.addAttribute("signUpUser",objDetails);
		
		return "sign-up";
	}
	
	
	@RequestMapping(value = "sign-up", method = RequestMethod.POST)
	public String signUpPost(@ModelAttribute("signUpUser") @Valid SignUpUserDetails signUpUser,BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors()) {
			System.out.println("in E R R O R");
			return "sign-up";
		}
		
 
		Optional<SignUpUserDetails> signUpObj = jpaSignUpRepository.findByUsername(signUpUser.getUsername());

		if (signUpObj.isPresent()) {
			model.addAttribute("message",
					"Username" + signUpUser.getUsername() + "already exists, try with some other username");
		} else {
			jpaSignUpRepository.save(signUpUser);
			model.put("message", "User added successfully " + signUpUser.getfullname());
			return "sign-up";
		}
		return "sign-up";
	}
}
