package mvc;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class QwirkleApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        QwirkleModel model = new QwirkleModel(4, 32, 16);
        QwirkleView view = new QwirkleView(model);
        //QwirkleController controller = new QwirkleController(view, model);

        Scene scene = new Scene(view.asParent());

        primaryStage.setTitle("Qwirkle");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
