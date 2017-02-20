package com.github.margeobur.PunchBlockPVP;

import com.github.margeobur.PunchBlockPVP.PunchBlockPVP;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.util.UUID;
import java.util.ArrayList;

public class Database 
{
	PunchBlockPVP plugin;
	private Connection connection;
	
	public Database(PunchBlockPVP inst)
	{
	    plugin = inst;
	}
	
	public boolean isLive() throws SQLException
	{
		return !connection.isClosed();
	}
	
	public void openConnection() throws SQLException, ClassNotFoundException
	{
		if (connection != null && !connection.isClosed()) 
			return;

	    synchronized (this)
	    {
	        if (connection != null && !connection.isClosed()) 
	            return;
	        
	        Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PBPvP", "Pb", "bl0cky");
	    }
	}
	
	public void closeConnection() throws SQLException
	{
		connection.close();
	}
	
	public void tryCreateTable() throws SQLException
	{
		if(connection.isClosed())
			return;
		
		boolean doCreate = true;
		
		Statement statement = connection.createStatement();
		DatabaseMetaData dbMetaData = connection.getMetaData();
		ResultSet result = dbMetaData.getTables(null, null, null, null);
		
		while(result.next())
		{	
			if(result.getString("TABLE_NAME").contentEquals("PvPers"))
			{
				doCreate = false;
				break;
			}
		}
		
		if(doCreate == true)
		{
			statement.executeUpdate("create table PvPers (UUID Char(36), time Int);");
		}
		return;
	}
	
	public void incrementTimes(ArrayList<UUID> onlinePvpUUIDs, ArrayList<UUID> playTimeList) throws SQLException
	{
		if(connection.isClosed())
			return;

		Statement statement = connection.createStatement();
		
		for(UUID tmpID : onlinePvpUUIDs)
		{
			statement.executeUpdate("UPDATE PvPers SET time = time + 1 WHERE UUID = '" + tmpID.toString() + "';");
			ResultSet result = statement.executeQuery("SELECT UUID FROM PvPers where time = 120 and UUID = '" + tmpID.toString() + "';");
		
			if(result.next())
			{
				tmpID = UUID.fromString(result.getString(1));
				playTimeList.add(tmpID);
			}
		}
		
		statement.executeUpdate("UPDATE PvPers SET time = 0 where time = 120;");
	}
	
	public boolean tryPutNewPlayerEntry(UUID playerID) throws SQLException
	{
		if(connection.isClosed())
			return false;
		
		String playerIDString  = playerID.toString();
		Statement statement = connection.createStatement();
		
		ResultSet result = statement.executeQuery("SELECT UUID FROM PvPers where UUID = '" + playerIDString + "';");
		if(result.next())
			return false;
		else
		{
			statement.executeUpdate("INSERT INTO PvPers VALUES ('" + playerIDString + "', 0)");
			return true;
		}		
	}
}
