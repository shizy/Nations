package shizu.bukkit.nations.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.command.NationsCommand;
import shizu.bukkit.nations.object.User;

public class Help extends NationsCommand {

	Nations plugin;
	
	public Help(Nations instance) {
		
		super("help");
		plugin = instance;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		User user = plugin.userManager.getUser(((Player) sender));
		return true;
	}

	/*
	String name = ((Player) sender).getDisplayName();
	User user = (userManager.exists(name)) ? userManager.getUser((Player) sender) : null;
	userCommands = new SimpleCommandMap(this.getServer());
	
	ChatColor yellow = ChatColor.getByCode(14);
	ChatColor white = ChatColor.getByCode(15);
	
	if (user != null) {
		
		if (commandLabel.equalsIgnoreCase("naw")) {
			
			if (args[0].equalsIgnoreCase("add")) {
				userManager.taxUser(name, Double.parseDouble(args[1]));
			}
			
			if (args[0].equalsIgnoreCase("treasury")) {
				user.message("Treasury: $" + iConomy.getAccount(user.getNation()).getHoldings());
			}
			
			if (args[0].equalsIgnoreCase("help")) {
				user.message(yellow + "Main command list: invites, plot, nation, diplomacy");
				user.message(yellow + "For more information, type /naw <command> help");
			}
			
			if (args[0].equalsIgnoreCase("invites")) {
				
				if (args[1].equalsIgnoreCase("help")) {
					user.message(yellow + "To list your invites, just type /naw invites");
					user.message(yellow + "Sub-command list");
					user.message(yellow + "accept <nation>" + white + " - Adds you to the <nation> that invited you.");
					user.message(yellow + "clear" + white + " - Clears all invites from your invite list.");
				}
				
				if (args[1].equalsIgnoreCase("view")) {
					user.viewInvites();
				}
				
				if (args[1].equalsIgnoreCase("accept")) {
					userManager.acceptInvite(user, args[2]);
				}
				
				if (args[1].equalsIgnoreCase("clear")) {
					user.clearInvites();
				}
			}
			
			if (args[0].equalsIgnoreCase("plot")) {
				
				if (args[1].equalsIgnoreCase("help")) {
					user.message(yellow + "Sub-command list");
					user.message(yellow + "claim" + white + " - Claims your nation's ownership on the land you're standing on.");
					user.message(yellow + "raze" + white + " - Removes your nation's claim on the land you're in.");
					user.message(yellow + "sell" + white + " - Allows other nation's to purchase the land you're in.");
					user.message(yellow + "rent" + white + " - Rents the land you're in if it's for sell.");
					user.message(yellow + "buy" + white + " - Buys the land you're in if it's for sell.");
					user.message(yellow + "region <name>" + white + " - Names the plot you're in. (Changes enter/leave message)");
				}
				
				if (args[1].equalsIgnoreCase("claim")) {
					plotManager.claimPlot(user);
				}
				
				if (args[1].equalsIgnoreCase("raze")) {
					plotManager.razePlot(user);
				}
				
				if (args[1].equalsIgnoreCase("sell")) {
					plotManager.resellPlot(user);
				}
				
				if (args[1].equalsIgnoreCase("rent")) {
					plotManager.rentPlot(user);
				}
				
				if (args[1].equalsIgnoreCase("buy")) {
					plotManager.buyPlot(user);
				}
				
				if (args[1].equalsIgnoreCase("region")) {
					plotManager.setRegion(user, args[2]);
				}
			}
			
			if (args[0].equalsIgnoreCase("nation")) {
				
				if (args[1].equalsIgnoreCase("help")) {
					user.message(yellow + "Sub-command list");
					user.message(yellow + "found <nation name>" + white + " - Creates a new nation.");
					user.message(yellow + "invite <user>" + white + " - Invites the <user> to join your nation.");
					user.message(yellow + "kick <member>" + white + " - Kicks the member from the nation.");
					user.message(yellow + "promote <member>" + white + " - Promotes the member to leader status.");
					user.message(yellow + "demote <member>" + white + " - Removes the member's leader status.");
					user.message(yellow + "leave" + white + " - Leaves your current nation.");
					user.message(yellow + "disband" + white + " - Destroys your nation and all the plots it has claimed.");
					user.message(yellow + "rename <nation name>" + white + " - Renames your nation.");
					user.message(yellow + "tax <rate>" + white + " - Sets the percentage of all member income to be stored in treasury.");
				}
				
				if (args[1].equalsIgnoreCase("found")) {
					groupManager.foundNation(user, args[2]);
				}
				
				if (args[1].equalsIgnoreCase("invite")) {
					groupManager.inviteUserToNation(user, args[2]);
				}
				
				if (args[1].equalsIgnoreCase("kick")) {
					groupManager.kickUserFromNation(user, args[2]);
				}
				
				if (args[1].equalsIgnoreCase("promote")) {
					groupManager.promoteUser(user, args[2]);
				}
				
				if (args[1].equalsIgnoreCase("demote")) {
					groupManager.demoteUser(user, args[2]);
				}
				
				if (args[1].equalsIgnoreCase("leave")) {
					groupManager.leaveNation(user);
				}
				
				if (args[1].equalsIgnoreCase("disband")) {
					groupManager.disbandNation(user, user.getNation());
				}
				
				if (args[1].equalsIgnoreCase("rename")) {
					groupManager.renameNation(user, args[2]);
				}
				
				if (args[1].equalsIgnoreCase("tax")) {
					groupManager.setTaxRate(user, Double.valueOf(args[2]));
				}
			}
			*/
			/* Diplomacy Section
			 * 
			 * Info - Lists your nation's allies and enemies
			 * Ally - Allies the subsequent nation
			 *
			if (args[0].equalsIgnoreCase("diplomacy")) {
				
				if (args[1].equalsIgnoreCase("help")) {
					user.message(yellow + "Sub-command list");
					user.message(yellow + "status <nation> <status>" + white + " - Changes diplomatic relationship with <nation>");
					user.message(white + "          - <status> can either be 'ally', 'neutral', or 'enemy'");
				}
				
				if (args[1].equalsIgnoreCase("info")) {
					ArrayList<String> allies = groupManager.getGroup(user.getNation()).getAllies();
					ArrayList<String> enemies = groupManager.getGroup(user.getNation()).getEnemies();
					String allyList = "";
					String enemyList = "";
					
					user.message(ChatColor.getByCode(5) + "YOUR NATION: " + (groupManager.exists(user.getNation()) ? user.getNation() : "No Nation"));
					
					if (allies.size() > 0) {
						for(int i=0;i<allies.size();i++)
						{
							allyList = allyList + allies.get(i) + ", ";
						}
						user.message(ChatColor.getByCode(2) + "Allies: " + allyList.substring(0, allyList.length() - 2) + ".");
					}
					else {
						user.message(ChatColor.getByCode(2) + "Allies: None");
					}
					
					if (enemies.size() > 0) {
						for(int i=0;i<enemies.size();i++)
						{
							enemyList = enemyList + enemies.get(i) + ", ";
						}
						user.message(ChatColor.getByCode(12) + "Enemies: " + enemyList.substring(0, enemyList.length() - 2) + ".");
					}
					else {
						user.message(ChatColor.getByCode(12) + "Enemies: None");
					}
				}
				
				if (args[1].equalsIgnoreCase("status") && userManager.isLeader(user) == true) {
					groupManager.changeStatus(user, args[2], args[3]);
				}
			}
			
		} 
		
		return true;
	} */
}
