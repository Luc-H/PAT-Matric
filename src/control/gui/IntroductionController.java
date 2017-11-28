/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Luc Hayward
 */
public class IntroductionController implements Initializable
{

    @FXML
    ImageView mainPage;
    @FXML
    ImageView editGame;
    @FXML
    ImageView editDev;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
	// TODO
System.out.println("This thing initialised");
	mainPage.setImage(new Image("file:resources/MainView.jpg"));

	editGame.setImage(new Image("file:resources/EditGame.jpg"));

	editDev.setImage(new Image("file:resources/EditDeveloper.jpg"));
    }

}
