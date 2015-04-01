package oleg.kilimnik.spacemultiplier.ships;

import oleg.kilimnik.spacemultiplier.Constants;
import oleg.kilimnik.spacemultiplier.GameInstance;
import oleg.kilimnik.spacemultiplier.Resources;
import oleg.kilimnik.spacemultiplier.buffs.Asteroid;
import oleg.kilimnik.spacemultiplier.buffs.BuffTimer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Array;

public class Ship extends Sprite {

	protected int width = Constants.VIRTUAL_WIDTH;
	protected int height = Constants.VIRTUAL_HEIGHT;
	protected Vector2 gridCoords = Constants.gridCoords;
	protected int shipSize = Constants.shipSize;
	protected float amount = 1.0f;
	protected float hitPoints = 0;
	protected float maxHitPoints = 0;
	public Vector2 position = new Vector2();
	public int velocity;
	public Vector2 facing = new Vector2();
	private float delta;
	public int id;
	public Touchpad touchpad;
	public int power;
	private int buffType = 0;
	public Array<BuffTimer> buffs = new Array<BuffTimer>();
	public Array<Image> sheathing = new Array<Image>();
	public boolean armored = false;
	private float opacity = 1f;
	public boolean alive = true;
	protected ParticleEffect fire;
	protected int fireAngle;
	public boolean isStealthy = false;
	private float damagedTimer = -1;
	private float colorTimer = -1;
	private float r;
	private float g;
	private float b;
	
	public Ship(int id, Vector2 position) {

		this.id = id;
		this.position.set(position);
		
		if(id==0) {
			fireAngle = -90;
			facing.x = 0;
			facing.y = 1;
			set(Resources.getInstance().shipP1);
		} else {
			fireAngle = 90;
			facing.x = 0;
			facing.y = -1;
			set(Resources.getInstance().shipP2);
		}
		velocity = GameInstance.getInstance().shipsSpeed;	
	
		hitPoints = 2800;
		power = 100;
		setOrigin(getWidth() / 2.f, getHeight() / 2.f);
			fire = new ParticleEffect();
			fire.load(Gdx.files.internal("data/particles/fire.p"), Gdx.files.internal("data/particles"));
			fire.setPosition(position.x+32-32*facing.x, position.y+32-32*facing.y);
			if(!fire.isComplete()) fire.reset();
			fire.findEmitter("fire").getAngle().setLow(fireAngle);
			fire.start();

		
		r = getColor().r;
		g = getColor().g;
		b = getColor().b;
	}

	@Override
	public void draw(Batch batch) {
		delta = Gdx.graphics.getDeltaTime();
		if(hitPoints == 0) {
			shipDestruct();
			super.draw(batch);
			return;
		}
		if(!isStealthy)fire.draw(batch, delta);

		if(touchpad.getKnobPercentX()!=0 || touchpad.getKnobPercentY()!=0) {
			
			float x0 = getX();
			float y0 = getY();
			float x1 = x0 + touchpad.getKnobPercentX()*velocity*delta;
			float y1 = y0 + touchpad.getKnobPercentY()*velocity*delta;
			position.add(touchpad.getKnobPercentX()*velocity*delta, touchpad.getKnobPercentY()*velocity*delta);
			setRotation(MathUtils.atan2(x0-x1, y1-y0)*MathUtils.radiansToDegrees);

			fire.findEmitter("fire").getAngle().setLow(MathUtils.atan2(x0-x1, y1-y0)*MathUtils.radiansToDegrees-90);
			facing.x = (float) ((x1-x0)/Math.sqrt((x1 - x0)*(x1-x0)+(y1-y0)*(y1-y0)));
			facing.y = (float) ((y1-y0)/Math.sqrt((x1 - x0)*(x1-x0)+(y1-y0)*(y1-y0)));
			
		}
		
		if(position.x>=width-shipSize) position.x = width-shipSize;
		if(position.x<=0) position.x = 0;
		if(position.y>=height-gridCoords.y-shipSize) position.y = height-gridCoords.y-shipSize;
		if(position.y<=gridCoords.y) position.y = gridCoords.y;
		setPosition(position.x, position.y);

		fire.setPosition(position.x+32-32*facing.x, position.y+32-32*facing.y);


		for(Asteroid asteroid : GameInstance.getInstance().asteroids) {
			if(Math.abs(asteroid.getX()-position.x)<asteroid.aWidth/2+shipSize/2 && 
					Math.abs(asteroid.getY()-position.y)<asteroid.aWidth/2+shipSize/2) {			
		
				damage(100);
				buff(asteroid); 

			}
		}
		
		for(BuffTimer buffTimer : buffs) {
			buffTimer.timer = Math.max(buffTimer.timer-delta, 0);
			if(buffTimer.timer==0) {
				buffTimer.unbuff();
				buffs.removeValue(buffTimer, true);
			}
		}
		
		if(damagedTimer>0) {
			damagedTimer -= delta;
			if(damagedTimer<0.6f) {
				setColor(r, 0, b, getColor().a);
				colorTimer = 0.15f;
				damagedTimer = -1;
			}
		}
		if(colorTimer>0) {
			colorTimer = Math.max(0, colorTimer-delta);
		} 
		if(colorTimer == 0) {
			setColor(r, g, b, getColor().a);
			colorTimer = -1;
		}
		
		super.draw(batch);	

	}

	public float healthPercentage() {
		return Math.max(hitPoints / maxHitPoints, 0);
	}

	public void damage(float amount) {
		if(hitPoints==0  || armored) return;
		if(hitPoints<amount) {
			for(int i=0;i<Math.ceil(hitPoints/100);i++) {
				Image randomSheathing = sheathing.random();
				randomSheathing.addAction(Actions.sequence(Actions.alpha(0, 2), Actions.removeActor()));
				sheathing.removeValue(randomSheathing, true);
			}
			hitPoints = Math.max(hitPoints - amount, 0);
			damagedTimer  = 0.9f;
		} else {		
			for(int i=0;i<Math.ceil(amount/100);i++) {
				Image randomSheathing = sheathing.random();
				randomSheathing.addAction(Actions.sequence(Actions.alpha(0, 2), Actions.removeActor()));
				sheathing.removeValue(randomSheathing, true);
			}
			hitPoints = Math.max(hitPoints - amount, 0);
			damagedTimer  = 0.9f;		
		}
	}

	public void buff(Asteroid asteroid) {
		
		if(GameInstance.getInstance().sound)Resources.getInstance().buffSound.play();
		buffType = asteroid.type;

		asteroid.delete();
			
		for(BuffTimer b : buffs) {
			if(b.type==buffType) {
				b.unbuff();
				buffs.removeValue(b, true);
			}
		}
		buffs.add(new BuffTimer(buffType, this));
		
		switch (buffType) {
		case 1:
			velocity+=100;
			break;
		case 2:
			power*=4;
			break;
		case 3:
			setAlpha(0);
			isStealthy  = true;
			break;
		case 4:
			armored  = true;
			break;
		}
		
	}
	
	public void shipDestruct() {
		if(opacity==0 && alive){
			alive = false;
			if(GameInstance.getInstance().sound) Resources.getInstance().explosionSound.play();
			GameInstance.getInstance().gameOver = true;
		}
		delta = Gdx.graphics.getDeltaTime();
		if(opacity == 1f) {
			ParticleEffect explosion = new ParticleEffect();
			explosion.load(Gdx.files.internal("data/particles/explosion.p"), Gdx.files.internal("data/particles"));
			explosion.setPosition(position.x+32, position.y+32);
			if(!explosion.isComplete()) explosion.reset();
			explosion.start();
			GameInstance.getInstance().explosions.add(explosion);
		}
		setColor(r, g, b, opacity);
		opacity = Math.max(0, opacity-delta);
		
	}

}
