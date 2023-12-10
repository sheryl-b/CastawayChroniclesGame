package Games;
import Trolls.Troll;
import java.util.Random;
import java.util.Scanner;

import Grids.GridGenerator;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

/**
 * Class MiniLuckA
 *  */
public class MiniLuckA implements Troll {

    /**
     * Print Luck of Sums instructions for the user
     */
    public void giveInstructions()
    {
        GridGenerator.gridView.minigameLabel.setText("How lucky are your sums!?" + "\n " + "You will get a number from 10-36. " + "\n " + "You must ENTER A NUMBER between 1-36 then" + "\n " + "ROLL A DICE TWICE to see if the numbers given by the" + "\n " + "dice sum up to the number I've provided!" + "\n " + "If you win, you earn 5 coins, else you lose 5 coins.");
    }

    /**
     * Play the Luck of Sums game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame() {
        MiniGameFactory.userGuess = "";
        giveInstructions();

        Random randomDice = new Random();
        Random randomSum = new Random();

        int randomNumber = randomSum.nextInt(36 - 10 + 1) + 10;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Luck minigame1");
        dialog.setContentText("Your numbers must sum to: " + randomNumber);
        dialog.setHeaderText("Enter a number between 1 and 36: ");
        String result = dialog.showAndWait().orElse("");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("");

        if (Integer.parseInt(result) >= 1 && Integer.parseInt(result) <= 36) {
            //if valid num

            int diceSum = 0;
            int roll = randomDice.nextInt(6) + 1;
            diceSum += roll;
            int roll1 = randomDice.nextInt(6) + 1;
            diceSum += roll1;

            if (diceSum == randomNumber) {
                alert.setTitle("VICTORY!");
                alert.setContentText("Your dice rolled: " + roll + " and " + roll1 + ". \n" + "Congratulations! You've won the LUCK OF SUMS MINI GAME!");
                alert.showAndWait();
                GridGenerator.p.coins += 5;
                MiniGameFactory.ret = true;
            } else {
                alert.setTitle("LOSS");
                alert.setContentText("Your dice rolled: " + roll + " and " + roll1 + ". \n" + "Sorry, you lost.");
                alert.showAndWait();
                GridGenerator.p.coins -= 5;
                MiniGameFactory.ret = false;
            }
        }
        else{
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setContentText("Invalid input. You lose.");
            alert2.setTitle("LOSS");
            alert2.showAndWait();
            MiniGameFactory.ret = false;
            GridGenerator.p.coins -= 5;
        }
        GridGenerator.p.isPlaying = false;
        return MiniGameFactory.ret;
    }

    /**
     * Main method, use for debugging
     *
     * @param args: Input arguments
     */
    public static void main(String [] args) throws InterruptedException {
        MiniLuckA new_game = new MiniLuckA();
        boolean ans = new_game.playGame();
    }
}
