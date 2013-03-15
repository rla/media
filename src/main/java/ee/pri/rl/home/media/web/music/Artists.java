package ee.pri.rl.home.media.web.music;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;

import ee.pri.rl.home.media.web.BasePage;

@SuppressWarnings("serial")
public class Artists extends BasePage {

	public Artists() {
		add(new ArtistListView());
	}

	private class ArtistListView extends ListView<String> {
		private static final long serialVersionUID = 1L;

		public ArtistListView() {
			super("artists", new ArtistsModel());
		}
		
		@Override
		protected void populateItem(final ListItem<String> item) {
			String artist = (String) item.getModelObject();
			item.add(new Label("artist", artist));
	        item.add(new Link<Void>("albumsLink") {
				@Override
				public void onClick() {
					setResponsePage(new Albums(item.getModelObject()));
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
	
	private class ArtistsModel extends LoadableDetachableModel<List<String>> {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected List<String> load() {
			List<String> artists = getMediaService().getArtists();
			Collections.sort(artists);
			
			return artists;
		}

	}
}
