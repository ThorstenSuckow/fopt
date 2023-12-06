package fopt3.uebung4_2;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 * From the API-docs at https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/layout/GridPane.html:
 *
 * <quote>
 *     By default the gridpane will resize rows/columns to their preferred sizes (either computed from content or fixed),
 *     even if the gridpane is resized larger than its preferred size. If an application needs a particular row or column
 *     to grow if there is extra space, it may set its grow priority on the RowConstraints or ColumnConstraints object. For example:
 *
 *      GridPane gridpane = new GridPane();
 *      ColumnConstraints column1 = new ColumnConstraints(100,100,Double.MAX_VALUE);
 *      column1.setHgrow(Priority.ALWAYS);
 *      ColumnConstraints column2 = new ColumnConstraints(100);
 *      gridpane.getColumnConstraints().addAll(column1, column2); // first column gets any extra width
 * </quote>
 *
 * Setting Hgrow/VGrow using the static methods setHgrow/setVgrow passing the children that needs to get resized
 * also works:
 *
 * <code>
 *  GridPane.setHgrow(Node, Priority.ALWAYS);
 *  GridPane.setVgrow(Node, Priority.ALWAYS);
 * </code>
 *
 */
public class GridPaneDemo extends Application {


    boolean shouldFill = false;

    private void fill(Button btn) {

        // example for positioning the button
        // by specifying the alignment
        GridPane.setHalignment(btn, HPos.RIGHT);
        GridPane.setValignment(btn, VPos.BOTTOM);

        if (!shouldFill) {
            return;
        }

        btn.setMaxHeight(Double.MAX_VALUE);
        btn.setMinHeight(Double.MAX_VALUE);
    }


    public void start(Stage stage) {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Insets ins = new Insets(10);
        gridPane.setPadding(ins);

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 5; j++) {

                if (i < 2 || j < 2) {
                    Button btn = new Button("Button " + i + "/" + j);
                    btn.setLayoutX(10);

                    fill(btn);
                    // allow for resizing the elements
                    GridPane.setHgrow(btn, Priority.ALWAYS);
                    GridPane.setVgrow(btn, Priority.ALWAYS);

                    gridPane.add(btn, i, j);
                }

            }
        }

        gridPane.setGridLinesVisible(true);

        Button btn;

        btn = new Button("Button 2/2");
        fill(btn);
        GridPane.setHgrow(btn, Priority.ALWAYS);
        GridPane.setVgrow(btn, Priority.ALWAYS);
        gridPane.add(btn, 2, 2, 3, 3);

        btn = new Button("Button */5");
        fill(btn);
        GridPane.setHgrow(btn, Priority.ALWAYS);
        GridPane.setVgrow(btn, Priority.ALWAYS);
        gridPane.add(btn, 0, 5, 3, 1);

        btn = new Button("Button 5/*");
        fill(btn);
        GridPane.setHgrow(btn, Priority.ALWAYS);
        GridPane.setVgrow(btn, Priority.ALWAYS);
        gridPane.add(btn, 5, 0, 1, 4);

        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.setTitle("GridPaneDemo");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
