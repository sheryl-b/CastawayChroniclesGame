package Games;
import Trolls.Troll;

import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import Grids.GridGenerator;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

/**
 * Class MiniGeoC
 *  */
public class MiniGeoC implements Troll {

    /**
     * Print World Geography instructions for the user
     */
    public void giveInstructions()
    {
        GridGenerator.gridView.minigameLabel.setText("Time to test your knowledge on World Geography!" + "\n " + "You will have 1 attempt to answer the question." + "\n " + "If you win, you earn 10 coins, else you lose 10 coins.");
    }

    /**
     * Play the GameTroll game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame() {
        MiniGameFactory.userGuess = "";
        giveInstructions();

        Map<String, String> worldMap = new HashMap<>();
        worldMap.put("Canada", "Ottawa");
        worldMap.put("United States", "Washington DC.");
        worldMap.put("United Kingdom", "London");
        worldMap.put("France", "Paris");
        worldMap.put("Germany", "Berlin");
        worldMap.put("India", "New Delhi");
        worldMap.put("China", "Beijing");
        worldMap.put("Japan", "Tokyo");
        worldMap.put("Australia", "Canberra");
        worldMap.put("Brazil", "Brasilia");
        worldMap.put("Russia", "Moscow");
        worldMap.put("South Africa", "Pretoria");
        worldMap.put("Egypt", "Cairo");
        worldMap.put("Mexico", "Mexico City");
        worldMap.put("Argentina", "Buenos Aires");
        worldMap.put("Italy", "Rome");
        worldMap.put("Spain", "Madrid");
        worldMap.put("Turkey", "Ankara");
        worldMap.put("South Korea", "Seoul");

        Random random = new Random();

        Object[] general = worldMap.keySet().toArray();
        Object country = general[random.nextInt(general.length)];
        String correctAns = worldMap.get(country);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Guess the country capital");
        dialog.setContentText("Guess the capital of the country (do not add apostrophes or periods): " + country);
        dialog.setHeaderText("Your answer: ");
        String result = dialog.showAndWait().orElse("");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("");

        if (result.equalsIgnoreCase(correctAns)) {
            alert.setTitle("Correct!");
            alert.setContentText("Correct!");
            GridGenerator.p.coins += 10;
            MiniGameFactory.ret = true;
        } else {
            alert.setTitle("Incorrect!");
            alert.setContentText("Incorrect!");            GridGenerator.p.coins -= 10;
            MiniGameFactory.ret = false;
        }
        alert.setHeaderText("The capital of " + country + " is " + correctAns + ".");
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
        MiniGeoC new_game = new MiniGeoC();
        boolean ans = new_game.playGame();
    }
}
