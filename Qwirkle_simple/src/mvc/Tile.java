package mvc;

public class Tile {

    // public constants
    public static final int NUM_FEATURES = 6;
    public static final int TILE_SIZE = 40;
    
    public static final char RED = 'R', ORANGE = 'O', YELLOW = 'Y', GREEN = 'G', BLUE = 'B', VIOLET = 'V';
    public static final char CIRCLE = 'C', DIAMOND = 'D', HEART = 'H', PENTAGON = 'P', STAR = 'S', TRIANGLE = 'T';

    // private constants
    private static final char BLANK = 'X';

    // fields
    private char colour;
    private char shape;

    public Tile() {
        //default tile is blank
        this(BLANK, BLANK);
    }

    public Tile(char colour, char shape) {
        this.colour = colour;
        this.shape = shape;
    }

    public char getColour() {
        return colour;
    }

    public char getShape() {
        return shape;
    }

    public String getImageName() {
        return Tile.class.getResource("/images/" + colour + shape + ".png").toString();
    }

    public boolean isBlank() {
        return colour == BLANK && shape == BLANK;
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
