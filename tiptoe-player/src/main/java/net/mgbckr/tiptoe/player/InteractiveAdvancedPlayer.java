package net.mgbckr.tiptoe.player;

import javax.naming.OperationNotSupportedException;

public interface InteractiveAdvancedPlayer<TSongInfo, TPlayerInfo> 
		extends 
			AdvancedPlayer<TSongInfo, TPlayerInfo>, 
			InteractivePlayer<TSongInfo, TPlayerInfo> {

	public static final String COMMAND_SET_POSITION = "setPosition";
	public static final String COMMAND_SET_SPEED = "setSpeed";
	public static final String COMMAND_SET_PITCH = "setPitch";
	
	default Message sendMessage(Message message) 
			throws OperationNotSupportedException {

		switch (message.getType()) {
		case COMMAND_SET_POSITION:
			this.setPosition(message.retrieveDouble("position"));
			return new Message();
		case COMMAND_SET_SPEED:
			this.setSpeed(message.retrieveDouble("speed"));
			return new Message();
		case COMMAND_SET_PITCH:
			this.setPitch(message.retrieveDouble("pitch"));
			return new Message();
		default:
			return InteractivePlayer.super.sendMessage(message);
		}
	}
	
}
