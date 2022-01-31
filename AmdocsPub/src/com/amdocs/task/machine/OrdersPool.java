package com.amdocs.task.machine;

import java.util.concurrent.PriorityBlockingQueue;
//Proxy
public class OrdersPool<E> {
	private PriorityBlockingQueue<E> queue; 
	private int maxCapacity;
	
	public OrdersPool(int maxCapacity) {
		this.queue = new PriorityBlockingQueue<>(maxCapacity);
		this.maxCapacity = maxCapacity;
	}
	
	public boolean add(E e) {
		if(queue.size() < maxCapacity)
			return queue.add(e);
		
		return false;
	}
	
	public E take() throws InterruptedException {
		return queue.take();
	}
	
	public int size() {
		return queue.size();
	}
}

