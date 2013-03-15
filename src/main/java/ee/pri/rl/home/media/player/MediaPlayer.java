package ee.pri.rl.home.media.player;

import java.util.List;

import ee.pri.rl.home.media.model.Playable;

public class MediaPlayer {
	private Thread thread;
	private PlaylistThread playlistThread;
	
	private final String mplayerBinary;

	public MediaPlayer(String mplayerBinary) {
		this.mplayerBinary = mplayerBinary;
	}

	public synchronized void play(List<? extends Playable> playlist, boolean repeat) {
		if (playlistThread != null) {
			throw new IllegalStateException("Last playlist thread was not shut down!");
		}
		playlistThread = new PlaylistThread(playlist, repeat, mplayerBinary);
		thread = new Thread(playlistThread);
		thread.start();
	}
	
	public synchronized void stop() {
		if (playlistThread != null) {
			playlistThread.stop();
		}
		playlistThread = null;
	}

	public void next() {
		playlistThread.next();
	}

	public void jump(Long position) {
		playlistThread.jump(position.intValue());
	}
	
	public Playable getCurrentPlayable() {
		return playlistThread == null ? null : playlistThread.getCurrentPlayable();
	}
	
	public boolean isPlaying() {
		return playlistThread != null && playlistThread.isRunning();
	}
	
}
