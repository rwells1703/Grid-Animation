import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
  public void start(Stage stage) {
    // Creates a root pane for all other nodes
    FlowPane root = new FlowPane();

    // Get the minimum dimension of the screen (either the width or height)
    // The size of the window will be 80% of this dimension
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    double windowSize = screenBounds.getWidth()*0.8;
    if (screenBounds.getWidth() > screenBounds.getHeight()) {
      windowSize = screenBounds.getHeight()*0.8;
    }

    // The size of the grid
    int dimension = 9;

    // Create the grid and add it to the root pane
    Grid grid = new Grid(dimension, windowSize);
    root.getChildren().add(grid.getGridPane());

    // Create the scene
    Scene scene = new Scene(root, windowSize, windowSize);
    stage.setScene(scene);

    // Show the window on the screen
    stage.show();

    // Animate the grid
    grid.animate();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
