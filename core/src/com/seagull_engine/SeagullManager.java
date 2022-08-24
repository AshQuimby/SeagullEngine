package com.seagull_engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.seagull_engine.input.MouseInfo;


public class SeagullManager extends ApplicationAdapter implements InputProcessor {
	public SpriteBatch batch;
	private ShaderProgram shader;
	public final Seagraphics graphics;
	public Camera camera;
	public final SeagullSounds soundEngine;
	public final ImageLoader imageProvider;
	public final MouseInfo cursor;
	public final int resolutionX;
	public final int resolutionY;
	public static final Color baseTint = new Color(1, 1, 1, 1);
	private final Messenger messenger;
	public ShapeRenderer shapeRenderer;
	private Vector2 screenDimensions;
	private int tick;
	private float time;
	private String assetsPath = null;
	
	public SeagullManager() {
		resolutionX = 512;
		resolutionY = 420;
		screenDimensions = new Vector2(resolutionX, resolutionY);
		soundEngine = new SeagullSounds(this, false);
		imageProvider = new ImageLoader(false);
		graphics = new Seagraphics(this, imageProvider);
		cursor = new MouseInfo(this);
		messenger = new Messenger();
		messenger.window = this;
	}

	public SeagullManager(String assetsPath, boolean usePathInsteadOfFileName, Vector2 resolution, Messenger messenger) {
		resolutionX = (int) resolution.x;
		resolutionY = (int) resolution.y;
		screenDimensions = new Vector2(resolutionX, resolutionY);
		this.assetsPath = assetsPath;
		soundEngine = new SeagullSounds(this, false);
		imageProvider = new ImageLoader(false);
		graphics = new Seagraphics(this, imageProvider);
		cursor = new MouseInfo(this);
		this.messenger = messenger;
		messenger.window = this;
	}

	@Override
	public void create() {
		if (assetsPath != null) {
			soundEngine.loadFolder(assetsPath);
			imageProvider.loadFolder(assetsPath);
			imageProvider.loadFonts(assetsPath);
		}
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		time = 0;
		tick = 0;
		
		batch = new SpriteBatch();
		messenger.window = this;
		messenger.load();		
		Gdx.input.setInputProcessor(this);
		shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
	}

	@Override
	public void render() {
		screenDimensions.x = Gdx.graphics.getWidth();
		screenDimensions.y = Gdx.graphics.getHeight();
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);

		batch.begin();
		shapeRenderer.begin();
		ScreenUtils.clear(0, 0, 0, 1);

		messenger.update();
		messenger.render(graphics);

		time += Gdx.graphics.getDeltaTime();
		tick++;

		// shader.setUniformf("time", time);
		// graphics.usefulDraw(imageProvider.getImage("a_gods_heart.png"), 256, 128, 310, 310, 2, 6, time * 20, false, false);
		batch.end();
		shapeRenderer.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		messenger.close();
		// shader.dispose();
	}

	public Seagraphics getGraphics() {
		return graphics;
	}

	public SeagullSounds getSoundEngine() {
		return soundEngine;
	}

	public ImageLoader getImages() {
		return imageProvider;
	}

	public int getTick() {
		return tick;
	}

	public Vector2 getScreenDimension() {
		return new Vector2(screenDimensions.x, screenDimensions.y);
	}

	public void draw(Texture image, float x, float y, float width, float height, int frameWidth, int frameHeight,
			int frame, float rotation, boolean flippedHorizontal, boolean flippedVertical, boolean verticalSheet, Color tint) {
		batch.setColor(tint);
		batch.draw(image, x, y, width / 2f, height / 2f, width, height, 1, 1, rotation,
				frameWidth * frame * (verticalSheet ? 0 : 1), frameHeight * frame, frameWidth, frameHeight,
				flippedHorizontal, flippedVertical);
		batch.setColor(1, 1, 1, 1);	
	}

	@Override
    public boolean keyDown(int keycode) {
		messenger.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
		messenger.keyUp(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
