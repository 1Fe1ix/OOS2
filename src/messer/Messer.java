package messer;
import senser.AircraftDisplay;
import java.util.concurrent.ArrayBlockingQueue;
import observer.Observable;
import observer.Observer;
import observer.SimpleObservable;
import senser.AircraftFactory;
import senser.AircraftSentense;




public class Messer extends SimpleObservable<BasicAircraft>  implements Observer <AircraftSentense>, Runnable 
{
    //Declares a Queue which stores data from type AircraftSentense
	private ArrayBlockingQueue<AircraftSentense> queue;
	
	public Messer()
	{
		//Creates a new object from ArrayBlockingQueue with size 1
		queue = new ArrayBlockingQueue<AircraftSentense>(1);
	}

	//Update the observer messer with data 
	public void update(Observable<AircraftSentense> observable, AircraftSentense aircraftSentence) 
	{
		//Save a sentence in a thread queue
        try 
        {
        	//put sentence from senser in queue
            queue.put(aircraftSentence);
        } 
        catch (InterruptedException e) 
        {
        	//Prints error message
            e.printStackTrace();
        }
	}
	
	
	public AircraftSentense getsentencefromqueue() throws InterruptedException 
	{
		//gives back the first input
		return queue.take();
	}
	
	
	
	
	public void run()
	{
		
		
		AircraftFactory factory;
		AircraftSentense Sentence;
		AircraftDisplay display = new AircraftDisplay();

		while (true)
		{   
			
		try 
		{
	    //gets string from queue 
		Sentence = getsentencefromqueue();
		//creates new AircraftFactory object which can create BasicAircrafts
		factory = new AircraftFactory(Sentence);
		//Always create an instance before trying to invoke a method thats not static
		//Creating BAsicAricrafts by using the methode getAircraftFactory() to get values for creating a BAsicAircraft
		BasicAircraft BasicAircraft = factory.getAircraftFactory(); 
		//display BasicAircrafts
		display.methodeDisplay(BasicAircraft);    
		//--------------------Lab4-------------------------------------
					//informs ActiveAircraft
					// each sentence will invoke setChanged Sentence goes through whole list 
					setChanged();
					//gives each sentence to the observer 
					notifyObservers(BasicAircraft);
	    } 
		catch (InterruptedException e) 
		{
	     //Prints error message
		e.printStackTrace();
		}
		}
		
	}
	
	

	
	
			
}
