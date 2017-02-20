package com.github.margeobur.PunchBlockPVP;

import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.margeobur.PunchBlockPVP.PVPCommands;
import com.github.margeobur.PunchBlockPVP.PlayerDeathListener;
import com.github.margeobur.PunchBlockPVP.VotesListener;
import com.github.margeobur.PunchBlockPVP.PlayerCommandListener;
import com.github.margeobur.PunchBlockPVP.Database;
import com.github.margeobur.PunchBlockPVP.PlayerJoinListener;
import com.github.margeobur.PunchBlockPVP.IncrementTimeTask;

import me.NoChance.PvPManager.PvPManager;

public class PunchBlockPVP extends JavaPlugin
{	
	PvPManager pvp;
	Database database;
	
	@Override
    public void onEnable()
	{	
		boolean databaseOpen = false;
		
		pvp = (PvPManager) Bukkit.getPluginManager().getPlugin("PvPManager");
		if(pvp == null)
		{
			this.getLogger().log(Level.INFO, "No instance of PvPManager found. Aborting.");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		database = new Database(this); 		
		
		PVPCommands commander = new PVPCommands(this);
		this.getCommand("pvpon").setExecutor(commander);
		this.getCommand("pvpoff").setExecutor(commander);
		this.getCommand("pvpyes").setExecutor(commander);
		this.getCommand("pvpno").setExecutor(commander);
		getServer().getPluginManager().registerEvents(new PlayerDeathListener(this, pvp), this);
		getServer().getPluginManager().registerEvents(new PlayerCommandListener(this), this);
		getServer().getPluginManager().registerEvents(new VotesListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, database, pvp.getPlayerHandler()), this);
		
		try 
		{    
            database.openConnection();
            database.tryCreateTable();
            if(database.isLive())
            	databaseOpen = true;
            else
            	databaseOpen = false;
        } 
		catch (ClassNotFoundException e) 
		{
            e.printStackTrace();
        } 
		catch (SQLException e) 
		{
            e.printStackTrace();
        }
		
		IncrementTimeTask diamondsTask = new IncrementTimeTask(this, database, pvp.getPlayerHandler());
		
		if(databaseOpen)
			diamondsTask.runTaskTimerAsynchronously(this, 1200, 1200);
		else
			this.getLogger().log(Level.INFO, "The database could not be accessed. Diamonds will not be rewarded.");
	}
	
	@Override
	public void onDisable()
	{
		try
		{
			database.closeConnection();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
