package com.amdocs.task;

public enum Priority {
	LOW(0), MEDIUM(50), HIGH(100);
	
	
	private int weight;
	
	Priority(int weight) {
		this.weight = weight;
	}
	
	public int getWeight() {
		return weight;
	}
}
