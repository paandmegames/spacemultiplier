package oleg.kilimnik.spacemultiplier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Grid extends ShapeRenderer {
	private Vector2 gridCoords = Constants.gridCoords;
	private float cellSize = Constants.cellSize;
	private int length = Constants.VIRTUAL_WIDTH;
	

	
	public Grid() {

	}
	
	public void draw() {
		
		//Grid
		this.begin(ShapeType.Line);
		this.setColor(Color.BLUE);
		for(int i=1;i<9;i++) {	
			this.line(gridCoords.x+cellSize*i, gridCoords.y, 
					gridCoords.x+cellSize*i, gridCoords.y+length);
		}
		for(int j=0;j<10;j++) {
			this.line(gridCoords.x, gridCoords.y+cellSize*j, 
					gridCoords.x+length, gridCoords.y+cellSize*j);
		}
		this.end();
		
	}

}
