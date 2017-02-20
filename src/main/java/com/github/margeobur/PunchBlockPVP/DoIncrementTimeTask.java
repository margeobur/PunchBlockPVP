package com.github.margeobur.PunchBlockPVP;

import java.sql.SQLException;
import java.util.UUID;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.margeobur.PunchBlockPVP.Database;
import com.github.margeobur.PunchBlockPVP.PunchBlockPVP;

public class DoIncrementTimeTask extends BukkitRunnable
{
	PunchBlockPVP plugin;
	Database database;
	ArrayList<UUID> onlinePvpUUIDs;
	ArrayList<UUID> playTimeList;
	
	public DoIncrementTimeTask(PunchBlockPVP inst, Database db, ArrayList<UUID> onlinePvpUUIDs)
	{
		plugin = inst;
		database = db;
		this.onlinePvpUUIDs = onlinePvpUUIDs;
   		playTimeList = new ArrayList<UUID>();
	}
	
	@Override
	public void run()
	{
		try
        {
        	database.incrementTimes(onlinePvpUUIDs, playTimeList);
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
      		return;
        }
		new BukkitRunnable() 
		{
            @Override
            public void run() 
            {
            	if(!playTimeList.isEmpty())
            	{
	    			for(UUID playerID : playTimeList)
	    			{
	    				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
	    						"give " + Bukkit.getPlayer(playerID).getName() + " 264 1");
	    				Bukkit.getPlayer(playerID).sendMessage(ChatColor.GOLD + "You gain 1 diamond for 2 hours of playtime!");
	    			}
            	}
            }   
		}.runTask(plugin);
	}
}
