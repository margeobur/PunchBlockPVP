package com.github.margeobur.PunchBlockPVP;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

public class BackBlockUnlistTask extends BukkitRunnable
{
	static ArrayList<UUID> backBlockList = new ArrayList<UUID>();
	UUID playerID;
	
	public BackBlockUnlistTask(UUID thePlayerID)
	{
		playerID = thePlayerID;
	}
	
	@Override
	public void run()
	{
		if(backBlockList.contains(playerID))
			backBlockList.remove(playerID);
	}
}
