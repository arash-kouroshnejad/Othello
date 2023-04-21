package Model;

import java.awt.*;

public class Player {
    final Color color;
    final String Name;

    public Player(Color color, String name) {
        this.color = color;
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public Color getColor() {
        return color;
    }
}
