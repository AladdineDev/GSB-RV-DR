package fr.gsb.rv.dr.vues;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.gsb.rv.dr.entites.RapportVisite;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Mois;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PanneauRapports extends StackPane {

    private ComboBox<Visiteur> cbVisiteurs = new ComboBox<>();
    private ComboBox<Mois> cbMois = new ComboBox<>();
    private ComboBox<Integer> cbAnnee = new ComboBox<>();

    private TableView<RapportVisite> tableRapportsVisite = new TableView<>();

    private TableColumn<RapportVisite, Integer> columnNumero = new TableColumn<>("Numéro");
    private TableColumn<RapportVisite, String> columnNomPraticien = new TableColumn<>("Praticien");
    private TableColumn<RapportVisite, String> columnVillePraticien = new TableColumn<>("Ville");
    private TableColumn<RapportVisite, LocalDate> columnVisite = new TableColumn<>("Visite");
    private TableColumn<RapportVisite, LocalDate> columnRedaction = new TableColumn<>("Rédaction");

    private List<RapportVisite> listRapportsVisite;
    private ObservableList<RapportVisite> observableListRapportsVisite;

    public PanneauRapports() {
        super();
        VBox vBox = new VBox();
        GridPane grid = new GridPane();
        BorderPane borderPane = new BorderPane();

        Image logo = new Image("file:img/logo.png", 178, 85, true, true);
        this.getChildren().add(borderPane);

        Button btnValider = new Button("Valider");

        vBox.setStyle("-fx-background-color: linear-gradient(#95b3d7, #5687c1)");

        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));

        grid.add(cbVisiteurs, 0, 0);
        grid.add(cbMois, 1, 0);
        grid.add(cbAnnee, 2, 0);
        grid.add(btnValider, 0, 1);

        borderPane.setLeft(grid);
        borderPane.setRight(new ImageView(logo));
        borderPane.setPadding(new Insets(5, 5, 5, 5));

        try {
            List<Visiteur> listVisiteurs = ModeleGsbRv.getVisiteurs();
            ObservableList<Visiteur> observableListVisiteurs = FXCollections.observableArrayList(listVisiteurs);
            cbVisiteurs.setItems(observableListVisiteurs);
        } catch (ConnexionException e) {
            e.printStackTrace();
        }

        ObservableList<Mois> observableListMois = FXCollections.observableList(Arrays.asList(Mois.values()));
        cbMois.setItems(observableListMois);

        List<Integer> annees = new ArrayList<>();
        LocalDate aujourdhui = LocalDate.now();
        for (int i = 0; i <= 5; i++) {
            annees.add(aujourdhui.minusYears(i).getYear());
        }
        ObservableList<Integer> observableListAnnee = FXCollections.observableList(annees);
        cbAnnee.setItems(observableListAnnee);

        columnNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tableRapportsVisite.getColumns().add(columnNumero);

        columnNomPraticien.setCellValueFactory(param -> {
            String nom = param.getValue().getPraticien().getNom();
            return new SimpleStringProperty(nom);
        });
        tableRapportsVisite.getColumns().add(columnNomPraticien);

        columnVillePraticien.setCellValueFactory(param -> {
            String ville = param.getValue().getPraticien().getVille();
            return new SimpleStringProperty(ville);
        });
        tableRapportsVisite.getColumns().add(columnVillePraticien);

        columnVisite.setCellFactory(colonne -> {
            return new TableCell<RapportVisite, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText("");
                    } else {
                        DateTimeFormatter formateur = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                        setText(item.format(formateur));
                    }
                }

            };
        });
        columnVisite.setCellValueFactory(new PropertyValueFactory<>("dateVisite"));
        tableRapportsVisite.getColumns().add(columnVisite);

        columnRedaction.setCellFactory(colonne -> {
            return new TableCell<RapportVisite, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText("");
                    } else {
                        DateTimeFormatter formateur = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                        setText(item.format(formateur));
                    }
                }
            };
        });
        columnRedaction.setCellValueFactory(new PropertyValueFactory<>("dateRedaction"));
        tableRapportsVisite.getColumns().add(columnRedaction);

        tableRapportsVisite.setRowFactory(ligne -> {
            return new TableRow<RapportVisite>() {
                @Override
                protected void updateItem(RapportVisite item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        if (item.isLu()) {
                            setStyle("-fx-background-color: orange");
                        } else {
                            setStyle("-fx-background-color: #80a2ce");
                        }
                    } else {
                        setStyle("-fx-background-color: #ffffff");
                    }
                }
            };
        });

        tableRapportsVisite.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {
                int indiceRapport = tableRapportsVisite.getSelectionModel().getSelectedIndex();
                if (indiceRapport >= 0) {
                    try {
                        String matriculeVisiteur = cbVisiteurs.getValue().getMatricule();
                        int mois = cbMois.getValue().ordinal() + 1;
                        int annee = cbAnnee.getValue();
                        RapportVisite rapport = tableRapportsVisite.getSelectionModel().getSelectedItem();

                        ModeleGsbRv.setRapportsVisiteLu(matriculeVisiteur, rapport.getNumero());
                        this.rafraichir();
                        listRapportsVisite = ModeleGsbRv.getRapportsVisite(matriculeVisiteur, mois, annee);

                        VueRapport vueRapport = new VueRapport(rapport);
                        vueRapport.show();
                    } catch (ConnexionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnValider.setOnAction(actionEvent -> {
            if (cbVisiteurs.getValue() != null && cbMois.getValue() != null && cbAnnee.getValue() != null) {
                this.rafraichir();
            } else {
                Alert alertValider = new Alert(Alert.AlertType.ERROR,
                        "Veuillez sélectionner l'ensemble des champs", ButtonType.OK);
                alertValider.setTitle("Sélection incomplète");
                alertValider.setHeaderText("La sélection est incomplète");
                alertValider.showAndWait();
            }
        });

        this.getChildren().add(vBox);
        tableRapportsVisite.setItems(observableListRapportsVisite);
        tableRapportsVisite.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        vBox.getChildren().addAll(borderPane, tableRapportsVisite);
    }

    public void rafraichir() {
        try {
            String matriculeVisiteur = cbVisiteurs.getValue().getMatricule();
            int mois = cbMois.getValue().ordinal() + 1;
            int annee = cbAnnee.getValue();
            listRapportsVisite = ModeleGsbRv.getRapportsVisite(matriculeVisiteur, mois, annee);
            observableListRapportsVisite = FXCollections.observableArrayList(listRapportsVisite);

            tableRapportsVisite.setItems(observableListRapportsVisite);
        } catch (ConnexionException e) {
            e.printStackTrace();
        }
    }

}
