package rs.ac.bg.fon.zajednicki.komunikacija;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Klasa zadužena za slanje podataka i objekata preko mrežnog soketa.
 * * @author Damjan
 */
public class Posiljalac {
    
    /**
     * Soket preko kojeg se vrši mrežna komunikacija sa udaljenom stranom.
     */
    private Socket socket;
    
    /**
     * Izlazni tok podataka za serijalizaciju i slanje objekata kroz soket.
     */
    private ObjectOutputStream out;

    /**
     * Konstruktor koji inicijalizuje objekat pošiljaoca i kreira izlazni tok
     * podataka nad prosleđenim soketom.
     * * @param socket Soket preko kojeg se uspostavlja veza i šalju podaci.
     */
    public Posiljalac(Socket socket) {
        this.socket = socket;
        try {
            // Kreiraj ObjectOutputStream JEDNOM
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Serijalizuje i šalje prosleđeni objekat preko mreže.
     * Nakon slanja, vrši se pražnjenje i resetovanje toka radi prevencije keširanja.
     * * @param object Objekat koji se šalje udaljenoj strani.
     */
    public void posalji(Object object){
        try {
            out.writeObject(object);
            out.flush();
            out.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}