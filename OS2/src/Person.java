import java.io.*;
import java.util.*;
import java.lang.Thread.*;



public class Person extends Thread{
	String number;
	ArrayList<Integer []> array;
	int location;
	Taxi taxi;


	public Person(String in, Taxi taxi){
		array = new ArrayList<>();
		this.taxi = taxi;
		location = 0;
		List<String> instructions = new ArrayList<String>(Arrays.asList(in.split(" ")));		
		number = instructions.remove(0);
		
		for(int i=0;i<instructions.size();i+=2){
				String a = instructions.get(i).replace("(","");
				a = a.replace(",","");
				
				String b = instructions.get(i+1).replace(")","");
				b = b.replace(",","");
				Integer [] cur = new Integer[2];
				cur[0] = Integer.parseInt(a);
				cur[1] = Integer.parseInt(b);
				array.add(cur);
				
		}	
	}


	void getNext(){
		array.remove(0);
	}

	void work(int i){
		for(int x=0;x<i;x++){
			try{
				Thread.sleep(33);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void run(){
		
		while(this.array.size()>0){
			if(location != array.get(0)[0]){
				if(location != taxi.location){
					taxi.giveDestination(location);
					System.out.println(taxi.getTime()+" branch "+location+": person "+number+" hails");
					try{
						taxi.getSem(location).acquire();
						taxi.getSem(location).release();
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				
				System.out.println(taxi.getTime()+" person "+number+" boards taxi");
				taxi.giveDestination(array.get(0)[0]);
				System.out.println(taxi.getTime()+" branch "+location+": person "+number+" request "+array.get(0)[0] );
				try{
					int x = array.get(0)[0];
					
					taxi.getSem(x).acquire();
					location = array.get(0)[0];
					taxi.getSem(array.get(0)[0]).release();
					//System.out.println("person "+number+" arrives "+  location);
				}
				catch(Exception e){
						e.printStackTrace();
				}

			}

			this.work(array.get(0)[1]);
			this.getNext();

		}
		taxi.removePerson();
	}

}
