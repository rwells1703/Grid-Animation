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

  public Node getHighlightedCell(int t) {
    int col = 0;
    int row = 0;

    int side = t / (dimension-1);

    if (side == 0) {
      col = t;
      row = 0;
    } else if (side == 1) {
      col = (dimension - 1);
      row = t - (dimension - 1);
    } else if (side == 2) {
      col = (dimension - 1) - t % (dimension - 1);
      row = (dimension-1);
    } else if (side == 3) {
      col = 0;
      row = (dimension - 1) - t % (dimension - 1);
    }

    return getCell(col, row);
  }

  public void animate() {
    getHighlightedCell(1).setStyle("-fx-background-color: red");
    getHighlightedCell(5).setStyle("-fx-background-color: red");
    getHighlightedCell(11).setStyle("-fx-background-color: red");
  }
}
