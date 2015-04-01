package oleg.kilimnik.spacemultiplier;


import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Resources {

	public TextureAtlas atlas;
	public Sound buttonSoundP1;
	public Sound shootSoundP1;
	public Sound falseShootP1;
	public Sound buttonSoundP2;
	public Sound shootSoundP2;
	public Sound falseShootP2;
	public Sprite shipP2;
	public Sprite shipP1;
	public static Resources instance;
	public Image coordsP1;
	public Image coordsP2;
	public Sprite blackFade;
	public Sound buffSound;
	public Sound explosionSound;

	
	public static Resources getInstance() {
		if (instance == null) {
			instance = new Resources();
		}
		return instance;
	}

	public Resources() {
		if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("uk"))
			atlas = new TextureAtlas(Gdx.files.internal("data/spritepackRu/atlas.pack"));
		else atlas = new TextureAtlas(Gdx.files.internal("data/spritepackEn/atlas.pack"));
		
		buttonSoundP1 = Gdx.audio.newSound(Gdx.files.internal("data/audio/buttonPressedP1.mp3"));
		shootSoundP1 = Gdx.audio.newSound(Gdx.files.internal("data/audio/shootP1.mp3"));
		falseShootP1 = Gdx.audio.newSound(Gdx.files.internal("data/audio/falseShootP1.mp3"));
		buttonSoundP2 = Gdx.audio.newSound(Gdx.files.internal("data/audio/buttonPressedP2.mp3"));
		shootSoundP2 = Gdx.audio.newSound(Gdx.files.internal("data/audio/shootP2.mp3"));
		falseShootP2 = Gdx.audio.newSound(Gdx.files.internal("data/audio/falseShootP2.mp3"));
		buffSound = Gdx.audio.newSound(Gdx.files.internal("data/audio/asteroidExplode.mp3"));
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("data/audio/Boom1.mp3"));
		
		shipP1 = atlas.createSprite("tigershipP1");
		shipP2 = atlas.createSprite("tigershipP2");

		blackFade = atlas.createSprite("blackfade");
		
		coordsP1 = new Image(atlas.findRegion("coordinatesP1"));
		coordsP2 = new Image(atlas.findRegion("coordinatesP2"));

	}
}
