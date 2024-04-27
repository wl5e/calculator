package ch.wl5e.calculator;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

public class Calculator extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        GridPane root = new GridPane(4, 4);
        root.setBackground(null);
        Scene scene = new Scene(root, 250, 350, Color.rgb(49, 49, 49));
        // Warning !
        var children = root.getChildren();

        // Create a text field and add it to the grid pane
        Label display = new Label("content");
        display.setMinWidth(450);
        display.setMinHeight(50);
        display.setStyle("-fx-font-size: 24; -fx-background-color: #3F3F3F; -fx-text-fill: #FFFFFF; -fx-font-family: 'Open Sans';");
        root.add(display, 0, 0, 4, 1);

        // Create buttons and add them to the grid pane
        Button[] buttons = new Button[15];
        // l1chorpe advice ! buttons[0->8]
        String[] buttonLabels = {"1","2","3","4","5","6","7","8","9","0","+","-","/","*","="};

        // buttons properties
        for (int i = 0; i < buttons.length; i++){
            buttons[i] = new Button(buttonLabels[i]);
            buttons[i].setMinWidth(50);
            buttons[i].setMinHeight(50);
            buttons[i].setStyle("-fx-font-size: 18; -fx-background-color: #171717; -fx-text-fill: #FFFFFF;");

        }
        // Display the buttons on the box
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Doesn't add buttons but tells them where they will go
                GridPane.setConstraints(buttons[(i * 3) + j], j, i + 1); // Add +1 to height to not hide the magnificent label
            }
        }

        GridPane.setConstraints(buttons[9], 1, 4); // 0
        GridPane.setConstraints(buttons[10], 3, 1); // +
        GridPane.setConstraints(buttons[11], 3, 2); // -
        GridPane.setConstraints(buttons[12], 3, 3); // /
        GridPane.setConstraints(buttons[13], 3, 4); // *
        GridPane.setConstraints(buttons[14], 3, 5); // =

        children.addAll(buttons);

        stage.setScene(scene);
        stage.show();
    }

    // TODO
    private ArrayList<Button> createNumericButtons() {
        return null;
    }

    private ArrayList<Button> createOperatorButtons() {
        return null;
    }

    private void setButtonEventHandlers(){

    }
}