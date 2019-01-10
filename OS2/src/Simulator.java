
import java.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.lang.Thread.*;


public class Simulator {
	
	
    public static void main(String [] args){
	String filename = args[0];
	int personNo;
	int branches;
	ArrayList<Person> people = new ArrayList<>();
	Taxi taxi;
	
		try{
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			personNo = Integer.parseInt(line);
			line = reader.readLine();
			branches = Integer.parseInt(line);
			line = reader.readLine();

			taxi = new Taxi(branches,personNo);
			
			while(line !=null){
				people.add(new Person(line,taxi));
				line = reader.readLine();
			}
			reader.close();
			for(int i=0;i<people.size();i++){
				people.get(i).start();
			}
			taxi.start();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
