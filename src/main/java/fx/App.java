package fx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import fx.controllers.SudokuResolver;
import fx.models.SudokuMatrix;
import fx.view.SudokuScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene = new SudokuScene();
        stage.setScene(scene);
        stage.setTitle("Sudoku Resolver");
        stage.show();
    }

    public static void sudoku(String file){
        Path filePath = Paths.get(file);
        try (Stream<String> lines = Files.lines(filePath)) {
            List<List<Integer>> nums = lines.map(x -> Arrays.asList(x.split(" ")).stream().map(y -> Integer.parseInt(y)).toList()).toList();
            int dim = (int)Math.sqrt(nums.size());
            int[][] a = new int[nums.size()][nums.size()];
            for(int i = 0; i < nums.size(); i++){
                for(int j = 0; j < nums.size(); j++){
                    a[i][j] = nums.get(i).get(j);
                }
            }
            SudokuMatrix matrix = new SudokuMatrix(dim, a);
            SudokuResolver resolver = new SudokuResolver();
            resolver.resolve(matrix);
            a = matrix.getFullMatrix();
            System.out.println();
            for(int i = 0; i < nums.size(); i++){
                for(int j = 0; j < nums.size(); j++){
                    System.out.print(a[i][j] + " ");
                    if(j%dim == dim-1) System.out.print("| ");
                }
                System.out.println();
                if(i%dim == dim-1) System.out.println();
            }
        } catch (IOException e) { }
    }

    public static void run(String[] args) {
        /* 
        try {
            sudoku(args[0]);
        } catch (Exception e) { System.out.println("Sudoku non compilato"); }
         */
        launch();
    }
}
