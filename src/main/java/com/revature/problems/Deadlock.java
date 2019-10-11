package com.revature.problems;

import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {
	
	public static void main(String[] args) throws InterruptedException {
		BankAccount a = new BankAccount();
		BankAccount b = new BankAccount();
		
		a.balance = 1000;
		b.balance = 0;
		
		final int count = 1000;
		
		Thread threadA = new Thread(() -> {
			for(int i = 0; i < count; i++) {
				a.transfer(1, b);
			}
		});
		
		Thread threadB = new Thread(() -> {
			for(int i = 0; i < count; i++) {
				b.transfer(1, a);
			}
		});
		
		threadA.start();
		threadB.start();
		
		threadA.join();
		threadB.join();
		
		System.out.println("Done!");
		System.out.println(a.balance);
		System.out.println(b.balance);
	}

}

class BankAccount {
	int balance = 0;
	
	public synchronized void withdraw(int amount) {
		balance -= amount;
	}
	
	public synchronized void deposit(int amount) {
		balance += amount;
	}
	
	public void transfer(int amount, BankAccount b) {
		
		ReentrantLock lock = new ReentrantLock();
		// To lock an arbitrary object you can use a synchronized block
		synchronized (this) { // locks current object
			synchronized (b) { // locks b
				this.balance -= amount;
				b.balance += amount;
			}
		}
	}
}