package com.karenspindler.qwirkle;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Qwirkle extends View {

    Board board;
    Bag bag;

    Player[] players;
    int activePlayer = 0;

    VBox root;

    public static boolean ONE_PLAYER_MODE;

    public Qwirkle() {
        super();
        
        newGame();
        
        root = new VBox();
        root.setAlignment(Pos.CENTER);
        
        setCenter(root);
        
        welcomeScreen();
    }

    public void welcomeScreen() {

        final String P1 = "1 player ", P2 = "2 players", P3 = "3 players", P4 = "4 players";

        VBox welcomeArea = new VBox(20);
        welcomeArea.setAlignment(Pos.CENTER);
        
        welcomeArea.getChildren().add(new ImageView(QwirkleApplication.class.getResource("/images/Qwirkle_instructions.jpg").toString()));

        HBox buttonArea = new HBox (20);
        buttonArea.setAlignment(Pos.CENTER);
        
        Button btn1Player = new Button(P1);
        btn1Player.setOnAction(event -> beginGame(1));
        buttonArea.getChildren().add(btn1Player);

        Button btn2Player = new Button(P2);
        btn2Player.setOnAction(event -> beginGame(2));
        buttonArea.getChildren().add(btn2Player);

        Button btn3Player = new Button(P3);
        btn3Player.setOnAction(event -> beginGame(3));
        buttonArea.getChildren().add(btn3Player);

        Button btn4Player = new Button(P4);
        btn4Player.setOnAction(event -> beginGame(4));
        buttonArea.getChildren().add(btn4Player);

        welcomeArea.getChildren().add(buttonArea);
        
        root.getChildren().add(welcomeArea);        
    }
    
    private void newGame() {
        bag = new Bag();
        board = new Board();
    }

    private void beginGame(int numPlayers) {

        root.getChildren().clear();

        ONE_PLAYER_MODE = (numPlayers == 1) ? true : false;

        HBox controls = new HBox();
        controls.setAlignment(Pos.CENTER);
        controls.setSpacing(15);

        controls.getChildren().add(bag);

        String buttonText = ONE_PLAYER_MODE ? "End Turn" : "Next Player";
        Button btnSwitchPlayer = new Button(buttonText);

        btnSwitchPlayer.setOnAction(event -> {

            // end the turn of the current player
            players[activePlayer].finishTurn();

            // increment and restart at 0 if needed
            activePlayer++;
            activePlayer %= numPlayers;

            // begin the next players turn
            players[activePlayer].beginTurn();
        });

        controls.getChildren().add(btnSwitchPlayer);

        root.getChildren().add(controls);

        HBox playerArea = new HBox();
        playerArea.setAlignment(Pos.CENTER);
        playerArea.setSpacing(5);

        players = new Player[numPlayers];

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(bag, "P" + (i + 1));
            playerArea.getChildren().add(players[i]);
        }

        root.getChildren().add(playerArea);

        root.getChildren().add(board);

        players[0].beginTurn();

    }
    
    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setTitleText("Qwirkle");
        appBar.setVisible(false);
    }

}
