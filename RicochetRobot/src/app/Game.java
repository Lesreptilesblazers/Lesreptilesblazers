package app;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends HBox {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;

    private Tile[][] board;
    private final Random random = new Random();

    private List<Player> players = new ArrayList<>();
    private List<Robot> robots = new ArrayList<>();
    private Button suggestNumber;
    private Button demonstrate;
    private TextField number;
    private Label stats;
    private Player currentPlayer;
    private int currentIndex = 0;
    private Timeline timeline;
    private int maxTime = 512;
    private int lastTime = maxTime;
    private Robot selectedRobot;

    private Button left, right, up, down;
    private VBox movement;

    public Game() {
        setStyle("-fx-backgroud-color:white");
        setPadding(new Insets(20));
        GridPane gridPane = new GridPane();
        board = new Tile[WIDTH][HEIGHT];
        //Définie les tuiles de la grille affiché à l'écran
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                board[i][j] = new Tile();
                final Tile tile = board[i][j];
                board[i][j].setOnMouseClicked(e -> {
                    System.out.println("DSDSD");
                    if (tile.getRobot() != null) {
                        selectedRobot = tile.getRobot();
                        System.out.println("LOLOLO");
                    }
                });
                gridPane.add(board[i][j], j, i);
            }
        }
        createObstacles();
        for (int i = 0; i < 4; i++) {
            players.add(new Player("player" + (i + 1)));       //ajoute les quatres joueurs
        }
        // Ajoute les quatres robots et leur image sur les tuiles ils sont
        for (int i = 0; i < 4; i++) {
            Robot robot = new Robot(random.nextInt(WIDTH), random.nextInt(HEIGHT), i);
            robots.add(robot);
            ImageView imageView = new ImageView(robot.getImage());

            board[robot.getInitX()][robot.getInitY()].setGraphic(imageView);        // récupère une position +setgraphique défini une image sur la tuile dans l'intefacce
            board[robot.getInitX()][robot.getInitY()].setRobot(robot);      // Setrobot permet de se souvenir qu'il y a un robot ici pour la logique du jeux
        }
        currentPlayer = players.get(0);
        getChildren().add(gridPane);
        initControls();
        startTimer();
    }

    private void nextPlayer() {         // fait passer au joueur suivant ou retourner au premeir bjoueur
        currentIndex++;
        if (currentIndex >= players.size()) {
            currentIndex = 0;
        }
        currentPlayer = players.get(currentIndex);
    }

    private void initControls() {                                       //ajoute à l'interface tout les boutons et ajoute des call back qui sont éxecuté qaund ils sont clické
        suggestNumber = new Button("Suggest Number");
        demonstrate = new Button("Demonstrate");
        number = new TextField();
        number.setPromptText("Number");
        stats = new Label(players.get(0).getPlayerName() + "'s Turn \n");

        suggestNumber.setOnAction(event -> {
            String text = number.getText();
            try {
                int nu = Integer.parseInt(text);
                currentPlayer.setTimeTook(maxTime - lastTime);
                nextPlayer();
                startTimer();
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid");
            }
        });

        left = new Button("Move Left");
        right = new Button("Move Right");
        up = new Button("Move UP");
        down = new Button("Move Down");

        VBox cont = new VBox(5);
        movement = new VBox(5);
        movement.getChildren().addAll(left, right, up, down);
        movement.setVisible(false);
        cont.setPadding(new Insets(20));
        cont.getChildren().addAll(stats, number, suggestNumber, demonstrate, movement);
        getChildren().add(cont);

        demonstrate.setOnAction(event -> {
            movement.setVisible(true);
        });

        up.setOnAction(event -> {
            if (selectedRobot == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Select");
                alert.setHeaderText("Please select robot first");
                alert.showAndWait();
                return;
            }
            int i = selectedRobot.getDemX();
            for ( i = selectedRobot.getDemX(); i >= 0; i--) {
                if (i - 1 < 0) {
                    timeline.stop();
                    return;
                }
                Tile tile = board[i - 1][selectedRobot.getDemY()];
                if (tile.getRobot() != null) {
                    timeline.stop();
                }
                if (tile.destination) {
                    currentPlayer.setPoints(currentPlayer.getPoints() + 1);
                    timeline.stop();
                }
                if (tile.closed[3]) {
                    timeline.stop();
                }
            }
            // ça enlève le robot de sa position de base et le met sur sa nouvelle case
                board[i][selectedRobot.getDemY()].setGraphic(new ImageView(selectedRobot.getImage()));
                board[i][selectedRobot.getDemY()].setRobot(selectedRobot);
                board[selectedRobot.getDemX()][selectedRobot.getDemY()].setRobot(null);
                selectedRobot.setDemX(i);

        });

        down.setOnAction(event -> {
            if (selectedRobot == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Select");
                alert.setHeaderText("Please select robot first");
                alert.showAndWait();
                return;
            }
            int i = selectedRobot.getDemX();
            for (i = selectedRobot.getDemX(); i < WIDTH; i++) {
                if (i + 1 >= WIDTH) {
                    break;
                }
                Tile tile = board[i + 1][selectedRobot.getDemY()];
                if (tile.getRobot() != null) {
                    break;
                }
                if (tile.destination) {
                    currentPlayer.setPoints(currentPlayer.getPoints() + 1);
                    break;
                }
                if (tile.closed[1]) {
                    break;
                }
            }
            board[i][selectedRobot.getDemY()].setGraphic(new ImageView(selectedRobot.getImage()));
            board[i][selectedRobot.getDemY()].setRobot(selectedRobot);
            board[selectedRobot.getDemX()][selectedRobot.getDemY()].setRobot(null);
            selectedRobot.setDemX(i);
        });

        left.setOnAction(event -> {
            if (selectedRobot == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Select");
                alert.setHeaderText("Please select robot first");
                alert.showAndWait();
                return;
            }
            int i = selectedRobot.getDemY();
            for (i = selectedRobot.getDemY(); i >= 0; i--) {
                if (i - 1 < 0) {
                    timeline.stop();
                    return;
                }
                Tile tile = board[selectedRobot.getDemX()][i - 1];
                if (tile.getRobot() != null) {
                    timeline.stop();
                }
                if (tile.destination) {
                    currentPlayer.setPoints(currentPlayer.getPoints() + 1);
                    timeline.stop();
                }
                if (tile.closed[2]) {
                    timeline.stop();
                }
            }
            board[selectedRobot.getDemX()][i].setGraphic(new ImageView(selectedRobot.getImage()));
            board[selectedRobot.getDemX()][i].setRobot(selectedRobot);
            board[selectedRobot.getDemX()][selectedRobot.getDemY()].setRobot(null);
            selectedRobot.setDemY(i);

        });

        right.setOnAction(event -> {
            if (selectedRobot == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Select");
                alert.setHeaderText("Please select robot first");
                alert.showAndWait();
                return;
            }
            int i = selectedRobot.getDemY();
            for ( i = selectedRobot.getDemY(); i < HEIGHT; i++) {
                if (i + 1 >= HEIGHT) {
                    timeline.stop();
                    break;
                }
                Tile tile = board[selectedRobot.getDemX()][i + 1];
                if (tile.getRobot() != null) {
                    timeline.stop();
                }
                if (tile.destination) {
                    currentPlayer.setPoints(currentPlayer.getPoints() + 1);
                    timeline.stop();
                }
                if (tile.closed[1]) {
                    timeline.stop();
                }
            }
                board[selectedRobot.getDemX()][i].setGraphic(new ImageView(selectedRobot.getImage()));
                board[selectedRobot.getDemX()][i].setRobot(selectedRobot);
                board[selectedRobot.getDemX()][selectedRobot.getDemY()].setRobot(null);
                selectedRobot.setDemY(i);

        });

    }

    private void startTimer() {
        final int[] time = {lastTime};
        if (timeline != null) {
            timeline.stop();
        }
        timeline = new Timeline();                                  //permet d'executer la fonction qu'il y a e dessous toutes les secondes
        KeyFrame kf = new KeyFrame(Duration.seconds(0),
                event -> {
                    time[0]--;          //permet d'enlever au temps restant
                    stats.setText(currentPlayer.getPlayerName() + "'s Turn \n " + time[0]);
                    if (time[0] == 0) {
                        timeline.stop();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Result");
                        Player winner = players.get(0);
                        for (Player player:players){
                            if (player.getPoints() > winner.getPoints()){
                                winner = player;
                            }
                        }
                        alert.setHeaderText(winner.getPlayerName()+" Won the Game");
                        alert.showAndWait();
                        System.exit(0);
                    }
                });
        timeline.getKeyFrames().addAll(kf, new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void createObstacles() {                    //creer une map aléatoire, pour chacune des tuiles de la map ça génere un coté aléatoirement si la tuimle est selectionnée al&éatoirement. avec des nombres aléatoires
        int des = 0;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Tile tile = board[i][j];
                int shouldBuild = random.nextInt(9);     //random.nextInt génere un nombre aléatoire entre 0 et le nb donné - 1
                int desR = random.nextInt(2);
                if (shouldBuild == 4) {
                    int oneOrTwo = random.nextInt(2);
                    if (oneOrTwo == 0) {                            //si oneortwo = 0 => ferme 1 coté
                        int close = random.nextInt(4);
                        switch (close) {
                            case 0:
                                if (desR == 0 & des < 4) {
                                    tile.destination = true;
                                    des++;
                                }
                                tile.closeTop();
                                break;
                            case 1:
                                if (desR == 0 & des < 4) {
                                    tile.destination = true;
                                    des++;
                                }
                                tile.closeRight();
                                break;
                            case 2:
                                if (desR == 0 & des < 4) {
                                    tile.destination = true;
                                    des++;
                                }
                                tile.closeBottom();
                                break;
                            case 3:
                                if (desR == 0 & des < 4) {
                                    tile.destination = true;
                                    des++;
                                }
                                tile.closeLeft();
                                break;
                        }
                    } else {                                        //si OneOrTwo = 1 => ferme deux cotés
                        int close = random.nextInt(4);
                        switch (close) {
                            case 0:
                                if (desR == 0 & des < 4) {
                                    tile.destination = true;
                                    des++;
                                }
                                tile.closeTop();
                                tile.closeRight();
                                break;
                            case 1:
                                if (desR == 0 & des < 4) {
                                    tile.destination = true;
                                    des++;
                                }
                                tile.closeRight();
                                tile.closeBottom();
                                break;
                            case 2:
                                if (desR == 0 & des < 4) {
                                    tile.destination = true;
                                    des++;
                                }
                                tile.closeBottom();
                                tile.closeLeft();
                                break;
                            case 3:
                                if (desR == 0 & des < 4) {
                                    tile.destination = true;
                                    des++;
                                }
                                tile.closeLeft();
                                tile.closeTop();
                                break;
                        }
                    }
                }
            }
        }
    }

}
