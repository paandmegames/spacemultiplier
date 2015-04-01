package oleg.kilimnik.spacemultiplier.keyboard;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ShootButtonP2 extends ImageButton {
	
	private float width, height;
	private Touchable touchable = Touchable.enabled;

	public ShootButtonP2(Drawable imageUp, Drawable imageDown, int width, int height) {
		super(imageUp, imageDown);
		this.width = width;
		this.height = height;
	}
	
	@Override
	public Actor hit (float x, float y, boolean touchable) {
		if (touchable && this.touchable != Touchable.enabled) return null;
		return x >= 0 && x < width-16 && y >= 0 && y < height ? this : null;
	}

}
