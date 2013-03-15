package ee.pri.rl.home.media.web.music;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;

import ee.pri.rl.home.media.model.Album;
import ee.pri.rl.home.media.model.AudioFile;
import ee.pri.rl.home.media.web.BasePage;

@SuppressWarnings("serial")
public class Songs extends BasePage {
	
	public Songs(Album album) {
		add(new SongsListView(new AlbumSongsModel(album)));
	}
	
	private class SongsListView extends ListView {
		private static final long serialVersionUID = 1L;

		public SongsListView(LoadableDetachableModel model) {
			super("songs", model);
		}
		
		@Override
		protected void populateItem(final ListItem item) {
			AudioFile file = (AudioFile) item.getModelObject();
			item.add(new Label("title", file.getTitle()));
			item.add(new Link("playLink") {
				@Override
				public void onClick() {
					setResponsePage(new MusicPlayer((AudioFile) item.getModelObject()));
				}
			});
		}
		
	}
	
	private class AlbumSongsModel extends LoadableDetachableModel {
		private static final long serialVersionUID = 1L;
		
		private Album album;

		private AlbumSongsModel(Album album) {
			this.album = album;
		}
		
		@Override
		protected Object load() {
			return getMediaService().getSongsOfAlbum(album);
		}
		
	}
}
