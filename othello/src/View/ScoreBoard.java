package View;

import Persistence.Config;
import Model.Board;

import javax.swing.*;
import java.awt.*;

public class ScoreBoard extends JPanel {
    private Board board;
    private String FontName;
    private int FontStyle;
    private int FontSize;

    private int X;

    private int Y;
    public void init(Board b) {
        board = b;
        int width = b.getWidth();
        Config config = Config.getInstance();
        Color bgColor = config.getColor("BackgroundColor");
        setBackground(bgColor);
        int totalHeight = config.getProperty("WindowHeight", Integer.class);
        int height = board.getHeight();
        setBounds(new Rectangle(0, height, board.getWidth(), totalHeight));
        int hAlign = config.getProperty("HorizontalTextAlign", Integer.class);
        int vAlign = config.getProperty("VerticalTextAlign", Integer.class);
        X = (int) (width * ((double) hAlign / 100));
        Y = (int) ((totalHeight - height) * ((double) vAlign / 100));
        FontName = config.getProperty("ScoreBoardFontName" , String.class);
        FontStyle = config.getProperty("ScoreBoardFontStyle", Integer.class);
        FontSize = config.getProperty("ScoreBoardFontSize", Integer.class);
    }
    public void paintComponent(Graphics g) {
        g.setColor(board.getCurrentPlayer().getColor());
        g.setFont(new Font(FontName, FontStyle, FontSize));
        if (board.isFinished()) {
            g.drawString(board.getResult().toString(), X, Y);
        } else {
            g.drawString(board.getCurrentPlayer().getName(), X, Y);
        }
    }
}
