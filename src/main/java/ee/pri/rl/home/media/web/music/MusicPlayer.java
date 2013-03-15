package ee.pri.rl.home.media.web.music;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;

import ee.pri.rl.home.media.model.Album;
import ee.pri.rl.home.media.model.AudioFile;
import ee.pri.rl.home.media.model.Playable;
import ee.pri.rl.home.media.web.BasePage;

@SuppressWarnings("serial")
public class MusicPlayer extends BasePage {
	
	public MusicPlayer() {
		add(new FilesListView());
		add(new Label("current", new CurrentPlayableModel()));
		add(new NextLink());
		add(new StopLink());
	}
	
	public MusicPlayer(AudioFile audioFile) {
		getMediaService().play(audioFile);
		add(new FilesListView());
		add(new Label("current", new CurrentPlayableModel()));
		add(new NextLink());
		add(new StopLink());
	}
	
	public MusicPlayer(String artist) {
		List<AudioFile> audioFiles = getMediaService().getAudioFilesByArtist(artist);
		Collections.sort(audioFiles);
		getMediaService().play(audioFiles);
		add(new FilesListView());
		add(new Label("current", new CurrentPlayableModel()));
		add(new NextLink());
		add(new StopLink());
	}
	
	public MusicPlayer(Album album) {
		List<AudioFile> audioFiles = getMediaService().getSongsOfAlbum(album);
		Collections.sort(audioFiles);
		getMediaService().play(audioFiles);
		add(new FilesListView());
		add(new Label("current", new CurrentPlayableModel()));
		add(new NextLink());
		add(new StopLink());
	}
	
	private class CurrentPlayableModel extends LoadableDetachableModel<String> {
		private static final long serialVersionUID = 1L;

		@Override
		protected String load() {
			Playable playable = getMediaService().getCurrentPlayable();
			return playable == null ? "-" : playable.toString();
		}
		
	}
	
	private class FilesListView extends ListView {
		private static final long serialVersionUID = 1L;
		
		public FilesListView() {
			super("playlist", new AudioFilesModel());
		}

		@Override
		protected void populateItem(ListItem item) {
			AudioFile file = (AudioFile) item.getModelObject();
			item.add(new Label("artist", file.getArtist()));
			item.add(new Label("album", file.getAlbum()));
			item.add(new Label("title", file.getTitle()));
		}
		
	}
	
	private class AudioFilesModel extends LoadableDetachableModel {
		private static final long serialVersionUID = 1L;

		@Override
		protected Object load() {
			return getMediaService().getCurrentAudioPlaylist();
		}
		
	}
	
	private class StopLink extends Link {
		private static final long serialVersionUID = 1L;

		public StopLink() {
			super("stop");
		}
		
		@Override
		public void onClick() {
			getMediaService().stop();
		}

		@Override
		public boolean isEnabled() {
			return getMediaService().isPlaying();
		}
		
	}
	
	private class NextLink extends Link {
		private static final long serialVersionUID = 1L;

		public NextLink() {
			super("next");
		}
		
		@Override
		public void onClick() {
			getMediaService().next();
		}

		@Override
		public boolean isEnabled() {
			return getMediaService().isPlaying();
		}
		
	}
}
