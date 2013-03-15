package ee.pri.rl.home.media;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.pri.rl.home.media.exception.BadAudioFileException;
import ee.pri.rl.home.media.io.AudioFileUtil;
import ee.pri.rl.home.media.model.Album;
import ee.pri.rl.home.media.model.AudioFile;
import ee.pri.rl.home.media.model.Playable;
import ee.pri.rl.home.media.player.MediaPlayer;
import ee.pri.rl.home.media.transaction.AddAudioFileTransaction;
import ee.pri.rl.home.media.transaction.AddBadAudioFileTransaction;
import ee.pri.rl.home.media.transaction.AddPathTransaction;
import ee.pri.rl.home.media.transaction.ClearBadAudioFiles;
import ee.pri.rl.home.media.transaction.DeletePathTransaction;
import ee.pri.rl.home.media.transaction.RemoveAudioFileTransaction;

public class MediaService {
	private static final Logger log = LoggerFactory.getLogger(MediaService.class);
	
	private static final String[] AUDIO_FILE_EXTENSIONS = {"mp3", "flac"};
	
	private List<AudioFile> currentAudioPlaylist;
	
	private final MediaPrevayler prevayler;
	private final MediaPlayer player;

	public MediaService(MediaPrevayler prevayler, MediaPlayer player) {
		this.prevayler = prevayler;
		this.player = player;
	}

	public void addPath(File path) {
		prevayler.getPrevayler().execute(new AddPathTransaction(path));
	}
	
	public void updateAudioFiles() throws Exception {
		Timer timer = new Timer(false);
		
		// Clear old bad files.
		prevayler.getPrevayler().execute(new ClearBadAudioFiles());
		
		// Find files to update.
		timer.start();
		List<File> filesToUpdate = getFilesToUpdate();
		timer.end();
		
		log.info("Going to update " + filesToUpdate.size() + " files, finding them took " + timer.getElapsed() + "ms");
		
		timer.start();
		for (File file : filesToUpdate) {
			try {
				log.info("Updating " + file);
				AudioFile audioFile = AudioFileUtil.readAudioFile(file);
				audioFile.setLastScanned(System.currentTimeMillis());
				AudioFile old = prevayler.getPrevayler().prevalentSystem().getAudioFileByPath(file);
				if (old != null) {
					prevayler.getPrevayler().execute(new RemoveAudioFileTransaction(audioFile));
				}
				prevayler.getPrevayler().execute(new AddAudioFileTransaction(audioFile));
			} catch (BadAudioFileException e) {
				prevayler.getPrevayler().execute(new AddBadAudioFileTransaction(file));
			}
		}
		timer.end();
		
		log.info("Updating took " + timer.getElapsed() + "ms");
		
		// Delete non-existing files.
		timer.start();
		for (AudioFile audioFile : prevayler.getPrevayler().prevalentSystem().getAudioFiles()) {
			if (!audioFile.getFile().exists()) {
				prevayler.getPrevayler().execute(new RemoveAudioFileTransaction(audioFile));
			}
		}
		timer.end();
		
		log.info("Deleting took " + timer.getElapsed() + "ms");
		
	}
	
	@SuppressWarnings("unchecked")
	public List<File> getFilesToUpdate() {
		// Build the map of modification dates.
		Map<File, Long> fileModificationDate = new HashMap<File, Long>();
		for (AudioFile audioFile : prevayler.getPrevayler().prevalentSystem().getAudioFiles()) {
			fileModificationDate.put(audioFile.getFile(), audioFile.getLastScanned());
		}
		
		// Find and filter out files that are modified after the last
		// scanning date or are never scanned.
		List<File> filesToUpdate = new ArrayList<File>();
		for (File path : prevayler.getPrevayler().prevalentSystem().getPaths()) {
			for (File file : (Collection<File>) FileUtils.listFiles(path, AUDIO_FILE_EXTENSIONS, true)) {
				if (fileModificationDate.containsKey(file)) {
					if (file.lastModified() > fileModificationDate.get(file)) {
						filesToUpdate.add(file);
					}
				} else {
					filesToUpdate.add(file);
				}
			}
		}

		return filesToUpdate;
	}
	
	public List<File> getBadAudioFileList() {
		return prevayler.getPrevayler().prevalentSystem().getBadAudioFileList();
	}
	
	public int getAudioFileCount() {
		return prevayler.getPrevayler().prevalentSystem().getAudioFileCount();
	}
	
	public List<String> getArtists() {
		return prevayler.getPrevayler().prevalentSystem().getArtists();
	}
	
	public List<AudioFile> getAudioFilesByArtist(String artist) {
		List<AudioFile> files = new ArrayList<AudioFile>();
		for (AudioFile file : prevayler.getPrevayler().prevalentSystem().getAudioFiles()) {
			if (file.getArtist().equalsIgnoreCase(artist)) {
				files.add(file);
			}
		}
		
		return files;
	}
	
	public List<Album> getAlbumsOfArtist(String artist) {
		Set<Album> albums = new HashSet<Album>();
		for (AudioFile file : prevayler.getPrevayler().prevalentSystem().getAudioFiles()) {
			if (file.getArtist().equalsIgnoreCase(artist)) {
				albums.add(new Album(artist, file.getAlbum()));
			}
		}
		
		return new ArrayList<Album>(albums);
	}
	
	public List<AudioFile> getSongsOfAlbum(Album album) {
		return prevayler.getPrevayler().prevalentSystem().getSongsOfAlbum(album);
	}
	
	public List<AudioFile> getCurrentAudioPlaylist() {
		if (currentAudioPlaylist == null) {
			return Collections.emptyList();
		}
		
		return currentAudioPlaylist;
	}
	
	public void play(AudioFile audioFile) {
		play(Collections.singletonList(audioFile));
	}
	
	public void play(List<AudioFile> audioFiles) {
		stop();
		currentAudioPlaylist = audioFiles;
		player.play(audioFiles, true);
	}
	
	public void next() {
		player.next();
	}
	
	public void stop() {
		player.stop();
	}
	
	public Playable getCurrentPlayable() {
		return player.getCurrentPlayable();
	}
	
	public boolean isPlaying() {
		return player.isPlaying();
	}
	
	public List<File> getPaths() {
		return prevayler.getPrevayler().prevalentSystem().getPaths();
	}
	
	public void deletePath(File path) {
		prevayler.getPrevayler().execute(new DeletePathTransaction(path));
	}
	
	private static class Timer {
		private long start;
		private long end;
		
		public Timer(boolean start) {
			if (start) {
				start();
			}
		}
		
		public void start() {
			start = System.currentTimeMillis();
		}
		
		public void end() {
			end = System.currentTimeMillis();
		}
		
		public long getElapsed() {
			return end - start;
		}
	}

	public List<Album> getAllAlbums() {
		return prevayler.getPrevayler().prevalentSystem().getAllAlbums();
	}
	
}
