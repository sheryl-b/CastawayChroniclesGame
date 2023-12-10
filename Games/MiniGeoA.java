package Games;
import Grids.Grid;
import Trolls.Troll;

import java.awt.event.KeyEvent;
import java.util.*;
import java.util.concurrent.CountDownLatch;

import Grids.GridGenerator;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

/**
 * Class MiniGeoA.
 *  */
public class MiniGeoA implements Troll {

    /**
     * Print MiniGeoA instructions for the user
     */
    public void giveInstructions() {
        GridGenerator.gridView.minigameLabel.setText("Time to test your Canadian knowledge!" + "\n" + "You will have 1 attempt to answer the question. \n If you win, you earn 10 coins, else you lose 10 coins.");
    }

    /**
     * Play the Canadian Geography game.
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame() {
        MiniGameFactory.userGuess = "";
        giveInstructions();

        Map<String, String> canadaMap = new HashMap<>();
        canadaMap.put("Alberta", "Edmonton");
        canadaMap.put("British Columbia", "Victoria");
        canadaMap.put("Manitoba", "Winnipeg");
        canadaMap.put("New Brunswick", "Fredericton");
        canadaMap.put("Newfoundland and Labrador", "St Johns");
        canadaMap.put("Nova Scotia", "Halifax");
        canadaMap.put("Ontario", "Toronto");
        canadaMap.put("Prince Edward Island", "Charlottetown");
        canadaMap.put("Quebec", "Quebec City");
        canadaMap.put("Saskatchewan", "Regina");


        Random random = new Random();
        Object[] provinces = canadaMap.keySet().toArray();
        Object randomProvince = provinces[random.nextInt(provinces.length)];
        String correctCapital = canadaMap.get(randomProvince);
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Guess the Capital");
        dialog.setHeaderText("Guess the capital of " + randomProvince + ":");
        dialog.setContentText("Capital:");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("The capital of " + randomProvince + " is " + correctCapital + ".");
        alert.setContentText("");

        String result = dialog.showAndWait().orElse("");
        if (result.equalsIgnoreCase(correctCapital)) {
            alert.setTitle("Correct!");
            alert.setContentText("Correct");
            GridGenerator.p.coins += 10;
            MiniGameFactory.ret = true;
        } else {
            alert.setTitle("Incorrect!");
            alert.setContentText("Incorrect!");
            GridGenerator.p.coins -= 10;
            MiniGameFactory.ret = false;
        }
        alert.setHeaderText("The capital of " + randomProvince + " is " + correctCapital + ".");
        alert.showAndWait();
        GridGenerator.p.isPlaying = false;
        return MiniGameFactory.ret;
    }

    /**
     * Main method, use for debugging
     *
     * @param args: Input arguments
     */
    public static void main (String[]args) throws InterruptedException {
        MiniGeoA new_game = new MiniGeoA();
        //boolean ans = new_game.playGame();
    }
}
