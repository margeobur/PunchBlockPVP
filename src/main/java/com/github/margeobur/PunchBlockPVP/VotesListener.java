package com.github.margeobur.PunchBlockPVP;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import com.vexsoftware.votifier.model.VotifierEvent;
import com.vexsoftware.votifier.model.Vote;

import com.github.margeobur.PunchBlockPVP.PunchBlockPVP;

public class VotesListener implements Listener 
{
	PunchBlockPVP  plugin;
	
	public VotesListener(PunchBlockPVP inst)
	{
		plugin = inst;
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onVoteReceive(VotifierEvent event)
	{
		Player player;
		
		Vote vote = event.getVote();
		player = Bukkit.getPlayer(vote.getUsername());
		
		if(player != null)
		{
			plugin.getLogger().log(Level.INFO, ChatColor.GOLD + "Player " + player.getName() + "voted for the server on " + vote.getServiceName() + ".");
			player.sendMessage(ChatColor.GOLD + "Thank you for voting! Here's 1 claim block :)");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "adjustbonusclaimblocks " + player.getName() + " 1");		
		}
		else
			plugin.getLogger().log(Level.INFO, ChatColor.GOLD + "Player " + vote.getUsername() + "voted for the server on " + vote.getServiceName() + ".");
		
		return;
	}
}
