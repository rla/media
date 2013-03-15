package ee.pri.rl.home.media.transaction;

import java.io.File;
import java.util.Date;

import ee.pri.rl.home.media.model.MediaBase;
import ee.pri.rl.prevayler.GenericTransaction;

public class AddBadAudioFileTransaction extends GenericTransaction<MediaBase> {
	private static final long serialVersionUID = 1L;
	
	private final File file;
	
	public AddBadAudioFileTransaction(File file) {
		this.file = file;
	}

	@Override
	protected void doExecuteOn(MediaBase prevalentObject, Date timestamp) {
		prevalentObject.addBadAudioFile(file);
	}

}
