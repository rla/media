package ee.pri.rl.home.media.model;

import java.io.File;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

public class AudioFile implements Serializable, Playable, Comparable<AudioFile> {
	private static final long serialVersionUID = 1L;
	
	private String artist;
	private String album;
	private String title;
	private File file;
	private long lastScanned;
	private int track;
	private String genre;
	
	public AudioFile(String artist, String album, String title, File file, int track, String genre) {
		Validate.notEmpty(artist, "Artist cannot be empty");
		Validate.notNull(album, "Album cannot be null");
		Validate.notEmpty(title, "Title cannot be empty");
		Validate.notNull(file, "File cannot be null");
		Validate.isTrue(file.exists(), "File must exist");
		
		this.album = album;
		this.artist = artist;
		this.title = title;
		this.file = file;
		this.track = track;
		this.genre = genre;
	}

	public String getArtist() {
		return artist;
	}

	public String getTitle() {
		return title;
	}

	public String getAlbum() {
		return album;
	}

	public File getFile() {
		return file;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AudioFile) {
			AudioFile other = (AudioFile) obj;
			return artist.equalsIgnoreCase(other.artist)
				&& title.equalsIgnoreCase(other.title)
				&& album.equalsIgnoreCase(other.album);
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		return artist.toLowerCase().hashCode()
			^ title.toLowerCase().hashCode()
			^ album.toLowerCase().hashCode();
	}

	@Override
	public String toString() {
		return artist + (StringUtils.isBlank(album) ? "" : " - " + album) + " - " + title;
	}

	public String getLocation() {
		return file.getAbsolutePath();
	}

	public long getLastScanned() {
		return lastScanned;
	}

	public void setLastScanned(long lastScanned) {
		this.lastScanned = lastScanned;
	}

	public int getTrack() {
		return track;
	}

	public String getGenre() {
		return genre;
	}

	public int compareTo(AudioFile o) {
		int byArtist = artist.toLowerCase().compareToIgnoreCase(o.getArtist());
		if (byArtist != 0) {
			return byArtist;
		}
		int byAlbum = album.toLowerCase().compareToIgnoreCase(o.getAlbum());
		if (byAlbum != 0) {
			return byAlbum;
		}
		int byTrack = Integer.valueOf(track).compareTo(o.getTrack());
		if (byTrack != 0) {
			return byTrack;
		}
		
		return title.toLowerCase().compareToIgnoreCase(o.getTitle());
	}
	
}
