package net.mgbckr.tiptoe.player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.naming.OperationNotSupportedException;

import net.mgbckr.tiptoe.player.beads.BeadsPlayer;

public interface InteractiveAdvancedPlayer<TSongInfo, TPlayerInfo> 
		extends 
			AdvancedPlayer<TSongInfo, TPlayerInfo>, 
			InteractivePlayer<TSongInfo, TPlayerInfo> {

	public static final String COMMAND_GET_POSITION = "getPosition";
	public static final String COMMAND_GET_SPEED = "getSpeed";
	public static final String COMMAND_GET_PITCH = "getPitch";
	
	public static final String COMMAND_SET_POSITION = "setPosition";
	public static final String COMMAND_SET_SPEED = "setSpeed";
	public static final String COMMAND_SET_PITCH = "setPitch";
	
	default Object request(Action action) 
			throws OperationNotSupportedException {
		
		switch (action.getType()) {
		
		case COMMAND_GET_POSITION:
			this.getPosition();
		case COMMAND_GET_SPEED:
			return this.getSpeed();
		case COMMAND_GET_PITCH:
			return this.getPitch();
			
		case COMMAND_SET_POSITION:
			this.setPosition(action.retrieveDouble("position"));
			return new AdvancedInfo(this.getPosition(), this.getSpeed(), this.getPitch());
		case COMMAND_SET_SPEED:
			this.setSpeed(action.retrieveDouble("speed"));
			return new AdvancedInfo(this.getPosition(), this.getSpeed(), this.getPitch());
		case COMMAND_SET_PITCH:
			this.setPitch(action.retrieveDouble("pitch"));
			return new AdvancedInfo(this.getPosition(), this.getSpeed(), this.getPitch());
		
		default:
			return InteractivePlayer.super.request(action);
		}
	}
	
	public static class AdvancedInfo {
		
		private double position;
		private double speed;
		private double pitch;
		
		public AdvancedInfo(double position, double speed, double pitch) {
			this.position = position;
			this.speed = speed;
			this.pitch = pitch;
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
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		BeadsPlayer b = new BeadsPlayer();
		b.load(new FileInputStream("src/main/resources/samples/celebrity.mp3"));
		System.out.println(b.getSpeed());
		b.setSpeed(0.5);
		System.out.println(b.getSpeed());
		Thread.sleep(1500);
		System.out.println(b.getSpeed());
		b.play();
		System.out.println(b.getSpeed());
		Thread.sleep(500);
		System.out.println(b.getSpeed());
	}
	
}
