package rs.ac.bg.fon.server.konfiguracija;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton klasa zadužena za upravljanje konfiguracionim parametrima serverske aplikacije.
 * Omogućava dinamičko čitanje, izmenu i trajno skladištenje parametara
 * unutar eksternog .properties fajla.
 *
 * @author Damjan
 */
public class Konfiguracija {
    
    /**
     * Jedinstvena statička instanca klase Konfiguracija.
     */
    private static Konfiguracija instanca;
    
    /**
     * Objekat koji sadrži skup parova ključ-vrednost učitanih iz konfiguracionog fajla.
     */
    private Properties konfiguracija;

    /**
     * Privatni konstruktor koji inicijalizuje Properties objekat i učitava konfiguraciju 
     * sa fiksne putanje na disku. Ako fajl ne postoji, ispisuje grešku u konzoli.
     */
    private Konfiguracija() {
        try {
            konfiguracija = new Properties();
            konfiguracija.load(new FileInputStream("D:\\FON\\ProjektovanjeSoftvera\\seminarski\\netbeans\\config\\config.properties"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Vraća jedinstvenu instancu klase Konfiguracija. Ukoliko instanca još uvek nije 
     * kreirana, poziva se privatni konstruktor.
     *
     * @return Konfiguracija Jedinstvena instanca ove klase.
     */
    public static Konfiguracija getIntanca() {
        if (instanca == null)
            instanca = new Konfiguracija();
        return instanca;
    }
    
    /**
     * Vraća vrednost konfiguracionog parametra na osnovu prosleđenog ključa.
     * Ukoliko ključ ne postoji u fajlu, metoda vraća podrazumevanu vrednost "n/a".
     *
     * @param key Jedinstveni naziv konfiguracionog parametra.
     * @return String Vrednost parametra iz fajla, ili "n/a" ukoliko ključ ne postoji.
     */
    public String getProperty(String key){
        return konfiguracija.getProperty(key, "n/a");
    }
    
    /**
     * Postavlja ili ažurira vrednost konfiguracionog parametra u radnoj memoriji aplikacije.
     * Da bi izmena ostala trajna, potrebno je nakon ovoga pozvati metodu izmeni().
     *
     * @param key Jedinstveni naziv parametra koji se menja/dodaje.
     * @param value Nova vrednost koja se dodeljuje ključu.
     */
    public void setProperty(String key, String value){
        konfiguracija.setProperty(key, value);
    }
    
    /**
     * Trajno upisuje i sinhronizuje sve izmene iz Properties objekta nazad u eksterni 
     * konfiguracioni fajl na disku.
     */
    public void izmeni(){
        try {
            konfiguracija.store(new FileOutputStream("D:\\FON\\ProjektovanjeSoftvera\\seminarski\\netbeans\\config\\config.properties"), null);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}