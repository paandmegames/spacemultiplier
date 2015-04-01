package oleg.kilimnik.spacemultiplier.gamescreens;

import oleg.kilimnik.spacemultiplier.Constants;
import oleg.kilimnik.spacemultiplier.GameInstance;
import oleg.kilimnik.spacemultiplier.Resources;
import oleg.kilimnik.spacemultiplier.buffs.Asteroid;
import oleg.kilimnik.spacemultiplier.keyboard.Keyboard;
import oleg.kilimnik.spacemultiplier.ships.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Pools;

public class TwoPlayersScreen extends Stage {
	
	private Image coordsP1;
	private Image coordsP2;
	private Vector2 gridCoords = Constants.gridCoords;
	private float width = Constants.VIRTUAL_WIDTH;
	private float height = Constants.VIRTUAL_HEIGHT;
	private int shipSize = Constants.shipSize;
	private Ship player1;
	private Ship player2;
	private Keyboard keyboardP1;
	private Keyboard keyboardP2;
	private SpriteBatch gameBatch;
	private Skin skin;
	private int asteroidTimer = 1;
	private Group group;
	private Image toMenuImage;
	private Image yesImage;
	private Image noImage;
	private float aTimer;
	private Camera cam;
	private final Group root;
	public Touchpad touchpadP1;
	public Touchpad touchpadP2;
	
	public TwoPlayersScreen() {
		
		root = new Group();
		addActor(root);
		
		//Skin
		skin = new Skin();
		skin.addRegions(Resources.getInstance().atlas);
		
		touchpadP1 = new Touchpad(2, new Touchpad.TouchpadStyle(skin.getDrawable("joystick"), skin.getDrawable("knobP1")));
		touchpadP1.setPosition(8, 32);
		
		touchpadP2 = new Touchpad(2, new Touchpad.TouchpadStyle(skin.getDrawable("joystick"), skin.getDrawable("knobP2")));
		touchpadP2.setPosition(width-8-touchpadP2.getWidth(), height-32-touchpadP2.getHeight());
		
		addActor(touchpadP1);
		addActor(touchpadP2);
		
		//Coordinates
		coordsP1 = Resources.getInstance().coordsP1;
		coordsP2 = Resources.getInstance().coordsP2;
		coordsP1.setPosition(gridCoords.x, gridCoords.y);
		coordsP2.setPosition(gridCoords.x, gridCoords.y);
		
	
	
		//Asteroid timer
		aTimer = GameInstance.getInstance().asteroidsQuantity;
		if(aTimer==1) aTimer=0.01f;
		if(aTimer==3) aTimer=8f;

		//Exit group
		toMenuImage = new Image(skin.getDrawable("toMenu"));
		toMenuImage.setPosition(width/2-toMenuImage.getWidth()/2, 400);
		toMenuImage.addAction(Actions.hide());
		yesImage = new Image(skin.getDrawable("Yes"));
		yesImage.setPosition(toMenuImage.getX(), toMenuImage.getY()-yesImage.getHeight()-20);
		yesImage.addAction(Actions.hide());
		noImage = new Image(skin.getDrawable("No"));
		noImage.setPosition(toMenuImage.getX()+toMenuImage.getWidth()-noImage.getWidth(), toMenuImage.getY()-yesImage.getHeight()-20);
		noImage.addAction(Actions.hide());
		
		yesImage.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				yesImage.addAction(Actions.sizeBy(-2, -2));
				return true;				
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				yesImage.addAction(Actions.sizeBy(2, 2));
				toMenuImage.addAction(Actions.hide());
				noImage.addAction(Actions.hide());
				yesImage.addAction(Actions.hide());
		
				GameInstance.getInstance().gameOver = true;				
				GameInstance.getInstance().fadeBegin = true;
				GameInstance.getInstance().fadeEnd = false;	
			}
		});
		noImage.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				noImage.addAction(Actions.sizeBy(-2, -2));
				return true;				
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				noImage.addAction(Actions.sizeBy(2, 2));
				toMenuImage.addAction(Actions.hide());
				noImage.addAction(Actions.hide());
				yesImage.addAction(Actions.hide());
			}
		});
		
		//Players
		player1 = new Ship(0, new Vector2(MathUtils.random(gridCoords.x, width-shipSize),
				MathUtils.random(gridCoords.y, height-gridCoords.y-shipSize)));	
		GameInstance.getInstance().ships.add(player1);

		player2 = new Ship(1, new Vector2(MathUtils.random(gridCoords.x, width-shipSize),
					MathUtils.random(gridCoords.y, height-gridCoords.y-shipSize)));
		player2.setRotation(180);
		GameInstance.getInstance().ships.add(player2);
		
		keyboardP1 = new Keyboard(0);
		player1.touchpad = touchpadP1;
		keyboardP2 = new Keyboard(1);
		player2.touchpad = touchpadP2;
		
		group = new Group();
		group.addActor(keyboardP1);
		group.addActor(keyboardP2);

		addActor(group);
		addActor(coordsP1);
		addActor(coordsP2);

		//Sheathing
		for(int i=0;i<28;i++) {		
			GameInstance.getInstance().ships.get(0).sheathing.add(new Image(skin.getDrawable("sheathing"+String.valueOf(i+1))));
			GameInstance.getInstance().ships.get(1).sheathing.add(new Image(skin.getDrawable("sheathing"+String.valueOf(i+1))));
		}
		float lastWidth = 3;
		for(Image sheathing : GameInstance.getInstance().ships.get(0).sheathing) {			
			sheathing.setPosition(lastWidth, 127);
			lastWidth+=sheathing.getWidth()-4;
			addActor(sheathing);
		}
		lastWidth = 3;
		for(Image sheathing : GameInstance.getInstance().ships.get(1).sheathing) {	
			sheathing.setPosition(lastWidth, height-gridCoords.y-2);
			lastWidth+=sheathing.getWidth()-4;
			addActor(sheathing);
		}

		cam = getCamera();

		addActor(toMenuImage);
		addActor(yesImage);
		addActor(noImage);
		
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		gameBatch = new SpriteBatch();
		gameBatch.getProjectionMatrix().set(cam.combined);
		
	}
	
	@Override
	public void draw() {
		
		gameBatch.begin();
		
		if(GameInstance.getInstance().gameOver) asteroidTimer = 1;

		//Ships
		player1.draw(gameBatch);
		player2.draw(gameBatch);

		//Asteroids
		if(asteroidTimer  == 0) {
			Asteroid asteroid = new Asteroid();
			group.addActorBefore(keyboardP1, asteroid);
			GameInstance.getInstance().asteroids.add(asteroid);
		}
		asteroidTimer++;
		if(asteroidTimer>400/aTimer) asteroidTimer = 0;

		gameBatch.end();
	
		//Exit
		if(Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)){	
			toMenuImage.addAction(Actions.show());
			yesImage.addAction(Actions.show());
			noImage.addAction(Actions.show());
		}
		
		super.draw();
	}
	
	@Override
	public void dispose() {
		GameInstance.getInstance().resetGame();
		gameBatch.dispose();
		skin.dispose();
		clear();
		super.dispose();
	}

	
	public void resize(float width, float height) {
		
		cam.viewportWidth = this.width;
		cam.viewportHeight = this.height;
		cam.position.x = this.width/2;
		cam.position.y = this.height/2;
		cam.update();

	}

	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		Vector2 stageCoords = new Vector2();
		screenToStageCoordinates(stageCoords.set(x, y));
		InputEvent event = Pools.obtain(InputEvent.class);
		event.setType(Type.touchDown);
		event.setStage(this);
		event.setStageX(stageCoords.x);
		event.setStageY(stageCoords.y);
		event.setPointer(pointer);
		event.setButton(button);
		Actor target = hit(stageCoords.x, stageCoords.y, true);
		if (target == null) target = root;
		target.fire(event);
		boolean handled = event.isHandled();
		Pools.free(event);
		return handled;
	}
	
	@Override
	public boolean touchDragged (int x, int y, int pointer) {
		Vector2 stageCoords = new Vector2();
		screenToStageCoordinates(stageCoords.set(x, y));
		if ((stageCoords.x<=86 && stageCoords.y<=135) || (stageCoords.x>=width-86 && stageCoords.y>=height-135)) {
			super.touchDragged(x, y, pointer);
		} else return false;
		return false;
	}

}
