package io.schedule.maker.sm.controller;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.schedule.maker.sm.Repository.JpaNewOfflineTableRepo;
import io.schedule.maker.sm.Repository.RepositoryTimeFactors;
import io.schedule.maker.sm.Repository.TimeFactorLabelValueInterface;
import io.schedule.maker.sm.model.TimeFactors;
import io.schedule.maker.sm.model.newofflinetable;

@Controller
public class TimeFactorController {
	
	
	
	
	@Autowired
	RepositoryTimeFactors object;
	
	@Autowired
	JpaNewOfflineTableRepo jpanewOfflineTableRepo;
	private String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // getting the
																									// principal
 		if (principal instanceof UserDetails)
			return ((UserDetails) principal).getUsername();
		return principal.toString();
	}
	
	@GetMapping("/logout")
	
	public String logout()
	{
		
		return "logout";
	}
	
	
	@GetMapping("/fresh-schedule")
	public String displayFresh(/* @ModelAttribute("tifaTwo") TimeFactors tifaTwo,ModelMap model */)
	{
		 		return "fresh-schedule";
 	}

	@RequestMapping("/create-schedule")
	public String createSchedule(Model model) {
 		 
		TimeFactors timefactor = new TimeFactors();
		timefactor.setUsername(getLoggedInUserName());
		model.addAttribute("tifa", timefactor);
		return "create-schedule";
	}

 	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String createSchedulePost(@ModelAttribute("tifa") TimeFactors tifa, ModelMap model) throws JsonProcessingException {	
 		List<String> data=new ArrayList<>();
 		
 		
 		
 		// lunchtime ka error thik hone wala hai
 		//System.out.println(tifa.getWorkingHoursFrom() +" "+ tifa.getWorkingHoursTo());
 		
 		
  		//System.out.println("gettin in save");
 		//MAKE A CHART SHOWING HOW MUCH TIME HE IS SPENDING ON WHAT
	
// 			object.save(tifa);
 			
 			
 			
	 		String username=tifa.getUsername();
  	   		long goaltime=0;
 			long gettingReady=30;
 	 		long commuteTime=Long.parseLong(tifa.getCommuteTime());	
 	 	 	long workoutTime=Long.parseLong(tifa.getWorkoutTime());	
 			long mealTime=40;
 			long restTime=0;
 			long phoneTime=Long.parseLong(tifa.getPhoneTime());
 			long gettingReadyForSleep=20;	
 			long sleepTime= Long.parseLong(tifa.getSleepTime());
 			long dinnerTime=60; //only for non-workers
 			long lunchTime=60;  //only for non-workers
 			long restTimeAfterWork=30; //only for workers
  			TimeFactors tifaTwo=new TimeFactors();
 	 		LocalTime totalTimeBeingUsed= LocalTime.parse("00:00");
 			
 			//System.out.println("W O R Ki n G H OURS "+tifa.getWorkingHoursFrom());
 	 		LocalTime workingHoursFrom = LocalTime.parse(tifa.getWorkingHoursFrom());
 	 		LocalTime workingHoursTo = LocalTime.parse(tifa.getWorkingHoursTo());
 	 		LocalTime timeRn;
 	 		
 	 		Duration workHours=Duration.between(workingHoursFrom,workingHoursTo);
 	 		//System.out.println("D I F F E R E N C E BETWEEN TO AND FROM IS " +		workHours.toMinutes());
 	 		
 	 		
 	 		
 	 		
 	 		
 	 		//
 	 		if(tifa.getWorkingHoursFrom().equals("00:00:00") && tifa.getWorkingHoursTo().equals("00:00:00")  ||tifa.getWorkingHoursFrom().equals("00:00") &&  tifa.getWorkingHoursTo().equals("00:00"))
 	 	 	{
 	 			timeRn=LocalTime.parse("00:00").plusMinutes(sleepTime);
 	 			
 	 			// calculating goalTime
 	 			
 	 			
 	 			
			totalTimeBeingUsed=totalTimeBeingUsed.plusMinutes(gettingReady+workHours.toMinutes()+commuteTime*2+workoutTime+mealTime+phoneTime+gettingReadyForSleep+sleepTime+restTimeAfterWork);
 	 			Duration varDuration= Duration.between(totalTimeBeingUsed,LocalTime.parse("23:59"));
  	  	 		goaltime= varDuration.toMinutes();
 	 	 		//restTime will be 20% of the goaltime goaltime=150; resttime=150- *20/100=30; goalTime=150-30;
 	  	 		restTime=goaltime*20/100;
 	  	 		goaltime=goaltime-restTime;
 	  	 		long halfOfGoaltime=goaltime*70/100;
  	 	 		data.add(LocalTime.parse("00:00").plusMinutes(goaltime)+"  hours is the total time that you can spend on your goal ");
  	 			
 	 			long totalMinutesUsedInADay=gettingReady+workHours.toMinutes()+commuteTime*2+workoutTime+mealTime+phoneTime+gettingReadyForSleep+sleepTime+restTimeAfterWork+goaltime+restTime;
 	 	 		 //System.out.println(" M I N U T E S "+ totalMinutesUsedInADay);
 	 	 		
 		 		  if(totalMinutesUsedInADay>1439) {
 		 		  
 
 		 			  model.addAttribute(
 		 		  "timeOverflowError","Time overflow Error: The time you are spending in a day is more than 24 Hours, please select less time from what you have already selected in the dropdown list and please try again"
 				  );
 				  
 				  return "create-schedule";
 				  }
 				 
 	 	 		
 		 		List<TimeFactorLabelValueInterface> timeFactorLabelValueInterface= jpanewOfflineTableRepo.getLabelAndValueByUsername(username);
 	 	 		
 	 			if(timeFactorLabelValueInterface!=null)
 	 			{
 	 				//if user schedule already exist, delete it
 	 				//System.out.println("User schedule already exist");
 	 				jpanewOfflineTableRepo.deleteUser(username);
 	 			}
 	 			;
 	 			jpanewOfflineTableRepo.save(new newofflinetable(" Wake up", String.valueOf(gettingReady), username)); 
 	 			//object.insertUser(" Wake up", gettingReady, username);
 	 			if(workoutTime!=0)
 	 			{
 	 	 			jpanewOfflineTableRepo.save(new newofflinetable("  Start working out ", String.valueOf(workoutTime), username)); 

 	  		 		//object.insertUser("  Start working out ", workoutTime, username);
 	 			}
 	 			
 	 			
 	 			jpanewOfflineTableRepo.save(new newofflinetable("  Have meal ", String.valueOf(mealTime), username)); 
 	 			jpanewOfflineTableRepo.save(new newofflinetable("  Time you work on your goal ", String.valueOf(goaltime), username)); 

 	 			jpanewOfflineTableRepo.save(new newofflinetable("  Rest time in a day ", String.valueOf(restTime), username)); 

 	 			jpanewOfflineTableRepo.save(new newofflinetable("  Have dinner  ", String.valueOf(dinnerTime), username)); 

 	 			
  		 		//object.insertUser("  Have meal ", mealTime, username); 
  		 		//object.insertUser("  Time you work on your goal ", goaltime, username);
		 		//object.insertUser("  Rest time in a day ", restTime, username);
  		 		//object.insertUser("  Have dinner ", dinnerTime, username);  
  		 		
  		 		if(phoneTime!=0)
  		 		{
  	 	 			jpanewOfflineTableRepo.save(new newofflinetable("  Use phone  ", String.valueOf(phoneTime), username)); 

  	 		 		//object.insertUser("  Use phone ", phoneTime, username);  
  		 		}
	 	 			jpanewOfflineTableRepo.save(new newofflinetable("  Getting ready for sleep ", String.valueOf(gettingReadyForSleep), username)); 
  	 	 			jpanewOfflineTableRepo.save(new newofflinetable("  Sleep time ", String.valueOf(sleepTime), username)); 

 		 		//object.insertUser("  Getting ready for sleep ", gettingReadyForSleep, username);
 		 		//object.insertUser(" Sleep time ", sleepTime, username);  
 	 			data.add(timeRn.toString()+" |  Wake up. Get ready till "+timeRn.plusMinutes(gettingReady));	
 	 			
 	 			
  	 			timeRn=timeRn.plusMinutes(gettingReady);
 	 			//checks if user works out or not
 	 			if(workoutTime!=0)
 	 			{
 	 		    data.add(timeRn.toString()+" |  Start working out ");
 	  		    timeRn=timeRn.plusMinutes(workoutTime);
 	 			}
	  		    
	  		    data.add(timeRn.toString()+" |  Have meal ");
		 		timeRn=timeRn.plusMinutes(mealTime);
 	 			
		 		
		 		
	 		 	while(goaltime>120)
	  	 		{
		  	 		if(goaltime-120>=60)
		  	 		{
		  	 			if(goaltime<=halfOfGoaltime && lunchTime!=0)
		  	 			{
		  	 			data.add(timeRn.toString()+" |  Have lunch till "+timeRn.plusMinutes(lunchTime));
		  	 			timeRn=timeRn.plusMinutes(lunchTime);
		  	 			lunchTime=0;
		  	 			}
		  	 			else
		  	 			{
		  	 			data.add(timeRn.toString()+" |  Take rest from "+timeRn.toString()+" to "+timeRn.plusMinutes(30));
		  	 			timeRn=timeRn.plusMinutes(30);
		  	 			restTime=restTime-30;
		  	 			
		  	 			data.add(timeRn.toString()+" |  Work on your goal: "+tifa.getGoal().toString());
	  	 			timeRn=timeRn.plusMinutes(120);
	  	 			goaltime=goaltime-120;
	  	 			
		  	 			}
		  	 		}	
		  	 		else
		  	 		{
		  	 			data.add(timeRn.toString()+" |  Take rest from "+timeRn.toString()+" to "+timeRn.plusMinutes(restTime));
			 			timeRn=timeRn.plusMinutes(restTime);
			 			restTime=restTime-restTime;
		  	 			
			 			
		  	 			
		  	 		data.add(timeRn.toString()+" |  Work on your goal: "+tifa.getGoal().toString()+", till "+timeRn.plusMinutes(goaltime));
	 	  	 		timeRn=timeRn.plusMinutes(goaltime); 
	 	  	 	goaltime=goaltime-goaltime;
	 	  	 	
		  	 		}
	  	 			
	  	 		}
	  	 		
	  	 		
	  	 		if(goaltime>0 && restTime>0) 
	  	 		{ 
	  	 			
	  	 			data.add(timeRn.toString()+" |  Take rest from "+timeRn.toString()+" to "+timeRn.plusMinutes(restTime));
	  	 			timeRn=timeRn.plusMinutes(restTime);
	  	 		data.add(timeRn.toString()+" |  Work on your goal:  "+tifa.getGoal().toString());
	  	 			timeRn=timeRn.plusMinutes(goaltime);
	  	 		
	  	 		}
	  	 		
	  	 		
	  	 		
	  	 			
	  	 		
	  	 		data.add(timeRn.toString()+" |  Have dinner");
 		 		

		        timeRn=timeRn.plusMinutes(dinnerTime);
	  	 		
	  	 		
		        if(phoneTime!=0)
		        {
	 	 		data.add(timeRn.toString()+" |  Use phone ");
	            timeRn=timeRn.plusMinutes(phoneTime);
		        }	            	
	            
	            
	            data.add(timeRn.toString()+" |  Start getting ready for sleep");
	 	 		timeRn=timeRn.plusMinutes(gettingReadyForSleep);
	 		    
	 		    data.add(timeRn.toString()+" |  Go to sleep ");

	 		    
	 		  //  makeChart(timeFactorLabelValueInterface, username);
	 		    
	 		    String jsonString= makeChart(timeFactorLabelValueInterface,username);
			    model.addAttribute("convertedJson",jsonString);
	 	 		model.addAttribute("data",data);

 	 			
 	 		}
 	 		
 	 		// above is for non workers
 	 		
 	 		
 	 		else
 	 		{	
 	 			
 	 			
 	 			
 	 			
 	 			
 	 			
 	 			
 	 			
 	 			
 	 			
 	 			
 	 			
 	 			
 	 			
 	 			
 	 		if(workingHoursFrom.isAfter(LocalTime.parse("10:59")))
 	 		{	
 	 			  
 	 			
	 			// calculating goalTime
 	 			
 	 			
			totalTimeBeingUsed=totalTimeBeingUsed.plusMinutes(gettingReady+workHours.toMinutes()+commuteTime*2+workoutTime+mealTime+phoneTime+gettingReadyForSleep+sleepTime+restTimeAfterWork);
 	 			Duration varDuration= Duration.between(totalTimeBeingUsed,LocalTime.parse("23:59"));
  	  	 		goaltime= varDuration.toMinutes();
 	 	 		//restTime will be 20% of the goaltime goaltime=150; resttime=150- *20/100=30; goalTime=150-30;
 	  	 		restTime=goaltime*20/100;
 	  	 		goaltime=goaltime-restTime;
   	 	 		data.add(LocalTime.parse("00:00").plusMinutes(goaltime)+" hours is the total time in a usual working day that you can spend on your Goal ");
  	 			
  	 	 		
  	 	 		
  	 	 		
  	 	 		
  	 	 		
  	 	 		
  	 	 	   
  	 	 		
  	 	 		
 	 			long totalMinutesUsedInADay=gettingReady+workHours.toMinutes()+commuteTime*2+workoutTime+mealTime+phoneTime+gettingReadyForSleep+sleepTime+restTimeAfterWork+goaltime+restTime;
 	 	 		 System.out.println(" M I N U T E S "+ totalMinutesUsedInADay);
 	 	 		
 		 		  if(totalMinutesUsedInADay>1439) {
 		 		  
 
 		 			  model.addAttribute(
 		 		  "timeOverflowError","Time overflow Error: The time you are spending in a day is more than 24 Hours, please select less time from what you have already selected in the dropdown list and please try again"
 				  );
 				  
 				  return "create-schedule";
 				  }
 				 
 	 	 		
 		 		List<TimeFactorLabelValueInterface> timeFactorLabelValueInterface= jpanewOfflineTableRepo.getLabelAndValueByUsername(username);
 	 	 		
 	 			if(timeFactorLabelValueInterface!=null)
 	 			{
 	 				//if user schedule already exist, delete it
 	 			//	System.out.println("User schedule already exist");
 	 				jpanewOfflineTableRepo.deleteUser(username);
 	 			}
 	 			 

 	 			jpanewOfflineTableRepo.save(new newofflinetable(" Wake up.", String.valueOf(gettingReady), username)); 

 	 			//object.insertUser(" Wake up.", gettingReady, username);
 		 		if(workoutTime!=0)
 		 		{
	 	 			jpanewOfflineTableRepo.save(new newofflinetable(" Start working out ", String.valueOf(workoutTime), username)); 

 	 			//object.insertUser(" Start working out ", workoutTime, username);
 		 		}
 		 		
 		 		
 	 			jpanewOfflineTableRepo.save(new newofflinetable(" Have meal ", String.valueOf(mealTime), username)); 
 	 			jpanewOfflineTableRepo.save(new newofflinetable("  Time you work on your goal  ", String.valueOf(goaltime), username)); 

 		 		//object.insertUser(" Have meal ", mealTime, username); 
 	 	 		//object.insertUser(" Time you work on your goal ", goaltime, username);
 	 	 		if(phoneTime!=0)
 	 	 		{
	 	 		jpanewOfflineTableRepo.save(new newofflinetable("  Use phone  ", String.valueOf(phoneTime ), username)); 

  		 		//object.insertUser(" Use phone ", phoneTime, username);
 	 	 		}
  		 		
  		 		if(commuteTime!=0)
  		 		{
	 	 			jpanewOfflineTableRepo.save(new newofflinetable(" Commute from home  ", String.valueOf(commuteTime), username)); 

  		 		//object.insertUser(" Commute from home", commuteTime, username);
  		 		}
 	 			jpanewOfflineTableRepo.save(new newofflinetable(" Working hours ", String.valueOf(workHours.toMinutes()), username)); 

 	 			jpanewOfflineTableRepo.save(new newofflinetable(" Getting ready for sleep ", String.valueOf(gettingReadyForSleep), username)); 

 	 			jpanewOfflineTableRepo.save(new newofflinetable("  Sleep time ", String.valueOf(sleepTime), username)); 

  		 		//object.insertUser(" Working hours", workHours.toMinutes(), username);
  		 		//object.insertUser(" Start getting ready for sleep ", gettingReadyForSleep, username);
  		 		//object.insertUser(" Sleep time ", sleepTime, username);  

   	 	 		
   	 	 		
 		 		
 	 			
 	 			
 	 			
 	 			
 	 			
 	 			//System.out.println(" I S A F T E R");
 	 			
 	 			
 	 			timeRn=workingHoursTo;
 	 			
 	 			
 	 		  
 	 		    
	            if(commuteTime!=0)
	            {
	            tifaTwo.setCommuteTime(timeRn.toString()); 
 	 	 		data.add(timeRn.toString()+" |  Start commuting ");
  	 		    timeRn=timeRn.plusMinutes(commuteTime);
	            }
  	 		    
  	 		    if(timeRn.isBefore(LocalTime.parse("21:00")) &&  timeRn.isBefore(LocalTime.parse("00:00")))
  	 		    	
  	 		    {
  	 		    	//if He is reaching home from work before 9 PM, then make him do work out and have meal
  	 		    	
  	 		    	if(workoutTime!=0)
  	 	 			{
  	 		    	data.add(timeRn.toString()+" |  Start working out ");
  		  		    timeRn=timeRn.plusMinutes(workoutTime);
  	 	 			}
  		  		    
  		  		    data.add(timeRn.toString()+" |  Have meal ");
  			 		timeRn=timeRn.plusMinutes(mealTime);
  	 		    	
  			 	    data.add(timeRn.toString()+" |  Start getting ready for sleep ");
    	 	 		timeRn=timeRn.plusMinutes(gettingReadyForSleep);
    	 	 		
    	 	 		
   	 	 		    data.add(timeRn.toString()+" |  Go to sleep ");
   	 	  		    timeRn=timeRn.plusMinutes(sleepTime);
   	 	  		    
   	 		    
   	 	 		    data.add(timeRn.toString()+" |  Wake up. Get ready till "+timeRn.plusMinutes(gettingReady));
   	 	 		    timeRn=timeRn.plusMinutes(gettingReady);
 
  	 		    }
  	 		    
  	 		    else {
 	 	 		System.out.println();
  	 		    data.add(timeRn.toString()+" |  Start getting ready for sleep ");
  	 	 		timeRn=timeRn.plusMinutes(gettingReadyForSleep);
 	 		    
 	 	 		data.add(timeRn.toString()+" |  Go to sleep ");
 	  		    timeRn=timeRn.plusMinutes(sleepTime);
 	  		    
 	 		    
 	 		    data.add(timeRn.toString()+" |  Wake up. Get ready till "+timeRn.plusMinutes(gettingReady));
 	 	 		timeRn=timeRn.plusMinutes(gettingReady);
 	 	 		
 	 	 		
	 	 	//	model.addAttribute("gettingReady","The Morning's here, start getting ready"+timeRn);

 	 		    data.add(timeRn.toString()+" |  Workout ");
	  		    timeRn=timeRn.plusMinutes(workoutTime);	
	  		    
	  		    
	  		    data.add(timeRn.toString()+" |  Have meal ");
		 		timeRn=timeRn.plusMinutes(mealTime);
		 		
  	 		    }
	 		 		
	            
	 		 
  	 		    totalTimeBeingUsed=totalTimeBeingUsed.plusMinutes(gettingReady+workHours.toMinutes()+commuteTime*2+workoutTime+mealTime+phoneTime+gettingReadyForSleep+sleepTime);
	 	 		
	 		 	
	 	 	   	
	  	 		
	 	 		while(goaltime>120)
	  	 		{ 	
		  	 		if(goaltime-120>=60)
		  	 		{
		  	 			data.add(timeRn.toString()+" |  Take rest from "+timeRn.toString()+" to "+timeRn.plusMinutes(30));
		  	 			timeRn=timeRn.plusMinutes(30);
		  	 			restTime=restTime-30;
		  	 			
		  	 			data.add(timeRn.toString()+" |  Work on your goal:  "+tifa.getGoal().toString());
	  	 			timeRn=timeRn.plusMinutes(120);
	  	 			goaltime=goaltime-120;
	  	 			
	  	 			
		  	 		}
		  	 		else
		  	 		{
		  	 			data.add(timeRn.toString()+" |  Take rest from "+timeRn.toString()+" to "+timeRn.plusMinutes(restTime));
			 			timeRn=timeRn.plusMinutes(restTime);
			 			restTime=restTime-restTime;
		  	 			
		  	 			
		  	 		data.add(timeRn.toString()+" |  Work on your goal:  "+tifa.getGoal().toString()+", till "+timeRn.plusMinutes(goaltime));
	 	  	 		timeRn=timeRn.plusMinutes(goaltime); 
	 	  	 	goaltime=goaltime-goaltime;
	 	  	 	
		  	 		}
	  	 			
	  	 		}
	  	 		
	  	 		
	  	 		if(goaltime>0 && restTime>0) 
	  	 		{ 
	  	 		data.add(timeRn.toString()+" |  Work on your goal:  "+tifa.getGoal().toString());
	  	 			timeRn=timeRn.plusMinutes(goaltime);
	  	 		data.add(timeRn.toString()+" |  Take rest from "+timeRn.toString()+" to "+timeRn.plusMinutes(restTime));
	  	 			timeRn=timeRn.plusMinutes(restTime);
	  	 		}
	  	 		
	  	 		
 	 	 		//System.out.println("G O A L T I M E and timern "+goaltime+"    "+timeRn);

	 	 		data.add(timeRn.toString()+" |  Use phone ");
	            timeRn=timeRn.plusMinutes(phoneTime);

	 	 	//	model.addAttribute("goalTime",LocalTime.parse("00:00").plusMinutes(goaltime));
	 	 		
	            if(commuteTime!=0)
	            {
	 	 		data.add(timeRn.toString() +" |  Commute from home ");
 	 	 		timeRn=timeRn.plusMinutes(commuteTime);
	            }	 	 		
	 	 			
	 			data.add("Work from "+tifa.getWorkingHoursFrom()+" to "+tifa.getWorkingHoursTo());
	 			timeRn=timeRn.plusMinutes(workHours.toMinutes());
	 			
	 			timeRn=timeRn.plusMinutes(restTimeAfterWork);
	 			data.add(timeRn.toString()+" |  Take rest from "+ timeRn.toString() +" to "+ timeRn.plusMinutes(restTimeAfterWork));
	 			
	 	 		model.addAttribute("data",data);
	 	 	
	 	 		
	 	 		
 	 			 
 		 		String jsonString= makeChart(timeFactorLabelValueInterface,username);
 			    model.addAttribute("convertedJson",jsonString);
	 	 	
 			    
	 	 		
 	 		}
 	 		else
 	 		{
 	  			
 	 			// in start we will always check for timeOverflow Error
 	 			totalTimeBeingUsed=totalTimeBeingUsed.plusMinutes(gettingReady+workHours.toMinutes()+commuteTime*2+workoutTime+mealTime+phoneTime+gettingReadyForSleep+sleepTime+restTimeAfterWork);
 	 			// here commuteTime*2 cz he is commuting two times, once from home and once to home
 	 			
 	 			
 	 	 	    Duration varDuration= 	Duration.between(totalTimeBeingUsed,LocalTime.parse("23:59"));
 	 	 	    
 	 	 	   
 	 	 	    
 			
 	 	 	    goaltime= varDuration.toMinutes();
 	 	 		restTime=goaltime*20/100;
 	  	 		goaltime=goaltime-restTime;
	 	 		data.add(LocalTime.parse("00:00").plusMinutes(goaltime)+" hours is the total time in a usual working day that you can spend on your Goal ");
 	  	 		// inserting goaltime and resttime in order of the occouraces of them
 	  	 		
// timeOverFlow Validation 
 	 			
 	  	 	long totalMinutesUsedInADay=gettingReady+workHours.toMinutes()+commuteTime*2+workoutTime+mealTime+phoneTime+gettingReadyForSleep+sleepTime+restTimeAfterWork+goaltime+restTime;
 	 		 System.out.println(" M I N U T E S "+ totalMinutesUsedInADay);
 	 		
	 		  if(totalMinutesUsedInADay>1439) {
	 		  
	 		 // System.out.println("is after 00:00");
	 		  model.addAttribute(
	 		  "timeOverflowError","Time overflow Error: The time you are spending in a day is more than 24 Hours, please select less time from what you have already selected in the dropdown list and please try again"
			  );
			  
			  return "create-schedule";
			  }
			  	
 	  	 		
 	 			
 	 		List<TimeFactorLabelValueInterface> timeFactorLabelValueInterface= jpanewOfflineTableRepo.getLabelAndValueByUsername(username);
 	 		
 	 			if(timeFactorLabelValueInterface!=null)
 	 			{
 	 				//if user schedule already exist, delete it
 	 				//System.out.println("User schedule already exist");
 	 				jpanewOfflineTableRepo.deleteUser(username);
 	 			}
 	 			 
 	 			
 	 			jpanewOfflineTableRepo.save(new newofflinetable("  Wake up time. ", String.valueOf(gettingReady), username)); 

 		 		//object.insertUser(" Wake up time.", gettingReady, username);
 		 		
 		 		if(commuteTime!=0)
 		 		{
 	 	 			jpanewOfflineTableRepo.save(new newofflinetable(" Commute from home  ", String.valueOf(commuteTime), username)); 

 	 	 		//object.insertUser(" Commute from home ", commuteTime, username);
 		 		}
 	 			jpanewOfflineTableRepo.save(new newofflinetable("  Working hours ", String.valueOf(workHours.toMinutes()), username)); 

 		 		//object.insertUser(" Working hours", workHours.toMinutes(), username);
 		 		if(commuteTime!=0)
 		 		{
 	 	 			jpanewOfflineTableRepo.save(new newofflinetable(" Commute from work to home ", String.valueOf(commuteTime), username)); 

 		 		//object.insertUser(" Commute from work to home ", commuteTime, username);
 		 		}
 	 			jpanewOfflineTableRepo.save(new newofflinetable(" Rest after work  ", String.valueOf(restTimeAfterWork), username)); 

 		 		//object.insertUser(" Rest after work ", restTimeAfterWork, username);

if(workoutTime!=0)
{
		jpanewOfflineTableRepo.save(new newofflinetable("   Workout time ", String.valueOf(workoutTime), username)); 

		//object.insertUser("  Workout time", workoutTime, username);
}
	jpanewOfflineTableRepo.save(new newofflinetable("  Have meal ", String.valueOf(mealTime), username)); 

		jpanewOfflineTableRepo.save(new newofflinetable("  Sleep time ", String.valueOf(sleepTime), username)); 
			jpanewOfflineTableRepo.save(new newofflinetable(" Time for your goal ", String.valueOf(goaltime), username)); 
	 			jpanewOfflineTableRepo.save(new newofflinetable("  Rest time in a day  ", String.valueOf(restTime), username)); 

	
	
 		 		//object.insertUser("  Have meal ", mealTime, username);
 		 		//object.insertUser("  Time for your goal ", goaltime, username);
 		 		//object.insertUser(" Rest time in a day ", restTime, username);
 		 		
 		 		if(phoneTime!=0)
 		 		{
 	 	 			jpanewOfflineTableRepo.save(new newofflinetable("  Use phone  ", String.valueOf(phoneTime), username)); 

 		 		//object.insertUser(" Use phone ", phoneTime, username);
 		 		}
 		 		
 	 			jpanewOfflineTableRepo.save(new newofflinetable(" Getting ready for sleep ", String.valueOf(gettingReadyForSleep), username)); 
 	 			jpanewOfflineTableRepo.save(new newofflinetable("  Sleep time ", String.valueOf(sleepTime), username)); 

 	 		   // object.insertUser("Getting ready for sleep", gettingReadyForSleep, username);
 	 		    //object.insertUser("Sleep time", sleepTime, username);

 	 				
 	 			 
 	 		timeRn= workingHoursFrom.minusMinutes(gettingReady);
 	 		if(commuteTime!=0)
 			{
 	 		timeRn=timeRn.minusMinutes(commuteTime);	//minus here cz this is commute from
 			}
 	 		
 	 		
	 			data.add(timeRn.toString()+" |  Wake up, it's time, get ready till "+timeRn.plusMinutes(gettingReady));
  	 		timeRn=timeRn.plusMinutes(gettingReady);
 	 		
  	 		
            if(commuteTime!=0)
            {
 	 		data.add(timeRn.toString() +" |  Commute from home ");
  	 		timeRn= timeRn.plusMinutes(commuteTime);
            }
 	 		
 			timeRn=timeRn.plusMinutes(workHours.toMinutes());
 			data.add("Work from "+tifa.getWorkingHoursFrom()+" to "+tifa.getWorkingHoursTo());

 			if(commuteTime!=0)
 			{
	 	 	data.add(timeRn.toString()+" |  Commute from work to home");
 	 	 	timeRn=timeRn.plusMinutes(commuteTime);
 			}
 	 		
 	 	 	
 	 		data.add(timeRn.toString()+ " |  Rest after work from "+timeRn.toString()+" to "+timeRn.plusMinutes(restTimeAfterWork));
   	 		timeRn=timeRn.plusMinutes(restTimeAfterWork);
 	 		// total rest time will be the 20% + this 20 mins
	 		
  	 		if(workoutTime!=0)
  	 		{
  	 		data.add(timeRn.toString()+" |  Workout  ");
   	 		timeRn=timeRn.plusMinutes(workoutTime);
  	 		
  	 		}
  	 		
   		    data.add(timeRn.toString()+" |  Have a meal ");
 	 		timeRn=timeRn.plusMinutes(mealTime);
	 		
	 		
 	 		
 			

  	 		
  	 		//System.out.println("G O A L T I M E and timern "+goaltime+"    "+timeRn);

 	 		data.add(timeRn.toString()+" |  Work on your goal: "+tifa.getGoal().toString());	
			/* timeRn=timeRn.plusMinutes(goaltime); */
 	 		
  	 		
 	 		
 	 		
  	 		
 	 		while(goaltime>120)
  	 		{ 	
	  	 		if(goaltime-120>=60)
	  	 		{
	  	 			
	  	 			data.add(timeRn.toString()+" |  Take rest from "+timeRn.toString()+" to "+timeRn.plusMinutes(30));
	  	 			timeRn=timeRn.plusMinutes(30);
	  	 			restTime=restTime-30;
	  	 			
	  	 			data.add(timeRn.toString()+" |  Work on your goal: : "+tifa.getGoal().toString());
  	 			timeRn=timeRn.plusMinutes(120);
  	 			goaltime=goaltime-120;
  	 			
  	 			
	  	 		}
	  	 		else
	  	 		{
	  	 			data.add(timeRn.toString()+" |  Take rest from "+timeRn.toString()+" to "+timeRn.plusMinutes(restTime));
		 			timeRn=timeRn.plusMinutes(restTime);
		 			restTime=restTime-restTime;
	  	 			
	  	 			
	  	 		data.add(timeRn.toString()+" |  Work on your goal: "+tifa.getGoal().toString()+", till "+timeRn.plusMinutes(goaltime));
 	  	 		timeRn=timeRn.plusMinutes(goaltime); 
 	  	 	    goaltime=goaltime-goaltime;
 	  	 	
	  	 		}
  	 			
  	 		}
  	 		
  	 		// it will run the below code when the goaltime is less than 120 from the starting
  	 		if(goaltime>0 && restTime>0) 
  	 		{ 
  	 		data.add(timeRn.toString()+"  |  Work on your goal: "+tifa.getGoal().toString());
  	 			timeRn=timeRn.plusMinutes(goaltime);
  	 		data.add(timeRn.toString()+" |  Take rest from "+timeRn.toString()+" to "+timeRn.plusMinutes(restTime));
  	 			timeRn=timeRn.plusMinutes(restTime);
  	 		}

 	 		data.add(timeRn.toString()+" |  Use phone till "+timeRn.plusMinutes(phoneTime));
	 		timeRn=timeRn.plusMinutes(phoneTime);
 	 		
 			// this will have the hours and minutes not just hours
 		    
 		    
	 		
 		    data.add(timeRn.toString()+" |  Start getting ready to sleep");
	 	 		
 		    timeRn=timeRn.plusMinutes(gettingReadyForSleep);
  		    data.add(timeRn.toString()+" |  Go to sleep ");
  		    timeRn=workingHoursTo;
  		    
				/*
				 * System.out.println("T O T A L T I M E " +totalTimeBeingUsed);
				 * 
				 * System.out.println("t i m e R n is "+timeRn.toString());
				 */	
		    model.addAttribute("data",data);
		    
		     		 
 	 		//making chart starts from here
		    String jsonString= makeChart(timeFactorLabelValueInterface,username);
		    model.addAttribute("convertedJson",jsonString);
 	 		
 	 	 	
 	 		}
 	 		}
 	 		
 	 		
 	 		
 	 		 List<TimeFactorLabelValueInterface> entityVal= jpanewOfflineTableRepo.getLabelAndValueByUsername(username); 
 			   if(entityVal!=null) 
 			  {
 				   model.put("message", "Hello "+tifa.getUsername()	  +", Your new healthy schedule is here. ");
			/* object.deleteByUsername(tifa.getUsername()); */
 				   // object.deleteByUsername(getLoggedInUserName());
 				   object.save(tifa);
 			  }
 			     else  
 			     {
 	 		  model.put("message", "Thank you "+tifa.getUsername()
 			  +" Your details are saved.");  
 	 		  object.save(tifa);
 			     }
 	 		
		/*
		 * object.insertUser("labelName", 100, "userNameName");
		 */ 			   //System.out.println("HOPEFULLY SAVED");
 			   
 		 	return "fresh-schedule";
   			
 	}
 	
 	
 	private String makeChart(List<TimeFactorLabelValueInterface> timeFactorLabelValueInterface,String username) throws JsonProcessingException
 	{
 		 
 		 List<TimeFactorLabelValueInterface> timeFactorLabelValueInterfaces=jpanewOfflineTableRepo.getLabelAndValueByUsername(username);
	 		ObjectMapper objectMapper=new ObjectMapper();
	 	String jsonString=
objectMapper.writeValueAsString(timeFactorLabelValueInterfaces);
	 		
	 			
	 	System.out.println("jsonstring "+jsonString);
	 	System.out.println("username "+username);

	 	
	 		
 		return jsonString;
 		
 	}
  	 		
}
 //		    odel.addAttribute("wakeUpFromSleep",timeRn);	 


//	 		timeRn=timeRn.plusMinutes(gettingReady);

//	tifaTwo.setWorkoutTime(timeRn.toString());

//  model.addAttribute("mealTime",timeRn);

//	tifaTwo.setPhoneTime(timeRn.toString());


//	model.addAttribute("tifaTwo",tifaTwo);

//	tifaTwo.setWorkingHoursFrom(tifa.getWorkingHoursFrom());
//	tifaTwo.setWorkingHoursTo(tifa.getWorkingHoursTo());
//	model.addAttribute("gettingReady",timeRn);

//model.addAttribute("commutefromhome",timeRn);

//	tifaTwo.setWorkingHoursFrom(tifa.getWorkingHoursFrom());
//	tifaTwo.setWorkingHoursTo(tifa.getWorkingHoursTo());
//tifaTwo.setCommuteTime(timeRn.toString()); 

//	tifaTwo.setWorkoutTime(timeRn.toString());

//model.addAttribute("mealTime",timeRn);

// tifaTwo.setPhoneTime(timeRn.toString());
// model.addAttribute("tifaTwo",tifaTwo);

// model.addAttribute("goalTime",LocalTime.parse("00:00").plusMinutes(goaltime));

// model.addAttribute("gettingReadyForSleep",timeRn);

// model.addAttribute("goToSleep",timeRn);	 		

// tifaTwo.setGoal(tifa.getGoal());

// timeoverflow is not updated in all the testcases

