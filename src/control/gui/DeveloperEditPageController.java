/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.gui;

import control.model.Developer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Luc Hayward
 */
public class DeveloperEditPageController implements Initializable
{

    @FXML
    private TextField developerIDField;
    @FXML
    private TextField developerNameField;
    @FXML
    private TextField yearFoundedField;
    @FXML
    private TextField founderField;
    @FXML
    private TextField HQField;
    @FXML
    private TextField websiteField;

    private Stage developerEditDialogStage;
    private Developer dev;
    private boolean okClicked = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	// TODO
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage The stage to be set
     */
    public void setDeveloperEditDialogStage(Stage dialogStage)
    {
	developerEditDialogStage = dialogStage;
    }

    /**
     * sets the developer to be edited in the dialog.
     *
     * @param dev The developer to be set
     */
    public void setDeveloper(Developer dev)
    {
	this.dev = dev;

	developerIDField.setText(String.valueOf(dev.getDevID()));
	developerNameField.setText(dev.getName());
	yearFoundedField.setText(String.valueOf(dev.getFoundedYear()));
	founderField.setText(dev.getFounder());
	HQField.setText(dev.gethqCountry());
	websiteField.setText(dev.getWebsite());
    }

    /**
     * Returns true if the user click OK, false otherwise.
     *
     * @return Returns true if ok was clicked.
     */
    public boolean isOkClicked()
    {
	return okClicked;
    }

    /**
     * Called when the user click btnOK.
     */
    @FXML
    private void handlebtnConfirm()
    {
	if (isInputValid())
	{
	    dev.setDevID(Integer.parseInt(developerIDField.getText()));
	    dev.setName(developerNameField.getText());
	    dev.setFoundedYear(Integer.parseInt(yearFoundedField.getText()));
	    dev.setFounder(founderField.getText());
	    dev.setHqCountry(HQField.getText());
	    dev.setWebsite(websiteField.getText());

	    okClicked = true;
	    developerEditDialogStage.close();
	}
    }

    @FXML
    private void handleBtnCancel()
    {
	developerEditDialogStage.close();
    }

    private boolean isInputValid()
    {
	String errorMessage = "";

	if (developerIDField.getText() == null || developerIDField.getText().length() == 0)
	{
	    errorMessage += " No valid Developer ID. Developer ID cannot be empty.\n";
	}

	if (developerNameField.getText() == null || developerNameField.getText().length() == 0)
	{
	    errorMessage += " No valid Developer Name. Developer name cannot be empty.\n";
	}

	if (errorMessage.length() == 0)
	{
	    return true;
	}
	else
	{
	    //Show the error message.
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.initOwner(developerEditDialogStage);
	    alert.setTitle("Invalid Fields");
	    alert.setHeaderText("Please correct the incorrect fields");
	    alert.setContentText(errorMessage);

	    alert.showAndWait();

	    return false;
	}
    }

}
