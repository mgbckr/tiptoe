package net.mgbckr.tiptoe.player;

public interface AdvancedPlayer<TSongInfo, TPlayerStatus> 
		extends Player<TSongInfo, TPlayerStatus> {

	double getPosition();
	void setPosition(double position);
	
	/**
	 * @return speed on a scale from 0 to infinity where 1 is the normal speed
	 */
	double getSpeed();
	
	/**
	 * @param spped speed on a scale from 0 to infinity where 1 is the normal speed
	 * @param tryToKeepPitch when setting speed pitch may vary; this indicated
	 */
	void setSpeed(double speed, boolean tryToKeepPitch);
	default void setSpeed(double rate) {
		this.setSpeed(rate, true);
	}
	
	/**
	 * @return pitch on a scale from 0 to infinity where 1 is the normal pitch
	 */
	double getPitch();
	void setPitch(double rate, boolean tryToKeepSpeed);
	default void setPitch(double rate) {
		this.setPitch(rate, true);
	}
}
