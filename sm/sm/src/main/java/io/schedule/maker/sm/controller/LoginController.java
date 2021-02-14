package io.schedule.maker.sm.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	private String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // getting the
																									// principal

		if (principal instanceof UserDetails)
			return ((UserDetails) principal).getUsername();
		return principal.toString();
	}
	
	
	/*
	 * @GetMapping("/login") public String loginstrin() { return "login"; }
	 */
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost(@RequestParam String username, ModelMap model) {
		model.put("name", username);
		model.put("username", (String) model.get("name"));
		return "about";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet(ModelMap model) {

 
		model.put("username", getLoggedInUserName());

		String name = (String) model.get("name");
		if (name == null) {
			model.put("logoutmessage", "You have been logged out");
		}
		//System.out.println("VALUE in SESSION is login " + name);
		return "login";
	}

}
