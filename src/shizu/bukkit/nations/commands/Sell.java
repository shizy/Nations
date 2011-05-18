package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.object.Plot;
import shizu.bukkit.nations.object.User;

public class Sell extends NationsCommand {

	Nations plugin;
	
	public Sell(Nations instance) {
		
		super("sell");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return resellPlot(user);
	}

	/**
	 * Flags a Plot as being available for resale by the commanding
	 * User/Nation.
	 * 
	 * @param user The User reselling the Plot
	 * @return true if the Plot resale status was toggled, false
	 * 		   otherwise
	 */
	public boolean resellPlot(User user) {
		
		String locKey = user.getLocationKey();
		Plot plot = plugin.plotManager.getPlotAtUser(user);
		
		if (!plugin.plotManager.exists(locKey)) {
			
			user.message("No plot exists here to resell!");
			return false;
		}
		
		if (plugin.userManager.isLeader(user) && plot.getOwner().equals(user.getNation())) {
			
			plot.toggleSaleStatus();
			plugin.plotManager.showBoundaries(plot);
			if (plot.getRentStatus()) { plot.toggleRentStatus(); }
			if (plot.getSaleStatus()) {
				user.message("Plot at " + locKey + " is now for sale!");
			} else {
				user.message("Plot at " + locKey + " is no longer for sale!");
			}
			
			return true;
		} else {
			
			user.message("You must be the leader of this nation to resell this plot!");
			return false;
		}
	}
}
