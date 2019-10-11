package com.revature.states;

/**
 * Very common interview question?
 * 
 * What are the states of a Thread?
 * Could be worded as: What is the lifecycle of a thread?
 * 
 * 1.  NEW - Thread has been created, but run has not been called by start.
 * 2.  RUNNABLE - Thread is qualified to be running or is running.
 * 3.  WAITING - Thread is waiting for an indeterminate amount of time
 * 				caused by Thread.join(), wait, etc.
 * 4.  TIMED_WAITING - Thread is waiting for a defined period of time or with
 * 				a timeout. Thread.sleep(int), Thread.join(int), etc.
 * 5.  BLOCKED - Thread is blocked by another thread which holds a lock on
 * 				a required resource.
 * 6.  TERMINATED - Thread run method has completed.
 * 
 * 
 * @author Mitch
 *
 */
public class ThreadStates {

	void newDemo() {
		Thread a = new Thread(() -> {});
		System.out.println(a.getState());
	}
	
	void runnableDemo() {
		System.out.println(Thread.currentThread().getState());
	}
	
	void waitingDemo() {
		Thread thread = new Thread(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		Thread b = new Thread(() -> {
			try {
				thread.join();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		thread.start();
		b.start();
		try {
			Thread.sleep(1);
			System.out.println(b.getState());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	void timedWaitingDemo() {
		Thread thread = new Thread(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread.start();
		try {
			Thread.sleep(1);
			System.out.println(thread.getState());
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	void blockedDemo() {
		BlockingObject o = new BlockingObject();
		Runnable runnable = () -> {
			try{
				o.pause();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		};
		
		Thread a = new Thread(runnable);
		Thread b = new Thread(runnable);
		
		a.start();
		try {
			Thread.sleep(1);
			b.start();
			Thread.sleep(1);
			System.out.println(b.getState());
			a.join();
			b.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void terminatedDemo() {
		Thread thread = new Thread(() -> {});
		thread.start();
		try {
			thread.join();
			System.out.println(thread.getState());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ThreadStates a = new ThreadStates();
		a.newDemo();
		a.runnableDemo();
		a.waitingDemo();
		a.timedWaitingDemo();
		a.blockedDemo();
		a.terminatedDemo();
	}
	
}

class BlockingObject {
	
	// synchronized in a method signature will block access to the object
	// to other threads for duration of the locking thread's use
	public synchronized void pause() throws InterruptedException {
		Thread.sleep(3000);
	}
}
