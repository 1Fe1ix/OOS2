package senser;
import java.util.Date;
import messer.BasicAircraft;
import messer.Coordinate;


public class AircraftFactory 
{
	//Attribute
	private BasicAircraft BasicAircraft;
	
	//constructor
	public AircraftFactory(AircraftSentense aircraft)
	{	
		
			//delets all " from String, splits at every komma, gives some strings for an array 
			//is overwritten every cycle 
			String [] values = aircraft.getAircraftSentense().replaceAll("\"", "").split(",");
			
		    //creates a new object from type Coordinate and gives value from String 
			Coordinate momentaneous_cordinate = new Coordinate(Double.parseDouble(values[5]),Double.parseDouble(values[6]));
			//Creates new object from type date value get parse in int before
			Date date = new Date(Integer.parseInt(values[3]));
			
		    //Puts data for planes in constructor of basicaircraft, references to each aircraft are stored  
			//BasicAircraftList.add(new BasicAircraft(values[0],values[1],date,momentaneous_cordinate, Double.parseDouble(values[9]),Double.parseDouble(values[10])));
			BasicAircraft = new BasicAircraft(values[0],values[1],date,momentaneous_cordinate, Double.parseDouble(values[9]),Double.parseDouble(values[10]));	
	}
	
	//method		
	public BasicAircraft getAircraftFactory()
	{	
		return BasicAircraft;
	}
					
}	
	

