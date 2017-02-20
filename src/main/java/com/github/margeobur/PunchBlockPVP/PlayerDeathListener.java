package com.github.margeobur.PunchBlockPVP;

import java.util.Random;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;

import me.NoChance.PvPManager.PvPManager;
//import me.NoChance.PvPManager.PvPlayer;
import me.NoChance.PvPManager.Managers.PlayerHandler;
import me.NoChance.PvPManager.Utils.CombatUtils;

import com.github.margeobur.PunchBlockPVP.PunchBlockPVP;
import com.github.margeobur.PunchBlockPVP.BackBlockUnlistTask;

public class PlayerDeathListener implements Listener 
{
	PunchBlockPVP plugin;
	PvPManager pvp;
	PlayerHandler ph;
	
	public PlayerDeathListener(PunchBlockPVP inst, PvPManager pvpmang)
	{
		plugin = inst;
		pvp = pvpmang;
		ph = pvp.getPlayerHandler();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST ,ignoreCancelled = true)
	public void onPlayerDie(PlayerDeathEvent event)
	{
		Player victim = (Player) event.getEntity();
//		PvPlayer pvplayer = ph.get(victim);
		PlayerInventory inventory;
		HashMap<Integer, ItemStack> itemsMap;
		
		float theFloat;
		Random r = new Random();
		int bits = 0;
		
		final Player killer = victim.getKiller();
		final boolean pvpDeath = killer != null;
		if(pvpDeath && !killer.equals(victim))
		{
			event.setKeepInventory(true);
			
			inventory = victim.getInventory();
			itemsMap = new HashMap<Integer, ItemStack>();
			for(int i=0; i<9; i++)
			{
				if(i != inventory.getHeldItemSlot() && inventory.getItem(i) != null)
				{
					itemsMap.put(i, inventory.getItem(i));
					inventory.clear(i);
				}
			}
			for(int i=9; i<36; i++)
			{
				if(inventory.getItem(i) != null)
				{
					itemsMap.put(i, inventory.getItem(i));
					inventory.clear(i);
				}
			}		
			
			if(inventory.getHelmet() != null)
				bits = bits | 1;
			if(inventory.getChestplate() != null)
				bits = bits | 2;
			if(inventory.getLeggings() != null)
				bits = bits | 4;
			if(inventory.getBoots() != null)
				bits = bits | 8;
				
			//I sincerely apologize for the grotesque bunch of code that follows but its LATE
			//and I have to get this done by tomorrow!
			theFloat = r.nextFloat();
			switch(bits)
			{
				case 1:	//There is only a helmet
				{
					itemsMap.put(36, inventory.getHelmet());
					inventory.clear(39);
					break;
				}
				case 2: //There is only a chestplate
				{
					itemsMap.put(36, inventory.getChestplate());
					inventory.clear(38);
					break;
				}
				case 3: //There are a helmet and a chestplate
				{
					if(theFloat <= 0.615)
					{
						itemsMap.put(36, inventory.getHelmet());
						inventory.clear(39);
						break;
					}
					else
					{
						itemsMap.put(36, inventory.getChestplate());
						inventory.clear(38);
						break;
					}
				}
				case 4: //There are only leggings
				{
					itemsMap.put(36, inventory.getLeggings());
					inventory.clear(37);
					break;
				}
				case 5: //There are a helmet and leggings
				{
					if(theFloat <= 0.583)
					{
						itemsMap.put(36, inventory.getHelmet());
						inventory.clear(39);
						break;
					}
					else
					{
						itemsMap.put(36, inventory.getLeggings());
						inventory.clear(37);
						break;
					}
				}
				case 6: //There are a chestplate and leggings
				{
					if(theFloat <= 0.533)
					{
						itemsMap.put(36, inventory.getLeggings());
						inventory.clear(37);
						break;
					}
					else
					{	
						itemsMap.put(36, inventory.getChestplate());
						inventory.clear(38);
						break;
					}
				}
				case 7: //There are a helmet, chestplate and leggings
				{
					if(theFloat <= 0.4)
					{
						itemsMap.put(36, inventory.getHelmet());
						inventory.clear(39);
						break;
					}
					if(theFloat > 0.4 && theFloat <= 0.75)
					{
						itemsMap.put(36, inventory.getLeggings());
						inventory.clear(37);
						break;
					}
					else
					{
						itemsMap.put(36, inventory.getChestplate());
						inventory.clear(38);
						break;
					}
				}
				case 8: //There are only boots
				{
					itemsMap.put(36, inventory.getBoots());
					inventory.clear(36);
					break;
				}
				case 9: //There are boots and a helmet
				{
					if(theFloat <= 0.556)
					{
						itemsMap.put(36, inventory.getBoots());
						inventory.clear(36);
						break;
					}
					else
					{
						itemsMap.put(36, inventory.getHelmet());
						inventory.clear(39);
						break;
					}
				}
				case 10: //There are boots and a chestpate
				{
					if(theFloat <= 0.667)
					{
						itemsMap.put(36, inventory.getBoots());
						inventory.clear(36);
						break;
					}
					else
					{
						itemsMap.put(36, inventory.getChestplate());
						inventory.clear(38);
						break;
					}
				}
				case 11: //There are boots, a chestplate and a helmet
				{
					if(theFloat <= 0.471)
					{
						itemsMap.put(36, inventory.getBoots());
						inventory.clear(36);
						break;
					}
					if(theFloat > 0.471 && theFloat <= 0.765)
					{
						itemsMap.put(36, inventory.getHelmet());
						inventory.clear(39);
						break;
					}
					else
					{
						itemsMap.put(36, inventory.getChestplate());
						inventory.clear(38);
						break;
					}
				}
				case 12: //There are boots and leggings
				{
					if(theFloat <= 0.636)
					{
						itemsMap.put(36, inventory.getBoots());
						inventory.clear(36);
						break;
					}
					else
					{
						itemsMap.put(36, inventory.getLeggings());
						inventory.clear(37);
						break;
					}
				}
				case 13: //There are boots, leggings and a helmet
				{
					if(theFloat <= 0.438)
					{
						itemsMap.put(36, inventory.getBoots());
						inventory.clear(36);
						break;
					}
					if(theFloat > 0.438 && theFloat <= 0.75)
					{
						itemsMap.put(36, inventory.getHelmet());
						inventory.clear(39);
						break;
					}
					else
					{
						itemsMap.put(36, inventory.getLeggings());
						inventory.clear(37);
						break;
					}
				}
				case 14: //There are boots, leggings and a chestplate
				{
					if(theFloat <= 0.421)
					{
						itemsMap.put(36, inventory.getBoots());
						inventory.clear(36);
						break;
					}
					if(theFloat > 0.421 && theFloat < 0.789)
					{
						itemsMap.put(36, inventory.getLeggings());
						inventory.clear(37);
						break;
					}
					else
					{
						itemsMap.put(36, inventory.getChestplate());
						inventory.clear(38);
						break;
					}
				}
				case 15: //The player was fully equipped when they died
				{
					if(theFloat <= 0.333)
					{
						itemsMap.put(36, inventory.getBoots());
						inventory.clear(36);
						break;
					}
					if(theFloat > 0.333 && theFloat <= 0.625)
					{
						itemsMap.put(36, inventory.getHelmet());
						inventory.clear(39);
						break;
					}
					if(theFloat > 0.625 && theFloat <= 0.833)
					{
						itemsMap.put(36, inventory.getLeggings());
						inventory.clear(37);
						break;
					}
					else
					{
						itemsMap.put(36, inventory.getChestplate());
						inventory.clear(38);
						break;
					}
				}
				default:
					break;
			}	
			//********************** end of 'grotesque' code
	
			CombatUtils.fakeItemStackDrop(victim, itemsMap.values().toArray(new ItemStack[itemsMap.values().size()]));
			BackBlockUnlistTask.backBlockList.add(victim.getUniqueId());
			
			BackBlockUnlistTask newRunnable = new BackBlockUnlistTask(victim.getUniqueId());
			newRunnable.runTaskLaterAsynchronously(plugin, 1200);
		}
	}	
}
