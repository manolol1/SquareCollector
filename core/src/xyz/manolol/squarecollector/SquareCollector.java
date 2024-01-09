package xyz.manolol.squarecollector;

import com.badlogic.gdx.Game;

public class SquareCollector extends Game {

	public SquareCollector game;

	private boolean mouseControls = true;

	@Override
	public void create() {
		game = this;
		this.setScreen(new StartScreen(game));
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
