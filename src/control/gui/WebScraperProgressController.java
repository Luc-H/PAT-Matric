/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.gui;

import control.MainApp;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Controller Class for WebScraperProgress
 *
 * @author Luc Hayward
 */
public class WebScraperProgressController
{
    /**
     * Reference to MainApp
     */
    private MainApp mainApp;

    public boolean initiliaseDialog()
    {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("Confirm Web Scraper");
	alert.setHeaderText("\tCAUTION");
//	alert.setGraphic(new ImageView(new Image("file:resources/icons/alert.png")));
	alert.setContentText("\u2022The following process is highly resource intensive."
		+ " \n\u2022The speed at which this will take place is ENTIRELY "
		+ "dependant on the speed of your internet. \n\u2022In order for an "
		+ "adequate number of entries to be collected it is HIGHLY"
		+ " recommended that you run this overnight ONLY.\n\u2022The program "
		+ "will appear to freeze and will need to be restarted in the"
		+ " morning.\n\u2022Are you ok with this?");


	Optional<ButtonType> result = alert.showAndWait();
	return result.get() == ButtonType.OK;
    }

    private int confirmWebScraper()
    {
	int amountToSearch = 0;

	return amountToSearch;
    }

}
