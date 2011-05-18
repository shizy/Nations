package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.object.Group;
import shizu.bukkit.nations.object.User;

public class Leave extends NationsCommand {

	Nations plugin;
	
	public Leave(Nations instance) {
		
		super("leave");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return leaveNation(user);
	}

	/**
	 * Removes a User from the Nation they are currently in.
	 * 
	 * @param user The commanding User
	 * @return true if the user left, false otherwise
	 */
	public boolean leaveNation(User user) {
	
		if (!user.getNation().equals("")) {
			
			Group group = plugin.groupManager.getGroup(user.getNation());
			user.message("You have left " + user.getNation() + "!");
			user.setNation("");
			group.removeMember(user.getName());
			group.removeLeader(user.getName());
			return true;
		} else {
			user.message("You cannot leave a nation you are not a part of!");
		}
		
		return false;
	}
}
