package com.revature.problems;

import java.util.concurrent.atomic.AtomicInteger;

public class NotThreadSafe {
	public static void main(String[] args) {
		MyObject myObject = new MyObject();
		MyAtomicObject myAtomicObject = new MyAtomicObject();
		
		final int count = 10000;
		
		// Increment x 100 times
		Thread a = new Thread(() ->  {
			for(int i = 0; i < count; i++) {
				myObject.x++;
			}
		});
		
		// Decrement x 100 times
		Thread b = new Thread(() -> {
			for(int i = 0; i < count; i++) {
				myObject.x--;
			}
		});
		
		Thread c = new Thread(() -> {
			for(int i = 0; i < count; i++) {
				myAtomicObject.x.incrementAndGet();
			}
		});
		
		Thread d = new Thread(() -> {
			for(int i = 0; i < count; i++) {
				myAtomicObject.x.decrementAndGet();
			}
		});
		
		
		a.start();
		b.start();

		
		try {
			// waiting for both threads to finish to continue main thread
			a.join();
			b.join();
			
			c.start();
			d.start();
			c.join();
			d.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Expectation: 0
		System.out.println(myObject.x);
		System.out.println(myAtomicObject.x.get());

		
	}
	
	
	
}

class MyObject {
	int x = 0;
}

class MyAtomicObject {
	AtomicInteger x = new AtomicInteger(0);
}
