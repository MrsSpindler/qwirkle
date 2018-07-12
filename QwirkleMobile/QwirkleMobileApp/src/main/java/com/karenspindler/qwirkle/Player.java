package com.karenspindler.qwirkle;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Player extends TilePane {

    public static final int NUM_TILES = 6;
    private static final int FONT_SIZE = 15;

    Bag bag;

    Text txtScore;
    Text txtName;

    int score = 0;
    ArrayList<Tile> tilesToSwap;
    ArrayList<Tile> tilesToReplace;

    public Player(Bag bag, String name) {

        tilesToSwap = new ArrayList<Tile>();
        tilesToReplace = new ArrayList<Tile>();

        setStyle("-fx-border-color: black");
        setAlignment(Pos.CENTER);
        setPrefWidth((NUM_TILES + 2) * (Tile.TILE_SIZE + Board.GAP));

        this.bag = bag;
        txtName = new Text(name);
        txtName.setFont(Font.font(FONT_SIZE));

        getChildren().add(txtName);

        for (int i = 0; i < NUM_TILES; i++) {
            Tile tile = bag.getNextTile();

            // wait until the turn starts to show the tile
            tile.hideTile();

            // add user tiles to the parent node
            getChildren().add(tile);
        }

        setOnMouseDragReleased(event -> setCursor(null));

        txtScore = new Text("0");
        txtScore.setFont(Font.font(FONT_SIZE));
        getChildren().add(txtScore);
    }

    public void beginTurn() {
        // make a new list for each turn
        tilesToSwap.clear();
        tilesToReplace.clear();

        // Gives tiles their event behaviour
        ObservableList<Node> children = getChildren();

        for (Node node : children) {
            if (node instanceof Tile) {

                // only activate non-blank tiles
                Tile tile = (Tile) node;

                if (!tile.isBlank()) {
                    activateTile((Tile) node);
                    tile.showTile();
                }
            }
        }

        // give a visual cue that the turn is started
        setStyle("-fx-border-color: black; -fx-background-color:lightgreen");

    }

    public void playTile(Tile tile) {
        // get position of the tile being played
        int pos = getChildren().indexOf(tile);

        // remove it from the players's area
        getChildren().remove(tile);

        // replace the tile with blank
        Tile blankTile = new Tile();
        tilesToReplace.add(blankTile);
        getChildren().add(pos, blankTile);

    }

    public void awardPoints(int points) {
        score += points;
        txtScore.setText(String.valueOf(score));
    }

    public void finishTurn() {

        if (tilesToReplace.isEmpty()) {
            // no blank tiles - no DnD occured
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

        // deactivate and hide tiles
        ObservableList<Node> children = getChildren();
        for (Node node : children) {
            if (node instanceof Tile) {
                Tile tile = (Tile) node;
                deactivateTile(tile);
                tile.hideTile();
            }
        }

        // give a visual cue that the turn is started
        setStyle("-fx-border-color: black");

    }

    private void replaceTile(Tile blank) {
        // get position of the tile being played
        int pos = getChildren().indexOf(blank);
        // remove it from the players's area
        getChildren().remove(blank);

        // pick a new tile from the bag
        Tile newTile = bag.getNextTile();

        // place it in the same spot
        getChildren().add(pos, newTile);
    }

    private void swapTile(Tile oldTile, Tile newTile) {

        // get current position of the tile
        int pos = getChildren().indexOf(oldTile);
        // remove it from the players's area
        getChildren().remove(oldTile);

        // return the tile to the bag
        bag.returnTile(oldTile);

        // put the new tile in the player's area
        getChildren().add(pos, newTile);

        // deduct points if in single player mode
        if (Qwirkle.ONE_PLAYER_MODE) awardPoints(-1);

    }

    private void activateTile(Tile tile) {

        tile.setOnDragDetected(event -> {
            tile.startFullDrag();
            tile.setCursor(
                    new ImageCursor(tile.getImage(), tile.getImage().getWidth() / 2, tile.getImage().getHeight() / 2));
        });

        tile.setOnMouseDragReleased(event -> tile.setCursor(null));

        tile.setOnMousePressed(event -> {
            if (tilesToSwap.contains(tile)) {
                tile.setEffect(null);
                tilesToSwap.remove(tile);
            } else {
                tile.setEffect(new Glow(1));
                tilesToSwap.add(tile);
            }
        });

    }

    private void deactivateTile(Tile tile) {

        tile.setOnDragDetected(null);

        tile.setOnMousePressed(null);

        tile.setOnMouseDragReleased(null);

        tile.setEffect(null);

    }

}
