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

public class PlayerOne {
    @FXML
    private GridPane grid1;
    @FXML
    private Label instructionLabel;
    @FXML
    private Button proceedButton;
    int bCount = 1;
    int dCount = 2;
    int sCount = 3;
    boolean placingSource = true;
    int sourceRow, sourceCol, destRow, destCol;
    private static Boolean[][] boolGrid1 = new Boolean[9][9];

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

                grid1.add(button, col, row);
                proceedButton.setVisible(false);

                boolGrid1[row][col] = false;
            }
        }
    }

    public static Boolean[][] getBoolGrid1() {
        return boolGrid1;
    }

    public void buttonClicked(Button button, int row, int col) {
        if (bCount > 0) {
            placeBattleship(row, col);
            setInstructionLabel();
        } else if (dCount > 0) {
            placeDestroyer(row, col);
            setInstructionLabel();
        } else if (sCount > 0) {
            placeSubmarine(row, col);
            setInstructionLabel();
        }
    }

    public void setInstructionLabel() {
        if (bCount > 0)
            instructionLabel.setText("Place Battleship");
        else if (dCount > 0)
            instructionLabel.setText("Place Destroyers");
        else if (sCount > 0)
            instructionLabel.setText("Place Submarines");
        else {
            instructionLabel.setText("All ships placed!");
            proceedButton.setVisible(true);
        }
    }

    void placeBattleship(int row, int col) {
        if (placingSource) {
            sourceRow = row;
            sourceCol = col;
            if (checkSourceValidity()) {
                colourButtonGrey(row, col);
                placingSource = false;
            }
        } else {
            destRow = row;
            destCol = col;
            if (checkDestValidity()) {
                placeAndMarkShip();
                bCount--;
            } else {
                colourButtonDefault(sourceRow, sourceCol);
            }
            placingSource = true;
        }
    }

    void placeDestroyer(int row, int col) {
        if (placingSource) {
            sourceRow = row;
            sourceCol = col;
            if (checkSourceValidity()) {
                colourButtonGrey(row, col);
                placingSource = false;
            }
        } else {
            destRow = row;
            destCol = col;
            if (checkDestValidity()) {
                placeAndMarkShip();
                dCount--;
            } else {
                colourButtonDefault(sourceRow, sourceCol);
            }
            placingSource = true;
        }
    }

    void placeSubmarine(int row, int col) {
        sourceRow = row;
        sourceCol = col;

        if (checkSourceValidity()) {
            colourButtonBlack(sourceRow, sourceCol);
            boolGrid1[sourceRow][sourceCol] = true;
            sCount--;
        }
    }

    boolean checkSourceValidity() {
        return !boolGrid1[sourceRow][sourceCol];
    }

    boolean checkDestValidity() {
        if (sourceRow != destRow && sourceCol != destCol)
            return false;
        if (sourceRow == destRow) {
            int colStart = Math.min(sourceCol, destCol);
            int colEnd = Math.max(sourceCol, destCol);

            for (int i = colStart; i <= colEnd; i++) {
                if (boolGrid1[sourceRow][i])
                    return false;
            }
        }
        if (sourceCol == destCol) {
            int rowStart = Math.min(sourceRow, destRow);
            int rowEnd = Math.max(sourceRow, destRow);

            for (int i = rowStart; i <= rowEnd; i++) {
                if (boolGrid1[i][sourceCol])
                    return false;
            }
        }
        int horizontalLen = Math.abs(destRow - sourceRow);
        int verticalLen = Math.abs(destCol - sourceCol);

        int len = horizontalLen + verticalLen;

        if (bCount > 0)
            return len == 2;
        else if (dCount > 0)
            return len == 1;
        else
            return false;
    }

    void placeAndMarkShip() {
        if (sourceRow == destRow) {
            int colStart = Math.min(sourceCol, destCol);
            int colEnd = Math.max(sourceCol, destCol);

            for (int i = colStart; i <= colEnd; i++) {
                colourButtonBlack(sourceRow, i);
                boolGrid1[sourceRow][i] = true;
            }
        } else {
            int rowStart = Math.min(sourceRow, destRow);
            int rowEnd = Math.max(sourceRow, destRow);

            for (int i = rowStart; i <= rowEnd; i++) {
                colourButtonBlack(i, sourceCol);
                boolGrid1[i][sourceCol] = true;
            }
        }
    }

    void colourButtonBlue(int row, int col) {
        Button button = (Button) grid1.getChildren().get((row * 9) + col + 1);
        button.setStyle("-fx-background-color: blue;");
    }

    void colourButtonBlack(int row, int col) {
        Button button = (Button) grid1.getChildren().get((row * 9) + col + 1);
        button.setStyle("-fx-background-color: black;");
    }

    void colourButtonDefault(int row, int col) {
        Button button = (Button) grid1.getChildren().get((row * 9) + col + 1);
        button.setStyle("-fx-background-color: #F0F0F0;");
    }

    void colourButtonGrey(int row, int col) {
        Button button = (Button) grid1.getChildren().get((row * 9) + col + 1);
        button.setStyle("-fx-background-color: grey;");
    }

    void colourButtonGreen(int row, int col) {
        Button button = (Button) grid1.getChildren().get((row * 9) + col + 1);
        button.setStyle("-fx-background-color: green;");
    }

    void colourButtonYellow(int row, int col) {
        Button button = (Button) grid1.getChildren().get((row * 9) + col + 1);
        button.setStyle("-fx-background-color: yellow;");
    }

    public void clickProceed(ActionEvent e1) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PlayerTwo.fxml"));
        Stage stage = (Stage) ((Node) e1.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Battleship: Player Two");
        stage.show();
    }
}