package io.schedule.maker.sm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class newofflinetable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String label;
	private String value;
	private String username;
 
	
	
	
	
	
	
	public newofflinetable(String label, String value, String username) {
		super();
		this.label = label;
		this.value = value;
		this.username = username;
	}







	public newofflinetable(int id, String label, String value, String username) {
		super();
		this.id = id;
		this.label = label;
		this.value = value;
		this.username = username;
	}







	public int getId() {
		return id;
	}







	public void setId(int id) {
		this.id = id;
	}







	public String getLabel() {
		return label;
	}







	public void setLabel(String label) {
		this.label = label;
	}







	public String getValue() {
		return value;
	}







	public void setValue(String value) {
		this.value = value;
	}







	public String getUsername() {
		return username;
	}







	public void setUsername(String username) {
		this.username = username;
	}







	public newofflinetable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
