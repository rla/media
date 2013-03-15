package ee.pri.rl.home.media.web.music;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;

import ee.pri.rl.home.media.model.Album;
import ee.pri.rl.home.media.web.BasePage;

@SuppressWarnings("serial")
public class Albums extends BasePage {
	private static final long serialVersionUID = 1L;
	
	private String artist;
	
	public Albums(String artist) {
		this.artist = artist;
		add(new Label("artist", artist));
		add(new AlbumListView());
	}
	
	private class AlbumListView extends ListView<Album> {
		private static final long serialVersionUID = 1L;

		public AlbumListView() {
			super("albums", new AlbumsModel());
		}
		
		@Override
		protected void populateItem(final ListItem<Album> item) {
			Album album = item.getModelObject();
			item.add(new Label("album", album.getName()));
			item.add(new Link<Void>("songsLink") {
				@Override
				public void onClick() {
					setResponsePage(new Songs(item.getModelObject()));
				}
			});
			item.add(new Link<Void>("playLink") {
				@Override
				public void onClick() {
					setResponsePage(new MusicPlayer(item.getModelObject()));
				}
			});
		}
	}
	
	private class AlbumsModel extends LoadableDetachableModel<List<Album>> {
		private static final long serialVersionUID = 1L;

		@Override
		protected List<Album> load() {
			return getMediaService().getAlbumsOfArtist(artist);
		}
		
	}
}
