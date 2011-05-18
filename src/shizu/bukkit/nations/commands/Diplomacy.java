package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.object.Group;
import shizu.bukkit.nations.object.User;

public class Diplomacy extends NationsCommand {

	Nations plugin;
	
	public Diplomacy(Nations instance) {
		
		super("diplomacy");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		changeStatus(user, args[0], args[1]);
		return true;
	}

	/**
	 * Changes diplomatic relationship with another nation.
	 * 
	 * @param user The User changing the diplomacy of his nation
	 * @param nation The Nation the user wants to change his diplomacy with
	 * @param status The status of the diplomatic relationship (ally, neutral, enemy)
	 */
	public void changeStatus(User user, String nation, String status) {
		
		Group group = plugin.groupManager.getGroup(user.getNation());
		
		if (plugin.groupManager.exists(nation) == true) {
			
			if (status.equalsIgnoreCase("ally")) {
				group.addAlly(nation);
				user.message(nation + " is now your ally.");
			}
			else if (status.equalsIgnoreCase("neutral")) {
				group.addNeutral(nation);
				user.message(nation + " is now neutral.");
			}
			else if (status.equalsIgnoreCase("enemy")) {
				group.addEnemy(nation);
				user.message(nation + " is now your enemy.");
			}
			else {
				user.message("Status parameters: 'ally', 'neutral', and 'enemy'");
				user.message("For example, '/naw diplomacy status Kentucky enemy'");
			}
		}
		
		else {
			user.message(nation + " does not exist.");
		}
	}
}
