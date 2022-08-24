package com.seagull_engine;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.files.FileHandle;

public class ImageLoader {
    private HashMap<String, Texture> images;
    private HashMap<String, BitmapFont> fonts;
    private boolean usePath;

    public ImageLoader(boolean usePathInsteadOfFileName) {
        images = new HashMap<String, Texture>();
        fonts = new HashMap<String, BitmapFont>();
        usePath = usePathInsteadOfFileName;
    }

    public ImageLoader(String assetsPath, boolean usePathInsteadOfFileName) {
        images = new HashMap<String, Texture>();
        fonts = new HashMap<String, BitmapFont>();
        loadFolder(assetsPath);
        loadFonts(assetsPath);
        usePath = usePathInsteadOfFileName;
    }

    public void loadFonts(String path) {
        FileHandle folder = Gdx.files.internal(path);
        if (folder.isDirectory()) {
            for (FileHandle file : folder.list()) {
                if (file.path().endsWith(".ttf")) {
                    loadFont(file.path());
                } else if (file.isDirectory()) {
                    loadFonts(file.path());
                }
            }
        }
    }

    public boolean loadFont(String path) {
        if (!fonts.containsKey(path)) {
            FileHandle f = Gdx.files.internal(path);
            if (f != null) {
                FreeTypeFontGenerator font = new FreeTypeFontGenerator(f);
                fonts.put(f.name().substring(0, f.name().length() - 4), font.generateFont(new FreeTypeFontParameter()));
            }
        }
        return false;
    }

    public boolean loadAbsoluteFont(String path) {
        if (!fonts.containsKey(path)) {
            FileHandle f = Gdx.files.internal(path);
            if (f != null) {
                FreeTypeFontGenerator font = new FreeTypeFontGenerator(f);
                fonts.put(f.name().substring(0, f.name().length() - 4), font.generateFont(new FreeTypeFontParameter()));
            }
        }
        return false;
    }

    public BitmapFont getFont(String key) {
        return fonts.get(key);
    }

    public void loadFolder(String path) {
        FileHandle folder = Gdx.files.internal(path);
        if (folder.isDirectory()) {
            for (FileHandle file : folder.list()) {
                if (file.path().endsWith(".png") || file.path().endsWith(".jpg") || file.path().endsWith(".jpeg")) {
                    Texture image = new Texture(file);
                    if (image != null) {
                        images.put(usePath ? path : file.name(), image);
                    }
                } else if (file.isDirectory()) {
                    loadFolder(file.path());
                }
            }
        }
    }

    public boolean loadImage(String path) {
        if (!images.containsKey(path)) {
            FileHandle f = Gdx.files.internal(path);
            if (f != null) {
                Texture image = new Texture(f);
                if (image != null) {
                    images.put(usePath ? path : f.name(), image);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean loadExternalImage(String path, String key) {
        if (!images.containsKey(path)) {
            FileHandle f = Gdx.files.external(path);
            if (f != null) {
                Texture image = new Texture(f);
                if (image != null) {
                    if (key == null) {
                        images.put(usePath ? path : f.name(), image);
                    } else {
                        images.put(key, image);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean loadAbsoluteImage(String path, String key) {
        if (!images.containsKey(path)) {
            FileHandle f = Gdx.files.absolute(path);
            if (f != null) {
                Texture image = new Texture(f);
                if (image != null) {
                    if (key == null) {
                        images.put(usePath ? path : f.name(), image);
                    } else {
                        images.put(key, image);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public Texture getImage(String name) {
        if (images.containsKey(name)) {
            return images.get(name);
        }
        System.out.println("Image with key: \"" + name + "\" not found");
        return null;
    }
}
