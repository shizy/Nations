package shizu.bukkit.nations.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.object.Plot;
import shizu.bukkit.nations.object.User;

public class Claim extends NationsCommand {

	Nations plugin;
	
	public Claim(Nations instance) {
		
		super("claim");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return claimPlot(user);
	}

	/**
	 * Creates a Plot (if none exists), gives ownership to the 
	 * commanding User/Nation, then loads the Plot into 'collection'
	 * and saves it to the data source.
	 * 
	 * @param user The User claiming the Plot
	 * @return true if the Plot was created, false otherwise
	 */
	public boolean claimPlot(User user) {
		
		String locKey = user.getLocationKey();

		if (plugin.plotManager.exists(locKey)) { 
			
			user.message("This plot has already been claimed!");
			return false; 
		}
		
		if (plugin.userManager.isLeader(user)) {
			
			Location loc = user.getLocation();
			Plot plot = new Plot(plugin.getWorld(), (int) loc.getX(), (int) loc.getZ());
			plot.setOwner(user.getNation());
			plugin.plotManager.collection.put(locKey, plot);
			plugin.userManager.setLocationDescriptionForAll(locKey);
			plugin.plotManager.saveObject(locKey);
			plugin.groupManager.getGroup(user.getNation()).addPlot(locKey);
			plugin.plotManager.showBoundaries(plot);
			user.message("Plot at " + locKey + " claimed!");
			return true;
			
		} else {
			
			user.message("You must be the leader of a nation to claim a plot!");
			return false;
		}
	}
}
