package com.amdocs.task.beverages;

import com.amdocs.task.Priority;

public abstract class BeverageBase implements Beverage {

	private int id;
	private Priority priority;	
	private String name;
	
	public BeverageBase(int id, Priority priority, String name) {
		this.id = id;
		this.priority = priority;
		this.name = name;
	}
	
	public abstract void prepare(); 
	
	public int getId() {
		return id;
	}

	public Priority getPriority() {
		return priority;
	}
	
	public String getName() {
		return name;
	}
}
