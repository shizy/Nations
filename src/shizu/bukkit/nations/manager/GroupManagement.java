package shizu.bukkit.nations.manager;

import java.util.HashMap;

import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.enums.GroupMemberType;
import shizu.bukkit.nations.object.Group;
import shizu.bukkit.nations.object.NAWObject;
import shizu.bukkit.nations.object.User;

/**
 * Manages instances of the Group class and their interactions
 * between the server and data source.
 * 
 * @author Shizukesa
 */
public class GroupManagement extends Management {

	public GroupManagement(Nations instance) {
		
		super(instance);
		collection = new HashMap<String, NAWObject>();
		type = "group";
	}
	
	/**
	 * Fetches the Group for the provided group name, if it exists.
	 * 
	 * @param key The group name of the Group to get
	 * @return the Group with a matching name
	 */
	public Group getGroup(String key) {

		return (exists(key)) ? (Group) collection.get(key) : null;
	}
	

	
	public double getTaxRate(User user) {
		return getGroup(user.getNation()).getTax();
	}
	
	/**
	 * Joins a User to the given Nation as a member.
	 * 
	 * @param user The User to join
	 * @param nation The Nation to join
	 * @return true if the User has joined, false otherwise
	 */
	public boolean joinNation(User user, String nation) {
	
		if (!exists(nation)) {
			
			user.message("No nation with that name exists!");
			return false;
		}
		
		if (user.getNation().equals("")) {
			
			Group group = getGroup(nation);
			user.setNation(nation);
			group.addMember(user.getName());	
			user.message("You have joined " + nation + "!");
			return true;
		} else {
			user.message("You must first leave your nation before you can join another!");
		}
		
		return false;
	}

	
	/**
	 * Sends a message to all members in the provided Group.
	 * 
	 * @param key The name of the Group
	 * @param message The message to send
	 */
	public void messageGroup(String key, GroupMemberType memberType, String message)
	{

		Group group = getGroup(key);
		switch(memberType)
		{
		case NONLEADERS:
			for (String member : group.getMembers())
			{
				User user = plugin.userManager.getUser(member);
				if(group.hasMember(member) && !group.hasLeader(member))
						user.message("[" + key + "]: " + message);
			}
			break;
		case LEADERS:
			for (String member : group.getMembers())
			{
				User user = plugin.userManager.getUser(member);
				if(group.hasLeader(member))
						user.message("[" + key + "]: " + message);
			}
			break;
		case MEMBERS:
			for (String member : group.getMembers())
			{
				User user = plugin.userManager.getUser(member);
				if(group.hasMember(member))
						user.message("[" + key + "]: " + message);
			}
			break;
		}
	}
	
	public void createGroup() {
		//PLACEHOLDER
	}
	
	public void deleteGroup() {
		//PLACEHOLDER
	}
}
