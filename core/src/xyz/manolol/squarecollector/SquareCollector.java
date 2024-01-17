package xyz.manolol.squarecollector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SquareCollector extends Game {

	public static SquareCollector GAME;
	public static Preferences PREFS;

	@Override
	public void create() {
		GAME = this;
		PREFS = Gdx.app.getPreferences("Data");
		this.setScreen(new StartScreen(GAME));
	}

	public boolean getMouseControls() {
		return PREFS.getBoolean("mouseControls", true);
	}

	public void setMouseControls(boolean mouseControls) {
		PREFS.putBoolean("mouseControls", mouseControls);
		PREFS.flush();
	}

	public int getHighscore() {
		return PREFS.getInteger("highscore", 0);
	}

	public void setHighscore(int highscore) {
		PREFS.putInteger("highscore", highscore);
		PREFS.flush();
	}

	public void toggleMouseControls() {
		if (getMouseControls()) {
			setMouseControls(false);
		} else {
			setMouseControls(true);
		}
	}
}
