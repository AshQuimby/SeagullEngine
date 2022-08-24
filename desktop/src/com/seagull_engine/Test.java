package com.seagull_engine;

import com.badlogic.gdx.math.Vector2;

public class Test {
    public static void main(String[] args) {
        SeagullEngine.hatch("Test", "assets", "assets/test.png", false, new Vector2(420, 420), new Messenger());
    }    
}
