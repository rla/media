package ee.pri.rl.home.media.exception;

import java.io.File;

public class BadAudioFileException extends Exception {
	private static final long serialVersionUID = 1L;

	private final File file;

	public BadAudioFileException(String message, File file, Throwable cause) {
		super(message, cause);
		this.file = file;
	}

	public File getFile() {
		return file;
	}

}
