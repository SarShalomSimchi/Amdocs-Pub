package com.amdocs.task.beverages;

import com.amdocs.task.Priority;

public interface Beverage {
	public void prepare(); 
	
	public int getId();

	public Priority getPriority();
	
	public String getName();
}
