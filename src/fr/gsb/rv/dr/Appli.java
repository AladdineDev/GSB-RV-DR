package fr.gsb.rv.dr;

import java.util.Optional;

import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Session;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import javafx.util.Pair;

public class Appli extends Application {

    private Session session;
    private Visiteur visiteur;

    private Stage primaryStage;
    private StackPane root = new StackPane();
    private BorderPane contentPane = new BorderPane();

    private PanneauAccueil vueAccueil = new PanneauAccueil();
    private PanneauRapports vueRapports = new PanneauRapports();
    private PanneauPraticiens vuePraticiens = new PanneauPraticiens();

    private MenuBar barreMenus = new MenuBar();

    private Menu menuFichier = new Menu("Fichier");
    private Menu menuRapports = new Menu("Rapports");
    private Menu menuPraticiens = new Menu("Praticiens");

    private MenuItem itemSeConnecter = new MenuItem("Se connecter");
    private MenuItem itemSeDeconnecter = new MenuItem("Se déconnecter");
    private MenuItem itemQuitter = new MenuItem("Quitter");
    private MenuItem itemConsulter = new MenuItem("Consulter");
    private MenuItem itemHesitant = new MenuItem("Hésitants");

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        contentPane.setTop(creerMenuBar());
        contentPane.setCenter(vueAccueil);

        root.getChildren().add(contentPane);

        Scene scene = new Scene(root, 1280, 720);

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
            this.authentification(false);
        });

        itemSeDeconnecter.setOnAction(actionEvent -> {
            Session.fermer();
            itemSeConnecter.setDisable(false);
            itemSeDeconnecter.setDisable(true);
            menuRapports.setDisable(true);
            menuPraticiens.setDisable(true);

            primaryStage.setTitle("GSB-RV-DR");
            contentPane.setCenter(vueAccueil);
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

        itemConsulter.setOnAction(ActionEvent -> {
            System.out.println("[Rapports]" + visiteur.getPrenom() + " " + visiteur.getNom());
            contentPane.setCenter(vueRapports);
        });

        itemHesitant.setOnAction(ActionEvent -> {
            System.out.println("[Praticiens]" + visiteur.getPrenom() + " " + visiteur.getNom());
            contentPane.setCenter(vuePraticiens);
        });

        barreMenus.getMenus().addAll(menuFichier, menuRapports, menuPraticiens);

        return barreMenus;
    }

    private void authentification(boolean identifiantsIncorrects) {
        VueConnexion vueConnexion = new VueConnexion(identifiantsIncorrects);
        Optional<Pair<String, String>> reponse = vueConnexion.showAndWait();
        if (reponse.isPresent()) {
            try {
                visiteur = ModeleGsbRv.seConnecter(reponse.get().getKey(), reponse.get().getValue());
            } catch (ConnexionException e) {
                e.printStackTrace();
            }
            if (visiteur != null) {
                Session.ouvrir(visiteur);
                session = Session.getSession();
                visiteur = session.getLeVisiteur();

                itemSeConnecter.setDisable(true);
                itemSeDeconnecter.setDisable(false);
                menuRapports.setDisable(false);
                menuPraticiens.setDisable(false);

                primaryStage.setTitle(visiteur.getPrenom() + " " + visiteur.getNom());
            } else {
                System.out.println("Identifiants incorrect");
                this.authentification(true);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}