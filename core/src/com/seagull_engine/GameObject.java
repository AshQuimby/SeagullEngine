package com.seagull_engine;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.seagull_engine.physics.CollisionResolver;

public class GameObject {
    public int frame;
    public int frameCount;
    public float rotation;
    public int direction;
    public Rectangle drawRect;
    public Rectangle hitbox;
    public String imageName;
    public Vector2 velocity;

    public GameObject() {}
    
    public GameObject(Vector2 position, float width, float height, String imageName, int frameCount) {
        drawRect = new Rectangle(position.x, position.y, width, height);
        hitbox = new Rectangle(position.x, position.y, width, height);
        this.frameCount = frameCount;
        velocity = Vector2.Zero;
        this.imageName = imageName;
        direction = 1;
        rotation = 0;
        frame = 0;
    }
    
    public void preUpdate() {
        hitbox.x += velocity.x;
        hitbox.y += velocity.y;
        drawRect.setCenter(hitbox.getCenter(new Vector2()));
        update();
        postUpdate();
    }

    public void update() {
    }

    public void postUpdate() {
    }

    public void lateUpdate() {
    }

    public boolean collideWith(GameObject other) {
        return CollisionResolver.resolve(hitbox, velocity, other.hitbox);
    }

    public boolean resize(float width, float height) {
        if (width > 0 && height > 0) {
            Rectangle oldRect = new Rectangle(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
            hitbox.width = width;
            hitbox.height = height;
            hitbox.setCenter(oldRect.getCenter(new Vector2()));
            return true;
        }
        return false;
    }

    public void preRender(Seagraphics g) {}

    public void render(Seagraphics g) {
        preRender(g);
        g.usefulDraw(g.imageProvider.getImage(imageName), drawRect.x, drawRect.y, (int) drawRect.width, (int) drawRect.height, frame, frameCount, rotation, direction == 1, false);
        postRender(g);
    }

    public void postRender(Seagraphics g) {}
}
