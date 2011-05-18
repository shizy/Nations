package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.object.Plot;
import shizu.bukkit.nations.object.User;

public class Raze extends NationsCommand {

	Nations plugin;
	
	public Raze(Nations instance) {
		
		super("raze");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return razePlot(user);
	}

	/**
	 * Destroys the Plot and ownership settings for the commanding 
	 * User/Nation.
	 * 
	 * @param user The User destroying the Plot
	 * @return true if the Plot was destroyed, false otherwise
	 */
	public boolean razePlot(User user) {
		
		String locKey = user.getLocationKey();
		Plot plot = plugin.plotManager.getPlotAtUser(user);
		
		if (!plugin.plotManager.exists(locKey)) {
			
			user.message("No plot exists here to raze!");
			return false;
		}
		
		if (plugin.userManager.isLeader(user) && plot.getOwner().equals(user.getNation())) {
			
			plugin.plotManager.collection.remove(locKey);
			plugin.plotManager.deleteObject(locKey);
			plugin.userManager.setLocationDescriptionForAll(locKey);
			user.setCurrentLocationDescription("");
			user.message("Plot at " + locKey + " razed!");
			return true;
			
		} else {
			
			user.message("You must be the leader of this nation to raze this plot!");
			return false;
		}
	}
}
