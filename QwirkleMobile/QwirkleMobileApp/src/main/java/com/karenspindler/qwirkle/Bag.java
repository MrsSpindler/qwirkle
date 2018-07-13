package com.karenspindler.qwirkle;

import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.DisplayService;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Bag extends Text {

    public static int MAX_TILES;
    public static final char[] SHAPES = { Tile.CIRCLE, Tile.HEART, Tile.DIAMOND, Tile.STAR, Tile.TRIANGLE,
            Tile.PENTAGON };
    public static final char[] COLOURS = { Tile.RED, Tile.GREEN, Tile.ORANGE, Tile.VIOLET, Tile.BLUE, Tile.YELLOW };

    private int[][] tileCount;
    private int tilesRemaining;

    public Bag() {
        //3 of each tile for a phone; 6 for a tablet or desktop
        Services.get(DisplayService.class).ifPresent(service -> {
            MAX_TILES = service.isPhone() ? 3 : 6;
        });
        
        tileCount = new int[Tile.NUM_FEATURES][Tile.NUM_FEATURES];
        tilesRemaining = Tile.NUM_FEATURES * Tile.NUM_FEATURES * MAX_TILES;

        setFont(Font.font(15));
        setText("Tiles Remaining: " + tilesRemaining);
    }

    public Tile getNextTile() {

        int colour;
        int shape;
        Tile tile;

        if (tilesRemaining <= 0) {
            tile = new Tile();
        } else {
            // pick a random colour and shape for the tile
            do {
                colour = (int) (Math.random() * Tile.NUM_FEATURES);
                shape = (int) (Math.random() * Tile.NUM_FEATURES);
                // continue checking if we have passed the limit
            } while (tileCount[colour][shape] >= MAX_TILES);

            // increment the counter for that particular tile
            tileCount[colour][shape]++;

            // track number of tiles left
            tilesRemaining--;
            setText("Tiles Remaining: " + tilesRemaining);

            tile = new Tile(COLOURS[colour], SHAPES[shape]);
        }
        return tile;
    }

    public void returnTile(Tile incoming) {

        // can't exchange if there are no tiles left in the bag
        if (!incoming.isBlank()) {
            // add old tile back in
            tileCount[getIndex(incoming.getColour())][getIndex(incoming.getShape())]--;
            tilesRemaining++;
            setText("Tiles Remaining: " + tilesRemaining);
        }

    }

    private int getIndex(char feature) {
        int index = -1;

        for (int i = 0; i < COLOURS.length; i++)
            if (COLOURS[i] == feature) index = i;

        for (int i = 0; i < SHAPES.length; i++)
            if (SHAPES[i] == feature) index = i;

        return index;

    }
}
