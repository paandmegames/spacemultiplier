package oleg.kilimnik.spacemultiplier.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;

public class Star extends ParticleEffect {
	public Star(Vector2 pos) {
		this.load(Gdx.files.internal("data/particles/stars.p"), Gdx.files.internal("data/particles"));
		this.setPosition(pos.x, pos.y);
		if(!this.isComplete()) this.reset();
		this.start();
	}
	
}
