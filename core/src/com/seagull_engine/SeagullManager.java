package com.seagull_engine;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.seagull_engine.graphics.SpriteShader;
import com.seagull_engine.input.MouseInfo;


public class SeagullManager extends ApplicationAdapter implements InputProcessor {
	public SpriteBatch batch;
	public final Seagraphics graphics;
	public Camera camera;
	public final SeagullSounds soundEngine;
	public final ImageLoader imageProvider;
	public final MouseInfo cursor;
	public final int resolutionX;
	public final int resolutionY;
	public static final Color baseTint = new Color(1, 1, 1, 1);
	private final Messenger messenger;
	private ShapeRenderer shapeRenderer;
	private Vector2 screenDimensions;
	private int tick;
	private float time;
	private String assetsPath = null;

	private FrameBuffer defaultFrameBuffer;
	private List<FrameBuffer> frameBuffers;
	private List<SpriteShader> postProcessingStack;
	
	public SeagullManager() {
		resolutionX = 512;
		resolutionY = 420;
		screenDimensions = new Vector2(resolutionX, resolutionY);
		soundEngine = new SeagullSounds(this, false);
		imageProvider = new ImageLoader(false);
		graphics = new Seagraphics(this, imageProvider, shapeRenderer);
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
		graphics = new Seagraphics(this, imageProvider, shapeRenderer);
		cursor = new MouseInfo(this);
		this.messenger = messenger;
		messenger.window = this;
	}

	protected SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void create() {
		frameBuffers = new ArrayList<>();
		postProcessingStack = new ArrayList<>();

		if (assetsPath != null) {
			soundEngine.loadFolder(assetsPath);
			imageProvider.loadFolder(assetsPath);
			imageProvider.loadFonts(assetsPath);
		}
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		tick = 0;
		time = 0;
		
		batch = new SpriteBatch();
		messenger.window = this;
		messenger.load();
		Gdx.input.setInputProcessor(this);
		shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

		defaultFrameBuffer = new FrameBuffer(Format.RGBA8888, resolutionX, resolutionY, false);
		addPostProcessingEffect(new SpriteShader("test.fsh"));
		addPostProcessingEffect(new SpriteShader("curved.fsh"));
	}

	@Override
	public void render() {
		screenDimensions.x = Gdx.graphics.getWidth();
		screenDimensions.y = Gdx.graphics.getHeight();
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);

		ScreenUtils.clear(0, 0, 0, 1); // Clear screen

		defaultFrameBuffer.begin();
		batch.begin();
		shapeRenderer.begin();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setShader(null);

		messenger.update();
		messenger.render(graphics);
		graphics.scalableDraw(imageProvider.getImage("colortest.png"), -234 / 2, -199 / 2, 234, 199);

		batch.end();
		shapeRenderer.end();
		defaultFrameBuffer.end();

		FrameBuffer lastBuffer = defaultFrameBuffer;

		for (int i = 0; i < postProcessingStack.size(); i++) {
			SpriteShader shader = postProcessingStack.get(i);
			FrameBuffer renderTarget = frameBuffers.get(i);

			renderTarget.begin();
			batch.begin();

			batch.setShader(shader.getShader());
			if (batch.getShader().hasUniform("u_time")) {
				batch.getShader().setUniformf("u_time", time);
			}

			Texture fboTexture = lastBuffer.getColorBufferTexture();
			TextureRegion fboTextureFlipped = new TextureRegion(fboTexture);
			fboTextureFlipped.flip(false, true);

			batch.draw(fboTextureFlipped, -resolutionX / 2, -resolutionY / 2);

			batch.end();
			renderTarget.end();

			lastBuffer = renderTarget;
		}

		batch.begin();
		batch.setShader(null);

		Texture fboTexture = lastBuffer.getColorBufferTexture();
		TextureRegion fboTextureFlipped = new TextureRegion(fboTexture);
		fboTextureFlipped.flip(false, true);

		batch.draw(fboTextureFlipped, -resolutionX / 2, -resolutionY / 2);
		batch.end();

		tick++;
		time += Gdx.graphics.getDeltaTime();
	}

	@Override
	public void dispose() {
		for (SpriteShader shader : postProcessingStack) {
			shader.dispose();
		}

		batch.dispose();
		shapeRenderer.dispose();

		messenger.close();
	}

	public void addPostProcessingEffect(SpriteShader shader) {
		postProcessingStack.add(shader);
		frameBuffers.add(new FrameBuffer(Format.RGBA8888, resolutionX, resolutionY, false));
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
