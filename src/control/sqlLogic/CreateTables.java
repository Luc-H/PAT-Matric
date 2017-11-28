/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.sqlLogic;

import static control.sqlLogic.DBConnect.conn;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author lucha
 */
public class CreateTables
{
    
    public static void CreateTblDevs() throws SQLException
    {
	Statement stmt = conn.createStatement();
	String SQL = "CREATE TABLE TblDevelopers"
		+ "("
		+ "DevID INT not null primary key GENERATED ALWAYS AS IDENTITY(START WITH 0, INCREMENT BY 1),"
		+ "Name VARCHAR (75) not null,"
		+ "FoundedYear INTEGER,"
		+ "Founder varchar (35),"
		+ "HQCountry VARCHAR (50),"
		+ "Website VARCHAR (100)"
		+ ")";
	stmt.executeUpdate(SQL);
    }
    
    public static void CreateTblGames() throws SQLException
    {
	Statement stmt = conn.createStatement();
	String SQL = "CREATE TABLE TblGames"
		+ "("
		+ "SteamID INT not null primary key,"
		+ "Title VARCHAR (55) not null,"
		+ "ShortDescription VARCHAR (350),"
		+ "UserReviews VARCHAR (30),"
		+ "Metacritic INT,"
		+ "ReleaseDate VARCHAR (50),"
		+ "Multiplayer SMALLINT,"
		+ "ControllerSupport SMALLINT,"
		+ "Genre1 VARCHAR (25),"
		+ "Genre2 VARCHAR (25),"
		+ "Genre3 VARCHAR (25),"
		+ "LongDescription VARCHAR (5000),"
		+ "IsOwned SMALLINT,"
		+ "DevID INT not null"
		+ ")";
	stmt.executeUpdate(SQL);
	
	//Second SQL Statement needed to create the PK/FK relationship
	SQL = "Alter Table APP.TblGames ADD FOREIGN KEY (DevID) References APP.TblDevelopers (DevID)";
	stmt.executeUpdate(SQL);
    }


}
