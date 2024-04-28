package ch.wl5e.calculator;

import java.util.ArrayList;
import java.util.Stack;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;


public class Calculator extends Application {
    // Declare display at the class level (for the ArrayList)
    private Label display = new Label();
    // store the mathematical expression
    private StringBuilder expression = new StringBuilder();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        GridPane root = new GridPane(4, 4);
        root.setBackground(null);
        Scene scene = new Scene(root, 212, 320, Color.rgb(49, 49, 49));
        // Warning !
        var children = root.getChildren();

        // Create a text field and add it to the grid pane
        display.setText("Hello !");
        display.setMinWidth(450);
        display.setMinHeight(50);
        display.setStyle("-fx-font-size: 24; -fx-background-color: #3F3F3F; -fx-text-fill: #FFFFFF; -fx-font-family: 'Open Sans';");
        root.add(display, 0, 0, 4, 1);

        // Create numeric buttons and add them to the grid pane
        Button[] buttons = new Button[15];
        // l1chorpe advice ! buttons[0->8]
        String[] buttonLabels = {"0","1","2","3","4","5","6","7","8","9","+","-","/","*","="};

        // buttons properties
        for (int i = 0; i < buttons.length; i++){
            buttons[i] = new Button(buttonLabels[i]);
            buttons[i].setMinWidth(50);
            buttons[i].setMinHeight(50);
            buttons[i].setStyle("-fx-font-size: 18; -fx-background-color: #171717; -fx-text-fill: #FFFFFF;");

        }
        // Mouse "hover" effect,
        for (Button button : buttons){
            button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 18; -fx-text-fill: #2F2F2F"));
            button.setOnMouseExited(e ->button.setStyle("-fx-font-size: 18; -fx-background-color: #171717; -fx-text-fill: #FFFFFF"));
        }
        // Display the buttons on the box
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Doesn't add buttons but tells them where they will go
                GridPane.setConstraints(buttons[(i * 3) + j +1], j, i + 1); // Add +1 to height to not hide the magnificent label
            }
        }

        GridPane.setConstraints(buttons[0], 1, 4); // 0
        GridPane.setConstraints(buttons[10], 3, 1); // +
        GridPane.setConstraints(buttons[11], 3, 2); // -
        GridPane.setConstraints(buttons[12], 3, 3); // /
        GridPane.setConstraints(buttons[13], 3, 4); // *
        GridPane.setConstraints(buttons[14], 3, 5); // =

        children.addAll(buttons);

        // Add event handlers to numeric buttons
        for (int i = 0; i <= 9; i++) {
            final int finalI = i; // Create a final variable
            buttons[i].setOnAction(e -> {
                expression.append(finalI); // Use the final variable
                display.setText(expression.toString()); // Update the display
            });
        }

        // Add event handlers to operator buttons
        String[] operators = {"+", "-", "/", "*"};
        for (int i = 0; i < 4; i++) {
            final String operator = operators[i];
            buttons[i + 10].setOnAction(e -> {
                expression.append(" ").append(operator).append(" ");
                display.setText(expression.toString()); // Update the display
            });
        }

        // Add event handler to "=" button
        buttons[14].setOnAction(e -> {
            if (expression.length() > 0) {
                display.setText(evaluateExpression(expression.toString()));
                expression.setLength(0); // Clear the expression
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    // TODO

    // Let's try the numeric buttons with "ArrayList<Button>"
    private ArrayList<Button> createNumericButtons() {
        ArrayList<Button> numericButtons = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            final int finalI = i; // create a final variable useable
            Button button = new Button(String.valueOf(i));
            button.setOnAction(e -> {
                expression.append(finalI);// <- there
                display.setText(expression.toString()); // Update the display
            });
            numericButtons.add(button);
        }
        return numericButtons;
    }

    // And the operator buttons ...
    private ArrayList<Button> createOperatorButtons() {
        ArrayList<Button> operatorButtons = new ArrayList<>();
        String[] operators = {"+", "-", "/", "*"};
        for (String operator : operators) {
            Button button = new Button(operator);
            button.setOnAction(e -> {
                expression.append(operator);
                display.setText(expression.toString()); // Update the display
            });
            operatorButtons.add(button);
        }
        return operatorButtons;
    }
    // Evaluate a mathematical expression ... "equal".
    // help ...
    private String evaluateExpression(String expression){
        String[] tokens = expression.split(" ");
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (String token : tokens) {
            if (token.isEmpty()) continue;
            char c = token.charAt(0);
            if (Character.isDigit(c)) {
                values.push(Double.parseDouble(token));
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                    values.push(applyOp(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            values.push(applyOp(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop().toString();
    }

    private boolean hasPrecedence(char op1, char op2) {
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        } else {
            return true;
        }
    }

    private double applyOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new UnsupportedOperationException("Cannot divide by zero");
                }
                return a / b;
        }
        return 0;
    }
}