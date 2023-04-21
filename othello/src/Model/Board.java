package Model;

import Persistence.Config;

import java.awt.*;
import java.util.HashMap;

public class Board {
    private static final HashMap<Integer, Board> allBoards = new HashMap<>();
    public static Board getBoard(int ID) {
        return allBoards.get(ID);
    }
    public static void addBoard(Board b) {
        allBoards.put(b.ID, b);
    }
    private final int ID;
    private final Piece[][] allPieces;
    private final int rowCount;
    private final int columnCount;
    private final int width;
    private final int height;
    private final int pieceRadius;
    private final int blockWidth;
    private final int blockHeight;
    private Player currentPlayer;
    private boolean finished;
    private Result result;

    public Board(int ID) { // modulate constructor
        this.ID = ID;
        Config config = Config.getInstance();
        rowCount = config.getProperty("RowCount", Integer.class);
        columnCount = config.getProperty("ColumnCount", Integer.class);
        allPieces = new Piece[rowCount][columnCount];
        width = config.getProperty("WindowWidth", Integer.class);
        height = config.getProperty("BoardHeight", Integer.class);
        pieceRadius = config.getProperty("PieceRadius", Integer.class);
        blockWidth = width / columnCount;
        blockHeight = height / rowCount;
        String[] initPieces = config.getProperty("InitialLayout", String.class).split(",");
        for (String str : initPieces) {
            int row = config.getProperty(str + "Row", Integer.class);
            int col = config.getProperty(str + "Column", Integer.class);
            Color c = config.getColor(str + "Color");
            addPiece(row, col, c);
        }
    }

    public void addPiece(int row, int col, Color c) {
        Piece p = new Piece((int) ((row + 0.5) * blockWidth - pieceRadius),
                (int) ((col + 0.5) * blockHeight - pieceRadius), c);
        allPieces[row][col] = p;
    }

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }

    public int getPieceDimension() {
        return 2 * pieceRadius;
    }

    public int getBlockWidth() {
        return blockWidth;
    }

    public int getBlockHeight() {
        return blockHeight;
    }

    public int getID() {
        return ID;
    }

    public Piece[][] getAllPieces() {
        return allPieces;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
