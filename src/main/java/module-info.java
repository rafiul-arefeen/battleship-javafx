module org.example.battleship {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.battleship to javafx.fxml;
    exports org.example.battleship;
}