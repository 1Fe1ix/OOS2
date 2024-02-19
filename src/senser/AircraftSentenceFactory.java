package senser;
import java.util.ArrayList;

public class AircraftSentenceFactory 
{
	public ArrayList<AircraftSentense> aircraftList = new ArrayList<>();
	//private ArrayList<Observable> ListObservable = new ArrayList<>();
	 
	 public AircraftSentenceFactory(String AircraftList)
	 {
		 
		 
		 String[] airplane = AircraftList.split("\\],\\[");          //Ein großer String der auf ein array aufgeteilt wird 
		 for (int i = 0; i < airplane.length; i++) {
	            airplane[i] = airplane[i].replaceAll("^\\[|]$", "");
	        }
		 
		 for(String element : airplane) 
		 {
			 aircraftList.add(new AircraftSentense(element));       //Erzeugt ein neues AircraftSentense Objekt für die aircraftList array
			 //NotifyObservable();
		 } 
	 }
	 
	 public ArrayList<AircraftSentense> getAircraftSentenceFactory()
	 {
		 return aircraftList;
	 }	
	 
	 
	 
	 
}
