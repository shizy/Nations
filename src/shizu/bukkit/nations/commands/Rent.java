package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.object.Plot;
import shizu.bukkit.nations.object.User;

public class Rent extends NationsCommand {

	Nations plugin;
	
	public Rent(Nations instance) {
		
		super("rent");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return rentPlot(user);
	}

	/**
	 * Flags a Plot as being available for rent by the commanding
	 * User/Nation.
	 * 
	 * @param user The User renting out the Plot
	 * @return true if the Plot rent status was toggled, false
	 * 		   otherwise
	 */
	public boolean rentPlot(User user) {
		
		String locKey = user.getLocationKey();
		Plot plot = plugin.plotManager.getPlotAtUser(user);
		
		if (!plugin.plotManager.exists(locKey)) {
			
			user.message("No plot exists here to rent!");
			return false;
		}
		
		if (plugin.userManager.isLeader(user) && plot.getOwner().equals(user.getNation())) {
			
			plot.toggleRentStatus();
			plugin.plotManager.showBoundaries(plot);
			if (plot.getSaleStatus()) { plot.toggleSaleStatus(); }
			if (plot.getRentStatus()) {
				user.message("Plot at " + locKey + " is now available for rent!");
			} else {
				user.message("Plot at " + locKey + " is no longer available for rent!");
			}
			
			return true;
		} else {
			
			user.message("You must be the leader of this nation to rent this plot!");
			return false;
		}
	}
}
