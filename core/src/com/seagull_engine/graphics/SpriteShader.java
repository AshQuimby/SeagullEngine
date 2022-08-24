package com.seagull_engine.graphics;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.Gdx;

public class SpriteShader {
    private final ShaderProgram shader;

    public SpriteShader(ShaderProgram shaderProgram) {
        shader = shaderProgram;
    }

    public SpriteShader(String vertexPath, String fragmentPath) {
        shader = new ShaderProgram(Gdx.files.internal(vertexPath), Gdx.files.internal(fragmentPath));
        if (!shader.isCompiled()) {
            System.out.println(shader.getLog());
        }
    }

    public SpriteShader(String fragmentPath) {
        this("default.vsh", fragmentPath);
    }

    public ShaderProgram getShader() {
        return shader;
    }
}