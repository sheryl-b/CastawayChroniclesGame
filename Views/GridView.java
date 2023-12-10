package Views;

import Games.MiniGameFactory;
import Grids.Coordinates;
import Grids.Grid;
import Grids.GridGenerator;
import Pirates.Pirate;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.media.MediaPlayer;


import java.io.File;

public class GridView {
    private Stage stage; // The stage to render the grid on
    public GridPane gridPane = new GridPane(); // The grid pane to position elements on
    public Grid grid; // The grid which this visualization is based on

    private MediaPlayer mediaPlayer; //mediaPlayer for sounds
    private Boolean mediaPlaying;

    public GridPane minigamePane = new GridPane(); //gridPane for minigame implementation
    public Label minigameLabel = new Label();

    public StackPane sp;
    public TilePane tilePane = new TilePane(); // The tilePane that holds all of the individual grid cells
    private Label coinAmount;
    private HBox lives;
    private VBox settings;
    public boolean soundEnabled = true;
    private String viewMode = "REGULAR";

    // Class constructor accepts stage
    public GridView(Stage stage, Grid grid) {
        this.stage = stage;
        this.grid = grid;
        initializeUI();
    }

    public void initializeUI() {

        // Setup Stage
        this.stage.setTitle("Castaway Chronicles");
        this.stage.setMaximized(true);
        this.stage.setResizable(true);

        // Build Grid Pane
        buildGridPane();

        Scene scene = new Scene(gridPane);
        this.stage.setScene(scene);

        // Display window
        this.stage.show();
        articulateSound("start");
        addTextHandlingEvent();

    }


    /**
     * Builds the grid pane
     */
    public void buildGridPane() {
        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        // Define grid pane Colour
        Color gridPaneColor;
        switch(viewMode) {
            case "HIGHCONTRAST" -> gridPaneColor = Color.NAVY;
            case "GRAYSCALE" -> gridPaneColor = Color.DIMGRAY;
            default -> gridPaneColor = Color.DEEPSKYBLUE;
        }

        // Setup Grid pane basics
        gridPane.setBackground(new Background(new BackgroundFill(
                gridPaneColor,
                new CornerRadii(0),
                new Insets(0)
        )));

        // Setup Grid pane grid
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(22.5);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(55);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(22.5);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(20);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(60);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(20);

        // Create rows and columns in grid pane
        gridPane.getColumnConstraints().addAll(column1, column2, column3);
        gridPane.getRowConstraints().addAll(row1, row2, row3);

        // Background for tile pane
        VBox mapBackBox = new VBox();
        mapBackBox.setAlignment(Pos.CENTER);

        Rectangle mapBack = new Rectangle();
        mapBack.setWidth(820);
        mapBack.setHeight(820);
        mapBack.setStroke(Color.DARKSLATEGRAY);
        mapBack.setStrokeWidth(5);
        mapBack.setFill(Color.LIGHTGRAY);

        mapBackBox.getChildren().add(mapBack);
        gridPane.add(mapBackBox, 1, 1);
//---------------- minigame
        // Setup minigame Grid pane basics
        minigamePane.getChildren().clear();
        minigameLabel.setText("");
        minigamePane.setBackground(new Background(new BackgroundFill(
                Color.TRANSPARENT,
                new CornerRadii(0),
                new Insets(0)
        )));

        minigamePane.add(minigameLabel,5,5);

        //------------------------------------------ minigames

        // Set up tile pane for holding the grid cells
        tilePane.setPrefTileWidth(80);
        tilePane.setPrefTileHeight(80);
        tilePane.setPrefRows(10);
        tilePane.setPrefColumns(10);
        tilePane.setMinSize(800, 800);
        tilePane.setMaxSize(800, 800);

        // Add tilePane to gridPane
        gridPane.add(tilePane, 1, 1);

        // Align the tile pane in center of center pane
        GridPane.setHalignment(tilePane, HPos.CENTER);
        GridPane.setValignment(tilePane, VPos.CENTER);

        // Build tile pane with initial grid
        buildTilePane();

        // Draw initial player on grid
        Coordinates playerLoc = GridGenerator.p.getCoordinates();
        clearFogAroundPlayer();
        addToCellVisual(playerLoc.getX(), playerLoc.getY(), "SHIP_1.png");

        // Setup Scene
        gridPane.add(minigamePane, 1, 0);
        minigamePane.toBack();


        // Player Statistics Setup --------------------------------------------------------
        VBox playerStats = new VBox();
        gridPane.add(playerStats, 2, 0);
        playerStats.setAlignment(Pos.CENTER_LEFT);

        // Define text Colour
        Color textColour = Color.BLACK;
        if(!viewMode.equals("REGULAR")) { textColour = Color.WHITE; }

        // Containers for lives and coins
        HBox playerLivesBox = new HBox();
        playerLivesBox.setPrefHeight(70);
        HBox playerCoinsBox = new HBox();
        playerCoinsBox.setPrefHeight(70);
        playerStats.getChildren().addAll(playerLivesBox, playerCoinsBox);

        // Lives text
        Label livesText = new Label();
        livesText.setText("Lives: ");
        livesText.setFont(new Font("Elephant", 30));
        livesText.setTextFill(textColour);

        // Create hearts to display lives
        updateLives();

        // Add lives display components
        playerLivesBox.getChildren().addAll(livesText, lives);

        // Coins text
        Label coinsText = new Label();
        coinsText.setText("Coins: ");
        coinsText.setFont(new Font("Elephant", 30));
        coinsText.setTextFill(textColour);

        // Coin image
        ImageView coinImage = new ImageView(new Image("./Images/" + getViewFolder() + "Coin.png"));
        coinImage.setFitHeight(60);
        coinImage.setFitWidth(70);

        // Actual money amount
        updateCoinAmount();

        // Add coins display components
        playerCoinsBox.getChildren().addAll(coinsText, coinImage, coinAmount);

        // Player Name
        VBox nameBox = new VBox();
        nameBox.setAlignment(Pos.CENTER);
        gridPane.add(nameBox, 0, 0);

        Label playerName = new Label();
        playerName.setText(GridGenerator.p.name);
        playerName.setFont(new Font("Elephant", 35));
        playerName.setTextFill(textColour);
        playerName.setTextAlignment(TextAlignment.CENTER);
        nameBox.getChildren().add(playerName);

        // Visual decorations ------------------------------------------

        // Right side rock
        HBox rightRock = new HBox();
        rightRock.setAlignment(Pos.BOTTOM_LEFT);
        rightRock.setTranslateY(20);
        rightRock.setTranslateX(-50);
        gridPane.add(rightRock, 2, 2);

        ImageView rock1 = new ImageView(new Image("./Images/" + getViewFolder() + "Rock1.png"));
        rock1.setFitWidth(200);
        rock1.setFitHeight(200);
        rightRock.getChildren().add(rock1);

        // Right side palm tree
        HBox rightTree = new HBox();
        rightTree.setAlignment(Pos.BOTTOM_LEFT);
        rightTree.setTranslateY(-80);
        rightTree.setTranslateX(-50);
        gridPane.add(rightTree, 2, 2);

        ImageView palmTree1 = new ImageView(new Image("./Images/" + getViewFolder() + "PalmTree1.png"));
        palmTree1.setFitHeight(600);
        palmTree1.setFitWidth(400);
        rightTree.getChildren().add(palmTree1);

        // Left Side Rock
        HBox leftRock = new HBox();
        leftRock.setAlignment(Pos.BOTTOM_LEFT);
        leftRock.setTranslateY(50);
        leftRock.setTranslateX(-80);
        gridPane.add(leftRock, 1, 2);

        ImageView rock2 = new ImageView(new Image("./Images/" + getViewFolder() + "Rock2.png"));
        rock2.setFitWidth(240);
        rock2.setFitHeight(170);
        leftRock.getChildren().add(rock2);

        // Left Side palm tree
        HBox leftTree = new HBox();
        leftTree.setAlignment(Pos.BOTTOM_CENTER);
        leftTree.setTranslateY(-80);
        leftTree.setTranslateX(-120);
        gridPane.add(leftTree, 0, 2);

        ImageView tree2 = new ImageView(new Image("./Images/" + getViewFolder() + "PalmTree2.png"));
        tree2.setFitWidth(500);
        tree2.setFitHeight(500);
        leftTree.getChildren().add(tree2);

        // Compass
        HBox compassBox = new HBox();
        compassBox.setAlignment(Pos.CENTER);
        compassBox.setTranslateY(-120);
        gridPane.add(compassBox, 0, 1);

        ImageView compass = new ImageView(new Image("./Images/" + getViewFolder() + "Compass.png"));
        compass.setFitHeight(300);
        compass.setFitWidth(300);
        compassBox.getChildren().add(compass);

        // Seagull
        HBox seagullBox = new HBox();
        seagullBox.setAlignment(Pos.CENTER);
        seagullBox.setTranslateY(-100);
        gridPane.add(seagullBox, 2, 1);

        ImageView seagull = new ImageView(new Image("./Images/" + getViewFolder() + "Seagull.png"));
        seagull.setFitWidth(250);
        seagull.setFitHeight(200);
        seagullBox.getChildren().add(seagull);


        // Bottom buttons alignment
        HBox buttonsAlign = new HBox();
        buttonsAlign.setAlignment(Pos.BOTTOM_CENTER);
        buttonsAlign.setTranslateY(-5);
        buttonsAlign.setPrefWidth(600);
        gridPane.add(buttonsAlign, 1, 2);

        // Settings Button
        Button settings = new Button("Settings");
        settings.setFont(new Font("Elephant", 30));
        settings.setPrefSize(250, 50);
        settings.setTranslateX(-20);
        //inputButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        // Draw the settings display
        settings.setOnMouseClicked(mouseEvent -> drawSettings());

        // Quit Button
        Button quit = new Button("Quit Game");
        quit.setFont(new Font("Elephant", 30));
        quit.setPrefSize(250, 50);
        quit.setTranslateX(20);

        // Quit game on mouse click
        quit.setOnMouseClicked(mouseEvent -> System.exit(0));

        buttonsAlign.getChildren().addAll(settings, quit);

    }

    /**
     * Reconstructs the tile pane for the current grid
     */
    public void buildTilePane() {

        // Clear existing tile pane
        tilePane.getChildren().clear();

        // Add tiles that represent the game's gridCells to tilePane
        int tilePaneIndex = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                // Stack pane for stacking elements in tile
                StackPane stackPane = new StackPane();

                // Define gridCellFill Colour
                Color gridCellFillColor;
                Color gridCellStrokeColor;
                Color fogCellFillColor;
                Color fogCellStrokeColor;
                switch(viewMode) {
                    case "HIGHCONTRAST":
                        gridCellFillColor = Color.STEELBLUE;
                        gridCellStrokeColor = Color.WHITE;
                        fogCellFillColor = Color.DIMGRAY;
                        fogCellStrokeColor = Color.WHITE;
                        break;
                    case "GRAYSCALE":
                        gridCellFillColor = Color.GRAY;
                        gridCellStrokeColor = Color.WHITE;
                        fogCellFillColor = Color.BLACK;
                        fogCellStrokeColor = Color.WHITE;
                        break;
                    default:
                        gridCellFillColor = Color.CORNFLOWERBLUE;
                        gridCellStrokeColor = Color.NAVY;
                        fogCellFillColor = Color.DARKGRAY;
                        fogCellStrokeColor = Color.DIMGRAY;
                }

                // Background of cell
                Rectangle gridCellVisual = new Rectangle(0, 0, 80, 80);
                gridCellVisual.setFill(gridCellFillColor);
                gridCellVisual.setStroke(gridCellStrokeColor);

                // Add background of cell to stack pane
                stackPane.getChildren().add(gridCellVisual);

                // Add fog if cell has it
                if(grid.getGridCell(j, i).hasFog()) {
                    Rectangle fogVisual = new Rectangle(0, 0, 80, 80);
                    fogVisual.setFill(fogCellFillColor);
                    fogVisual.setStroke(fogCellStrokeColor);

                    // Add fog to stack pane
                    stackPane.getChildren().add(fogVisual);
                }

                // Add stack pane to tile pane
                tilePane.getChildren().add(tilePaneIndex, stackPane);
                tilePaneIndex++;

                // Check if island should be added to cell
                drawIsland(j, i);

                // Check if rock should be added to cell
                drawRock(j, i);

            }
        }

        // Draw pirates on grid
        drawAllPirates();
    }

    /**
     * Draw all the pirates for this grid. (To be used for initialization)
     */
    private void drawAllPirates() {
        for (Pirate pirate : grid.getPirateManager().getPirates()) {
            addToCellVisual(pirate.getLocation().getX(), pirate.getLocation().getY(), "Pirate.png");
        }
    }

    /**
     * Draws an island on the specified cell (ONLY IF CELL HAS DESIGNATED ISLAND)
     * @param x The x coordinate of the cell
     * @param y The y coordinate of the cell
     */
    private void drawIsland(int x, int y) {
        if(grid.getGridCell(x, y).hasIsland()) {
            addToCellVisual(x, y, "island.png");
        }
    }


    /**
     * Draws a rock on the specified cell (ONLY IF CELL HAS DESIGNATED ROCK)
     * @param x The x coordinate of the cell
     * @param y The y coordinate of the cell
     */
    private void drawRock(int x, int y) {
        if(grid.getGridCell(x, y).hasRock()) {
            addToCellVisual(x, y, "Rock" + grid.getGridCell(x, y).getRockId() + ".png");
        }
    }

    /**
     * Update the coin display
     */
    public void updateCoinAmount() {

        // Define text Colour
        Color textColour = Color.BLACK;
        if(!viewMode.equals("REGULAR")) { textColour = Color.WHITE; }

        // Define coin amount label if not defined
        if(coinAmount == null) {
            coinAmount = new Label();
            coinAmount.setText(" $" + GridGenerator.p.coins);
            coinAmount.setFont(new Font("Elephant", 30));
            coinAmount.setTextFill(textColour);
        }
        else {
            coinAmount.setText("$" + GridGenerator.p.coins);
            coinAmount.setTextFill(textColour);
        }
    }

    /**
     * Update the lives display of the player
     */
    public void updateLives() {

        int lives = GridGenerator.p.lives;

        // Initialize if needed
        if (this.lives == null) {
            this.lives = new HBox();
            this.lives.setPrefHeight(60);
        }

        // Clear existing children
        this.lives.getChildren().clear();

        // Add all life hearts
        for (int i = 0; i < lives; i++) {
            ImageView heart = new ImageView(new Image("./Images/" + getViewFolder() + "Heart.png"));
            heart.setFitWidth(60);
            heart.setFitHeight(60);
            this.lives.getChildren().add(heart);
        }

        // Add all broken hearts (missing lives)
        int missingLives = 3 - lives;
        for (int i = 0; i < missingLives; i++) {
            ImageView lostHeart = new ImageView(new Image("./Images/" + getViewFolder() + "BrokenHeart.png"));
            lostHeart.setFitHeight(60);
            lostHeart.setFitWidth(60);
            this.lives.getChildren().add(lostHeart);
        }
    }

    /**
     * Takes in keyboard input from the player and calls the movePlayer
     * function as needed.
     */
    public void addTextHandlingEvent() {
        stage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (!GridGenerator.p.isPlaying) {
                if (keyEvent.getCode().equals(KeyCode.W)) {
                    GridGenerator.movePlayer("NORTH");
                } else if (keyEvent.getCode() == KeyCode.A) {
                    GridGenerator.movePlayer("WEST");
                } else if (keyEvent.getCode().equals(KeyCode.S)) {
                    GridGenerator.movePlayer("SOUTH");
                } else if (keyEvent.getCode() == KeyCode.D) {
                    GridGenerator.movePlayer("EAST");
                }
            }
        });
    }

    public void articulateSound(String s) {
        // Check if sound enabled
        if(!soundEnabled) {
            return;
        }
        String x = "src" + File.separator + "Sounds" + File.separator + s + ".mp3";
        Media sound = new Media(new File(x).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlaying = true;

    }

    /**
     * This method stops articulations
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }


    public void clearFogAroundPlayer() {
        // Get player location
        Coordinates playerLoc = GridGenerator.p.getCoordinates();

        // Clear fog from all cells in 3x3 radius of player
        for (int x = playerLoc.getX()-1; x <= playerLoc.getX()+1; x++) {
            for (int y = playerLoc.getY()-1; y <= playerLoc.getY()+1; y++) {
                if (x >= 0 && x < grid.getGridSize() && y >= 0 && y < grid.getGridSize() &&
                        grid.getGridCell(x, y).hasFog()) {

                    // Clear fog attribute from that cell
                    grid.getGridCell(x, y).clearFog();

                    // Clear the fog visually
                    popCellVisual(x, y);

                    // Add island if needed
                    drawIsland(x, y);

                    // Draw rock if needed
                    drawRock(x, y);
                }
            }
        }

    }


    /**
     * Gets the cell stack pane at the specified coordinates
     * @param x The x coordinate of the desired grid cell.
     * @param y The y coordinate of the desired grid cell.
     * @return The associated stackPane with the given coordinates.
     */
    private StackPane getCellStackPane(int x, int y) {
        return (StackPane) tilePane.getChildren().get(x + y * grid.getGridSize());
    }


    /**
     * Clears the overlapping image from the specified grid cell.
     * @param x The x coordinate of the desired grid cell.
     * @param y The y coordinate of the desired grid cell.
     */
    public void popCellVisual(int x, int y) {

        // Don't update if there is fog (should be hidden)
        if (grid.getGridCell(x, y).hasFog()) {
            return;
        }

        // Get the stack pane
        StackPane stackPane = getCellStackPane(x, y);

        // Check if stack pane has more than 1 element (means there is an element overlapping)
        if (stackPane.getChildren().size() > 1) {

            // Remove top most layer from cell visual
            stackPane.getChildren().remove(stackPane.getChildren().size()-1);
        }
    }

    /**
     * Adds the specified image onto the specified grid cells visual display.
     * @param x The x coordinate of the desired grid cell
     * @param y The y coordinate of the desired grid cell
     * @param imageName The name of the image -> Example: "apple.png"
     */
    public void addToCellVisual(int x, int y, String imageName) {

        // Don't add anything if cell has fog
        if(grid.getGridCell(x, y).hasFog()) {
            return;
        }

        // Get the stack pane
        StackPane stackPane = getCellStackPane(x, y);

        // Get the image to apply
        Image image = new Image("./Images/" + getViewFolder() + imageName);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);

        // Add image onto stack pane
        stackPane.getChildren().add(imageView);

    }

    public void setUpMiniGame() {
        minigamePane.toFront();
        minigamePane.setAlignment(Pos.BOTTOM_CENTER); // Center the contents of minigamePane
        minigameLabel.setAlignment(Pos.BOTTOM_CENTER);
        minigameLabel.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 24px; -fx-padding: 20px;");

        if(viewMode.equals("GRAYSCALE")) {
            minigameLabel.setStyle("-fx-background-color: DimGray; -fx-text-fill: white; -fx-font-size: 24px; -fx-padding: 20px;");
        }

        minigameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24)); // Set the font and size
    }


    /**
     * Draw the settings display
     */
    public void drawSettings() {
        // Destroy existing settings (if it is there)
        destroySettings();

        GridGenerator.p.isPlaying = true;

        VBox settings = new VBox();
        this.settings = settings;

        // Vertical Box Styling
        settings.setStyle("-fx-background-color: LightGray; -fx-border-color: DarkSlateGray; -fx-border-width: 10;");
        settings.setMinSize(450, 700);
        settings.setMaxSize(450, 700);
        gridPane.add(settings, 1, 1);
        GridPane.setHalignment(settings, HPos.CENTER);
        settings.setAlignment(Pos.CENTER);

        // Settings Header Text
        Label settingsText = new Label("Settings");
        settingsText.setFont(new Font("Elephant", 30));
        VBox.setMargin(settingsText, new Insets(20));

        // Restart button
        Button restart = new Button("Restart");
        restart.setFont(new Font("Elephant", 30));
        restart.setPrefSize(300, 50);
        VBox.setMargin(restart, new Insets(20));

        // Restart when clicked
        restart.setOnMouseClicked(mouseEvent -> GridGenerator.restartGame());

        // Sound Toggle Button
        String soundStatus = "On";
        if(!soundEnabled) {
            soundStatus = "Off";
        }
        Button soundToggle = new Button("Sound: " + soundStatus);
        soundToggle.setFont(new Font("Elephant", 30));
        soundToggle.setPrefSize(300, 50);
        VBox.setMargin(soundToggle, new Insets(20));

        // Toggle sound on mouse click
        soundToggle.setOnMouseClicked(mouseEvent -> {
            soundEnabled = !soundEnabled;
            if (soundEnabled) {
                soundToggle.setText("Sound: On");
            } else {
                soundToggle.setText("Sound: Off");
            }
        });

        // Accessibility Section
        VBox accessibilityBox = new VBox();
        VBox.setMargin(accessibilityBox, new Insets(20));
        accessibilityBox.setAlignment(Pos.CENTER);

        // Accessibility Text
        Label accessibilityText = new Label("Accessibility Settings");
        accessibilityText.setFont(new Font("Elephant", 30));
        VBox.setMargin(accessibilityText, new Insets(20));

        // Regular vision button
        Button regularVision = new Button("Regular");
        regularVision.setFont(new Font("Elephant", 30));
        regularVision.setPrefSize(300, 45);
        regularVision.setOnMouseClicked(mouseEvent -> setViewMode("REGULAR"));

        // High contrast button
        Button highContrastVision = new Button("High Contrast");
        highContrastVision.setFont(new Font("Elephant", 30));
        highContrastVision.setPrefSize(300, 45);
        highContrastVision.setOnMouseClicked(mouseEvent -> setViewMode("HIGHCONTRAST"));

        // Grayscale button
        Button grayScaleVision = new Button("Grayscale");
        grayScaleVision.setFont(new Font("Elephant", 30));
        grayScaleVision.setPrefSize(300, 45);
        grayScaleVision.setOnMouseClicked(mouseEvent -> setViewMode("GRAYSCALE"));

        accessibilityBox.getChildren().addAll(accessibilityText, regularVision, highContrastVision, grayScaleVision);

        // Close Menu Button
        Button closeMenu = new Button("Close Menu");
        closeMenu.setFont(new Font("Elephant", 30));
        closeMenu.setPrefSize(300, 50);
        VBox.setMargin(closeMenu, new Insets(20));

        // Destroy settings popup when clicked
        closeMenu.setOnMouseClicked(mouseEvent -> destroySettings());

        settings.getChildren().addAll(settingsText, restart, soundToggle, accessibilityBox, closeMenu);
    }


    /**
     * Destroy the settings display
     */
    public void destroySettings() {
        gridPane.getChildren().remove(settings);
        GridGenerator.p.isPlaying = false;
    }

    /**
     * Set the view mode of the grid view for accessibility settings.
     * @param viewMode Takes "REGULAR", "HIGHCONTRAST", "GRAYSCALE"
     */
    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
        GridGenerator.p.isPlaying = false;
        buildGridPane();
    }

    /**
     * Gets the images folder depending on the viewmode for accessibility
     * @return The added image path for images of grayscale or high contrast
     */
    private String getViewFolder() {
        String viewFolder = "";
        if(viewMode.equals("HIGHCONTRAST")) { viewFolder = "HighContrastImages/"; }
        else if(viewMode.equals("GRAYSCALE")) { viewFolder = "GrayScaleImages/"; }

        return viewFolder;
    }

    public void displayRestartButton() {
        // Restart button
        Button restart = new Button("Restart");
        restart.setFont(new Font("Elephant", 50));
        restart.setPrefSize(500, 80);

        // Restart when clicked
        restart.setOnMouseClicked(mouseEvent -> GridGenerator.restartGame());

        gridPane.add(restart, 1, 1);
        GridPane.setHalignment(restart, HPos.CENTER);
        GridPane.setValignment(restart, VPos.CENTER);
    }

    /**
     * Get the view mode
     * @return The view mode
     */
    public String getViewMode() {
        return viewMode;
    }
}
