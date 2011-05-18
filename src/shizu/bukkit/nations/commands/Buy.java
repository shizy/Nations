package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.object.Plot;
import shizu.bukkit.nations.object.User;

public class Buy extends NationsCommand {

	Nations plugin;
	
	public Buy(Nations instance) {
		
		super("buy");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return buyPlot(user);
	}

	/**
	 * Purchases or rents a Plot for the commanding User
	 * 
	 * @param user The user purchasing the Plot
	 * @return true if the Plot was purchased, false otherwise
	 */
	public boolean buyPlot(User user) {
		
		String locKey = user.getLocationKey();
		Plot plot = plugin.plotManager.getPlotAtUser(user);
		
		if (!plugin.plotManager.exists(locKey)) {
			
			user.message("No plot exists here to buy!");
			return false;
		}
			
		if (plot.getRentStatus()) {
			
			if (plot.getOwner().equals(user.getNation())) {
				
				plot.setRenter(user.getName());
				plugin.plotManager.saveObject(plot.getLocationKey());
				plot.toggleRentStatus();
				plugin.plotManager.showBoundaries(plot);
				plugin.userManager.setLocationDescriptionForAll(locKey);
				user.message("Plot at " + locKey + " is now being rented!");
				//TODO: Alert the plot's owner
				return true;
			} else {
				
				user.message("You can only rent plots offered from your own nation!");
				return false;
			}
		}
		
		if (plot.getSaleStatus()) {
			
			if (plot.getOwner().equals(user.getNation())) {
			
				user.message("Your nation already owns this land!");
				return false;
			}
			
			plot.setOwner(user.getNation());
			plot.setRenter("");
			plot.setRegion("");
			plugin.plotManager.saveObject(plot.getLocationKey());
			plot.toggleSaleStatus();
			plugin.plotManager.showBoundaries(plot);
			plugin.userManager.setLocationDescriptionForAll(locKey);
			user.message("Plot at " + locKey + " has been purchased!");
			//TODO: Alert the plot's previous owner
			return true;
		}
		
		user.message("This plot is not available for purchase!");
		return false;
	}
}
