package clickers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ButtonExperiments extends Application  {
    private boolean green = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Button Backgroud Color");

        Button button1 = new Button("Change Color");
        button1.setStyle("-fx-background-color: #00ff00");
        button1.setOnAction(event -> getActionEventEventHandler(button1));

        HBox hbox = new HBox(button1);
        Scene scene = new Scene(hbox, 400, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void getActionEventEventHandler(Button button1) {

            if (green) {
                button1.setStyle("-fx-background-color: #00ff00");
            } else {
                button1.setStyle("-fx-background-color: #ff0000");
            }
            green = !green;

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}