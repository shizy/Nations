package shizu.bukkit.nations.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.commands.*;
import shizu.bukkit.nations.Nations;

public class NationsCommandManager extends NationsCommand {

	Nations plugin;
	SimpleCommandMap cm;
	
	public NationsCommandManager(String name, Nations instance) {
		
		super("naw");
		plugin = instance;
		cm = new SimpleCommandMap(instance.getServer());
		
		cm.register("accept", "", new Accept(plugin));
		cm.register("buy", "", new Buy(plugin));
		cm.register("claim", "", new Claim(plugin));
		cm.register("demote", "", new Demote(plugin));
		cm.register("diplomacy", "", new Diplomacy(plugin));
		cm.register("disband", "", new Disband(plugin));
		cm.register("found", "", new Found(plugin));
		cm.register("help", "", new Help(plugin));
		cm.register("invite", "", new Invite(plugin));
		cm.register("kick", "", new Kick(plugin));
		cm.register("leave", "", new Leave(plugin));
		cm.register("promote", "", new Promote(plugin));
		cm.register("raze", "", new Raze(plugin));
		cm.register("region", "", new Region(plugin));
		cm.register("rename", "", new Rename(plugin));
		cm.register("rent", "", new Rent(plugin));
		cm.register("sell", "", new Sell(plugin));
		cm.register("tax", "", new Tax(plugin));
	}

	//Check for user registration?
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		if (!plugin.userManager.exists(((Player) sender).getDisplayName())) { //ERROR HERE
			
			((Player) sender).sendMessage("You must be registered with Nations At War to use this command!");
			return false;
		} 
		
		return cm.dispatch(sender, implode(args, " "));
	}
}
