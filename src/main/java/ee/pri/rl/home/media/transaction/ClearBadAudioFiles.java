package ee.pri.rl.home.media.transaction;

import java.util.Date;

import ee.pri.rl.home.media.model.MediaBase;
import ee.pri.rl.prevayler.GenericTransaction;

public class ClearBadAudioFiles extends GenericTransaction<MediaBase> {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doExecuteOn(MediaBase mediaBase, Date timestamp) {
		mediaBase.clearBadAudioFiles();
	}

}
