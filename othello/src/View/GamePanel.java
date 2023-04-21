package View;

import Persistence.Config;
import Control.Logic;
import Model.Board;
import Model.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private Board board;
    private final Color lineColor;
    private int width;
    private int height;
    private final int hLineX;
    private final int vLineY;
    public GamePanel(Logic l) {
        Config config = Config.getInstance();
        lineColor = config.getColor("LineColor");
        Color backgroundColor = config.getColor("BackgroundColor");
        hLineX = config.getProperty("HLineX", Integer.class);
        vLineY = config.getProperty("VLineY", Integer.class);
        setBackground(backgroundColor);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                l.handle(e.getX(), e.getY());
                getParent().repaint();
                getParent().revalidate();
            }
        });
    }

    public void init(Board b) {
        this.board = b;
        width = b.getWidth();
        height = b.getHeight();
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(lineColor);
        for (int i = 1; i < board.getRowCount(); i++) {
            // horizontal grid lines
            g.drawLine(hLineX, i * board.getBlockHeight(), width, i * board.getBlockHeight());
            // vertical grid lines
            g.drawLine(i * board.getBlockWidth(), vLineY, i * board.getBlockWidth(), height);
        }
        for (Piece[] row : board.getAllPieces()) {
            for (Piece p : row) {
                if (p != null) {
                    g.setColor(p.getColour());
                    g.fillOval(p.getX(), p.getY(), board.getPieceDimension(), board.getPieceDimension());
                }
            }
        }
    }
}
