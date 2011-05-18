package shizu.bukkit.nations.manager;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.object.NAWObject;
import shizu.bukkit.nations.object.Plot;
import shizu.bukkit.nations.object.User;

/**
 * Manages instances of the Plot class and their interactions
 * between the server and data source.
 * 
 * @author Shizukesa
 */
public class PlotManagement extends Management {
	
	//TODO: check if new claim is an 'outpost'
	
	public PlotManagement(Nations instance) {
		
		super(instance);
		collection = new HashMap<String, NAWObject>();
		type = "plot";
	}
	
	/**
	 * Fetches the Plot for the provided location key, if it exists.
	 * 
	 * @param key The location key of the Plot to get
	 * @return the Plot at the provided location key, 
	 * 		   null if no matching instance exists
	 */
	public Plot getPlot(String key) {
		
		return (exists(key)) ? (Plot) collection.get(key) : null;
	}
	
	/**
	 * Fetches the Plot from a specific location
	 * 
	 * @param loc The location of the Plot
	 * @return the Plot at the given location, null
	 * 		   if no Plot was found
	 */
	public Plot getPlotAtLocation(Location loc) {
	
		String locKey = getLocationKey(loc);
		Plot plot = getPlot(locKey);
		//if (plot == null) { plugin.sendToLog("No plot found at location: " + locKey); } //Usefull for debugging
		return plot;
	}
	
	/**
	 * Fetches the Plot from a User's location
	 * 
	 * @param user The User at the Plot
	 * @return the Plot at the User's location, null
	 * 		   if no Plot was found
	 */
	public Plot getPlotAtUser(User user) {
		
		Plot plot = getPlot(user.getLocationKey());
		if (plot == null) { plugin.sendToLog("No plot found at " + user.getName() + "'s location"); }
		return plot;
	}
	
	/**
	 * Razes all plots associated with a Group.
	 * 
	 * @param plots ArrayList<String> of plots from the group that must be razed.
	 * @return
	 */
	public boolean razeGroupPlots(ArrayList<String> plots)
	{
		Boolean result = true;
		for (String plotKey : plots)
		{
			if(exists(plotKey))
			{
				plugin.userManager.setLocationDescriptionForAll(plotKey);
				collection.remove(plotKey);
				deleteObject(plotKey);
			}
			else
				result = false;
		}
		return result;
	}
	
	/**
	 * Creates torches at the plot's corners to signify its boundaries. If the Plot
	 * is for sale or rent, redstone torches are created instead.
	 * 
	 * @param plot The Plot who's boundaries will be created/shown
	 */
	public void showBoundaries(Plot plot) {
		
		World world = plot.getWorld();
		int x = plot.getX();
		int z = plot.getZ();
		int id = 50;
		
		if (plot.getSaleStatus() || plot.getRentStatus()) {
			
			id = 76; //redstone torch lit
		}
		
		world.getBlockAt(x * 16, world.getHighestBlockYAt(x * 16, z * 16), z * 16).setTypeId(id);
		world.getBlockAt((x * 16) + 15, world.getHighestBlockYAt((x * 16) + 15, z * 16), z * 16).setTypeId(id);
		world.getBlockAt(x * 16, world.getHighestBlockYAt(x * 16, (z * 16) + 15), (z * 16) + 15).setTypeId(id);
		world.getBlockAt((x * 16) + 15, world.getHighestBlockYAt((x * 16) + 15, (z * 16) + 15), (z * 16) + 15).setTypeId(id);
	}
}
