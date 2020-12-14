package io.schedule.maker.sm.model;

 import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
 
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.SessionAttributes;


@SessionAttributes("name")
@Entity
public class TimeFactors {
	
	  
	  private String getLoggedInUserName() { Object principal =
	  SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	 
	  
	  
	  if (principal instanceof UserDetails) return ((UserDetails)
	  principal).getUsername(); return principal.toString(); 
	  }
	  
	  
	  //check workbench if workingHoursFrom is string andd change to int and then work on further with order of doing the things
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private int id;
	
	  private String username;
	
	  private String workingHoursFrom;
	
	  private String workingHoursTo;
	  
 	  private String commuteTime; 
	
 	  private String workoutTime;
 
 	  private String phoneTime;
 	  
  	  
	  private String goal;
	  
	  private String sleepTime;
	   
 	  

	public TimeFactors() {
		 
	}
	 
	public String getWorkingHoursFrom() {
		return workingHoursFrom;
	}
	public void setWorkingHoursFrom(String workingHoursFrom) {
		this.workingHoursFrom = workingHoursFrom;
	}
	public String getWorkingHoursTo() {
		return workingHoursTo;
	}
	public void setWorkingHoursTo(String workingHoursTo) {
		this.workingHoursTo = workingHoursTo;
	}
	public String getCommuteTime() {
		return commuteTime;
	}
	public void setCommuteTime(String commuteTime) {
		this.commuteTime = commuteTime;
	}
	public String getWorkoutTime() {
		return workoutTime;
	}
	public void setWorkoutTime(String workoutTime) {
		this.workoutTime = workoutTime;
	}
	 
	public String getPhoneTime() {
		return phoneTime;
	}
	public void setPhoneTime(String phoneTime) {
		this.phoneTime = phoneTime;
	}
	public String getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(String sleepTime) {
		this.sleepTime = sleepTime;
	}
	public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) 
		{
			this.username = username;
		}
		 
		public String getGoal() {
			return goal;
		}
		public void setGoal(String goal) {
			this.goal = goal;
		}
		 
}
