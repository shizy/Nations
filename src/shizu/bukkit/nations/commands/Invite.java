package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.object.User;

public class Invite extends NationsCommand {

	Nations plugin;
	
	public Invite(Nations instance) {
		
		super("invite");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return inviteUserToNation(user, implode(args, ""));
	}

	/**
	 * Invites a User to the commanding User's nation.
	 * 
	 * @param user The commanding User
	 * @param invited The User to invite
	 * @return true if an invitation was sent, false otherwise
	 */
	public boolean inviteUserToNation(User user, String invited) {
		
		if (!plugin.userManager.exists(invited)) {
			
			user.message("That user does not exist or is not registered!");
			return false;
		}
		
		User invitee = plugin.userManager.getUser(invited);
		
		if (plugin.userManager.isLeader(user)) {
			
			if (invitee.getNation().equals("")) {
				
				invitee.addInvite(user.getNation());
				user.message("Invitation sent!");
				return true;
			} else {
				user.message("This user already belongs to a nation!");
				return false;
			}
		} else {
			user.message("You must be a leader to invite a user to the nation!");
			return false;
		}
	}
}
