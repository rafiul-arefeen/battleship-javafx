package org.example.battleship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerOneShooter {
    @FXML
    private GridPane shooterGrid1;
    @FXML
    private Label instructionLabel;
    @FXML
    private Button proceedButton;
    @FXML
    private Button finishButton;
    private Boolean[][] player2Board = PlayerTwo.getBoolGrid1();
    private static Boolean[][] clickTrack = new Boolean[9][9];
    private static Integer[][] hitMissTrack = new Integer[9][9];
    private static boolean canTakeTurn = true;
    private static int hitCount = 0;

    @FXML
    void initialize() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Button button = new Button();
                button.setPrefSize(60, 60);
                button.setText("");

                int _row = row;
                int _col = col;
                button.setOnAction(event -> buttonClicked(button, _row, _col));

                shooterGrid1.add(button, col, row);
                if(hitMissTrack[row][col] == 2)
                    colourButtonRed(row, col);
                else if(hitMissTrack[row][col] == 1)
                    colourButtonBlue(row, col);
                proceedButton.setVisible(false);
                finishButton.setVisible(false);
                instructionLabel.setText("Your turn!");
            }
        }
    }

    void buttonClicked(Button button, int row, int col) {
        if (canTakeTurn) {
            if (clickTrack[row][col]) {
                instructionLabel.setText("Already shot! Try another cell.");
            } else {
                if (player2Board[row][col]) {
                    instructionLabel.setText("Hit! Your turn again.");
                    hitCount++;
                    hitMissTrack[row][col] = 2;
                    colourButtonRed(row, col);
                } else {
                    instructionLabel.setText("Miss. Opponents turn.");
                    proceedButton.setVisible(true);
                    hitMissTrack[row][col] = 1;
                    colourButtonBlue(row, col);
                    canTakeTurn = false;
                }
                clickTrack[row][col] = true;
            }
        }
        if (hitCount == 10){
            instructionLabel.setText("You won!");
            finishButton.setVisible(true);
        }
    }

    public static void canTakeTurnTrue(){
        canTakeTurn = true;
    }

    void colourButtonBlue(int row, int col) {
        Button button = (Button) shooterGrid1.getChildren().get((row * 9) + col + 1);
        button.setStyle("-fx-background-color: blue;");
    }

    void colourButtonBlack(int row, int col) {
        Button button = (Button) shooterGrid1.getChildren().get((row * 9) + col + 1);
        button.setStyle("-fx-background-color: black;");
    }

    void colourButtonDefault(int row, int col) {
        Button button = (Button) shooterGrid1.getChildren().get((row * 9) + col + 1);
        button.setStyle("-fx-background-color: #F0F0F0;");
    }

    void colourButtonGrey(int row, int col) {
        Button button = (Button) shooterGrid1.getChildren().get((row * 9) + col + 1);
        button.setStyle("-fx-background-color: grey;");
    }

    void colourButtonRed(int row, int col) {
        Button button = (Button) shooterGrid1.getChildren().get((row * 9) + col + 1);
        button.setStyle("-fx-background-color: red;");
    }

    public static void initStartingBoard(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                hitMissTrack[i][j] = 0;
                clickTrack[i][j] = false;
            }
        }
    }

    public void clickProceed(ActionEvent e1) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PlayerTwoShooter.fxml"));
        Stage stage = (Stage) ((Node) e1.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        PlayerTwoShooter.canTakeTurnTrue();
        stage.setScene(scene);
        stage.setTitle("Battleship: Player Two");
        stage.show();
    }

    public void clickFinish(ActionEvent e2) throws IOException{
        Stage stage = (Stage) ((Node) e2.getSource()).getScene().getWindow();
        stage.close();
    }
}
