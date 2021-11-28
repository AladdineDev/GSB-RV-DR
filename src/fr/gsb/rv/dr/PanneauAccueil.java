package fr.gsb.rv.dr;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class PanneauAccueil extends StackPane {

    PanneauAccueil() {
        super();
        BorderPane centerPane = new BorderPane();
        Image logo = new Image("file:img/logo.png", 400, 252, true, true);
        centerPane.setCenter(new ImageView(logo));
        this.getChildren().add(centerPane);
    }
}
