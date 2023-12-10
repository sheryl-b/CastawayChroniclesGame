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
 * Class MiniGeoB
 *  */
public class MiniGeoB implements Troll {

    /**
     * Print GameTroll instructions for the user
     */
    public void giveInstructions()
    {
        GridGenerator.gridView.minigameLabel.setText("Time to test your knowledge on general Geography!"+"\n You will have 1 attempt to answer the question."+ "\n If you win, you earn 10 coins, else you lose 10 coins.");
    }

    /**
     * Play the General geography game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame() {
        giveInstructions();

        Map<String, String> generalMap = new HashMap<>();
        generalMap.put("longest river in Canada? ", "mackenzie");
        generalMap.put("smallest country in the world by land area? ", "vatican");
        generalMap.put("highest mountain in North America? ", "denali");
        generalMap.put("largest desert in the world? ", "antarctica");
        generalMap.put("largest ocean in the world by land area? ", "pacific");
        generalMap.put("largest island in the world? ", "greenland");
        generalMap.put("African country known as the Pearl of Africa? ", "uganda");
        generalMap.put("great lake entirely in the USA? ", "superior");
        generalMap.put("country where the ancient city of Petra is? ", "jordan");
        generalMap.put("country known as the Land of the Midnight Sun? ", "norway");


        Random random = new Random();

        Object[] general = generalMap.keySet().toArray();
        Object question = general[random.nextInt(general.length)];
        String correctAns = generalMap.get(question);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("What is the ?");
        dialog.setContentText("Your answer (One word only): ");
        dialog.setHeaderText("What is the " + question + "?");
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
                alert.setContentText("Incorrect!");
                GridGenerator.p.coins -= 10;
                MiniGameFactory.ret = false;
            }
        alert.setHeaderText("The correct answer" + " is " + correctAns + ".");
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
        MiniGeoB new_game = new MiniGeoB();
        boolean ans = new_game.playGame();
    }
}
