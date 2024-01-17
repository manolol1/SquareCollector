package xyz.manolol.squarecollector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SquareCollector extends Game {

	public static SquareCollector GAME;
	public static Preferences PREFS;

	private boolean mouseControls = true;

	@Override
	public void create() {
		GAME = this;
		PREFS = Gdx.app.getPreferences("Data");
		this.setScreen(new StartScreen(GAME));
	}

	public boolean getMouseControls() {
		return mouseControls;
	}

	public void setMouseControls(boolean mouseControls) {
		this.mouseControls = mouseControls;
	}

	public void toggleMouseControls() {
		if (mouseControls) {
			mouseControls = false;
		} else {
			mouseControls = true;
		}
	}
}
