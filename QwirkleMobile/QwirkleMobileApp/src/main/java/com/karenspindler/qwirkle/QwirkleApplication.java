package com.karenspindler.qwirkle;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class QwirkleApplication extends MobileApplication {

    @Override
    public void init() {
        addViewFactory(HOME_VIEW, Qwirkle::new);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.PURPLE.assignTo(scene);

        ((Stage) scene.getWindow()).getIcons().add(new Image(QwirkleApplication.class.getResourceAsStream("/images/icon.jpg")));

    }
}
