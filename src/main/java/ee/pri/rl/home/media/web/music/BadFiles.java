package ee.pri.rl.home.media.web.music;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import ee.pri.rl.home.media.web.BasePage;

@SuppressWarnings("serial")
public class BadFiles extends BasePage {
	
	public BadFiles() {
		List<File> badAudioFiles = getMediaService().getBadAudioFileList();
		Collections.sort(badAudioFiles);
		add(new FileListView(badAudioFiles));
	}
	
	private class FileListView extends ListView<File> {
		private static final long serialVersionUID = 1L;

		public FileListView(List<File> badAudioFiles) {
			super("files", badAudioFiles);
		}
		
		@Override
		protected void populateItem(ListItem<File> item) {
			item.add(new Label("filename", (item.getModelObject()).getAbsolutePath()));
		}
		
	}
	
}
