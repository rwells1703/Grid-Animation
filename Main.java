import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public void start(Stage stage) {
    FlowPane root = new FlowPane();

    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    double windowSize = screenBounds.getWidth()*0.8;
    if (screenBounds.getWidth() > screenBounds.getHeight()) {
      windowSize = screenBounds.getHeight()*0.8;
    }

    int dimension = 4;

    Grid grid = new Grid(dimension, windowSize);
    root.getChildren().add(grid.getGridPane());

    Scene scene = new Scene(root, windowSize, windowSize);
    stage.setScene(scene);

    stage.show();

    grid.animate();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
