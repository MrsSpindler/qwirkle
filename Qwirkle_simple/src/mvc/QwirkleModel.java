package mvc;

import java.util.ArrayList;
import java.util.List;

public class QwirkleModel {

    private Board board;
    private Bag bag;

    private List<Player> players;
    private int activePlayer = 0;

    private boolean onePlayerMode;

    public QwirkleModel(int numPlayers, int col, int row) {

        bag = new Bag();

        board = new Board(col, row);
        
        onePlayerMode = (numPlayers == 1) ? true : false;

        // Create array of requested number of players
        players = new ArrayList<Player>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player(bag, "P" + (i + 1)));
        }

    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void beginGame(int numPlayers) {



        // Start the game!!!
        players.get(0).beginTurn();

    }

    public void changeTurn() {
        // end the turn of the current player
        players.get(activePlayer).finishTurn();

        // increment and restart at 0 if needed
        activePlayer++;
        activePlayer %= players.size();

        // begin the next players turn
        players.get(activePlayer).beginTurn();
    }

    public int getTilesRemaining() {
        return bag.getTilesRemaining();
    }

    public boolean isOnePlayerMode() {
        return onePlayerMode;
    }


}
