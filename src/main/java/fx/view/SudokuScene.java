package fx.view;

import fx.controllers.SudokuResolver;
import fx.models.SudokuMatrix;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class SudokuScene extends Scene{

    private int[][] matrix;
    private TextField[][] fields;

    public SudokuScene() {
        this(new BorderPane(), 300d, 400d);
    }
    private SudokuScene(BorderPane root, double width, double height) {
        super(root, width, height);
        matrix = new int[9][9];
        fields = new TextField[9][9];
        GridPane grid = new GridPane();
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                int x = i, y = j;
                TextField numericField = new TextField("");
                numericField.textProperty().addListener((observable, oldValue, newValue) -> {
                    try {
                        int numericInput = Integer.parseInt(newValue.charAt(newValue.length() -1) + "");
                        matrix[x][y] = numericInput;
                        numericField.setText(numericInput + "");
                    } catch (Exception e) {
                        numericField.setText("");
                        matrix[x][y] = 0;
                    }
                });
                numericField.setMinSize(40, 40);
                numericField.setMaxSize(40, 40);
                grid.add(numericField, i, j);
                fields[i][j] = numericField;
            }
        }
        Button Btn = new Button("Solve");
        Btn.setOnAction(event -> {
            SudokuMatrix sm = new SudokuMatrix(3, matrix);
            SudokuResolver sr = new SudokuResolver();
            sr.resolve(sm);
            matrix = sm.getFullMatrix();
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    fields[i][j].setText(matrix[i][j] + "");
                }
            }
        });
        Btn.setStyle("-fx-margin-right: 10px;");
        root.setCenter(grid);
        root.setBottom(Btn);


    }


}
