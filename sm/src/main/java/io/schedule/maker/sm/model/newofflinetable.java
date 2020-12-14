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
	private String username;
	private String value;
	private String label;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public newofflinetable(String username, String value, String label) {
		super();
		this.username = username;
		this.value = value;
		this.label = label;
	}
	public newofflinetable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
