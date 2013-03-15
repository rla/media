package ee.pri.rl.home.media.model;

import java.io.Serializable;

public class Album implements Serializable, Comparable<Album> {
	private static final long serialVersionUID = 1L;
	
	private String artist;
	private String name;

	public Album(String artist, String name) {
		this.artist = artist;
		this.name = name;
	}
	
	public Album(String name) {
		this(null, name);
	}

	public String getArtist() {
		return artist;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Album) {
			Album other = (Album) obj;
			return other.getName().equalsIgnoreCase(name)
				&& (!other.hasArtist() && !hasArtist()
					|| hasArtist()
						&& other.hasArtist()
						&& artist.equalsIgnoreCase(other.getArtist())
				);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (hasArtist() ? artist.toLowerCase().hashCode() : 0) ^ name.toLowerCase().hashCode();
	}

	public int compareTo(Album o) {
		return name.compareTo(o.getName());
	}
	
	public boolean hasArtist() {
		return artist != null;
	}

}
