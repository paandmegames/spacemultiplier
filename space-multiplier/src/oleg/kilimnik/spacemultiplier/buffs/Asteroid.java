package oleg.kilimnik.spacemultiplier.buffs;

import oleg.kilimnik.spacemultiplier.Constants;
import oleg.kilimnik.spacemultiplier.GameInstance;
import oleg.kilimnik.spacemultiplier.Resources;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Asteroid extends Group {
	private int width = Constants.VIRTUAL_WIDTH;
	private int height = Constants.VIRTUAL_HEIGHT;
	private Vector2 gridCoords = Constants.gridCoords;
	private int size = 64;
	private Vector2 position = new Vector2();
	public int type;
	int direction;
	int startPos;
	private Image asteroidUp;
	private Image asteroidDown;
	private Image buffName;
	private int time;
	public float aWidth;
	private float scale;
	private Skin skin;
	
	public Asteroid() {
		skin = new Skin();
		skin.addRegions(Resources.getInstance().atlas);
		asteroidUp = new Image(skin.getDrawable("asteroid"));
		asteroidUp.setOrigin(asteroidUp.getWidth() / 2.f, 32);
		asteroidUp.addAction(Actions.rotateBy(180));
		asteroidDown = new Image(skin.getDrawable("asteroid"));
		
		setOrigin(32, 32);
		aWidth = 64;
		scale = MathUtils.random(0.7f, 1f);
		setScale(scale);
		aWidth *=scale; 
		type = MathUtils.random(1, 4);
		
		buffName = new Image(skin.getDrawable("buff"+String.valueOf(type)));
		
		buffName.addAction(Actions.alpha(0));
		buffName.setScale(0.5f);
		buffName.setOrigin(buffName.getWidth()/2, buffName.getHeight()/2);
		
		time = 2000/GameInstance.getInstance().asteroidsSpeed;
		setPosition(-100, 0);
		int dir = MathUtils.random(1, 4);
		int pos = MathUtils.random(0, 480-size);
		if(dir==1) {
			position.x = -size;
			position.y = gridCoords.y+pos;
			addAction(Actions.sequence(Actions.moveTo(position.x, position.y), Actions.moveBy(width+size, 0, time)));	
			addAction(Actions.delay(time-1, Actions.sequence(Actions.alpha(0, 1), Actions.removeActor())));
		}
		if(dir==2) {
			position.x = pos;
			position.y = height-gridCoords.y;
			addAction(Actions.sequence(Actions.alpha(0), 
					Actions.moveTo(position.x, position.y), 
					Actions.parallel(Actions.alpha(1, 1), Actions.moveBy(0, -width-size, time))));
			addAction(Actions.delay(time-1, Actions.sequence(Actions.alpha(0, 1), Actions.removeActor())));
		}
		if(dir==3) {
			position.x = width;
			position.y = gridCoords.y+pos;
			addAction(Actions.sequence(Actions.moveTo(position.x, position.y), Actions.moveBy(-width-size, 0, time)));
			addAction(Actions.delay(time-1, Actions.sequence(Actions.alpha(0, 1), Actions.removeActor())));
		}
		if(dir==4) {
			position.x = pos;
			position.y = gridCoords.y-size;
			addAction(Actions.sequence(Actions.alpha(0), 
					Actions.moveTo(position.x, position.y), 
					Actions.parallel(Actions.alpha(1, 1), Actions.moveBy(0, width+size, time))));
			addAction(Actions.delay(time-1, Actions.sequence(Actions.alpha(0, 1), Actions.removeActor())));
		}
		
		addAction(Actions.forever(Actions.rotateBy(360, 20)));
	
		addActor(asteroidUp);
		addActor(asteroidDown);
		addActor(buffName);

	}

	public void delete() {
		asteroidUp.addAction(Actions.parallel(Actions.alpha(0, 2), Actions.moveTo(asteroidUp.getX(), asteroidUp.getY()+20, 2)));
		asteroidDown.addAction(Actions.parallel(Actions.alpha(0, 2), Actions.moveTo(asteroidDown.getX(), asteroidUp.getY()-20, 2)));
		buffName.addAction(Actions.parallel(Actions.rotateBy(360, 3), Actions.alpha(1, 2), Actions.scaleBy(0.6f, 0.6f, 3)));
		buffName.addAction(Actions.delay(2, Actions.alpha(0, 1)));
		addAction(Actions.delay(3, Actions.removeActor()));
		GameInstance.getInstance().asteroids.removeValue(this, true);
	}
}
