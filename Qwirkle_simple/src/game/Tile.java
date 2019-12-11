package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends ImageView {

    public static final int NUM_FEATURES = 6;

    static final char RED = 'R', ORANGE = 'O', YELLOW = 'Y', GREEN = 'G', BLUE = 'B', VIOLET = 'V';
    static final char CIRCLE = 'C', DIAMOND = 'D', HEART = 'H', PENTAGON = 'P', STAR = 'S', TRIANGLE = 'T';

    static final int BLANK = 'X';

    static final int TILE_SIZE = 39;

    char colour;
    char shape;

    public Tile() {
        this.colour = BLANK;
        this.shape = BLANK;

        setFitHeight(TILE_SIZE);
        setFitWidth(TILE_SIZE);

        setImage(new Image(getImageName()));
    }

    public Tile(char colour, char shape) {
        this();
        this.colour = colour;
        this.shape = shape;

        setImage(new Image(getImageName()));
    }

    public char getColour() {
        return colour;
    }

    public char getShape() {
        return shape;
    }

    public void hideTile() {
        setImage(null);
    }

    public void showTile() {
        setImage(new Image(getImageName()));

    }

    private String getImageName() {
        return Game.class.getResource("/images/" + colour + shape + ".png").toString();
    }

    public boolean isBlank() {
        return colour == BLANK & shape == BLANK;
    }

    public boolean matchesNeighbour(Tile neighbour) {
        if (neighbour == null) return true;

        return neighbour.getShape() == shape ^ neighbour.getColour() == colour;
    }

    public boolean equals(Tile t) {
        if (t == null) return false;

        return t.getShape() == shape && t.getColour() == colour;

    }

}
