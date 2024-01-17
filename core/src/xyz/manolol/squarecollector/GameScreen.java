package xyz.manolol.squarecollector;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;

import static xyz.manolol.squarecollector.SquareCollector.GAME;

public class GameScreen extends ScreenAdapter {

    private final float COLLECTOR_WIDTH = 150;
    private final float COLLECTOR_HEIGHT = 35;
    private final float COLLECTOR_SPEED = 1500;

    private final float SQUARE_SIZE = 60;
    private final float SQUARE_START_SPEED = 200;
    private final float SQUARE_MAX_SPEED = 900;
    private final float SQUARE_SPEED_INCREASE = 10;
    private final int SQUARE_SPAWN_START_INTERVAL = 1500; // ms
    private final int MIN_SQUARE_SPAWN_INTERVAL = 400; // ms
    private final int SQUARE_SPAWN_INTERVAL_DECREASE = 30;

    private final int DIFFICULTY_INCREASE_START_INTERVAL = 3000; // ms
    private final int DIFFICULTY_INCREASE_INTERVAL_DECREASE = 10; // ms
    private final int MIN_DIFFICULTY_INCREASE_INTERVAL = 1200; // ms

    private float squareSpeed = SQUARE_START_SPEED;
    private float squareSpawnInterval = SQUARE_SPAWN_START_INTERVAL;
    private long lastSquareSpawnTime;

    private int difficultyIncreaseInterval = DIFFICULTY_INCREASE_START_INTERVAL;
    private long lastDifficultyIncreaseTime;

    private int score = 0;
    private boolean isNewHighscore = false;

    Array<Rectangle> squares;

    final private OrthographicCamera camera;
    final private Viewport viewport;
    final private SpriteBatch batch;
    final private TextWriter textWriter;

    ShapeRenderer shapeRenderer;

    Rectangle collector;

    public GameScreen() {

        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        batch = new SpriteBatch();
        textWriter = new TextWriter(batch, "fonts/Ubuntu-Regular.ttf", viewport);
        shapeRenderer = new ShapeRenderer();
        collector = new Rectangle(viewport.getWorldWidth() / 2 - COLLECTOR_WIDTH / 2, 50, COLLECTOR_WIDTH, COLLECTOR_HEIGHT);
        squares = new Array<>();

        lastDifficultyIncreaseTime = TimeUtils.millis();
        spawnSquare();

    }

    @Override
    public void render(float delta) {

        //**** INPUT ****//

        // Keyboard
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            collector.x -= COLLECTOR_SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            collector.x += COLLECTOR_SPEED * delta;
        }

        // Touch and Mouse
        if (Gdx.input.isTouched() || GAME.getMouseControls()) {
            collector.x = viewport.unproject(new Vector3(Gdx.input.getX(), 0, 0)).x - COLLECTOR_WIDTH / 2;
        }

        // Keep Collector inside screen bounds
        collector.x = MathUtils.clamp(collector.x, 0, viewport.getWorldWidth() - COLLECTOR_WIDTH);


        //**** UPDATE ****//

        if (TimeUtils.millis() - lastSquareSpawnTime > squareSpawnInterval) spawnSquare();

        // increase difficulty over time
        if (TimeUtils.millis() - lastDifficultyIncreaseTime > difficultyIncreaseInterval) {
            if (squareSpeed < SQUARE_MAX_SPEED) {
                squareSpeed += SQUARE_SPEED_INCREASE;
            }

            if (squareSpawnInterval > MIN_SQUARE_SPAWN_INTERVAL) {
                squareSpawnInterval -= SQUARE_SPAWN_INTERVAL_DECREASE;
            }

            if (difficultyIncreaseInterval > MIN_DIFFICULTY_INCREASE_INTERVAL) {
                difficultyIncreaseInterval -= DIFFICULTY_INCREASE_INTERVAL_DECREASE;
            }
            lastDifficultyIncreaseTime = TimeUtils.millis();
        }

        // update rectangles
        for (Iterator<Rectangle> i = squares.iterator(); i.hasNext(); ) {
            Rectangle square = i.next();
            square.y -= squareSpeed * delta;

            if (square.y + SQUARE_SIZE < 0) {
                if (score > GAME.getHighscore()) {
                    GAME.setHighscore(score);
                    isNewHighscore = true;
                }
                GAME.setScreen(new GameOverScreen(score, isNewHighscore));
                return;
            }

            if (square.overlaps(collector)) {
                i.remove();
                score++;
            }
        }


        //**** RENDER ****/

        ScreenUtils.clear(0, 0, 0, 0);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // render collector
        shapeRenderer.rect(collector.x, collector.y, collector.width, collector.height);

        // render squares
        for (Rectangle square : squares) {
            shapeRenderer.rect(square.x, square.y, square.width, square.height);
        }

        shapeRenderer.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        textWriter.DrawTextTopRightXY("" + score, 60, 30, 0);
        batch.end();
    }

    public void spawnSquare() {
        Rectangle square = new Rectangle();
        square.width = SQUARE_SIZE;
        square.height = SQUARE_SIZE;
        square.x = MathUtils.random(0, viewport.getWorldWidth() - SQUARE_SIZE);
        square.y = viewport.getWorldHeight();

        squares.add(square);
        lastSquareSpawnTime = TimeUtils.millis();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        textWriter.dispose();
    }
}
