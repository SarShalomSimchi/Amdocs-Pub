package com.amdocs.task.beverages;

import com.amdocs.task.Priority;

public class Mochito extends BeverageBase {

	public static final int ID = 1;
	private static final Priority PRIORITY = Priority.LOW;
	private static final String NAME = "Mochito";
	
	public Mochito() {
		super(ID, PRIORITY, NAME);
	}
	
	@Override
	public void prepare() {
		System.out.print("PREPARING a Mochito");
	}
}
