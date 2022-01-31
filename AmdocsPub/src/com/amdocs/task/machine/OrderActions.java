package com.amdocs.task.machine;

import com.amdocs.task.beverages.Beverage;

public interface OrderActions{
	/** Adds a new drink with priority
	*@param drinkId - The id of the drink (Mochito, Beer, Cosmopolitan)
	*@param priority - The Priority of the order
	*/
	void orderDrink(Beverage beverage);
	
	/**
	* Adds a recurring HappyHour occasion that will be taking place according to priority and will be executed every X interval
	* @param drinkId - The id of the drink (Mochito, Beer, Cosmopolitan)
	* @param priority - The Priority of the order
	* @param interval - The interval that between each happy hour execution of that type in # of seconds
	*/
	void orderRecurringHappyHourDrink(Beverage beverage, int interval) ;
	
	/**
	* Gets the next drink according to the pub rules
	* @return drinkId that bartender needs to prepare
	*/		
	int getNextDrink();
}