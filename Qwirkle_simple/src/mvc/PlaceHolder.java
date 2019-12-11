package mvc;

public class PlaceHolder {

    final private int row;
    final private int column;

    private Tile tile;

    public PlaceHolder(int row, int col) {
        this.row = row;
        this.column = col;

        // start out with a blank tile
        tile = new Tile();
        placeTile(tile);

    }

    public void placeTile(Tile newTile) {
        // update reference to tile in this place
        this.tile = newTile;
    }

    public Tile getTile() {
        return tile;
    }

    public boolean isEmpty() {
        return tile.isBlank();
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}
