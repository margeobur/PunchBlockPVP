package com.github.margeobur.PunchBlockPVP;

import java.util.UUID;
import java.util.ArrayList;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.NoChance.PvPManager.Managers.PlayerHandler;

import com.github.margeobur.PunchBlockPVP.Database;
import com.github.margeobur.PunchBlockPVP.PunchBlockPVP;
import com.github.margeobur.PunchBlockPVP.DoIncrementTimeTask;

public class IncrementTimeTask extends BukkitRunnable 
{
	Database database;
	PunchBlockPVP plugin;
	PlayerHandler playerHandler;
	
	public IncrementTimeTask(PunchBlockPVP inst, Database db, PlayerHandler ph)
	{
		plugin = inst;
		database = db;
		playerHandler = ph;
	}
	
	@Override
	public void run() 
	{	
		new BukkitRunnable() 
		{
            @Override
            public void run() 
            {
        		ArrayList<UUID> onlinePvpUUIDs = new ArrayList<UUID>();
        		ArrayList<Player> onlinePlayers = new ArrayList<Player>(Bukkit.getOnlinePlayers());
        		
        		for(Player player : onlinePlayers)
        		{
        			if(playerHandler.get(player).hasPvPEnabled())
        			{
        				onlinePvpUUIDs.add(player.getUniqueId());
        			}
        		}
        		
		       DoIncrementTimeTask dataTask = new DoIncrementTimeTask(plugin, database, onlinePvpUUIDs);
		       dataTask.runTaskAsynchronously(plugin);
            }   
        }.runTask(plugin);
	}
}
