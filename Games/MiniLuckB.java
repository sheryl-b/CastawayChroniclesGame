package Games;
import Trolls.Troll;
import java.util.Random;
import java.util.Scanner;

import Grids.GridGenerator;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

/**
 * Class MiniLuckB
 *  */
public class MiniLuckB implements Troll {

    /**
     * Print Marble Jar instructions for the user
     */
    public void giveInstructions()
    {
        GridGenerator.gridView.minigameLabel.setText("Can you find the marbles?\n " + "There are 3 JARS, you will have 3 chances to pick a jar in which you think the marbles are in!\n " + "If you win, you earn 10 coins, else you lose 5 coins.");
    }

    /**
     * Play the Marble Jar game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame() {
        MiniGameFactory.userGuess = "";
        giveInstructions();

        int numberOfJars = 3;

        for (int chance = 1; chance <= 3; chance++) {
            int correctJar = (int) (Math.random() * numberOfJars) + 1; // Shuffle the jars for each chance

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Luck minigame2");
            dialog.setContentText("");
            dialog.setHeaderText("Chance " + chance + ": Choose a jar (1, 2, or 3): ");
            String result = dialog.showAndWait().orElse("");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("");


            if (Integer.parseInt(result) < 1 || Integer.parseInt(result) > numberOfJars) {
                alert.setHeaderText("Invalid choice. You lose.");
            } else {
                if (Integer.parseInt(result) == correctJar) {
                    alert.setHeaderText("Congratulations! You found the marbles in jar " + correctJar + "!");
                    GridGenerator.p.coins += 10;
                    MiniGameFactory.ret = true;
                } else {
                    alert.setHeaderText("Oops! No marbles in jar " + result + ".");
                    MiniGameFactory.ret = false;
                }
            }
            alert.showAndWait();
        }
        GridGenerator.p.isPlaying = false;
        GridGenerator.p.coins -= 5;
        return MiniGameFactory.ret;
    }

        /**
         * Main method, use for debugging
         *
         * @param args: Input arguments
         */
        public static void main (String[]args) throws InterruptedException {
            MiniLuckB new_game = new MiniLuckB();
            boolean ans = new_game.playGame();
        }
    }

