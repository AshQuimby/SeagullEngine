package com.seagull_engine.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CollisionResolver {
    public static boolean resolve(Rectangle a, Vector2 velocity, Rectangle b) {
        if (!a.overlaps(b)) return false;

        a.x -= velocity.x;

        if (a.overlaps(b)) {
            if (velocity.y > 0) {
                a.y = b.y - a.height;
            } else {
                a.y = b.y + b.height;
            }
        }

        a.x += velocity.x;

        if (a.overlaps(b)) {
            if (velocity.x > 0) {
                a.x = b.x - a.width;
            } else {
                a.x = b.x + b.width;
            }
            
        }

        return true;
    }

    public static boolean movingResolve(Rectangle a, Vector2 aVelocity, Rectangle b, Vector2 bVelocity) {
        if (!a.overlaps(b)) return false;

        a.x += bVelocity.x;
        a.y += bVelocity.y;

        a.x -= aVelocity.x;

        if (a.overlaps(b)) {
            if (aVelocity.y > 0) {
                a.y = b.y - a.height;
            } else {
                a.y = b.y + b.height;
            }
        }

        a.x += aVelocity.x;

        if (a.overlaps(b)) {
            if (aVelocity.x > 0) {
                a.x = b.x - a.width;
            } else {
                a.x = b.x + b.width;
            }
            
        }

        return true;
    }
}