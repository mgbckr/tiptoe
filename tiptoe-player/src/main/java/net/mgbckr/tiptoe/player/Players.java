package net.mgbckr.tiptoe.player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class Players {

	public static File createTmpFile(InputStream in) throws IOException {
		File mediaFile = File.createTempFile("tmp-", UUID.randomUUID().toString());
		Files.copy(in, mediaFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		return mediaFile;
	}
}
