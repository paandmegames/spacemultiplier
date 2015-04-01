package oleg.kilimnik.spacemultiplier.particles;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class Laser extends ParticleEffect {

	private float density = 0.5f;
	public int type;
	public Vector2 endPoint = new Vector2(); 
	
	public Laser(boolean isStealthy, int type, Vector2 playerPos, Vector2 enemyPos, Vector2 facing) {
		this.type = type;
		this.endPoint = enemyPos;
		
		if(type==0) {
			this.load(Gdx.files.internal("data/particles/laserP1.p"), Gdx.files.internal("data/particles"));
		} else {
			this.load(Gdx.files.internal("data/particles/laserP2.p"), Gdx.files.internal("data/particles"));
		}
		this.setPosition(playerPos.x+32+32*facing.x, playerPos.y+32+32*facing.y);
		
		if(!isStealthy) {
			this.findEmitter("Ray").setMinParticleCount((int)(density*Math.sqrt((enemyPos.x - (playerPos.x+32+32*facing.x))
				*(enemyPos.x- (playerPos.x+32+32*facing.x))+(enemyPos.y - (playerPos.y+32+32*facing.y))*(enemyPos.y- (playerPos.y+32+32*facing.y)))));
			this.findEmitter("Ray").setMaxParticleCount((int)(density*Math.sqrt((enemyPos.x- (playerPos.x+32+32*facing.x))
				*(enemyPos.x- (playerPos.x+32+32*facing.x))+(enemyPos.y- (playerPos.y+32+32*facing.y))*(enemyPos.y- (playerPos.y+32+32*facing.y)))));
			this.findEmitter("Ray").getAngle().setHigh((float)MathUtils.atan2((enemyPos.y - (playerPos.y+32+32*facing.y)),
				(enemyPos.x- (playerPos.x+32+32*facing.x)))*MathUtils.radiansToDegrees);
			this.findEmitter("Ray").getAngle().setLow((float)MathUtils.atan2((enemyPos.y- (playerPos.y+32+32*facing.y)),
				(enemyPos.x - (playerPos.x+32+32*facing.x)))*MathUtils.radiansToDegrees);
			this.findEmitter("Ray").getVelocity().setHighMax((float)Math.sqrt((enemyPos.x - (playerPos.x+32+32*facing.x))
				*(enemyPos.x - (playerPos.x+32+32*facing.x))+(enemyPos.y - (playerPos.y+32+32*facing.y))*(enemyPos.y- (playerPos.y+32+32*facing.y)))*6);	
		} else {
			this.findEmitter("Ray").setMaxParticleCount(0);
		}
		this.findEmitter("Explode").setPosition(enemyPos.x, enemyPos.y);	
		
		if(!this.isComplete()) this.reset();
		this.start();
		
		
	}	

	
}
