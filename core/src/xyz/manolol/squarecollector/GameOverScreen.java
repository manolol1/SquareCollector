package xyz.manolol.squarecollector;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static xyz.manolol.squarecollector.SquareCollector.GAME;

public class GameOverScreen extends ScreenAdapter {

    private int score;
    private boolean isNewHighscore;

    private final SpriteBatch batch;
    private final TextWriter textWriter;

    private final OrthographicCamera camera;
    private final FitViewport viewport;

    public GameOverScreen(int score, boolean isNewHighscore) {
        this.score = score;
        this.isNewHighscore = isNewHighscore;

        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(1920, 1080, camera);

        batch = new SpriteBatch();
        textWriter = new TextWriter(batch, "fonts/Ubuntu-Regular.ttf", viewport);

    }

    @Override
    public void render(float delta) {
        //**** INPUT ****//

        // restart game
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            GAME.setScreen(new GameScreen());
            return;
        }

        // Toggle Mouse Controls
        if (Gdx.app.getType() == Application.ApplicationType.Desktop && Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            GAME.toggleMouseControls();
        }

        //**** RENDER ****//
        ScreenUtils.clear(0, 0, 0, 0);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        textWriter.drawTextCenterXY("GAME OVER", 120, 320, 1, 0, 0);
        textWriter.drawTextCenterXY("Score: " + score, 90, 180);

        if (isNewHighscore) {
            textWriter.drawTextCenterXY("NEW HIGHSCORE: " + GAME.getHighscore(), 90, 50);
        } else {
            textWriter.drawTextCenterXY("Highscore: " + GAME.getHighscore(), 90, 50);
        }

        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            textWriter.drawTextCenterXY("Press SPACE to try again!", 60, -120);

            if (GAME.getMouseControls()) {
                textWriter.drawTextCenterXY("Mouse Controls: ON", 45, -320);
                textWriter.drawTextCenterXY("Press M to toggle", 40, -390);
            } else {
                textWriter.drawTextCenterXY("Mouse Controls: OFF", 45, -320);
                textWriter.drawTextCenterXY("Press M to toggle", 40, -390);
            }
        } else if (Gdx.app.getType() == Application.ApplicationType.Android) {
            textWriter.drawTextCenterXY("Tap the screen to try again!", 60, -250);
        } else {
            textWriter.drawTextCenterXY("Press SPACE or tap the screen to try again!", 60, -250);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        // center camera
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        textWriter.dispose();
        super.dispose();
    }
}
