package net.mgbckr.tiptoe.player.javafx;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import net.mgbckr.tiptoe.player.Player;

public class JavaFxPlayer extends Application implements Player {

	private File mediaFile;
	private MediaPlayer player;
	
	@Override
	public void load(InputStream in) throws IOException {
		mediaFile = File.createTempFile("tmp-", UUID.randomUUID().toString());
		Files.copy(in, mediaFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		launch();
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void play() {
		this.player.play();
	}

	@Override
	public void stop() {
		this.player.stop();
	}

	@Override
	public void pause() {
		this.player.pause();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Media media = new Media(this.mediaFile.toURI().toString());
		this.player = new MediaPlayer(media);
	}

}
