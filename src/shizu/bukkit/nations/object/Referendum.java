package shizu.bukkit.nations.object;

import java.util.Date;

import shizu.bukkit.nations.enums.GroupMemberType;

public class Referendum 
{
	
	private static final int referendumTime = 60000;
	
	// I know these are public but that's fine -- this is just a data-holding class and there isn't much that goes on here.
	public int lastVoteResult;
	public boolean voteInProgress;
	public boolean broadcastResults;
	public GroupMemberType votingGroup;
	public Date votingStartTime;
	public Group owningGroup;
	
	public Referendum()
	{
		this.reset();
	}
	
	/**
	 * Returns all values to default
	 */
	public void reset()
	{
		this.lastVoteResult = 0;
		this.voteInProgress = false;
		this.broadcastResults = true;
		this.votingGroup = GroupMemberType.NONE;
		this.votingStartTime = new Date();
		this.owningGroup = null;
	}
	
	public boolean isExpired(Date currentTime)
	{
		// To change the voting time length, we need to change referendumTime above
		return ((currentTime.getTime() - votingStartTime.getTime()) <= referendumTime) ? true : false;
	}
}
