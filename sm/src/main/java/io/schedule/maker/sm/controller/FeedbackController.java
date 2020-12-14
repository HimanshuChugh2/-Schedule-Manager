package io.schedule.maker.sm.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.validator.internal.constraintvalidators.bv.NotNullValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import io.schedule.maker.sm.Repository.JpaFeedbackRepository;
import io.schedule.maker.sm.model.Feedback;
import io.schedule.maker.sm.model.SignUpUserDetails;

@Controller
public class FeedbackController {
	private String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // getting the
																									// principal
 		if (principal instanceof UserDetails)
			return ((UserDetails) principal).getUsername();
		return principal.toString();
	}
	@Autowired
	JpaFeedbackRepository jpaFeedbackRepository;
	
	@GetMapping("/feedback")
	public String feedback(Feedback fbFeedback, Model model) {
	Feedback fb=new Feedback();
	model.addAttribute("fb",fb);
		return "feedback-form";
	}
	
@GetMapping("/welcome")
public String welcome(Model model)
{
  	 
	List<Feedback> ownFeedback=jpaFeedbackRepository.findByUsername(getLoggedInUserName());
	
	if(!ownFeedback.isEmpty())
	{
		model.addAttribute("ownfeedback",ownFeedback);
	}
	
  List<Feedback> fbList=	jpaFeedbackRepository.findAll();
  if(fbList.isEmpty())
  {
	  model.addAttribute("message","");
  }
  model.addAttribute("fbList",fbList); 
  return "user";
}
	
	 
	@PostMapping("/savefb")
	public String saveString(@ModelAttribute("fb") Feedback fb,Model model)
	 {
		fb.setUsername(getLoggedInUserName());
		
 
		
		
 		List<Feedback> objOptional=jpaFeedbackRepository.findByUsername(fb.getUsername());
		if(!objOptional.isEmpty())
		{	
			jpaFeedbackRepository.deleteByUsername(fb.getUsername());
			model.addAttribute("message","Your previous rating and comments were updated with this one");
			
		}
		
		else
		{
		//jpaFeedbackRepository.deleteByUsername(fb.getUsername());
		model.addAttribute("message","Your feedback have been saved");
		
		jpaFeedbackRepository.save(fb);
	    model.addAttribute("fb",fb);
		System.out.println("U S E R N A  M E " +fb.getId());
		}
		
		
		return "feedback-form";
	}
}
