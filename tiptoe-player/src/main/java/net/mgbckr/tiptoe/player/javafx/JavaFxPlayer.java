package net.mgbckr.tiptoe.player.javafx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.lang.model.type.NullType;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import net.mgbckr.tiptoe.player.Player;

public class JavaFxPlayer extends Application implements Player<NullType, NullType> {

	private File mediaFile;
	private MediaPlayer player;
	
	@Override
	public NullType load(InputStream in) throws IOException {

		this.mediaFile = File.createTempFile("tmp-", UUID.randomUUID().toString());

		Media media = new Media(new File("src/test/resources/samples/celebrity.mp3").toURI().toString());
		this.player = new MediaPlayer(media);
		
		Files.copy(in, mediaFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//		launch();
		
		return null;
		
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
	}
	
	
	
	public static void main(String[] args) throws Exception, InterruptedException {
		Player player = new JavaFxPlayer();
		player.load(new FileInputStream("src/test/resources/samples/celebrity.mp3"));

		player.play();
		Thread.sleep(2000);
		player.pause();
		Thread.sleep(2000);
		player.play();
		Thread.sleep(2000);
		player.stop();
		Thread.sleep(2000);
		player.play();
	}

}
