package oleg.kilimnik.spacemultiplier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class StarsRenderer extends ShapeRenderer {

	private Array<Vector2> smallStars = new Array<Vector2>();
	public Array<Vector2> middleStars = new Array<Vector2>();
	public Array<Vector2> bigStars = new Array<Vector2>();
	private int density = Gdx.graphics.getHeight()*3;
	
	public StarsRenderer() {
		 
		for(int i=0;i<=density;i++) {
			smallStars.add(new Vector2(MathUtils.random(0, Gdx.graphics.getWidth()), MathUtils.random(0, (int)Gdx.graphics.getHeight())));
		}
		for(int i=0;i<=density/5;i++) {
			middleStars.add(new Vector2(MathUtils.random(0, Gdx.graphics.getWidth()), MathUtils.random(0, (int)Gdx.graphics.getHeight())));
		}
		for(int i=0;i<=density/20;i++) {
			bigStars.add(new Vector2(MathUtils.random(0, Gdx.graphics.getWidth()), MathUtils.random(0, (int)Gdx.graphics.getHeight())));
		}
	}
	
	public void draw() {

		this.setColor(Color.WHITE);
		
		//small stars
		this.begin(ShapeType.Point);
		for(Vector2 pos: smallStars) {
			this.point(pos.x, pos.y, 0);
		}
		this.end();

		//middle stars
		this.begin(ShapeType.Filled);
		for(Vector2 pos: middleStars) {
			this.circle(pos.x, pos.y, 1f);
		}
		this.end();
		
		//big stars
		this.begin(ShapeType.Filled);
		for(Vector2 pos: bigStars) {
			this.circle(pos.x, pos.y, 2f);
		}
		this.end();
		
	}

}
