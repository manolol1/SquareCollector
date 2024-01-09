package xyz.manolol.squarecollector;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(120);
		config.useVsync(true);
		config.setWindowedMode(1280, 720);
		config.setResizable(true);
		config.setTitle("SquareCollector");
		new Lwjgl3Application(new SquareCollector(), config);
	}
}
