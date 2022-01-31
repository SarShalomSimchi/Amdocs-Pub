package com.amdocs.task.beverages;

public class BeverageFactory {

//	
//	private static final Map<Integer, Beverage> beverages = Map.of(
//			1, new Mochito(),
//			2, new Beer(), 
//			3, new Cosmopolitan());
//	
	
	
	public static Beverage getBevderage(int id) {
		
		switch (id) {
		case 1:
			return new Mochito();
		case 2:
			return new Beer();
		case 3:
			return new Cosmopolitan();
		}
		
		throw new IllegalArgumentException();
	}
	
	
	
//	public static Beverage getBeverageFly(int id) {
//		return beverages.get(id);
//	}
}
