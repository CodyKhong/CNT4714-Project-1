import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Conveyor {

	// variables for number of conveyors and for locking
	int conveyorNum;
	private Lock myLock = new ReentrantLock();

	// constructor for conveyor sets the conveyor number
	public Conveyor(int conveyorNum) {
	   
		this.conveyorNum = conveyorNum;
	} 

	// lock the conveyor
	public boolean acquireLock() {
		
		if(myLock.tryLock())
			return true;
		else
			return false;
	}
	
	// unlock conveyor
	public void doUnlock() {
		
		myLock.unlock();
	}	
	
	@Override
	public String toString() {
		return "conveyor: " + conveyorNum;
	}

}