import Persistence.Config;
import Control.GameLogic;

public class Driver {
    public static void main(String[] args) {
        // loading configuration
        if (args.length > 0) {
            System.out.println("Config Path Set To " + args[0]);
            Config.getInstance().setPath(args[0]);
        } else {
            System.out.println("Config Path Set To Default (./src/.config.properties)");
            Config.getInstance().setPath("./src/.config.properties");
        }
        // loading game
        GameLogic logic = new GameLogic();
        logic.newGame();
    }
}
