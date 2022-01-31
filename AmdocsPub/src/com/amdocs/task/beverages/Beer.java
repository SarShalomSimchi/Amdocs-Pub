package com.amdocs.task.beverages;

import com.amdocs.task.Priority;

public class Beer extends BeverageBase {
	
	public static final int ID = 2;
	private static final Priority PRIORITY = Priority.MEDIUM;	
	private static final String NAME = "Beer";

	public Beer() {
		super(ID, PRIORITY, NAME);
	}
	
	@Override
	public void prepare() {
		System.out.print("PREPARING a Beer");
	}
}
