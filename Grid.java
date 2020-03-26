import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Timeline;

public class Grid {
  private final GridPane gridPane;
  private final int dimension;
  private int time = 0;

  public Grid(int dimension, double windowSize) {
    this.dimension = dimension;

    // Set the size of the grid to fit the window
    gridPane = new GridPane();
    gridPane.setMaxHeight(windowSize);
    gridPane.setMaxWidth(windowSize);

    // Add column/row constraints to make all grid cells take an equal share of space
    for (int i = 0; i < dimension; i++) {
      ColumnConstraints columnConstraints = new ColumnConstraints();
      columnConstraints.setPercentWidth(100f/dimension);
      gridPane.getColumnConstraints().add(columnConstraints);

      RowConstraints rowConstraints = new RowConstraints();
      rowConstraints.setPercentHeight(100f/dimension);
      gridPane.getRowConstraints().add(rowConstraints);
    }

    // Create each cell in the grid
    for (int col = 0; col < dimension; col++) {
      for (int row = 0; row < dimension; row++) {
        // Creates a new cell
        FlowPane cell = new FlowPane();

        // Ensures each cell will fill its slot in the grid
        cell.setPrefHeight(Double.POSITIVE_INFINITY);
        cell.setPrefWidth(Double.POSITIVE_INFINITY);

        // Adds the cell to the grid
        gridPane.add(cell, col, row);
      }
    }
  }

  public GridPane getGridPane() {
    return gridPane;
  }

  // Returns the cell at that exact position in the grid
  private Node getCell(int col, int row) {
    return gridPane.getChildren().get(row + col * dimension);
  }

  // Returns the cell that is n positions in from the start
  // Each ring, is a loop of cells around the grid
  // Ring 0 is the outer loop of cells
  // Higher ring numbers move further into the center
  private Node getCellInRing(int n, int ring) {
    // Set the maximum and minimum row and column numbers
    // This is based on which ring we are currently highlighting
    int min = ring;
    int max = dimension-1 - ring;

    // The size of each side of the current ring
    int sideLength = max - min;

    // If we have not reached the center (a single cell for odd sized grids)
    if (sideLength != 0) {
      // Get the current side of the ring we are on
      int side = n / sideLength;

      if (side == 0) {
        // Top side of the ring
        return getCell(min + n, min);
      } else if (side == 1) {
        // Right side of the ring
        return getCell(max, min + n % sideLength);
      } else if (side == 2) {
        // Bottom side of the ring
        return getCell(max - n % sideLength, max);
      } else if (side == 3) {
        // Left side of the ring
        return getCell(min, max - n % sideLength);
      }
    }

    // Otherwise return the central cell of the grid (the final cell to be highlighted)
    return getCell(ring, ring);
  }

  // Get the size of a ring
  private int getRingSize(int ring) {
    // The length of one side of the ring
    int length = dimension - 2*ring;

    // If the ring is a single cell
    if (length == 1)
      return 1;

    // If the ring is multiple cells
    return 4*(length - 1);
  }

  // Takes a time parameter and highlights the correct cell accordingly
  private Node getHighlightedCell(int time) {
    // Move from the outer ring to the inner ring
    for (int ring = 0; ring <= dimension/2; ring++) {
      // Get the number of cells in the ring
      int ringSize = getRingSize(ring);

      // If the current cell is in this ring
      if (time < ringSize) {
        // Highlight the current cell
        return getCellInRing(time, ring);
      }

      // Otherwise move onto the next ring
      time -= ringSize;
    }

    // The time provided has no corresponding cell
    return null;
  }

  // Handles the animation of cell highlighting
  public void animate() {
    // Handles every time a new frame of the animation is needed
    EventHandler<ActionEvent> highlightCell = new EventHandler<>() {
      // Updates the grid highlighting for the next cell along
      @Override
      public void handle(ActionEvent e) {
        // Sets the background of the cell to green
        getHighlightedCell(time).setStyle("-fx-background-color: green");
        time++;
      }
    };

    // Next frame of animation occurs every 50ms
    KeyFrame keyframe = new KeyFrame(Duration.millis(50), highlightCell);

    // Creates an animation timeline
    Timeline timeline = new Timeline(keyframe);

    // The animation continues until the entire grid is filled up (all NxN cells) before stopping
    timeline.setCycleCount(dimension*dimension);
    // Begins the animation
    timeline.play();
  }
}
