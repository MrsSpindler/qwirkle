package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Qwirkle game = new Qwirkle();
        
        Scene scene = new Scene(game);

        primaryStage.setTitle("Qwirkle");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        
        game.showWelcomeScreen();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
