package com.amdocs.task.beverages;

import com.amdocs.task.Priority;

public class Cosmopolitan extends BeverageBase {
	
	public static final int ID = 3;
	private static final Priority PRIORITY = Priority.HIGH;	
	private static final String NAME = "Cosmopolitan";
	
	public Cosmopolitan() {
		super(ID, PRIORITY, NAME);
	}
	
	@Override
	public void prepare() {
		System.out.print("PREPARING a Cosmopolitan");
	}

}
