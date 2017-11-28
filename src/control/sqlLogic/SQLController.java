/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.sqlLogic;

import static control.sqlLogic.DBConnect.conn;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class containing methods required to process any SQL query passed to it.
 *
 * @author Luc Hayward
 */
public class SQLController
{

    /**
     * Passed a SQL statement, method will execute and return the data(if any)
     * as a Result Set. Recommend declare a temp value for potential rs return.
     *
     * @param query SQL query as a string.
     * @return The ResultSet created from the DataBase.
     * @throws java.sql.SQLException Exception can safely be thrown as most
     * queries passed to this method are either unknown to the end user or in
     * the rare case that they are custom written, the user should fix them
     * anyhow. To this end the error may be caught by the calling method.
     */
    public ResultSet ExecuteStmt(String query) throws SQLException
    {
	Statement stmt = conn.createStatement();
	ResultSet rs = null;
	int thisIsNeededForTheReturnOfAnUpdate = 0;

	// Checks whether the query produced a result set(SELECT) or not (eg: UPDATE)
	if (query.startsWith("SELECT"))
	{
	    rs = stmt.executeQuery(query);
	}
	// The query was not a SELECT query and thus should not be run again (no Duplicates)
	else
	{
	    // INSERT/UPDATE/DELETE
	    thisIsNeededForTheReturnOfAnUpdate = stmt.executeUpdate(query);
	    System.out.println("UPDATE query run: " + query + "\n results returned :" + thisIsNeededForTheReturnOfAnUpdate);
	}

	return rs;
    }

}
