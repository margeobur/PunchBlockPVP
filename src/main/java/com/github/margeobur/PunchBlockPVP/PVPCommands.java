/* Title: PVPCommands
 * Description: The command handler for PunchBlockPvP.
 * 				So far its only role is to handle the pvpon, pvpoff, yes and no commands,
 * 				which are used when a player first joins the server, to ask them whether
 * 				they want to be PvP-on or -off. They are then promoted and allowed into
 * 				the server.
 * 				As a means of making sure people think and make the right choice, 
 * 				players have a 'focus' that gets set when they type /pvpon or /pvpoff.
 * 				They then type /yes or /no after being prompted to make sure they have the 
 * 				right choice selected. The HashMap that contains their choices just gets
 * 				destroyed when the server is closed, which is OK because it's just temporary.
 */
package com.github.margeobur.PunchBlockPVP;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.UUID;

import com.github.margeobur.PunchBlockPVP.PunchBlockPVP;

public class PVPCommands implements CommandExecutor
{
	HashMap<UUID, Integer> playersFocus;
	PunchBlockPVP plugin;
	
	public PVPCommands(PunchBlockPVP inst)
	{
		plugin = inst;
		playersFocus = new HashMap<UUID, Integer>();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args)
	{
		Player player;
		UUID playerID;
		if(sender instanceof Player)
		{
			player = (Player) sender;
			playerID = player.getUniqueId();
		}
		else 
		{
			sender.sendMessage("Only players can use this command!");
			return true;
		}
		
		if(args.length != 0)
		{
			player.sendMessage("This command does not require arguments.");
			return false;
		}
		
		if(command.getLabel().equalsIgnoreCase("pvpon"))
		{
			if(playersFocus.containsKey(playerID))
				playersFocus.remove(playerID);
				
			playersFocus.put(playerID, 0);
			player.sendMessage("Are you sure you want to be " + ChatColor.DARK_RED + "PVP-ON" + ChatColor.WHITE + "?");
			player.sendMessage("This is a one-time choice! Under certain circumstances we will");
			player.sendMessage("manually turn PvP off for you, but cannot usually toggle on or");
			player.sendMessage("off so be sure of your choice now. Type /pvpyes to accept");
			player.sendMessage("and be flagged as PvP-on. You may then leave the spawn and play.");
			return true;
		}
		if(command.getLabel().equalsIgnoreCase("pvpoff"))
		{
			if(playersFocus.containsKey(playerID))
				playersFocus.remove(playerID);
			
			playersFocus.put(playerID, 1);
			player.sendMessage("Are you sure you want to be " + ChatColor.DARK_RED + "PVP-OFF" + ChatColor.WHITE + "?");
			player.sendMessage("This is a one-time choice! Under certain circumstances we will");
			player.sendMessage("manually turn PvP on for you, but cannot usually toggle on or");
			player.sendMessage("off so be sure of your choice now. Type /pvpyes to accept");
			player.sendMessage("and be flagged as PvP-off. You may then leave the spawn and play.");
			return true;
		}
		if(command.getLabel().equalsIgnoreCase("pvpyes"))
		{
			if( !(playersFocus.containsKey(playerID)) )
			{
				player.sendMessage("Either something went wrong or you have not typed");
				player.sendMessage(" either /pvpon or /pvpoff. Please type /pvpon if you");
				player.sendMessage("wish to PvP on this server or /pvpoff if you do not wish to PvP.");
				return false;
			}
			else
			{
				/* dispatch two commands as console:
				 *  /pex user <player> group set <group>
				 *  	this promotes the player to either a PvPPlayer or a Player
				 *  	these will be two PEX groups, the only difference between which will be
				 *  	the added permission of TimeForMoney rewards
				 *  
				 *  IF the player wishes to be pvp, this command is dispatched:
				 *  (pvp is off by default)
				 *  /pvp <player>
				 *  	this flags the player as PvP with the PvPManger plugin
				 */
				if(playersFocus.get(playerID) == 0)
				{
					plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), 
														"pex user " + player.getName() + " group set PvPPlayer");
					plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), 
														"pvp " + player.getName());
				}
				else
				{
					if(playersFocus.get(playerID) == 1)
					{
						plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), 
														"pex user " + player.getName() + " group set Player");
					}
				}
				return true;
			}
		}
		if(command.getLabel().equalsIgnoreCase("pvpno"))
		{
			/* basically this will undo their 'focus' and 
			 * maybe send them a message.
			 * it's pretty useless but it's symmetrical with /yes xD
			 */
			if(playersFocus.containsKey(playerID))
				playersFocus.remove(playerID);
				
			return true;
		}
		return false;
	}
}
