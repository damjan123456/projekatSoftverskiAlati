package rs.ac.bg.fon.server.main;

import rs.ac.bg.fon.server.forme.ServerskaForma;

/**
 * Glavna ulazna klasa serverske aplikacije koja sadrži main metodu.
 * Služi isključivo za instanciranje i prikazivanje glavnog grafičkog interfejsa servera.
 *
 * @author Damjan
 */
public class Main {
    
    /**
     * Podrazumevani konstruktor klase Main.
     */
    public Main() {
    }

    /**
     * Glavna metoda aplikacije. Kreira i prikazuje prozor ServerskaForma.
     *
     * @param args Argumenti komandne linije (ne koriste se).
     */
    public static void main(String[] args) {
        new ServerskaForma().setVisible(true);
    }
}