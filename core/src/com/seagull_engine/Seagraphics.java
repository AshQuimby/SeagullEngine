package com.seagull_engine;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.seagull_engine.graphics.SeagullCamera;
import com.seagull_engine.graphics.SpriteShader;

public class Seagraphics {

    private final SeagullManager window;
    public final ImageLoader imageProvider;
    public final ShapeRenderer shapeRenderer;
    private static final GlyphLayout fontMeasurement = new GlyphLayout();

    public Seagraphics(SeagullManager window, ImageLoader imageProvider, ShapeRenderer shapeRenderer) {
        this.window = window;
        this.imageProvider = imageProvider;
        this.shapeRenderer = shapeRenderer;
    }

    // Sets the current camera to the unmoving camera
    public void useStaticCamera() {
        window.batch.setProjectionMatrix(window.staticCamera.combined);
        shapeRenderer.setProjectionMatrix(window.staticCamera.combined);
    }

    // Sets the current camera to the dynamic camera
    public void useDynamicCamera() {
        window.batch.setProjectionMatrix(window.camera.combined);
        shapeRenderer.setProjectionMatrix(window.camera.combined);
    }

    public Camera getStaticCamera() {
        return window.staticCamera;
    }

    public SeagullCamera getDynamicCamera() {
        return window.camera;
    }

    public void basicDraw(Texture image, float x, float y) {
        window.draw(image, x, y, image.getWidth(), image.getHeight(), image.getWidth(), image.getHeight(), 0, 0, false, false, false, SeagullManager.baseTint);
    }
    
    public void overlapDraw(Texture image, Texture other, float x, float y, float width, float height, int overlapX, int overlapY, float overlapWidth, float overlapHeight) {
        window.batch.draw(image, x, y, width, height, 0, 0, image.getWidth(), image.getHeight(), false, false);
        window.batch.draw(other, x + overlapX, y + overlapY, width - overlapX, height - overlapY, overlapX, overlapY, image.getWidth() - overlapX, image.getHeight() - overlapY, false, false);
    }

    public void colorDraw(Texture image, float x, float y, Color tint) {
        window.draw(image, x, y, image.getWidth(), image.getHeight(), image.getWidth(), image.getHeight(), 0, 0, false, false, false, tint);
    }

    public void flippableDraw(Texture image, float x, float y, boolean flippedHorizontal, boolean flippedVertical) {
        window.draw(image, x, y, image.getWidth(), image.getHeight(), image.getWidth(), image.getHeight(), 0, 0, flippedHorizontal, flippedVertical, false, SeagullManager.baseTint);
    }

    public void scalableDraw(Texture image, float x, float y, int width, int height) {
        window.draw(image, x, y, width, height, image.getWidth(), image.getHeight(), 0, 0, false, false, false, SeagullManager.baseTint);
    }

    public void rotatableDraw(Texture image, float x, float y, float rotation) {
        window.draw(image, x, y, image.getWidth(), image.getHeight(), image.getWidth(), image.getHeight(), 0, rotation, false, false, false, SeagullManager.baseTint);
    }

    public void framedDraw(Texture image, float x, float y, int frame, int frameCount, boolean verticalSheet) {
        if (frameCount <= 0) frameCount = 1;
        window.draw(image, x, y, image.getWidth(), image.getHeight(), image.getWidth() / (verticalSheet ? 1 : frameCount), image.getHeight() / (verticalSheet ? frameCount : 1), frame, 0, false, false, verticalSheet, SeagullManager.baseTint);
    }

    public void usefulDraw(Texture image, float x, float y, int width, int height, int frame, int frameCount, float rotation, boolean flippedHorizontal, boolean flippedVertical) {
        if (frameCount <= 0) frameCount = 1;
        window.draw(image, x, y, width, height, image.getWidth(), image.getHeight() / frameCount, frame, rotation, flippedHorizontal, flippedVertical, true, SeagullManager.baseTint);
    }

    public void usefulTintDraw(Texture image, float x, float y, int width, int height, int frame, int frameCount, float rotation, boolean flippedHorizontal, boolean flippedVertical, Color tint) {
        if (frameCount <= 0) frameCount = 1;
        window.draw(image, x, y, width, height, image.getWidth(), image.getHeight() / frameCount, frame, rotation, flippedHorizontal, flippedVertical, true, tint);
    }

    public void usefulShaderDraw(Texture image, float x, float y, int width, int height, int frame, int frameCount, float rotation, boolean flippedHorizontal, boolean flippedVertical, SpriteShader shader) {
        if (frameCount <= 0) frameCount = 1;
        window.getBatch().setShader(shader.getShader());
        window.draw(image, x, y, width, height, image.getWidth(), image.getHeight() / frameCount, frame, rotation, flippedHorizontal, flippedVertical, true, SeagullManager.baseTint);
        window.getBatch().setShader(null);
    }

    public void usefulShaderDraw(Texture image, float x, float y, int width, int height, int frame, int frameCount, float rotation, boolean flippedHorizontal, boolean flippedVertical, SpriteShader shader, Color tint) {
        if (frameCount <= 0) frameCount = 1;
        window.getBatch().setShader(shader.getShader());
        window.draw(image, x, y, width, height, image.getWidth(), image.getHeight() / frameCount, frame, rotation, flippedHorizontal, flippedVertical, true, tint);
        window.getBatch().setShader(null);
    }

    public void setShader(SpriteShader shader) {
        window.getBatch().setShader(shader.getShader());
    }

    public void resetShader() {
        window.getBatch().setShader(null);
    }

	public void draw(Texture image, float x, float y, float width, float height, int frameWidth, int frameHeight, int frame, float rotation, boolean flippedHorizontal, boolean flippedVertical, boolean verticalSheet) {
		window.draw(image, x, y, width, height, frameWidth, frameHeight, frame, rotation, flippedHorizontal, flippedVertical, verticalSheet, SeagullManager.baseTint);
	}

    public void draw(Texture image, float x, float y, float width, float height, int frame, GraphicEffects effects) {
		window.draw(image, x, y, width, height, image.getWidth() / (effects.verticalSheet ? 1 : effects.frameCount), image.getHeight() / (effects.verticalSheet ? 1 : effects.frameCount), frame, effects.rotation, effects.flippedOnX, effects.flippedOnY, effects.verticalSheet, SeagullManager.baseTint);
	}

    public Rectangle drawText(String text, BitmapFont font, float x, float y, float size, Color color, int anchor) {
        Rectangle bounds = getTextBounds(text, font, x, y, size, anchor);
        font.draw(window.batch, text, bounds.x, bounds.y);
        return bounds;
    }

    public Rectangle getTextBounds(String text, BitmapFont font, float x, float y, float size, int anchor) {
        font.getData().setScale(size, size);

        fontMeasurement.setText(font, text);
        Rectangle bounds = new Rectangle(x, y, fontMeasurement.width, fontMeasurement.height);
        bounds.setCenter(new Vector2(x, y + bounds.height / 2));

        if (anchor == 1) {
            bounds.x -= bounds.width / 2;
        } else if (anchor == -1) {
            bounds.x += bounds.width / 2;
        }
        return bounds;
    }

    public Rectangle[] drawStringArray(String[] text, BitmapFont font, float x, float y, float size, Color color, int anchor, float seperation) {
        font.getData().setScale(size, size);

        Rectangle[] allBounds = new Rectangle[text.length];

        for (int i = 0; i < text.length; i++) {
            String line = text[i];
            fontMeasurement.setText(font, line);
            Rectangle bounds = new Rectangle(x, y, fontMeasurement.width, fontMeasurement.height);
            
            bounds.setCenter(new Vector2(x, y + bounds.height / 2 - (seperation * i)));

            if (anchor == 1) {
                bounds.x -= bounds.width / 2;
            } else if (anchor == -1) {
                bounds.x += bounds.width / 2;
            }
            font.draw(window.batch, line, bounds.x, bounds.y);
            allBounds[i] = bounds;
        }
        return allBounds;
    }
}