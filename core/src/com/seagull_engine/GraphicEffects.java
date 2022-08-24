package com.seagull_engine;

import com.badlogic.gdx.graphics.Color;

public class GraphicEffects {
    public float scaleX;
    public float scaleY;
    public boolean flippedOnX;
    public boolean flippedOnY;
    public float rotation;
    public boolean verticalSheet;
    public int frameCount;
    public Color tint;
    
    public GraphicEffects() {
        scaleX = 1;
        scaleY = 1;
        flippedOnX = false;
        flippedOnY = false;
        rotation = 0;
        verticalSheet = true;
        frameCount = 1;
        tint = SeagullManager.baseTint;
    }

    public GraphicEffects(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        flippedOnX = false;
        flippedOnY = false;
        rotation = 0;
        verticalSheet = true;
        frameCount = 1;
        tint = SeagullManager.baseTint;
    }

    public GraphicEffects(boolean flippedOnX, boolean flippedOnY) {
        scaleX = 1;
        scaleY = 1;
        this.flippedOnX = flippedOnX;
        this.flippedOnY = flippedOnY;
        rotation = 0;
        verticalSheet = true;
        frameCount = 1;
        tint = SeagullManager.baseTint;
    }

    public GraphicEffects(float rotation) {
        scaleX = 1;
        scaleY = 1;
        flippedOnX = false;
        flippedOnY = false;
        this.rotation = rotation;
        verticalSheet = true;
        frameCount = 1;
        tint = SeagullManager.baseTint;
    }

    public GraphicEffects(float scaleX, float scaleY, boolean flippedOnX, boolean flippedOnY, float rotation, int frameCount, boolean verticalSheet, Color tint) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.flippedOnX = flippedOnX;
        this.flippedOnY = flippedOnY;
        this.rotation = rotation;
        this.verticalSheet = verticalSheet;
        this.frameCount = frameCount;
        this.tint = tint;
    }
}
