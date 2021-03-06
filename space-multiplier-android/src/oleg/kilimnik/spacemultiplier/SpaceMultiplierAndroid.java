package oleg.kilimnik.spacemultiplier;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class SpaceMultiplierAndroid extends AndroidApplication {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useWakelock = true;
        config.useAccelerometer = false;
        config.useCompass = false; 
        config.hideStatusBar = false;
        initialize(new SpaceMultiplier(), config);
    }
}
