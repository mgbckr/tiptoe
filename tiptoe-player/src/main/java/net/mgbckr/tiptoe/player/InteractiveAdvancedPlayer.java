package net.mgbckr.tiptoe.player;

import javax.naming.OperationNotSupportedException;

public interface InteractiveAdvancedPlayer<TSongInfo, TPlayerInfo> 
		extends 
			AdvancedPlayer<TSongInfo, TPlayerInfo>, 
			InteractivePlayer<TSongInfo, TPlayerInfo> {

	public static final String COMMAND_SET_POSITION = "setPosition";
	public static final String COMMAND_SET_RATE = "setRate";
	public static final String COMMAND_SET_PITCH = "setPitch";
	
	default Message sendMessage(Message message) 
			throws OperationNotSupportedException {

		switch (message.getType()) {
		case COMMAND_SET_POSITION:
			this.setPosition(message.retrieveDouble("position"));
			return new Message();
		case COMMAND_SET_RATE:
			this.setPosition(message.retrieveDouble("rate"));
			return new Message();
		case COMMAND_SET_PITCH:
			this.setPosition(message.retrieveDouble("pitch"));
			return new Message();
		default:
			return InteractivePlayer.super.sendMessage(message);
		}
	}
	
}
