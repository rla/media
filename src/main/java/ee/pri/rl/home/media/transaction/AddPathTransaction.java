package ee.pri.rl.home.media.transaction;

import java.io.File;
import java.util.Date;

import ee.pri.rl.home.media.model.MediaBase;
import ee.pri.rl.prevayler.GenericTransaction;

public class AddPathTransaction extends GenericTransaction<MediaBase> {
	private static final long serialVersionUID = 1L;
	
	private final File path;
	
	public AddPathTransaction(File path) {
		this.path = path;
	}

	@Override
	protected void doExecuteOn(MediaBase prevalentObject, Date timestamp) {
		prevalentObject.addPath(path);
	}
	
}
