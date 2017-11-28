/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.gui;

import control.MainApp;
import static control.MainApp.developersList;
import control.model.Developer;
import control.model.Game;
import control.sqlLogic.HandleResultSet;
import control.sqlLogic.SQLController;
import control.web.WebReader;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Controller Class for MainPage
 *
 * @author Luc Hayward
 */
public class MainPageController
{

    // Game Table @FXML definitions
    @FXML
    private TableView<Game> gameTable;
    @FXML
    private TableColumn<Game, Integer> steamIDColumn;
    @FXML
    private TableColumn<Game, String> titleColumn;
    @FXML
    private TableColumn<Game, Integer> metaCriticScoreColumn;

    // Developer Table @FXML definitions
    @FXML
    private TableView<Developer> developerTable;
    @FXML
    private TableColumn<Developer, String> developerNameColumn;
    @FXML
    private TableColumn<Developer, String> foundedColumn;
    @FXML
    private TableColumn<Developer, String> hqCountryColumn;

    // Side Bar @FXML definitions
    @FXML
    private ImageView imgCoverArt;
    @FXML
    private Label titleSideBar;
    @FXML
    private Label shortDescriptionSideBar;
    @FXML
    private Label developerSideBar;
    @FXML
    private Label genreSideBar;
    @FXML
    private Label nameSideBar;
    @FXML
    private Label founderSideBar;
    @FXML
    private VBox developerSideBarPanel;
    @FXML
    private VBox gameSideBarPanel;
    @FXML
    private Button btnAddNew;


    // General Variables
    private boolean gameTableVisible = true;
    @FXML
    private Button btnSwitch;
    private SQLController sqlController;
    private HandleResultSet rsHandler;


    /**
     * Reference to MainApp
     */
    private MainApp mainApp;

    /**
     * The constructor. The constructor is called BEFORE the initiliase()
     * method.
     */
    public MainPageController()
    {
    }

    /**
     * Initialises the controller class. This method is called automatically
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize()
    {
	System.out.println("Testing: show main page");
	// Setup Table with the games from the DB
	// NBNB when using anything other than StringProperty you MUST include .asObject()
	steamIDColumn.setCellValueFactory(cellData -> cellData.getValue().steamIDProperty().asObject());
	titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
	metaCriticScoreColumn.setCellValueFactory(cellData -> cellData.getValue().metaCriticProperty().asObject());

	// Setup table with the developers from the DB
	developerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
	foundedColumn.setCellValueFactory(cellData -> cellData.getValue().founderProperty());
	hqCountryColumn.setCellValueFactory(cellData -> cellData.getValue().hqCountryProperty());

	// Clear the details of any person or developer in either table
	showGameSidePanel(null);
	showDeveloperSidePanel(null);

	//Listen for selection changes and show the Game details when changed.
	gameTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showGameSidePanel(newValue));

	//Listen for the selection changes and show th Developer details when changed.
	developerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDeveloperSidePanel(newValue));
    }


    /**
     * Called by the main app to give a reference back to itself.
     *
     * @param mainApp The MainApp class calling the method.
     */
    public void setMainApp(MainApp mainApp)
    {
	this.mainApp = mainApp;
	gameTable.setItems(mainApp.getGameData());
	developerTable.setItems(mainApp.getDeveloperData());
    }

    /**
     * Fills the text fields in the side panel with details about the selected
     * game. If the specified game is null, all the text fields are cleared.
     *
     * @param game The Game or null
     */
    private void showGameSidePanel(Game game)
    {
	if (game != null)
	{
	    titleSideBar.setText(game.getTitle());
	    shortDescriptionSideBar.setText(game.getShortDescription());
	    developerSideBar.setText("Unknown");
	    developerSideBar.setText((String) (developersList.get(game.getDevID())));
	    genreSideBar.setText(game.getGenre1());
	    if (new File("resources/cover_art/" + Integer.toString(game.getSteamID()) + ".jpg").isFile())
	    {
		imgCoverArt.setImage(new Image("file:resources/cover_art/" + Integer.toString(game.getSteamID()) + ".jpg"));
	    }
	    else
	    {
		imgCoverArt.setImage(new Image("http://cdn.akamai.steamstatic.com/steam/apps/" + Integer.toString(game.getSteamID()) + "/header.jpg"));
	    }
	}
	else
	{
	    titleSideBar.setText("");
	    shortDescriptionSideBar.setText("");
	    developerSideBar.setText("");
	    genreSideBar.setText("");
	}
    }

    /**
     * Fills the text fields in the side panel with details about the selected
     * developer. If the specified developer is null, all the text fields are
     * cleared.
     *
     * @param dev The Developer or null.
     */
    private void showDeveloperSidePanel(Developer dev)
    {
	if (dev != null)
	{
	    nameSideBar.setText(dev.getName());
	    founderSideBar.setText(dev.getFounder());
	}
	else
	{
	    nameSideBar.setText("");
	    founderSideBar.setText("");
	}
    }

    @FXML
    private void handleBtnRepopulateDB()
    {
	System.out.println("Repopulate Database button clicked");
	boolean runWebScraper;
	// call WebScraperProgressController to set up the dialog boxes and options.
	WebScraperProgressController progressController = new WebScraperProgressController();
	runWebScraper = progressController.initiliaseDialog();
	if (runWebScraper)
	{
	    WebReader webReaderInstance = new WebReader();
	    webReaderInstance.SteamScraper();
	}
	else
	{
	    System.err.println("User Chose not to run the program or clicked close");
	}

	SQLController deleteTableData = new SQLController();
	try
	{
	    deleteTableData.ExecuteStmt("DELETE FROM TblGames");
	    deleteTableData.ExecuteStmt("DELETE FROM TblDevelopers");
	}
	catch (SQLException ex)
	{
	    System.err.println("Error in handleRepopulateDB, MainPageController,"
		    + "gui,control whilst attempting to clear the database");
	}


	System.out.println(developersList.size());
    }

    @FXML
    private void handleBtnSwitch()
    {
	// Switch from game table to dev table
	if (gameTableVisible)
	{
	    gameTableVisible = false;
	    gameTable.setVisible(gameTableVisible);
	    developerTable.setVisible(!gameTableVisible);
	    btnSwitch.setText("Games");
	    gameSideBarPanel.setVisible(gameTableVisible);
	    developerSideBarPanel.setVisible(!gameTableVisible);
	    btnAddNew.setText("Add New Dev");
	    imgCoverArt.setVisible(!gameTableVisible);
	}
	else
	{
	    gameTableVisible = true;
	    gameTable.setVisible(gameTableVisible);
	    developerTable.setVisible(!gameTableVisible);
	    btnSwitch.setText("Developers");
	    gameSideBarPanel.setVisible(gameTableVisible);
	    developerSideBarPanel.setVisible(!gameTableVisible);
	    btnAddNew.setText("Add New Game");
	    imgCoverArt.setVisible(!gameTableVisible);
	}
    }

    @FXML
    private void handleBtnYourGames()
    {

    }

    @FXML
    private void handleBtnMoreInfo()
    {

    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit the
     * details for a new Game/Developer.
     */
    @FXML
    private void handleBtnAddNew()
    {
	// If in Game table view.
	if (gameTableVisible)
	{
	    Game tempGame = new Game();
	    boolean okClicked = mainApp.showGameEditDialog(tempGame);
	    if (okClicked)
	    {
		mainApp.getGameData().add(tempGame);

		try
		{
		    String ms, cs, io;

		    if (tempGame.isMultiplayerSupport())
		    {
			ms = "1";
		    }
		    else
		    {
			ms = "0";
		    }
		    if (tempGame.isControllerSupport())
		    {
			cs = "1";
		    }
		    else
		    {
			cs = "0";
		    }
		    if (tempGame.isIsOwned())
		    {
			io = "1";
		    }
		    else
		    {
			io = "0";
		    }
		    // Add the game to the database
		    sqlController.ExecuteStmt("INSERT INTO TblGames"
			    + " VALUES (" + String.valueOf(tempGame.getSteamID()) + ", '" + tempGame.getTitle() + "', '"
			    + tempGame.getShortDescription() + "', '" + tempGame.getUserReviews() + "', " + tempGame.getMetaCritic() + ", '"
			    + tempGame.getReleaseDate() + "', " + ms + ", "
			    + cs + ", '" + tempGame.getGenre1() + "', '" + tempGame.getGenre2()
			    + "', '" + tempGame.getGenre3() + "', '" + tempGame.getLongDescription() + "', " + io
			    + ", " + String.valueOf(developersList.indexOf(tempGame.getDevID())) + ") ");
		}
		catch (SQLException ex)
		{
		    Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
		}

	    }
	}

	// If in developer table view.
	else
	{
	    Developer tempDev = new Developer();
	    boolean okClicked = mainApp.showDeveloperEditDialog(tempDev);
	    if (okClicked)
	    {
		mainApp.getDeveloperData().add(tempDev);

		try
		{
		    sqlController.ExecuteStmt("INSERT INTO TBLDEVELOPERS VALUES("
			    + String.valueOf(tempDev.getDevID()) + ", '" + tempDev.getName()
			    + "', " + String.valueOf(tempDev.getFoundedYear())
			    + ", '" + tempDev.getFounder() + "', '" + tempDev.gethqCountry()
			    + "', '" + tempDev.getWebsite() + "')");
		}
		catch (SQLException ex)
		{
		    Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	}
    }

    /**
     * Called when the user click the edit button. Opens a dialog to edit
     * details for the selected Game or Developer.
     */
    @FXML
    private void handleBtnEdit()
    {
	// If in game table view.
	if (gameTableVisible)
	{
	    Game selectedGame = gameTable.getSelectionModel().getSelectedItem();
	    if (selectedGame != null)
	    {
		boolean okClicked = mainApp.showGameEditDialog(selectedGame);
		if (okClicked)
		{
		    showGameSidePanel(selectedGame);

		    try
		    {
			String ms, cs, io;

			if (selectedGame.isMultiplayerSupport())
			{
			    ms = "1";
			}
			else
			{
			    ms = "0";
			}
			if (selectedGame.isControllerSupport())
			{
			    cs = "1";
			}
			else
			{
			    cs = "0";
			}
			if (selectedGame.isIsOwned())
			{
			    io = "1";
			}
			else
			{
			    io = "0";
			}
			//Update game table with new data
			sqlController.ExecuteStmt("UPDATE TBLGAMES"
				+ "SET SteamID=" + String.valueOf(selectedGame.getSteamID())
				+ ", Title='" + selectedGame.getTitle()
				+ "', ShortDescription='" + selectedGame.getShortDescription()
				+ "', UserReviews='" + selectedGame.getUserReviews()
				+ "', MetaCritic=" + String.valueOf(selectedGame.getMetaCritic())
				+ ", ReleaseDate='" + selectedGame.getReleaseDate()
				+ "', Multiplayer=" + ms
				+ ", ControllerSupport=" + cs
				+ ", Genre1='" + selectedGame.getGenre1()
				+ "', Genre2='" + selectedGame.getGenre2()
				+ "', Genre3='" + selectedGame.getGenre3()
				+ "', LongDescription='" + selectedGame.getLongDescription()
				+ "', IsOwned=" + io
				+ ", DevID=" + String.valueOf(developersList.indexOf(selectedGame.getDevID())) + ")"
				+ "WHERE SteamID=" + String.valueOf(selectedGame.getSteamID()));
		    }
		    catch (SQLException ex)
		    {
			Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
		    }
		}
	    }
	    else
	    {
		//Nothing selected
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getMainStage());
		alert.setTitle("No Selection");
		alert.setHeaderText("No Game Selected");
		alert.setContentText("Please select a game in the table.");

		alert.showAndWait();
	    }
	}
	// If in developer table view.
	else
	{
	    Developer selectedDev = developerTable.getSelectionModel().getSelectedItem();
	    if (selectedDev != null)
	    {
		boolean okClicked = mainApp.showDeveloperEditDialog(selectedDev);
		if (okClicked)
		{
		    showDeveloperSidePanel(selectedDev);

		    //Update developer table with new data
		    try
		    {
			sqlController.ExecuteStmt("UPDATE TBLDEVELOPERS"
				+ "SET DevID=" + String.valueOf(selectedDev.getDevID())
				+ ", Name='" + selectedDev.getName()
				+ "', FoundedYear=" + String.valueOf(selectedDev.getFoundedYear())
				+ ", Founder='" + selectedDev.getFounder()
				+ "', HQCountry='" + selectedDev.gethqCountry()
				+ "', Website='" + selectedDev.getWebsite() + "')"
				+ "WHERE DevID=" + selectedDev.getDevID());
		    }
		    catch (SQLException ex)
		    {
			Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
		    }
		}
	    }
	    else
	    {
		//Nothing selected
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getMainStage());
		alert.setTitle("No Selection");
		alert.setHeaderText("No Developer Selected");
		alert.setContentText("Please select a developer in the table.");

		alert.showAndWait();
	    }
	}
    }

    /**
     * Called when the user clicks the delete button.
     *
     * @throws SQLException
     */
    @FXML
    private void handleBtnDelete() throws SQLException
    {
	SQLController deleteBtn = new SQLController();
	if (gameTableVisible)
	{
	    int selectedIndex = gameTable.getSelectionModel().getSelectedItem().getSteamID();
	    if (selectedIndex >= 0)
	    {
		gameTable.getItems().remove(selectedIndex);
		deleteBtn.ExecuteStmt("DELETE * FROM TBLGAMES WHERE STEAMID = " + selectedIndex);
	    }
	    else
	    {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getMainStage());
		alert.setTitle("No selection");
		alert.setHeaderText("No Game Selected");
		alert.setContentText("Please select a game in the table");

		alert.showAndWait();
	    }
	}
	else
	{
	    int selectedIndex = developerTable.getSelectionModel().getSelectedItem().getDevID();
	    if (selectedIndex >= 0)
	    {
		developerTable.getItems().remove(selectedIndex);
		deleteBtn.ExecuteStmt("DELETE * FROM TBLDEVELOPERS WHERE DEVID = " + selectedIndex);
	    }
	    else
	    {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getMainStage());
		alert.setTitle("No selection");
		alert.setHeaderText("No Developer Selected");
		alert.setContentText("Please select a developer in the table");

		alert.showAndWait();
	    }
	}
    }

}
