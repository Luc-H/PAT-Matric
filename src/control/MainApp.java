/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.gui.DeveloperEditPageController;
import control.gui.GameEditPageController;
import control.gui.MainPageController;
import control.model.Developer;
import control.model.Game;
import control.sqlLogic.CreateTables;
import control.sqlLogic.DBConnect;
import control.sqlLogic.HandleResultSet;
import control.sqlLogic.SQLController;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Luc Hayward
 */
public class MainApp extends Application 
{
    private Stage mainStage;
    private BorderPane rootLayout;

    Properties userPrefs = new Properties();

    public static List developersList = new ArrayList();

    /**
     * Create an observable list for both game and dev data. Supply public
     * methods for each to allow other classes to access them
     */
    private ObservableList<Game> gameData = FXCollections.observableArrayList();
    private ObservableList<Developer> developerData = FXCollections.observableArrayList();

    public ObservableList<Game> getGameData()
    {
	return gameData;
    }

    public ObservableList<Developer> getDeveloperData()
    {
	return developerData;
    }

//    public MainApp()
//    {
//	System.out.println("Adding dummy game data");
//	gameData.add(new Game(22, "YOYOYO"));
//    }

    @Override
    public void start(Stage primaryStage)
    {
	// Set the initial visual for the user
	mainStage = primaryStage;
	mainStage.setTitle("Luc Hayward Steam Collector");

	// Add a custon icon
	this.mainStage.getIcons().add(new Image("file:resources/icons/main.png"));

	/**
	 * Run the initial methods required to setup the main view of the
	 * program for the user as well as any dependant connections such as the
	 * DB connection so that other methods might have access to it.
	 */
	initRootLayout();

	DBConnect.Connector();

	// Run the initial setup to determine whether this is the first use of the program.
	if (!hasRun())
	{
	    showIntroduction();

	    try
	    {
		System.out.println("Creating tables omfg pls work");
		CreateTables.CreateTblDevs();
		CreateTables.CreateTblGames();
		System.out.println("Tables created.");
	    }
	    catch (SQLException ex)
	    {
		System.err.println("Error: control/MainAPP attempting to create"
			+ " tables; " + ex + "You and I are basically f*cked sorry");
	    }

	}

	// initialise a list of developers
	populateDevelopersList();
	populateGameData();
	populateDeveloperData();
	System.out.println("testing");

	showMainPage();

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout()
    {
	try
	{
	    // Load root layout from fxml file.
	    FXMLLoader loader = new FXMLLoader();
	    // Use relative file path
	    loader.setLocation(MainApp.class.getResource("gui/RootLayout.fxml"));
	    rootLayout = (BorderPane) loader.load();

	    // Show the scene containing the root layout.
	    Scene scene = new Scene(rootLayout);

	    mainStage.setScene(scene);
	    mainStage.setResizable(false);
	    mainStage.show();
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
    }

    /**
     * Shows the main view inside the root layout. This is the Main page the
     * user will see as well as the page the program will open to.
     */
    public void showMainPage()
    {
	try
	{
	    // Load the main page
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainApp.class.getResource("gui/MainPage.fxml"));
	    AnchorPane mainPage = (AnchorPane) loader.load();

	    // Set the person overview to the center or the root layout.
	    rootLayout.setCenter(mainPage);

	    // Give the controller access to MainApp
	    MainPageController controller = loader.getController();
	    controller.setMainApp(this);
	}
	catch (IOException ex)
	{
	    System.err.println("Error: PATfx/MainApp/showTestPage: " + ex);
	}
    }

    /**
     * Reads the PAT.ini file specifically to determine whether or not the user
     * has opened the program before.
     *
     * @return Has the user ever opened the program?
     */
    private boolean hasRun()
    {
	try
	{

	    // The following file has the potential to not work in the distibutable.
	    // File currently does NOT work in distributable, will fix for later build.
	    // Should b e as simple as changing the path to a relative one.
	    userPrefs.load(new FileInputStream("resources/PAT.ini"));

	    return userPrefs.getProperty("hasBeenRun", "false").equals("true");
	}
	catch (IOException ex)
	{
	    System.err.println("Error in control/MainApp/hasRun: " + ex);
	    System.err.println("False has been returned to avoid any error");
	    return false;
	}
    }

    /**
     * Creates a new stage and scene in order to display an introductory message
     * in a new window to acquaint the user with the basic program.
     */
    public void showIntroduction()
    {
	try
	{
	    // Load the Introduction page
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainApp.class.getResource("gui/Introduction.fxml"));
	    AnchorPane introduction = (AnchorPane) loader.load();

	    // Create a new stage to hold the introduction so that the Main
	    // Stage can remain behind to be created.
	    Stage introStage = new Stage();
	    introStage.setTitle("Introduction");
	    // TODO: set a unique icon (eg a info icon)
	    introStage.initModality(Modality.WINDOW_MODAL);
	    introStage.initOwner(mainStage);
	    Scene scene = new Scene(introduction);
	    introStage.setScene(scene);

	    introStage.showAndWait();

	    // set the hasBeenRun property in PAT.ini to true
	    // The following file has the potential to not work in the distibutable.
	    userPrefs.setProperty("hasBeenRun", "true");
	    userPrefs.store(new FileOutputStream("resources/PAT.ini"), "Some comment property desciptiony thing:)");

	}
	catch (IOException ex)
	{
	    System.err.println("Error: PATfx/MainApp/showIntroduction: " + ex);
	}
    }

    /**
     * retrieves all the names of developers currently in the database and
     * inserts them into a list which may be searched during the running of the
     * program.
     */
    private void populateDevelopersList()
    {
	String queryDevelopers = "SELECT \"NAME\" FROM APP.TBLDEVELOPERS";
	SQLController developersListPopulator = new SQLController();
	HandleResultSet developerRSHandler = new HandleResultSet();
	ResultSet developersRS = null;

	try
	{
	    developersRS = developersListPopulator.ExecuteStmt(queryDevelopers);
	}
	catch (SQLException ex)
	{
	    System.err.println("SQL Exception thrown in populateDevelopersList,"
		    + " MainAPp: \n" + ex);
	}

	// pass the result set to HandleResultSet.java to properly deal with the data.
	developerRSHandler.developerListRS(developersRS);

	developersList.addAll(developersList);
	System.out.println("Total number of developers currently in DB = "
		+ developersList.size());
    }

    /**
     * Queries the DB to provide all the data for every game available. Returned
     * result set is then used to populate the gameData Observable List with
     * game objects. The list can then be used correctly throughout the program
     * for extremely rapid access to the data.
     */
    private void populateGameData()
    {
	String queryGameTable = "SELECT * FROM TBLGAMES";
	SQLController gameDataSQLController = new SQLController();
	HandleResultSet gameDataRSHandler = new HandleResultSet();
	ResultSet gameDataRS = null;

	try
	{
	    gameDataRS = gameDataSQLController.ExecuteStmt(queryGameTable);
	}
	catch (SQLException ex)
	{
	    System.err.println("SQL Exception thrown in populateGameData,"
		    + " MainAPp: \n" + ex);
	}

	gameData.addAll(gameDataRSHandler.gameDataRS(gameDataRS));
    }

    /**
     * Queries the DB to provide all the data for every developer available.
     * Returned result set is then used to populate the developerData Observable
     * List with developer objects. The list can then be used correctly
     * throughout the program for extremely rapid access to the data.
     */
    private void populateDeveloperData()
    {
	String queryDeveloperTable = "SELECT * FROM TBLDevelopers";
	SQLController developerDataSQLController = new SQLController();
	HandleResultSet developerDataRSHandler = new HandleResultSet();
	ResultSet developerDataRS = null;

	try
	{
	    developerDataRS = developerDataSQLController.ExecuteStmt(queryDeveloperTable);
	}
	catch (SQLException ex)
	{
	    System.err.println("SQL Exception thrown in populateGameData,"
		    + " MainAPp: \n" + ex);
	}

	developerData.addAll(developerDataRSHandler.developerDataRS(developerDataRS));
    }

    /**
     * Opens a dialog to edit details for the specified game. If the user clicks
     * OK, the changes are saved into the provided game object and true is
     * returned. These changes are then saved to the database by means of an
     * update query.
     *
     * @param game The game to be edited.
     * @return True if the user clicked OK, false otherwise.
     */
    public boolean showGameEditDialog(Game game)
    {
	try
	{
	    //Load the FXML file and create a new stage for the popup dialog.
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainApp.class.getResource("gui/GameEditPage.fxml"));
	    AnchorPane page = (AnchorPane) loader.load();

	    // Create the dialog Stage.
	    Stage dialogStage = new Stage();
	    dialogStage.setTitle("Edit Game");
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    dialogStage.initOwner(mainStage);
	    Scene scene = new Scene(page);
	    dialogStage.setScene(scene);

	    // Set the Game into the controller.
	    GameEditPageController controller = loader.getController();
	    controller.setGameEditDialogStage(dialogStage);
	    controller.setGame(game);

	    // Show the dialog and wait until the user closes it.
	    dialogStage.showAndWait();

	    return controller.isOkClicked();

	}
	catch (IOException ex)
	{
	    ex.printStackTrace();
	    return false;
	}
    }

    /**
     * Opens a dialog to edit details for the specified Developer. If the user
     * clicks OK, the changes are saved into the provided developer object and
     * true is returned. These changes are then saved to the database by means
     * of an update query.
     *
     * @param dev the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showDeveloperEditDialog(Developer dev)
    {
	try
	{
	    //Load the FXML file and create a new stage for the popup dialog.
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainApp.class.getResource("gui/DeveloperEditPage.fxml"));
	    AnchorPane page = (AnchorPane) loader.load();

	    // Create the dialog Stage.
	    Stage dialogStage = new Stage();
	    dialogStage.setTitle("Edit Developer");
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    dialogStage.initOwner(mainStage);
	    Scene scene = new Scene(page);
	    dialogStage.setScene(scene);

	    // Set the Game into the controller.
	    DeveloperEditPageController controller = loader.getController();
	    controller.setDeveloperEditDialogStage(dialogStage);
	    controller.setDeveloper(dev);

	    // Show the dialog and wait until the user closes it.
	    dialogStage.showAndWait();

	    return controller.isOkClicked();
	}
	catch (IOException ex)
	{
	    ex.printStackTrace();
	    return false;
	}
    }


    public Stage getMainStage()
    {
	return mainStage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
