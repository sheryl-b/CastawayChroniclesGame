package Games;
import Grids.GridGenerator;
import Trolls.Troll;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

/**
 * Class MiniWordB
 *  */
public class MiniWordB implements Troll {

    /**
     * Print MiniGame instructions for the user
     */
    public void giveInstructions()
    {
        GridGenerator.gridView.minigameLabel.setText("Definitions of words!\n " + "You will have 1 attempt to answer the question.\n " + "If you win, you earn 10 coins, else you lose 10 coins.");
    }
    /**
     * Play the Definition of words game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame() {
        giveInstructions();
        MiniGameFactory.userGuess = "";

        Map<String, String> wordsMap = new HashMap<>();
        wordsMap.put("a celestial body that orbits around a star", "planet");
        wordsMap.put("a unit of time equal to 60 seconds", "minute");
        wordsMap.put("a unit of time equal to 60 minutes", "hour");
        wordsMap.put("a large body of water surrounded by land", "lake");
        wordsMap.put("something that interests you because it is important", "concern");
        wordsMap.put("deficient in quantity or number compared with the demand", "scarce");
        wordsMap.put("an abstract or general idea inferred from specific instances", "concept");
        wordsMap.put("an elaborate and systematic plan of action", "scheme");
        wordsMap.put("a system of government by the whole population", "democracy");
        wordsMap.put("a substance that conducts electricity", "conductor");
        wordsMap.put("a process of converting food into energy", "metabolism");

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("WORDGame2");
        dialog.setContentText("Your answer:");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        String result;
        Random random = new Random();

        Object[] general = wordsMap.keySet().toArray();
        Object meaning = general[random.nextInt(general.length)];
        String correctAns = wordsMap.get(meaning);

        dialog.setHeaderText("Guess the word that means : " + meaning);
        result = dialog.showAndWait().orElse("");

        if (result.equalsIgnoreCase(correctAns)) {
            alert.setTitle("CORRECT");
            alert.setHeaderText("Correct!");
            alert.setContentText("You got the correct answer!");
            alert.showAndWait();
            GridGenerator.p.isPlaying = false;
            GridGenerator.p.coins += 10;
            return true;
        } else {
            alert.setTitle("INCORRECT");
            alert.setHeaderText("Incorrect!");
            alert.setContentText("You didn't get the correct answer!");
            alert.showAndWait();
            GridGenerator.p.isPlaying = false;
            GridGenerator.p.coins -= 10;
            return false;
        }
    }

    /**
     * Main method, use for debugging
     *
     * @param args: Input arguments
     */
    public static void main (String[]args) throws InterruptedException {
        MiniWordB new_game = new MiniWordB();
        boolean ans = new_game.playGame();
    }
}
