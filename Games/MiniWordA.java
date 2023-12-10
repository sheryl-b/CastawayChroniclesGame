package Games;
import Grids.GridGenerator;
import Trolls.Troll;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * Class MiniWordA
 *  */
public class MiniWordA implements Troll {

    /**
     * Print Word Scramble instructions for the user
     */
    public void giveInstructions()
    {
        GridGenerator.gridView.minigameLabel.setText("Unscramble this word!\n " + "You will have 1 attempt to answer the question.\n " + "If you win, you earn 10 coins, else you lose 10 coins.");
    }


    /**
     * Play the GameTroll game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame() {
        giveInstructions();
        MiniGameFactory.userGuess = "";

        List<String> wordList = new ArrayList<>();
        wordList.add("computer");
        wordList.add("software");
        wordList.add("water");
        wordList.add("butterfly");
        wordList.add("apple");
        wordList.add("where");
        wordList.add("music");
        wordList.add("alphabet");
        wordList.add("game");
        wordList.add("wrapper");
        wordList.add("chocolate");
        wordList.add("science");
        wordList.add("experiment");
        wordList.add("acronym");
        wordList.add("walnut");
        wordList.add("pencil");
        wordList.add("friend");
        wordList.add("scissors");
        wordList.add("lollipop");

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("WORDGame1");
        dialog.setContentText("Unscramble the word:");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("");
        String result;
        Random random = new Random();

        String originalWord = wordList.get(random.nextInt(wordList.size()));
        String scrambledWord = scrambleWord(originalWord);

        dialog.setHeaderText("Scrambled word: " + scrambledWord);

        result = dialog.showAndWait().orElse("");
        if (result.equalsIgnoreCase(originalWord)) {
            alert.setTitle("CORRECT");
            alert.setContentText("You got the correct word!");
            alert.setHeaderText("Correct!");
            alert.showAndWait();
            GridGenerator.p.coins += 10;
            GridGenerator.p.isPlaying = false;
            return true;
        } else {
            alert.setTitle("INCORRECT");
            alert.setContentText("You didn't get the correct word!");
            alert.setHeaderText("Incorrect!");
            alert.showAndWait();
            GridGenerator.p.coins -= 10;
            GridGenerator.p.isPlaying = false;
            return false;
        }
    }

    private static String scrambleWord(String word) {
        char[] characters = word.toCharArray();
        Random random = new Random();

        for (int i = characters.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = characters[index];
            characters[index] = characters[i];
            characters[i] = temp;
        }
        return new String(characters);
    }

    /**
     * Main method, use for debugging
     *
     * @param args: Input arguments
     */
    public static void main (String[]args) throws InterruptedException {
        MiniWordA new_game = new MiniWordA();
        boolean ans = new_game.playGame();
    }
}
