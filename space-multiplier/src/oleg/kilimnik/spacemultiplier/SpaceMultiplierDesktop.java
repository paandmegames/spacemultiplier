package oleg.kilimnik.spacemultiplier;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class SpaceMultiplierDesktop {
	public static void main(String[] args) {
		new LwjglApplication(new SpaceMultiplier(),
				"Space Multiplier", 480, 800);
	}
}
