package xyz.manolol.squarecollector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TextWriter {

    private final SpriteBatch batch;
    private final Viewport viewport;
    private final FreeTypeFontGenerator fontGenerator;
    private final GlyphLayout layout;
    private final FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private final ObjectMap<Integer, BitmapFont> fonts;

    public TextWriter(SpriteBatch batch, String filePath, Viewport viewport) {
        this.batch = batch;
        this.viewport = viewport;
        this.fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(filePath));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.layout = new GlyphLayout();
        this.fonts = new ObjectMap<>();

    }

    private BitmapFont getFont(int size) {
        // generate new font if the specific size hasn't been generated yet
        if (!fonts.containsKey(size)) {
            parameter.size = size;
            parameter.minFilter = Texture.TextureFilter.Linear;
            parameter.magFilter = Texture.TextureFilter.Linear;
            fonts.put(size, fontGenerator.generateFont(parameter));
        }
        return fonts.get(size);
    }

    public void drawTextCenterXY(String text, int fontSize, float yOffset) {
        BitmapFont font = getFont(fontSize);
        layout.setText(font, text);
        float x = (viewport.getWorldWidth() - layout.width) / 2;
        float y = (viewport.getWorldHeight() + layout.height) / 2 + yOffset;
        font.draw(batch, layout, x, y);
    }

    public void drawTextCenterXY(String text, int fontSize, float yOffset, float r, float g, float b) {
        BitmapFont font = getFont(fontSize);
        font.setColor(r, g, b, 1);
        layout.setText(font, text);
        float x = (viewport.getWorldWidth() - layout.width) / 2;
        float y = (viewport.getWorldHeight() + layout.height) / 2 + yOffset;
        font.draw(batch, layout, x, y);
    }

    public void DrawTextTopRightXY(String text, int fontSize, float xOffset, float yOffset) {
        BitmapFont font = getFont(fontSize);
        layout.setText(font, text);
        float x = (viewport.getWorldWidth() - xOffset) - layout.height;
        float y = (viewport.getWorldHeight() - yOffset) - layout.height;
        font.draw(batch, layout, x, y);
    }

    public void dispose() {
        if (fontGenerator != null) {
            fontGenerator.dispose();
        }
        // dispose all fonts
        for (BitmapFont font : fonts.values()) {
            if (font != null) {
                font.dispose();
            }
        }
    }
}
