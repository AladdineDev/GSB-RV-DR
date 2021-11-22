package fr.gsb.rv.dr;

import java.util.Optional;

import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.technique.Session;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Appli extends Application {

    Session session;

    MenuBar barreMenus = new MenuBar();
    Menu menuFichier = new Menu("Fichier");
    Menu menuRapports = new Menu("Rapports");
    Menu menuPraticiens = new Menu("Praticiens");

    MenuItem itemSeConnecter = new MenuItem("Se connecter");
    MenuItem itemSeDeconnecter = new MenuItem("Se déconnecter");
    MenuItem itemQuitter = new MenuItem("Quitter");

    MenuItem itemConsulter = new MenuItem("Consulter");
    MenuItem itemHesitant = new MenuItem("Hésitants");

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("GSB-RV-DR");
            }
        });

        StackPane root = new StackPane();
        BorderPane topPane = new BorderPane();

        topPane.setTop(creerMenuBar());

        root.getChildren().add(topPane);
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("GSB-RV-DR");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar creerMenuBar() {
        menuFichier.getItems().addAll(itemSeConnecter, itemSeDeconnecter);
        menuFichier.getItems().add(new SeparatorMenuItem());
        menuFichier.getItems().addAll(itemQuitter);
        menuRapports.getItems().add(itemConsulter);
        menuPraticiens.getItems().add(itemHesitant);

        itemSeConnecter.setDisable(false);
        itemSeDeconnecter.setDisable(true);
        menuRapports.setDisable(true);
        menuPraticiens.setDisable(true);

        itemQuitter.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));

        itemSeConnecter.setOnAction(actionEvent -> {
            Session.ouvrir(new Visiteur("a17", "Andre", "David"));
            session = Session.getSession();

            itemSeConnecter.setDisable(true);
            itemSeDeconnecter.setDisable(false);
            menuRapports.setDisable(false);
            menuPraticiens.setDisable(false);
        });

        itemSeDeconnecter.setOnAction(actionEvent -> {
            Session.fermer();
            itemSeConnecter.setDisable(false);
            itemSeDeconnecter.setDisable(true);
            menuRapports.setDisable(true);
            menuPraticiens.setDisable(true);
        });

        itemQuitter.setOnAction(actionEvent -> {
            Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous quitter l'application ?",
                    ButtonType.YES, ButtonType.NO);
            alertQuitter.setTitle("Quitter");
            alertQuitter.setHeaderText("Demande de confirmation");

            Optional<ButtonType> reponse = alertQuitter.showAndWait();
            if (reponse.get().getButtonData() == ButtonData.YES) {
                Platform.exit();
            }
        });

        barreMenus.getMenus().addAll(menuFichier, menuRapports, menuPraticiens);

        return barreMenus;
    }

    public static void main(String[] args) {
        launch(args);
    }
}