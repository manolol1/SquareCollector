package xyz.manolol.squarecollector;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static xyz.manolol.squarecollector.SquareCollector.GAME;

public class StartScreen extends ScreenAdapter {
    private final SquareCollector game;

    private final SpriteBatch batch;
    private final TextWriter textWriter;
    private final OrthographicCamera camera;
    private final FitViewport viewport;

    public StartScreen(SquareCollector game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);

        batch = new SpriteBatch();
        textWriter = new TextWriter(batch, "fonts/Ubuntu-Regular.ttf", viewport);

    }

    @Override
    public void render(float delta) {

        //**** INPUT ****//

        // Start Game
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            GAME.setScreen(new GameScreen());
            return;
        }

        // Toggle Mouse Controls
        if (Gdx.app.getType() == Application.ApplicationType.Desktop && Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            game.toggleMouseControls();
        }

        //**** RENDER ****//
        ScreenUtils.clear(0, 0, 0, 0);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        textWriter.drawTextCenterXY("SQUARE COLLECTOR", 120, 250, 0.5f, 0.5f, 0);

        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            textWriter.drawTextCenterXY("Press SPACE to start the game!", 80, 50);
            if (game.getMouseControls()) {
                textWriter.drawTextCenterXY("Mouse Controls: ON", 45, -200);
                textWriter.drawTextCenterXY("Press M to toggle", 40, -270);
            } else {
                textWriter.drawTextCenterXY("Mouse Controls: OFF", 45, -200);
                textWriter.drawTextCenterXY("Press M to toggle", 40, -270);
            }
        } else if (Gdx.app.getType() == Application.ApplicationType.Android) {
            textWriter.drawTextCenterXY("Tap the screen to start the game!", 80, 20);
        } else {
            textWriter.drawTextCenterXY("Press SPACE or tap the screen to start the game!", 80, 20);
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
