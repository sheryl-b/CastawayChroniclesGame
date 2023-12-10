package Games;
import Grids.Grid;
import Trolls.Troll;
import java.util.Random;
import java.util.Scanner;

import Grids.GridGenerator;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

/**
 * Class MiniLuckC
 *  */
public class MiniLuckC implements Troll {

    /**
     * Print Coin Flip instructions for the user
     */
    public void giveInstructions()
    {
        GridGenerator.gridView.minigameLabel.setText("Time to flip a coin!\n " + "You will have 3 attempts to guess what side the coin will land on.\n " + "If you win, you earn 10 coins, else you lose 5 coins.");
    }

    /**
     * Play the Coin Flip game
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
        dialog.setHeaderText("Guess the coin flip (Heads or Tails): ");
        alert.setContentText("Congratulations! You guessed correctly. You win!");
        alert.setTitle("WIN");
        alert2.setContentText("Sorry, Incorrect Guess.");
        alert2.setTitle("LOSE");
        String result;
        for (int i = 1; i <= 3; i++) {
            int randomNumber = randomCoin.nextInt(2);
            result = dialog.showAndWait().orElse("");
            if ((result.equalsIgnoreCase("heads") && randomNumber == 0) ||
                    (result.equalsIgnoreCase("tails") && randomNumber == 1)) {
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
        MiniLuckC new_game = new MiniLuckC();
        boolean ans = new_game.playGame();
    }
}

