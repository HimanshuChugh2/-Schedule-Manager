// package io.schedule.maker.sm.config; 
//
// import java.util.Collection;
// 
//
//import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
//
//import io.schedule.maker.sm.model.SignUpUserDetails;
// 
// 
//
//public class MyUserDetails implements UserDetails{
//
//	/*
//	 * private String userName; private String password; private boolean active;
//	 * private List<GrantedAuthority> authorities;
//	 */
//// this constructor method will convert user object to UserDetails method 
//	 
////	    public MyUserDetails(User user) {
////	        this.userName = user.getUserName();
////	        this.password = user.getPassword();
////	        this.active = user.isActive();
////	        this.authorities = Arrays.stream(user.getRoles().split(","))
////	                    .map(SimpleGrantedAuthority::new)
////	                    .collect(Collectors.toList());
////	    }
//	    
//	    
// 	    
//	    private String username;
//	    private String fullname;
//	    private String password;
//	    public String getFullname() {
//			return fullname;
//		}
//
//
//
//
//
//		public void setFullname(String fullname) {
//			this.fullname = fullname;
//		}
//
//
//
//
//
//		public MyUserDetails(SignUpUserDetails signUpUserDetails) {
//	        this.username = signUpUserDetails.getUsername();
//	        this.password = signUpUserDetails.getPassword();
//	        this.fullname=signUpUserDetails.getfullname();
//	        
//	       // this.active = user.isActive();
//		/*
//		 * this.authorities = Arrays.stream(user.getRoles().split(","))
//		 * .map(SimpleGrantedAuthority::new) .collect(Collectors.toList());
//		 */
//	    }
//	    
//	    
//	    
//	    
//	    
//	/*
//	 * @Override public Collection<? extends GrantedAuthority> getAuthorities() { //
//	 * TODO Auto-generated method stub return authorities; }
//	 */
//
//
//
//@Override
//public String getPassword() {
//	// TODO Auto-generated method stub
//	return password;
//}
//@Override
//public String getUsername() {
//	// TODO Auto-generated method stub
//	return username;
//}
//@Override
//public boolean isAccountNonExpired() {
//	// TODO Auto-generated method stub
//	return true;
//}
//@Override
//public boolean isAccountNonLocked() {
//	// TODO Auto-generated method stub
//	return true;
//}
//@Override
//public boolean isCredentialsNonExpired() {
//	// TODO Auto-generated method stub
//	return true;
//}
//@Override
//public boolean isEnabled() {
//	// TODO Auto-generated method stub
//	return true;
//}
//
//
//
//
//
//@Override
//public Collection<? extends GrantedAuthority> getAuthorities() {
//	// TODO Auto-generated method stub
//	return null;
//}
//	    
//	    
//	 	
//	
//
//}
