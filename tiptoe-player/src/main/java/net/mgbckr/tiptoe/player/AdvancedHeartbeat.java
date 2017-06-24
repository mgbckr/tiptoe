package net.mgbckr.tiptoe.player;

public class AdvancedHeartbeat {
	
	private boolean playing;
	private double position;
	private double speed;
	private double pitch;
	private boolean looping;
	
	public boolean isPlaying() {
		return playing;
	}
	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
	public double getPosition() {
		return position;
	}
	public void setPosition(double position) {
		this.position = position;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getPitch() {
		return pitch;
	}
	public void setPitch(double pitch) {
		this.pitch = pitch;
	}
	public boolean isLooping() {
		return looping;
	}
	public void setLooping(boolean looping) {
		this.looping = looping;
	}
	
}