import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.io.*;

public class Station implements Runnable {
		 
	private static Lock myLock = new ReentrantLock();
	
	public Conveyor conveyor0;
	public Conveyor conveyor1;
	public int workload;
	public int station;
	
	public Station(Conveyor conveyor0, Conveyor conveyor1, int workload, int station) {
		
		super();
		this.conveyor0 = conveyor0;
		this.conveyor1 = conveyor1;
		this.workload = workload;
		this.station = station;
	}

	public void doWork() {
	
		Random rand0 = new Random();
		
		System.out.println("\tStart moving the package");
		System.out.println("Station " + station + ": successfully moves package from " + conveyor0);
		System.out.println("Station " + station + ": successfully moves package to " + conveyor1);	
		
		workload = workload - 1;
		
		System.out.println("Station " + station + ": has " + workload + " packages left.");	

		try {
	
				Thread.sleep(rand0.nextInt(100));			
			} 
			catch (InterruptedException e) {
				
				System.err.println("Error");
				e.printStackTrace();
			}
			
			if(workload == 0){
				
				System.out.println("Station " + station + ": Workload successfully completed.");
				return;
			}	
	}
	
	@Override
	public void run() {

		myLock.lock();
		
		if(conveyor0.acquireLock()) {
			System.out.println("\nStation " + station + ": In-Connection set to " + conveyor0);
		
			if(conveyor1.acquireLock()) {
				System.out.println("Station " + station + ": Out-Connection set to " + conveyor1);	
	
				System.out.println("Station " + station + ": Workload set to " + workload);
				System.out.println("\n\t**Locks secured**");
				
				try {
					while(workload != 0){			
			
						try {
						
							doWork();
			            } 
						catch (Exception e) {
			            	
							System.err.println("Error");
							e.printStackTrace();
			            }				
					}
				
					System.out.println("Station " + station + ": released " + conveyor0);
					System.out.println("Station " + station + ": released " + conveyor1);
					myLock.unlock();
					conveyor0.doUnlock();
					conveyor1.doUnlock();
					System.out.println("\t**Locks released**");
				}
				catch(Exception e) {
					
					System.err.println("Error");
					e.printStackTrace();
				}	
			}
		}
	}
	
	@Override
	public String toString() {
		
		return "Station: " + station + " conveyor0 = " + conveyor0 + ", conveyor1 = " + conveyor1 + ", workload = " + workload;
	}	    
}