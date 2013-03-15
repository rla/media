package ee.pri.rl.home.media.web;

import java.io.IOException;

import org.apache.wicket.markup.html.link.Link;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends BasePage {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public Main() {
		add(new Link<String>("powerOnLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				try {
					getPowerService().turnOn();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		});
		add(new Link<String>("powerOffLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				try {
					getPowerService().turnOff();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		});
	}
}
