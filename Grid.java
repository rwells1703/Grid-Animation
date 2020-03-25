import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

public class Grid {
  private final GridPane gridPane;
  private final int dimension;

  public Grid(int dimension, double windowSize) {
    this.dimension = dimension;

    gridPane = new GridPane();
    gridPane.setGridLinesVisible(true);
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
    return gridPane.getChildren().get(row + col * dimension + 1);
  }

  public Node getHighlightedCell(int t, int shell) {
    int min = shell;
    int max = dimension-1 - shell;

    int sideLength = max - min;

    if (sideLength != 0) {
      int side = t / sideLength;

      if (side == 0) {
        // Top
        return getCell(min + t, min);
      } else if (side == 1) {
        // Right
        return getCell(max, min + t % sideLength);
      } else if (side == 2) {
        // Bottom
        return getCell(max - t % sideLength, max);
      } else if (side == 3) {
        // Left
        return getCell(min, max - t % sideLength);
      }
    }

    return getCell(shell, shell);
  }

  public void animate() {
    getHighlightedCell(0,0).setStyle("-fx-background-color: red");
    getHighlightedCell(0,1).setStyle("-fx-background-color: red");
    getHighlightedCell(0,2).setStyle("-fx-background-color: red");
  }
}
