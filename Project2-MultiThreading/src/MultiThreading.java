/*
 Name:James MacMahon
 Course: CNT4714 Fall2020
 Assignment title: Project 2–Multi-threaded programming in Java
 Date: October 4, 2020
 Class: Enterprise IT
*/

import java.util.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreading {

	// maximum number of stations
	static int MAX_NUM = 10;
	static int numOfStations = 0;
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("\t**Start**\n");
		ExecutorService application = Executors.newFixedThreadPool(MAX_NUM);

		// scan values to an array
        int []stationInfo = scanFile(); 
        
        // set number of stations to whatever was in the first line of the config file
        numOfStations = stationInfo[0];
        
        // declare station and conveyor object arrays for storing as many of them as we need
        Station []myStation = new Station[numOfStations];
        Conveyor []myConveyor = new Conveyor[numOfStations];
        
        // a for loop creates an object for the necessary amount of conveyors
        for(int i = 0; i < numOfStations; i++) {
        	
        	myConveyor[i] = new Conveyor(i);
        	System.out.println(myConveyor[i].toString());
        }
        
        // a for loop creates an object for each station and assigns an input and output conveyor and a workload
        for(int j = 0; j < numOfStations; j++) {
        	
        	myStation[j] = new Station(myConveyor[j], myConveyor[(j + 1) % (numOfStations - 1)], stationInfo[j + 1], j);
        	System.out.println(myStation[j].toString());

        	// execute the threads
        	application.execute(myStation[j]);               
        }

        // wait for threads to terminate then send information to file and shutdown
        application.awaitTermination(numOfStations, TimeUnit.SECONDS);
        System.out.println("\n\t**End**"); 
        
        application.shutdown(); 
        PrintStream out = new PrintStream(new FileOutputStream("multiThreadOutput.txt"));
        System.setOut(out);
    }
	
	public static int[] scanFile() {
		   
		 // array to store values from config file
		 int arr[] = new int[MAX_NUM]; 
		
		 int i = 0;	
		 File myFile = new File("config.txt");
	     
		 // try to scan file and store values in an array
		 try (Scanner in = new Scanner(myFile)) {

			 while (in.hasNextLine()) {
			   
				 arr[i] = in.nextInt();
				 System.out.println("Line " + (i + 1) + " contains " + arr[i]);	
				 i++;	      	      	
			 }
		 }
		 catch(Exception ex) {
			 
			 System.err.println("\tError");
		 }
		 
		 return arr;
	}
}