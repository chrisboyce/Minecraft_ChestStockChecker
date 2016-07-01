package com.bukkit.Turbowaffle.ChestStockChecker;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.SignChangeEvent;

public class ChestStockCheckerBlockListener extends BlockListener {
	public static ChestStockChecker plugin;
	public ChestStockCheckerBlockListener(ChestStockChecker instance) {
	        plugin = instance;
	}
	
	public void onBlockBreak(BlockBreakEvent event){
		int blockTypeId = event.getBlock().getTypeId();

		int chestRowID = 0;
		if(blockTypeId == 54){
			//System.out.println("Chest break detected");
			//check sign
			Location signLocation = event.getBlock().getLocation();
			chestRowID = plugin.getChestIDByLocation(
					signLocation.getBlockX(), 
					signLocation.getBlockY(), 
					signLocation.getBlockZ());
		} else if(blockTypeId == 68){
			//check chest
			//System.out.println("Sign break detected");
			Location chestLocation = event.getBlock().getLocation();
			chestRowID = plugin.getChestIDBySignLocation(
					chestLocation.getBlockX(), 
					chestLocation.getBlockY(), 
					chestLocation.getBlockZ());
		} else {
			return;
		}
		
		//System.out.println("Got Row ID " + chestRowID);
		String eventPlayer = event.getPlayer().getName();
		

		if(chestRowID == 0){
			return;
		}
		String chestOwner = plugin.getPlayerNameByChestID(chestRowID);
		if(chestOwner.equalsIgnoreCase(eventPlayer)){
			plugin.deleteRow(chestRowID);
			plugin.notifyPlayer(eventPlayer,"Chest de-registered!");
		} else {
			plugin.notifyPlayer(eventPlayer, "This chest is not registered to you!");
			event.setCancelled(true);
			return;
		}
	}
	public void onSignChange(SignChangeEvent event){
		ChestShopSignInfo signInfo = plugin.parseSign(
				event.getLine(0),
				event.getLine(1),
				event.getLine(2),
				event.getLine(3));
		
		if(signInfo == null){
			//plugin.notifyPlayer(event.getPlayer().getName(), "We couldn't understand your shop sign. Please check the formatting.");
			return;
		}
		Location chestLocationBelow = event.getBlock().getLocation().clone();
		Location chestLocationAbove = chestLocationBelow.clone();
		
		chestLocationAbove.setY(chestLocationAbove.getY() + 1);
		chestLocationBelow.setY(chestLocationBelow.getY() - 1);
		
		Block chestBelowBlock = event.getBlock().getWorld().getBlockAt(chestLocationBelow);
		Block chestAboveBlock = event.getBlock().getWorld().getBlockAt(chestLocationAbove);
		Location chestLocation = null;
		if(chestBelowBlock.getTypeId() == 54){
			//System.out.println("Found chest below");
			chestLocation = chestLocationBelow;
		} else if(chestAboveBlock.getTypeId() == 54){
			//System.out.println("Found chest above");
			chestLocation = chestLocationAbove;
		} else {
			plugin.notifyPlayer(event.getPlayer().getName(), "Chest must be below or above sign");
			return;
		}
		plugin.registerChest(signInfo, chestLocation,event.getBlock().getLocation());
		
	}
}
