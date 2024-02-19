package acamo;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

import javafx.application.Application;
//import application.UserAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import jsonstream.PlaneDataServer;
import messer.BasicAircraft;
import observer.Observable;
import observer.Observer;
import senser.Senser;
import messer.Coordinate;
import messer.Messer;
import acamo.ActiveAircrafts;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.event.*;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import com.sun.security.auth.module.JndiLoginModule;
import de.saring.leafletmap.*;
import de.saring.leafletmap.LatLong;
import de.saring.leafletmap.LeafletMapView;
import de.saring.leafletmap.MapConfig;
import de.saring.leafletmap.Marker;
import com.sun.security.auth.module.JndiLoginModule;
import de.saring.leafletmap.*;
import de.saring.leafletmap.LatLong;
import de.saring.leafletmap.LeafletMapView;
import de.saring.leafletmap.MapConfig;
import de.saring.leafletmap.Marker;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import jsonstream.PlaneDataServer;
import org.w3c.dom.Text;
import java.awt.*;
import java.awt.im.InputContext;
import java.sql.Time;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;






public class Acamo extends Application implements Observer<BasicAircraft>, Runnable
{
	private static double latitude = 48.7433425;
	private static double longitude = 9.3201122;
	private static boolean haveConnection = false;
	private ObservableList<BasicAircraft> Aircraftlist = FXCollections.observableArrayList();
	private ActiveAircrafts activeAircrafts;
	private BasicAircraft selectedAircraft;
	private BasicAircraft firstItem;
	private Label outputAirplane1; 
	private TableView.TableViewSelectionModel<BasicAircraft> selectedItem;
	private VBox HorizontalboxMap;
	private HBox root1;
	private VBox verticalBoxTable;
	private Label outputAirplane;
	private VBox verticalBoxOutput;
	private VBox verticalBoxOutput1;
	private Scene scene;
	private TableView<BasicAircraft> tableView;
	 
	@Override
	public synchronized void update(Observable<BasicAircraft> observable, BasicAircraft ac) 
	{
		if(activeAircrafts != null) 
		{
			//
    		//activeAircrafts.update(observable, ac);
    		//clear old aircrafts 
    		Aircraftlist.clear();
    		//adding list of aircrafts
    		Aircraftlist.addAll(activeAircrafts.values());
    		
		}
	}

	public synchronized TableView<BasicAircraft> table()
	{
		
		 	//creating a tableview like tables?
	      	tableView = new TableView<BasicAircraft>();
	     
			//creating columns for the table TableColumn<Objecttype, Cell Datatyoe> name of row is icao
			TableColumn<BasicAircraft,String> icao = new TableColumn<>("icao");
			//setCellValueFactory = takes the PropertyValueFactory-Object as Param (PropertyValueFactory has extraction logic , setCellValueFactory stores data)
			//PropertyValueFactory = gets the wanted values from basicAircraft and interprets them as string --> class  methode geticao
			icao.setCellValueFactory(new PropertyValueFactory<BasicAircraft,String>("icao"));
			
			TableColumn<BasicAircraft,String> operator = new TableColumn<>("operator");
			operator.setCellValueFactory(new PropertyValueFactory<BasicAircraft,String>("operator"));
			
			TableColumn<BasicAircraft,Date> posTime = new TableColumn<>("posTime");
			posTime.setCellValueFactory(new PropertyValueFactory<BasicAircraft,Date>("PosTime"));
			
			TableColumn<BasicAircraft,Coordinate> coordinate = new TableColumn<>("coordinate");
			coordinate.setCellValueFactory(new PropertyValueFactory<BasicAircraft,Coordinate>("coordinate"));
			
			TableColumn<BasicAircraft,Double> speed = new TableColumn<>("speed");
			speed.setCellValueFactory(new PropertyValueFactory<BasicAircraft,Double>("speed"));
			
			TableColumn<BasicAircraft,Double> trak = new TableColumn<>("trak");
			trak.setCellValueFactory(new PropertyValueFactory<BasicAircraft,Double>("trak"));
	     
	      
						// adding the rows to a table 
						tableView.getColumns().add(icao);
						tableView.getColumns().add(operator);
						tableView.getColumns().add(posTime);
						tableView.getColumns().add(coordinate);
						tableView.getColumns().add(speed);
						tableView.getColumns().add(trak);
						
			
			return tableView;
	}
		
	public synchronized void setTable(TableView<BasicAircraft> tableView) 
	{
		
				//table will get its items here, will be filled
				tableView.setItems(Aircraftlist);
				//to check list in terminal 
				System.out.println("Liste für Tabelle"+Aircraftlist);	
	}
	
	@Override
	public synchronized void start(Stage primaryStage) 
	{
		String urlString = "https://opensky-network.org/api/states/all";
		PlaneDataServer server;
		
		if(haveConnection)
			server = new PlaneDataServer(urlString, latitude, longitude, 150);
		else
			server = new PlaneDataServer(latitude, longitude, 100);
		
		new Thread(server).start();
		
		Senser senser = new Senser(server);
		
		new Thread(senser).start();
		
		Messer messer = new Messer();
		senser.addObserver(messer);
		new Thread(messer).start();
		
	    //adding acamo and active aricraft to observerlist of messer
		activeAircrafts = new ActiveAircrafts();
		messer.addObserver(activeAircrafts);
		//this is for acamo as observer for messer
		messer.addObserver(this);
		
		
	
		try 
		{	
		//creating a table
		TableView<BasicAircraft>tableView = table();	
		
		//HBox case for everything 
		root1 = new HBox();
			
		//put planes in table	
		setTable(tableView);
			
		
		//--------------------EventHandling selected row------------------------------------------------------
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>(){
			
		@Override
		public void handle(MouseEvent event)
		{
			selectedItem = tableView.getSelectionModel();
			System.out.println(tableView.getSelectionModel());
			
			selectedAircraft = selectedItem.getSelectedItem();
			
			if(selectedAircraft != null)
			{			
			outputAirplane1.setText(" Aircraft\n" + selectedAircraft.getIcao()+"\n"+selectedAircraft.getOperator()+"\n"+selectedAircraft.getPosTime()+"\n"+selectedAircraft.getCoordinate()+"\n"+selectedAircraft.getSpeed()+"\n"+selectedAircraft.getTrak());
			}
		}});
		

		
		//----------------------backround map-----------------------------
		
		
		LeafletMapView mapView = new LeafletMapView();
		mapView.setLayoutX(0);
		mapView.setLayoutY(0);
		//mapView.setMaxWidth(640);
		LinkedList<MapLayer> config = new LinkedList<>();
		config.add(MapLayer.OPENSTREETMAP);
		
		
		// Record the load state
		CompletableFuture<Worker.State> loadState;
		loadState= mapView.displayMap( new MapConfig(config, new ZoomControlConfig(), new ScaleControlConfig(),new LatLong(latitude, longitude)));
		
		System.out.println("loadestate: "+loadState);
		
		HorizontalboxMap = new VBox(mapView);
		HorizontalboxMap.setPrefWidth(600);  
		HorizontalboxMap.setPrefHeight(600);
		root1.getChildren().add(HorizontalboxMap);
		
		//-------------------------------------------------------------------------------------------
	
		//table config case 
		verticalBoxTable = new VBox(tableView);
		//somehow adjust size of vertical box
		verticalBoxTable.setPrefWidth(600);  
		verticalBoxTable.setPrefHeight(1000);
		root1.getChildren().add(verticalBoxTable);
		
		//Textoutput config case
		outputAirplane = new Label(" Selected\n icao\n operator\n posTime\n coordinate\n speed\n trak\n"); 
		
		
		verticalBoxOutput = new VBox(outputAirplane);
		//somehow adjust size of vertical box
		verticalBoxOutput.setPrefWidth(100);  
		verticalBoxOutput.setPrefHeight(1000);
		root1.getChildren().add(verticalBoxOutput);
		
		//Lable für ausgabe
		outputAirplane1 = new Label(" Aircraft \n");
		
		
		
		
		verticalBoxOutput1 = new VBox(outputAirplane1);
		//somehow adjust size of vertical box
		verticalBoxOutput1.setPrefHeight(1000);
		root1.getChildren().add(verticalBoxOutput1);
		
		
		//vordere zahl breite hinter höhe
		scene = new Scene(root1,1200,800);
		primaryStage.setTitle("Active Aircrafts");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		} 
		catch(Exception e) 
		{
		e.printStackTrace();
		}	
	}
	
	 @Override
	 public void run()
	 {
	    launch();
	 }
	 
}



