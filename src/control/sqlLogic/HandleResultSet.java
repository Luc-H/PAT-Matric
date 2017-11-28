/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.sqlLogic;

import static control.MainApp.developersList;
import control.model.Developer;
import control.model.Game;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luc Hayward
 */
public class HandleResultSet
{
    public void developerListRS(ResultSet developersRS)
    {
	int indexCNT = 0;
	String developerName;

	try
	{
	    // While the next row exists
	    while (developersRS.next())
	    {
		developerName = developersRS.getString("Name");
		developersList.add(developerName);
		indexCNT++;


	    }
	}
	catch (SQLException ex)
	{
	    System.err.println("Error occured in developerListRS, HandleResultSet,"
		    + "sqlLogic, control during the while method attempting to "
		    + "go through the result set from start to end: " + ex);
	}
    }

    public String getDevName(ResultSet devNameRS) throws SQLException
    {
	String devName = "Dev Not Found";
	devNameRS.next();
	devName = devNameRS.getString("Name");

	return devName;
    }

    public List<Game> gameDataRS(ResultSet gameRS)
    {
	List<Game> gameDataList = new ArrayList();
	Game currentGame = null;

	try
	{
	    while (gameRS.next())
	    {
		//currentGame = new Game();

		currentGame = new Game(gameRS.getInt(1), gameRS.getString(2),
			gameRS.getString(3), gameRS.getString(4), gameRS.getInt(5),
			gameRS.getString(6), gameRS.getBoolean(7), gameRS.getBoolean(8),
			gameRS.getString(9), gameRS.getString(10), gameRS.getString(11),
			gameRS.getString(12), gameRS.getBoolean(13), gameRS.getInt(14));
		//System.out.println("Adding: " + currentGame);
		gameDataList.add(currentGame);
	    }
	}
	catch (SQLException ex)
	{
	    System.err.println("Error occured in gameDataRS, HandleResultSet,"
		    + "sqlLogic, control during the while method attempting to "
		    + "go through the result set from start to end: " + ex);
	}

	return gameDataList;
    }

    public List<Developer> developerDataRS(ResultSet developerRS)
    {
	List<Developer> developerDataList = new ArrayList();
	Developer currentDeveloper = null;

	try
	{
	    while (developerRS.next())
	    {
		currentDeveloper = new Developer(developerRS.getInt(1), developerRS.getString(2),
			developerRS.getInt(3), developerRS.getString(4), developerRS.getString(5),
			developerRS.getString(6));
		developerDataList.add(currentDeveloper);
	    }
	}
	catch (SQLException ex)
	{
	    System.err.println("Error occured in developerDataRS, HandleResultSet,"
		    + "sqlLogic, control during the while method attempting to "
		    + "go through the result set from start to end: " + ex);
	}

	return developerDataList;
    }

}
