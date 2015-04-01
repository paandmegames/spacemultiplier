package oleg.kilimnik.spacemultiplier.mainmenu;

import oleg.kilimnik.spacemultiplier.Constants;
import oleg.kilimnik.spacemultiplier.GameInstance;
import oleg.kilimnik.spacemultiplier.Resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

import java.util.Locale;

public class MainMenu extends Stage {
	
	private ImageButton training;
	private ImageButton twoPlayers;
	private ImageButton exit;
	private ImageButton settings;
	private int width = Constants.VIRTUAL_WIDTH;
	private int height = Constants.VIRTUAL_HEIGHT;
	
	private Image shipsSpeed;
	private Image asteroidsSpeed;
	private Image sound;
	private Image asteroidsQuantity;
	private Slider aQSlider;
	private Image lowQ;
	private Image middleQ;
	private Image manyQ;
	private Slider shipsSpeedSlider;
	private Slider asteroidsSpeedSlider;
	private CheckBox soundCheckBox;
	private ImageButton back;
	private Image parsekIs;
	private Image parsek1;
	private Image parsek2;
	private Image twentyfive1;
	private Image twentyfive2;
	private Image fifty1;
	private Image fifty2;
	private Image seventyfive1;
	private Image seventyfive2;
	private Image hundred1;
	private Image hundred2;

	private float spaceFlyTimer = 0;
	private boolean animateSpaceFly = false;
	private ParticleEffect space;
	private SpriteBatch menuBatch;
	private float delta;
	private Skin skin;
	private Preferences prefs;
	private Group group;
	
	public MainMenu() {
		skin = new Skin();
		skin.addRegions(Resources.getInstance().atlas);
		
		
		
		prefs = Gdx.app.getPreferences("spacemultiplier");
		GameInstance.getInstance().shipsSpeed  = prefs.getInteger("shipsSpeed", 25);
		GameInstance.getInstance().asteroidsSpeed  = prefs.getInteger("asteroidsSpeed", 25);	

		GameInstance.getInstance().sound  = prefs.getBoolean("sound", true);
		GameInstance.getInstance().asteroidsQuantity = prefs.getInteger("asteroidsQuantity", 1);
		
		training = new ImageButton(skin.getDrawable("menu1"));
		twoPlayers = new ImageButton(skin.getDrawable("menu2"));
		settings = new ImageButton(skin.getDrawable("menuSettings"));
		exit = new ImageButton(skin.getDrawable("menu3"));
		training.setPosition(width/2-training.getWidth()/2, 500);
		twoPlayers.setPosition(width/2-twoPlayers.getWidth()/2, 400);
		settings.setPosition(width/2-settings.getWidth()/2, 300);
		exit.setPosition(width/2-exit.getWidth()/2, 200);
		training.addAction(Actions.alpha(0));
		twoPlayers.addAction(Actions.alpha(0));
		settings.addAction(Actions.alpha(0));
		exit.addAction(Actions.alpha(0));
		training.addAction(Actions.alpha(1,1));
		twoPlayers.addAction(Actions.alpha(1,1));
		settings.addAction(Actions.alpha(1,1));
		exit.addAction(Actions.alpha(1,1));
		
		
		sound = new Image(skin.getDrawable("settingsSound"));
		sound.setPosition(100-400, 650);
		sound.addAction(Actions.alpha(0));
		soundCheckBox = new CheckBox("", new CheckBox.CheckBoxStyle(skin.getDrawable("checkBoxOff"), skin.getDrawable("checkBoxOn"), new BitmapFont(), Color.CLEAR));
		if(Locale.getDefault().getLanguage().equals("ru") || Locale.getDefault().getLanguage().equals("uk"))
			soundCheckBox.setPosition(sound.getX()+120, sound.getY()+5);
		else soundCheckBox.setPosition(sound.getX()+120, sound.getY());
		soundCheckBox.setChecked(GameInstance.getInstance().sound);
		soundCheckBox.addAction(Actions.alpha(0));
		
		shipsSpeed = new Image(skin.getDrawable("settingsShipsSpeed"));
		shipsSpeed.setPosition(100-400, sound.getY()-50);
		shipsSpeed.addAction(Actions.alpha(0));
		shipsSpeedSlider = new Slider(25, 100, 25, false, new Slider.SliderStyle(skin.getDrawable("sliderLine"), skin.getDrawable("slider")));
		shipsSpeedSlider.setPosition(100-400, shipsSpeed.getY()-50);
		shipsSpeedSlider.setValue(GameInstance.getInstance().shipsSpeed);
		shipsSpeedSlider.addAction(Actions.alpha(0));
		
		asteroidsSpeed = new Image(skin.getDrawable("settingsAsteroidsSpeed"));
		asteroidsSpeed.setPosition(100-400, shipsSpeedSlider.getY()-50);
		asteroidsSpeed.addAction(Actions.alpha(0));
		asteroidsSpeedSlider = new Slider(25, 100, 25, false, new Slider.SliderStyle(skin.getDrawable("sliderLine"), skin.getDrawable("slider")));
		asteroidsSpeedSlider.setPosition(100-400, asteroidsSpeed.getY()-50);
		asteroidsSpeedSlider.setValue(GameInstance.getInstance().asteroidsSpeed);
		asteroidsSpeedSlider.addAction(Actions.alpha(0));
		
		asteroidsQuantity = new Image(skin.getDrawable("asteroidsQuantity"));
		asteroidsQuantity.setPosition(100-400, asteroidsSpeedSlider.getY()-50);
		asteroidsQuantity.addAction(Actions.alpha(0));
		aQSlider = new Slider(1, 3, 1, false, new Slider.SliderStyle(skin.getDrawable("sliderLine"), skin.getDrawable("slider")));
		aQSlider.setPosition(100-400, asteroidsQuantity.getY()-50);
		aQSlider.setValue(GameInstance.getInstance().asteroidsQuantity);
		aQSlider.addAction(Actions.alpha(0));

	
		
		parsekIs = new Image(skin.getDrawable("settingsParsekIs"));
		parsekIs.setPosition(width/2 - parsekIs.getWidth()/2-400, aQSlider.getY()-100);
		parsekIs.addAction(Actions.alpha(0));
		
		back = new ImageButton(skin.getDrawable("menuBack"));
		back.setPosition(width/2 - back.getWidth()/2-400, parsekIs.getY()-100);
		back.addAction(Actions.alpha(0));


		parsek1 = new Image(skin.getDrawable("settingsParsek"));
		parsek1.setPosition(width-parsek1.getWidth()-100-400, shipsSpeedSlider.getY()+5);
		parsek1.addAction(Actions.alpha(0));
		twentyfive1 = new Image(skin.getDrawable("25"));
		twentyfive1.setPosition(width-twentyfive1.getWidth()-parsek1.getWidth()-105-400, shipsSpeedSlider.getY()+5);
		twentyfive1.addAction(Actions.alpha(0));
		fifty1 = new Image(skin.getDrawable("50"));
		fifty1.setPosition(width - fifty1.getWidth()-parsek1.getWidth()-105-400, shipsSpeedSlider.getY()+5);
		fifty1.addAction(Actions.alpha(0));
		seventyfive1 = new Image(skin.getDrawable("75"));
		seventyfive1.setPosition(width-seventyfive1.getWidth()-parsek1.getWidth()-105-400, shipsSpeedSlider.getY()+5);
		seventyfive1.addAction(Actions.alpha(0));
		hundred1 = new Image(skin.getDrawable("100"));
		hundred1.setPosition(width-hundred1.getWidth()-parsek1.getWidth()-105-400, shipsSpeedSlider.getY()+5);
		hundred1.addAction(Actions.alpha(0));
		
		parsek2 = new Image(skin.getDrawable("settingsParsek"));
		parsek2.setPosition(width-parsek2.getWidth()-100-400, asteroidsSpeedSlider.getY()+5);
		parsek2.addAction(Actions.alpha(0));
		twentyfive2 = new Image(skin.getDrawable("25"));
		twentyfive2.setPosition(width-twentyfive2.getWidth()-parsek2.getWidth()-105-400, asteroidsSpeedSlider.getY()+5);
		twentyfive2.addAction(Actions.alpha(0));
		fifty2 = new Image(skin.getDrawable("50"));
		fifty2.setPosition(width - fifty2.getWidth()-parsek2.getWidth()-105-400, asteroidsSpeedSlider.getY()+5);
		fifty2.addAction(Actions.alpha(0));
		seventyfive2 = new Image(skin.getDrawable("75"));
		seventyfive2.setPosition(width-seventyfive2.getWidth()-parsek2.getWidth()-105-400, asteroidsSpeedSlider.getY()+5);
		seventyfive2.addAction(Actions.alpha(0));
		hundred2 = new Image(skin.getDrawable("100"));
		hundred2.setPosition(width-hundred2.getWidth()-parsek2.getWidth()-105-400, asteroidsSpeedSlider.getY()+5);
		hundred2.addAction(Actions.alpha(0));

		
		lowQ = new Image(skin.getDrawable("minimum"));
		lowQ.setPosition(width-lowQ.getWidth()-500, aQSlider.getY());
		lowQ.addAction(Actions.alpha(0));
		middleQ = new Image(skin.getDrawable("middle"));
		middleQ.setPosition(width-middleQ.getWidth()-500, aQSlider.getY());
		middleQ.addAction(Actions.alpha(0));
		manyQ = new Image(skin.getDrawable("maximum"));
		manyQ.setPosition(width-manyQ.getWidth()-500, aQSlider.getY());
		manyQ.addAction(Actions.alpha(0));
		
		training.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				training.addAction(Actions.sizeBy(-2, -2));
				return true;				
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				training.addAction(Actions.sizeBy(2, 2));
				training.addAction(Actions.alpha(0, 1f));
				twoPlayers.addAction(Actions.alpha(0, 1f));
				settings.addAction(Actions.alpha(0, 1f));
				exit.addAction(Actions.alpha(0, 1f));
				animateSpaceFly  = true;
				GameInstance.getInstance().fadeBegin = true;
				GameInstance.getInstance().fadeEnd = false;
				GameInstance.getInstance().training = true;
			}
		});
		twoPlayers.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				twoPlayers.addAction(Actions.sizeBy(-2, -2));
				return true;				
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				twoPlayers.addAction(Actions.sizeBy(2, 2));
				training.addAction(Actions.alpha(0, 1f));
				twoPlayers.addAction(Actions.alpha(0, 1f));
				settings.addAction(Actions.alpha(0, 1f));
				exit.addAction(Actions.alpha(0, 1f));
				animateSpaceFly  = true;
				GameInstance.getInstance().fadeBegin = true;
				GameInstance.getInstance().fadeEnd = false;
			}
		});
		settings.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
					settings.addAction(Actions.sizeBy(-2, -2));
				return true;				
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				settings.addAction(Actions.sizeBy(2, 2));
				training.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				twoPlayers.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				settings.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				exit.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				
				shipsSpeed.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				asteroidsSpeed.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				asteroidsQuantity.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				
				sound.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				shipsSpeedSlider.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				asteroidsSpeedSlider.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				aQSlider.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				
				soundCheckBox.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				back.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				parsekIs.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));

				parsek1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
	
				
				if(shipsSpeedSlider.getValue()==25) {
					twentyfive1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
					fifty1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					seventyfive1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					hundred1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0)))); 
				}
				if(shipsSpeedSlider.getValue()==50) {
					twentyfive1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					fifty1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
					seventyfive1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0)))); 
					hundred1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
				}
				if(shipsSpeedSlider.getValue()==75) {
					twentyfive1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					fifty1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					seventyfive1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
					hundred1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0)))); 
				}
				if(shipsSpeedSlider.getValue()==100) {
					twentyfive1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					fifty1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0)))); 
					seventyfive1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					hundred1.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				}
				
				
			parsek2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
	
				
				if(asteroidsSpeedSlider.getValue()==25) {
					twentyfive2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
					fifty2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					seventyfive2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					hundred2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0)))); 
				}
				if(asteroidsSpeedSlider.getValue()==50) {
					twentyfive2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					fifty2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
					seventyfive2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0)))); 
					hundred2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
				}
				if(asteroidsSpeedSlider.getValue()==75) {
					twentyfive2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					fifty2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					seventyfive2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
					hundred2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0)))); 
				}
				if(asteroidsSpeedSlider.getValue()==100) {
					twentyfive2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					fifty2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0)))); 
					seventyfive2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					hundred2.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				}
				
				if(aQSlider.getValue()==1) {
					lowQ.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
					middleQ.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					manyQ.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0)))); 
				}
				if(aQSlider.getValue()==2) {
					lowQ.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					middleQ.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
					manyQ.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
				}
				if(aQSlider.getValue()==3) {
					lowQ.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0))));
					middleQ.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(0)))); 
					manyQ.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				}
			}
		});
		exit.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				exit.addAction(Actions.sizeBy(-2, -2));
				return true;				
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				exit.addAction(Actions.sizeBy(2, 2));
				System.exit(0);
			}
		});
		back.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {			
				back.addAction(Actions.sizeBy(-2, -2));
				return true;				
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				back.addAction(Actions.sizeBy(2, 2));
				
				prefs.putInteger("shipsSpeed",(int)shipsSpeedSlider.getValue());
				GameInstance.getInstance().shipsSpeed  = (int)shipsSpeedSlider.getValue();
				
				prefs.putInteger("asteroidsSpeed",(int)asteroidsSpeedSlider.getValue());
				GameInstance.getInstance().asteroidsSpeed  = (int)asteroidsSpeedSlider.getValue();
					
				prefs.putBoolean("sound", soundCheckBox.isChecked());
				GameInstance.getInstance().sound  =  soundCheckBox.isChecked();
				
				prefs.putInteger("asteroidsQuantity", (int)aQSlider.getValue());
				GameInstance.getInstance().asteroidsQuantity  = (int)aQSlider.getValue();
	
				prefs.flush();
				
				training.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				twoPlayers.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				settings.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				exit.addAction(Actions.delay(1, Actions.sequence(Actions.moveBy(400, 0), Actions.alpha(1,1))));
				
				shipsSpeed.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				asteroidsSpeed.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				asteroidsQuantity.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				
				sound.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				shipsSpeedSlider.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				asteroidsSpeedSlider.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				aQSlider.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				
				soundCheckBox.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				back.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				parsekIs.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				
				parsek1.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				twentyfive1.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				fifty1.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				seventyfive1.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				hundred1.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				
				parsek2.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				twentyfive2.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				fifty2.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				seventyfive2.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				hundred2.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));	
				
			
				lowQ.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));
				middleQ.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));	
				manyQ.addAction(Actions.sequence(Actions.alpha(0, 1f), Actions.moveBy(-400, 0)));	
				
			}
		});
		
		shipsSpeedSlider.addListener(new EventListener() {

			@Override
			public boolean handle(Event arg0) {
				if(shipsSpeedSlider.getValue()==25) {
					twentyfive1.addAction(Actions.alpha(1)); 
					fifty1.addAction(Actions.alpha(0)); 
					seventyfive1.addAction(Actions.alpha(0)); 
					hundred1.addAction(Actions.alpha(0)); 
				}
				if(shipsSpeedSlider.getValue()==50) {
					twentyfive1.addAction(Actions.alpha(0)); 
					fifty1.addAction(Actions.alpha(1)); 
					seventyfive1.addAction(Actions.alpha(0)); 
					hundred1.addAction(Actions.alpha(0)); 
				}
				if(shipsSpeedSlider.getValue()==75) {
					twentyfive1.addAction(Actions.alpha(0)); 
					fifty1.addAction(Actions.alpha(0)); 
					seventyfive1.addAction(Actions.alpha(1)); 
					hundred1.addAction(Actions.alpha(0)); 
				}
				if(shipsSpeedSlider.getValue()==100) {
					twentyfive1.addAction(Actions.alpha(0)); 
					fifty1.addAction(Actions.alpha(0)); 
					seventyfive1.addAction(Actions.alpha(0)); 
					hundred1.addAction(Actions.alpha(1)); 
				}
				return false;
			}
			
		});
		
		asteroidsSpeedSlider.addListener(new EventListener() {

			@Override
			public boolean handle(Event arg0) {
				if(asteroidsSpeedSlider.getValue()==25) {
					twentyfive2.addAction(Actions.alpha(1)); 
					fifty2.addAction(Actions.alpha(0)); 
					seventyfive2.addAction(Actions.alpha(0)); 
					hundred2.addAction(Actions.alpha(0)); 
				}
				if(asteroidsSpeedSlider.getValue()==50) {
					twentyfive2.addAction(Actions.alpha(0)); 
					fifty2.addAction(Actions.alpha(1)); 
					seventyfive2.addAction(Actions.alpha(0)); 
					hundred2.addAction(Actions.alpha(0)); 
				}
				if(asteroidsSpeedSlider.getValue()==75) {
					twentyfive2.addAction(Actions.alpha(0)); 
					fifty2.addAction(Actions.alpha(0)); 
					seventyfive2.addAction(Actions.alpha(1)); 
					hundred2.addAction(Actions.alpha(0)); 
				}
				if(asteroidsSpeedSlider.getValue()==100) {
					twentyfive2.addAction(Actions.alpha(0)); 
					fifty2.addAction(Actions.alpha(0)); 
					seventyfive2.addAction(Actions.alpha(0)); 
					hundred2.addAction(Actions.alpha(1)); 
				}
				return false;
			}
			
		});
		
		aQSlider.addListener(new EventListener() {

			@Override
			public boolean handle(Event arg0) {
				if(aQSlider.getValue()==1) {
					lowQ.addAction(Actions.alpha(1)); 
					middleQ.addAction(Actions.alpha(0));  
					manyQ.addAction(Actions.alpha(0));
				}
				if(aQSlider.getValue()==2) {
					lowQ.addAction(Actions.alpha(0)); 
					middleQ.addAction(Actions.alpha(1));  
					manyQ.addAction(Actions.alpha(0));  
				}
				if(aQSlider.getValue()==3) {
					lowQ.addAction(Actions.alpha(0)); 
					middleQ.addAction(Actions.alpha(0));  
					manyQ.addAction(Actions.alpha(1)); 
				}
				return false;
			}
		});
		

		
		group = new Group();
		group.addActor(training);
		group.addActor(twoPlayers);
		group.addActor(settings);
		group.addActor(exit);
		group.addActor(shipsSpeed);
		group.addActor(asteroidsSpeed);

		group.addActor(sound);
		group.addActor(shipsSpeedSlider);
		group.addActor(asteroidsSpeedSlider);
		group.addActor(soundCheckBox);

		group.addActor(back);
		group.addActor(parsekIs);
		group.addActor(parsek1);
		group.addActor(twentyfive1);
		group.addActor(fifty1);
		group.addActor(seventyfive1);
		group.addActor(hundred1);
		group.addActor(parsek2);
		group.addActor(twentyfive2);
		group.addActor(fifty2);
		group.addActor(seventyfive2);
		group.addActor(hundred2);

		group.addActor(asteroidsQuantity);
		group.addActor(aQSlider);
		group.addActor(lowQ);
		group.addActor(manyQ);
		group.addActor(middleQ);
		addActor(group);
		
		space = new ParticleEffect();
		space.load(Gdx.files.internal("data/particles/menuParticles.p"), Gdx.files.internal("data/particles"));
		space.setPosition(width/2, height/2);
		if(!space.isComplete()) space.reset();
		space.start();
		spaceFlyTimer = 0;
		
		menuBatch = new SpriteBatch();
		menuBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		
	}

	@Override
	public void draw() {
		delta = Gdx.graphics.getDeltaTime();
	
		menuBatch.begin();
		space.draw(menuBatch, delta);
		menuBatch.end();	
		super.draw();
		
		if(animateSpaceFly) {
			spaceFlyTimer+=delta;		
			menuBatch.getProjectionMatrix().setToOrtho2D(spaceFlyTimer*175,
					spaceFlyTimer*200, width-spaceFlyTimer*350, height-spaceFlyTimer*400);
			if(spaceFlyTimer>2f) {
				GameInstance.getInstance().gameStart = true;
				dispose();	
			}
		}
		
		
	}
	
	@Override
	public void dispose() {
		space.dispose();
		menuBatch.dispose();
		skin.dispose();
		clear();
		super.dispose();	
	}
	
}
