package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.enums.GroupMemberType;
import shizu.bukkit.nations.object.User;

public class Accept extends NationsCommand {

	Nations plugin;
	
	public Accept(Nations instance) {
		
		super("accept");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return acceptInvite(user, implode(args, ""));
	}

	/**
	 * Accepts an invite to join a particular nation. Upon acceptance, the user
	 * becomes a member of that nation.
	 * 
	 * @param user The commanding user
	 * @param nation The accepted invitation's nation
	 * @return true if the invitation was accepted, false otherwise
	 */
	public Boolean acceptInvite(User user, String nation) {

		if (!plugin.groupManager.exists(nation)) {
			user.message("That nation does not exist or no longer exists!");
			return false;
		}	

		if (user.hasInvite(nation)) {

			plugin.groupManager.messageGroup(nation, GroupMemberType.MEMBERS, user.getName() + " has joined " + nation + "!");
			plugin.groupManager.joinNation(user, nation);
			user.clearInvites();
			return true;
		} else {
			user.message("You have not been invited to join that nation!");
			return false;
		}
	}
}
