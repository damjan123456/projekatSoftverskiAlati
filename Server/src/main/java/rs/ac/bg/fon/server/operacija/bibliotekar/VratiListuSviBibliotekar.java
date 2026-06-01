package rs.ac.bg.fon.server.operacija.bibliotekar;

import java.util.List;
import rs.ac.bg.fon.zajednicki.model.Bibliotekar;
import rs.ac.bg.fon.server.broker.DBBrokerInterfejs;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 * Konkretna sistemska operacija koja realizuje preuzimanje liste svih bibliotekara iz baze podataka.
 * Nasleđuje ApstraktnaGenerickaOperacija i sprovodi transakcionu logiku u okviru šablonske metode.
 *
 * @author Damjan
 */
public class VratiListuSviBibliotekar extends ApstraktnaGenerickaOperacija {
    
    /**
     * Interna lista u koju se smeštaju objekti klase Bibliotekar učitani iz baze.
     */
    private List<Bibliotekar> bibliotekari;

    /**
     * Podrazumevani konstruktor klase VratiListuSviBibliotekar.
     */
    public VratiListuSviBibliotekar() {
    }

    /**
     * Konstruktor za potrebe testiranja
     */
    public VratiListuSviBibliotekar(DBBrokerInterfejs broker) {
        super(broker);
    }

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        // Operacija preuzimanja svih zapisa nema specifične strukturne preduslove
    }

    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        bibliotekari = broker.getAll(new Bibliotekar(), null);
    }

    /**
     * Vraća listu svih bibliotekara učitanih nakon uspešnog izvršenja sistemske operacije.
     *
     * @return List Lista objekata tipa Bibliotekar.
     */
    public List<Bibliotekar> getBibliotekari() {
        return bibliotekari;
    }
}