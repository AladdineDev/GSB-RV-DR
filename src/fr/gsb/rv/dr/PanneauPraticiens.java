package fr.gsb.rv.dr;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class PanneauPraticiens extends StackPane {

    PanneauPraticiens() {
        super();
        BorderPane centerPane = new BorderPane();
        Label label = new Label("Praticiens");
        centerPane.setCenter(label);
        this.getChildren().add(centerPane);
    }

}
