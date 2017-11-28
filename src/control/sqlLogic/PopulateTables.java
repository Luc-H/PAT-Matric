/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.sqlLogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Luc Hayward
 */
public class PopulateTables
{

    /**
     * inserts a new game into the DB. Contains all necessary checks.
     *
     * @param data Contents of the passed data: String steamID;//0 String
     * imageURL;//1 String shortDescription;//2 String userReviews;//3 String
     * releaseDate;//4 String multiplayerSupp;//5 String controllerSupp;//6
     * String genre1;//7 String genre2;//8 String genre3;//9 String devName;//10
     * String title;//11 String metaCritic;//12 String longDescription;//13
     *
     * @param developersList List of developer names where index = devID. used
     * for checking whether a dev exists and what their ID will be.
     *
     * @return Returns the list of developers so that it can be given to the
     * original calling method. This prevents a massive bottleneck from
     * occurring were the program forced to recreate the list of developers from
     * scratch     * every time the method was called.
     */
    public List insertNewGame(String[] data, List developersList)
    {
	String insertQuery;
	SQLController gameInserter = new SQLController();
	ResultSet weNeedAResultSet;

	//Field values
	int steamID = Integer.parseInt(data[0]);
	String title = data[11];
	String shortDescription = data[2];
	String userReviews = data[3];
	int metaCritic = Integer.parseInt(data[12]);
	String releaseDate = data[4];
	int multiplayerSupport = 0;
	if (data[5].equals("True"))
	{
	    multiplayerSupport = 1;
	}
	int controllerSupport = 0;
	if (data[6].equals("True"))
	{
	    controllerSupport = 1;
	}
	String genre1 = data[7];
	String genre2 = data[8];
	String genre3 = data[9];
	String longDescription = data[13];
	int isOwned = 0;
	int devID;

	//<editor-fold defaultstate="collapsed" desc="Unused date formatting">
	/*DateFormat format = new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH);
	try
	{
	Date date = format.parse(data[4]);
	}
	catch (ParseException ex)
	{
	System.err.println("Error: control/sqlLogic/populateTables Attempting to parse date" + ex);
	}*/
 /*
	Check whether the current developer has ever been found. If not insert
	the dev into the table
	 */
//</editor-fold>

	// Set devID from the list of developers.
	devID = developersList.indexOf(data[10]);
	// IF devID is -1 then it did not exist and a new developer should be
	// appended to the list.
	if (devID == -1)
	{
	    System.err.println("Dev not found");
	    insertNewDevBasic(data[10]);
	    developersList.add(data[10]);
	    devID = developersList.indexOf(data[10]);
	}

	insertQuery = "INSERT INTO TblGames"
		+ " VALUES (" + steamID + ", '" + title + "', '"
		+ shortDescription + "', '" + userReviews + "', " + metaCritic + ", '"
		+ releaseDate + "', " + multiplayerSupport + ", "
		+ controllerSupport + ", '" + genre1 + "', '" + genre2
		+ "', '" + genre3 + "', '" + longDescription + "', " + isOwned
		+ ", " + devID + ") ";

	//Testing
	System.out.println(insertQuery);
	try
	{
	    weNeedAResultSet = gameInserter.ExecuteStmt(insertQuery);
	}
	catch (SQLException ex)
	{
	    System.err.println("SQL Exception thrown in insertNewGame,"
		    + " PopulateTables, sqlLogic: \n" + ex);
	}

	return developersList;
    }

    /**
     * Method to reliable insert a new developer into the table. NB: This
     * inserts ONLY a name for the developer.
     *
     * @param devName The name of the developer to be inserted into the table.
     */
    public void insertNewDevBasic(String devName)
    {
	String insertQuery;
	SQLController devInserter = new SQLController();
	ResultSet weNeedAResultSet;

	insertQuery = "INSERT INTO TblDevelopers (Name)"
		+ " VALUES ('" + devName + "')";

	//Testing
	System.out.println(insertQuery);

	try
	{
	    weNeedAResultSet = devInserter.ExecuteStmt(insertQuery);
	}
	catch (SQLException ex)
	{
	    System.err.println("SQL Exception thrown in insertNewDevBasic,"
		    + " PopulateTables, sqlLogic: \n" + ex);
	}

    }

}
