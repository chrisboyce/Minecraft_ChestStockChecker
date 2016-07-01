package com.bukkit.Turbowaffle.ChestStockChecker;

public class ChestShopSignInfo {

	public String player;
	public int quantity;
	public float buyPrice;
	public float sellPrice;
	public int itemID;
	public String itemData;
	
	public String toString(){
		return "Player [" + player + "], Quantity [" + quantity + "]" +
		"Buy/Sell [" + buyPrice + "/" + sellPrice +"], Item ID [" + itemID + ":" + itemData
		+ "]";
	}
}
