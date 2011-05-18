package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.iConomy.iConomy;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.object.Group;
import shizu.bukkit.nations.object.User;

public class Found extends NationsCommand {

	Nations plugin;
	
	public Found(Nations instance) {
		
		super("foundnation");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return foundNation(user, implode(args, " "));
	}

	/**
	 * Creates a nation Group for the commanding User and sets them
	 * in a position of leadership for that Group.
	 * 
	 * @param user The User creating the nation Group
	 * @param name The name of the new nation Group
	 * @return true if the nation was founded, false otherwise
	 */
	public boolean foundNation(User user, String name) {
		
		// TODO: Case insensitive check on nation name
		if (!plugin.groupManager.exists(name)) {
			
			if (user.getNation().equals("")) {
				
				Group group = new Group(name);
				group.addMember(user.getName());
				group.addLeader(user.getName());
				user.setNation(name);
				iConomy.Accounts.create(name);
				plugin.groupManager.collection.put(name, group);
				plugin.groupManager.saveObject(name);
				plugin.messageAll("The Nation of '" + name + "' has been founded!");
				return true;
			} else {
				
				user.message("You are already a member of a nation. You must leave that nation before you can found a new one!");
				return false;
			}
		} else {
			
			user.message("A Nation with that name already exists!");
			return false;
		}	
	}
}
