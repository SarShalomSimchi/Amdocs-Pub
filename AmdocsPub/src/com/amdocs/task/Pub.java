package com.amdocs.task;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.amdocs.task.beverages.Beer;
import com.amdocs.task.beverages.Beverage;
import com.amdocs.task.beverages.Cosmopolitan;
import com.amdocs.task.beverages.KopanganBucket;
import com.amdocs.task.beverages.Mochito;
import com.amdocs.task.machine.OrderMachine;


public class Pub {

public static final int BEVERAGE_PREPERATION_TIME_IN_MILSEC = 700;
	
	//Flyweight
	private static final Map<Integer, Beverage> menu = Map.of(
		Mochito.ID, new Mochito(),
		Beer.ID, new Beer(),
		Cosmopolitan.ID, new Cosmopolitan()
	);

	private OrderMachine orderMachine;
	
	public Pub(OrderMachine orderMachine) {
		this.orderMachine = orderMachine;
	}
	
	public void startServing() {
		createBartender();
	}
	
	
	private void createBartender() {
		Bartender bartemder = new Bartender();
		bartemder.serve();
	}
	
	public class Bartender { //private
		public void serve() {
			while(true) {
				try {
					Beverage beverage = getNextOrderedBeverage();
					prepareBeverage(beverage);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}

		public Beverage getNextOrderedBeverage() { //private
			int drinkId = orderMachine.getNextDrink();
			
			Beverage beverage = menu.get(Math.abs(drinkId));
			if(isKopanganBucket(drinkId))
				beverage = createKopanganBucket(beverage);
			
			return beverage;
		}

		
		private boolean isKopanganBucket(int drinkId) {
			return drinkId < 1;
		}

		public void prepareBeverage(Beverage beverage) { //private
			try {
				prepare(beverage);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}

		private void prepare(Beverage beverage) throws InterruptedException {
			beverage.prepare();
			//System.out.println();
			Thread.sleep(BEVERAGE_PREPERATION_TIME_IN_MILSEC);
			System.out.println(beverage.getName() + " is ready!\n");
		}
	}
	
	
	private Beverage createKopanganBucket(Beverage beverage) {
		return new KopanganBucket(beverage);
	}
	
	public void orderDrink(int drinkId) {
		validate(drinkId);
		Beverage beverage = menu.get(drinkId);
		orderMachine.orderDrink(beverage);
	}
	
	private void validate(int drinkId) {
		if(!isInMenu(drinkId))
			throw new InvalidDrinkIdExceprion();
	}

	private boolean isInMenu(int drinkId) {
		return menu.containsKey(drinkId);
	}

	public void orderHappyHourDrink(int drinkId, int interval) {
		validate(drinkId);
		Beverage beverage = menu.get(drinkId);
		orderMachine.orderRecurringHappyHourDrink(createKopanganBucket(beverage), interval);
	}
	
	class Client implements Runnable {
		@Override
		public void run() {
			while(true) {
				try {
				    orderDrink(Beer.ID);
				    Thread.sleep(2000);
				} catch (Exception  e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	public class InvalidDrinkIdExceprion extends RuntimeException {
		private static final long serialVersionUID = 1084244105218875693L;

		public InvalidDrinkIdExceprion() {
			super("Invalid Drink Id");
		}
	}
	
	public static void main(String[] args) {
		 ThreadPoolExecutor clients = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
		 OrderMachine orderMachine = new OrderMachine();
		 Pub pub = new Pub(orderMachine);
		 
		 
//		 List.of(1,2).stream().forEach(num -> {
//			 Client client = pub.new Client();
//			 clients.execute(client);
//		 });
		 
		 IntStream.range(0, 2)
		  .forEach(index -> {
			  Client client = pub.new Client();
			  clients.execute(client);
		  });
		 
		 
		 Stream.
			iterate(1, i -> i < 2, i -> i + 1)
			.forEach(i -> {
				  Client client = pub.new Client();
				  clients.execute(client);
			  });
		 
		 
		 
		 pub.orderHappyHourDrink(Mochito.ID, 7000); 
		 pub.orderHappyHourDrink(Cosmopolitan.ID, 4000);
		 
		 pub.startServing();
	}

}
