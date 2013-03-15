package ee.pri.rl.home.media;

import java.io.IOException;

import org.prevayler.PrevaylerFactory;

import ee.pri.rl.home.media.model.MediaBase;
import ee.pri.rl.prevayler.GenericPrevayler;

public class MediaPrevayler {
	private final GenericPrevayler<MediaBase> prevayler;
	
	public MediaPrevayler(String mediaBase) throws IOException, ClassNotFoundException {
		prevayler = new GenericPrevayler<MediaBase>(
				PrevaylerFactory.createPrevayler(new MediaBase(), mediaBase));
	}

	public GenericPrevayler<MediaBase> getPrevayler() {
		return prevayler;
	}
	
}
