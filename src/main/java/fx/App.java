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

    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        newScene();
        stage.setTitle("Sudoku Resolver");
        stage.setResizable(false);
        stage.show();
    }

    private void newScene(){
        Scene scene = new SudokuScene(() -> newScene());
        stage.setScene(scene);
    }

    public static void run(String[] args) {
        launch();
    }
}
