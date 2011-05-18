package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.enums.GroupMemberType;
import shizu.bukkit.nations.object.Group;
import shizu.bukkit.nations.object.User;

public class Demote extends NationsCommand {

	Nations plugin;
	
	public Demote(Nations instance) {
		
		super("demote");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return demoteUser(user, implode(args, ""));
	}

	/**
	 * Allows leaders to demote other members of the same nation from leader
	 * status.
	 * 
	 * @param user The User that is demoting the leader.
	 * @param promoted The User that is being demoted
	 */
	public Boolean demoteUser(User user, String demoted)
	{

		User member = plugin.userManager.getUser(demoted);
		Group nation = plugin.groupManager.getGroup(user.getNation());

		if (!plugin.userManager.exists(demoted))
		{
			user.message("That user does not exist or is not registered!");
			return false;
		}

		if (plugin.userManager.isLeader(user))
		{

			if (user.getNation().equals(member.getNation()))
			{

				nation.removeLeader(demoted);
				plugin.groupManager.messageGroup(nation.getName(), GroupMemberType.MEMBERS, demoted
						+ " has been demoted from leadership!");
				return true;
			} else
			{
				user.message("You cannot demote users outside of your own nation!");
				return false;
			}
		} else
		{
			user.message("You must be the leader of your nation to demote a leader!");
			return false;
		}
	}
}
