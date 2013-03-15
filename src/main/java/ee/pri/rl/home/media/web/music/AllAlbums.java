package ee.pri.rl.home.media.web.music;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;

import ee.pri.rl.home.media.model.Album;
import ee.pri.rl.home.media.web.BasePage;

@SuppressWarnings("serial")
public class AllAlbums extends BasePage {
	
	public AllAlbums() {
		add(new AllAlbumListView());
	}
	
	private class AllAlbumListView extends ListView<Album> {
		private static final long serialVersionUID = 1L;

		public AllAlbumListView() {
			super("albums", new AllAlbumsModel());
		}
		
		@Override
		protected void populateItem(final ListItem<Album> item) {
			Album album = (Album) item.getModelObject();
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
	
	private class AllAlbumsModel extends LoadableDetachableModel<List<Album>> {
		private static final long serialVersionUID = 1L;

		@Override
		protected List<Album> load() {
			List<Album> albums = getMediaService().getAllAlbums();
			Collections.sort(albums);
			
			return albums;
		}
		
	}
}
