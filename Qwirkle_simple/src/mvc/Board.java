package mvc;

public class Board {

    private static final int MAX_LINE = 6;

    // directions for getting tiles in all 4 directions
    private static final char ABOVE = 'A';
    private static final char BELOW = 'B';
    private static final char LEFT = 'L';
    private static final char RIGHT = 'R';

    private PlaceHolder[][] squares;
    
    private int numColumns;
    private int numRows;

    public Board(int _numColumns, int _numRows) {
        
        numColumns = _numColumns;
        numRows = _numRows;
        
        // create the grid of squares
        squares = new PlaceHolder[numRows][numColumns];

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                squares[row][col] = new PlaceHolder(row, col);
            }
        }
    }

    public boolean placeTile(Tile tile, int row, int col) {
        if (isValidPlacement(tile, squares[row][col])) {
            squares[row][col].placeTile(tile);
            return true;
        }
        
        return false;
    }

    public int determinePointValue(Tile tileInPlay, PlaceHolder tileHolder) {
        int score = 1;
        // get the location on the board of the tileholder that is being considered
        int row = tileHolder.getRow();
        int column = tileHolder.getColumn();

        // get need all surrounding tiles
        Tile[] tilesAbove = getLine(row, column, ABOVE);
        Tile[] tilesBelow = getLine(row, column, BELOW);
        Tile[] tilesLeft = getLine(row, column, LEFT);
        Tile[] tilesRight = getLine(row, column, RIGHT);

        Tile[][] surrounding = { tilesAbove, tilesBelow, tilesLeft, tilesRight };

        // see how many tiles are already in the rows that are attached.
        for (int i = 0; i < surrounding.length; i++) {
            for (int j = 0; j < surrounding[i].length; j++) {
                if (surrounding[i][j] != null) score += 1;
                else // stop counting once a null tile is found
                    break;
            }
        }

        int quirkleCount = 0;

        // check for a Qwirkle
        for (int i = 0; i < surrounding.length; i++) {
            for (int j = 0; j < surrounding[i].length; j++) {
                if (surrounding[i][j] != null) quirkleCount++;
                else // stop counting once a null tile is found
                    break;
            }

            if (quirkleCount == 5) score += 6;

            quirkleCount = 0;
        }

        return score;
    }

    private boolean isValidPlacement(Tile tileToBePlaced, PlaceHolder tileHolder) {

        // get the location on the board of the tileholder that is being considered
        int row = tileHolder.getRow();
        int column = tileHolder.getColumn();

        // get all surrounding tiles
        Tile[] tilesAbove = getLine(row, column, ABOVE);
        Tile[] tilesBelow = getLine(row, column, BELOW);
        Tile[] tilesLeft = getLine(row, column, LEFT);
        Tile[] tilesRight = getLine(row, column, RIGHT);

        // tiles in a line must have common feature, no duplicates
        boolean validPlacement = checkPlacement(tileToBePlaced, tilesAbove, tilesBelow, tilesLeft, tilesRight);

        return tileHolder.isEmpty() && validPlacement;
    }

    private Tile[] getLine(int row, int column, char direction) {
        // Returns the line formed above, below, left or right of the indicated
        // position.
        // Blank tiles are returned as null objects.
        Tile[] line = new Tile[MAX_LINE];

        for (int i = 0; i < line.length; i++) {
            Tile temp = null;
            switch (direction) {
            case ABOVE:
                temp = getTile(row - 1 - i, column);
                break;
            case BELOW:
                temp = getTile(row + 1 + i, column);
                break;
            case LEFT:
                temp = getTile(row, column - 1 - i);
                break;
            case RIGHT:
                temp = getTile(row, column + 1 + i);
                break;
            }
            if (temp != null && !temp.isBlank()) // do not include blank tiles
                line[i] = temp;
            else // stop at the first blank space
                break;
        }
        return line;
    }
    
    private Tile getTile(int row, int column) {
        // check that request is within the correct range
        if (row >= 0 && row < numRows && column >= 0 && column < numColumns) return squares[row][column].getTile();

        // if request is outside the grid, return a null object
        return null;
    }

    private boolean checkPlacement(Tile tileToBePlaced, Tile[] up, Tile[] down, Tile[] left, Tile[] right) {

        // check immediate neighbours first
        // connecting tiles must match in either colour or shape
        boolean neighboursMatch = checkImmediateNeighbours(tileToBePlaced, up[0], down[0], left[0], right[0]);

        boolean linesMatch = checkLine(tileToBePlaced, up) && checkLine(tileToBePlaced, down)
                && checkLine(tileToBePlaced, left) && checkLine(tileToBePlaced, right);

        // make sure there are no duplicates in the line
        boolean noDoubles = checkForDuplicates(tileToBePlaced, up) && checkForDuplicates(tileToBePlaced, down)
                && checkForDuplicates(tileToBePlaced, left) && checkForDuplicates(tileToBePlaced, right);

        // prevent tile being placed between two compatible lines that should not be
        // joined (would create a duplicate
        if (up[0] != null && down[0] != null) noDoubles = noDoubles && canJoinLines(up, down);
        if (left[0] != null && right[0] != null) noDoubles = noDoubles && canJoinLines(left, right);

        return neighboursMatch && linesMatch && noDoubles;
    }

    private boolean checkImmediateNeighbours(Tile tileToBePlaced, Tile up, Tile down, Tile left, Tile right) {

        // need to ensure that at least 1 neighbour is compatible, no neighbours
        // incompatible
        // Check at least 1 is compatible. only check for non-empty (not null) tiles
        boolean hasCompatible = false;
        if (up != null) hasCompatible = hasCompatible || tileToBePlaced.matchesNeighbour(up);
        if (down != null) hasCompatible = hasCompatible || tileToBePlaced.matchesNeighbour(down);
        if (left != null) hasCompatible = hasCompatible || tileToBePlaced.matchesNeighbour(left);
        if (right != null) hasCompatible = hasCompatible || tileToBePlaced.matchesNeighbour(right);

        // check no neighbours incompatible - null is defined as compatible
        boolean allMatch = tileToBePlaced.matchesNeighbour(up) && tileToBePlaced.matchesNeighbour(down)
                && tileToBePlaced.matchesNeighbour(left) && tileToBePlaced.matchesNeighbour(right);

        // prevent tile being placed between two compatible tiles that could not make a
        // line
        if (up != null && down != null) allMatch = allMatch && up.matchesNeighbour(down);
        if (left != null && right != null) allMatch = allMatch && left.matchesNeighbour(right);

        return hasCompatible && allMatch;
    }

    private boolean checkLine(Tile tile, Tile[] line) {

        // default to true to prevent false negatives; checking immediate neighbours
        // have already happened.
        boolean matchesFeature = true;

        // check to see if we have a line
        int count = 0;

        // need minimum 2 tiles to make a line
        for (int i = 0; i < line.length; i++) {
            if (line[i] != null) count++;
            else break; // stop counting
        }

        if (count > 1) { // check that there are at least 2 tiles in a row
            char feature = line[0].getColour() == line[1].getColour() ? line[0].getColour() : line[0].getShape();

            // needs to match at least 1 of colour or shape
            matchesFeature = matchesFeature && (tile.getColour() == feature || tile.getShape() == feature);
        }

        return matchesFeature;
    }

    private boolean checkForDuplicates(Tile tileToBePlaced, Tile[] line) {
        for (int i = 0; i < line.length; i++) {
            if (tileToBePlaced.equals(line[i])) {
                // if one duplicate is found, not a valid placement
                return false;
            }
        }

        // no duplicates found, valid placement
        return true;
    }

    private boolean canJoinLines(Tile[] line1, Tile[] line2) {
        // make sure that each line doesn't already contain a duplicate
        for (int i = 0; i < line1.length; i++) {
            for (int j = 0; j < line2.length; j++) {
                // only check for duplicates if tile isn't null
                if (line1[i] != null && line2[j] != null && line1[i].equals(line2[j])) return false; // found a match,
                                                                                                     // no can join
            }
        }
        return true;
    }
}
