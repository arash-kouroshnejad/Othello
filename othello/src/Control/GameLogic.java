package Control;

import Model.Board;
import Model.Piece;
import Model.Player;
import Model.Result;
import Persistence.Config;
import View.GameFrame;
import View.GamePanel;
import View.ScoreBoard;

import java.awt.*;
import java.util.ArrayList;

public class GameLogic implements Logic{
    private Board board;
    private Player player1;
    private Player player2;
    private GameFrame frame;
    private boolean initialized;
    private GamePanel gamePanel;
    private ScoreBoard scoreBoard;

    public void newGame() {
        Board.addBoard(new Board(0));
        board = Board.getBoard(0);
        Config config = Config.getInstance();
        String playerName = config.getProperty("Player1Name", String.class);
        Color c = config.getColor("Player1Color");
        player1 = new Player(c, playerName);
        playerName = config.getProperty("Player2Name", String.class);
        c = config.getColor("Player2Color");
        player2 = new Player(c, playerName);
        board.setCurrentPlayer(player1);
        setUp();
    }
    @Override
    public void handle(int x, int y) {
        int row = x / board.getBlockWidth();
        int col = y / board.getBlockHeight();
        if (isVacant(row, col) && isValid(row, col)) {
            board.addPiece(row, col, board.getCurrentPlayer().getColor());
            board.setCurrentPlayer((board.getCurrentPlayer() == player1 ? player2 : player1));
            if (!gameEnded()) {
                terminateGame();
            }
        }
    }
    private void terminateGame() {
        board.setFinished(true);
        int[] scores = calcScore();
        Result result;
        if (scores[0] > scores[1]) {
            result = Result.Player1;
        } else if (scores[0] < scores[1]) {
            result = Result.Player2;
        } else {
            result = Result.Tie;
        }
        board.setResult(result);
        frame.showDialogue(result);
        reset();
    }
    private void reset() {
        Board.addBoard(new Board(0));
        newGame();
    }
    private void setUp() {
        if (!initialized) {
            frame = new GameFrame();
            scoreBoard = new ScoreBoard();
            gamePanel = new GamePanel(this);
            frame.init(gamePanel, scoreBoard);
            initialized = true;
        }
        scoreBoard.init(Board.getBoard(0));
        gamePanel.init(Board.getBoard(0));
    }
    private int[] calcScore() {
        Piece[][] allPieces = board.getAllPieces();
        int[] scores = new int[2];
        for (Piece[] row : allPieces) {
            for (Piece p : row) {
                if (p != null) {
                    if (p.getColour().equals(player1.getColor())) {
                        scores[0]++;
                    } else {
                        scores[1]++;
                    }
                }
            }
        }
        return scores;
    }
    public boolean gameEnded() {
        Player player = board.getCurrentPlayer();
        for (int i = 0; i < board.getRowCount(); i++)
            for (int j = 0; j < board.getColumnCount(); j++) {
                Piece[][] allPieces = board.getAllPieces();
                if (allPieces[i][j] != null) {
                    continue;
                }
                int[] dx = {1, 1, -1, -1, 0, 0, 1, -1};
                int[] dy = {-1, 1, 1, -1, 1, -1, 0, 0};
                boolean doesFlip = false;
                mainLoop:
                for (int k = 0; k < dx.length; k++) {
                    ArrayList<Piece> flipRow = new ArrayList<>();
                    for (int l = 1; l <= Math.max(board.getRowCount(), board.getColumnCount()); l++) {
                        int nx = i + l * dx[k], ny = j + l * dy[k];
                        if (nx >= board.getRowCount() || nx < 0 || ny >= board.getColumnCount() || ny < 0 || allPieces[nx][ny] == null)
                            continue mainLoop;
                        if (allPieces[nx][ny].getColour().equals(player.getColor()))
                            break;
                        flipRow.add(allPieces[nx][ny]);
                    }
                    doesFlip = !flipRow.isEmpty();
                }
                if (doesFlip)
                    return false;
            }
        return true;
    }
    private boolean isVacant(int row, int col) {
        return board.getAllPieces()[row][col] == null;
    }
    private boolean isValid(int row, int col) {
        Piece[][] allPieces = board.getAllPieces();
        boolean valid = false;
        int[] dx = {1, 1, -1, -1, 0, 0, 1, -1};
        int[] dy = {-1, 1, 1, -1, 1, -1, 0, 0};
        mainLoop:
        for (int k = 0; k < dx.length; k++) {
            ArrayList<Piece> flipRow = new ArrayList<>();
            for (int i = 1; i <= Math.max(board.getRowCount(), board.getColumnCount()); i++) {
                int nx = row + i * dx[k], ny = col + i * dy[k];
                if (nx >= board.getColumnCount() || nx < 0 || ny >= board.getRowCount() || ny < 0 || allPieces[nx][ny] == null)
                    continue mainLoop;
                if (allPieces[nx][ny].getColour().equals(board.getCurrentPlayer().getColor()))
                    break;
                flipRow.add(allPieces[nx][ny]);
            }
            for (Piece piece : flipRow) {
                valid = true;
                piece.setColour(board.getCurrentPlayer().getColor());
            }
        }
        return valid;
    }
}
