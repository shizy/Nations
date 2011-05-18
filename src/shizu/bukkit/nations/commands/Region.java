package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.object.Plot;
import shizu.bukkit.nations.object.User;

public class Region extends NationsCommand {

	Nations plugin;
	
	public Region(Nations instance) {
		
		super("region");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return setRegion(user, implode(args, ""));
	}

	/**
	 * Renames the regional description of the Plot for the commanding
	 * User/Nation.
	 * 
	 * @param user The user renaming the Plot's region
	 * @param region The new region description
	 * @return true if the Plot's region was renamed, false otherwise
	 */
	public boolean setRegion(User user, String region) {
		
		String locKey = user.getLocationKey();
		Plot plot = plugin.plotManager.getPlotAtUser(user);
		
		if (!plugin.plotManager.exists(locKey)) {
			
			user.message("No plot exists here to set the region name of!");
			return false;
		}
		
		if (plugin.userManager.isLeader(user) && plot.getOwner().equals(user.getNation())) {
			
			plot.setRegion(region);
			plugin.plotManager.saveObject(locKey);
			plugin.userManager.setLocationDescriptionForAll(locKey);
			user.message("Plot at " + locKey + " is now in region: " + region);
			return true;
		} else {
			
			user.message("You must be the leader of this nation to name this region!");
			return false;
		}
	}
}
