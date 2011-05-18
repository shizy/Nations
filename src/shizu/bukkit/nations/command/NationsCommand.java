package shizu.bukkit.nations.command;

import org.bukkit.command.Command;

public abstract class NationsCommand extends Command {
	
	protected NationsCommand(String name) {
		
		super(name);
	}
	
	//TODO: make this not suck
	protected String implode(String[] args, String delimiter) {
		
		String arguments = "";
		
		for (String arg : args) {
			
			arguments += arg + delimiter;
		}
		
		return arguments;
	}
}