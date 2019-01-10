import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.lang.Thread.*;


public class Taxi extends Thread{
	public static int location;
	public int branches, target, people, time, peopleTwo;
	ArrayList<Integer> destinations;
	boolean up;
	private Semaphore[] semaphores;
			
	public Taxi(int n, int p){
		location = 1;
		branches = n;
		target = 0;
		time = 0;
		up = true;
		people = p;
		peopleTwo = p;
		semaphores = new Semaphore[branches];
		destinations = new ArrayList<>();

		for(int i=0;i<branches;i++){
			semaphores[i] = new Semaphore(people);
			semaphores[i].drainPermits();
		}
	}

	boolean setTarget(){
		synchronized(destinations){
			if(destinations.size()==0){
				return false;
			}
			
			if(destinations.get(destinations.size()-1)==location ){
				up = !up;
				Collections.sort(destinations);
				if(!up){
					Collections.reverse(destinations);
				}
					
			}
			if(peopleTwo==0){
				return true;
			}
			if(destinations.size()==0){
				return false;
			}
			else if(destinations.size()==1 && destinations.get(0) ==location){
				destinations.remove(new Integer(location));
				return false;
			}
			else if(destinations.size()==1 && destinations.get(0) !=location){
				target = destinations.get(0);
				destinations.remove(new Integer(location));
				return true;
			}
			else{
				target = destinations.get(destinations.indexOf(location)+1);
				destinations.remove(new Integer(location));
				return true;
			}		
		}
	}
	void move(){
		location = target;
	}
	public void removePerson(){
		peopleTwo--;
	}

	public void giveDestination(int dest){
		synchronized(destinations){
			if(!destinations.contains(dest)){
				destinations.add(dest);
				Collections.sort(destinations);
				if(!up){
					Collections.reverse(destinations);
				}
			}
		}
	}
	
	public String getTime(){
		String b;
		String a;
		int hours = 9;
		int minutes = time;
		while(minutes>59){
			minutes = minutes - 60;
			hours += 1;
		}
		a = Integer.toString(hours);
		if(minutes < 10){
			b= "0"+Integer.toString(minutes);
		}
		else{
			b = Integer.toString(minutes);
		}
		return a+":"+b;
	}
	
	public void taxiSleep(){		
		time += 1;
		try{	
			Thread.sleep(33);
		}
		catch(Exception e){
			e.printStackTrace();
		}	
	}

	public Semaphore getSem(int i){
		
		return semaphores[i];
	}

	public void run() {
		while(this.peopleTwo != 0){
			//wait for destinations
			try{
				this.getSem(location).release(people);
				this.taxiSleep();
				System.out.println(getTime()+" branch "+location+": taxi depart");
				this.getSem(location).acquire(people);
			}
			catch(Exception e){
				e.printStackTrace();
			}

			while(!this.setTarget()){
				this.taxiSleep();
				if(peopleTwo == 0){
					break;
				}
			}
			if(peopleTwo == 0){
					break;
			}

			this.taxiSleep();
			this.taxiSleep();
			
			this.move();
			System.out.println(getTime()+" branch "+location+": taxi arrive");

		}
	}
}
