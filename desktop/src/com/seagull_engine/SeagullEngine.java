package com.seagull_engine;

import java.util.Random;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class SeagullEngine {
	
	public static SeagullManager hatch(String windowName, String assetsPath, String pathToIcon, boolean usePathInsteadOfFileName, Vector2 resolution, Messenger messenger) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		Random rand = new Random();
		String seagullSplash;
		switch (rand.nextInt(100)) {
			case 0 : seagullSplash = ": Powered by Seagull Engine"; break;

			case 1 : seagullSplash = ", and don't forget to praise Big Seagull"; break;

			case 2 : seagullSplash = " 2: Rise of the Seagull"; break;

			case 3 : seagullSplash = " now including: https://en.wikipedia.org/wiki/Gull"; break;

			case 4 : seagullSplash = "... or is it?"; break;

			default : seagullSplash = "";
		}
		config.setTitle(windowName + seagullSplash);
		config.setWindowedMode((int) resolution.x, (int) resolution.y);
		config.setAutoIconify(true);
		config.setWindowIcon(pathToIcon);
		SeagullManager game = new SeagullManager(assetsPath, usePathInsteadOfFileName, resolution, messenger);
		new Lwjgl3Application(game, config);
		return game;
	}
}
