package com.amdocs.task.machine;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import com.amdocs.task.Priority;
import com.amdocs.task.beverages.Beverage;

public class OrderMachine  implements OrderActions {
	private static final int MAX_ORDERS_CAPACITY = 50;
	public static final int BEVERAGE_PREPERATION_TIME_IN_MILSEC = 5000;
	
	private OrdersPool<Order> orders;
	private Object lock = new Object();
	private Timer timer = new Timer();
	
	
	public OrderMachine() {
		orders = new OrdersPool<Order>(MAX_ORDERS_CAPACITY);
	}


	@Override
	public void orderDrink(Beverage beverage) {
		synchronized (lock) {
			System.out.println("\nNew order: " + beverage.getName().toUpperCase());
			Order order = createOrder(beverage);
			boolean success = orders.add(order);
			System.out.println("OrdersPool size == " + orders.size());
			if(!success)
				throw new OrdersLimitHasReachedExceprion(order);	 //TODO
		}
	}

	private Order createOrder(Beverage beverage) {
		return new Order(beverage);
	}

	@Override
	public void orderRecurringHappyHourDrink(Beverage beverage, int interval) {
		System.out.println("\nHAPPY HOUR " + beverage.getClass().getSimpleName());
		scheduleOrder(beverage, interval);
	}

	@Override
	public int getNextDrink() {
		Order order = null;
		try {
			order = orders.take();
		} catch (InterruptedException e) {
			throw new RuntimeException(e.getMessage());
		}
		
		return order.getId();
	}
	
	public int waitingOrdersCount() {
		return orders.size();
	}
	
	private void scheduleOrder(Beverage beverage, int interval) {
		timer.scheduleAtFixedRate(new TimerTask() {
		  @Override
		  public void run() {
			  orderDrink(beverage);
		  }
		}, 0, interval);
	}


	
	private class Order implements Comparable<Order> {
		int id;
		Priority priority;
		LocalDateTime initiation;

		public Order(Beverage beverage) {
			this.id = beverage.getId();
			this.priority = beverage.getPriority();
			this.initiation = LocalDateTime.now(); 
		}

		public int getId() {
			return id;
		}

		public Priority getPriority() {
			return priority;
		}
		
		public LocalDateTime getInitiation() {
			return initiation;
		}

		@Override
		public int compareTo(Order o) {
			if(this.getPriority() == o.getPriority()) {
				return o.getInitiation().compareTo(this.getInitiation());
			}

			return o.getPriority().getWeight() - this.getPriority().getWeight();
		}
	}
	
	
	public class OrdersLimitHasReachedExceprion extends RuntimeException {
		private static final long serialVersionUID = -3557533302848914640L;

		public OrdersLimitHasReachedExceprion(Order order) {
			super("Orders Limit Has Reached, order with beverage id " + order.getId() + " is declined.");
		}
	}

}
