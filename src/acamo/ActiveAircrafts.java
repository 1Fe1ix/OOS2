package acamo;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import messer.BasicAircraft;
import observer.Observable;
import observer.Observer;


public class ActiveAircrafts implements ActiveAircraftsInterface , Observer<BasicAircraft>
{
	//Specify type of HashMap<Key , Value> activeAircrafts
		private HashMap<String ,BasicAircraft > activeAircrafts;
		
		public ActiveAircrafts()
		{
			//creating the hashmap
			activeAircrafts = new HashMap<>();
		}
		
		@Override
		public synchronized void store(String icao, BasicAircraft ac)
		{
			//store values in table
			activeAircrafts.put(icao,ac);
		}

		@Override
		public synchronized void clear()
		{
			//delet whole table
			activeAircrafts.clear();
		}

		@Override
		public synchronized BasicAircraft retrieve(String icao) 
		{
			//get Aircraft from table 
			BasicAircraft ac = activeAircrafts.get(icao);
			//if nothing was found return NULL?????? how ???!!!!!!!!!!!!!!!!!!!!!!!!! still to do
			return ac; 
		}

		@Override
		public synchronized ArrayList<BasicAircraft> values()
		{
			//give value of the amount of aircrafts in the hashmap
			ArrayList<BasicAircraft> aircrafts = new ArrayList<>(activeAircrafts.values());
	        return aircrafts;
		}
		
		public String toString() 
		{
			return activeAircrafts.toString();
		}
		
		@Override
		public void update(Observable<BasicAircraft> observable,BasicAircraft newValue) 
		{
			//puts a basicAircraft in Hashmap like store 
			String icao = newValue.getIcao();
			activeAircrafts.put(icao,newValue);
			
		}

}





