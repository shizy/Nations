package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.enums.GroupMemberType;
import shizu.bukkit.nations.object.Group;
import shizu.bukkit.nations.object.User;

public class Kick extends NationsCommand {

	Nations plugin;
	
	public Kick(Nations instance) {
		
		super("kick");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return kickUserFromNation(user, implode(args, ""));
	}

	/**
	 * Kicks a User from the commanding User's nation.
	 * 
	 * @param user The commanding User
	 * @param kicked The User to kick
	 * @return true if the User was kicked, false otherwise
	 */
	public Boolean kickUserFromNation(User user, String kicked)
	{

		if (!plugin.userManager.exists(kicked))
		{

			user.message("That user does not exist or is not registered!");
			return false;
		}

		User kickee = plugin.userManager.getUser(kicked);

		if (plugin.userManager.isLeader(user))
		{

			if (kickee.getNation().equals(user.getNation()))
			{

				Group group = plugin.groupManager.getGroup(user.getNation());
				kickee.message("You have been kicked from " + user.getNation()
						+ "!");
				kickee.setNation("");
				group.removeMember(kickee.getName());
				group.removeLeader(kickee.getName());
				plugin.groupManager.messageGroup(user.getNation(), GroupMemberType.MEMBERS, kicked
						+ " has been kicked from the nation!");
				return true;
			} else
			{
				user.message("This user does not belong to your nation, and cannot be kicked!");
				return false;
			}
		} else
		{
			user.message("You must be a leader to kick a user from the nation!");
			return false;
		}
	}
}
