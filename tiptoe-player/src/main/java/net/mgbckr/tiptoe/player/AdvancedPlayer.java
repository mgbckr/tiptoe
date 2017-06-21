package net.mgbckr.tiptoe.player;

public interface AdvancedPlayer<TSongInfo, TPlayerInfo> 
		extends Player<TSongInfo, TPlayerInfo> {

	double getPosition();
	void setPosition(double position);
	
	double getSpeed();
	void setSpeed(double rate, boolean tryToKeepPitch);
	default void setSpeed(double rate) {
		this.setSpeed(rate, true);
	}
	
	double getPitch();
	void setPitch(double pitch);
	
	
}
