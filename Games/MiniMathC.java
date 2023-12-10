package Games;
import Grids.Grid;
import Grids.GridGenerator;
import Trolls.Troll;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.Random;
import java.util.Scanner;

/**
 * Class MiniMathC.
 *  */
public class MiniMathC implements Troll {

    /**
     * Print Mini Game instructions for the user
     */
    public void giveInstructions()
    {
        GridGenerator.gridView.minigameLabel.setText("Here is a AREA OR PERIMETER Mini Game!\n " + "If you win, you earn 15 coins, else you lose 10 coins.");
    }

    /**
     * Play the Area or perimeter game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame() {
        giveInstructions();
        MiniGameFactory.userGuess = "";

        Random randomShape = new Random();
        int shape = randomShape.nextInt(3);
        boolean ans;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("");

        if (shape == 1) {
            ans = askRectangle();
        }
        else if (shape == 2) {
            ans = askTrapezoid();
        }
        else {
           ans = askTriangle();
        }

        if (ans) {
            alert.setTitle("CORRECT");
            alert.setHeaderText("CORRECT");
            alert.setContentText("That is the correct value!");
            alert.showAndWait();
            GridGenerator.p.isPlaying = false;
            GridGenerator.p.coins += 15;
        }
        else {
            alert.setTitle("INCORRECT");
            alert.setHeaderText("INCORRECT");
            alert.setContentText("That is the incorrect value!");
            alert.showAndWait();
            GridGenerator.p.isPlaying = false;
            GridGenerator.p.coins -= 10;
        }

        return ans;
    }

    public boolean askRectangle () {

        Random randomNum = new Random();
        int length = randomNum.nextInt(30);
        int width = randomNum.nextInt(30);

        int question = randomNum.nextInt(2);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Math minigame3");
        dialog.setContentText("Given the length as " + length + " and width as " + width + ", enter the AREA of this RECTANGLE" );
        dialog.setHeaderText("Enter area number: ");

        Integer result = Integer.parseInt(dialog.showAndWait().orElse(""));
        double area = length * width;
        return area == result;
    }

    public boolean askTriangle () {

        Random randomNum = new Random();
        int length = randomNum.nextInt(30);
        int width = randomNum.nextInt(30);

        int question = randomNum.nextInt(2);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Math minigame3");
        dialog.setContentText("Given the length as " + length + " and width as " + width + ", enter the AREA of this TRIANGLE" );
        dialog.setHeaderText("Enter area number: ");

        Integer result = Integer.parseInt(dialog.showAndWait().orElse(""));
        double area = length * width/2;
        return area == result;
    }

    public boolean askTrapezoid () {
        Random randomNum = new Random();
        Scanner scanner = new Scanner(System.in);

        int base1 = randomNum.nextInt(25);
        int base2 = randomNum.nextInt(25);
        int height = randomNum.nextInt(25);

        int question = randomNum.nextInt(2);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Math minigame3");
        dialog.setContentText("Given the BASE1 as " + base1 + " and width as " + base2 + ", and height as " + height + " enter the AREA of this TRAPEZOID" );
        dialog.setHeaderText("Enter area number: ");
        Integer result = Integer.parseInt(dialog.showAndWait().orElse(""));
        double area = 0.5 * (base1 + base2) * height;
        return area == result;
    }

    /**
     * Main method
     *
     * @param args: Input arguments
     */
    public static void main (String[]args) throws InterruptedException {
        MiniMathC s = new MiniMathC();
        boolean a = s.playGame();
    }
}
