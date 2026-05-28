package rs.ac.bg.fon.zajednicki.komunikacija;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Klasa zadužena za prijem podataka preko mrežnog soketa.
 * * @author Damjan
 */
public class Primalac {
    
    /**
     * Soket preko kojeg se prima mrežni saobraćaj sa udaljene strane.
     */
    private Socket socket;
    
    /**
     * Ulazni tok podataka za deserijalizaciju i čitanje objekata iz soketa.
     */
    ObjectInputStream in;

    /**
     * Konstruktor koji inicijalizuje objekat primaoca i kreira ulazni tok
     * podataka nad prosleđenim soketom.
     * * @param socket Soket preko kojeg se prihvataju mrežni podaci.
     */
    public Primalac(Socket socket) {
        this.socket = socket;
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Čita i deserijalizuje objekat pristigao sa mreže.
     * Ukoliko dođe do prekida veze od strane klijenta, ispisuje se poruka u konzoli.
     * * @return Primljeni Object sa mreže, ili null ukoliko dođe do greške.
     */
    public Object primi(){
        try { 
            return in.readObject();
        } catch (Exception ex) {
            System.out.println("Klijent se odvezao");
        }
        return null;
    }
}