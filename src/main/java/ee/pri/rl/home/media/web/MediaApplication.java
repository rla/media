package ee.pri.rl.home.media.web;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import ee.pri.rl.home.media.MediaPrevayler;
import ee.pri.rl.home.media.MediaService;
import ee.pri.rl.home.media.PowerService;
import ee.pri.rl.home.media.player.MediaPlayer;

public class MediaApplication extends WebApplication {
	private MediaService mediaService;
	private PowerService powerService;
	
	@Override
	protected void init() {
		Properties properties = new Properties();
		try {
			URL url = MediaApplication.class.getClassLoader().getResource("media.properties");
			properties.load(url.openStream());
		} catch (IOException e) {
			throw new RuntimeException("Cannot load properties", e);
		}
		
		MediaPlayer player = new MediaPlayer(properties.getProperty("mplayerBinary"));
		MediaPrevayler prevayler;
		try {
			prevayler = new MediaPrevayler(properties.getProperty("prevaylerBase"));
		} catch (Exception e) {
			throw new RuntimeException("Cannot create prevayler instance", e);
		}
		
		mediaService = new MediaService(prevayler, player);
		powerService = new PowerService(
				properties.getProperty("powerOnCommand"),
				properties.getProperty("powerOffCommand"));
	}

	public Class<? extends Page> getHomePage() {
		return Main.class;
	}

	@Override
	protected void onDestroy() {
		if (mediaService != null) {
			mediaService.stop();
		}
		super.onDestroy();
	}

	public MediaService getMediaService() {
		return mediaService;
	}

	public PowerService getPowerService() {
		return powerService;
	}

}
