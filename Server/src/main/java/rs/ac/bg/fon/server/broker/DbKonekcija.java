package rs.ac.bg.fon.server.broker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.server.konfiguracija.Konfiguracija;

/**
 * Singleton klasa zadužena za kreiranje, upravljanje i deljenje jedinstvene konekcije sa bazom podataka.
 * Konfiguracioni podaci (URL, korisničko ime i lozinka) se dinamički učitavaju preko klase Konfiguracija.
 * Eksplicitno isključuje auto-commit režim kako bi omogućila upravljanje transakcijama sa višeg nivoa.
 *
 * @author Damjan
 */
public class DbKonekcija {
    
    /**
     * Jedinstvena statička instanca klase DbKonekcija.
     */
    private static DbKonekcija instance;
    
    /**
     * Aktivni objekat baze podataka koji predstavlja vezu sa MySQL serverom.
     */
    private Connection connection = null;

    /**
     * Privatni konstruktor koji učitava drajver za bazu podataka i uspostavlja 
     * konekciju koristeći spoljne konfiguracione parametre.
     * Isključuje automatsko potvrđivanje transakcija.
     */
    private DbKonekcija() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (connection == null || connection.isClosed()) {
                String url = Konfiguracija.getIntanca().getProperty("url");
                String username = Konfiguracija.getIntanca().getProperty("username");
                String password = Konfiguracija.getIntanca().getProperty("password");
                connection = DriverManager.getConnection(url, username, password);
                connection.setAutoCommit(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbKonekcija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Vraća trenutno otvorenu i aktivnu konekciju ka bazi podataka.
     *
     * @return Connection Objekat konekcije ka bazi podataka.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Vraća jedinstvenu instancu klase DbKonekcija. Ako instanca ne postoji, 
     * kreira se u tom trenutku.
     *
     * @return DbKonekcija Jedinstvena instanca ovog objekta.
     */
    public static DbKonekcija getInstance() {
        if (instance == null) {
            instance = new DbKonekcija();
        }
        return instance;
    }
}