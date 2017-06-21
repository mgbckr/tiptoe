package net.mgbckr.tiptoe.player;

import javax.naming.OperationNotSupportedException;

public interface InteractivePlayer<TSongInfo, TPlayerInfo>
		extends Player<TSongInfo, TPlayerInfo>, Interactive {

	public static String COMMAND_PLAY = "play";
	public static String COMMAND_PAUSE = "pause";
	public static String COMMAND_STOP = "stop";
	
	@Override
	default Message sendMessage(Message message) 
			throws OperationNotSupportedException {
		
		switch (message.getType()) {
		case COMMAND_PLAY:
			this.play();
			return new Message();
		case COMMAND_PAUSE:
			this.pause();
			return new Message();
		case COMMAND_STOP:
			this.stop();
			return new Message();
		default:
			throw new OperationNotSupportedException();
		}
	}
}
