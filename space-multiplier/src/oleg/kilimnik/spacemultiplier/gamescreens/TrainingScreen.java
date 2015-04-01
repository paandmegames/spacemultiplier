package oleg.kilimnik.spacemultiplier.gamescreens;

import oleg.kilimnik.spacemultiplier.Constants;
import oleg.kilimnik.spacemultiplier.GameInstance;
import oleg.kilimnik.spacemultiplier.Resources;
import oleg.kilimnik.spacemultiplier.buffs.Asteroid;
import oleg.kilimnik.spacemultiplier.keyboard.Keyboard;
import oleg.kilimnik.spacemultiplier.ships.AIShip;
import oleg.kilimnik.spacemultiplier.ships.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class TrainingScreen extends Stage {
	
	private Image coordsP1;
	private Vector2 gridCoords = Constants.gridCoords;
	private float width = Constants.VIRTUAL_WIDTH;
	private float height = Constants.VIRTUAL_HEIGHT;
	private int shipSize = Constants.shipSize;
	private Ship player1;
	private AIShip player2;
	private Keyboard keyboardP1;
	private Keyboard keyboardP2;
	private SpriteBatch gameBatch;

	private Skin skin;

	private int asteroidTimer = 0;

	private Group group;
	private Image toMenuImage;
	private Image yesImage;
	private Image noImage;
	private CheckBox mTableCheckBox;
	private Preferences prefs;
	private float cellSize = Constants.cellSize;
	private float aTimer;
	private BitmapFont font;

	private Image howto; 
	private Image faq1;
	private Image faq2;
	private Image faq3;
	private Image faq4;
	private ScrollPane scrollPane1;
	private Table faqTable;
	private ShapeRenderer shapeRenderer;
	private Camera cam;
	private final Group root;
	public Touchpad touchpadP1;
	public Touchpad touchpadP2;

	public TrainingScreen() {
		
		root = new Group();
		addActor(root);
		
		font = new BitmapFont();
	
		prefs = Gdx.app.getPreferences("spacemultiplier");
		skin = new Skin();
		skin.addRegions(Resources.getInstance().atlas);
		
		touchpadP1 = new Touchpad(2, new Touchpad.TouchpadStyle(skin.getDrawable("joystick"), skin.getDrawable("knobP1")));
		touchpadP1.setPosition(8, 32);
		
		touchpadP2 = new Touchpad(2, new Touchpad.TouchpadStyle(skin.getDrawable("joystick"), skin.getDrawable("knobP2")));
		touchpadP2.setPosition(width-8-touchpadP2.getWidth(), height-32-touchpadP2.getHeight());
		
		addActor(touchpadP1);
		
		//Coordinates
		coordsP1 = Resources.getInstance().coordsP1;
		coordsP1.setPosition(gridCoords.x, gridCoords.y);

		//Asteroid timer
		aTimer = GameInstance.getInstance().asteroidsQuantity;
		if(aTimer==1) aTimer=0.01f;
		if(aTimer==3) aTimer=8f;

		//Multiplying table
		mTableCheckBox = new CheckBox("", new CheckBox.CheckBoxStyle(skin.getDrawable("mTable"), skin.getDrawable("mTableChecked"), new BitmapFont(), Color.CLEAR));
		mTableCheckBox.setPosition(width/2+10, height-gridCoords.y);
		mTableCheckBox.setChecked(prefs.getBoolean("mTableShow", false));
			
		//FAQ
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(Color.GREEN);
		
		faq1 = new Image(skin.getDrawable("faq1"));
		faq2 = new Image(skin.getDrawable("faq2"));
		faq3 = new Image(skin.getDrawable("faq3"));
		faq4 = new Image(skin.getDrawable("faq4"));
		
		faqTable = new Table();
		faqTable.columnDefaults(1);
		faqTable.row();
		faqTable.add(faq1);
		faqTable.row();
		faqTable.add(faq2);
		faqTable.row();
		faqTable.add(faq3);
		faqTable.row();
		faqTable.add(faq4);

		scrollPane1 = new ScrollPane(faqTable);
		scrollPane1.setPosition(5, height-130);
		scrollPane1.setWidth(faq1.getWidth());
		scrollPane1.setHeight(130);
		scrollPane1.addAction(Actions.hide());		
		addActor(scrollPane1);
		
		howto = new Image(skin.getDrawable("howtoplay"));
		howto.setPosition(10, height-110);
		float g = howto.getColor().g;
		howto.addAction(Actions.repeat(20, Actions.sequence(Actions.color(new Color(howto.getColor().r, 0, howto.getColor().b, 1), 1),
				Actions.color(new Color(howto.getColor().r, g, howto.getColor().b, 1)))));
		
		howto.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				howto.addAction(Actions.hide());
				return true;				
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				scrollPane1.addAction(Actions.show());
				scrollPane1.addAction(Actions.delay(5, Actions.run(new Runnable() {
					@Override
					public void run() {
						scrollPane1.fling(500, 0, -0.05f);						
					}					
				})));
			}
		});

		//Exit
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

		player2 = new AIShip(1, new Vector2(MathUtils.random(gridCoords.x, width-shipSize),
					MathUtils.random(gridCoords.y, height-gridCoords.y-shipSize)));
		player2.setRotation(180);
		GameInstance.getInstance().ships.add(player2);

		//Keyboards
		keyboardP1 = new Keyboard(0);
		player1.touchpad = touchpadP1;
		player2.touchpad = touchpadP2;
		keyboardP2 = new Keyboard(1);
		player2.keyboard = keyboardP2;
	
		group = new Group();
		group.addActor(keyboardP1);
		group.addActor(mTableCheckBox);
		group.addActor(howto);
		
		addActor(group);
		addActor(coordsP1);
		
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
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		gameBatch = new SpriteBatch();
		gameBatch.getProjectionMatrix().set(getCamera().combined);
		
		addActor(toMenuImage);
		addActor(yesImage);
		addActor(noImage);

	}
	
	@Override
	public void draw() {

		
	
		
		//asteroids
		if(asteroidTimer  == 0) {
			Asteroid asteroid = new Asteroid();
			group.addActorBefore(keyboardP1, asteroid);
			GameInstance.getInstance().asteroids.add(asteroid);
		}
		asteroidTimer++;
		if(asteroidTimer>400/aTimer) asteroidTimer = 0;
		if(GameInstance.getInstance().gameOver) asteroidTimer = 1;
		
		gameBatch.begin();
				
		//Ships
		player1.draw(gameBatch);
		player2.draw(gameBatch);

		//Multiplying table
		if(mTableCheckBox.isChecked()) {
			for(int i=1; i<=9;i++) {
				for(int j=1;j<=9;j++) {
					font.draw(gameBatch, String.valueOf(i*j), cellSize*2/3+cellSize*(i-1), cellSize-2+cellSize*(j-1)+gridCoords.y);
				}
			}
		}
			
		gameBatch.end();
		
		if(Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)){	
			toMenuImage.addAction(Actions.show());
			yesImage.addAction(Actions.show());
			noImage.addAction(Actions.show());
		}
		
		//FAQ : lines
		if(scrollPane1.getScrollPercentY()>0.25f && scrollPane1.getScrollPercentY()<0.58f) {
			player2.isMooving = false;
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.line(player2.position.x+32, player2.position.y+32, player2.position.x+32, gridCoords.y+20);
			shapeRenderer.end();
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.circle(player2.position.x+32, gridCoords.y+14, 15);
			shapeRenderer.end();
		}
		if(scrollPane1.getScrollPercentY()>0.31f && scrollPane1.getScrollPercentY()<0.58f) {
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.line(player2.position.x+32, player2.position.y+32, 20, player2.position.y+32);
			shapeRenderer.end();
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.circle(10, player2.position.y+32, 15);
			shapeRenderer.end();
		}
		if(scrollPane1.getScrollPercentY()>0.58f) player2.isMooving = true;
		
		super.draw();
	}
	
	
	@Override
	public void dispose() {
		prefs.putBoolean("mTableShow", mTableCheckBox.isChecked());
		prefs.flush();
		GameInstance.getInstance().resetGame();
		gameBatch.dispose();
		skin.dispose();
		font.dispose();
		shapeRenderer.dispose();
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

}
