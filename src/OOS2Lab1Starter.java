import jsonstream.*;
import senser.Senser;
import messer.Messer;



public class OOS2Lab1Starter
{
    private static double latitude = 48.7433;
    private static double longitude = 9.3201;
    private static boolean haveConnection = true;

	public static void main(String[] args)
	{
		String urlString = "https://opensky-network.org/api/states/all";
		PlaneDataServer server;
		
		if(haveConnection)
			server = new PlaneDataServer(urlString, latitude, longitude, 100);
		else
			server = new PlaneDataServer(latitude, longitude, 150);

		Senser senser = new Senser(server);
		new Thread(server).start();
		new Thread(senser).start();
		
		//----------------------------
		Messer Messer = new Messer();
		//Adding Messer in the list of observers in from 
		senser.addObserver(Messer);
		
		//creating objects from type acamo and activeaircraft
		//Acamo acamo = new Acamo();
		//ActiveAircrafts activeaircreafts= new ActiveAircrafts();
		
		//Adding acamo and activeaircrafts to messers observerlist
		//Messer.addObserver(acamo);
		//Messer.addObserver(activeaircreafts);
		
		//Started thread without this comand nothing will run
		new Thread(Messer).start();	
		
		
		
		
		
	}
}


