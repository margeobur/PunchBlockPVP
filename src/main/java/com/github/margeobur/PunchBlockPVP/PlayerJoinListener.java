package com.github.margeobur.PunchBlockPVP;

import java.sql.SQLException;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import me.NoChance.PvPManager.Managers.PlayerHandler;

import com.github.margeobur.PunchBlockPVP.PunchBlockPVP;
import com.github.margeobur.PunchBlockPVP.Database;

public class PlayerJoinListener implements Listener
{
	PunchBlockPVP plugin;
	Database database;
	PlayerHandler playerHandler;
	
	public PlayerJoinListener(PunchBlockPVP inst, Database db, PlayerHandler ph)
	{
		plugin = inst;
		database = db;
		playerHandler = ph;
	}
	
	@EventHandler
	public void OnPlayerJoin(PlayerJoinEvent event)
	{
		if(playerHandler.get(event.getPlayer()).hasPvPEnabled())
		{
			try 
			{    
	            database.tryPutNewPlayerEntry(event.getPlayer().getUniqueId());
	        } 
			catch (SQLException e) 
			{
	            e.printStackTrace();
	        }
		}
	}
}
