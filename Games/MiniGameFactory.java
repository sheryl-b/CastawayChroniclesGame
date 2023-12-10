package Games;
import java.util.Objects;
import java.util.Random;

import Grids.GridGenerator;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class MiniGameFactory {

    public static String userGuess;
    public static Boolean ret;

    public MiniGameFactory() {
        userGuess = "";
        Random random = new Random();
        int randomGeo = random.nextInt(3);
        int randomLuck = random.nextInt(4);
        int randomMath = random.nextInt(3);
        int randomWord = random.nextInt(2);

        GridGenerator.gridView.setUpMiniGame();


        if (Objects.equals(GridGenerator.p.currentIsland.island_type, "GEO")) {
            switch (randomGeo) {
                case 0:
                    MiniGeoA a = new MiniGeoA();
                    a.playGame();
                    break;
                case 1:
                    MiniGeoB b = new MiniGeoB();
                    b.playGame();
                    break;
                case 2:
                    MiniGeoC c = new MiniGeoC();
                    c.playGame();
                    break;
            }
        } else if (Objects.equals(GridGenerator.p.currentIsland.island_type, "MATH")) {
            switch (randomMath) {
                case 0:
                    MiniMathA a = new MiniMathA();
                    a.playGame();
                    break;
                case 1:
                    MiniMathB b = new MiniMathB();
                    b.playGame();
                    break;
                case 2:
                    MiniMathC c = new MiniMathC();
                    c.playGame();
                    break;
            }
        } else if (Objects.equals(GridGenerator.p.currentIsland.island_type, "LUCK")) {
            switch (randomLuck) {
                case 0:
                    MiniLuckA a = new MiniLuckA();
                    a.playGame();
                    break;
                case 1:
                    MiniLuckB b = new MiniLuckB();
                    b.playGame();
                    break;
                case 2:
                    MiniLuckC c = new MiniLuckC();
                    c.playGame();
                    break;
                case 3:
                    MiniLuckD d = new MiniLuckD();
                    d.playGame();
                    break;
            }
        } else {
            switch (randomWord) {
                case 0:
                    MiniWordA a = new MiniWordA();
                    a.playGame();
                    break;
                case 1:
                    MiniWordB b = new MiniWordB();
                    b.playGame();
                    break;
            }
        }

        PauseTransition p = new PauseTransition(Duration.seconds(5));
        p.play();
        p.setOnFinished(event -> {
            GridGenerator.gridView.minigamePane.toBack();
        });


    }
}
