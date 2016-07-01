package com.bukkit.Turbowaffle.ChestStockChecker;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerInventoryEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class ChestStockCheckerPlayerListener extends PlayerListener{
	public static ChestStockChecker plugin;
	public ChestStockCheckerPlayerListener(ChestStockChecker instance) {
	        plugin = instance;
	}

	private void removeInvalidItems(Block chestBlock){
		Chest chest = (Chest) chestBlock.getState();
		Inventory chestInventory = chest.getInventory();
		ItemStack[] items = chestInventory.getContents();
		
		//System.out.println("Chest Conents");
		boolean fixed = false;
		for(int j = 0; j < items.length; j++){
			//items[j] = new ItemStack(10,100);
			//continue;
			
			
			if(items[j] == null) { continue; }
			if(items[j].getAmount() > items[j].getMaxStackSize()){
				System.out.println("Found invalid stack ID [" + items[j].getTypeId() +
						"] with count " + items[j].getAmount() + " at location " +
						chestBlock.getLocation().getX() + "," + chestBlock.getLocation().getY() +
						chestBlock.getLocation().getZ());
				items[j].setAmount(items[j].getMaxStackSize());
				fixed = true;
				//System.out.println("Deleting invalid item stack ID [" + items[j].getTypeId() +
				//		"] with count " + items[j].getAmount());
				//items[j] = null;
			}
			
		}
		if(fixed){
			chestInventory.setContents(items);
		}
	}
	public void onPlayerInteract(PlayerInteractEvent event){
		//System.out.println("onPlayerInteract()");
		//System.out.println(event.getClickedBlock().getTypeId());
		Block block = event.getClickedBlock();
		
		//System.out.println(event.getAction());
		
		
		if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR){
			return;
		} 
		
		
		//System.out.println("Checking block type");
		if(block == null){
			return;
		} else if(block.getTypeId() == 68){
			/**
			 * They clicked on a sign, check to see if it's one of the
			 * registered shop signs. Update the stock
			 */
			Location chestLocation = block.getLocation();
			chestLocation.setY(chestLocation.getY() - 1);
			
			int rowID = plugin.getChestIDByLocation(chestLocation.getBlockX(),
					chestLocation.getBlockY(), 
					chestLocation.getBlockZ());
			if(rowID != 0){
				plugin.enqueueChestStockUpdate(rowID);
				return;
			}
			chestLocation.setY(chestLocation.getY() + 2);
			rowID = plugin.getChestIDByLocation(chestLocation.getBlockX(),
					chestLocation.getBlockY(), 
					chestLocation.getBlockZ());
			if(rowID != 0){
				plugin.enqueueChestStockUpdate(rowID);
				return;
			}
			
		} else if(block.getTypeId() == 54){
			if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {removeInvalidItems(block);}
			//The clicked on a chest
			Location chestLocation = block.getLocation();
			
			int rowID = plugin.getChestIDByLocation(chestLocation.getBlockX(),
					chestLocation.getBlockY(), 
					chestLocation.getBlockZ());
			
			//System.out.println("Registered chest ID " + rowID);
			/* They clicked on a chest, so we check to see if they
			 * are trying to register it, or update the quantity
			 */	
			if(rowID != 0){
				//System.out.println("DB: Found chest in DB");
				
				if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
					
					plugin.enqueueChestStockUpdate(rowID);
					
					return;
				}
				/* The chest is already registered. If the user is in
				 * the register queue, then it looks like they're trying to register
				 * a chest that's already been registered
				 */
				if(!plugin.registerQueue.isEmpty()){
					String playerName = event.getPlayer().getName();
					for(int i = 0; i < plugin.registerQueue.size(); i++){
						if(plugin.registerQueue.get(i).equalsIgnoreCase(playerName)){
							plugin.registerQueue.remove(i);
							plugin.notifyPlayer(playerName, "This chest is already registered");
							return;
						}
					}
				}
				if(!plugin.deregisterQueue.isEmpty()){
					String playerName = event.getPlayer().getName();
					for(int i = 0; i < plugin.deregisterQueue.size(); i++){
						if(plugin.deregisterQueue.get(i).equalsIgnoreCase(playerName)){
							plugin.deregisterQueue.remove(i);
							ResultSet chestInfo = plugin.getChestDbRowById(rowID);
							try {
								if(chestInfo.getString("player").equalsIgnoreCase(playerName)){
									//Delete row
									plugin.deleteRow(rowID);
									plugin.notifyPlayer(playerName, "Chest deregistered!");
								} else {
									plugin.notifyPlayer(playerName, "This chest is not registered to you");
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return;
						}
					}
				}
				
				
				String playerName = plugin.getPlayerNameByChestID(rowID);
				if(! playerName.equalsIgnoreCase(event.getPlayer().getName())){
					System.out.println("DB: Doesn't own chest, not updating");
					return;
				}
				
				
			} else {
				/*
				 * If we're here, they left clicked on chest, but it wasn't registered to anyone.
				 * If they're in the register queue, check the sign above and add it to the DB
				 */
				if(!plugin.registerQueue.isEmpty()){
					String playerName = event.getPlayer().getName();
					for(int i = 0; i < plugin.registerQueue.size(); i++){
						if(plugin.registerQueue.get(i).equalsIgnoreCase(playerName)){
							plugin.registerQueue.remove(i);
							if(plugin.registerChestOld(playerName,chestLocation)){
								plugin.notifyPlayer(playerName, "Chest registered!");
							} else {
								plugin.notifyPlayer(playerName, "Could not register chest!");
							}
							
							
							return;
						}
					}
				}
			}
		}
	}
}
