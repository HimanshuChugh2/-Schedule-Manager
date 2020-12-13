 package io.schedule.maker.sm.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	//THIS IS FOR AUTHENTICATION
	@Autowired
	UserDetailsService userDetailsService;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userDetailsService);
	}
	//this userDetailsService will look up a user in database	
	
	
	
	//THIS IS FOR AUTHORIZATION when the page is default 
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// TODO Auto-generated method stub
//	//below code sets up the authorization
//		
//		http.authorizeRequests()
//		.antMatchers("/admin").hasRole("ADMIN")// allow to /admin if user has the role admin
//		.antMatchers("/user").hasAnyRole("USER","ADMIN")/// allow to /user if the user have any of the roles mentioned
//		.antMatchers("/").permitAll()// permit everyone to "/"
//		.and().formLogin().permitAll();
//	}
	
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.csrf().disable()
//			.authorizeRequests().antMatchers("/admin").hasRole("ADMIN")
//			.antMatchers("/user").hasAnyRole("USER","ADMIN")
//			.antMatchers("/").permitAll()
//			.antMatchers("/sign-up").permitAll()
//			.anyRequest().authenticated()
//			.and()
//			.formLogin().loginPage("/login").permitAll();
//	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		  http
 		  .authorizeRequests() .antMatchers("/sign-up").permitAll()
		  .antMatchers("/test").permitAll().anyRequest().authenticated() .and()
				.formLogin().defaultSuccessUrl("/user", true)
		  
		  . loginPage("/login").permitAll() .and() .logout().permitAll();
		 
//			.logoutUrl("/login?logout")
//  			;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	            .ignoring()
	            .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/icon/**");
	}
	


 
 


}
//here if we use only defaultSuccessUrl("/user") then it will not go to /user on the very first successLogin, if we use defaultSuccessUrl("/user",true) then it will go to .user on first successLogin 

// csrf disable helps in making post request from api, otherwise it enables security and gives 403 forbidden error