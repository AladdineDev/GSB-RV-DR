package fr.gsb.rv.dr;

import java.util.Optional;

import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Session;
import fr.gsb.rv.dr.vues.PanneauAccueil;
import fr.gsb.rv.dr.vues.PanneauPraticiens;
import fr.gsb.rv.dr.vues.PanneauRapports;
import fr.gsb.rv.dr.vues.VueConnexion;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;

public class App extends Application {

    private Session session;
    private Visiteur visiteur;

    private Stage primaryStage;
    private StackPane root = new StackPane();
    private BorderPane contentPane = new BorderPane();

    private PanneauAccueil vueAccueil = new PanneauAccueil();
    private PanneauRapports vueRapports = new PanneauRapports();
    private PanneauPraticiens vuePraticiens = new PanneauPraticiens();

    private MenuBar barreMenus = new MenuBar();

    private Menu menuConnexion = new Menu("Connexion");
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
        stage.setMinHeight(420);
        stage.setMinWidth(720);

        contentPane.setTop(creerMenuBar());
        contentPane.setCenter(vueAccueil);

        root.getChildren().add(contentPane);

        Scene scene = new Scene(root, 720, 480);

        primaryStage.setTitle("GSB-RV-DR - Bienvenue");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/logo_old.png")));
        primaryStage.setScene(scene);
        primaryStage.show();

        Window window = primaryStage.getScene().getWindow();
        window.setOnCloseRequest(event -> {
            Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous quitter l'application ?",
                    ButtonType.YES, ButtonType.NO);
            alertQuitter.setTitle("Quitter");
            alertQuitter.setHeaderText("Demande de confirmation");

            Optional<ButtonType> reponse = alertQuitter.showAndWait();
            if (reponse.get().getButtonData() == ButtonData.YES) {
                Platform.exit();
            }
        });
    }

    private MenuBar creerMenuBar() {
        menuConnexion.getItems().addAll(itemSeConnecter, itemSeDeconnecter);
        menuConnexion.getItems().add(new SeparatorMenuItem());
        menuConnexion.getItems().addAll(itemQuitter);
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

            primaryStage.setTitle("GSB-RV-DR - À bientôt");
            contentPane.setCenter(vueAccueil);
        });

        itemQuitter.setOnAction(actionEvent -> {
            Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous quitter l'application ?",
                    new ButtonType("Oui", ButtonData.YES), new ButtonType("Non", ButtonData.NO));
            alertQuitter.setTitle("Quitter");
            alertQuitter.setHeaderText("Demande de confirmation");

            Optional<ButtonType> reponse = alertQuitter.showAndWait();
            if (reponse.get().getButtonData() == ButtonData.YES) {
                Platform.exit();
            }
        });

        itemConsulter.setOnAction(actionEvent -> {
            contentPane.setCenter(vueRapports);
        });

        itemHesitant.setOnAction(actionEvent -> {
            contentPane.setCenter(vuePraticiens);
        });

        barreMenus.getMenus().addAll(menuConnexion, menuRapports, menuPraticiens);

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
                this.authentification(true);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}