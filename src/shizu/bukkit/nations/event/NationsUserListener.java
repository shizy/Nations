package shizu.bukkit.nations.event;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import shizu.bukkit.nations.Nations;

public class NationsUserListener extends PlayerListener {
	
	// TODO Config: pull this data from config
	private final Boolean registerOnJoin = true;
	private Nations plugin;
	
	public NationsUserListener(Nations instance) {
		
		plugin = instance;
	}
	
	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		player.sendMessage("Nations at War!");
		
		if (plugin.userManager.loadUser(player)) {
		
				plugin.userManager.updateLocation(player);
		} else {
			if (registerOnJoin) {
				plugin.userManager.registerUser(player);		
			} else {
				player.sendMessage("* You are not yet registered on the server *");
				player.sendMessage("NAW functionality will be disabled until you are registered!");
			}
		}
	}
	
	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		String name = event.getPlayer().getDisplayName();
		plugin.userManager.saveObject(name);
		plugin.userManager.collection.remove(name);
	}

	@Override
	public void onPlayerKick(PlayerKickEvent event) {

		String name = event.getPlayer().getDisplayName();
		plugin.userManager.saveObject(name);
		plugin.userManager.collection.remove(name);
	}
	
	@Override
	public void onPlayerMove(PlayerMoveEvent event) {
		
		plugin.userManager.updateLocation(event.getPlayer());
	}
}
