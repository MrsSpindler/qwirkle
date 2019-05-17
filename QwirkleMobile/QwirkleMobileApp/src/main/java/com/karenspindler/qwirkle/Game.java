package com.karenspindler.qwirkle;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.mvc.SplashView;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends MobileApplication {

    @Override
    public void init() {
        
        addViewFactory(MobileApplication.SPLASH_VIEW, () -> {
            return new SplashView(HOME_VIEW, 
                         new ImageView(Game.class.getResource("/images/Qwirkle_splash.jpg").toString()), 
                         Duration.seconds(5));
        });
        
        addViewFactory(HOME_VIEW, Qwirkle::new);
        
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.PURPLE.assignTo(scene);

        ((Stage) scene.getWindow()).getIcons().add(new Image(Game.class.getResourceAsStream("/images/icon.jpg")));
        ((Stage) scene.getWindow()).setMaximized(true);

        
    }
}
