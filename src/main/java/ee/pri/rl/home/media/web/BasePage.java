package ee.pri.rl.home.media.web;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.PackageResourceReference;

import ee.pri.rl.home.media.MediaService;
import ee.pri.rl.home.media.PowerService;

public class BasePage extends WebPage {
	private static final long serialVersionUID = 1L;

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderCSSReference(new PackageResourceReference(BasePage.class, "style.css"));
	}
	
	protected MediaService getMediaService() {
		return ((MediaApplication) Application.get()).getMediaService();
	}
	
	protected PowerService getPowerService() {
		return ((MediaApplication) Application.get()).getPowerService();
	}
	
}
