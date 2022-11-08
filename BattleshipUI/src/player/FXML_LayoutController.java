/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

/**
 *
 * @author eleni
 */
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import java.io.IOException;
import static java.lang.String.valueOf;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Platform.exit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class FXML_LayoutController {

    private StackPane[][] stackPanes_enemy;
    private StackPane[][] stackPanes_player;
    private static final int maxrounds = 40; //--> 40 rounds each player
    private int round;
    private Cell[][] boardplayer;
    private Cell[][] boardenemy;
    private Player player;
    private Player enemy;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label player_points;

    @FXML
    private Label player_unsankships;

    @FXML
    private Label player_hitperc;

    @FXML
    private Label enemy_points;

    @FXML
    private Label enemy_unsankships;

    @FXML
    private Label enemy_hitperc;

    @FXML
    private GridPane board_enemy;

    @FXML
    private GridPane board_player;

    @FXML
    private TextField hit_row;

    @FXML
    private TextField hit_column;

    @FXML
    private Button hit_button;

    @FXML
    private Button start_button;

    @FXML
    private Label message;

    @FXML
    private Label shoot;

    @FXML
    private Label youplay;

    @FXML
    private Label enemyplays;

    @FXML
    private Label showround;

    @FXML
    private Label won;
    @FXML
    private TextField playerID;

    @FXML
    private TextField enemyID;

    //blank 10x10 board --> board of enemy (no info shown to the player)
    public void initPlayer() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                // first time
                StackPane s = new StackPane();
                s.setStyle("-fx-border-color: black;-fx-border;-fx-background-color: white; -fx-text-fill: white;");
                stackPanes_enemy[i][j] = s;
                board_enemy.add(s, j, i);
            }
        }
    }

    //ships of player are shown in a 10x10 board --> board of player
    public void initEnemy() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                // first time
                StackPane s = new StackPane();
                s.setStyle("-fx-border-color: black;-fx-border;-fx-background-color: white;");
                if (!enemy.getBoard()[i][j].isIsblank()) {
                    int id = enemy.getBoard()[i][j].getShip().getId();
                    switch (id) {
                        case 1: {
                            s.setStyle("-fx-border-color: black;-fx-border;-fx-background-color:rgba(61, 159, 255, 1)");
                            break;
                        }
                        case 2: {
                            s.setStyle("-fx-border-color: black;-fx-border;-fx-background-color:rgba(189, 186, 179, 1)");
                            break;
                        }
                        case 3: {
                            s.setStyle("-fx-border-color: black;-fx-border;-fx-background-color:rgba(255, 177, 18, 1)");
                            break;

                        }
                        case 4: {
                            s.setStyle("-fx-border-color: black;-fx-border;-fx-background-color:rgba(255, 232, 20, 1)");
                            break;
                        }
                        case 5: {
                            s.setStyle("-fx-border-color: black;-fx-border;-fx-background-color:rgba(20, 255, 59, 1)");
                            break;

                        }
                    }
                }
                stackPanes_player[i][j] = s;
                board_player.add(s, j, i);
            }

        }
    }

    //called every round 
    public void RefreshPlayer() {
        player_points.setText(valueOf(player.getPoints()));
        player_unsankships.setText(valueOf(player.getUnsank_ships()));
        player_hitperc.setText(valueOf(player.getHit_perc()));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                StackPane s = new StackPane();
                if (player.getBoard()[i][j].isIsshoot()) { //check if cell was shot
                    if (player.getBoard()[i][j].isIsblank()) { //no hit --> black
                        board_enemy.getChildren().remove(stackPanes_enemy[i][j]);
                        s.setStyle("-fx-border-color: black;-fx-border;-fx-background-color: black;");
                    } else {    //hit --> red
                        board_enemy.getChildren().remove(stackPanes_enemy[i][j]);
                        s.setStyle("-fx-border-color: black;-fx-border;-fx-background-color: red;");
                    }
                } else {//if not shot
                    s = stackPanes_enemy[i][j];
                    board_enemy.getChildren().remove(stackPanes_enemy[i][j]);
                }
                stackPanes_enemy[i][j] = s;
                board_enemy.add(s, j, i);
            }
        }

    }

    //called every round
    public void RefreshEnemy() {
        enemy_points.setText(valueOf(enemy.getPoints()));
        enemy_unsankships.setText(valueOf(enemy.getUnsank_ships()));
        enemy_hitperc.setText(valueOf(enemy.getHit_perc()));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                StackPane s = new StackPane();
                if (enemy.getBoard()[i][j].isIsshoot()) { //check if cell was shot
                    if (enemy.getBoard()[i][j].isIsblank()) { //no hit --> black
                        board_player.getChildren().remove(stackPanes_player[i][j]);
                        s.setStyle("-fx-border-color: black;-fx-border;-fx-background-color: black;");
                    } else {    //hit --> red
                        board_player.getChildren().remove(stackPanes_player[i][j]);
                        s.setStyle("-fx-border-color: black;-fx-border;-fx-background-color: red;");
                    }
                } else {//if not shot
                    s = stackPanes_player[i][j];
                    board_player.getChildren().remove(stackPanes_player[i][j]);
                }
                stackPanes_player[i][j] = s;
                board_player.add(s, j, i);
            }
        }
    }

    @FXML
    void start(ActionEvent event) throws IOException, OversizeException, OverlapTilesException, AdjacentTilesException, InvalidCountException {
        String file_name_p = "C:\\Users\\eleni\\Downloads\\BattleshipUI\\BattleshipUI\\src\\player\\medialab\\player_SCENARIO-" + playerID.getText() + ".txt";
        String file_name_e = "C:\\Users\\eleni\\Downloads\\BattleshipUI\\BattleshipUI\\src\\player\\medialab\\enemy_SCENARIO-" + enemyID.getText() + ".txt";
        try {
            boardplayer = Initialize_Board.readFile(file_name_p);
        } catch (InvalidCountException ex) {
            Logger.getLogger(FXML_LayoutController.class.getName()).log(Level.SEVERE, null, ex);
            message.setText("Player File: INVALID COUNT!");
            message.setStyle("-fx-text-fill: red;");
            return;
        } catch (OversizeException ex) {
            Logger.getLogger(FXML_LayoutController.class.getName()).log(Level.SEVERE, null, ex);
            message.setText("Player File: OVERSIZE!");
            message.setStyle("-fx-text-fill: red;");
            return;
        } catch (OverlapTilesException ex) {
            Logger.getLogger(FXML_LayoutController.class.getName()).log(Level.SEVERE, null, ex);
            message.setText("Player File: OVERLAP TILES!");
            message.setStyle("-fx-text-fill: red;");
            return;
        } catch (AdjacentTilesException ex) {
            Logger.getLogger(FXML_LayoutController.class.getName()).log(Level.SEVERE, null, ex);
            message.setText("Player File: ADJACENT TILES!");
            message.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            boardenemy = Initialize_Board.readFile(file_name_e);
        } catch (InvalidCountException ex) {
            Logger.getLogger(FXML_LayoutController.class.getName()).log(Level.SEVERE, null, ex);
            message.setText("Enemy File: INVALID COUNT!");
            message.setStyle("-fx-text-fill: red;");
            return;
        } catch (OversizeException ex) {
            Logger.getLogger(FXML_LayoutController.class.getName()).log(Level.SEVERE, null, ex);
            message.setText("Enemy File: OVERSIZE!");
            message.setStyle("-fx-text-fill: red;");
            return;
        } catch (OverlapTilesException ex) {
            Logger.getLogger(FXML_LayoutController.class.getName()).log(Level.SEVERE, null, ex);
            message.setText("Enemy File: OVERLAP TILES!"); //make red
            message.setStyle("-fx-text-fill: red;");
            return;
        } catch (AdjacentTilesException ex) {
            Logger.getLogger(FXML_LayoutController.class.getName()).log(Level.SEVERE, null, ex);
            message.setText("Enemy File: ADJACENT TILES!");
            message.setStyle("-fx-text-fill: red;");
            return;
        }
        player = new Player(boardenemy);
        enemy = new Player(boardplayer);
        enemyplays.setText("");
        youplay.setText("");
        won.setText("");
        hit_button.setDisable(false);
        hit_row.setPromptText("row");
        hit_column.setPromptText("column");
        shoot.setText("");
        initEnemy();
        initPlayer();
        RefreshPlayer();
        RefreshEnemy();
        round = maxrounds;
        showround.setText(String.valueOf(maxrounds - round));
        if (Math.random() > 0.5) {
            player.setPlayed_first(true);
            youplay.setText("YOU PLAY FIRST");
            youplay.setStyle("-fx-text-fill: green;");

        } else {
            enemyplays.setText("ENEMY PLAYS FIRST");
            enemyplays.setStyle("-fx-text-fill: red;");
            enemy.setPlayed_first(true);
            enemy.enemyPlays();
            shoot.setText(String.valueOf(enemy.getShoot_row()) + "  " + String.valueOf(enemy.getShoot_column()));
            try {
                enemy.hitcell(enemy.getShoot_row(), enemy.getShoot_column());
            } catch (alreadyshotException ex) {
                Logger.getLogger(FXML_LayoutController.class.getName()).log(Level.SEVERE, null, ex);
            }
            RefreshEnemy();
        }
    }

    @FXML
    void hitcell(ActionEvent event) {
        //player plays --> check if correct coordinates
        if (Integer.parseInt((hit_row.getText())) > 9 || Integer.parseInt((hit_row.getText())) < 0 || Integer.parseInt((hit_column.getText())) > 9 || Integer.parseInt((hit_column.getText())) < 0) {
            message.setText("Wrong value, try again"); //make red
            message.setStyle("-fx-text-fill: red;");
            return;
        }
        try {
            player.hitcell(Integer.parseInt((hit_row.getText())), Integer.parseInt((hit_column.getText())));
        } catch (alreadyshotException ex) {
            Logger.getLogger(FXML_LayoutController.class.getName()).log(Level.SEVERE, null, ex);
            message.setText("Already shot, try again"); //make red
            message.setStyle("-fx-text-fill: red;");
            return;
        }
        message.setText("");
        RefreshPlayer();
        //enemy played first
        if (enemy.isPlayed_first()) {
            --round;
            showround.setText(String.valueOf(maxrounds - round));
            if (round == 0 || enemy.getUnsank_ships() == 0 || player.getUnsank_ships() == 0) {
                hit_button.setDisable(true);
                if (enemy.getPoints() > player.getPoints()) {
                    won.setText("ENEMY WON!");
                    won.setStyle("-fx-text-fill: red;");
                } else if ((enemy.getPoints() < player.getPoints())) {
                    won.setText("YOU WON!");
                    won.setStyle("-fx-text-fill: green;");
                } //tie 
                else {
                    won.setText("IT'S A TIE!");
                    won.setStyle("-fx-text-fill:black;");
                }
                return;
            }
        }
        enemy.enemyPlays();
        shoot.setText(String.valueOf(enemy.getShoot_row()) + "  " + String.valueOf(enemy.getShoot_column()));
        try {
            enemy.hitcell(enemy.getShoot_row(), enemy.getShoot_column());
        } catch (alreadyshotException ex) {
            Logger.getLogger(FXML_LayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        RefreshEnemy();
        //player played first
        if (player.isPlayed_first()) {
            --round;
            showround.setText(String.valueOf(maxrounds - round));
            if (round == 0 || enemy.getUnsank_ships() == 0 || player.getUnsank_ships() == 0) {
                hit_button.setDisable(true);
                if (enemy.getPoints() > player.getPoints()) {
                    won.setText("ENEMY WON!");
                    won.setStyle("-fx-text-fill: red;");
                } else if ((enemy.getPoints() < player.getPoints())) {
                    won.setText("YOU WON!");
                    won.setStyle("-fx-text-fill: green;");
                } //tie 
                else {
                    won.setText("IT'S A TIE!");
                    won.setStyle("-fx-text-fill:black;");
                }
            }
            return;
        }

    }

    @FXML
    void initialize() {
        stackPanes_enemy = new StackPane[10][10];
        stackPanes_player = new StackPane[10][10];
    }
}
