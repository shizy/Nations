package shizu.bukkit.nations;

import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.iConomy.*;

import shizu.bukkit.nations.command.NationsCommandManager;
import shizu.bukkit.nations.event.NationsBlockListener;
import shizu.bukkit.nations.event.NationsUserListener;
import shizu.bukkit.nations.event.iConomyListener;
import shizu.bukkit.nations.manager.GroupManagement;
import shizu.bukkit.nations.manager.PlotManagement;
import shizu.bukkit.nations.manager.UserManagement;

/**
 * Nations At War plugin class
 * 
 * @author Shizukesa
 */
public class Nations extends JavaPlugin {

	private static final Logger log = Logger.getLogger("Minecraft");

	//**ICONOMY**
	public iConomy money = null;
	public iConomyListener iconListener = new iConomyListener(this);
	
	public Config config = new Config(this);
	public NationsCommandManager cm = new NationsCommandManager("naw", this);
	public PlotManagement plotManager = new PlotManagement(this);
	public UserManagement userManager = new UserManagement(this);
	public GroupManagement groupManager = new GroupManagement(this);
	public NationsBlockListener blockListener = new NationsBlockListener(this);
	public NationsUserListener userListener = new NationsUserListener(this);
	
	SimpleCommandMap userCommands;
    
	public void onEnable() {

		PluginManager pm = getServer().getPluginManager();

		pm.registerEvent(Event.Type.BLOCK_DAMAGE, blockListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, userListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, userListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_KICK, userListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_MOVE, userListener, Event.Priority.High, this);
		
		//**ICONOMY**
		pm.registerEvent(Event.Type.PLUGIN_ENABLE, iconListener, Event.Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLUGIN_DISABLE, iconListener, Event.Priority.Monitor, this);

		plotManager.loadAll();
		groupManager.loadAll();
		userManager.loadAll();
		sendToLog("Nations At War Plugin Loaded");
	}

	public void onDisable() {
		
		plotManager.saveAll();
		groupManager.saveAll();
		userManager.saveAll();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		
		return cm.execute(sender, commandLabel, args);
	}
	
	public void sendToLog(String message) {
		log.info("[NationsAtWar]: " + message);
	}
	
	public void messageAll(String message) {
		this.getServer().broadcastMessage("[NationsAtWar]: " + message);
	}
	
	public World getWorld() {
		return this.getServer().getWorld(config.get("world_name"));
	}
}
