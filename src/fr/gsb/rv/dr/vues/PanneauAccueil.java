package fr.gsb.rv.dr.vues;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class PanneauAccueil extends StackPane {

    public PanneauAccueil() {
        super();
        BorderPane centerPane = new BorderPane();
        Image logo = new Image("file:img/logo.png", 400, 252, true, true);
        centerPane.setCenter(new ImageView(logo));
        centerPane.setStyle("-fx-background-color: radial-gradient(center 50% 50% , radius 100% , #6690c5, #000000)");
        this.getChildren().add(centerPane);
    }
}
