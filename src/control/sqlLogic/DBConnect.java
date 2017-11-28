/**
 * PAT 2015/09/09 Main Db Connect
 *
 * @author Luc
 */
package control.sqlLogic;

import java.sql.*;

public class DBConnect
{

    public static Connection conn = null;

    /**
     * Connector contains the code required to setup an initial connection to
     * the database. From this connection all other access is achievable.
     */
    public static void Connector()
    {
	/*BufferedReader inKb = new BufferedReader(new InputStreamReader(System.in));			//keybopard reader
	SQLStatement sqlStmt = new SQLStatement();							//reference to public class SQLStatekment
	WebReader webReader = new WebReader();	*/							//reference to public class WebReader

	try
	{
	    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

	    //create a connection to the database (herein named GameDB)
	    String url = "jdbc:derby:GameDB;create=true";
	    conn = DriverManager.getConnection(url);
	    //check that connection is made
	    if (conn != null)
	    {
		System.out.println("Connections made");
	    }
	}
	catch (SQLException | ClassNotFoundException ex)
	{
	    System.err.println("Error: DBConnect/Connector(): " + ex);
	    ex.printStackTrace();
	}
    }

}
