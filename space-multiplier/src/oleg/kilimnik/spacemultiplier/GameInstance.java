package oleg.kilimnik.spacemultiplier;


import oleg.kilimnik.spacemultiplier.buffs.Asteroid;
import oleg.kilimnik.spacemultiplier.particles.Laser;
import oleg.kilimnik.spacemultiplier.ships.Ship;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class GameInstance {

	public Array<Laser> lasers = new Array<Laser>();
	public Array<Ship> ships = new Array<Ship>();
	public boolean gameOver = false;
	public Array<ParticleEffect> stars = new Array<ParticleEffect>();
	public Array<Asteroid> asteroids = new Array<Asteroid>();
	public boolean gameStart = false;
	public boolean fadeBegin = false;
	public boolean fadeEnd = false;
	public boolean training = false;
	public int shipsSpeed;
	public int asteroidsSpeed;
	public boolean sound;
	public int asteroidsQuantity;
	public Array<ParticleEffect> explosions = new Array<ParticleEffect>();
	public Array<Sprite> smokes = new Array<Sprite>();
	
	public static GameInstance instance;

	public static GameInstance getInstance() {
		if (instance == null) {
			instance = new GameInstance();
		}
		return instance;
	}
	
	public void resetGame() {
		lasers.clear();
		ships.clear();
		stars.clear();
		asteroids.clear();
		smokes.clear();
		explosions.clear();
	}
}