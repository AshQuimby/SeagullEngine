package com.seagull_engine;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class SeagullSounds {
    private boolean usePath;
    private HashMap<String, Sound> sounds;
    private float globalVolume;
    private ArrayList<Sound> musicQueue;
    private ArrayList<Long> musicQueueId;
    private ArrayList<Float> musicQueueVolume;
    private int currentMusicPriority;

    public SeagullSounds(SeagullManager window, boolean usePathInsteadOfFileName) {
        sounds = new HashMap<String, Sound>();
        globalVolume = 1f;
        musicQueue = new ArrayList<>();
        musicQueueId = new ArrayList<>();
        musicQueueVolume = new ArrayList<>();
        usePath = usePathInsteadOfFileName;
        currentMusicPriority = 0;
    }

    public SeagullSounds(SeagullManager window, String folderPath, boolean usePathInsteadOfFileName) {
        sounds = new HashMap<String, Sound>();
        globalVolume = 1f;
        musicQueue = new ArrayList<>();
        currentMusicPriority = 0;
        usePath = usePathInsteadOfFileName;
        loadFolder(folderPath);
    }

    public void loadFolder(String path) {
        FileHandle folder = Gdx.files.internal(path);
        if (folder.isDirectory()) {
            for (FileHandle file : folder.list()) {
                if (file.path().endsWith(".wav") || file.path().endsWith(".mp3") || file.path().endsWith(".ogg")) {
                    Sound sound = Gdx.audio.newSound(file);
                    if (sound != null) {
                        sounds.put(usePath ? path : file.name(), sound);
                    }
                } else if (file.isDirectory()) {
                    loadFolder(file.path());
                }
            }
        }
    }

    public boolean loadSound(String path) {
        if (!sounds.containsKey(path)) {
            FileHandle f = Gdx.files.internal(path);
            if (f != null) {
                Sound sound = Gdx.audio.newSound(f);
                if (sound != null) {
                    sounds.put(usePath ? path : f.name(), sound);
                    return true;
                }
            }
        }
        return false;
    }

    public long playSound(String fileName) {
        if (sounds.containsKey(fileName)) {
            return sounds.get(fileName).play(globalVolume);
        }
        System.out.println("Sound with key: \"" + fileName + "\" could not be found");
        return -1;
    }

    public long playSound(String fileName, float volume) {
        if (sounds.containsKey(fileName)) {
            return  sounds.get(fileName).play(volume);
        }
        System.out.println("Sound with key: \"" + fileName + "\" could not be found");
        return -1;
    }

    public void playMusic(String fileName, final boolean loops, int priority, boolean fade, float volume, float pan) {
        if (priority >= currentMusicPriority && sounds.containsKey(fileName)) {
            musicQueue.add(sounds.get(fileName));
            if (fade && musicQueue.size() > 1) {
                musicQueueId.add(musicQueue.get(musicQueue.size() - 1).play(volume, 1f, pan));
                musicQueueVolume.add(volume);
                Thread thread = new Thread() {
                    private int fadeTime = 480;

                    @Override
                    public void run() {
                        musicQueue.get(1).setVolume(musicQueueId.get(1), 0f);
                        while(true) {
                            fadeTime--;
                            if (fadeTime < 241) musicQueue.get(1).setVolume(musicQueueId.get(1), Math.max(0f, (241 - fadeTime) / 240f * 0.1f));
                            else musicQueue.get(0).setVolume(musicQueueId.get(0), Math.max(0f, (fadeTime - 240f) / 240f * 0.1f));
                            if (fadeTime <= 0) {
                                if (loops) musicQueue.get(1).setLooping(musicQueueId.get(1), true);
                                if (musicQueue.size() > 1) {
                                    musicQueue.get(1).setVolume(musicQueueId.get(1), musicQueueVolume.get(1));
                                    musicQueue.get(0).stop();
                                    musicQueueVolume.remove(0);
                                    musicQueueId.remove(0);
                                    musicQueue.remove(0);
                                }
                                break;
                            }
                            try { sleep(10); } catch (Exception e) {}
                        }
                    }
                };
                thread.start();
            } else {
                if (musicQueue.size() > 1) {
                    musicQueue.get(0).stop();
                    musicQueue.remove(0);
                    musicQueueId.remove(0);
                }
                if (loops) musicQueueId.add(musicQueue.get(0).loop(volume)); else musicQueueId.add(musicQueue.get(0).play(volume));
                musicQueueVolume.add(volume);
            }
        } else System.out.println("Sound with key: \"" + fileName + "\" could not be found");
    }

    public void playMusic(String fileName, boolean loops, int priority, boolean fade) {
        playMusic(fileName, loops, priority, fade, globalVolume, 0);
    }

    public void playMusic(String fileName, boolean loops, int priority, boolean fade, float volume) {
        playMusic(fileName, loops, priority, fade, volume, 0);
    }

    public void resetMusicPriority() {
        currentMusicPriority = 0;
    }

    public void setMusicPriority(int newPriority) {
        currentMusicPriority = newPriority;
    }

    public void setCurrentMusicVolume(float volume) {
        musicQueue.get(0).setVolume(musicQueueId.get(0), volume);
    }

    public void setCurrentMusicLoop(boolean loops) {
        musicQueue.get(0).setLooping(musicQueueId.get(0), loops);
    }

    public void pauseMusic() {
        musicQueue.get(0).pause(musicQueueId.get(0));
    }

    public void unpauseMusic() {
        musicQueue.get(0).resume(musicQueueId.get(0));
    }

    public void setCurrentMusicPan(float pan) {
        musicQueue.get(0).setPan(musicQueueId.get(0), pan, musicQueueVolume.get(0));
    }

    public void stopMusic() {
        for (int i = musicQueue.size() - 1; i >= 0; i--) {
            musicQueue.get(i).stop();
            musicQueue.remove(i);
            musicQueueId.remove(i);
        }
        currentMusicPriority = 0;
    }

    public void setGlobalVolume(float volume) {
        globalVolume = volume;
    }

    public float getGlobalVolume() {
        return globalVolume;
    }
}
