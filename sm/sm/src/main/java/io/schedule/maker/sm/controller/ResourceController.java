package io.schedule.maker.sm.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import ch.qos.logback.classic.Logger;
import io.schedule.maker.sm.Repository.JPASignUpRepository;
import io.schedule.maker.sm.Repository.RepositoryTimeFactors;
import io.schedule.maker.sm.model.SignUpUserDetails;
import io.schedule.maker.sm.model.TimeFactors;

@SessionAttributes("name")
@Controller
public class ResourceController {
	
	@Autowired
	RepositoryTimeFactors object;

	private String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // getting the
																									// principal

		if (principal instanceof UserDetails)
			return ((UserDetails) principal).getUsername();
		return principal.toString();
	}

	
	/*
	 * @GetMapping("/") public String loginHome(ModelMap model) {
	 * System.out.println("----" + getLoggedInUserName()); // model.put("username",
	 * getLoggedInUserName()); model.put("name", "notNull"); String name = (String)
	 * model.get("name"); System.out.println("VALUE in SESSION is" + name); return
	 * "login"; }
	 */

	
	
	

//	@GetMapping("/test")
//	public String home() {
//		return "test1";
//	}
//
//	@GetMapping("/my-schedule")
//	public String myschedule() {
//		return "my-schedule";
//	}
//
//	@GetMapping("/login2")
//	public String login2() {
//		return "login2";
//	}
//
//	 

	
	/*
	 * @GetMapping("/create-schedule") public String mychedule() { return
	 * "create-schedule"; }
	 */



	@GetMapping("/about")
	public String about(ModelMap model) {
		//System.out.println("----" + getLoggedInUserName());
		if(getLoggedInUserName()!="anonymousUser")	
		{
					model.put("username", getLoggedInUserName());
		}
		return "about";
	}

	
	@GetMapping("/")
	public String aboutLandingPage(ModelMap model) {
		
		System.out.println(" getLoggedInUserName  " +getLoggedInUserName());
		
			if(getLoggedInUserName()!="anonymousUser")	
			{
 					model.put("username", getLoggedInUserName());
			}
 
		return "about";
	}
	
	/*
	 * @GetMapping("/admin") public String admin() { return "admin"; }
	 * 
	 * @RequestMapping(value = "/hello", method = RequestMethod.GET) public String
	 * hello() { return "hello"; }
	 */
}
