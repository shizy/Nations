package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.object.Group;
import shizu.bukkit.nations.object.User;

public class Rename extends NationsCommand {

	Nations plugin;
	
	public Rename(Nations instance) {
		
		super("renamenation");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return renameNation(user, implode(args, ""));
	}
	
	/**
	 * Renames a nation and updates the ownership info for all members and plots in
	 * the nation.
	 * 
	 * @param user The commanding user
	 * @param name The new nation name
	 * @return true if the nation was renamed, false otherwise
	 */
	public boolean renameNation(User user, String name) {
		
		if (plugin.groupManager.exists(name)) {
			
			user.message("A Nation with that name already exists!");
			return false;
		}
		
		if (!user.getNation().equals("")){
			
			Group nation = plugin.groupManager.getGroup(user.getNation());
			
			if (nation.hasLeader(user.getName())) {
				
				plugin.messageAll("The Nation of '" + user.getNation() + "' is now the Nation of '" + name + "'!");
				plugin.groupManager.deleteObject(user.getNation());
				nation.setName(name);
				plugin.groupManager.collection.put(name, nation);
				plugin.groupManager.saveObject(name);
				
				for (String plot : nation.getPlots()) {
					
					plugin.plotManager.getPlot(plot).setOwner(name);
				}
				
				for (String member : nation.getMembers()) {
					
					plugin.userManager.getUser(member).setNation(name);
				}
				
				return true;
				
			} else {
				
				user.message("You must be a leader to rename the nation!");
				return false;
			}
		} else {
			
			user.message("You are not part of a nation, and therefore, cannot rename it!");
			return false;
		}
	}
}
