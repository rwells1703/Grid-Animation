import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;

public class Grid {
  private final GridPane gridPane;
  private final int dimension;
  private int t = 0;

  public Grid(int dimension, double windowSize) {
    this.dimension = dimension;

    gridPane = new GridPane();
    gridPane.setMaxHeight(windowSize);
    gridPane.setMaxWidth(windowSize);

    for (int i = 0; i < dimension; i++) {
      ColumnConstraints columnConstraints = new ColumnConstraints();
      columnConstraints.setPercentWidth(100f/dimension);
      gridPane.getColumnConstraints().add(columnConstraints);

      RowConstraints rowConstraints = new RowConstraints();
      rowConstraints.setPercentHeight(100f/dimension);
      gridPane.getRowConstraints().add(rowConstraints);
    }

    for (int col = 0; col < dimension; col++) {
      for (int row = 0; row < dimension; row++) {
        FlowPane cell = new FlowPane();

        cell.setPrefHeight(Double.POSITIVE_INFINITY);
        cell.setPrefWidth(Double.POSITIVE_INFINITY);
        gridPane.add(cell, col, row);
      }
    }
  }

  public GridPane getGridPane() {
    return gridPane;
  }

  private Node getCell(int col, int row) {
    return gridPane.getChildren().get(row + col * dimension);
  }

  private Node getCellInShell(int n, int shell) {
    int min = shell;
    int max = dimension-1 - shell;

    int sideLength = max - min;

    if (sideLength != 0) {
      int side = n / sideLength;

      if (side == 0) {
        // Top
        return getCell(min + n, min);
      } else if (side == 1) {
        // Right
        return getCell(max, min + n % sideLength);
      } else if (side == 2) {
        // Bottom
        return getCell(max - n % sideLength, max);
      } else if (side == 3) {
        // Left
        return getCell(min, max - n % sideLength);
      }
    }

    return getCell(shell, shell);
  }

  private int getShellSize(int shell) {
    int length = dimension - 2*shell;

    if (length == 1)
      return 1;

    return 4*(length - 1);
  }

  private Node getHighlightedCell(int t) {
    for (int shell = 0; shell <= dimension/2; shell++) {
      int shellSize = getShellSize(shell);

      if (t < shellSize) {
        return getCellInShell(t, shell);
      }

      t -= shellSize;
    }

    return null;
  }

  public void animate() {
    Timeline timeline = new Timeline(
      new KeyFrame(Duration.millis(50), e -> {
        getHighlightedCell(t).setStyle("-fx-background-color: green");
        t++;
      })
    );

    timeline.setCycleCount(dimension*dimension);
    timeline.play();
  }
}
