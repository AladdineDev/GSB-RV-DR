package fr.gsb.rv.dr;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class PanneauRapports extends StackPane {

    public PanneauRapports() {
        super();
        BorderPane centerPane = new BorderPane();
        Label label = new Label("Rapports");
        centerPane.setCenter(label);
        this.getChildren().add(centerPane);
    }

}
