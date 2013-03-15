package ee.pri.rl.home.media.transaction;

import java.util.Date;

import ee.pri.rl.home.media.model.AudioFile;
import ee.pri.rl.home.media.model.MediaBase;
import ee.pri.rl.prevayler.GenericTransaction;

public class RemoveAudioFileTransaction extends GenericTransaction<MediaBase> {
	private static final long serialVersionUID = 1L;
	
	private final AudioFile audioFile;

	public RemoveAudioFileTransaction(AudioFile audioFile) {
		this.audioFile = audioFile;
	}

	@Override
	protected void doExecuteOn(MediaBase prevalentObject, Date timestamp) {
		prevalentObject.deleteAudioFile(audioFile);
	}

}
