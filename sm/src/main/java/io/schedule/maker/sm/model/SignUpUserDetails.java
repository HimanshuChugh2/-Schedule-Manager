package io.schedule.maker.sm.model;

 

 import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
 import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

 
@Entity
@Table(name= "sign_up")
public class SignUpUserDetails implements UserDetails {
	 
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private int id;

		@NotBlank 
		@Size(min = 3,max = 30)
  		@Pattern(regexp= "/[一-龠]+|[ぁ-ゔ]+|[ァ-ヴー]+|[a-zA-Z ]+|[ａ-ｚＡ-Ｚ ]+|[々〆〤]+/u" ,message = "Do not")
		//i have added whitespace afterA-Z to let it take white space between words
		private String fullname;
 		@Size(min = 3,max = 30)
	    private String password;
		
		@NotBlank 
 		@Size(min = 3,max = 30)
  		@Email
  		private String username;
		
		
	/*
	 * private int workingHours; private int commuteTime; private int workoutTime;
	 * private int mealTime;
	 * 
	 * private int phoneTime; private int sleepTime;
	 */
 	    
 	    
 	   public SignUpUserDetails() {
 			super();
  		}
 	   
 			public SignUpUserDetails(int id, String fullname, @NotNull @Size(min = 3, max = 12) String password,
 				String username) {
 			super();
 			this.id = id;
 			this.fullname = fullname;
 			this.password = password;
 			this.username = username;
 		}	   
 		
 	    
 			
  		public int getId() {
			
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getfullname() {
			return fullname;
		}
		public void setfullname(String fullname) {
			this.fullname = fullname;
		}
		 
		public void setPassword(String password) {
			this.password = password;
		}
		 
		 
		public void setUsername(String username) {
			this.username = username;
		}
		
		
		public SignUpUserDetails(SignUpUserDetails signUpUserDetails) {
	        this.username = signUpUserDetails.getUsername();
	        this.password = signUpUserDetails.getPassword();
	        this.fullname=signUpUserDetails.getfullname();
	        
	       // this.active = user.isActive();
		/*
		 * this.authorities = Arrays.stream(user.getRoles().split(","))
		 * .map(SimpleGrantedAuthority::new) .collect(Collectors.toList());
		 */
	    }
	    
	    
	    
	    
	    
	/*
	 * @Override public Collection<? extends GrantedAuthority> getAuthorities() { //
	 * TODO Auto-generated method stub return authorities; }
	 */



@Override
public String getPassword() {
	// TODO Auto-generated method stub
	return password;
}
@Override
public String getUsername() {
	// TODO Auto-generated method stub
	return username;
}
@Override
public boolean isAccountNonExpired() {
	// TODO Auto-generated method stub
	return true;
}
@Override
public boolean isAccountNonLocked() {
	// TODO Auto-generated method stub
	return true;
}
@Override
public boolean isCredentialsNonExpired() {
	// TODO Auto-generated method stub
	return true;
}
@Override
public boolean isEnabled() {
	// TODO Auto-generated method stub
	return true;
}





@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	// TODO Auto-generated method stub
	return null;
}
	    
	    
		
		
		}