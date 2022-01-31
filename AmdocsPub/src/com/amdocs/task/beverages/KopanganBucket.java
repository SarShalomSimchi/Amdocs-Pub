package com.amdocs.task.beverages;

import com.amdocs.task.Priority;

//Decorator
public class KopanganBucket implements Beverage {

	private Beverage beverage;

	public KopanganBucket(Beverage beverage) {
		this.beverage = beverage;
	}
	
	@Override
	public void prepare() {
		beverage.prepare();
		System.out.print(" in a Kopangan Bucket");
	}

	@Override
	public int getId() {
		return -beverage.getId();
	}
	
	@Override
	public Priority getPriority() {
		return beverage.getPriority();
	}
	
	@Override
	public String getName() {
		return "KopanganBucket with a " + beverage.getName();
	}
	
}
