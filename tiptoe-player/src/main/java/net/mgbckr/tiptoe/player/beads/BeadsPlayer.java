package net.mgbckr.tiptoe.player.beads;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Sample;
import net.beadsproject.beads.data.SampleManager;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.SamplePlayer;
import net.mgbckr.tiptoe.player.Player;
import net.mgbckr.tiptoe.player.Players;

public class BeadsPlayer implements Player {
	
	private AudioContext ac;
	private SamplePlayer player;
	
	public BeadsPlayer() {
		this.ac = new AudioContext();
		this.ac.start();
	}
	
	@Override
	public void load(InputStream in) throws IOException {
		File file = Players.createTmpFile(in);
		
		Sample sample = SampleManager.sample(file.getAbsolutePath());
		System.out.println(sample.getNumFrames());
		
		this.player = new SamplePlayer(this.ac, sample);
		this.player.pause(true);
		
		Gain g = new Gain(ac, 2, 1f);
		g.addInput(player);
		this.ac.out.addInput(g);
	}

	@Override
	public void play() {
		this.player.pause(false);	
	}

	@Override
	public void stop() {
		this.player.pause(true);
		this.player.setPosition(0);
	}

	@Override
	public void pause() {
		this.player.pause(true);
		
	}
	
	public static void main(String[] args) throws InterruptedException {

		AudioContext ac = new AudioContext();
		ac.start();
		
		Thread.sleep(2000);
		System.out.println("Loading file ...");
		Sample sample = SampleManager.sample("src/main/resources/samples/celebrity.mp3");
		SamplePlayer player = new SamplePlayer(ac, sample);

		Thread.sleep(2000);
		System.out.println("Define gain on player ...");
		Gain g = new Gain(ac, 2, 0.2f);
		g.addInput(player);
		
		Thread.sleep(2000);
		System.out.println("Add gain to audio context ...");
		ac.out.addInput(g);
		
		Thread.sleep(2000);
		System.out.println("Pause ...");
		g.pause(true);
		Thread.sleep(2000);
		System.out.println("Resume ...");
		g.pause(false);
		Thread.sleep(3000);
//		player.setPosition(1000);
//		Thread.sleep(2000);
//		player.setPosition(1000);
//		Thread.sleep(2000);
//		player.setPosition(1000);
		System.out.println("Slow down ...");
		player.setRate(new Glide(ac, 0.8f));
	}

	@Override
	public SongInfo getSongInfo() {
		BeadsPlayerInfo info = new BeadsPlayerInfo();
		info.setLength(this.player.getSample().getLength());
		return info;
	}
	
	public static class BeadsPlayerInfo extends SongInfo {

		private double length;
		
		public BeadsPlayerInfo() {
			super(BeadsPlayer.class);
		}
		
		public double getLength() {
			return this.length;
		}
		
		public void setLength(double length) {
			this.length = length;
		}
		
	}

}
