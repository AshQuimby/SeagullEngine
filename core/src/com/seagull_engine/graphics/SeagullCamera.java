package com.seagull_engine.graphics;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SeagullCamera extends OrthographicCamera {

    public Vector2 targetPosition;
    public float targetZoom;
    private float originalWidth;
    private float originalHeight;

    public SeagullCamera(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
        targetPosition = new Vector2(0, 0);
        originalWidth = viewportWidth;
        originalHeight = viewportHeight;
        targetZoom = 1;
    }

    public void moveToTarget(float easing) {
        position.x += (targetPosition.x - position.x) / easing;
        position.y += (targetPosition.y - position.y) / easing;
    }

    public void updateZoom(float easing) {
        zoom += (targetZoom - zoom) / easing;
    }

    public void updateSeagullCamera() {
        super.update();
        moveToTarget(8);
        updateZoom(8);
    }

    public void updateSeagullCamera(float easing) {
        super.update();
        moveToTarget(easing);
        updateZoom(easing);
    }

    public Rectangle getViewport() {
        return new Rectangle(position.x, position.y, viewportWidth, viewportHeight);
    }
}
