package com.seagull_engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.seagull_engine.SeagullManager;

public class MouseInfo {
    
    private SeagullManager window;

    public MouseInfo(SeagullManager window) {
        this.window = window;
    }

    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    public int getX() {
        return (int) window.camera.unproject(new Vector3(getRawX(), getRawY(), 0)).x;
    }

    public int getY() {
        return (int) window.camera.unproject(new Vector3(getRawX(), getRawY(), 0)).y;
    }

    public Vector2 getRawPosition() {
        return new Vector2(getRawX(), getRawY());
    }

    public int getRawX() {
        return Gdx.input.getX();
    }

    public int getRawY() {
        return Gdx.input.getY();
    }

    public boolean leftMouseDown() {
        return Gdx.input.isButtonPressed(0);
    }

    public boolean rightMouseDown() {
        return Gdx.input.isButtonPressed(1);
    }

    public boolean mouseButtonDown(int button) {
        return Gdx.input.isButtonJustPressed(button);
    }

    public boolean leftMouseClicked() {
        return Gdx.input.isButtonJustPressed(0);
    }

    public boolean rightMouseClicked() {
        return Gdx.input.isButtonJustPressed(1);
    }

    public boolean mouseButtonClicked(int button) {
        return Gdx.input.isButtonJustPressed(button);
    }
}
