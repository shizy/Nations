package shizu.bukkit.nations.manager;

import java.util.ArrayList;
import java.util.Date;
import shizu.bukkit.nations.Nations;
import shizu.bukkit.nations.object.Referendum;

/**
 * Don't use me yet!
 * @author JerikTelorian
 *
 */
public class VotingManager implements Runnable 
{
	private Nations owner;
	public ArrayList<Referendum> currentReferendums = new ArrayList<Referendum>();
	
	public VotingManager(Nations owner)
	{
		this.owner = owner;
	}
	
	@Override
	public void run() 
	{
		for(int i = 0; i < currentReferendums.size(); i++)
		{
			//Kludgey
			int[] refsToRemove = new int[currentReferendums.size()];
			int index = 0;
			
			if(currentReferendums.get(i).isExpired(new Date()))
				refsToRemove[index] = i; index++; this.terminateReferendum(currentReferendums.get(i));
				
		}

	}
	
	private void terminateReferendum(Referendum ref)
	{
		ref.voteInProgress = false;
		
		
		
		if (ref.broadcastResults)
			owner.groupManager.messageGroup(ref.owningGroup.getName(), ref.votingGroup, "Referendum Complete: ");
	}

}
