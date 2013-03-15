package ee.pri.rl.home.media.io;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ee.pri.rl.home.media.exception.BadAudioFileException;
import ee.pri.rl.home.media.model.AudioFile;
import entagged.audioformats.AudioFileIO;
import entagged.audioformats.Tag;
import entagged.audioformats.generic.TagTextField;

public class AudioFileUtil {
	
	/**
	 * Extracts the audio file information from the given file.
	 */
	public static final AudioFile readAudioFile(File file) throws BadAudioFileException {
		try {
			Tag tag = AudioFileIO.read(file).getTag();
			
			String album = getBest(tag.getAlbum());
			String artist = getBest(tag.getArtist());
			String title = getBest(tag.getTitle());
			String trackString = getBest(tag.getTrack());
			String genre = getBest(tag.getGenre());
			
			if (StringUtils.isEmpty(artist) || StringUtils.isEmpty(title)) {
				throw new BadAudioFileException("Bad audio file", file, null);
			}
			
			int track = -1;
			if (StringUtils.isNotBlank(trackString) && StringUtils.isNumeric(trackString)) {
				track = Integer.valueOf(trackString);
			}
			
			return new AudioFile(artist.trim(), album.trim(), title.trim(), file, track, genre);
		} catch (Exception e) {
			throw new BadAudioFileException("Bad audio file", file, e);
		}
	}
	
	private static String getBest(List<?> data) {
		
		int bestLength = Integer.MIN_VALUE;
		String best = null;

		for (Object o : data) {
			String contents = ((TagTextField) o).getContent().trim();
			if (StringUtils.isNotBlank(contents) && contents.length() > bestLength) {
				bestLength = contents.length();
				best = contents;
			}
		}
		
		return best == null ? "" : best;
	}
	
}
