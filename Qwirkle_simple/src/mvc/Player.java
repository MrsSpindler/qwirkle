package mvc;

import java.util.ArrayList;
import java.util.List;

public class Player  {

    public static final int NUM_TILES = 6;

    private Bag bag;
    private String name;
    private int score = 0;
    
    private List<Tile> tilesToSwap;
    private List<Tile> tilesToReplace;
    private List<Tile> tilesInPlay;

    public Player(Bag _bag, String _name) {

        tilesToSwap = new ArrayList<Tile>();
        tilesToReplace = new ArrayList<Tile>();
        tilesInPlay = new ArrayList<Tile>();
        
        bag = _bag;
        name = _name;

        for (int i = 0; i < NUM_TILES; i++) {
            Tile tile = bag.getNextTile();

            // add user tiles to the parent node
            tilesInPlay.add(tile);
        }
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public List<Tile> getTilesInPlay() {
        return tilesInPlay;
    }

    public void beginTurn() {
        // make a new list for each turn
        tilesToSwap.clear();
        tilesToReplace.clear();
    }

    public void playTile(Tile tile) {
        // get position of the tile being played
        int pos = tilesInPlay.indexOf(tile);

        // remove it from the players's area
        tilesInPlay.remove(tile);

        // replace the tile with blank
        Tile blankTile = new Tile();
        tilesToReplace.add(blankTile);
        
        //update the tiles being displayed
        tilesInPlay.add(pos, blankTile);
    }

    public void awardPoints(int points) {
        score += points;
    }

    public void finishTurn() {

        if (tilesToReplace.isEmpty()) {
            // no blank tiles - no tiles played this turn
            // check for swapping tiles
            if (!tilesToSwap.isEmpty()) {
                // get a list of new tiles
                Tile[] newTiles = new Tile[tilesToSwap.size()];
                for (int i = 0; i < tilesToSwap.size(); i++) {
                    newTiles[i] = bag.getNextTile();
                }

                for (int i = 0; i < tilesToSwap.size(); i++) {
                    if (!newTiles[i].isBlank()) // check that the new tile has a value
                        swapTile(tilesToSwap.get(i), newTiles[i]);
                }
            }
        } else {
            for (Tile tile : tilesToReplace) {
                replaceTile(tile);
            }
        }

    }

    private void replaceTile(Tile blank) {
        // get position of the tile being played
        int pos = tilesInPlay.indexOf(blank);
        // remove it from the players's area
        tilesInPlay.remove(blank);

        // pick a new tile from the bag
        Tile newTile = bag.getNextTile();

        // place it in the same spot
        tilesInPlay.add(pos, newTile);
    }

    private void swapTile(Tile oldTile, Tile newTile) {

        // get current position of the tile
        int pos = tilesInPlay.indexOf(oldTile);
        // remove it from the players's area
        tilesInPlay.remove(oldTile);

        // return the tile to the bag
        bag.returnTile(oldTile);

        // put the new tile in the player's area
        tilesInPlay.add(pos, newTile);

        // deduct points if in single player mode
        //if (Qwirkle.ONE_PLAYER_MODE) awardPoints(-1);

    }


}
