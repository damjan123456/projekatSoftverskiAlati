package rs.ac.bg.fon.server.server;

import rs.ac.bg.fon.server.niti.ObradaKlijentskihZahteva;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Predstavlja glavnu serversku nit zaduženu za osluškivanje i prihvatanje dolaznih 
 * klijentskih mrežnih veza na određenom portu. 
 * Prilikom uspostavljanja veze, server instancira klijentsku nit ObradaKlijentskihZahteva
 * i registruje je u internu listu aktivnih korisnika.
 *
 * @author Damjan
 */
public class Server extends Thread {
    
    /**
     * Kontrolni fleg koji označava da li server treba da nastavi sa osluškivanjem novih klijenata.
     */
    private boolean kraj = false;
    
    /**
     * Mrežni soket servera koji osluškuje dolazne zahteve za povezivanje.
     */
    private ServerSocket serverSocket;
    
    /**
     * Lista svih aktivno povezanih klijentskih niti koje trenutno komuniciraju sa serverom.
     */
    List<ObradaKlijentskihZahteva> klijenti = new ArrayList<>();

    /**
     * Podrazumevani konstruktor klase Server.
     */
    public Server() {
    }

    /**
     * Pokreće ServerSocket na portu 9000 i ulazi u beskonačnu petlju gde prihvata veze, 
     * pakuje ih u zasebne niti i startuje ih.
     */
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9000);
            System.out.println("Server je pokrenut");
            while (!kraj){
                try{
                    Socket s = serverSocket.accept();
                    System.out.println("Klijent je povezan");
                    
                    ObradaKlijentskihZahteva okz = new ObradaKlijentskihZahteva(s);
                    klijenti.add(okz);
                    okz.start();
                }catch(SocketException e){
                    // Hvata se izuzetak kada se soket nasilno zatvori metodom close() tokom accept-a
                }    
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Bezbedno zaustavlja rad servera, prolazi kroz listu svih aktivnih klijenata i gasi njihove komunikacione niti, 
     * a zatim zatvara i sam glavni serverski soket.
     */
    public void zaustaviServer(){
        kraj = true;
        try {
            for (ObradaKlijentskihZahteva klijent : klijenti)
                klijent.prekiniNit();
            
            serverSocket.close();
            System.out.println("Server je zaustavljen");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}