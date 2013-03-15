package ee.pri.rl.home.media.transaction;

import java.io.File;
import java.util.Date;

import ee.pri.rl.home.media.model.MediaBase;
import ee.pri.rl.prevayler.GenericTransaction;

public class DeletePathTransaction extends GenericTransaction<MediaBase> {
	private static final long serialVersionUID = 1L;
	
	private final File file;

	public DeletePathTransaction(File file) {
		this.file = file;
	}

	@Override
	protected void doExecuteOn(MediaBase base, Date timestamp) {
		base.deletePath(file);
	}

}
