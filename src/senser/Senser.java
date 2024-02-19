package senser;
import observer.SimpleObservable;
import java.util.ArrayList;
import jsonstream.*;



//heritage from SimpleObserver, cause SimpleObserver is generic, generic means that this class is a container
//for several data types so if the generic class should be inherited to another, it must be told which data type 
//should be contained in this case --> <AircraftSentense> = Data type
public class Senser extends SimpleObservable<AircraftSentense>  implements Runnable  
{
	PlaneDataServer server;

	public Senser(PlaneDataServer server)
	{
		this.server = server;
	}

	private String getSentence()
	{
		String list = server.getPlaneListAsString();
		return list;
	}
	
	public void run()
	{
		String aircraftList;
		//Array for Airplanes
		ArrayList<AircraftSentense> aircraftsentencelist = new ArrayList<>();
		AircraftDisplay display = new AircraftDisplay();
		
		
		while (true)
		{   
			//________________________________Lab1_________________________________
			//Initialisierung aircraftList
			aircraftList = getSentence();
			//Creates object which will contain a list of Aircraftsentense objekt
			AircraftSentenceFactory factory = new AircraftSentenceFactory(aircraftList);	
			
			//aircraftsentencelist contains all created and manipulated strings from AircraftSentenceFactory
			aircraftsentencelist = factory.getAircraftSentenceFactory();
			
			//Whole printing area
			System.out.println("Current Aircrafts in range" + aircraftsentencelist.size());
			System.out.println("Index");
			for (AircraftSentense aircraft : aircraftsentencelist) 
			{
			//prints AircraftSentences
				display.methodeDisplay(aircraft);
			}
			
			
			//________________________________Lab2_________________________________
			
			// gives info to observer(messer) there where changes 
			for(AircraftSentense Sentence: aircraftsentencelist) 
			{
			// each sentence will invoke setChanged Sentence goes through whole list 
			setChanged();
			//gives each sentence to the observer 
			notifyObservers(Sentence);
			}
		}		
	}
}





