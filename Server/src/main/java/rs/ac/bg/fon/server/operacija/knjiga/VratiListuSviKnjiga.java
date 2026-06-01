package rs.ac.bg.fon.server.operacija.knjiga;

import java.util.List;
import rs.ac.bg.fon.zajednicki.model.Knjiga;
import rs.ac.bg.fon.server.broker.DBBrokerInterfejs;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 * Konkretna sistemska operacija zadužena za dobavljanje kompletne liste knjiga iz baze podataka.
 *
 * @author Damjan
 */
public class VratiListuSviKnjiga extends ApstraktnaGenerickaOperacija {
    
    /**
     * Interna lista u koju se smeštaju objekti klase Knjiga učitani iz baze podataka.
     */
    private List<Knjiga> knjige;

    /**
     * Podrazumevani konstruktor klase VratiListuSviKnjiga.
     */
    public VratiListuSviKnjiga() {
    }

    /**
     * Konstruktor za potrebe testiranja
     */
    public VratiListuSviKnjiga(DBBrokerInterfejs broker) {
        super(broker);
    }

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        // Operacija preuzimanja svih knjiga nema predefinisane preduslove
    }

    /**
     * Poziva db brokera da učita sve zapise o knjigama bez dodatnih restriktivnih uslova filtriranja.
     */
    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        knjige = broker.getAll(new Knjiga(), null);
    }

    /**
     * Vraća listu svih knjiga koje su uspešno izvučene iz baze podataka.
     *
     * @return List Lista objekata tipa Knjiga.
     */
    public List<Knjiga> getKnjige() {
        return knjige;
    }
}