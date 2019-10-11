package com.revature;

/*
 * What are the different ways to make a thread?
 * 
 * 1. Extend the Thread class
 * 2. Implement the Runnable interface (preferred)
 * 		- Create class and implement
 * 		- Runnable is a functional interface, so we can use a lambda
 * 
 * Final in Java -
 * 	Can apply to:
 * 		1. Variable declaration - Variable cannot be reassigned
 * 		2. Method parameter - Variable cannot be reassigned
 * 		3. Method signature - Method cannot be overridden
 * 		4. Class definition - Class cannot be extended
 * 
 * 			* Abstract class and interfaces cannot be marked final
 * 
 * 
 */
public class BasicThreading {
	
	public static void threadExample() {
		// We can create lots of threads (but probably don't)
		for(int i = 0; i < 1000; i++) {
			CountingThread countingThread = new CountingThread(10);
			// mistake 1
//			countingThread.run(); this will just call run, not start a thread
			countingThread.start();		
		}

	}
	
	public static void lambdaExample() {
		// Type is Runnable, it can infer that the lambda
		// must then implement the 'run' method (because it's the only
		// method on Runnable).
		// Curly brackets around code block are optional when there is only
		// a single operation in the method body
		Runnable runnable = () -> System.out.println("Hello");
		
		// infers that parameters are passed to method
		//		System.out::println
//		Thread thread = new Thread(runnable);
		Thread thread = new Thread(() -> System.out.println("Hello"));
		thread.start();		
	}
	
	public static void runnableExample() {
		Thread myThread = new Thread(new RunnableThread(10));
		myThread.start();
	}
	
	public static void noClosures() {
		int x = 10;
		x++;
		Thread thread = new Thread(() ->  {
			// Can't interact with x because it is not final or 'effectively final'
//			System.out.println(x);
		});
	}
	
	public static void main(String[] args) {
		noClosures();
//		Sample sample = new Sample((x) -> {}); 
	}
}

/*
 * Runnable is an instance of what we call (in Java) a "Functional Interface".
 * An interface is considered a functional interface if, and only if, it defines
 * a single abstract method.
 * Runnable defines only 1 abstract method - run
 * As such, it is considered a functional interface.
 * 
 * - Comparable (sort of)
 * - Comparator 
 * 
 * Functional Interfaces can be implemented in a shorthand syntax called a "lambda expression"
 * (or you may hear them called "arrow functions")
 * 
 * A lambda expression, is a shorthand expression for a method or function. The function definition
 * is inferred by an interface that it is assigned to or used as a parameter for. As such,
 * lambda expressions are only valid when they correctly implement a known functional interface.
 * 
 * Basic anatomy:
 * 
 * parameters    arrow notation      function body
 * (a, b, c)          ->                 {}
 * 
 * Sample implementation of Runnable might look like
 * () -> { System.out.println("Hello!"); }
 * 
 * Caveats:
 * 		* Must be a functional interface
 * 		* Error text associates with lambdas is more complicated
 * 		* Java doesn't have full support for closures, arrow expression which access
 * 				data outside the arrow expression can only do so when that object is effectively
 * 				final
 * 
 */
class RunnableThread implements Runnable {
	int startingPoint;
	
	RunnableThread(int startingPoint) {
		this.startingPoint = startingPoint;
	}
	
	public void run() {
		for(int i = this.startingPoint; i > 0; i--) {
			System.out.print(i + " ");
		}
	}	
}

class Sample {
	
	Sample(Runnable runnable) {
		System.out.println("Runnable");
	}
	
	Sample(SomeInterface someInterface) {
		System.out.println("some interface");
	}
}

interface SomeInterface {
	
	void doStuff(int x);
}


/*
 * Extend Thread and override the run method (public void run())
 * with the thread's execution logic.
 */
class CountingThread extends Thread {
	private int max;
	public CountingThread(int max) {
		this.max = max;
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
		for(int i = 0; i < max; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print(i + " ");
		}
	}
	
}