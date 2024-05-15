package ch.wl5e.calculator;

import java.util.Stack;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

import javafx.util.Duration;

public class Calculator extends Application {
    // Declare display at the class level (for the ArrayList)
    private final Label display = new Label();
    // store the mathematical expression
    private final StringBuilder expression = new StringBuilder();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {

        GridPane root = new GridPane(4, 4);
        root.setPadding(new Insets(10));// Add a marge around the grid
        root.setBackground(null);
        Scene scene = new Scene(root, 256, 384, Color.rgb(31, 31, 31));
        // Warning !
        var children = root.getChildren();
        // Create an animation
        

        // Create a text field and add it to the grid pane
        display.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        display.setText("80085");
        display.setMinWidth(240);
        display.setMinHeight(60);
        display.setStyle("-fx-font-size: 24; -fx-background-color: #2F2F2F; -fx-text-fill: #FFFFFF; -fx-font-family: 'Thoma'; -fx-background-radius: 5;");
        root.add(display, 0, 0, 4, 1);

        // Create numeric buttons and add them to the grid pane
        Button[] buttons = new Button[18];
        // l1chorpe advice ! buttons[0->8]
        String[] buttonLabels = {"1","2","3","4","5","6","7","8","9","0","+","-","/","*","=","C","CE", "."};

        // buttons properties
        for (int i = 0; i < buttons.length; i++){
            buttons[i] = new Button(buttonLabels[i]);
            buttons[i].setMinWidth(56); // Width size
            buttons[i].setMinHeight(56); // Height size
            buttons[i].setStyle("-fx-font-size: 18; -fx-background-color: #171717; -fx-text-fill: #FFFFFF;");
            switch (buttonLabels[i]) {
                case "C" -> buttons[i].setOnAction(e -> {
                    if (!expression.isEmpty()) {
                        expression.deleteCharAt(expression.length() - 1);  // Delete the last expression
                        display.setText(expression.toString());
                    }
                });
                case "CE" -> buttons[i].setOnAction(e -> {
                    expression.setLength(0);  // Remove all the expression
                    display.setText("");
                });
                case "." -> buttons[i].setOnAction(e -> {
                    expression.append(".");
                    display.setText(expression.toString());
                });
            }
            // Set the position of the button in the grid
            int row, col;

            if (i < 9) {  // If the button is a numeric button (1-9)
                row = i / 3 + 2;  // +2 because the first row is for the display and the second for "C", "CE", and "."
                col = i % 3;
            } else if (i == 9) {  // If the button is "0"
                row = 5;
                col = 1;
            } else if (i < 15) {  // If the button is an operator button or "="
                row = i - 9; //Display + 1
                col = 3;
            } else {  // If the button is "C", "CE", or "."
                row = 1;
                col = i - 15;
            }
            GridPane.setConstraints(buttons[i], col, row);
            GridPane.setHalignment(buttons[i], HPos.CENTER); // Center button horizontally in its cell
            GridPane.setValignment(buttons[i], VPos.CENTER); // Center button vertically in its cell
            children.add(buttons[i]);  // Add the button to the GridPane
        }
        // Mouse "hover" effect,
        for (Button button : buttons){
            button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 18; -fx-text-fill: #2F2F2F"));
            button.setOnMouseExited(e ->button.setStyle("-fx-font-size: 18; -fx-background-color: #171717; -fx-text-fill: #FFFFFF"));
        }

        // Add event handlers to numeric buttons
        for (int i = 0; i <= 9; i++) {
            final int finalI;
            if (i == 9) {  // If the button is "0"
                finalI = 0;
            } else {
                finalI = i + 1; // Add 1 to i for other buttons
            }
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
            if (!expression.isEmpty()) {
                display.setText(evaluateExpression(expression.toString()));
                expression.setLength(0); // Clear the expression
            }
        });

        stage.setScene(scene);
        //Use the keyboard layout !
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case DIGIT1:
                case NUMPAD1:
                    buttons[0].fire();
                    break;
                case DIGIT2:
                case NUMPAD2:
                    buttons[1].fire();
                    break;
                case DIGIT3:
                case NUMPAD3:
                    buttons[2].fire();
                    break;
                case DIGIT4:
                case NUMPAD4:
                    buttons[3].fire();
                    break;
                case DIGIT5:
                case NUMPAD5:
                    buttons[4].fire();
                    break;
                case DIGIT6:
                case NUMPAD6:
                    buttons[5].fire();
                    break;
                case DIGIT7:
                case NUMPAD7:
                    buttons[6].fire();
                    break;
                case DIGIT8:
                case NUMPAD8:
                    buttons[7].fire();
                    break;
                case DIGIT9:
                case NUMPAD9:
                    buttons[8].fire();
                    break;
                case DIGIT0:
                case NUMPAD0:
                    buttons[9].fire();
                    break;
                case ADD:
                    buttons[10].fire();
                    break;
                case SUBTRACT:
                    buttons[11].fire();
                    break;
                case DIVIDE:
                    buttons[12].fire();
                    break;
                case MULTIPLY:
                    buttons[13].fire();
                    break;
                case ENTER:
                    buttons[14].fire();
                    break;
                case BACK_SPACE:
                    buttons[15].fire();
                    break;
                case DELETE:
                    buttons[16].fire();
                    break;
                case DECIMAL:
                    buttons[17].fire();
                    break;
                default:
                    break;
            }
        });

        //Size the window to the scene.
        stage.sizeToScene();
    
        // Create a FadeTransition
        FadeTransition ft = new FadeTransition(Duration.millis(1000), root);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);

        // Start the animation
        ft.play();

        //Show the stage
        stage.show();
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
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }

    private double applyOp(char op, double b, double a) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) {
                    throw new UnsupportedOperationException("Cannot divide by zero");
                }
                yield a / b;
            }
            default -> 0;
        };
    }

}