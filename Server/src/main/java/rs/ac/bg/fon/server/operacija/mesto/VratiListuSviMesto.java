package rs.ac.bg.fon.server.operacija.mesto;

import java.util.List;
import rs.ac.bg.fon.zajednicki.model.Mesto;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 * Konkretna sistemska operacija zadužena za dobavljanje liste svih geografskih mesta iz baze podataka.
 * Koristi se najčešće za popunjavanje padajućih menija na formama za unos entiteta.
 *
 * @author Damjan
 */
public class VratiListuSviMesto extends ApstraktnaGenerickaOperacija {
    
    /**
     * Interna lista u koju se skladište objekti klase Mesto učitani iz baze.
     */
    private List<Mesto> mesta;

    /**
     * Podrazumevani konstruktor klase VratiListuSviMesto.
     */
    public VratiListuSviMesto() {
    }

    /**
     * Konstruktor za potrebe testiranja
     */
    public VratiListuSviMesto(rs.ac.bg.fon.server.broker.DBBrokerInterfejs broker) {
        super(broker);
    }

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        // Operacija preuzimanja svih mesta nema specifične preduslove
    }

    /**
     * Poziva brokera baze podataka da učita sve zapise iz tabele mesto bez restriktivnih uslova.
     */
    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        mesta = broker.getAll(new Mesto(), null);
    }

    /**
     * Vraća listu svih mesta uspešno preuzetih iz baze podataka.
     *
     * @return List Lista objekata tipa Mesto.
     */
    public List<Mesto> getMesta() {
        return mesta;
    }
}