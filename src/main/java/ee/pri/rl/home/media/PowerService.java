package ee.pri.rl.home.media;

import java.io.IOException;

public class PowerService {
	private final String powerOnCommand;
	private final String powerOffCommand;
	
	public PowerService(String powerOnCommand, String powerOffCommand) {
		this.powerOnCommand = powerOnCommand;
		this.powerOffCommand = powerOffCommand;
	}

	public void turnOn() throws IOException {
		Runtime.getRuntime().exec(powerOnCommand);
	}
	
	public void turnOff() throws IOException {
		Runtime.getRuntime().exec(powerOffCommand);
	}

}
