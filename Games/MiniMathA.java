package Games;
import Trolls.Troll;
import java.util.Random;
import java.util.Scanner;

import Grids.GridGenerator;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

/**
 * Class MiniMathA.
 *  */
public class MiniMathA implements Troll {

    /**
     * Print Mini Game instructions for the user
     */
    public void giveInstructions()
    {
        GridGenerator.gridView.minigameLabel.setText("Here is an ADDITION Mini Game!\n " + "There will be 5 questions. You have 1 try for each question.\n " + "If you win, you earn 10 coins, else you lose 10 coins.");
    }

    /**
     * Play the Addition game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame() {
        giveInstructions();
        MiniGameFactory.userGuess = "";



        Random randomNum = new Random();

        int num = 50;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("MathGame1");
        dialog.setContentText("Number:");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("");
        Integer result;

        for (int i = 0; i < 5; i++) {
            int basic1 = randomNum.nextInt(num);
            int basic2 = randomNum.nextInt(num);
            int basic_answer = basic1 + basic2;
            dialog.setHeaderText("Question " + (i + 1) + ": What is " + basic1 + " + " + basic2 + "?");
            result = Integer.parseInt(dialog.showAndWait().orElse(""));
            if (result == basic_answer) {
                alert.setTitle("Correct!");
                alert.setHeaderText("Correct!");
                alert.setContentText("You guessed the number!");
                alert.showAndWait();
            } else {
                alert.setTitle("Correct!");
                alert.setHeaderText("Incorrect!");
                alert.setContentText("Incorrect. The correct answer is: " + basic_answer);
                alert.showAndWait();
                GridGenerator.p.coins -= 10;
                GridGenerator.p.isPlaying = false;
                return false;
            }
        }
        GridGenerator.p.isPlaying = false;
        GridGenerator.p.coins += 10;
        return true;
    }
    /**
     * Main method
     *
     * @param args: Input arguments
     */
    public static void main (String[]args) throws InterruptedException {
        MiniMathA s = new MiniMathA();
        boolean a = s.playGame();
    }
}
