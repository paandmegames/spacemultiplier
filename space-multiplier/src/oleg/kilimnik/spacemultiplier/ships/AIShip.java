package oleg.kilimnik.spacemultiplier.ships;

import oleg.kilimnik.spacemultiplier.keyboard.Keyboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class AIShip extends Ship {
	
	private float moveTimer = 0;
	private float shootTimer = 5;
	private float directionX;
	private float directionY;
	private boolean changeDir = true;
	private boolean randomY;
	public Array<String> target = new Array<String>();
	public boolean isMooving = true;
	public Keyboard keyboard;
	
	public AIShip(int id, Vector2 position) {
		super(id, position);
		target.addAll(new String[] {"1", "2", "3", "4", "5", "6", "7" ,"8", "9", "10", "12", "14", "15", "16", "18",
				"20", "21", "24", "25", "27", "28", "30", "32", "35", "36", "40", "42", "45", "48", "49", "54", "56",
				"63", "64", "72", "81"});
	}
	
	@Override
	public void draw(Batch batch) {
		float d = Gdx.graphics.getDeltaTime();
		
		if(isMooving) {

		if(hitPoints!=0) {
		if(changeDir) {
			moveTimer = MathUtils.random(5, 10);
			directionX = MathUtils.random(-1f, 1f);
			randomY = MathUtils.randomBoolean();
			if(randomY) directionY = (float)Math.sqrt(1-directionX*directionX);
			if(!randomY) directionY = -(float)Math.sqrt(1-directionX*directionX);
			changeDir = false;
		} else {
			
			float x0 = getX();
			float y0 = getY();
			float x1 = x0 + directionX*velocity*d;
			float y1 = y0 + directionY*velocity*d;
			position.add(directionX*velocity*d, directionY*velocity*d);
			setRotation(MathUtils.atan2(x0-x1, y1-y0)*MathUtils.radiansToDegrees);
			
			fire.findEmitter("fire").getAngle().setLow(MathUtils.atan2(x0-x1, y1-y0)*MathUtils.radiansToDegrees-90);
			
			facing.x = (float) ((x1-x0)/Math.sqrt((x1 - x0)*(x1-x0)+(y1-y0)*(y1-y0)));
			facing.y = (float) ((y1-y0)/Math.sqrt((x1 - x0)*(x1-x0)+(y1-y0)*(y1-y0)));
			moveTimer = Math.max(moveTimer-d, 0);
			if(moveTimer==0 || position.x>=width-shipSize || position.x<=0 || position.y>=height-gridCoords.y-shipSize
					|| position.y<=gridCoords.y) changeDir = true;
		}

		if(shootTimer==0) {
			shootTimer = MathUtils.random(5, 10);
			keyboard.shoot(target.random());
		}
		shootTimer=Math.max(shootTimer-d,  0);
		}
		}
		super.draw(batch);
	}
}
