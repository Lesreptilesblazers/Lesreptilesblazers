package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Ricochet Robot");
        stage.setScene(new Scene(new Game()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
