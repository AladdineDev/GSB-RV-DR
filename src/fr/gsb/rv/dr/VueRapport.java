package fr.gsb.rv.dr;

import fr.gsb.rv.dr.entites.RapportVisite;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Window;

public class VueRapport extends Dialog<RapportVisite> {

    public VueRapport(RapportVisite rapportVisite) {
        super();
        Window window = this.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        this.setTitle("Rapport");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text scenetitle = new Text("Rapport de visite");
        scenetitle.setStyle("-fx-font-weight: bold");
        grid.add(scenetitle, 0, 0, 2, 1);

        Label numeroLabel = new Label("Mactricule : ");
        grid.add(numeroLabel, 0, 1);
        Text numeroField = new Text(String.valueOf(rapportVisite.getNumero()));
        grid.add(numeroField, 1, 1);

        Label dateVisiteLabel = new Label("Date de visite : ");
        grid.add(dateVisiteLabel, 0, 2);
        Text dateVisiteField = new Text(String.valueOf(rapportVisite.getDateVisite()));
        grid.add(dateVisiteField, 1, 2);

        Label dateRedactionLabel = new Label("Date de rédaction : ");
        grid.add(dateRedactionLabel, 0, 3);
        Text dateRedactionField = new Text(String.valueOf(rapportVisite.getDateRedaction()));
        grid.add(dateRedactionField, 1, 3);

        Label bilanLabel = new Label("Bilan : ");
        grid.add(bilanLabel, 0, 4);
        Text bilanField = new Text(String.valueOf(rapportVisite.getBilan()));
        grid.add(bilanField, 1, 4);

        Label motifLabel = new Label("Motif : ");
        grid.add(motifLabel, 0, 5);
        Text motifField = new Text(String.valueOf(rapportVisite.getMotif()));
        grid.add(motifField, 1, 5);

        Label coefConfianceLabel = new Label("Confiance : ");
        grid.add(coefConfianceLabel, 0, 6);
        Text coefConfianceField = new Text(String.valueOf(rapportVisite.getCoefConfiance()));
        grid.add(coefConfianceField, 1, 6);

        Button btnFermer = new Button("Retour à la liste");
        grid.add(btnFermer, 0, 7);
        btnFermer.setOnAction(event -> window.hide());

        this.getDialogPane().setContentText("Rapport de visite");
        this.getDialogPane().setContent(grid);

    }
}
