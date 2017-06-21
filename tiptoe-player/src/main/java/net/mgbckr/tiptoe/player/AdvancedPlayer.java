package net.mgbckr.tiptoe.player;

public interface AdvancedPlayer<TSongInfo, TPlayerInfo> 
		extends Player<TSongInfo, TPlayerInfo> {

	double getPosition();
	void setPosition(double position);
	
	double getRate();
	void setRate(double rate, boolean tryToKeepPitch);
	default void setRate(double rate) {
		this.setRate(rate, true);
	}
	
	double getPitch();
	void setPitch(double pitch);
	
	
}
