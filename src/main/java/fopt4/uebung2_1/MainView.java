package fopt4.uebung2_1;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainView extends BorderPane
{
    public MainView() {
        initView();
    }

    private void initView() {
        VBox topArea = new VBox(5);
        topArea.setPadding(new Insets(10));

        Label titleLabel = new Label("Kontaktsystem der HS Trier");
        topArea.getChildren().add(titleLabel);
        Label tagLine = new Label("Implementiert mit JavaFX");
        topArea.getChildren().add(tagLine);

        setTop(topArea);
    }

    public void setContent(Pane content) {
        setCenter(content);
        setMargin(content, new Insets(20, 20, 20, 20));
    }
}

