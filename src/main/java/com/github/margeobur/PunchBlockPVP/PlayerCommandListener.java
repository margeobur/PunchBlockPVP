package com.github.margeobur.PunchBlockPVP;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.github.margeobur.PunchBlockPVP.BackBlockUnlistTask;
import com.github.margeobur.PunchBlockPVP.PunchBlockPVP;

public class PlayerCommandListener implements Listener
{
	PunchBlockPVP plugin;
	
	public PlayerCommandListener(PunchBlockPVP inst)
	{
		plugin = inst;
	}
	
	@EventHandler
	public void onCommandUse(PlayerCommandPreprocessEvent event)
	{
		String command = event.getMessage();
		if(!command.equalsIgnoreCase("/back"))
			return;
			
		if(BackBlockUnlistTask.backBlockList.contains(event.getPlayer().getUniqueId()))
		{
			event.getPlayer().sendMessage("You cannot use /back for 60 seconds after dying in PvP.");
			event.setCancelled(true);
		}
	}
	
}
