package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.enums.GroupMemberType;
import shizu.bukkit.nations.object.Group;
import shizu.bukkit.nations.object.User;

public class Tax extends NationsCommand {

	Nations plugin;
	
	public Tax(Nations instance) {
		
		super("taxnation");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return setTaxRate(user, Double.parseDouble(implode(args, "")));
	}

	/**
	 * Sets the tax rate for the nation
	 * 
	 * @param user The commanding user
	 * @param rate The new tax rate
	 * @return true if the tax rate was set, false otherwise
	 */
	public Boolean setTaxRate(User user, double rate)
	{

		if (!user.getNation().equals(""))
		{

			Group nation = plugin.groupManager.getGroup(user.getNation());

			if (nation.hasLeader(user.getName()))
			{

				nation.setTax(rate);
				plugin.groupManager.messageGroup(nation.getName(), GroupMemberType.MEMBERS, "The " + user.getNation()
						+ " tax rate is now %" + nation.getTax());
				return true;

			} else
			{

				user.message("You must be a leader to set the tax rate!");
				return false;
			}
		} else
		{

			user.message("You must be the leader of a nation to set a tax rate!");
			return false;
		}
	}
}
