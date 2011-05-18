package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.enums.GroupMemberType;
import shizu.bukkit.nations.object.Group;
import shizu.bukkit.nations.object.User;

public class Promote extends NationsCommand {

	Nations plugin;
	
	public Promote(Nations instance) {
		
		super("promote");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return promoteUser(user, implode(args, ""));
	}

	/**
	 * Allows leaders to promote other members of the same nation to leader
	 * status.
	 * 
	 * @param user The User that is promoting the member.
	 * @param promoted The User that is being promoted
	 */
	public Boolean promoteUser(User user, String promoted)
	{

		User member = plugin.userManager.getUser(promoted);
		Group nation = plugin.groupManager.getGroup(user.getNation());

		if (!plugin.userManager.exists(promoted))
		{
			user.message("That user does not exist or is not registered!");
			return false;
		}

		if (plugin.userManager.isLeader(user))
		{

			if (user.getNation().equals(member.getNation()))
			{

				nation.addLeader(promoted);
				plugin.groupManager.messageGroup(nation.getName(), GroupMemberType.MEMBERS, promoted
						+ " has been promoted to leadership!");
				return true;
			} else
			{
				user.message("You cannot promote users outside of your nation!");
				return false;
			}
		} else
		{
			user.message("You must be the leader of your nation to promote a member!");
			return false;
		}
	}
}
