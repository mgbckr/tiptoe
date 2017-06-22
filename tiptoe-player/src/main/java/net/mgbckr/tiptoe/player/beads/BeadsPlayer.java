package net.mgbckr.tiptoe.player.beads;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.core.Bead;
import net.beadsproject.beads.data.Sample;
import net.beadsproject.beads.data.SampleManager;
import net.beadsproject.beads.ugens.Clock;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.GranularSamplePlayer;
import net.beadsproject.beads.ugens.SamplePlayer;
import net.beadsproject.beads.ugens.SamplePlayer.InterpolationType;
import net.beadsproject.beads.ugens.Static;
import net.mgbckr.tiptoe.player.AdvancedHeartbeat;
import net.mgbckr.tiptoe.player.AdvancedPlayer;
import net.mgbckr.tiptoe.player.Observable;
import net.mgbckr.tiptoe.player.Players;

public class BeadsPlayer 
		implements 
			AdvancedPlayer<BeadsPlayer.SongInfo, BeadsPlayer.PlayerStatus>,
			Observable {
	
	public static final int WAVE_WIDTH = 400;
	public static final float HEARTBEAT_INTERVAL = 200f;
	
	protected AudioContext ac;
	protected SamplePlayer player;
	
	protected EventListener listener;
	
	public BeadsPlayer() {
		
		this.ac = new AudioContext();
		this.ac.start();
		
	}
	
	@Override
	public void addEventListener(EventListener listener) {
		this.listener = listener;
	}
	
	@Override
	public SongInfo load(InputStream in) throws IOException {
		File file = Players.createTmpFile(in);
		
		Sample sample = SampleManager.sample(file.getAbsolutePath());

		SamplePlayer oldPlayer = this.player;		

		this.player = new SamplePlayer(this.ac, sample);
		this.player.setKillOnEnd(false);
		this.player.setEndListener(new Bead() {
			@Override
			protected void messageReceived(Bead message) {
				stop();
			}
		});
		if (oldPlayer == null) {
			this.player.pause(true);
		} else {
			this.player.pause(oldPlayer.isPaused());
		}
		
		Gain g = new Gain(ac, 2, 1f);
		g.addInput(player);
		this.ac.out.addInput(g);

		// add clock to audio context
		Clock clock = new Clock(this.ac, HEARTBEAT_INTERVAL * 1f / 0.6f * 10f);
		clock.addMessageListener(new Bead() {
			@Override
			protected void messageReceived(Bead message) {
				AdvancedHeartbeat heartbeat = new AdvancedHeartbeat();
				heartbeat.setPlaying(!player.isPaused());
				heartbeat.setPosition(getPosition());
				heartbeat.setSpeed(getSpeed());
				heartbeat.setPitch(getPitch());
				Event e = new Event("heartbeat", heartbeat);
				BeadsPlayer.this.listener.notify(e);
			}
		});
//		clock.pause(true);
		this.ac.out.addDependent(clock);
		
		if (oldPlayer != null)
			oldPlayer.kill();
		
		return getSongInfo();
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
	
	@Override
	public double getPosition() {
		return this.player.getPosition();
	}
	
	@Override
	public void setPosition(double milliseconds) {
		this.player.setPosition(milliseconds);
	}
	
	@Override
	public double getSpeed() {
		return this.player.getRateUGen().getValueDouble();
	}
	
	@Override
	public void setSpeed(double rate, boolean tryToKeepPitch) {
		this.player.setRate(new Static(this.ac, (float) rate));
	}
	
	@Override
	public double getPitch() {
		return this.player.getPitchUGen().getValueDouble();
	}
	
	@Override
	public void setPitch(double pitch, boolean tryToKeepSpeed) {
		this.player.setPitch(new Glide(this.ac, (float) pitch)); 
	}
	
	public SongInfo getSongInfo() {
		
		SongInfo info = new SongInfo();
		
		// length
		info.setLength(this.player.getSample().getLength());
		
		// wave
		double[] wave = new double[WAVE_WIDTH];
		Sample sample = this.player.getSample();
		float[] frame = new float[sample.getNumChannels()];
		double step = sample.getNumFrames() / (double) WAVE_WIDTH;
		
		for (int i = 0; i < WAVE_WIDTH; i ++) {
			
			double sum = 0;
			for (int j = 0; j < step; j ++) {
				sample.getFrame((int) (i * step + j), frame);
				sum += Math.abs(frame[0]);
			}
			wave[i] = sum / step;
		}

		info.setWave(wave);
		
		// return
		return info;
	}
	
	public static class SongInfo {

		private double length;
		private double[] wave;
		
		public double getLength() {
			return this.length;
		}
		
		public void setLength(double length) {
			this.length = length;
		}
		
		public double[] getWave() {
			return wave;
		}
		
		public void setWave(double[] wave) {
			this.wave = wave;
		}
		
	}
	
	public static class PlayerStatus {
	}

	public static void main(String[] args) throws InterruptedException {
	
			AudioContext ac = new AudioContext();
			ac.start();
			
	//		Thread.sleep(2000);
			System.out.println("Loading file ...");
			Sample sample = SampleManager.sample("src/main/resources/samples/elvis_riverside.wav");
			GranularSamplePlayer player = new GranularSamplePlayer(ac, sample);
	//		SamplePlayer player = new SamplePlayer(ac, sample);
	
			System.out.println(player.getInterpolationType());
			player.setInterpolationType(InterpolationType.NONE);
			
			System.out.println(player.getGrainIntervalUGen().getValue());
			player.setGrainInterval(new Glide(ac, 80f)); // 70 (best so far until 20: 80)
	
			System.out.println(player.getGrainSizeUGen().getValue());
			player.setGrainSize(new Glide(ac, 150f)); // 100 (best so far until 20: 150)
				
	//		Thread.sleep(2000);
			System.out.println("Define gain on player ...");
			Gain g = new Gain(ac, 2, 0.2f);
			g.addInput(player);
			
	//		Thread.sleep(2000);
			System.out.println("Add gain to audio context ...");
			ac.out.addInput(g);
			
	//		Thread.sleep(2000);
	//		System.out.println("Pause ...");
	//		g.pause(true);
	//		Thread.sleep(2000);
	//		System.out.println("Resume ...");
	//		g.pause(false);
	//		Thread.sleep(3000);
	//		player.setPosition(1000);
	//		Thread.sleep(2000);
	//		player.setPosition(1000);
	//		Thread.sleep(2000);
	//		player.setPosition(1000);
//			System.out.println("Slow down ...");
//			player.setRate(new Glide(ac, 1f));
			
			Clock c = new Clock(ac, 1000f * 1f / 0.6f * 10f);
			c.addMessageListener(new Bead() {
				@Override
				protected void messageReceived(Bead message) {
					Clock c = (Clock) message;
					System.out.println(c.getTempo());
					System.out.println(c.isBeat());
					System.out.println("---");
				}
			});
			ac.out.addDependent(c);
			
//			player.setLoopPointsFraction(0.5f, 0.7f);
//			System.out.println(player.getLoopType());
//			player.setLoopType(LoopType.LOOP_FORWARDS);
			player.setEndListener(new Bead() {
				@Override
				protected void messageReceived(Bead message) {
					System.out.println(message);
				}
			});
			
	//		player.setPitch(new Glide(ac, 1.2f));
		}

}
