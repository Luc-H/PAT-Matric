/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.gui;

import static control.MainApp.developersList;
import control.model.Game;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luc Hayward
 */
public class GameEditPageController implements Initializable
{

    @FXML
    private TextField steamIDField;
    @FXML
    private TextArea shortDescriptionField;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea longDescriptionField;
    @FXML
    private TextField metacriticField;
    @FXML
    private TextField releaseDateField;
    @FXML
    private RadioButton multiplayerToggle;
    @FXML
    private RadioButton controllerSupportToggle;
    @FXML
    private TextField genre1Field;
    @FXML
    private TextField genre2Field;
    @FXML
    private TextField genre3Field;
    @FXML
    private RadioButton ownedToggle;
    @FXML
    private TextField developerField;
    @FXML
    private ComboBox<?> userReviewsDropDown;
    @FXML
    private TextField userReviewsField;

    private ObservableList<String> userReviewsDropDownOptions = FXCollections.observableArrayList("Overwhelmingly Positive", "Very Positive", "Positive", "Mostly Positive", "Mixed", "Mostly Negative", "Negative", "Very Negative", "Overwhelmingly Negative");
    private Stage gameEditDialogStage;
    private Game game;
    private boolean okClicked = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }

    /**
     * Sets the stage of this dialog
     *
     * @param dialogStage The stage to be set
     */
    public void setGameEditDialogStage(Stage dialogStage)
    {
	gameEditDialogStage = dialogStage;
    }

    /**
     * Sets the game to be edited in the dialog.
     *
     * @param game the game to be set
     */
    public void setGame(Game game)
    {
	this.game = game;

	steamIDField.setText(String.valueOf(game.getSteamID()));
	shortDescriptionField.setText(game.getShortDescription());
	titleField.setText(game.getTitle());
	longDescriptionField.setText(game.getLongDescription());
	metacriticField.setText(String.valueOf(game.getMetaCritic()));
	releaseDateField.setText(game.getReleaseDate());
	multiplayerToggle.setSelected(game.isMultiplayerSupport());
	controllerSupportToggle.setSelected(game.isControllerSupport());
	genre1Field.setText(game.getGenre1());
	String g2f = game.getGenre2();
	if (g2f.equals("null"))
	{
	    genre2Field.setText("");
	}
	else
	{
	    genre2Field.setText(g2f);
	}
	String g3f = game.getGenre3();
	if (g3f.equals("null"))
	{
	    genre3Field.setText("");
	}
	else
	{
	    genre3Field.setText(g3f);
	}
	ownedToggle.setSelected(game.isIsOwned());
	developerField.setText((String) (developersList.get(game.getDevID())));
	String urf = game.getUserReviews();
	userReviewsField.setText(urf);
    }

    /**
     * Returns true if the user click OK, false otherwise.
     *
     * @return returns if ok was clicked
     */
    public boolean isOkClicked()
    {
	return okClicked;
    }

    /**
     * Called when the user clicks btnOK.
     */
    @FXML
    private void handleBtnConfirm()
    {
	if (isInputValid())
	{
	    game.setSteamID(Integer.parseInt(steamIDField.getText()));
	    game.setShortDescription(shortDescriptionField.getText());
	    game.setTitle(titleField.getText());
	    game.setLongDescription(longDescriptionField.getText());
	    game.setMetaCritic(Integer.parseInt(metacriticField.getText()));
	    game.setReleaseDate(releaseDateField.getText());
	    game.setMultiplayerSupport(multiplayerToggle.isSelected());
	    game.setControllerSupport(controllerSupportToggle.isSelected());
	    game.setGenre1(genre1Field.getText());
	    game.setGenre2(genre2Field.getText());
	    game.setGenre3(genre3Field.getText());
	    game.setIsOwned(ownedToggle.isSelected());
	    game.setDevID(developersList.indexOf(developerField));

	    okClicked = true;
	    gameEditDialogStage.close();
	}
    }

    @FXML
    private void handleBtnCancle()
    {
	gameEditDialogStage.close();
    }

    private boolean isInputValid()
    {
	String errorMessage = "";

	if (titleField.getText() == null || titleField.getText().length() == 0)
	{
	    errorMessage += "No valid Title. Title cannot be empty.\n";
	}

	if (shortDescriptionField.getText() == null || shortDescriptionField.getText().length() == 0)
	{
	    errorMessage += "no valid short description. Short Description cannot be empty.\n";
	}

	if (longDescriptionField.getText() == null || longDescriptionField.getText().length() == 0)
	{
	    errorMessage += "No valid long description. Long description cannot be empty.\n";
	}

	if (userReviewsField.getText() == null || userReviewsField.getText().length() == 0)
	{
	    errorMessage += "No valid user review. User reviews cannot be empty.\n";
	}

	if (!userReviewsDropDownOptions.contains(userReviewsField.getText()))
	{
	    errorMessage += "No valid user review. Review must be one of the following: \"Overwhelmingly Positive\", \"Very Positive\", \"Positive\", \"Mostly Positive\", \"Mixed\", \"Mostly Negative\", \"Negative\", \"Very Negative\", \"Overwhelmingly Negative\"\n";
	}

	if (metacriticField.getText() == null || titleField.getText().length() == 0)
	{
	    errorMessage += "No valid Metacritic score. Metacritic cannot be empty.\n";
	}
	else
	{
	    try
	    {
		int x = Integer.parseInt(metacriticField.getText());
		if (x < 0 || x > 100)
		{
		    errorMessage += "No valid Metacritic score. Metacritic must be a number between 0 and 100.\n";
		}
	    }
	    catch (Exception ex)
	    {
		errorMessage += "No valid Metacritic score. Metacritic must be a number between 0 and 100.\n";
	    }
	}

	if (releaseDateField.getText() == null || releaseDateField.getText().length() == 0)
	{
	    errorMessage += "No valid release date. Release date cannot be empty.\n";
	}

	if (genre1Field.getText() == null || genre1Field.getText().length() == 0)
	{
	    errorMessage += "No valid entry for genre 1. At least genre 1 must be filled in.\n";
	}

	if (developerField.getText() == null || developerField.getText().length() == 0)
	{
	    errorMessage += "No valid developer. Developer cannot be empty.\n";
	}
	else if (!developersList.contains(developerField.getText()))
	{
	    errorMessage += "No valid developer. Developer must exist in the database.\n";
	}

	if (errorMessage.length() == 0)
	{
	    return true;
	}
	else
	{
	    //Show the error message.
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.initOwner(gameEditDialogStage);
	    alert.setTitle("Invalid Fields");
	    alert.setHeaderText("Please correct the incorrect fields");
	    alert.setContentText(errorMessage);

	    alert.showAndWait();

	    return false;
	}

    }


}
