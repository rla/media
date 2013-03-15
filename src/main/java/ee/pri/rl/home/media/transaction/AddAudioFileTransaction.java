package ee.pri.rl.home.media.transaction;

import java.util.Date;

import ee.pri.rl.home.media.model.AudioFile;
import ee.pri.rl.home.media.model.MediaBase;
import ee.pri.rl.prevayler.GenericTransaction;

public class AddAudioFileTransaction extends GenericTransaction<MediaBase> {
	private static final long serialVersionUID = -5993986398386623588L;

	private final AudioFile audioFile;
	
	public AddAudioFileTransaction(AudioFile audioFile) {
		this.audioFile = audioFile;
	}

	@Override
	protected void doExecuteOn(MediaBase mediaBase, Date executionTime) {
		mediaBase.addAudioFile(audioFile);
	}

}
