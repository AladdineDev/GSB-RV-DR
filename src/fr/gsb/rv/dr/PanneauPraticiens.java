package fr.gsb.rv.dr;

import java.util.Collections;
import java.util.List;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import fr.gsb.rv.dr.utilitaires.ComparateurDateVisite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PanneauPraticiens extends StackPane {

    public static final int CRITERE_COEF_CONFIANCE = 1;
    public static final int CRITERE_COEF_NOTORIETE = 2;
    public static final int CRITERE_COEF_VISITE = 3;

    private int critereTri = CRITERE_COEF_CONFIANCE;

    private RadioButton rbCoefConfiance = new RadioButton("Confiance");
    private RadioButton rbCoefNotoriete = new RadioButton("Notoriété");
    private RadioButton rbDateVisite = new RadioButton("Date Visite");

    private TableView<Praticien> tablePraticiens = new TableView<>();

    private List<Praticien> listPraticiens;
    private ObservableList<Praticien> observableListPraticiens;

    public PanneauPraticiens() {
        super();
        VBox vBox = new VBox();
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("Sélectionner un critère de tri : ");
        prompt.setStyle("-fx-font-weight: bold");

        vBox.getChildren().addAll(prompt, grid);
        this.getChildren().add(vBox);

        grid.add(rbCoefConfiance, 1, 0);
        grid.add(rbCoefNotoriete, 2, 0);
        grid.add(rbDateVisite, 3, 0);

        rbCoefConfiance.setOnAction(ActionEvent -> {
            this.setCritereTri(CRITERE_COEF_CONFIANCE);
            this.rafraichir();
        });
        rbCoefNotoriete.setOnAction(ActionEvent -> {
            this.setCritereTri(CRITERE_COEF_NOTORIETE);
            this.rafraichir();
        });
        rbDateVisite.setOnAction(ActionEvent -> {
            this.setCritereTri(CRITERE_COEF_VISITE);
            this.rafraichir();
        });

        ToggleGroup toggleGroup = new ToggleGroup();
        rbCoefConfiance.setToggleGroup(toggleGroup);
        rbCoefNotoriete.setToggleGroup(toggleGroup);
        rbDateVisite.setToggleGroup(toggleGroup);
        rbCoefConfiance.setSelected(true);

        try {
            listPraticiens = ModeleGsbRv.getPraticiensHesitants();
            observableListPraticiens = FXCollections.observableArrayList(listPraticiens);

            TableColumn<Praticien, Integer> columnNumero = new TableColumn<>("Numéro");
            TableColumn<Praticien, String> columnNom = new TableColumn<>("Nom");
            TableColumn<Praticien, String> columnVille = new TableColumn<>("Ville");

            columnNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
            tablePraticiens.getColumns().add(columnNumero);

            columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            tablePraticiens.getColumns().add(columnNom);

            columnVille.setCellValueFactory(new PropertyValueFactory<>("ville"));
            tablePraticiens.getColumns().add(columnVille);

            tablePraticiens.setItems(observableListPraticiens);
            this.rafraichir();

            vBox.getChildren().add(tablePraticiens);
        } catch (ConnexionException e) {
            e.printStackTrace();
        }
    }

    public void rafraichir() {
        try {
            listPraticiens = ModeleGsbRv.getPraticiensHesitants();
            observableListPraticiens = FXCollections.observableArrayList(listPraticiens);

            switch (this.getCritereTri()) {
                case CRITERE_COEF_CONFIANCE:
                    observableListPraticiens.sort(new ComparateurCoefConfiance());
                    tablePraticiens.setItems(observableListPraticiens);
                    break;

                case CRITERE_COEF_NOTORIETE:
                    observableListPraticiens.sort(new ComparateurCoefNotoriete());
                    Collections.reverse(observableListPraticiens);
                    tablePraticiens.setItems(observableListPraticiens);
                    break;

                case CRITERE_COEF_VISITE:
                    observableListPraticiens.sort(new ComparateurDateVisite());
                    Collections.reverse(observableListPraticiens);
                    tablePraticiens.setItems(observableListPraticiens);
                    break;

                default:
                    break;
            }
        } catch (ConnexionException e) {
            e.printStackTrace();
        }
    }

    public int getCritereTri() {
        return this.critereTri;
    }

    public void setCritereTri(int critereTri) {
        this.critereTri = critereTri;
    }

}
