package ee.pri.rl.home.media.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;


public class MediaBase implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Set<AudioFile> audioFiles;
	private final Set<File> paths;
	private final Set<File> badAudioFiles;
	
	public MediaBase() {
		audioFiles = new HashSet<AudioFile>();
		paths = new HashSet<File>();
		badAudioFiles = new HashSet<File>();
	}
	
	public void addAudioFile(AudioFile audioFile) {
		audioFiles.add(audioFile);
	}
	
	public void deleteAudioFile(AudioFile audioFile) {
		audioFiles.remove(audioFile);
	}
	
	public void deletePath(File path) {
		paths.remove(path);
	}
	
	public void replaceAudioFile(AudioFile audioFile, AudioFile replacement) {
		audioFiles.remove(audioFile);
		audioFiles.add(replacement);
	}
	
	public List<AudioFile> getAudioFiles() {
		return new ArrayList<AudioFile>(audioFiles);
	}
	
	public void addPath(File path) {
		Validate.isTrue(path.exists() && path.isDirectory() && path.canRead(), "Path must be readable existing directory");
		paths.add(path);
	}
	
	public List<File> getPaths() {
		return new ArrayList<File>(paths);
	}
	
	public void addBadAudioFile(File file) {
		badAudioFiles.add(file);
	}
	
	public List<File> getBadAudioFileList() {
		return new ArrayList<File>(badAudioFiles);
	}
	
	public int getAudioFileCount() {
		return audioFiles.size();
	}
	
	public AudioFile getAudioFileByPath(File path) {
		for (AudioFile file : audioFiles) {
			if (file.getFile().equals(path)) {
				return file;
			}
		}
		
		return null;
	}
	
	public List<String> getArtists() {
		Set<String> artists = new HashSet<String>();
		
		for (AudioFile file : audioFiles) {
			artists.add(file.getArtist());
		}
		
		return new ArrayList<String>(artists);
	}
	
	public void clearBadAudioFiles() {
		badAudioFiles.clear();
	}
	
	public List<AudioFile> getSongsOfAlbum(Album album) {
		List<AudioFile> files = new ArrayList<AudioFile>();
		
		for (AudioFile file : audioFiles) {
			boolean condition = album.getName().equalsIgnoreCase(file.getAlbum());
			condition &= !album.hasArtist() || album.getArtist().equalsIgnoreCase(file.getArtist());
			if (condition) {
				files.add(file);
			}
		}
		
		return files;
	}

	public List<Album> getAllAlbums() {
		Set<String> albumNames = new HashSet<String>();
		List<Album> albums = new ArrayList<Album>();
		
		for (AudioFile file : audioFiles) {
			if (!albumNames.contains(file.getAlbum())) {
				albums.add(new Album(file.getAlbum()));
				albumNames.add(file.getAlbum());
			}
		}
		
		return albums;
	}
	
}
