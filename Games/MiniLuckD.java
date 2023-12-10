package Games;
import Trolls.Troll;
import java.util.Random;
import java.util.Scanner;

import Grids.GridGenerator;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

/**
 * Class GameTroll.
 * Course code tailored by the CSC207 instructional
 * team at UTM, with special thanks to:
 *
 * @author anshag01
 * @author mustafassami
 * @author guninkakr03
 *  */
public class MiniLuckD implements Troll {

    /**
     * Print GameTroll instructions for the user
     */
    public void giveInstructions() {
        GridGenerator.gridView.minigameLabel.setText("Are you lucky enough to guess the number!?\n " + "You will have 3 attempts to guess what the number may be.");
    }

    /**
     * Play the GameTroll game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame() {
        giveInstructions();
        MiniGameFactory.userGuess = "";


        Random randomCoin = new Random();
        TextInputDialog dialog = new TextInputDialog();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Luck minigame3");
        dialog.setContentText("");
        dialog.setHeaderText("Guess the number 1-10: ");
        alert.setContentText("Congratulations! You guessed correctly. You win!");
        alert.setTitle("WIN");
        alert2.setContentText("Sorry, Incorrect Guess.");
        alert2.setTitle("LOSE");
        String result;
        for (int i = 1; i <= 3; i++) {
            int randomNumber = randomCoin.nextInt(10);
            result = dialog.showAndWait().orElse("");
            if (Integer.parseInt(result) == randomNumber) {
                alert.showAndWait();
                GridGenerator.p.coins += 10;
                MiniGameFactory.ret = true;
                GridGenerator.p.isPlaying = false;
                return MiniGameFactory.ret;
            } else {
                alert2.showAndWait();
            }
        }
        GridGenerator.p.coins -= 5;
        GridGenerator.p.isPlaying = false;
        return false;
    }


    /**
     * Main method, use for debugging
     *
     * @param args: Input arguments
     */
    public static void main (String[]args) throws InterruptedException {
        MiniLuckD new_game = new MiniLuckD();
        boolean ans = new_game.playGame();
    }
}
