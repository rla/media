package ee.pri.rl.home.media.web.music;

import java.io.File;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.pri.rl.home.media.web.BasePage;

public class Paths extends BasePage {
	private static final Logger log = LoggerFactory.getLogger(Paths.class);
	
	public Paths() {
		add(new PathsListView());
		add(new AddPathForm());
		add(new UpdateDatabaseLink());
	}
	
	private class PathsListView extends ListView {
		private static final long serialVersionUID = 1L;

		public PathsListView() {
			super("paths", new PathsListModel());
		}
		
		@Override
		protected void populateItem(ListItem item) {
			File file = (File) item.getModelObject();
			item.add(new Label("filename", file.getAbsolutePath()));
			item.add(new DeletePathLink(file));
		}
		
	}
	
	private class PathsListModel extends LoadableDetachableModel {
		private static final long serialVersionUID = 1L;

		@Override
		protected Object load() {
			return getMediaService().getPaths();
		}
		
	}
	
	private class DeletePathLink extends Link {
		private static final long serialVersionUID = 1L;
		
		private File path;
		
		public DeletePathLink(File path) {
			super("deleteLink");
			this.path = path;
		}

		@Override
		public void onClick() {
			getMediaService().deletePath(path);
		}
		
	}
	
	private class AddPathForm extends Form {
		private static final long serialVersionUID = 1L;
		
		private String pathText;
		
		public AddPathForm() {
			super("addForm");
			add(new RequiredTextField("path", new PropertyModel(this, "pathText")));
		}

		@Override
		protected void onSubmit() {
			getMediaService().addPath(new File(pathText));
			pathText = "";
		}

		public String getPathText() {
			return pathText;
		}

		public void setPathText(String pathText) {
			this.pathText = pathText;
		}

	}
	
	private class UpdateDatabaseLink extends Link {
		private static final long serialVersionUID = 1L;

		public UpdateDatabaseLink() {
			super("updateLink");
		}
		
		@Override
		public void onClick() {
			try {
				getMediaService().updateAudioFiles();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
	}
}
