package net.mgbckr.tiptoe.player;

import javax.naming.OperationNotSupportedException;

public interface InteractivePlayer<TSongInfo, TPlayerInfo>
		extends Player<TSongInfo, TPlayerInfo>, Interactive {

	public static String COMMAND_PLAY = "play";
	public static String COMMAND_PAUSE = "pause";
	public static String COMMAND_STOP = "stop";
	
	@Override
	default Object request(Action action) 
			throws OperationNotSupportedException {
		
		switch (action.getType()) {
		case COMMAND_PLAY:
			this.play();
			return null;
		case COMMAND_PAUSE:
			this.pause();
			return null;
		case COMMAND_STOP:
			this.stop();
			return null;
		default:
			throw new OperationNotSupportedException();
		}
	}
}
