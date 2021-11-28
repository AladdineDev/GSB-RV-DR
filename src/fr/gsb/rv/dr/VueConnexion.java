package fr.gsb.rv.dr;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Pair;

public class VueConnexion extends Dialog<Pair<String, String>> {

    boolean identifiantsIncorrects = false;

    VueConnexion(boolean identifiantIncorrect) {
        super();
        this.setTitle("Authentification");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text scenetitle = new Text("Saisir vos données de connexion.");
        grid.add(scenetitle, 0, 0, 2, 1);

        Label matriculeLabel = new Label("Mactricule : ");
        grid.add(matriculeLabel, 0, 1);

        TextField matriculeField = new TextField();
        grid.add(matriculeField, 1, 1);

        Label mdpLabel = new Label("Mot de passe : ");
        grid.add(mdpLabel, 0, 2);

        PasswordField mdpField = new PasswordField();
        grid.add(mdpField, 1, 2);

        if (identifiantIncorrect) {
            Label identifiantsInvalides = new Label("Identifiants invalides.");
            identifiantsInvalides.setTextFill(Color.RED);
            grid.add(identifiantsInvalides, 1, 3);
        }

        ButtonType btnConnexion = new ButtonType("Se connecter", ButtonData.OK_DONE);
        ButtonType btnAnnuler = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);

        this.getDialogPane().setContentText("Saisir vos données de connexion");
        this.getDialogPane().setContent(grid);
        this.getDialogPane().getButtonTypes().addAll(btnAnnuler, btnConnexion);

        this.setResultConverter(new Callback<ButtonType, Pair<String, String>>() {
            @Override
            public Pair<String, String> call(ButtonType typeButton) {
                if (typeButton == btnConnexion) {
                    return new Pair<String, String>(matriculeField.getText(), mdpField.getText());
                }
                return null;
            }
        });
    }
}
