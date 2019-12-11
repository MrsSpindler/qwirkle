package game;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Qwirkle extends BorderPane {

    private Board board;
    private Bag bag;

    private Player[] players;
    private int activePlayer = 0;

    private VBox root;

    public static boolean ONE_PLAYER_MODE;

    public Qwirkle() {
        super();
        
        prepareNewGame();
        
        root = new VBox();
        root.setAlignment(Pos.CENTER);
        
        setCenter(root);
    }

    public void showWelcomeScreen() {

        //TODO: do I need these strings?
        final String P1 = "1 player ", P2 = "2 players", P3 = "3 players", P4 = "4 players";

        VBox welcomeArea = new VBox(20);
        welcomeArea.setAlignment(Pos.CENTER);
        
        HBox titleArea = new HBox(20);
        titleArea.setAlignment(Pos.CENTER);

        Text title = new Text("Welcome to Qwirkle!");
        title.setFont(Font.font(20));
        titleArea.getChildren().add(title);
        
        titleArea.getChildren().add(new ImageView(Game.class.getResource("/images/Qwirkle_game.jpg").toString()));
        
        welcomeArea.getChildren().add(titleArea);
        
        Text instructions = new Text("Create rows and columns by dragging tiles with the same symbol or colour.\n\n"
                + "Click the button to signal the end of your turn.\n\n"
                + "If you wish to swap out your tiles, click the tiles you wish to replace and click the button.\nYour turn will end and the tiles will be replaced.\n\n"
                + "How many players?");
        instructions.setFont(Font.font(20));
        welcomeArea.getChildren().add(instructions);
        
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
    
    private void prepareNewGame() {
        bag = new Bag();
        board = new Board();
    }

    private void beginGame(int numPlayers) {

        //remove the welcome screen to prepare for the board placement
        root.getChildren().clear();

        ONE_PLAYER_MODE = (numPlayers == 1) ? true : false;

        
        HBox controls = new HBox();
        controls.setAlignment(Pos.CENTER);
        controls.setSpacing(15);

        controls.getChildren().add(bag);

        String buttonText = ONE_PLAYER_MODE ? "End Turn" : "Next Player";
        Button btnSwitchPlayer = new Button(buttonText);

        btnSwitchPlayer.setOnAction(event -> changeTurn());

        controls.getChildren().add(btnSwitchPlayer);

        root.getChildren().add(controls);

        HBox playerArea = new HBox();
        playerArea.setAlignment(Pos.CENTER);
        playerArea.setSpacing(5);
        
        //Create array of requested number of players
        players = new Player[numPlayers];

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(bag, "P" + (i + 1));
            playerArea.getChildren().add(players[i]);
        }

        root.getChildren().add(playerArea);

        root.getChildren().add(board);

        //Start the game!!!
        players[0].beginTurn();

    }

    private void changeTurn() {
        // end the turn of the current player
        players[activePlayer].finishTurn();

        // increment and restart at 0 if needed
        activePlayer++;
        activePlayer %= players.length;

        // begin the next players turn
        players[activePlayer].beginTurn();
    }

}
