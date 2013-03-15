package ee.pri.rl.home.media.player;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.pri.rl.home.media.model.Playable;

/**
 * A thread which manages audio player to play
 * songs in the given playlist.
 */
public class PlaylistThread implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(PlaylistThread.class);
	
	private String mplayerBinary;
	private List<? extends Playable> playlist;
	private boolean running;
	private boolean repeat;
	private Process playerProcess;
	private Playable currentPlayable;
	private int position;
	
	public int getPosition() {
		return position;
	}

	public PlaylistThread(List<? extends Playable> playlist, boolean repeat, String mplayerBinary) {
		this.playlist = playlist;
		this.running = false;
		this.repeat = repeat;
		this.mplayerBinary = mplayerBinary;
		this.currentPlayable = playlist.get(0);
	}

	public void run() {
		running = true;
		int ioeCount = 0;
		while (running) {
			position = 0;
			ioeCount = 0;
			while (position < playlist.size()) {
				if (!running) {
					break;
				}
				Playable file = playlist.get(position);
				try {
					playerProcess = Runtime.getRuntime().exec(new String[] {
						mplayerBinary,
						"-nolirc",
						"-really-quiet",
						"-slave",
						file.getLocation()
					});
					currentPlayable = file;
				} catch (IOException e) {
					ioeCount++;
					if (ioeCount > 5) {
						log.error(e.getMessage(), e);
						running = false;
					}
				}
				try {
					playerProcess.waitFor();
				} catch (InterruptedException e) {
					log.error(e.getMessage(), e);
				}
				position++;
			}
			if (!repeat) {
				break;
			}
		}
		running = false;
	}
	
	public void stop() {
		running = false;
		if (playerProcess != null) {
			playerProcess.destroy();
		}
	}
	
	public Playable getCurrentPlayable() {
		return currentPlayable;
	}

	public void next() {
		jump(position + 1);
	}
	
	private void killCurrentProcess() {
		if (playerProcess != null) {
			playerProcess.destroy();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<? extends Playable> getCurrentFiles() {
		return playlist;
	}

	public void jump(int newPosition) {
		position = (newPosition < playlist.size() && newPosition >= 0 ? newPosition : 0) - 1;
		killCurrentProcess();
	}
	
	public boolean isRunning() {
		return running;
	}
}
