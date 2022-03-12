package fr.gsb.rv.dr.technique;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {

    private static String dbURL = "jdbc:mysql://localhost:3306/gsb_rv_dr";
    private static String user = "developpeur";
    private static String password = "azerty";

    private static Connection connexion = null;

    private ConnexionBD() throws ConnexionException, SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connexion = (Connection) DriverManager.getConnection(dbURL, user, password);
        } catch (Exception e) {
            throw new ConnexionException();
        }
    }

    public static Connection getConnexion() throws ConnexionException {
        if (connexion == null) {
            try {
                new ConnexionBD();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connexion;
    }
}
