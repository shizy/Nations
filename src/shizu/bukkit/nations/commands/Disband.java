package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.enums.GroupMemberType;
import shizu.bukkit.nations.object.Group;
import shizu.bukkit.nations.object.User;

public class Disband extends NationsCommand {

	Nations plugin;
	
	public Disband(Nations instance) {
		
		super("disband");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return disbandNation(user, implode(args, ""));
	}

	public boolean disbandNation(User user, String groupKey)
	{		
		// TODO: It may be useful to set this on a timer to allow people to get their affairs in order
		Group group = plugin.groupManager.getGroup(groupKey);

		if (group.hasLeader(user.getName()))
		{
			String disbandStartMsg = "Despair denizens of " + group.getName() + "! Your nation is ending!" ;
			String disbandFinishMsg = group.getName() + " has been lost to the dusts of time!";			
			plugin.groupManager.messageGroup(groupKey, GroupMemberType.MEMBERS, disbandStartMsg);

			// Remove all plots. Should be first so people are not locked out of their stuff.
			Boolean result = plugin.plotManager.razeGroupPlots(group.getPlots());
			if (result)
				plugin.groupManager.messageGroup(groupKey, GroupMemberType.MEMBERS, "All plots razed.");
			else
				plugin.groupManager.messageGroup(groupKey, GroupMemberType.MEMBERS, "Error: Some plots not razed! (GroupManagement.disbandNation)");

			// Remove all leaders.
			for (String leader : group.getLeaders())
			{
				group.removeLeader(leader);
			}
			plugin.groupManager.messageGroup(groupKey, GroupMemberType.MEMBERS, group.getName() +"'s leaders deposed!");
			// Remove all members and let them know how that shit be.
			for (String member : group.getMembers())
			{
				group.removeLeader(member);
				plugin.userManager.getUser(member).message(group.getName() +"is gone. You are thrust into the wilderness alone!");
			}
			plugin.getServer().broadcastMessage(disbandFinishMsg);
			
			return true;
		}
		else
			user.message("You lack the autority to disband a Nation.");
		
		return false;
	}
}
