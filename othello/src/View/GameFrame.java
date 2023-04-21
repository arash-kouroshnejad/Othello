package View;


import Model.Result;
import Persistence.Config;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        Config config = Config.getInstance();
        Class<Integer> Int = Integer.class;
        int windowX = config.getProperty("WindowX", Int);
        int windowY = config.getProperty("WindowY", Int);
        int width = config.getProperty("WindowWidth", Int);
        int totalHeight = config.getProperty("WindowHeight", Int);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setBounds(windowX, windowY, width, totalHeight);
        setVisible(true);
        setResizable(false);
    }

    public void showDialogue(Result result) {
        JOptionPane.showMessageDialog(this, result.toString());
    }

    public void init(GamePanel panel, ScoreBoard scoreBoard) {
        JPanel panel1 = new JPanel();
        setContentPane(panel1);
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.add(panel);
        panel1.add(scoreBoard);
    }
}
