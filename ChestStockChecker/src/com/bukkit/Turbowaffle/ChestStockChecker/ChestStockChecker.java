package com.bukkit.Turbowaffle.ChestStockChecker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;


import com.bukkit.Turbowaffle.ChestStockChecker.ChestStockChecker;

public class ChestStockChecker extends org.bukkit.plugin.java.JavaPlugin {

	private Connection con;
	public ArrayList<String> registerQueue;
	public ArrayList<Integer> pushStockQueue;
	public ArrayList<String> deregisterQueue;
	public HashMap<String, int[]> itemLookup;
	private final ChestStockCheckerPlayerListener playerListener = new ChestStockCheckerPlayerListener(this);
	private final ChestStockCheckerBlockListener blockListener = new ChestStockCheckerBlockListener(this);
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}
	
	private void setItemLookup(){
		itemLookup = new HashMap<String, int[]>();
		itemLookup.put("stone", new int[]{1,0});
		itemLookup.put("sstone", new int[]{1,0});
		itemLookup.put("smoothstone", new int[]{1,0});
		itemLookup.put("rock", new int[]{1,0});
		itemLookup.put("grass", new int[]{2,0});
		itemLookup.put("dirt", new int[]{3,0});
		itemLookup.put("cobblestone", new int[]{4,0});
		itemLookup.put("cstone", new int[]{4,0});
		itemLookup.put("cobble", new int[]{4,0});
		itemLookup.put("plank", new int[]{5,0});
		itemLookup.put("woodenplank", new int[]{5,0});
		itemLookup.put("woodplank", new int[]{5,0});
		itemLookup.put("wplank", new int[]{5,0});
		itemLookup.put("plankwooden", new int[]{5,0});
		itemLookup.put("plankwood", new int[]{5,0});
		itemLookup.put("plankw", new int[]{5,0});
		itemLookup.put("sapling", new int[]{6,0});
		itemLookup.put("treesapling", new int[]{6,0});
		itemLookup.put("logsapling", new int[]{6,0});
		itemLookup.put("trunksapling", new int[]{6,0});
		itemLookup.put("woodsapling", new int[]{6,0});
		itemLookup.put("oaktreesapling", new int[]{6,0});
		itemLookup.put("oaklogsapling", new int[]{6,0});
		itemLookup.put("oaktrunksapling", new int[]{6,0});
		itemLookup.put("oakwoodsapling", new int[]{6,0});
		itemLookup.put("normaltreesapling", new int[]{6,0});
		itemLookup.put("normallogsapling", new int[]{6,0});
		itemLookup.put("normaltrunksapling", new int[]{6,0});
		itemLookup.put("normalwoodsapling", new int[]{6,0});
		itemLookup.put("osapling", new int[]{6,0});
		itemLookup.put("otreesapling", new int[]{6,0});
		itemLookup.put("ologsapling", new int[]{6,0});
		itemLookup.put("otrunksapling", new int[]{6,0});
		itemLookup.put("owoodsapling", new int[]{6,0});
		itemLookup.put("nsapling", new int[]{6,0});
		itemLookup.put("ntreesapling", new int[]{6,0});
		itemLookup.put("nlogsapling", new int[]{6,0});
		itemLookup.put("ntrunksapling", new int[]{6,0});
		itemLookup.put("nwoodsapling", new int[]{6,0});
		itemLookup.put("treesap", new int[]{6,0});
		itemLookup.put("logsap", new int[]{6,0});
		itemLookup.put("trunksap", new int[]{6,0});
		itemLookup.put("woodsap", new int[]{6,0});
		itemLookup.put("oaktreesap", new int[]{6,0});
		itemLookup.put("oaklogsap", new int[]{6,0});
		itemLookup.put("oaktrunksap", new int[]{6,0});
		itemLookup.put("oakwoodsap", new int[]{6,0});
		itemLookup.put("normaltreesap", new int[]{6,0});
		itemLookup.put("normallogsap", new int[]{6,0});
		itemLookup.put("normaltrunksap", new int[]{6,0});
		itemLookup.put("normalwoodsap", new int[]{6,0});
		itemLookup.put("osap", new int[]{6,0});
		itemLookup.put("otreesap", new int[]{6,0});
		itemLookup.put("ologsap", new int[]{6,0});
		itemLookup.put("otrunksap", new int[]{6,0});
		itemLookup.put("owoodsap", new int[]{6,0});
		itemLookup.put("nsap", new int[]{6,0});
		itemLookup.put("ntreesap", new int[]{6,0});
		itemLookup.put("nlogsap", new int[]{6,0});
		itemLookup.put("ntrunksap", new int[]{6,0});
		itemLookup.put("nwoodsap", new int[]{6,0});
		itemLookup.put("redsapling", new int[]{6,1});
		itemLookup.put("redtreesapling", new int[]{6,1});
		itemLookup.put("redlogsapling", new int[]{6,1});
		itemLookup.put("redtrunksapling", new int[]{6,1});
		itemLookup.put("redwoodsapling", new int[]{6,1});
		itemLookup.put("darksapling", new int[]{6,1});
		itemLookup.put("darktreesapling", new int[]{6,1});
		itemLookup.put("darklogsapling", new int[]{6,1});
		itemLookup.put("darktrunksapling", new int[]{6,1});
		itemLookup.put("darkwoodsapling", new int[]{6,1});
		itemLookup.put("blacksapling", new int[]{6,1});
		itemLookup.put("blacktreesapling", new int[]{6,1});
		itemLookup.put("blacklogsapling", new int[]{6,1});
		itemLookup.put("blacktrunksapling", new int[]{6,1});
		itemLookup.put("blackwoodsapling", new int[]{6,1});
		itemLookup.put("pinesapling", new int[]{6,1});
		itemLookup.put("pinetreesapling", new int[]{6,1});
		itemLookup.put("pinelogsapling", new int[]{6,1});
		itemLookup.put("pinetrunksapling", new int[]{6,1});
		itemLookup.put("pinewoodsapling", new int[]{6,1});
		itemLookup.put("sprucesapling", new int[]{6,1});
		itemLookup.put("sprucetreesapling", new int[]{6,1});
		itemLookup.put("sprucelogsapling", new int[]{6,1});
		itemLookup.put("sprucetrunksapling", new int[]{6,1});
		itemLookup.put("sprucewoodsapling", new int[]{6,1});
		itemLookup.put("rsapling", new int[]{6,1});
		itemLookup.put("rtreesapling", new int[]{6,1});
		itemLookup.put("rlogsapling", new int[]{6,1});
		itemLookup.put("rtrunksapling", new int[]{6,1});
		itemLookup.put("rwoodsapling", new int[]{6,1});
		itemLookup.put("dsapling", new int[]{6,1});
		itemLookup.put("dtreesapling", new int[]{6,1});
		itemLookup.put("dlogsapling", new int[]{6,1});
		itemLookup.put("dtrunksapling", new int[]{6,1});
		itemLookup.put("dwoodsapling", new int[]{6,1});
		itemLookup.put("blsapling", new int[]{6,1});
		itemLookup.put("bltreesapling", new int[]{6,1});
		itemLookup.put("bllogsapling", new int[]{6,1});
		itemLookup.put("bltrunksapling", new int[]{6,1});
		itemLookup.put("blwoodsapling", new int[]{6,1});
		itemLookup.put("psapling", new int[]{6,1});
		itemLookup.put("ptreesapling", new int[]{6,1});
		itemLookup.put("plogsapling", new int[]{6,1});
		itemLookup.put("ptrunksapling", new int[]{6,1});
		itemLookup.put("pwoodsapling", new int[]{6,1});
		itemLookup.put("ssapling", new int[]{6,1});
		itemLookup.put("streesapling", new int[]{6,1});
		itemLookup.put("slogsapling", new int[]{6,1});
		itemLookup.put("strunksapling", new int[]{6,1});
		itemLookup.put("swoodsapling", new int[]{6,1});
		itemLookup.put("redsap", new int[]{6,1});
		itemLookup.put("redtreesap", new int[]{6,1});
		itemLookup.put("redlogsap", new int[]{6,1});
		itemLookup.put("redtrunksap", new int[]{6,1});
		itemLookup.put("redwoodsap", new int[]{6,1});
		itemLookup.put("darksap", new int[]{6,1});
		itemLookup.put("darktreesap", new int[]{6,1});
		itemLookup.put("darklogsap", new int[]{6,1});
		itemLookup.put("darktrunksap", new int[]{6,1});
		itemLookup.put("darkwoodsap", new int[]{6,1});
		itemLookup.put("blacksap", new int[]{6,1});
		itemLookup.put("blacktreesap", new int[]{6,1});
		itemLookup.put("blacklogsap", new int[]{6,1});
		itemLookup.put("blacktrunksap", new int[]{6,1});
		itemLookup.put("blackwoodsap", new int[]{6,1});
		itemLookup.put("pinesap", new int[]{6,1});
		itemLookup.put("pinetreesap", new int[]{6,1});
		itemLookup.put("pinelogsap", new int[]{6,1});
		itemLookup.put("pinetrunksap", new int[]{6,1});
		itemLookup.put("pinewoodsap", new int[]{6,1});
		itemLookup.put("sprucesap", new int[]{6,1});
		itemLookup.put("sprucetreesap", new int[]{6,1});
		itemLookup.put("sprucelogsap", new int[]{6,1});
		itemLookup.put("sprucetrunksap", new int[]{6,1});
		itemLookup.put("sprucewoodsap", new int[]{6,1});
		itemLookup.put("rsap", new int[]{6,1});
		itemLookup.put("rtreesap", new int[]{6,1});
		itemLookup.put("rlogsap", new int[]{6,1});
		itemLookup.put("rtrunksap", new int[]{6,1});
		itemLookup.put("rwoodsap", new int[]{6,1});
		itemLookup.put("dsap", new int[]{6,1});
		itemLookup.put("dtreesap", new int[]{6,1});
		itemLookup.put("dlogsap", new int[]{6,1});
		itemLookup.put("dtrunksap", new int[]{6,1});
		itemLookup.put("dwoodsap", new int[]{6,1});
		itemLookup.put("blsap", new int[]{6,1});
		itemLookup.put("bltreesap", new int[]{6,1});
		itemLookup.put("bllogsap", new int[]{6,1});
		itemLookup.put("bltrunksap", new int[]{6,1});
		itemLookup.put("blwoodsap", new int[]{6,1});
		itemLookup.put("psap", new int[]{6,1});
		itemLookup.put("ptreesap", new int[]{6,1});
		itemLookup.put("plogsap", new int[]{6,1});
		itemLookup.put("ptrunksap", new int[]{6,1});
		itemLookup.put("pwoodsap", new int[]{6,1});
		itemLookup.put("ssap", new int[]{6,1});
		itemLookup.put("streesap", new int[]{6,1});
		itemLookup.put("slogsap", new int[]{6,1});
		itemLookup.put("strunksap", new int[]{6,1});
		itemLookup.put("swoodsap", new int[]{6,1});
		itemLookup.put("birchsapling", new int[]{6,2});
		itemLookup.put("birchtreesapling", new int[]{6,2});
		itemLookup.put("birchlogsapling", new int[]{6,2});
		itemLookup.put("birchtrunksapling", new int[]{6,2});
		itemLookup.put("birchwoodsapling", new int[]{6,2});
		itemLookup.put("pandasapling", new int[]{6,2});
		itemLookup.put("pandatreesapling", new int[]{6,2});
		itemLookup.put("pandalogsapling", new int[]{6,2});
		itemLookup.put("pandatrunksapling", new int[]{6,2});
		itemLookup.put("pandawoodsapling", new int[]{6,2});
		itemLookup.put("whitesapling", new int[]{6,2});
		itemLookup.put("whitetreesapling", new int[]{6,2});
		itemLookup.put("whitelogsapling", new int[]{6,2});
		itemLookup.put("whitetrunksapling", new int[]{6,2});
		itemLookup.put("whitewoodsapling", new int[]{6,2});
		itemLookup.put("bisapling", new int[]{6,2});
		itemLookup.put("bitreesapling", new int[]{6,2});
		itemLookup.put("bilogsapling", new int[]{6,2});
		itemLookup.put("bitrunksapling", new int[]{6,2});
		itemLookup.put("biwoodsapling", new int[]{6,2});
		itemLookup.put("bsapling", new int[]{6,2});
		itemLookup.put("btreesapling", new int[]{6,2});
		itemLookup.put("blogsapling", new int[]{6,2});
		itemLookup.put("btrunksapling", new int[]{6,2});
		itemLookup.put("bwoodsapling", new int[]{6,2});
		itemLookup.put("pasapling", new int[]{6,2});
		itemLookup.put("patreesapling", new int[]{6,2});
		itemLookup.put("palogsapling", new int[]{6,2});
		itemLookup.put("patrunksapling", new int[]{6,2});
		itemLookup.put("pawoodsapling", new int[]{6,2});
		itemLookup.put("wsapling", new int[]{6,2});
		itemLookup.put("wtreesapling", new int[]{6,2});
		itemLookup.put("wlogsapling", new int[]{6,2});
		itemLookup.put("wtrunksapling", new int[]{6,2});
		itemLookup.put("wwoodsapling", new int[]{6,2});
		itemLookup.put("birchsap", new int[]{6,2});
		itemLookup.put("birchtreesap", new int[]{6,2});
		itemLookup.put("birchlogsap", new int[]{6,2});
		itemLookup.put("birchtrunksap", new int[]{6,2});
		itemLookup.put("birchwoodsap", new int[]{6,2});
		itemLookup.put("pandasap", new int[]{6,2});
		itemLookup.put("pandatreesap", new int[]{6,2});
		itemLookup.put("pandalogsap", new int[]{6,2});
		itemLookup.put("pandatrunksap", new int[]{6,2});
		itemLookup.put("pandawoodsap", new int[]{6,2});
		itemLookup.put("whitesap", new int[]{6,2});
		itemLookup.put("whitetreesap", new int[]{6,2});
		itemLookup.put("whitelogsap", new int[]{6,2});
		itemLookup.put("whitetrunksap", new int[]{6,2});
		itemLookup.put("whitewoodsap", new int[]{6,2});
		itemLookup.put("bisap", new int[]{6,2});
		itemLookup.put("bitreesap", new int[]{6,2});
		itemLookup.put("bilogsap", new int[]{6,2});
		itemLookup.put("bitrunksap", new int[]{6,2});
		itemLookup.put("biwoodsap", new int[]{6,2});
		itemLookup.put("bsap", new int[]{6,2});
		itemLookup.put("btreesap", new int[]{6,2});
		itemLookup.put("blogsap", new int[]{6,2});
		itemLookup.put("btrunksap", new int[]{6,2});
		itemLookup.put("bwoodsap", new int[]{6,2});
		itemLookup.put("pasap", new int[]{6,2});
		itemLookup.put("patreesap", new int[]{6,2});
		itemLookup.put("palogsap", new int[]{6,2});
		itemLookup.put("patrunksap", new int[]{6,2});
		itemLookup.put("pawoodsap", new int[]{6,2});
		itemLookup.put("wsap", new int[]{6,2});
		itemLookup.put("wtreesap", new int[]{6,2});
		itemLookup.put("wlogsap", new int[]{6,2});
		itemLookup.put("wtrunksap", new int[]{6,2});
		itemLookup.put("wwoodsap", new int[]{6,2});
		itemLookup.put("bedrock", new int[]{7,0});
		itemLookup.put("oprock", new int[]{7,0});
		itemLookup.put("opblock", new int[]{7,0});
		itemLookup.put("adminblock", new int[]{7,0});
		itemLookup.put("adminrock", new int[]{7,0});
		itemLookup.put("adminium", new int[]{7,0});
		itemLookup.put("water", new int[]{8,0});
		itemLookup.put("stationarywater", new int[]{9,0});
		itemLookup.put("swater", new int[]{9,0});
		itemLookup.put("lava", new int[]{10,0});
		itemLookup.put("stationarylava", new int[]{11,0});
		itemLookup.put("slava", new int[]{11,0});
		itemLookup.put("sand", new int[]{12,0});
		itemLookup.put("gravel", new int[]{13,0});
		itemLookup.put("goldore", new int[]{14,0});
		itemLookup.put("oregold", new int[]{14,0});
		itemLookup.put("goldo", new int[]{14,0});
		itemLookup.put("ogold", new int[]{14,0});
		itemLookup.put("gore", new int[]{14,0});
		itemLookup.put("oreg", new int[]{14,0});
		itemLookup.put("ironore", new int[]{15,0});
		itemLookup.put("oreiron", new int[]{15,0});
		itemLookup.put("irono", new int[]{15,0});
		itemLookup.put("oiron", new int[]{15,0});
		itemLookup.put("steelore", new int[]{15,0});
		itemLookup.put("oresteel", new int[]{15,0});
		itemLookup.put("steelo", new int[]{15,0});
		itemLookup.put("osteel", new int[]{15,0});
		itemLookup.put("iore", new int[]{15,0});
		itemLookup.put("orei", new int[]{15,0});
		itemLookup.put("sore", new int[]{15,0});
		itemLookup.put("ores", new int[]{15,0});
		itemLookup.put("coalore", new int[]{16,0});
		itemLookup.put("orecoal", new int[]{16,0});
		itemLookup.put("coalo", new int[]{16,0});
		itemLookup.put("ocoal", new int[]{16,0});
		itemLookup.put("core", new int[]{16,0});
		itemLookup.put("tree", new int[]{17,0});
		itemLookup.put("log", new int[]{17,0});
		itemLookup.put("trunk", new int[]{17,0});
		itemLookup.put("wood", new int[]{17,0});
		itemLookup.put("oaktree", new int[]{17,0});
		itemLookup.put("oaklog", new int[]{17,0});
		itemLookup.put("oaktrunk", new int[]{17,0});
		itemLookup.put("oakwood", new int[]{17,0});
		itemLookup.put("normaltree", new int[]{17,0});
		itemLookup.put("normallog", new int[]{17,0});
		itemLookup.put("normaltrunk", new int[]{17,0});
		itemLookup.put("normalwood", new int[]{17,0});
		itemLookup.put("otree", new int[]{17,0});
		itemLookup.put("olog", new int[]{17,0});
		itemLookup.put("otrunk", new int[]{17,0});
		itemLookup.put("owood", new int[]{17,0});
		itemLookup.put("ntree", new int[]{17,0});
		itemLookup.put("nlog", new int[]{17,0});
		itemLookup.put("ntrunk", new int[]{17,0});
		itemLookup.put("nwood", new int[]{17,0});
		itemLookup.put("redtree", new int[]{17,1});
		itemLookup.put("redlog", new int[]{17,1});
		itemLookup.put("redtrunk", new int[]{17,1});
		itemLookup.put("redwood", new int[]{17,1});
		itemLookup.put("darktree", new int[]{17,1});
		itemLookup.put("darklog", new int[]{17,1});
		itemLookup.put("darktrunk", new int[]{17,1});
		itemLookup.put("darkwood", new int[]{17,1});
		itemLookup.put("blacktree", new int[]{17,1});
		itemLookup.put("blacklog", new int[]{17,1});
		itemLookup.put("blacktrunk", new int[]{17,1});
		itemLookup.put("blackwood", new int[]{17,1});
		itemLookup.put("pine", new int[]{17,1});
		itemLookup.put("pinetree", new int[]{17,1});
		itemLookup.put("pinelog", new int[]{17,1});
		itemLookup.put("pinetrunk", new int[]{17,1});
		itemLookup.put("pinewood", new int[]{17,1});
		itemLookup.put("spruce", new int[]{17,1});
		itemLookup.put("sprucetree", new int[]{17,1});
		itemLookup.put("sprucelog", new int[]{17,1});
		itemLookup.put("sprucetrunk", new int[]{17,1});
		itemLookup.put("sprucewood", new int[]{17,1});
		itemLookup.put("rtree", new int[]{17,1});
		itemLookup.put("rlog", new int[]{17,1});
		itemLookup.put("rtrunk", new int[]{17,1});
		itemLookup.put("rwood", new int[]{17,1});
		itemLookup.put("dtree", new int[]{17,1});
		itemLookup.put("dlog", new int[]{17,1});
		itemLookup.put("dtrunk", new int[]{17,1});
		itemLookup.put("dwood", new int[]{17,1});
		itemLookup.put("bltree", new int[]{17,1});
		itemLookup.put("bllog", new int[]{17,1});
		itemLookup.put("bltrunk", new int[]{17,1});
		itemLookup.put("blwood", new int[]{17,1});
		itemLookup.put("ptree", new int[]{17,1});
		itemLookup.put("plog", new int[]{17,1});
		itemLookup.put("ptrunk", new int[]{17,1});
		itemLookup.put("pwood", new int[]{17,1});
		itemLookup.put("stree", new int[]{17,1});
		itemLookup.put("slog", new int[]{17,1});
		itemLookup.put("strunk", new int[]{17,1});
		itemLookup.put("swood", new int[]{17,1});
		itemLookup.put("birch", new int[]{17,2});
		itemLookup.put("birchtree", new int[]{17,2});
		itemLookup.put("birchlog", new int[]{17,2});
		itemLookup.put("birchtrunk", new int[]{17,2});
		itemLookup.put("birchwood", new int[]{17,2});
		itemLookup.put("pandatree", new int[]{17,2});
		itemLookup.put("pandalog", new int[]{17,2});
		itemLookup.put("pandatrunk", new int[]{17,2});
		itemLookup.put("pandawood", new int[]{17,2});
		itemLookup.put("whitetree", new int[]{17,2});
		itemLookup.put("whitelog", new int[]{17,2});
		itemLookup.put("whitetrunk", new int[]{17,2});
		itemLookup.put("whitewood", new int[]{17,2});
		itemLookup.put("bitree", new int[]{17,2});
		itemLookup.put("bilog", new int[]{17,2});
		itemLookup.put("bitrunk", new int[]{17,2});
		itemLookup.put("biwood", new int[]{17,2});
		itemLookup.put("btree", new int[]{17,2});
		itemLookup.put("blog", new int[]{17,2});
		itemLookup.put("btrunk", new int[]{17,2});
		itemLookup.put("bwood", new int[]{17,2});
		itemLookup.put("patree", new int[]{17,2});
		itemLookup.put("palog", new int[]{17,2});
		itemLookup.put("patrunk", new int[]{17,2});
		itemLookup.put("pawood", new int[]{17,2});
		itemLookup.put("wtree", new int[]{17,2});
		itemLookup.put("wlog", new int[]{17,2});
		itemLookup.put("wtrunk", new int[]{17,2});
		itemLookup.put("wwood", new int[]{17,2});
		itemLookup.put("leaves", new int[]{18,0});
		itemLookup.put("leaf", new int[]{18,0});
		itemLookup.put("treeleaves", new int[]{18,0});
		itemLookup.put("logleaves", new int[]{18,0});
		itemLookup.put("trunkleaves", new int[]{18,0});
		itemLookup.put("woodleaves", new int[]{18,0});
		itemLookup.put("oaktreeleaves", new int[]{18,0});
		itemLookup.put("oaklogleaves", new int[]{18,0});
		itemLookup.put("oaktrunkleaves", new int[]{18,0});
		itemLookup.put("oakwoodleaves", new int[]{18,0});
		itemLookup.put("normaltreeleaves", new int[]{18,0});
		itemLookup.put("normallogleaves", new int[]{18,0});
		itemLookup.put("normaltrunkleaves", new int[]{18,0});
		itemLookup.put("normalwoodleaves", new int[]{18,0});
		itemLookup.put("otreeleaves", new int[]{18,0});
		itemLookup.put("ologleaves", new int[]{18,0});
		itemLookup.put("otrunkleaves", new int[]{18,0});
		itemLookup.put("owoodleaves", new int[]{18,0});
		itemLookup.put("ntreeleaves", new int[]{18,0});
		itemLookup.put("nlogleaves", new int[]{18,0});
		itemLookup.put("ntrunkleaves", new int[]{18,0});
		itemLookup.put("nwoodleaves", new int[]{18,0});
		itemLookup.put("treeleaf", new int[]{18,0});
		itemLookup.put("logleaf", new int[]{18,0});
		itemLookup.put("trunkleaf", new int[]{18,0});
		itemLookup.put("woodleaf", new int[]{18,0});
		itemLookup.put("oaktreeleaf", new int[]{18,0});
		itemLookup.put("oaklogleaf", new int[]{18,0});
		itemLookup.put("oaktrunkleaf", new int[]{18,0});
		itemLookup.put("oakwoodleaf", new int[]{18,0});
		itemLookup.put("normaltreeleaf", new int[]{18,0});
		itemLookup.put("normallogleaf", new int[]{18,0});
		itemLookup.put("normaltrunkleaf", new int[]{18,0});
		itemLookup.put("normalwoodleaf", new int[]{18,0});
		itemLookup.put("otreeleaf", new int[]{18,0});
		itemLookup.put("ologleaf", new int[]{18,0});
		itemLookup.put("otrunkleaf", new int[]{18,0});
		itemLookup.put("owoodleaf", new int[]{18,0});
		itemLookup.put("ntreeleaf", new int[]{18,0});
		itemLookup.put("nlogleaf", new int[]{18,0});
		itemLookup.put("ntrunkleaf", new int[]{18,0});
		itemLookup.put("nwoodleaf", new int[]{18,0});
		itemLookup.put("sponge", new int[]{19,0});
		itemLookup.put("glass", new int[]{20,0});
		itemLookup.put("lapislazuliore", new int[]{21,0});
		itemLookup.put("lapislazulio", new int[]{21,0});
		itemLookup.put("orelapislazuli", new int[]{21,0});
		itemLookup.put("olapislazuli", new int[]{21,0});
		itemLookup.put("lapisore", new int[]{21,0});
		itemLookup.put("lapiso", new int[]{21,0});
		itemLookup.put("orelapis", new int[]{21,0});
		itemLookup.put("olapis", new int[]{21,0});
		itemLookup.put("lore", new int[]{21,0});
		itemLookup.put("orel", new int[]{21,0});
		itemLookup.put("lapislazuliblock", new int[]{22,0});
		itemLookup.put("blocklapislazuli", new int[]{22,0});
		itemLookup.put("lapisblock", new int[]{22,0});
		itemLookup.put("blocklapis", new int[]{22,0});
		itemLookup.put("lblock", new int[]{22,0});
		itemLookup.put("blockl", new int[]{22,9});
		itemLookup.put("dispenser", new int[]{23,0});
		itemLookup.put("dispense", new int[]{23,0});
		itemLookup.put("sandstone", new int[]{24,0});
		itemLookup.put("sastone", new int[]{24,0});
		itemLookup.put("noteblock", new int[]{25,0});
		itemLookup.put("musicblock", new int[]{25,0});
		itemLookup.put("nblock", new int[]{25,0});
		itemLookup.put("mblock", new int[]{25,0});
		itemLookup.put("bedblock", new int[]{26,0});
		itemLookup.put("poweredtrack", new int[]{27,0});
		itemLookup.put("poweredrail", new int[]{27,0});
		itemLookup.put("boostertrack", new int[]{27,0});
		itemLookup.put("boosterrail", new int[]{27,0});
		itemLookup.put("powertrack", new int[]{27,0});
		itemLookup.put("powerrail", new int[]{27,0});
		itemLookup.put("boosttrack", new int[]{27,0});
		itemLookup.put("boostrail", new int[]{27,0});
		itemLookup.put("ptrack", new int[]{27,0});
		itemLookup.put("prail", new int[]{27,0});
		itemLookup.put("btrack", new int[]{27,0});
		itemLookup.put("brail", new int[]{27,0});
		itemLookup.put("detectortrack", new int[]{28,0});
		itemLookup.put("detectorrail", new int[]{28,0});
		itemLookup.put("detectingtrack", new int[]{28,0});
		itemLookup.put("detectingrail", new int[]{28,0});
		itemLookup.put("detecttrack", new int[]{28,0});
		itemLookup.put("detectrail", new int[]{28,0});
		itemLookup.put("dtrack", new int[]{28,0});
		itemLookup.put("drail", new int[]{28,0});
		itemLookup.put("stickypiston", new int[]{29,0});
		itemLookup.put("spiderweb", new int[]{30,0});
		itemLookup.put("sweb", new int[]{30,0});
		itemLookup.put("web", new int[]{30,0});
		itemLookup.put("longgrass", new int[]{31,0});
		itemLookup.put("tallgrass", new int[]{31,0});
		itemLookup.put("grasslong", new int[]{31,0});
		itemLookup.put("grasstall", new int[]{31,0});
		itemLookup.put("lgrass", new int[]{31,0});
		itemLookup.put("tgrass", new int[]{31,0});
		itemLookup.put("grassl", new int[]{31,0});
		itemLookup.put("grasst", new int[]{31,0});
		itemLookup.put("deadshrub", new int[]{32,0});
		itemLookup.put("shrubdead", new int[]{32,0});
		itemLookup.put("dshrub", new int[]{32,0});
		itemLookup.put("shrubd", new int[]{32,0});
		itemLookup.put("piston", new int[]{33,0});
		itemLookup.put("whitecloth", new int[]{35,0});
		itemLookup.put("whitewool", new int[]{35,0});
		itemLookup.put("whitecotton", new int[]{35,0});
		itemLookup.put("wcloth", new int[]{35,0});
		itemLookup.put("wwool", new int[]{35,0});
		itemLookup.put("wcotton", new int[]{35,0});
		itemLookup.put("cloth", new int[]{35,0});
		itemLookup.put("wool", new int[]{35,0});
		itemLookup.put("cotton", new int[]{35,0});
		itemLookup.put("orangecloth", new int[]{35,1});
		itemLookup.put("orangewool", new int[]{35,1});
		itemLookup.put("orangecotton", new int[]{35,1});
		itemLookup.put("ocloth", new int[]{35,1});
		itemLookup.put("owool", new int[]{35,1});
		itemLookup.put("ocotton", new int[]{35,1});
		itemLookup.put("magentacloth", new int[]{35,2});
		itemLookup.put("magentawool", new int[]{35,2});
		itemLookup.put("magentacotton", new int[]{35,2});
		itemLookup.put("mcloth", new int[]{35,2});
		itemLookup.put("mwool", new int[]{35,2});
		itemLookup.put("mcotton", new int[]{35,2});
		itemLookup.put("lightbluecloth", new int[]{35,3});
		itemLookup.put("lightbluewool", new int[]{35,3});
		itemLookup.put("lightbluecotton", new int[]{35,3});
		itemLookup.put("lbluecloth", new int[]{35,3});
		itemLookup.put("lbluewool", new int[]{35,3});
		itemLookup.put("lbluecotton", new int[]{35,3});
		itemLookup.put("lightblucloth", new int[]{35,3});
		itemLookup.put("lightbluwool", new int[]{35,3});
		itemLookup.put("lightblucotton", new int[]{35,3});
		itemLookup.put("lblucloth", new int[]{35,3});
		itemLookup.put("lbluwool", new int[]{35,3});
		itemLookup.put("lblucotton", new int[]{35,3});
		itemLookup.put("yellowcloth", new int[]{35,4});
		itemLookup.put("yellowwool", new int[]{35,4});
		itemLookup.put("yellowcotton", new int[]{35,4});
		itemLookup.put("ycloth", new int[]{35,4});
		itemLookup.put("ywool", new int[]{35,4});
		itemLookup.put("ycotton", new int[]{35,4});
		itemLookup.put("lightgreencloth", new int[]{35,5});
		itemLookup.put("lightgreenwool", new int[]{35,5});
		itemLookup.put("lightgreencotton", new int[]{35,5});
		itemLookup.put("lgreencloth", new int[]{35,5});
		itemLookup.put("lgreenwool", new int[]{35,5});
		itemLookup.put("lgreencotton", new int[]{35,5});
		itemLookup.put("lightgrecloth", new int[]{35,5});
		itemLookup.put("lightgrewool", new int[]{35,5});
		itemLookup.put("lightgrecotton", new int[]{35,5});
		itemLookup.put("lgrecloth", new int[]{35,5});
		itemLookup.put("lgrewool", new int[]{35,5});
		itemLookup.put("lgrecotton", new int[]{35,5});
		itemLookup.put("pinkcloth", new int[]{35,6});
		itemLookup.put("pinkwool", new int[]{35,6});
		itemLookup.put("pinkcotton", new int[]{35,6});
		itemLookup.put("picloth", new int[]{35,6});
		itemLookup.put("piwool", new int[]{35,6});
		itemLookup.put("picotton", new int[]{35,6});
		itemLookup.put("graycloth", new int[]{35,7});
		itemLookup.put("graywool", new int[]{35,7});
		itemLookup.put("graycotton", new int[]{35,7});
		itemLookup.put("gracloth", new int[]{35,7});
		itemLookup.put("grawool", new int[]{35,7});
		itemLookup.put("gracotton", new int[]{35,7});
		itemLookup.put("lightgraycloth", new int[]{35,8});
		itemLookup.put("lightgraywool", new int[]{35,8});
		itemLookup.put("lightgraycotton", new int[]{35,8});
		itemLookup.put("lgraycloth", new int[]{35,8});
		itemLookup.put("lgraywool", new int[]{35,8});
		itemLookup.put("lgraycotton", new int[]{35,8});
		itemLookup.put("lightgracloth", new int[]{35,8});
		itemLookup.put("lightgrawool", new int[]{35,8});
		itemLookup.put("lightgracotton", new int[]{35,8});
		itemLookup.put("lgracloth", new int[]{35,8});
		itemLookup.put("lgrawool", new int[]{35,8});
		itemLookup.put("lgracotton", new int[]{35,8});
		itemLookup.put("cyancloth", new int[]{35,9});
		itemLookup.put("cyanwool", new int[]{35,9});
		itemLookup.put("cyancotton", new int[]{35,9});
		itemLookup.put("ccloth", new int[]{35,9});
		itemLookup.put("cwool", new int[]{35,9});
		itemLookup.put("ccotton", new int[]{35,9});
		itemLookup.put("purplecloth", new int[]{35,10});
		itemLookup.put("purplewool", new int[]{35,10});
		itemLookup.put("purplecotton", new int[]{35,10});
		itemLookup.put("pucloth", new int[]{35,10});
		itemLookup.put("puwool", new int[]{35,10});
		itemLookup.put("pucotton", new int[]{35,10});
		itemLookup.put("bluecloth", new int[]{35,11});
		itemLookup.put("bluewool", new int[]{35,11});
		itemLookup.put("bluecotton", new int[]{35,11});
		itemLookup.put("blucloth", new int[]{35,11});
		itemLookup.put("bluwool", new int[]{35,11});
		itemLookup.put("blucotton", new int[]{35,11});
		itemLookup.put("browncloth", new int[]{35,12});
		itemLookup.put("brownwool", new int[]{35,12});
		itemLookup.put("browncotton", new int[]{35,12});
		itemLookup.put("brocloth", new int[]{35,12});
		itemLookup.put("browool", new int[]{35,12});
		itemLookup.put("brocotton", new int[]{35,12});
		itemLookup.put("darkgreencloth", new int[]{35,13});
		itemLookup.put("darkgreenwool", new int[]{35,13});
		itemLookup.put("darkgreencotton", new int[]{35,13});
		itemLookup.put("dgreencloth", new int[]{35,13});
		itemLookup.put("dgreenwool", new int[]{35,13});
		itemLookup.put("dgreencotton", new int[]{35,13});
		itemLookup.put("greencloth", new int[]{35,13});
		itemLookup.put("greenwool", new int[]{35,13});
		itemLookup.put("greencotton", new int[]{35,13});
		itemLookup.put("darkgrecloth", new int[]{35,13});
		itemLookup.put("darkgrewool", new int[]{35,13});
		itemLookup.put("darkgrecotton", new int[]{35,13});
		itemLookup.put("dgrecloth", new int[]{35,13});
		itemLookup.put("dgrewool", new int[]{35,13});
		itemLookup.put("dgrecotton", new int[]{35,13});
		itemLookup.put("grecloth", new int[]{35,13});
		itemLookup.put("grewool", new int[]{35,13});
		itemLookup.put("grecotton", new int[]{35,13});
		itemLookup.put("redcloth", new int[]{35,14});
		itemLookup.put("redwool", new int[]{35,14});
		itemLookup.put("redcotton", new int[]{35,14});
		itemLookup.put("rcloth", new int[]{35,14});
		itemLookup.put("rwool", new int[]{35,14});
		itemLookup.put("rcotton", new int[]{35,14});
		itemLookup.put("blackcloth", new int[]{35,15});
		itemLookup.put("blackwool", new int[]{35,15});
		itemLookup.put("blackcotton", new int[]{35,15});
		itemLookup.put("blacloth", new int[]{35,15});
		itemLookup.put("blawool", new int[]{35,15});
		itemLookup.put("blacotton", new int[]{35,15});
		itemLookup.put("yellowflower", new int[]{37,0});
		itemLookup.put("yflower", new int[]{37,0});
		itemLookup.put("flower", new int[]{37,0});
		itemLookup.put("redrose", new int[]{38,0});
		itemLookup.put("rrose", new int[]{38,0});
		itemLookup.put("rose", new int[]{38,0});
		itemLookup.put("redflower", new int[]{38,0});
		itemLookup.put("rflower", new int[]{38,0});
		itemLookup.put("brownmushroom", new int[]{39,0});
		itemLookup.put("brownmush", new int[]{39,0});
		itemLookup.put("bmushroom", new int[]{39,0});
		itemLookup.put("bmush", new int[]{39,0});
		itemLookup.put("redmushroom", new int[]{40,0});
		itemLookup.put("redmush", new int[]{40,0});
		itemLookup.put("rmushroom", new int[]{40,0});
		itemLookup.put("rmush", new int[]{40,0});
		itemLookup.put("goldblock", new int[]{41,0});
		itemLookup.put("blockgold", new int[]{41,0});
		itemLookup.put("gblock", new int[]{41,0});
		itemLookup.put("blockg", new int[]{41,0});
		itemLookup.put("ironblock", new int[]{42,0});
		itemLookup.put("steelblock", new int[]{42,0});
		itemLookup.put("blockiron", new int[]{42,0});
		itemLookup.put("blocksteel", new int[]{42,0});
		itemLookup.put("iblock", new int[]{42,0});
		itemLookup.put("stblock", new int[]{42,0});
		itemLookup.put("blocki", new int[]{42,0});
		itemLookup.put("blockst", new int[]{42,0});
		itemLookup.put("smoothstonedoublestep", new int[]{43,0});
		itemLookup.put("smoothstonedstep", new int[]{43,0});
		itemLookup.put("stonedoublestep", new int[]{43,0});
		itemLookup.put("stonedstep", new int[]{43,0});
		itemLookup.put("sdoublestep", new int[]{43,0});
		itemLookup.put("sdstep", new int[]{43,0});
		itemLookup.put("doublesmoothstonestep", new int[]{43,0});
		itemLookup.put("dsmoothstonestep", new int[]{43,0});
		itemLookup.put("doublestonestep", new int[]{43,0});
		itemLookup.put("dstonestep", new int[]{43,0});
		itemLookup.put("doublesstep", new int[]{43,0});
		itemLookup.put("doublestep", new int[]{43,0});
		itemLookup.put("dstep", new int[]{43,0});
		itemLookup.put("smoothstonedoubleslab", new int[]{43,0});
		itemLookup.put("smoothstonedslab", new int[]{43,0});
		itemLookup.put("stonedoubleslab", new int[]{43,0});
		itemLookup.put("stonedslab", new int[]{43,0});
		itemLookup.put("sdoubleslab", new int[]{43,0});
		itemLookup.put("sdslab", new int[]{43,0});
		itemLookup.put("doublesmoothstoneslab", new int[]{43,0});
		itemLookup.put("dsmoothstoneslab", new int[]{43,0});
		itemLookup.put("doublestoneslab", new int[]{43,0});
		itemLookup.put("dstoneslab", new int[]{43,0});
		itemLookup.put("doublesslab", new int[]{43,0});
		itemLookup.put("doubleslab", new int[]{43,0});
		itemLookup.put("dslab", new int[]{43,0});
		itemLookup.put("sandstonedoublestep", new int[]{43,1});
		itemLookup.put("sandstonedstep", new int[]{43,1});
		itemLookup.put("sstonedoublestep", new int[]{43,1});
		itemLookup.put("sstonedstep", new int[]{43,1});
		itemLookup.put("ssdoublestep", new int[]{43,1});
		itemLookup.put("ssdstep", new int[]{43,1});
		itemLookup.put("doublesandstonestep", new int[]{43,1});
		itemLookup.put("dsandstonestep", new int[]{43,1});
		itemLookup.put("doublesstonestep", new int[]{43,1});
		itemLookup.put("dsstonestep", new int[]{43,1});
		itemLookup.put("doublessstep", new int[]{43,1});
		itemLookup.put("dsstep", new int[]{43,1});
		itemLookup.put("sandstonedoubleslab", new int[]{43,1});
		itemLookup.put("sandstonedslab", new int[]{43,1});
		itemLookup.put("sstonedoubleslab", new int[]{43,1});
		itemLookup.put("sstonedslab", new int[]{43,1});
		itemLookup.put("ssdoubleslab", new int[]{43,1});
		itemLookup.put("ssdslab", new int[]{43,1});
		itemLookup.put("doublesandstoneslab", new int[]{43,1});
		itemLookup.put("dsandstoneslab", new int[]{43,1});
		itemLookup.put("doublesstoneslab", new int[]{43,1});
		itemLookup.put("dsstoneslab", new int[]{43,1});
		itemLookup.put("doublessslab", new int[]{43,1});
		itemLookup.put("dsslab", new int[]{43,1});
		itemLookup.put("woodenplankdoublestep", new int[]{43,2});
		itemLookup.put("woodenplankdstep", new int[]{43,2});
		itemLookup.put("woodplankdoublestep", new int[]{43,2});
		itemLookup.put("woodplankdstep", new int[]{43,2});
		itemLookup.put("wplankdoublestep", new int[]{43,2});
		itemLookup.put("wplankdstep", new int[]{43,2});
		itemLookup.put("plankdoublestep", new int[]{43,2});
		itemLookup.put("plankdstep", new int[]{43,2});
		itemLookup.put("wpdoublestep", new int[]{43,2});
		itemLookup.put("pdoublestep", new int[]{43,2});
		itemLookup.put("wpdstep", new int[]{43,2});
		itemLookup.put("pdstep", new int[]{43,2});
		itemLookup.put("doublewoodenplankstep", new int[]{43,2});
		itemLookup.put("dwoodenplankstep", new int[]{43,2});
		itemLookup.put("doublewoodplankstep", new int[]{43,2});
		itemLookup.put("dwoodplankstep", new int[]{43,2});
		itemLookup.put("doublewplankstep", new int[]{43,2});
		itemLookup.put("dwplankstep", new int[]{43,2});
		itemLookup.put("doubleplankstep", new int[]{43,2});
		itemLookup.put("dplankstep", new int[]{43,2});
		itemLookup.put("doublewpstep", new int[]{43,2});
		itemLookup.put("dwpstep", new int[]{43,2});
		itemLookup.put("doublepstep", new int[]{43,2});
		itemLookup.put("dpstep", new int[]{43,2});
		itemLookup.put("woodenplankdoubleslab", new int[]{43,2});
		itemLookup.put("woodenplankdslab", new int[]{43,2});
		itemLookup.put("woodplankdoubleslab", new int[]{43,2});
		itemLookup.put("woodplankdslab", new int[]{43,2});
		itemLookup.put("wplankdoubleslab", new int[]{43,2});
		itemLookup.put("wplankdslab", new int[]{43,2});
		itemLookup.put("plankdoubleslab", new int[]{43,2});
		itemLookup.put("plankdslab", new int[]{43,2});
		itemLookup.put("wpdoubleslab", new int[]{43,2});
		itemLookup.put("pdoubleslab", new int[]{43,2});
		itemLookup.put("wpdslab", new int[]{43,2});
		itemLookup.put("pdslab", new int[]{43,2});
		itemLookup.put("doublewoodenplankslab", new int[]{43,2});
		itemLookup.put("dwoodenplankslab", new int[]{43,2});
		itemLookup.put("doublewoodplankslab", new int[]{43,2});
		itemLookup.put("dwoodplankslab", new int[]{43,2});
		itemLookup.put("doublewplankslab", new int[]{43,2});
		itemLookup.put("dwplankslab", new int[]{43,2});
		itemLookup.put("doubleplankslab", new int[]{43,2});
		itemLookup.put("dplankslab", new int[]{43,2});
		itemLookup.put("doublewpslab", new int[]{43,2});
		itemLookup.put("dwpslab", new int[]{43,2});
		itemLookup.put("doublepslab", new int[]{43,2});
		itemLookup.put("dpslab", new int[]{43,2});
		itemLookup.put("cobblestonedoublestep", new int[]{43,3});
		itemLookup.put("cobblestonedstep", new int[]{43,3});
		itemLookup.put("cobbledoublestep", new int[]{43,3});
		itemLookup.put("cobbledstep", new int[]{43,3});
		itemLookup.put("cstonedoublestep", new int[]{43,3});
		itemLookup.put("cstonedstep", new int[]{43,3});
		itemLookup.put("csdoublestep", new int[]{43,3});
		itemLookup.put("csdstep", new int[]{43,3});
		itemLookup.put("doublecobblestonestep", new int[]{43,3});
		itemLookup.put("dcobblestonestep", new int[]{43,3});
		itemLookup.put("doublecobblestep", new int[]{43,3});
		itemLookup.put("dcobblestep", new int[]{43,3});
		itemLookup.put("doublecstonestep", new int[]{43,3});
		itemLookup.put("dcstonestep", new int[]{43,3});
		itemLookup.put("doublecsstep", new int[]{43,3});
		itemLookup.put("dcsstep", new int[]{43,3});
		itemLookup.put("cobblestonedoubleslab", new int[]{43,3});
		itemLookup.put("cobblestonedslab", new int[]{43,3});
		itemLookup.put("cobbledoubleslab", new int[]{43,3});
		itemLookup.put("cobbledslab", new int[]{43,3});
		itemLookup.put("cstonedoubleslab", new int[]{43,3});
		itemLookup.put("cstonedslab", new int[]{43,3});
		itemLookup.put("csdoubleslab", new int[]{43,3});
		itemLookup.put("csdslab", new int[]{43,3});
		itemLookup.put("doublecobblestoneslab", new int[]{43,3});
		itemLookup.put("dcobblestoneslab", new int[]{43,3});
		itemLookup.put("doublecobbleslab", new int[]{43,3});
		itemLookup.put("dcobbleslab", new int[]{43,3});
		itemLookup.put("doublecstoneslab", new int[]{43,3});
		itemLookup.put("dcstoneslab", new int[]{43,3});
		itemLookup.put("doublecsslab", new int[]{43,3});
		itemLookup.put("dcsslab", new int[]{43,3});
		itemLookup.put("smoothstonestep", new int[]{44,0});
		itemLookup.put("stonestep", new int[]{44,0});
		itemLookup.put("sstep", new int[]{44,0});
		itemLookup.put("step", new int[]{44,0});
		itemLookup.put("smoothstoneslab", new int[]{44,0});
		itemLookup.put("stoneslab", new int[]{44,0});
		itemLookup.put("sslab", new int[]{44,0});
		itemLookup.put("slab", new int[]{44,0});
		itemLookup.put("sanddstonestep", new int[]{44,1});
		itemLookup.put("sstonestep", new int[]{44,1});
		itemLookup.put("ssstep", new int[]{44,1});
		itemLookup.put("sanddstoneslab", new int[]{44,1});
		itemLookup.put("sstoneslab", new int[]{44,1});
		itemLookup.put("ssslab", new int[]{44,1});
		itemLookup.put("woodplankstep", new int[]{44,2});
		itemLookup.put("wplankstep", new int[]{44,2});
		itemLookup.put("plankstep", new int[]{44,2});
		itemLookup.put("wpstep", new int[]{44,2});
		itemLookup.put("pstep", new int[]{44,2});
		itemLookup.put("woodplankslab", new int[]{44,2});
		itemLookup.put("wplankslab", new int[]{44,2});
		itemLookup.put("plankslab", new int[]{44,2});
		itemLookup.put("wpslab", new int[]{44,2});
		itemLookup.put("pslab", new int[]{44,2});
		itemLookup.put("cobblestonestep", new int[]{44,3});
		itemLookup.put("cobblestep", new int[]{44,3});
		itemLookup.put("cstonestep", new int[]{44,3});
		itemLookup.put("csstep", new int[]{44,3});
		itemLookup.put("cobblestoneslab", new int[]{44,3});
		itemLookup.put("cobbleslab", new int[]{44,3});
		itemLookup.put("cstoneslab", new int[]{44,3});
		itemLookup.put("csslab", new int[]{44,3});
		itemLookup.put("brickblock", new int[]{45,0});
		itemLookup.put("blockbrick", new int[]{45,0});
		itemLookup.put("bblock", new int[]{45,0});
		itemLookup.put("blockb", new int[]{45,0});
		itemLookup.put("tntblock", new int[]{46,0});
		itemLookup.put("blocktnt", new int[]{46,0});
		itemLookup.put("bombblock", new int[]{46,0});
		itemLookup.put("blockbomb", new int[]{46,0});
		itemLookup.put("dynamiteblock", new int[]{46,0});
		itemLookup.put("blockdynamite", new int[]{46,0});
		itemLookup.put("tnt", new int[]{46,0});
		itemLookup.put("bomb", new int[]{46,0});
		itemLookup.put("dynamite", new int[]{46,0});
		itemLookup.put("bookshelf", new int[]{47,0});
		itemLookup.put("shelfbook", new int[]{47,0});
		itemLookup.put("bookblock", new int[]{47,0});
		itemLookup.put("blockbook", new int[]{47,0});
		itemLookup.put("mossycobblestone", new int[]{48,0});
		itemLookup.put("mosscobblestone", new int[]{48,0});
		itemLookup.put("mcobblestone", new int[]{48,0});
		itemLookup.put("mossycobble", new int[]{48,0});
		itemLookup.put("mosscobble", new int[]{48,0});
		itemLookup.put("mcobble", new int[]{48,0});
		itemLookup.put("obsidian", new int[]{49,0});
		itemLookup.put("obsi", new int[]{49,0});
		itemLookup.put("obby", new int[]{49,0});
		itemLookup.put("torch", new int[]{50,0});
		itemLookup.put("fire", new int[]{51,0});
		itemLookup.put("flame", new int[]{51,0});
		itemLookup.put("flames", new int[]{51,0});
		itemLookup.put("mobspawner", new int[]{52,0});
		itemLookup.put("monsterspawner", new int[]{52,0});
		itemLookup.put("mspawner", new int[]{52,0});
		itemLookup.put("spawner", new int[]{52,0});
		itemLookup.put("woodenstairs", new int[]{53,0});
		itemLookup.put("woodstairs", new int[]{53,0});
		itemLookup.put("wstairs", new int[]{53,0});
		itemLookup.put("woodenstair", new int[]{53,0});
		itemLookup.put("woodstair", new int[]{53,0});
		itemLookup.put("wstair", new int[]{53,0});
		itemLookup.put("stairswooden", new int[]{53,0});
		itemLookup.put("stairswood", new int[]{53,0});
		itemLookup.put("stairsw", new int[]{53,0});
		itemLookup.put("stairwooden", new int[]{53,0});
		itemLookup.put("stairwood", new int[]{53,0});
		itemLookup.put("stairw", new int[]{53,0});
		itemLookup.put("chest", new int[]{54,0});
		itemLookup.put("redstonewireblock", new int[]{55,0});
		itemLookup.put("rstonewireblock", new int[]{55,0});
		itemLookup.put("redswireblock", new int[]{55,0});
		itemLookup.put("redwireblock", new int[]{55,0});
		itemLookup.put("rswireblock", new int[]{55,0});
		itemLookup.put("rwireblock", new int[]{55,0});
		itemLookup.put("redstonewire", new int[]{55,0});
		itemLookup.put("rstonewire", new int[]{55,0});
		itemLookup.put("redswire", new int[]{55,0});
		itemLookup.put("redwire", new int[]{55,0});
		itemLookup.put("rswire", new int[]{55,0});
		itemLookup.put("rwire", new int[]{55,0});
		itemLookup.put("wire", new int[]{55,0});
		itemLookup.put("diamondore", new int[]{56,0});
		itemLookup.put("crystalore", new int[]{56,0});
		itemLookup.put("diamondo", new int[]{56,0});
		itemLookup.put("crystalo", new int[]{56,0});
		itemLookup.put("orediamond", new int[]{56,0});
		itemLookup.put("odiamond", new int[]{56,0});
		itemLookup.put("orecrystal", new int[]{56,0});
		itemLookup.put("ocrystal", new int[]{56,0});
		itemLookup.put("dore", new int[]{56,0});
		itemLookup.put("ored", new int[]{56,0});
		itemLookup.put("diamondblock", new int[]{57,0});
		itemLookup.put("blockdiamond", new int[]{57,0});
		itemLookup.put("crystalblock", new int[]{57,0});
		itemLookup.put("blockcrystal", new int[]{57,0});
		itemLookup.put("dblock", new int[]{57,0});
		itemLookup.put("blockd", new int[]{57,0});
		itemLookup.put("workbench", new int[]{58,0});
		itemLookup.put("craftingbench", new int[]{58,0});
		itemLookup.put("crafterbench", new int[]{58,0});
		itemLookup.put("craftbench", new int[]{58,0});
		itemLookup.put("worktable", new int[]{58,0});
		itemLookup.put("craftingtable", new int[]{58,0});
		itemLookup.put("craftertable", new int[]{58,0});
		itemLookup.put("crafttable", new int[]{58,0});
		itemLookup.put("wbench", new int[]{58,0});
		itemLookup.put("cbench", new int[]{58,0});
		itemLookup.put("crops", new int[]{59,0});
		itemLookup.put("crop", new int[]{59,0});
		itemLookup.put("soil", new int[]{60,0});
		itemLookup.put("furnace", new int[]{61,0});
		itemLookup.put("burningfurnace", new int[]{62,0});
		itemLookup.put("bfurnace", new int[]{62,0});
		itemLookup.put("signpost", new int[]{63,0});
		itemLookup.put("spost", new int[]{63,0});
		itemLookup.put("woodendoorhalf", new int[]{64,0});
		itemLookup.put("wooddoorhalf", new int[]{64,0});
		itemLookup.put("wdoorhalf", new int[]{64,0});
		itemLookup.put("woodendoorbottom", new int[]{64,0});
		itemLookup.put("wooddoorbottom", new int[]{64,0});
		itemLookup.put("wdoorbottom", new int[]{64,0});
		itemLookup.put("woodendoorblock", new int[]{64,0});
		itemLookup.put("wooddoorblock", new int[]{64,0});
		itemLookup.put("wdoorblock", new int[]{64,0});
		itemLookup.put("ladder", new int[]{65,0});
		itemLookup.put("minecarttrack", new int[]{66,0});
		itemLookup.put("minecartrail", new int[]{66,0});
		itemLookup.put("mcarttrack", new int[]{66,0});
		itemLookup.put("mcartrail", new int[]{66,0});
		itemLookup.put("mctrack", new int[]{66,0});
		itemLookup.put("mcrail", new int[]{66,0});
		itemLookup.put("track", new int[]{66,0});
		itemLookup.put("rail", new int[]{66,0});
		itemLookup.put("cobblestonestairs", new int[]{67,0});
		itemLookup.put("cstonestairs", new int[]{67,0});
		itemLookup.put("stonestairs", new int[]{67,0});
		itemLookup.put("cobblestairs", new int[]{67,0});
		itemLookup.put("csstairs", new int[]{67,0});
		itemLookup.put("sstairs", new int[]{67,0});
		itemLookup.put("cstairs", new int[]{67,0});
		itemLookup.put("cobblestonestair", new int[]{67,0});
		itemLookup.put("cstonestair", new int[]{67,0});
		itemLookup.put("stonestair", new int[]{67,0});
		itemLookup.put("cobblestair", new int[]{67,0});
		itemLookup.put("csstair", new int[]{67,0});
		itemLookup.put("sstair", new int[]{67,0});
		itemLookup.put("cstair", new int[]{67,0});
		itemLookup.put("stairscobblestone", new int[]{67,0});
		itemLookup.put("stairscstone", new int[]{67,0});
		itemLookup.put("stairsstone", new int[]{67,0});
		itemLookup.put("stairscobble", new int[]{67,0});
		itemLookup.put("stairscs", new int[]{67,0});
		itemLookup.put("stairss", new int[]{67,0});
		itemLookup.put("stairsc", new int[]{67,0});
		itemLookup.put("staircobblestone", new int[]{67,0});
		itemLookup.put("staircstone", new int[]{67,0});
		itemLookup.put("stairstone", new int[]{67,0});
		itemLookup.put("staircobble", new int[]{67,0});
		itemLookup.put("staircs", new int[]{67,0});
		itemLookup.put("stairs", new int[]{67,0});
		itemLookup.put("stairc", new int[]{67,0});
		itemLookup.put("wallsign", new int[]{68,0});
		itemLookup.put("wsign", new int[]{68,0});
		itemLookup.put("lever", new int[]{69,0});
		itemLookup.put("smoothstonepressureplate", new int[]{70,0});
		itemLookup.put("smoothstonepressplate", new int[]{70,0});
		itemLookup.put("smoothstonepplate", new int[]{70,0});
		itemLookup.put("smoothstoneplate", new int[]{70,0});
		itemLookup.put("sstonepressureplate", new int[]{70,0});
		itemLookup.put("sstonepressplate", new int[]{70,0});
		itemLookup.put("sstonepplate", new int[]{70,0});
		itemLookup.put("sstoneplate", new int[]{70,0});
		itemLookup.put("stonepressureplate", new int[]{70,0});
		itemLookup.put("stonepressplate", new int[]{70,0});
		itemLookup.put("stonepplate", new int[]{70,0});
		itemLookup.put("stoneplate", new int[]{70,0});
		itemLookup.put("spressureplate", new int[]{70,0});
		itemLookup.put("spressplate", new int[]{70,0});
		itemLookup.put("spplate", new int[]{70,0});
		itemLookup.put("splate", new int[]{70,0});
		itemLookup.put("irondoorhalf", new int[]{71,0});
		itemLookup.put("idoorhalf", new int[]{71,0});
		itemLookup.put("irondoorbottom", new int[]{71,0});
		itemLookup.put("idoorbottom", new int[]{71,0});
		itemLookup.put("irondoorblock", new int[]{71,0});
		itemLookup.put("idoorblock", new int[]{71,0});
		itemLookup.put("steeldoorhalf", new int[]{71,0});
		itemLookup.put("sdoorhalf", new int[]{71,0});
		itemLookup.put("steeldoorbottom", new int[]{71,0});
		itemLookup.put("sdoorbottom", new int[]{71,0});
		itemLookup.put("steeldoorblock", new int[]{71,0});
		itemLookup.put("sdoorblock", new int[]{71,0});
		itemLookup.put("woodenpressureplate", new int[]{72,0});
		itemLookup.put("woodenpressplate", new int[]{72,0});
		itemLookup.put("woodenpplate", new int[]{72,0});
		itemLookup.put("woodenplate", new int[]{72,0});
		itemLookup.put("woodpressureplate", new int[]{72,0});
		itemLookup.put("woodpressplate", new int[]{72,0});
		itemLookup.put("woodpplate", new int[]{72,0});
		itemLookup.put("woodplate", new int[]{72,0});
		itemLookup.put("wpressureplate", new int[]{72,0});
		itemLookup.put("wpressplate", new int[]{72,0});
		itemLookup.put("wpplate", new int[]{72,0});
		itemLookup.put("wplate", new int[]{72,0});
		itemLookup.put("redstoneore", new int[]{73,0});
		itemLookup.put("redsore", new int[]{73,0});
		itemLookup.put("redore", new int[]{73,0});
		itemLookup.put("rstoneore", new int[]{73,0});
		itemLookup.put("rsore", new int[]{73,0});
		itemLookup.put("rore", new int[]{73,0});
		itemLookup.put("oreredstone", new int[]{73,0});
		itemLookup.put("orereds", new int[]{73,0});
		itemLookup.put("orered", new int[]{73,0});
		itemLookup.put("orerstone", new int[]{73,0});
		itemLookup.put("orers", new int[]{73,0});
		itemLookup.put("orer", new int[]{73,0});
		itemLookup.put("glowingredstoneore", new int[]{74,0});
		itemLookup.put("glowredstoneore", new int[]{74,0});
		itemLookup.put("gredstoneore", new int[]{74,0});
		itemLookup.put("glowingrstoneore", new int[]{74,0});
		itemLookup.put("glowrstoneore", new int[]{74,0});
		itemLookup.put("grstoneore", new int[]{74,0});
		itemLookup.put("glowingredsore", new int[]{74,0});
		itemLookup.put("glowredsore", new int[]{74,0});
		itemLookup.put("gredsore", new int[]{74,0});
		itemLookup.put("glowingredore", new int[]{74,0});
		itemLookup.put("glowredore", new int[]{74,0});
		itemLookup.put("gredore", new int[]{74,0});
		itemLookup.put("glowingrsore", new int[]{74,0});
		itemLookup.put("glowrsore", new int[]{74,0});
		itemLookup.put("grsore", new int[]{74,0});
		itemLookup.put("grore", new int[]{74,0});
		itemLookup.put("oreglowingredstone", new int[]{74,0});
		itemLookup.put("oreglowredstone", new int[]{74,0});
		itemLookup.put("oregredstone", new int[]{74,0});
		itemLookup.put("oreglowingrstone", new int[]{74,0});
		itemLookup.put("oreglowrstone", new int[]{74,0});
		itemLookup.put("oregrstone", new int[]{74,0});
		itemLookup.put("oreglowingreds", new int[]{74,0});
		itemLookup.put("oreglowreds", new int[]{74,0});
		itemLookup.put("oregreds", new int[]{74,0});
		itemLookup.put("oreglowingred", new int[]{74,0});
		itemLookup.put("oreglowred", new int[]{74,0});
		itemLookup.put("oregred", new int[]{74,0});
		itemLookup.put("oreglowingrs", new int[]{74,0});
		itemLookup.put("oreglowrs", new int[]{74,0});
		itemLookup.put("oregrs", new int[]{74,0});
		itemLookup.put("oregr", new int[]{74,0});
		itemLookup.put("redstonetorchoff", new int[]{75,0});
		itemLookup.put("rstonetorchoff", new int[]{75,0});
		itemLookup.put("redstorchoff", new int[]{75,0});
		itemLookup.put("redtorchoff", new int[]{75,0});
		itemLookup.put("rstorchoff", new int[]{75,0});
		itemLookup.put("redstonetorchon", new int[]{76,0});
		itemLookup.put("redstonetorch", new int[]{76,0});
		itemLookup.put("rstonetorchon", new int[]{76,0});
		itemLookup.put("rstonetorch", new int[]{76,0});
		itemLookup.put("redstorchon", new int[]{76,0});
		itemLookup.put("redstorch", new int[]{76,0});
		itemLookup.put("redtorchon", new int[]{76,0});
		itemLookup.put("redtorch", new int[]{76,0});
		itemLookup.put("rstorchon", new int[]{76,0});
		itemLookup.put("rstorch", new int[]{76,0});
		itemLookup.put("smoothstonebutton", new int[]{77,0});
		itemLookup.put("sstonebutton", new int[]{77,0});
		itemLookup.put("stonebutton", new int[]{77,0});
		itemLookup.put("sbutton", new int[]{77,0});
		itemLookup.put("button", new int[]{77,0});
		itemLookup.put("snowcovering", new int[]{78,0});
		itemLookup.put("snowcover", new int[]{78,0});
		itemLookup.put("scover", new int[]{78,0});
		itemLookup.put("ice", new int[]{79,0});
		itemLookup.put("snowblock", new int[]{80,0});
		itemLookup.put("blocksnow", new int[]{80,0});
		itemLookup.put("sblock", new int[]{80,0});
		itemLookup.put("blocks", new int[]{80,0});
		itemLookup.put("cactus", new int[]{81,0});
		itemLookup.put("cactuses", new int[]{81,0});
		itemLookup.put("cacti", new int[]{81,0});
		itemLookup.put("clayblock", new int[]{82,0});
		itemLookup.put("blockclay", new int[]{82,0});
		itemLookup.put("cblock", new int[]{82,0});
		itemLookup.put("blockc", new int[]{82,0});
		itemLookup.put("reedblock", new int[]{83,0});
		itemLookup.put("reedsblock", new int[]{83,0});
		itemLookup.put("sugarcaneblock", new int[]{83,0});
		itemLookup.put("scaneblock", new int[]{83,0});
		itemLookup.put("bambooblock", new int[]{83,0});
		itemLookup.put("blockreed", new int[]{83,0});
		itemLookup.put("blockreeds", new int[]{83,0});
		itemLookup.put("blocksugarcane", new int[]{83,0});
		itemLookup.put("blockscane", new int[]{83,0});
		itemLookup.put("blockbamboo", new int[]{83,0});
		itemLookup.put("jukebox", new int[]{84,0});
		itemLookup.put("jbox", new int[]{84,0});
		itemLookup.put("fence", new int[]{85,0});
		itemLookup.put("pumpkin", new int[]{86,0});
		itemLookup.put("netherrack", new int[]{87,0});
		itemLookup.put("netherrock", new int[]{87,0});
		itemLookup.put("netherstone", new int[]{87,0});
		itemLookup.put("hellstone", new int[]{87,0});
		itemLookup.put("nstone", new int[]{87,0});
		itemLookup.put("hstone", new int[]{87,0});
		itemLookup.put("soulsand", new int[]{88,0});
		itemLookup.put("slowsand", new int[]{88,0});
		itemLookup.put("slowmud", new int[]{88,0});
		itemLookup.put("ssand", new int[]{88,0});
		itemLookup.put("smud", new int[]{88,0});
		itemLookup.put("mud", new int[]{88,0});
		itemLookup.put("glowingstoneblock", new int[]{89,0});
		itemLookup.put("lightstoneblock", new int[]{89,0});
		itemLookup.put("glowstoneblock", new int[]{89,0});
		itemLookup.put("blockglowingstone", new int[]{89,0});
		itemLookup.put("blocklightstone", new int[]{89,0});
		itemLookup.put("blockglowstone", new int[]{89,0});
		itemLookup.put("glowingstoneb", new int[]{89,0});
		itemLookup.put("lightstoneb", new int[]{89,0});
		itemLookup.put("glowstoneb", new int[]{89,0});
		itemLookup.put("bglowingstone", new int[]{89,0});
		itemLookup.put("blightstone", new int[]{89,0});
		itemLookup.put("bglowstone", new int[]{89,0});
		itemLookup.put("glowingstone", new int[]{89,0});
		itemLookup.put("lightstone", new int[]{89,0});
		itemLookup.put("glowstone", new int[]{89,0});
		itemLookup.put("glowingblock", new int[]{89,0});
		itemLookup.put("lightblock", new int[]{89,0});
		itemLookup.put("glowblock", new int[]{89,0});
		itemLookup.put("lstone", new int[]{89,0});
		itemLookup.put("gstone", new int[]{89,0});
		itemLookup.put("portal", new int[]{90,0});
		itemLookup.put("jackolantern", new int[]{91,0});
		itemLookup.put("pumpkinlantern", new int[]{91,0});
		itemLookup.put("glowingpumpkin", new int[]{91,0});
		itemLookup.put("lightpumpkin", new int[]{91,0});
		itemLookup.put("jpumpkin", new int[]{91,0});
		itemLookup.put("plantren", new int[]{91,0});
		itemLookup.put("glowpumpkin", new int[]{91,0});
		itemLookup.put("gpumpkin", new int[]{91,0});
		itemLookup.put("lpumpkin", new int[]{91,0});
		itemLookup.put("cakeblock", new int[]{92,0});
		itemLookup.put("repeateroff", new int[]{93,0});
		itemLookup.put("repeatoff", new int[]{93,0});
		itemLookup.put("delayeroff", new int[]{93,0});
		itemLookup.put("delayoff", new int[]{93,0});
		itemLookup.put("dioderoff", new int[]{93,0});
		itemLookup.put("diodeoff", new int[]{93,0});
		itemLookup.put("repeaterblockoff", new int[]{93,0});
		itemLookup.put("repeatblockoff", new int[]{93,0});
		itemLookup.put("delayerblockoff", new int[]{93,0});
		itemLookup.put("delayblockoff", new int[]{93,0});
		itemLookup.put("dioderblockoff", new int[]{93,0});
		itemLookup.put("diodeblockoff", new int[]{93,0});
		itemLookup.put("repeateron", new int[]{94,0});
		itemLookup.put("repeaton", new int[]{94,0});
		itemLookup.put("delayeron", new int[]{94,0});
		itemLookup.put("delayon", new int[]{94,0});
		itemLookup.put("dioderon", new int[]{94,0});
		itemLookup.put("diodeon", new int[]{94,0});
		itemLookup.put("repeaterblockon", new int[]{94,0});
		itemLookup.put("repeatblockon", new int[]{94,0});
		itemLookup.put("delayerblockon", new int[]{94,0});
		itemLookup.put("delayblockon", new int[]{94,0});
		itemLookup.put("dioderblockon", new int[]{94,0});
		itemLookup.put("diodeblockon", new int[]{94,0});
		itemLookup.put("lockedchest", new int[]{95,0});
		itemLookup.put("lockchest", new int[]{95,0});
		itemLookup.put("jokechest", new int[]{95,0});
		itemLookup.put("aprilfoolschest", new int[]{95,0});
		itemLookup.put("aprilchest", new int[]{95,0});
		itemLookup.put("lootchest", new int[]{95,0});
		itemLookup.put("trapdoor", new int[]{96,0});
		itemLookup.put("doortrap", new int[]{96,0});
		itemLookup.put("hatch", new int[]{96,0});
		itemLookup.put("tdoor", new int[]{96,0});
		itemLookup.put("doort", new int[]{96,0});
		itemLookup.put("trapd", new int[]{96,0});
		itemLookup.put("dtrap", new int[]{96,0});
		itemLookup.put("ironshovel", new int[]{256,0});
		itemLookup.put("ironspade", new int[]{256,0});
		itemLookup.put("ishovel", new int[]{256,0});
		itemLookup.put("ispade", new int[]{256,0});
		itemLookup.put("steelshovel", new int[]{256,0});
		itemLookup.put("steelspade", new int[]{256,0});
		itemLookup.put("ironpickaxe", new int[]{257,0});
		itemLookup.put("ironpick", new int[]{257,0});
		itemLookup.put("steelpickaxe", new int[]{257,0});
		itemLookup.put("steelpick", new int[]{257,0});
		itemLookup.put("ipickaxe", new int[]{257,0});
		itemLookup.put("ipick", new int[]{257,0});
		itemLookup.put("ironaxe", new int[]{258,0});
		itemLookup.put("iaxe", new int[]{258,0});
		itemLookup.put("steelaxe", new int[]{258,0});
		itemLookup.put("flintandsteel", new int[]{259,0});
		itemLookup.put("flintandiron", new int[]{259,0});
		itemLookup.put("flintandtinder", new int[]{259,0});
		itemLookup.put("flintnsteel", new int[]{259,0});
		itemLookup.put("flintniron", new int[]{259,0});
		itemLookup.put("flintntinder", new int[]{259,0});
		itemLookup.put("flintsteel", new int[]{259,0});
		itemLookup.put("flintiron", new int[]{259,0});
		itemLookup.put("flinttinder", new int[]{259,0});
		itemLookup.put("lighter", new int[]{259,0});
		itemLookup.put("apple", new int[]{260,0});
		itemLookup.put("bow", new int[]{261,0});
		itemLookup.put("arrow", new int[]{262,0});
		itemLookup.put("coal", new int[]{263,0});
		itemLookup.put("charcoal", new int[]{263,1});
		itemLookup.put("ccoal", new int[]{263,1});
		itemLookup.put("diamond", new int[]{264,0});
		itemLookup.put("crystal", new int[]{264,0});
		itemLookup.put("ironingot", new int[]{265,0});
		itemLookup.put("ironbar", new int[]{265,0});
		itemLookup.put("ironi", new int[]{265,0});
		itemLookup.put("steelingot", new int[]{265,0});
		itemLookup.put("steelbar", new int[]{265,0});
		itemLookup.put("steeli", new int[]{265,0});
		itemLookup.put("iingot", new int[]{265,0});
		itemLookup.put("ibar", new int[]{265,0});
		itemLookup.put("ingotiron", new int[]{265,0});
		itemLookup.put("bariron", new int[]{265,0});
		itemLookup.put("iiron", new int[]{265,0});
		itemLookup.put("ingotsteel", new int[]{265,0});
		itemLookup.put("barsteel", new int[]{265,0});
		itemLookup.put("isteel", new int[]{265,0});
		itemLookup.put("ingoti", new int[]{265,0});
		itemLookup.put("bari", new int[]{265,0});
		itemLookup.put("goldingot", new int[]{266,0});
		itemLookup.put("goldbar", new int[]{266,0});
		itemLookup.put("goldi", new int[]{266,0});
		itemLookup.put("gingot", new int[]{266,0});
		itemLookup.put("gbar", new int[]{266,0});
		itemLookup.put("ingotgold", new int[]{266,0});
		itemLookup.put("bargold", new int[]{266,0});
		itemLookup.put("igold", new int[]{266,0});
		itemLookup.put("ingotg", new int[]{266,0});
		itemLookup.put("barg", new int[]{266,0});
		itemLookup.put("ironsword", new int[]{267,0});
		itemLookup.put("steelsword", new int[]{267,0});
		itemLookup.put("isword", new int[]{267,0});
		itemLookup.put("woodensword", new int[]{268,0});
		itemLookup.put("woodsword", new int[]{268,0});
		itemLookup.put("wsword", new int[]{268,0});
		itemLookup.put("woodenshovel", new int[]{269,0});
		itemLookup.put("woodenspade", new int[]{269,0});
		itemLookup.put("woodshovel", new int[]{269,0});
		itemLookup.put("woodspade", new int[]{269,0});
		itemLookup.put("wshovel", new int[]{269,0});
		itemLookup.put("wspade", new int[]{269,0});
		itemLookup.put("woodenpickaxe", new int[]{270,0});
		itemLookup.put("woodenpick", new int[]{270,0});
		itemLookup.put("woodpickaxe", new int[]{270,0});
		itemLookup.put("woodpick", new int[]{270,0});
		itemLookup.put("wpickaxe", new int[]{270,0});
		itemLookup.put("wpick", new int[]{270,0});
		itemLookup.put("woodenaxe", new int[]{271,0});
		itemLookup.put("woodaxe", new int[]{271,0});
		itemLookup.put("waxe", new int[]{271,0});
		itemLookup.put("smoothstonesword", new int[]{272,0});
		itemLookup.put("cobblestonesword", new int[]{272,0});
		itemLookup.put("sstonesword", new int[]{272,0});
		itemLookup.put("cstonesword", new int[]{272,0});
		itemLookup.put("stonesword", new int[]{272,0});
		itemLookup.put("sssword", new int[]{272,0});
		itemLookup.put("cssword", new int[]{272,0});
		itemLookup.put("ssword", new int[]{272,0});
		itemLookup.put("smoothstoneshovel", new int[]{273,0});
		itemLookup.put("smoothstonespade", new int[]{273,0});
		itemLookup.put("cobblestoneshovel", new int[]{273,0});
		itemLookup.put("cobblestonespade", new int[]{273,0});
		itemLookup.put("sstoneshovel", new int[]{273,0});
		itemLookup.put("sstonespade", new int[]{273,0});
		itemLookup.put("cstoneshovel", new int[]{273,0});
		itemLookup.put("cstonespade", new int[]{273,0});
		itemLookup.put("stoneshovel", new int[]{273,0});
		itemLookup.put("stonespade", new int[]{273,0});
		itemLookup.put("ssshovel", new int[]{273,0});
		itemLookup.put("csshovel", new int[]{273,0});
		itemLookup.put("ssspade", new int[]{273,0});
		itemLookup.put("csspade", new int[]{273,0});
		itemLookup.put("sshovel", new int[]{273,0});
		itemLookup.put("sspade", new int[]{273,0});
		itemLookup.put("smoothstonepickaxe", new int[]{274,0});
		itemLookup.put("cobblestonepickaxe", new int[]{274,0});
		itemLookup.put("smoothstonepick", new int[]{274,0});
		itemLookup.put("cobblestonepick", new int[]{274,0});
		itemLookup.put("sstonepickaxe", new int[]{274,0});
		itemLookup.put("sstonepick", new int[]{274,0});
		itemLookup.put("cstonepickaxe", new int[]{274,0});
		itemLookup.put("cstonepick", new int[]{274,0});
		itemLookup.put("stonepickaxe", new int[]{274,0});
		itemLookup.put("stonepick", new int[]{274,0});
		itemLookup.put("sspickaxe", new int[]{274,0});
		itemLookup.put("sspick", new int[]{274,0});
		itemLookup.put("cspickaxe", new int[]{274,0});
		itemLookup.put("cspick", new int[]{274,0});
		itemLookup.put("spickaxe", new int[]{274,0});
		itemLookup.put("spick", new int[]{274,0});
		itemLookup.put("smoothstoneaxe", new int[]{275,0});
		itemLookup.put("cobblestoneaxe", new int[]{275,0});
		itemLookup.put("sstoneaxe", new int[]{275,0});
		itemLookup.put("cstoneaxe", new int[]{275,0});
		itemLookup.put("stoneaxe", new int[]{275,0});
		itemLookup.put("ssaxe", new int[]{275,0});
		itemLookup.put("csaxe", new int[]{275,0});
		itemLookup.put("saxe", new int[]{275,0});
		itemLookup.put("diamondsword", new int[]{276,0});
		itemLookup.put("crystalsword", new int[]{276,0});
		itemLookup.put("dsword", new int[]{276,0});
		itemLookup.put("diamondshovel", new int[]{277,0});
		itemLookup.put("diamondspade", new int[]{277,0});
		itemLookup.put("crystalshovel", new int[]{277,0});
		itemLookup.put("crystalspade", new int[]{277,0});
		itemLookup.put("dshovel", new int[]{277,0});
		itemLookup.put("dspade", new int[]{277,0});
		itemLookup.put("diamondpickaxe", new int[]{278,0});
		itemLookup.put("diamondpick", new int[]{278,0});
		itemLookup.put("crystalpickaxe", new int[]{278,0});
		itemLookup.put("crystalpick", new int[]{278,0});
		itemLookup.put("dpickaxe", new int[]{278,0});
		itemLookup.put("dpick", new int[]{278,0});
		itemLookup.put("diamondaxe", new int[]{279,0});
		itemLookup.put("crystalaxe", new int[]{279,0});
		itemLookup.put("daxe", new int[]{279,0});
		itemLookup.put("stick", new int[]{280,0});
		itemLookup.put("bowl", new int[]{281,0});
		itemLookup.put("mushroomsoup", new int[]{282,0});
		itemLookup.put("mrsoup", new int[]{282,0});
		itemLookup.put("soup", new int[]{282,0});
		itemLookup.put("goldsword", new int[]{283,0});
		itemLookup.put("gsword", new int[]{283,0});
		itemLookup.put("goldshovel", new int[]{284,0});
		itemLookup.put("goldspade", new int[]{284,0});
		itemLookup.put("gshovel", new int[]{284,0});
		itemLookup.put("gspade", new int[]{284,0});
		itemLookup.put("goldpickaxe", new int[]{285,0});
		itemLookup.put("goldpick", new int[]{285,0});
		itemLookup.put("gpickaxe", new int[]{285,0});
		itemLookup.put("gpick", new int[]{285,0});
		itemLookup.put("goldaxe", new int[]{286,0});
		itemLookup.put("gaxe", new int[]{286,0});
		itemLookup.put("string", new int[]{287,0});
		itemLookup.put("rope", new int[]{287,0});
		itemLookup.put("feather", new int[]{288,0});
		itemLookup.put("gunpowder", new int[]{289,0});
		itemLookup.put("sulfur", new int[]{289,0});
		itemLookup.put("sulphur", new int[]{289,0});
		itemLookup.put("woodenhoe", new int[]{290,0});
		itemLookup.put("woodhoe", new int[]{290,0});
		itemLookup.put("whoe", new int[]{290,0});
		itemLookup.put("smoothstonehoe", new int[]{291,0});
		itemLookup.put("cobblestonehoe", new int[]{291,0});
		itemLookup.put("sstonehoe", new int[]{291,0});
		itemLookup.put("cstonehoe", new int[]{291,0});
		itemLookup.put("stonehoe", new int[]{291,0});
		itemLookup.put("sshoe", new int[]{291,0});
		itemLookup.put("cshoe", new int[]{291,0});
		itemLookup.put("shoe", new int[]{291,0});
		itemLookup.put("ironhoe", new int[]{292,0});
		itemLookup.put("steelhoe", new int[]{292,0});
		itemLookup.put("ihoe", new int[]{292,0});
		itemLookup.put("diamondhoe", new int[]{293,0});
		itemLookup.put("crystalhoe", new int[]{293,0});
		itemLookup.put("dhoe", new int[]{293,0});
		itemLookup.put("goldhoe", new int[]{294,0});
		itemLookup.put("ghoe", new int[]{294,0});
		itemLookup.put("seeds", new int[]{295,0});
		itemLookup.put("seed", new int[]{295,0});
		itemLookup.put("wheat", new int[]{296,0});
		itemLookup.put("bread", new int[]{297,0});
		itemLookup.put("leatherhelmet", new int[]{298,0});
		itemLookup.put("leatherhelm", new int[]{298,0});
		itemLookup.put("leatherhat", new int[]{298,0});
		itemLookup.put("lhelmet", new int[]{298,0});
		itemLookup.put("lhelm", new int[]{298,0});
		itemLookup.put("lhat", new int[]{298,0});
		itemLookup.put("leatherchestplate", new int[]{299,0});
		itemLookup.put("leatherplatebody", new int[]{299,0});
		itemLookup.put("leatherplate", new int[]{299,0});
		itemLookup.put("leathershirt", new int[]{299,0});
		itemLookup.put("lchestplate", new int[]{299,0});
		itemLookup.put("lplatebody", new int[]{299,0});
		itemLookup.put("lplate", new int[]{299,0});
		itemLookup.put("lshirt", new int[]{299,0});
		itemLookup.put("leatherleggings", new int[]{300,0});
		itemLookup.put("leatherlegs", new int[]{300,0});
		itemLookup.put("leatherpants", new int[]{300,0});
		itemLookup.put("lleggings", new int[]{300,0});
		itemLookup.put("llegs", new int[]{300,0});
		itemLookup.put("lpants", new int[]{300,0});
		itemLookup.put("leatherboots", new int[]{301,0});
		itemLookup.put("leathershoes", new int[]{301,0});
		itemLookup.put("lboots", new int[]{301,0});
		itemLookup.put("lshoes", new int[]{301,0});
		itemLookup.put("chainmailhelmet", new int[]{302,0});
		itemLookup.put("chainmailhelm", new int[]{302,0});
		itemLookup.put("chainmailhat", new int[]{302,0});
		itemLookup.put("chainmhelmet", new int[]{302,0});
		itemLookup.put("chainmhelm", new int[]{302,0});
		itemLookup.put("chainmhat", new int[]{302,0});
		itemLookup.put("cmailhelmet", new int[]{302,0});
		itemLookup.put("cmailhelm", new int[]{302,0});
		itemLookup.put("cmailhat", new int[]{302,0});
		itemLookup.put("chainhelmet", new int[]{302,0});
		itemLookup.put("chainhelm", new int[]{302,0});
		itemLookup.put("chainhat", new int[]{302,0});
		itemLookup.put("cmhelmet", new int[]{302,0});
		itemLookup.put("cmhelm", new int[]{302,0});
		itemLookup.put("cmhat", new int[]{302,0});
		itemLookup.put("chainmailchestplate", new int[]{303,0});
		itemLookup.put("chainmailplatebody", new int[]{303,0});
		itemLookup.put("chainmailplate", new int[]{303,0});
		itemLookup.put("chainmailshirt", new int[]{303,0});
		itemLookup.put("chainmchestplate", new int[]{303,0});
		itemLookup.put("chainmplatebody", new int[]{303,0});
		itemLookup.put("chainmplate", new int[]{303,0});
		itemLookup.put("chainmshirt", new int[]{303,0});
		itemLookup.put("cmailchestplate", new int[]{303,0});
		itemLookup.put("cmailplatebody", new int[]{303,0});
		itemLookup.put("cmailplate", new int[]{303,0});
		itemLookup.put("cmailshirt", new int[]{303,0});
		itemLookup.put("chainchestplate", new int[]{303,0});
		itemLookup.put("chainplatebody", new int[]{303,0});
		itemLookup.put("chainplate", new int[]{303,0});
		itemLookup.put("chainshirt", new int[]{303,0});
		itemLookup.put("cmchestplate", new int[]{303,0});
		itemLookup.put("cmplatebody", new int[]{303,0});
		itemLookup.put("cmplate", new int[]{303,0});
		itemLookup.put("cmshirt", new int[]{303,0});
		itemLookup.put("chainmailleggings", new int[]{304,0});
		itemLookup.put("chainmaillegs", new int[]{304,0});
		itemLookup.put("chainmailpants", new int[]{304,0});
		itemLookup.put("chainmleggings", new int[]{304,0});
		itemLookup.put("chainmlegs", new int[]{304,0});
		itemLookup.put("chainmpants", new int[]{304,0});
		itemLookup.put("cmailleggings", new int[]{304,0});
		itemLookup.put("cmaillegs", new int[]{304,0});
		itemLookup.put("cmailpants", new int[]{304,0});
		itemLookup.put("chainleggings", new int[]{304,0});
		itemLookup.put("chainlegs", new int[]{304,0});
		itemLookup.put("chainpants", new int[]{304,0});
		itemLookup.put("cmleggings", new int[]{304,0});
		itemLookup.put("cmlegs", new int[]{304,0});
		itemLookup.put("cmpants", new int[]{304,0});
		itemLookup.put("chainmailboots", new int[]{305,0});
		itemLookup.put("chainmailshoes", new int[]{305,0});
		itemLookup.put("chainmboots", new int[]{305,0});
		itemLookup.put("chainmshoes", new int[]{305,0});
		itemLookup.put("cmailboots", new int[]{305,0});
		itemLookup.put("cmailshoes", new int[]{305,0});
		itemLookup.put("chainboots", new int[]{305,0});
		itemLookup.put("chainshoes", new int[]{305,0});
		itemLookup.put("cmboots", new int[]{305,0});
		itemLookup.put("cmshoes", new int[]{305,0});
		itemLookup.put("ironhelmet", new int[]{306,0});
		itemLookup.put("ironhelm", new int[]{306,0});
		itemLookup.put("ironhat", new int[]{306,0});
		itemLookup.put("ihelmet", new int[]{306,0});
		itemLookup.put("ihelm", new int[]{306,0});
		itemLookup.put("ihat", new int[]{306,0});
		itemLookup.put("steelhelmet", new int[]{306,0});
		itemLookup.put("steelhelm", new int[]{306,0});
		itemLookup.put("steelhat", new int[]{306,0});
		itemLookup.put("shelmet", new int[]{306,0});
		itemLookup.put("shelm", new int[]{306,0});
		itemLookup.put("shat", new int[]{306,0});
		itemLookup.put("ironchestplate", new int[]{307,0});
		itemLookup.put("ironplatebody", new int[]{307,0});
		itemLookup.put("ironplate", new int[]{307,0});
		itemLookup.put("ironshirt", new int[]{307,0});
		itemLookup.put("ichestplate", new int[]{307,0});
		itemLookup.put("iplatebody", new int[]{307,0});
		itemLookup.put("iplate", new int[]{307,0});
		itemLookup.put("ishirt", new int[]{307,0});
		itemLookup.put("steelchestplate", new int[]{307,0});
		itemLookup.put("steelplatebody", new int[]{307,0});
		itemLookup.put("steelplate", new int[]{307,0});
		itemLookup.put("steelshirt", new int[]{307,0});
		itemLookup.put("schestplate", new int[]{307,0});
		itemLookup.put("splatebody", new int[]{307,0});
		itemLookup.put("sshirt", new int[]{307,0});
		itemLookup.put("ironleggings", new int[]{308,0});
		itemLookup.put("ironlegs", new int[]{308,0});
		itemLookup.put("ironpants", new int[]{308,0});
		itemLookup.put("ileggings", new int[]{308,0});
		itemLookup.put("ilegs", new int[]{308,0});
		itemLookup.put("ipants", new int[]{308,0});
		itemLookup.put("steelleggings", new int[]{308,0});
		itemLookup.put("steellegs", new int[]{308,0});
		itemLookup.put("steelpants", new int[]{308,0});
		itemLookup.put("sleggings", new int[]{308,0});
		itemLookup.put("slegs", new int[]{308,0});
		itemLookup.put("spants", new int[]{308,0});
		itemLookup.put("ironboots", new int[]{309,0});
		itemLookup.put("ironshoes", new int[]{309,0});
		itemLookup.put("iboots", new int[]{309,0});
		itemLookup.put("ishoes", new int[]{309,0});
		itemLookup.put("steelboots", new int[]{309,0});
		itemLookup.put("steelshoes", new int[]{309,0});
		itemLookup.put("sboots", new int[]{309,0});
		itemLookup.put("sshoes", new int[]{309,0});
		itemLookup.put("diamondhelmet", new int[]{310,0});
		itemLookup.put("diamondhelm", new int[]{310,0});
		itemLookup.put("diamondhat", new int[]{310,0});
		itemLookup.put("dhelmet", new int[]{310,0});
		itemLookup.put("dhelm", new int[]{310,0});
		itemLookup.put("dhat", new int[]{310,0});
		itemLookup.put("crystalhelmet", new int[]{310,0});
		itemLookup.put("crystalhelm", new int[]{310,0});
		itemLookup.put("crystalhat", new int[]{310,0});
		itemLookup.put("chelmet", new int[]{310,0});
		itemLookup.put("chelm", new int[]{310,0});
		itemLookup.put("chat", new int[]{310,0});
		itemLookup.put("diamondchestplate", new int[]{311,0});
		itemLookup.put("diamondplatebody", new int[]{311,0});
		itemLookup.put("diamondplate", new int[]{311,0});
		itemLookup.put("diamondshirt", new int[]{311,0});
		itemLookup.put("dchestplate", new int[]{311,0});
		itemLookup.put("dplatebody", new int[]{311,0});
		itemLookup.put("dplate", new int[]{311,0});
		itemLookup.put("dshirt", new int[]{311,0});
		itemLookup.put("crystalchestplate", new int[]{311,0});
		itemLookup.put("crystalplatebody", new int[]{311,0});
		itemLookup.put("crystalplate", new int[]{311,0});
		itemLookup.put("crystalshirt", new int[]{311,0});
		itemLookup.put("cchestplate", new int[]{311,0});
		itemLookup.put("cplatebody", new int[]{311,0});
		itemLookup.put("cplate", new int[]{311,0});
		itemLookup.put("cshirt", new int[]{311,0});
		itemLookup.put("diamondleggings", new int[]{312,0});
		itemLookup.put("diamondlegs", new int[]{312,0});
		itemLookup.put("diamondpants", new int[]{312,0});
		itemLookup.put("dleggings", new int[]{312,0});
		itemLookup.put("dlegs", new int[]{312,0});
		itemLookup.put("dpants", new int[]{312,0});
		itemLookup.put("crystalleggings", new int[]{312,0});
		itemLookup.put("crystallegs", new int[]{312,0});
		itemLookup.put("crystalpants", new int[]{312,0});
		itemLookup.put("cleggings", new int[]{312,0});
		itemLookup.put("clegs", new int[]{312,0});
		itemLookup.put("cpants", new int[]{312,0});
		itemLookup.put("diamondboots", new int[]{313,0});
		itemLookup.put("diamondshoes", new int[]{313,0});
		itemLookup.put("dboots", new int[]{313,0});
		itemLookup.put("dshoes", new int[]{313,0});
		itemLookup.put("crystalboots", new int[]{313,0});
		itemLookup.put("crystalshoes", new int[]{313,0});
		itemLookup.put("cboots", new int[]{313,0});
		itemLookup.put("cshoes", new int[]{313,0});
		itemLookup.put("goldhelmet", new int[]{314,0});
		itemLookup.put("goldhelm", new int[]{314,0});
		itemLookup.put("goldhat", new int[]{314,0});
		itemLookup.put("ghelmet", new int[]{314,0});
		itemLookup.put("ghelm", new int[]{314,0});
		itemLookup.put("ghat", new int[]{314,0});
		itemLookup.put("goldchestplate", new int[]{315,0});
		itemLookup.put("goldplatebody", new int[]{315,0});
		itemLookup.put("goldplate", new int[]{315,0});
		itemLookup.put("goldshirt", new int[]{315,0});
		itemLookup.put("gchestplate", new int[]{315,0});
		itemLookup.put("gplatebody", new int[]{315,0});
		itemLookup.put("gplateplate", new int[]{315,0});
		itemLookup.put("gshirt", new int[]{315,0});
		itemLookup.put("goldleggings", new int[]{316,0});
		itemLookup.put("goldlegs", new int[]{316,0});
		itemLookup.put("goldpants", new int[]{316,0});
		itemLookup.put("gleggings", new int[]{316,0});
		itemLookup.put("glegs", new int[]{316,0});
		itemLookup.put("gpants", new int[]{316,0});
		itemLookup.put("goldboots", new int[]{317,0});
		itemLookup.put("goldshoes", new int[]{317,0});
		itemLookup.put("gboots", new int[]{317,0});
		itemLookup.put("gshoes", new int[]{317,0});
		itemLookup.put("flint", new int[]{318,0});
		itemLookup.put("pork", new int[]{319,0});
		itemLookup.put("rawpork", new int[]{319,0});
		itemLookup.put("rpork", new int[]{319,0});
		itemLookup.put("grilledpork", new int[]{320,0});
		itemLookup.put("grillpork", new int[]{320,0});
		itemLookup.put("gpork", new int[]{320,0});
		itemLookup.put("cookedpork", new int[]{320,0});
		itemLookup.put("bacon", new int[]{320,0});
		itemLookup.put("painting", new int[]{321,0});
		itemLookup.put("picture", new int[]{321,0});
		itemLookup.put("goldenapple", new int[]{322,0});
		itemLookup.put("goldapple", new int[]{322,0});
		itemLookup.put("gapple", new int[]{322,0});
		itemLookup.put("sign", new int[]{323,0});
		itemLookup.put("woodendoor", new int[]{324,0});
		itemLookup.put("wooddoor", new int[]{324,0});
		itemLookup.put("wdoor", new int[]{324,0});
		itemLookup.put("bucket", new int[]{325,0});
		itemLookup.put("bukkit", new int[]{325,0});
		itemLookup.put("waterbucket", new int[]{326,0});
		itemLookup.put("waterbukkit", new int[]{326,0});
		itemLookup.put("wbucket", new int[]{326,0});
		itemLookup.put("wbukkit", new int[]{326,0});
		itemLookup.put("magmabucket", new int[]{327,0});
		itemLookup.put("magmabukkit", new int[]{327,0});
		itemLookup.put("lavabucket", new int[]{327,0});
		itemLookup.put("lavabukkit", new int[]{327,0});
		itemLookup.put("lbucket", new int[]{327,0});
		itemLookup.put("lbukkit", new int[]{327,0});
		itemLookup.put("minecart", new int[]{328,0});
		itemLookup.put("mcart", new int[]{328,0});
		itemLookup.put("cart", new int[]{328,0});
		itemLookup.put("saddle", new int[]{329,0});
		itemLookup.put("irondoor", new int[]{330,0});
		itemLookup.put("idoor", new int[]{330,0});
		itemLookup.put("steeldoor", new int[]{330,0});
		itemLookup.put("sdoor", new int[]{330,0});
		itemLookup.put("dooriron", new int[]{330,0});
		itemLookup.put("doori", new int[]{330,0});
		itemLookup.put("doorsteel", new int[]{330,0});
		itemLookup.put("doors", new int[]{330,0});
		itemLookup.put("redstonedust", new int[]{331,0});
		itemLookup.put("redstone", new int[]{331,0});
		itemLookup.put("rstonedust", new int[]{331,0});
		itemLookup.put("rstone", new int[]{331,0});
		itemLookup.put("redsdust", new int[]{331,0});
		itemLookup.put("reddust", new int[]{331,0});
		itemLookup.put("rsdust", new int[]{331,0});
		itemLookup.put("rdust", new int[]{331,0});
		itemLookup.put("snowball", new int[]{332,0});
		itemLookup.put("snball", new int[]{332,0});
		itemLookup.put("boat", new int[]{333,0});
		itemLookup.put("leather", new int[]{334,0});
		itemLookup.put("milkbucket", new int[]{335,0});
		itemLookup.put("milkbukkit", new int[]{335,0});
		itemLookup.put("mbucket", new int[]{335,0});
		itemLookup.put("mbukkit", new int[]{335,0});
		itemLookup.put("claybrick", new int[]{336,0});
		itemLookup.put("brick", new int[]{336,0});
		itemLookup.put("clayball", new int[]{337,0});
		itemLookup.put("cball", new int[]{337,0});
		itemLookup.put("clay", new int[]{337,0});
		itemLookup.put("reeds", new int[]{338,0});
		itemLookup.put("reed", new int[]{338,0});
		itemLookup.put("sugarcane", new int[]{338,0});
		itemLookup.put("scane", new int[]{338,0});
		itemLookup.put("bamboo", new int[]{338,0});
		itemLookup.put("paper", new int[]{339,0});
		itemLookup.put("papyrus", new int[]{339,0});
		itemLookup.put("book", new int[]{340,0});
		itemLookup.put("slimeball", new int[]{341,0});
		itemLookup.put("slball", new int[]{341,0});
		itemLookup.put("storageminecart", new int[]{342,0});
		itemLookup.put("chestminecart", new int[]{342,0});
		itemLookup.put("storagemcart", new int[]{342,0});
		itemLookup.put("chestmcart", new int[]{342,0});
		itemLookup.put("storagecart", new int[]{342,0});
		itemLookup.put("chestcart", new int[]{342,0});
		itemLookup.put("sminecart", new int[]{342,0});
		itemLookup.put("cminecart", new int[]{342,0});
		itemLookup.put("smcart", new int[]{342,0});
		itemLookup.put("cmcart", new int[]{342,0});
		itemLookup.put("scart", new int[]{342,0});
		itemLookup.put("ccart", new int[]{342,0});
		itemLookup.put("engineminecart", new int[]{343,0});
		itemLookup.put("poweredminecart", new int[]{343,0});
		itemLookup.put("powerminecart", new int[]{343,0});
		itemLookup.put("furnaceminecart", new int[]{343,0});
		itemLookup.put("enginemcart", new int[]{343,0});
		itemLookup.put("poweredmcart", new int[]{343,0});
		itemLookup.put("powermcart", new int[]{343,0});
		itemLookup.put("furnacemcart", new int[]{343,0});
		itemLookup.put("enginecart", new int[]{343,0});
		itemLookup.put("poweredcart", new int[]{343,0});
		itemLookup.put("powercart", new int[]{343,0});
		itemLookup.put("furnacecart", new int[]{343,0});
		itemLookup.put("eminecart", new int[]{343,0});
		itemLookup.put("pminecart", new int[]{343,0});
		itemLookup.put("fminecart", new int[]{343,0});
		itemLookup.put("emcart", new int[]{343,0});
		itemLookup.put("pmcart", new int[]{343,0});
		itemLookup.put("fmcart", new int[]{343,0});
		itemLookup.put("ecart", new int[]{343,0});
		itemLookup.put("pcart", new int[]{343,0});
		itemLookup.put("fcart", new int[]{343,0});
		itemLookup.put("egg", new int[]{344,0});
		itemLookup.put("compass", new int[]{345,0});
		itemLookup.put("fishingrod", new int[]{346,0});
		itemLookup.put("fishrod", new int[]{346,0});
		itemLookup.put("frod", new int[]{346,0});
		itemLookup.put("rod", new int[]{346,0});
		itemLookup.put("goldwatch", new int[]{347,0});
		itemLookup.put("goldclock", new int[]{347,0});
		itemLookup.put("gwatch", new int[]{347,0});
		itemLookup.put("gclock", new int[]{347,0});
		itemLookup.put("watch", new int[]{347,0});
		itemLookup.put("clock", new int[]{347,0});
		itemLookup.put("glowingstoneblockdust", new int[]{348,0});
		itemLookup.put("lightstoneblockdust", new int[]{348,0});
		itemLookup.put("glowstoneblockdust", new int[]{348,0});
		itemLookup.put("blockglowingstonedust", new int[]{348,0});
		itemLookup.put("blocklightstonedust", new int[]{348,0});
		itemLookup.put("blockglowstonedust", new int[]{348,0});
		itemLookup.put("glowingstonebdust", new int[]{348,0});
		itemLookup.put("lightstonebdust", new int[]{348,0});
		itemLookup.put("glowstonebdust", new int[]{348,0});
		itemLookup.put("bglowingstonedust", new int[]{348,0});
		itemLookup.put("blightstonedust", new int[]{348,0});
		itemLookup.put("bglowstonedust", new int[]{348,0});
		itemLookup.put("glowingstonedust", new int[]{348,0});
		itemLookup.put("lightstonedust", new int[]{348,0});
		itemLookup.put("glowstonedust", new int[]{348,0});
		itemLookup.put("glowingblockdust", new int[]{348,0});
		itemLookup.put("lightblockdust", new int[]{348,0});
		itemLookup.put("glowblockdust", new int[]{348,0});
		itemLookup.put("lbdust", new int[]{348,0});
		itemLookup.put("gbdust", new int[]{348,0});
		itemLookup.put("lsdust", new int[]{348,0});
		itemLookup.put("gsdust", new int[]{348,0});
		itemLookup.put("rawfish", new int[]{349,0});
		itemLookup.put("rafish", new int[]{349,0});
		itemLookup.put("fish", new int[]{349,0});
		itemLookup.put("cookedfish", new int[]{350,0});
		itemLookup.put("cookfish", new int[]{350,0});
		itemLookup.put("cfish", new int[]{350,0});
		itemLookup.put("grilledfish", new int[]{350,0});
		itemLookup.put("grillfish", new int[]{350,0});
		itemLookup.put("gfish", new int[]{350,0});
		itemLookup.put("roastedfish", new int[]{350,0});
		itemLookup.put("roastfish", new int[]{350,0});
		itemLookup.put("rofish", new int[]{350,0});
		itemLookup.put("inksack", new int[]{351,0});
		itemLookup.put("inksac", new int[]{351,0});
		itemLookup.put("isack", new int[]{351,0});
		itemLookup.put("isac", new int[]{351,0});
		itemLookup.put("sack", new int[]{351,0});
		itemLookup.put("sac", new int[]{351,0});
		itemLookup.put("blackinksack", new int[]{351,0});
		itemLookup.put("blackinksac", new int[]{351,0});
		itemLookup.put("blackisack", new int[]{351,0});
		itemLookup.put("blackisac", new int[]{351,0});
		itemLookup.put("blacksack", new int[]{351,0});
		itemLookup.put("blacksac", new int[]{351,0});
		itemLookup.put("inksackblack", new int[]{351,0});
		itemLookup.put("inksacblack", new int[]{351,0});
		itemLookup.put("isackblack", new int[]{351,0});
		itemLookup.put("isacclack", new int[]{351,0});
		itemLookup.put("sackblack", new int[]{351,0});
		itemLookup.put("sacblack", new int[]{351,0});
		itemLookup.put("blackinksackcolour", new int[]{351,0});
		itemLookup.put("blackinksaccolour", new int[]{351,0});
		itemLookup.put("blackisackcolour", new int[]{351,0});
		itemLookup.put("blackisaccolour", new int[]{351,0});
		itemLookup.put("blacksackcolour", new int[]{351,0});
		itemLookup.put("blacksaccolour", new int[]{351,0});
		itemLookup.put("inksackblackcolour", new int[]{351,0});
		itemLookup.put("inksacblackcolour", new int[]{351,0});
		itemLookup.put("isackblackcolour", new int[]{351,0});
		itemLookup.put("isacclackcolour", new int[]{351,0});
		itemLookup.put("sackblackcolour", new int[]{351,0});
		itemLookup.put("sacblackcolour", new int[]{351,0});
		itemLookup.put("blackinksackcolor", new int[]{351,0});
		itemLookup.put("blackinksaccolor", new int[]{351,0});
		itemLookup.put("blackisackcolor", new int[]{351,0});
		itemLookup.put("blackisaccolor", new int[]{351,0});
		itemLookup.put("blacksackcolor", new int[]{351,0});
		itemLookup.put("blacksaccolor", new int[]{351,0});
		itemLookup.put("inksackblackcolor", new int[]{351,0});
		itemLookup.put("inksacblackcolor", new int[]{351,0});
		itemLookup.put("isackblackcolor", new int[]{351,0});
		itemLookup.put("isacblackcolor", new int[]{351,0});
		itemLookup.put("sackblackcolor", new int[]{351,0});
		itemLookup.put("sacblackcolor", new int[]{351,0});
		itemLookup.put("blackinksackdye", new int[]{351,0});
		itemLookup.put("blackinksacdye", new int[]{351,0});
		itemLookup.put("blackisackdye", new int[]{351,0});
		itemLookup.put("blackisacdye", new int[]{351,0});
		itemLookup.put("blacksackdye", new int[]{351,0});
		itemLookup.put("blacksacdye", new int[]{351,0});
		itemLookup.put("inksackblackdye", new int[]{351,0});
		itemLookup.put("inksacblackdye", new int[]{351,0});
		itemLookup.put("isackblackdye", new int[]{351,0});
		itemLookup.put("isacclackdye", new int[]{351,0});
		itemLookup.put("sackblackdye", new int[]{351,0});
		itemLookup.put("sacblackdye", new int[]{351,0});
		itemLookup.put("blackcolor", new int[]{351,0});
		itemLookup.put("blackdye", new int[]{351,0});
		itemLookup.put("rosered", new int[]{351,1});
		itemLookup.put("roseredcolor", new int[]{351,1});
		itemLookup.put("roseredcolour", new int[]{351,1});
		itemLookup.put("rosereddye", new int[]{351,1});
		itemLookup.put("redrosecolor", new int[]{351,1});
		itemLookup.put("redrosecolour", new int[]{351,1});
		itemLookup.put("redrosedye", new int[]{351,1});
		itemLookup.put("redr", new int[]{351,1});
		itemLookup.put("redrcolor", new int[]{351,1});
		itemLookup.put("redrcolour", new int[]{351,1});
		itemLookup.put("redrdye", new int[]{351,1});
		itemLookup.put("redcolor", new int[]{351,1});
		itemLookup.put("redcolour", new int[]{351,1});
		itemLookup.put("reddye", new int[]{351,1});
		itemLookup.put("cactusgreen", new int[]{351,2});
		itemLookup.put("greencactus", new int[]{351,2});
		itemLookup.put("cactusgreencolour", new int[]{351,2});
		itemLookup.put("greencactuscolour", new int[]{351,2});
		itemLookup.put("cactusgreencolor", new int[]{351,2});
		itemLookup.put("greencactuscolor", new int[]{351,2});
		itemLookup.put("cactusgreendye", new int[]{351,2});
		itemLookup.put("greencactusdye", new int[]{351,2});
		itemLookup.put("greencolour", new int[]{351,2});
		itemLookup.put("greencolor", new int[]{351,2});
		itemLookup.put("greendye", new int[]{351,2});
		itemLookup.put("cocobeans", new int[]{351,3});
		itemLookup.put("cocobean", new int[]{351,3});
		itemLookup.put("cbeans", new int[]{351,3});
		itemLookup.put("cbean", new int[]{351,3});
		itemLookup.put("beans", new int[]{351,3});
		itemLookup.put("bean", new int[]{351,3});
		itemLookup.put("browncocobeans", new int[]{351,3});
		itemLookup.put("browncocobean", new int[]{351,3});
		itemLookup.put("browncbeans", new int[]{351,3});
		itemLookup.put("browncbean", new int[]{351,3});
		itemLookup.put("brownbeans", new int[]{351,3});
		itemLookup.put("brownbean", new int[]{351,3});
		itemLookup.put("brownb", new int[]{351,3});
		itemLookup.put("cocobeanscolour", new int[]{351,3});
		itemLookup.put("cocobeancolour", new int[]{351,3});
		itemLookup.put("cbeanscolour", new int[]{351,3});
		itemLookup.put("cbeancolour", new int[]{351,3});
		itemLookup.put("beanscolour", new int[]{351,3});
		itemLookup.put("beancolour", new int[]{351,3});
		itemLookup.put("browncocobeanscolour", new int[]{351,3});
		itemLookup.put("browncocobeancolour", new int[]{351,3});
		itemLookup.put("browncbeanscolour", new int[]{351,3});
		itemLookup.put("browncbeancolour", new int[]{351,3});
		itemLookup.put("brownbeanscolour", new int[]{351,3});
		itemLookup.put("brownbeancolour", new int[]{351,3});
		itemLookup.put("brownbcolour", new int[]{351,3});
		itemLookup.put("cocobeanscolor", new int[]{351,3});
		itemLookup.put("cocobeancolor", new int[]{351,3});
		itemLookup.put("cbeanscolor", new int[]{351,3});
		itemLookup.put("cbeancolor", new int[]{351,3});
		itemLookup.put("beanscolor", new int[]{351,3});
		itemLookup.put("beancolor", new int[]{351,3});
		itemLookup.put("browncocobeanscolor", new int[]{351,3});
		itemLookup.put("browncocobeancolor", new int[]{351,3});
		itemLookup.put("browncbeanscolor", new int[]{351,3});
		itemLookup.put("browncbeancolor", new int[]{351,3});
		itemLookup.put("brownbeanscolor", new int[]{351,3});
		itemLookup.put("brownbeancolor", new int[]{351,3});
		itemLookup.put("brownbcolor", new int[]{351,3});
		itemLookup.put("cocobeansdye", new int[]{351,3});
		itemLookup.put("cocobeandye", new int[]{351,3});
		itemLookup.put("cbeansdye", new int[]{351,3});
		itemLookup.put("cbeandye", new int[]{351,3});
		itemLookup.put("beansdye", new int[]{351,3});
		itemLookup.put("beandye", new int[]{351,3});
		itemLookup.put("browncocobeansdye", new int[]{351,3});
		itemLookup.put("browncocobeandye", new int[]{351,3});
		itemLookup.put("browncbeansdye", new int[]{351,3});
		itemLookup.put("browncbeandye", new int[]{351,3});
		itemLookup.put("brownbeansdye", new int[]{351,3});
		itemLookup.put("brownbeandye", new int[]{351,3});
		itemLookup.put("brownbdye", new int[]{351,3});
		itemLookup.put("browncolour", new int[]{351,3});
		itemLookup.put("browncolor", new int[]{351,3});
		itemLookup.put("browndye", new int[]{351,3});
		itemLookup.put("bluelapislzuli", new int[]{351,4});
		itemLookup.put("bluelapisl", new int[]{351,4});
		itemLookup.put("bluelapis", new int[]{351,4});
		itemLookup.put("bluel", new int[]{351,4});
		itemLookup.put("lapislazuliblue", new int[]{351,4});
		itemLookup.put("lapislblue", new int[]{351,4});
		itemLookup.put("lapisblue", new int[]{351,4});
		itemLookup.put("lapislazuli", new int[]{351,4});
		itemLookup.put("lapisl", new int[]{351,4});
		itemLookup.put("lapis", new int[]{351,4});
		itemLookup.put("bluelapislazulicolour", new int[]{351,4});
		itemLookup.put("bluelapislcolour", new int[]{351,4});
		itemLookup.put("bluelapiscolour", new int[]{351,4});
		itemLookup.put("lapislazulibluecolour", new int[]{351,4});
		itemLookup.put("lapislbluecolour", new int[]{351,4});
		itemLookup.put("lapisbluecolour", new int[]{351,4});
		itemLookup.put("lapislazulicolour", new int[]{351,4});
		itemLookup.put("lapislcolour", new int[]{351,4});
		itemLookup.put("lapiscolour", new int[]{351,4});
		itemLookup.put("bluelapislazulicolor", new int[]{351,4});
		itemLookup.put("bluelapislcolor", new int[]{351,4});
		itemLookup.put("bluelapiscolor", new int[]{351,4});
		itemLookup.put("lapislazulibluecolor", new int[]{351,4});
		itemLookup.put("lapislbluecolor", new int[]{351,4});
		itemLookup.put("lapisbluecolor", new int[]{351,4});
		itemLookup.put("lapislazulicolor", new int[]{351,4});
		itemLookup.put("lapislcolor", new int[]{351,4});
		itemLookup.put("lapiscolor", new int[]{351,4});
		itemLookup.put("bluelapislazulidye", new int[]{351,4});
		itemLookup.put("bluelapisldye", new int[]{351,4});
		itemLookup.put("bluelapisdye", new int[]{351,4});
		itemLookup.put("lapislazulibluedye", new int[]{351,4});
		itemLookup.put("lapislbluedye", new int[]{351,4});
		itemLookup.put("lapisbluedye", new int[]{351,4});
		itemLookup.put("lapislazulidye", new int[]{351,4});
		itemLookup.put("lapisldye", new int[]{351,4});
		itemLookup.put("lapisdye", new int[]{351,4});
		itemLookup.put("bluecolour", new int[]{351,4});
		itemLookup.put("bluecolor", new int[]{351,4});
		itemLookup.put("bluedye", new int[]{351,4});
		itemLookup.put("purplecolour", new int[]{351,5});
		itemLookup.put("purplecolor", new int[]{351,5});
		itemLookup.put("purpledye", new int[]{351,5});
		itemLookup.put("cyancolour", new int[]{351,6});
		itemLookup.put("cyancolor", new int[]{351,6});
		itemLookup.put("cyandye", new int[]{351,6});
		itemLookup.put("lightgraycolour", new int[]{351,7});
		itemLookup.put("lightgraycolor", new int[]{351,7});
		itemLookup.put("lightgraydye", new int[]{351,7});
		itemLookup.put("lgraycolour", new int[]{351,7});
		itemLookup.put("lgraycolor", new int[]{351,7});
		itemLookup.put("lgraydye", new int[]{351,7});
		itemLookup.put("graycolour", new int[]{351,8});
		itemLookup.put("graycolor", new int[]{351,8});
		itemLookup.put("graydye", new int[]{351,8});
		itemLookup.put("pinkcolour", new int[]{351,9});
		itemLookup.put("pinkcolor", new int[]{351,9});
		itemLookup.put("pinkdye", new int[]{351,9});
		itemLookup.put("limecolour", new int[]{351,10});
		itemLookup.put("limecolor", new int[]{351,10});
		itemLookup.put("limedye", new int[]{351,10});
		itemLookup.put("dandelionyellow", new int[]{351,11});
		itemLookup.put("dandelionyellowcolour", new int[]{351,11});
		itemLookup.put("dandelionyellowcolor", new int[]{351,11});
		itemLookup.put("dandelionyellowdye", new int[]{351,11});
		itemLookup.put("yellowdandelion", new int[]{351,11});
		itemLookup.put("yellowdandelioncolour", new int[]{351,11});
		itemLookup.put("yellowdandelioncolor", new int[]{351,11});
		itemLookup.put("yellowdandeliondye", new int[]{351,11});
		itemLookup.put("yellowd", new int[]{351,11});
		itemLookup.put("yellowdcolour", new int[]{351,11});
		itemLookup.put("yellowdcolor", new int[]{351,11});
		itemLookup.put("yellowddye", new int[]{351,11});
		itemLookup.put("dyellow", new int[]{351,11});
		itemLookup.put("dyellowcolour", new int[]{351,11});
		itemLookup.put("dyellowcolor", new int[]{351,11});
		itemLookup.put("dyellowdye", new int[]{351,11});
		itemLookup.put("yellowcolour", new int[]{351,11});
		itemLookup.put("yellowcolor", new int[]{351,11});
		itemLookup.put("yellowdye", new int[]{351,11});
		itemLookup.put("lightbluecolour", new int[]{351,12});
		itemLookup.put("lightbluecolor", new int[]{351,12});
		itemLookup.put("lightbluedye", new int[]{351,12});
		itemLookup.put("lbluecolour", new int[]{351,12});
		itemLookup.put("lbluecolor", new int[]{351,12});
		itemLookup.put("lbluedye", new int[]{351,12});
		itemLookup.put("magentacolour", new int[]{351,13});
		itemLookup.put("magentacolor", new int[]{351,13});
		itemLookup.put("magentadye", new int[]{351,13});
		itemLookup.put("orangecolour", new int[]{351,14});
		itemLookup.put("orangecolor", new int[]{351,14});
		itemLookup.put("orangedye", new int[]{351,14});
		itemLookup.put("whitebonemeal", new int[]{351,15});
		itemLookup.put("whitebonemealcolour", new int[]{351,15});
		itemLookup.put("whitebonemealcolor", new int[]{351,15});
		itemLookup.put("whitebonemealdye", new int[]{351,15});
		itemLookup.put("bonemealwhite", new int[]{351,15});
		itemLookup.put("bonemealwhitecolour", new int[]{351,15});
		itemLookup.put("bonemealwhitecolor", new int[]{351,15});
		itemLookup.put("bonemealwhitedye", new int[]{351,15});
		itemLookup.put("whitebonem", new int[]{351,15});
		itemLookup.put("whitebonemcolour", new int[]{351,15});
		itemLookup.put("whitebonemcolor", new int[]{351,15});
		itemLookup.put("whitebonemdye", new int[]{351,15});
		itemLookup.put("bonemwhite", new int[]{351,15});
		itemLookup.put("bonemwhitecolour", new int[]{351,15});
		itemLookup.put("bonemwhitecolor", new int[]{351,15});
		itemLookup.put("bonemwhitedye", new int[]{351,15});
		itemLookup.put("bonemeal", new int[]{351,15});
		itemLookup.put("bonemealcolour", new int[]{351,15});
		itemLookup.put("bonemealcolor", new int[]{351,15});
		itemLookup.put("bonemealdye", new int[]{351,15});
		itemLookup.put("bonem", new int[]{351,15});
		itemLookup.put("bonemcolour", new int[]{351,15});
		itemLookup.put("bonemcolor", new int[]{351,15});
		itemLookup.put("bonemdye", new int[]{351,15});
		itemLookup.put("whitecolour", new int[]{351,15});
		itemLookup.put("whitecolor", new int[]{351,15});
		itemLookup.put("whitedye", new int[]{351,15});
		itemLookup.put("bone", new int[]{352,0});
		itemLookup.put("sugar", new int[]{353,0});
		itemLookup.put("cake", new int[]{354,0});
		itemLookup.put("bed", new int[]{355,0});
		itemLookup.put("repeater", new int[]{356,0});
		itemLookup.put("repeat", new int[]{356,0});
		itemLookup.put("delayer", new int[]{356,0});
		itemLookup.put("delay", new int[]{356,0});
		itemLookup.put("dioder", new int[]{356,0});
		itemLookup.put("diode", new int[]{356,0});
		itemLookup.put("cookie", new int[]{357,0});
		itemLookup.put("map", new int[]{358,0});
		itemLookup.put("chart", new int[]{358,0});
		itemLookup.put("goldmusicrecord", new int[]{2256,0});
		itemLookup.put("goldmusicdisk", new int[]{2256,0});
		itemLookup.put("goldmusiccd", new int[]{2256,0});
		itemLookup.put("gomusicrecord", new int[]{2256,0});
		itemLookup.put("gomusicdisk", new int[]{2256,0});
		itemLookup.put("gomusiccd", new int[]{2256,0});
		itemLookup.put("goldmrecord", new int[]{2256,0});
		itemLookup.put("goldmdisk", new int[]{2256,0});
		itemLookup.put("goldmcd", new int[]{2256,0});
		itemLookup.put("gomrecord", new int[]{2256,0});
		itemLookup.put("gomdisk", new int[]{2256,0});
		itemLookup.put("gomcd", new int[]{2256,0});
		itemLookup.put("goldrecord", new int[]{2256,0});
		itemLookup.put("golddisk", new int[]{2256,0});
		itemLookup.put("goldcd", new int[]{2256,0});
		itemLookup.put("gorecord", new int[]{2256,0});
		itemLookup.put("godisk", new int[]{2256,0});
		itemLookup.put("gocd", new int[]{2256,0});
		itemLookup.put("greenmusicrecord", new int[]{2257,0});
		itemLookup.put("greenmusicdisk", new int[]{2257,0});
		itemLookup.put("greenmusiccd", new int[]{2257,0});
		itemLookup.put("grmusicrecord", new int[]{2257,0});
		itemLookup.put("grmusicdisk", new int[]{2257,0});
		itemLookup.put("grmusiccd", new int[]{2257,0});
		itemLookup.put("greenmrecord", new int[]{2257,0});
		itemLookup.put("greenmdisk", new int[]{2257,0});
		itemLookup.put("greenmcd", new int[]{2257,0});
		itemLookup.put("grmrecord", new int[]{2257,0});
		itemLookup.put("grmdisk", new int[]{2257,0});
		itemLookup.put("grmcd", new int[]{2257,0});
		itemLookup.put("greenrecord", new int[]{2257,0});
		itemLookup.put("greendisk", new int[]{2257,0});
		itemLookup.put("greencd", new int[]{2257,0});
		itemLookup.put("grrecord", new int[]{2257,0});
		itemLookup.put("grdisk", new int[]{2257,0});
		itemLookup.put("grcd", new int[]{2257,0});

	}
	@Override
	public void onEnable() {
		setItemLookup();
		
		registerQueue = new ArrayList<String>();
		pushStockQueue = new ArrayList<Integer>();
		deregisterQueue = new ArrayList<String>();
		
		reconnectDB();
		getServer().getScheduler().scheduleSyncRepeatingTask(this,
				new CheckForUpdates(con,this), 0, 20 * 60);
		/*
		int pushChestTaskID = getServer().getScheduler().scheduleSyncRepeatingTask(this,
				new PushChestStock(con,this), 0, 20 * 2);*/
				
		PluginManager pm = getServer().getPluginManager();
		//pm.registerEvent(Event.Type.PLAYER_CHAT, this.playerListener,Event.Priority.Normal,this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT,this.playerListener,Event.Priority.Normal,this);
		//pm.registerEvent(Event.Type.PLAYER_INVENTORY, this.playerListener,Event.Priority.Normal,this);
		pm.registerEvent(Event.Type.SIGN_CHANGE, this.blockListener,Event.Priority.High,this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, this.blockListener,Event.Priority.Normal,this);
	}
	

	public ChestShopSignInfo parseSign(Sign sign){
		return parseSign(
				sign.getLine(0),
				sign.getLine(1),
				sign.getLine(2),
				sign.getLine(3));
	}
	public ChestShopSignInfo parseSign(String line1, String line2, String line3, String line4){
		ChestShopSignInfo signInfo = new ChestShopSignInfo();
		
		if(line1.length() == 0){
			return null;
		}
		signInfo.player = line1;
		
		
		try{
			signInfo.quantity = Integer.valueOf(line2);
		} catch(NumberFormatException e){
			return null;
		}
		
		try{
			signInfo.buyPrice = parseSignPrice(line3, "B");
		} catch(NumberFormatException e){
			return null;
		}
		
		try{
			signInfo.sellPrice = parseSignPrice(line3, "S");
		} catch(NumberFormatException e){
			return null;
		}
		
		int itemId = parseItemID(line4);
		if(itemId < 0){
			return null;
		}
		
		signInfo.itemID = itemId;
		signInfo.itemData = parseItemData(line4);
		return signInfo;
	}
	public void pushChestQuantity(int rowID){
		System.out.println("Pushing quantity for row " + rowID);
		int index = pushStockQueue.indexOf(rowID);
		if(index >= 0){
			System.out.println("Removing " + rowID + " from queue");
			pushStockQueue.remove(index);
		}
		ResultSet row = getChestDbRowById(rowID);
		if(row == null){
			System.out.println("DB: Couldn't find record for row number " + rowID);
			return;
		}
		//System.out.println(row);
		World world;
		int totalCount = 0;
		try {
			world = getServer().getWorld(row.getString("world"));
		
			Location chestLocation = new Location(
					world,
					row.getInt("chest_x"),
					row.getInt("chest_y"),
					row.getInt("chest_z"));
					
			ArrayList<Location> locationsToQuery = new ArrayList<Location>();
			
			Location northLocation = chestLocation.clone();
			northLocation.setX(northLocation.getX()+1);
			
			Location southLocation = chestLocation.clone();
			southLocation.setX(southLocation.getX()-1);
			
			Location eastLocation = chestLocation.clone();
			eastLocation.setZ(eastLocation.getZ()+1);
			
			Location westLocation = chestLocation.clone();
			westLocation.setZ(westLocation.getZ()-1);
			
			locationsToQuery.add(chestLocation);
			locationsToQuery.add(northLocation);
			locationsToQuery.add(southLocation);
			locationsToQuery.add(eastLocation);
			locationsToQuery.add(westLocation);
			
			int chestsFound = 0; //Once we find two, we know we won't find any more
			
			for(int i = 0; i < 5; i ++){
				if(chestsFound == 2){
					break;
				}
				
				Block chestBlock = world.getBlockAt(locationsToQuery.get(i));
				//System.out.println("Searching " + chestBlock.getX() +"," + 
				//		chestBlock.getY() + "," + chestBlock.getZ());
				if(chestBlock.getTypeId() == 54){
					//System.out.println("Found chest");
					chestsFound++;
					Chest chest = (Chest) chestBlock.getState();
					Inventory chestInventory = chest.getInventory();
					ItemStack[] items = chestInventory.getContents();
					//System.out.println("Chest Conents");
					for(int j = 0; j < items.length; j++){
						//System.out.println("Index " + j);
						if(items[j] != null){
							
							//System.out.println(items[j].getTypeId());
							if(items[j].getTypeId() == row.getInt("item_id")){
								//System.out.println("Adding amount " + items[j].getAmount());
								totalCount += items[j].getAmount();
							}
						}
					}
				} else {
					//System.out.println("No chest");
				}
			}
			updateDbWithChestStock(rowID,totalCount);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//notifyPlayer(playerName, "Stock quantity updated!");
		
	}
	
	
	public boolean playerHasChests(String playername){
		String query = "SELECT player FROM csc_chests WHERE player ='" + playername +"'";
		Statement stat;
		try {
			stat = con.createStatement();
			ResultSet rs = stat.executeQuery(query);
			
			while(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public String getPlayerNameByChestID(int id){
		String query = "SELECT player FROM csc_chests WHERE id = " + id;
		Statement stat;
		try {
			stat = con.createStatement();
			ResultSet rs = stat.executeQuery(query);
			
			while(rs.next()){
				return rs.getString("player");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	public float parseSignPrice(String signLine,String type){

		Pattern buyPattern = Pattern.compile("[bB]\\s*([0-9\\.]+)");
		Pattern sellPattern = Pattern.compile("[sS]\\s*([0-9\\.]+)");
		Pattern sellPattern2 = Pattern.compile("([0-9\\.]+)\\s*[sS]");
		if(type.equalsIgnoreCase("b")){
			Matcher matcher = buyPattern.matcher(signLine);
			if(!matcher.find()){
				return -1;
			} else {
				return Float.parseFloat(matcher.group(1));
			}
		}
		if(type.equalsIgnoreCase("s")){
			Matcher matcher = sellPattern.matcher(signLine);
			Matcher matcher2 = sellPattern2.matcher(signLine);
			if(!matcher.find()){
				if(!matcher2.find()){
					return -1;
				} else {
					return Float.parseFloat(matcher2.group(1));
				}
			} else {
				return Float.parseFloat(matcher.group(1));
			}
		}
		
		return -1;
	}
	
	public int parseItemID(String signLine){
		Pattern itemTypePattern = Pattern.compile("([A-Za-z\\_]+):?(\\S+)?");
		
		Matcher itemTypeMatcher = itemTypePattern.matcher(signLine);
		
		String itemName;
		if(itemTypeMatcher.find()){
			itemName = itemTypeMatcher.group(1).toLowerCase();
			itemName = itemName.replace("_", "");
			//System.out.println("Found item name [" + itemName + "] in sign");
			if(itemLookup.containsKey(itemName)){
				int[] itemInfo = itemLookup.get(itemName);
				//System.out.println("Found item id " + itemInfo[0]);
				return itemInfo[0];
			}
		}
		return -1;
	}
	
	public String parseItemData(String signLine){
		Pattern itemTypePattern = Pattern.compile("([A-Z\\_]+):(\\S+)?");
		
		Matcher itemTypeMatcher = itemTypePattern.matcher(signLine);
		
		
		String itemData = "";
		if(itemTypeMatcher.find()){
			if(itemTypeMatcher.group(2) != null){
				itemData = itemTypeMatcher.group(2);
			}
		}
		return itemData;
	}
	public boolean registerChest(ChestShopSignInfo signInfo, Location chestLocation,Location signLocation){
		if(signInfo == null){
			return false;
		}
		System.out.println("Registering with " + signInfo);
		if(getChestIDByLocation(chestLocation.getBlockX(),chestLocation.getBlockY(),chestLocation.getBlockZ()) != 0){
			notifyPlayer(signInfo.player,"This chest is already registered");
			return false;
		}
		
		if(!playerHasChests(signInfo.player)){
			notifyPlayer(signInfo.player,"Manage your chest prices at http://bit.ly/iF9RoA");
		}
		String registerQuery = "INSERT INTO csc_chests SET " +
		"player = ?, " +	//1
		"chest_x = ?, " +	//2
		"chest_y = ?, " +	//3
		"chest_z = ?, " +	//4
		"sign_x = ?, " +	//5
		"sign_y = ?, " +	//6
		"sign_z = ?, " +	//7
		"item_id = ?," +	//8
		"item_quantity = ?, " +	//9
		"buy_price = ?, " +	//10
		"sell_price = ?," +	//11
		"world = ?," +		//12
		"item_data = ?, "+	//13
		"per_unit_buy = ? ,"+//14
		"per_unit_sell = ? ,"+//15
		"date_created = NOW(), last_updated = NOW()";

		String updateChestQuery = "UPDATE csc_chests,csc_markets SET market_id = csc_markets.id " +
"WHERE " +
"	csc_chests.`chest_x` > csc_markets.x1 && csc_chests.`chest_x` < csc_markets.x2 && " +
"	csc_chests.`chest_z` > csc_markets.z1 && csc_chests.`chest_z` < csc_markets.z2 ";
		try {
			PreparedStatement statement = con.prepareStatement(registerQuery);
			statement.setString(1,signInfo.player);
			statement.setInt(2,chestLocation.getBlockX());
			statement.setInt(3,chestLocation.getBlockY());
			statement.setInt(4,chestLocation.getBlockZ());
			statement.setInt(5,signLocation.getBlockX());
			statement.setInt(6,signLocation.getBlockY());
			statement.setInt(7,signLocation.getBlockZ());
			statement.setInt(8,signInfo.itemID);
			statement.setInt(9,signInfo.quantity);
			statement.setFloat(10, signInfo.buyPrice);
			statement.setFloat(11,signInfo.sellPrice);
			statement.setString(12,chestLocation.getWorld().getName());
			statement.setString(13,signInfo.itemData);
			statement.setFloat(14,signInfo.buyPrice / signInfo.quantity);
			statement.setFloat(15,signInfo.sellPrice / signInfo.quantity);
			statement.executeUpdate();
			con.prepareStatement(updateChestQuery).executeUpdate();
			pushChestQuantity(
					getChestIDByLocation(chestLocation.getBlockX(), chestLocation.getBlockY(), chestLocation.getBlockZ())
			);
			notifyPlayer(signInfo.player, "Chest registered!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			notifyPlayer(signInfo.player,"Couldn't update database");
		}
		return true;
	}
	public boolean registerChestOld(String playerName,Location chestLocation){
		Location signLocation = chestLocation.clone();
		signLocation.setY(signLocation.getY() + 1);
		
		Block signBlock = chestLocation.getWorld().getBlockAt(signLocation);
		//System.out.println("DB: Target block type: " + signBlock.getTypeId());
		if(signBlock.getTypeId() != 68){
			
			notifyPlayer(playerName, "Cannot find the sign for this chest. Sign should be placed above chest");
			return false;
		}
		
		Sign sign = (Sign) signBlock.getState();

		String nameOnSign = sign.getLine(0);
		if(!nameOnSign.equalsIgnoreCase(playerName)){
			notifyPlayer(playerName,"This chest is not yours");
			return false;
		}
		
		int itemQuantity = Integer.parseInt(sign.getLine(1));
		float buyPrice = -1;
		float sellPrice = -1;
		
		String priceLine = sign.getLine(2);
		
		Pattern p = Pattern.compile("(B (\\S+))?\\s?:?\\s?(S (\\S+))?");
		
		Matcher m = p.matcher(priceLine);
		
		if(m.find()){
			//int groupCount = m.groupCount();
			//System.out.println("Group count is " + groupCount);
			
			//System.out.println(m.group(1));
			//System.out.println(m.group(2));
			//System.out.println(m.group(3));
			//System.out.println(m.group(4));
			if(m.group(1) != null){
				buyPrice = Float.parseFloat(m.group(2));
			}
			if(m.group(3) != null){
				sellPrice = Float.parseFloat(m.group(4));
			}
			
		} else {
			notifyPlayer(playerName,"Could not determine the Buy/Sell prices. Is it formatted correctly?");
			return false;
		}
		
		int itemTypeID = parseItemID(sign.getLine(3));
		String itemData = parseItemData(sign.getLine(3));
		
		
		if(itemTypeID == -1){
			notifyPlayer(playerName,"Could not determine item type");
			return false;
		}
			
		String registerQuery = "INSERT INTO csc_chests SET " +
				"player = ?, " +	//1
				"chest_x = ?, " +	//2
				"chest_y = ?, " +	//3
				"chest_z = ?, " +	//4
				"sign_x = ?, " +	//5
				"sign_y = ?, " +	//6
				"sign_z = ?, " +	//7
				"item_id = ?," +	//8
				"item_quantity = ?, " +	//9
				"buy_price = ?, " +	//10
				"sell_price = ?," +	//11
				"world = ?," +		//12
				"item_data = ?, "+	//13
				"per_unit_buy = ? ,"+//14
				"per_unit_sell = ? ,"+//15
				"date_created = NOW(), last_updated = NOW()";

		try {
			PreparedStatement statement = con.prepareStatement(registerQuery);
			statement.setString(1,playerName);
			statement.setInt(2,chestLocation.getBlockX());
			statement.setInt(3,chestLocation.getBlockY());
			statement.setInt(4,chestLocation.getBlockZ());
			statement.setInt(5,signLocation.getBlockX());
			statement.setInt(6,signLocation.getBlockY());
			statement.setInt(7,signLocation.getBlockZ());
			statement.setInt(8,itemTypeID);
			statement.setInt(9,itemQuantity);
			statement.setFloat(10, buyPrice);
			statement.setFloat(11,sellPrice);
			statement.setString(12,chestLocation.getWorld().getName());
			statement.setString(13,itemData);
			statement.setFloat(14,buyPrice / itemQuantity);
			statement.setFloat(15,sellPrice / itemQuantity);
			statement.executeUpdate();
			pushChestQuantity(
					getChestIDByLocation(chestLocation.getBlockX(), chestLocation.getBlockY(), chestLocation.getBlockZ())
			);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			notifyPlayer(playerName,"Couldn't update database");
		}
		return true;
	}
	
	public ResultSet getChestDbRowById(int id){
		String query = "SELECT * FROM csc_chests WHERE id = " + id;
		Statement stat;
		
		try {
			stat = con.createStatement();
			ResultSet rs = stat.executeQuery(query);
			
			while(rs.next()){
				return rs;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int getChestIDBySignLocation(int x, int y, int z){
		String query = "SELECT id FROM csc_chests WHERE sign_x = " + x +
			" && sign_y = " + y + " && sign_z = " + z;
		Statement stat;
		//System.out.println(query);
		try {
			stat = con.createStatement();
			ResultSet rs = stat.executeQuery(query);
			
			while(rs.next()){
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int getChestIDByLocation(int x, int y, int z){
		String query = "SELECT id FROM csc_chests WHERE chest_x = " + x +
			" && chest_y = " + y + " && chest_z = " + z;
		Statement stat;
		
		try {
			stat = con.createStatement();
			ResultSet rs = stat.executeQuery(query);
			
			while(rs.next()){
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public void updateDbWithChestStock(int rowID, int quantity){
		String query = "UPDATE csc_chests SET chest_stock = " + quantity +
			" WHERE id = " + rowID;

		try {
			con.createStatement().executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void reconnectDB(){
		try {
			System.out.println("Loading MySQL driver");
			Class.forName("com.mysql.jdbc.Driver");
			String url =
				"jdbc:mysql://host:3306/database";

			 //Get a connection to the database for a
			 // user named root with a blank password.
			 // This user is the default administrator
			 // having full privileges to do anything.
			System.out.println("Connect to ChestStockChecker DB");
			 con =
			 DriverManager.getConnection(
			 url,"username", "password");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void notifyPlayer(String playerName, String message){
		System.out.println("ChestStock message to " + playerName + ": " + message);
		for(World world: getServer().getWorlds()){
			for(Player player: world.getPlayers()){
				if(player.getName().equalsIgnoreCase(playerName)){
					player.sendMessage("§a[ChestStock]§f " + message);
					return;
				}
			}
		}
	}
	public void updateChestBoard(int boardx, int boardy, int boardz, int quantity, float buyPrice, float sellPrice,String playerName,String worldName){
		World world = getServer().getWorld(worldName);
		Location signLocation = new Location(world, boardx, boardy, boardz);
		
		Block signBlock = world.getBlockAt(signLocation);
		if(signBlock.getTypeId() != 68){
			System.out.println("We block type [" + signBlock.getTypeId() + 
					"at location " + boardx + "," + boardy + "," + boardz +
					" is not a sign!");
			return;
		}
		
		DecimalFormat df = new DecimalFormat("0.00");


		
		Sign sign = (Sign) signBlock.getState();
		sign.setLine(1,Integer.toString(quantity));
		
		String sellPriceString = "";
		if(sellPrice - Math.round(sellPrice) == 0){
			sellPriceString = "S " + (int) sellPrice;
		} else {
			sellPriceString = "S " + df.format(sellPrice);
		}
		
		String buyPriceString = "";
		if(buyPrice - Math.round(buyPrice) == 0){
			buyPriceString = "B " + (int) buyPrice;
		} else {
			buyPriceString = "B " + df.format(buyPrice);
		}
		
		
		if(buyPrice < 0 && sellPrice > 0){
			sign.setLine(2, sellPriceString);
		} else if(buyPrice > 0 && sellPrice < 0){
			sign.setLine(2, buyPriceString);
		} else {
			sign.setLine(2, buyPriceString + " : " + sellPriceString);
			
		}
		sign.update(true);
	}
	
	/**
	 * Finds all registered chests for a user, and then
	 * pushes the price, item, quantity and stock to the DB
	 * @param username
	 */
	public void pushAllForUser(String username){
		String query = "SELECT * FROM csc_chests WHERE player = '" + username + "'";
		
		Statement stat;
		try {
			
			stat = con.createStatement();
			ResultSet rs = stat.executeQuery(query);
			String updateQuery = "UPDATE csc_chests SET " +
					"buy_price = ?," +	//1
					"sell_price = ?," +	//2
					"per_unit_buy = ?,"+//3
					"per_unit_sell = ?," +//4
					"item_quantity = ?," +	//5
					"item_id = ?," +			//6
					"item_data = ?," + 		//7
					"dirty = 0 WHERE id = ?";//8
			System.out.println(updateQuery);
			PreparedStatement updateStatement = con.prepareStatement(updateQuery);
			
			while(rs.next()){
				int x,y,z, quantity;
				float buyPrice, sellPrice;
				int rowID = rs.getInt("id");
				x = rs.getInt("sign_x");
				y = rs.getInt("sign_y");
				z = rs.getInt("sign_z");
				World world = getServer().getWorld(rs.getString("world"));
				Location signLocation = new Location(world,x,y,z);
				Block signBlock = world.getBlockAt(signLocation);
				
				
				if(signBlock.getTypeId() != 68){
					notifyPlayer(username, "Cannot find the sign for one of your chests at " + x + "," + y + "," + z);
					continue;
				}
				
				Sign sign = (Sign) signBlock.getState();

				String priceLine = sign.getLine(2);
				buyPrice = parseSignPrice(priceLine, "B");
				sellPrice = parseSignPrice(priceLine,"S");
				quantity = Integer.valueOf(sign.getLine(1));
				
				int itemID = parseItemID(sign.getLine(3));
				if(itemID < 0){
					notifyPlayer(username,"Could not determine item type from sign at " + x + "," + y + "," + z);
					continue;
				}
				String itemData = parseItemData(sign.getLine(3));
				updateStatement.setFloat(1, buyPrice);
				updateStatement.setFloat(2, sellPrice);
				updateStatement.setFloat(3, buyPrice / quantity);
				updateStatement.setFloat(4, sellPrice / quantity);
				updateStatement.setInt(5,quantity);
				updateStatement.setInt(6,itemID);
				updateStatement.setString(7,itemData);
				updateStatement.setInt(8, rowID);
				
				updateStatement.execute();
				pushChestQuantity(rowID);
				//player.sendMessage("You already have a chest! It is at " + x + "," + y + "," + z);
				//return true;

			}
			notifyPlayer(username,"Your chest prices have been updated!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			reconnectDB();
		}
	}
	public void deleteRow(int rowID){
		String query = "DELETE FROM csc_chests WHERE id = " + rowID + " LIMIT 1";
		try {
			con.createStatement().executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void pullUpdatesForUser(String username){
		
		//System.out.println("Running CheckForUpdates()");
		String findDirty;
		if(username == null){
			findDirty = "SELECT * FROM csc_chests WHERE dirty = 1";
		} else {
			findDirty = "SELECT * FROM csc_chests WHERE dirty = 1 && player = '" + username + "'";
		}
		//System.out.println(findDirty);
		Statement stat;
		try {
			
			stat = con.createStatement();
			ResultSet rs = stat.executeQuery(findDirty);
			
			while(rs.next()){
				int x,y,z, quantity;
				float buyPrice, sellPrice;
				String playerName,world;
				x = rs.getInt("sign_x");
				y = rs.getInt("sign_y");
				z = rs.getInt("sign_z");
				quantity = rs.getInt("item_quantity");
				buyPrice = rs.getFloat("buy_price");
				sellPrice = rs.getFloat("sell_price");
				playerName = rs.getString("player");
				world = rs.getString("world");
				updateChestBoard(x,y,z,quantity,buyPrice,sellPrice,playerName,world);
				
				String updateQuery = "UPDATE csc_chests SET dirty = 0 WHERE id = " + rs.getInt("id");
				con.createStatement().executeUpdate(updateQuery);
				notifyPlayer(playerName,"Your chest prices have been updated!");
				//player.sendMessage("You already have a chest! It is at " + x + "," + y + "," + z);
				//return true;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			reconnectDB();
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		String commandName = command.getName().toLowerCase();
		//System.out.println(commandName);
		if (!(sender instanceof Player)){
			System.out.println("Got command from server, doing nothing");
			return false;
		}
		Player player = (Player) sender;
		if(commandName.equalsIgnoreCase("cs_reg")){
			
			notifyPlayer(player.getName(), "Left click on the chest to register");
			if(!registerQueue.contains(player.getName())){
				System.out.println("Adding user [" + player.getName() + "] to reg queue");
				registerQueue.add(player.getName());
			}
			return true;
		}
		if(commandName.equalsIgnoreCase("cs_dreg")){
			
			notifyPlayer(player.getName(), "Left click on the chest to deregister");
			if(!registerQueue.contains(player.getName())){
				System.out.println("Adding user [" + player.getName() + "] to dereg queue");
				deregisterQueue.add(player.getName());
			}
			return true;
		}
		if(commandName.equalsIgnoreCase("cs_gp")){
			pullUpdatesForUser(player.getName());
			return true;
		}
		if(commandName.equalsIgnoreCase("cs_pa")){
			pushAllForUser(player.getName());
			return true;
		}
		return false;
	}
	public void enqueueChestStockUpdate(int rowID) {
		if(pushStockQueue.contains(rowID)){
			System.out.println("row ID " + rowID + " already scheduled for stock update");
		} else {
			System.out.println("Scheduling stock stock update for rowID [" + rowID +"]");
			pushStockQueue.add(rowID);
			getServer().getScheduler().scheduleSyncDelayedTask(this, new PushChestStock(this, rowID), 20 * 15);
		}
	}
	public class CheckForUpdates implements Runnable {
		
		private ChestStockChecker plugin;
		
		
		
		public CheckForUpdates(Connection connection,ChestStockChecker plug){
			
			plugin = plug;
			
			
		}
		@Override
		public void run() {
			plugin.pullUpdatesForUser(null);
		}
	}
	public class PushChestStock implements Runnable {
		
		private ChestStockChecker plugin;
		private int rowID;
		
		
		public PushChestStock(ChestStockChecker plug,int id){
			
			rowID = id;
			plugin = plug;
			
			
		}
		@Override
		public void run() {
			plugin.pushChestQuantity(rowID);
		}
	}
	
}
