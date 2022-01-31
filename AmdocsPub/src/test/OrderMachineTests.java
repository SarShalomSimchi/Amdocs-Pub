package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amdocs.task.beverages.Beer;
import com.amdocs.task.beverages.Beverage;
import com.amdocs.task.beverages.Cosmopolitan;
import com.amdocs.task.beverages.Mochito;
import com.amdocs.task.machine.OrderMachine;

public class OrderMachineTests {
	private static final Map<Integer, Beverage> menu = Map.of(
			Mochito.ID, new Mochito(),
			Beer.ID, new Beer(),
			Cosmopolitan.ID, new Cosmopolitan()
	);
	
	
	private OrderMachine orderMachine;
	 
	@BeforeEach
	public void setUp() {
		orderMachine = new OrderMachine();
	}

	
	@Test
	public void orderABeverage_isInOrdersPool()	{
		Beverage beverage = fetchBeverage(1);
		orderMachine.orderDrink(beverage);
		
		int drinkId = orderMachine.getNextDrink();
		
		assertEquals(beverage.getId(), drinkId);
	}
	
	@Test
	public void allOrders_areInOrdersPool()	{
		
		for (int i = 0; i < 5; i++) {
			Beverage beverage = fetchBeverage(1);
			orderMachine.orderDrink(beverage);
		}
		
		assertEquals(5, orderMachine.waitingOrdersCount());
	}
	
	
	@Test
	public void over50OrdersInOrderPool_throwsException()	{
		
		for (int i = 1; i <= 50; i++) {
			Beverage beverage = fetchBeverage(Mochito.ID);
			orderMachine.orderDrink(beverage);
		}
		
		Exception exception = assertThrows(OrderMachine.OrdersLimitHasReachedExceprion.class, () -> {
			Beverage beverage = fetchBeverage(Cosmopolitan.ID);
			orderMachine.orderDrink(beverage);
		});
		
		assertEquals("Orders Limit Has Reached, order with beverage id " + Cosmopolitan.ID + " is declined.", exception.getMessage());
	}
	
	
	//Mochito - LOW
	//Beer - MEDIUM
	//Cosmopolitan - HIGH
	@Test
	public void machineUpholdsToBeveragesPriority()	{
		Beverage beverage = fetchBeverage(Mochito.ID);
		orderMachine.orderDrink(beverage);
		
		beverage = fetchBeverage(Beer.ID);
		orderMachine.orderDrink(beverage);
		
		beverage = fetchBeverage(Cosmopolitan.ID);
		orderMachine.orderDrink(beverage);
		
		int drinkId = orderMachine.getNextDrink();
		assertEquals(Cosmopolitan.ID, drinkId);
		
		drinkId = orderMachine.getNextDrink();
		assertEquals(Beer.ID, drinkId);
		
		drinkId = orderMachine.getNextDrink();
		assertEquals(Mochito.ID, drinkId);
	}
	
	
	@Test
	public void orderHappyHourDrinkInsertes() {
		Beverage beverage = fetchBeverage(Mochito.ID);
		orderMachine.orderRecurringHappyHourDrink(beverage, 500);
		
		try {
			Thread.sleep(1250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(3, orderMachine.waitingOrdersCount());
	}
	
	
	private Beverage fetchBeverage(int drinkId) {
		return menu.get(drinkId);
	}
}
