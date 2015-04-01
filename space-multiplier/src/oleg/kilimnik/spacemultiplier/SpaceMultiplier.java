package oleg.kilimnik.spacemultiplier;

import oleg.kilimnik.spacemultiplier.gamescreens.TrainingScreen;
import oleg.kilimnik.spacemultiplier.gamescreens.TwoPlayersScreen;
import oleg.kilimnik.spacemultiplier.mainmenu.MainMenu;
import oleg.kilimnik.spacemultiplier.particles.Laser;
import oleg.kilimnik.spacemultiplier.particles.Star;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SpaceMultiplier implements ApplicationListener {
	
	private MainMenu menuStage;
	private Stage gameScreen;
	private int width = Constants.VIRTUAL_WIDTH;
	private int height = Constants.VIRTUAL_HEIGHT;
	private Camera cam;
	private float delta;
	private SpriteBatch fadeBatch;
	private Sprite blackFade;
	private float fade = 0;
	private boolean menu;
	private boolean game;
	private SpriteBatch particlesBatch;
	private StarsRenderer background;
	private int starsTimer = 0;
	private Grid grid;
	private SpriteBatch gameBatch;

	@Override 
	public void create () {			
		createMainMenu();
		Gdx.input.setCatchBackKey(true);
		fadeBatch = new SpriteBatch();
		fadeBatch.getProjectionMatrix().setToOrtho2D(0, 0, 2, 2);
		blackFade = Resources.getInstance().blackFade;
		fade = 1;
	}
	
	private void createMainMenu() {
		menu = true;
		game = false;	
		menuStage = new MainMenu();
		Gdx.input.setInputProcessor(menuStage);	
		cam = menuStage.getCamera();
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		GameInstance.getInstance().training = false;
		GameInstance.getInstance().fadeEnd = true;
		GameInstance.getInstance().gameOver = false;
		GameInstance.getInstance().fadeBegin = false;
	}
	
	public void createGameScreen() {
		menu = false;
		game = true;
		if(GameInstance.getInstance().training) gameScreen = new TrainingScreen();
		else gameScreen = new TwoPlayersScreen();
		Gdx.input.setInputProcessor(gameScreen);
		cam = gameScreen.getCamera();	
		GameInstance.getInstance().fadeEnd = true;
		GameInstance.getInstance().fadeBegin = false;
		GameInstance.getInstance().gameStart = false;
		
		//Background
		background = new StarsRenderer();
		background.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());	
		
		grid = new Grid();
		grid.getProjectionMatrix().set(cam.combined);
		
		particlesBatch = new SpriteBatch();
		particlesBatch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());	
		
		//Smoke
		for(int i=0;i<7;i++) {
			GameInstance.getInstance().smokes.add(new Sprite(Resources.getInstance().atlas.createSprite("smoke")));
		}
		for(Sprite smoke : GameInstance.getInstance().smokes) {
	
			float scale = MathUtils.random(Gdx.graphics.getWidth()/1000f, 
					Gdx.graphics.getWidth()/360f);
			smoke.setScale(scale);
			smoke.setPosition(MathUtils.random(-smoke.getWidth()*scale/4, 
					Gdx.graphics.getWidth()-smoke.getWidth()+Math.abs(smoke.getWidth()*scale-smoke.getWidth())/2+smoke.getWidth()*scale/4), 
					MathUtils.random(-smoke.getHeight()*scale/4, 
							Gdx.graphics.getHeight()-smoke.getHeight()+Math.abs(smoke.getHeight()*scale-smoke.getHeight())/2+smoke.getHeight()*scale/4));
			
			if(smoke.getY()+smoke.getHeight()/2>Gdx.graphics.getHeight()/2) 
			smoke.setColor(1, MathUtils.random(0f, 1f), 0, 0.4f);
			else smoke.setColor(0, MathUtils.random(0f, 1f), 1, 0.4f);
		}

		
		gameBatch = new SpriteBatch();
		gameBatch.getProjectionMatrix().set(cam.combined);
		
	}

	@Override
	public void render() {

 		delta = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0f, 0f, 0.0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
		if(fade==1 && GameInstance.getInstance().gameOver) {
			gameScreen.dispose();
			particlesBatch.dispose();
			createMainMenu();
		}
		
		if(menu) {
			menuStage.act();
			menuStage.draw();
		}
		
		if(game) {


		
			
			particlesBatch.begin();
			//Smoke
			for(Sprite smoke : GameInstance.getInstance().smokes) {
				smoke.draw(particlesBatch);
			}
			//big stars
			if(starsTimer == 0) {
				Vector2 pos = new Vector2();
				pos = background.bigStars.get(MathUtils.random(background.bigStars.size-1));
				GameInstance.getInstance().stars.add(new Star(new Vector2(pos.x, pos.y)));
			}
			starsTimer++;
			if(starsTimer>100) starsTimer = 0;
			
			for (ParticleEffect star : GameInstance.getInstance().stars) {
				star.draw(particlesBatch, delta);
				if(star.isComplete()) GameInstance.getInstance().stars.removeValue(star, true);
			}
			
			particlesBatch.end();
			
			background.draw();
			grid.draw();
			
			gameScreen.act();
			gameScreen.draw();

			gameBatch.begin();
			//Laser	
			for (Laser laser : GameInstance.getInstance().lasers) {			
				laser.draw(gameBatch, delta);
				if(laser.isComplete()) {
					GameInstance.getInstance().lasers.removeValue(laser, true);
				}
			}
			//explosion
			for (ParticleEffect explosion : GameInstance.getInstance().explosions) {
				explosion.draw(gameBatch, delta);
				if(explosion.isComplete()) {
					GameInstance.getInstance().explosions.removeValue(explosion, true);
					GameInstance.getInstance().fadeBegin = true;
				}
			}

			gameBatch.end();
		}
		if(GameInstance.getInstance().gameStart) {
			createGameScreen();			
		}
		

		
		if(GameInstance.getInstance().fadeEnd) {
			fade = Math.max(fade - delta / 2.f, 0);
			fadeBatch.begin();
			blackFade.setColor(blackFade.getColor().r, blackFade.getColor().g, blackFade.getColor().b, fade);
			blackFade.draw(fadeBatch);
			fadeBatch.end();
			if(fade==0) GameInstance.getInstance().fadeEnd = false;
		}
		
		if(GameInstance.getInstance().fadeBegin) {
			fade = Math.min(fade + delta/2f, 1);
			fadeBatch.begin();
			blackFade.setColor(blackFade.getColor().r, blackFade.getColor().g, blackFade.getColor().b, fade);
			blackFade.draw(fadeBatch);
			fadeBatch.end();
		}	
	}

	@Override
	public void resize(int width, int height) {
		
		cam.viewportWidth = this.width;
		cam.viewportHeight = this.height;
		cam.position.x = this.width/2;
		cam.position.y = this.height/2;
		cam.update();

	}

	@Override
	public void dispose() {

	
	}

	@Override
	public void pause() {

		
	}

	@Override
	public void resume() {
	
		
	}

}
