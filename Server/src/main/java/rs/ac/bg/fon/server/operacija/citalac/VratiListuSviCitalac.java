package rs.ac.bg.fon.server.operacija.citalac;

import java.util.List;
import rs.ac.bg.fon.zajednicki.model.Citalac;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 * Konkretna sistemska operacija koja preuzima listu svih čitalaca iz baze podataka 
 * zajedno sa podacima o njihovom mestu prebivališta kroz SQL JOIN mehanizam.
 *
 * @author Damjan
 */
public class VratiListuSviCitalac extends ApstraktnaGenerickaOperacija {
    
    /**
     * Interna lista u koju se skladište učitani objekti klase Citalac.
     */
    private List<Citalac> citaoci;

    /**
     * Podrazumevani konstruktor klase VratiListuSviCitalac.
     */
    public VratiListuSviCitalac() {
    }

    @Override
    protected void preduslovi(Object objekat) {
        // Operacija učitavanja svih entiteta nema predefinisane strukturne preduslove
    }

    /**
     * Formira SQL JOIN uslov i poziva brokera da učita sve čitaoce sa spojenim podacima iz tabele mesto.
     */
    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        String uslov = " JOIN mesto ON citalac.idMesto=mesto.idMesto";
        citaoci = broker.getAll(new Citalac(), uslov);
    }

    /**
     * Vraća listu svih čitalaca koji su uspešno preuzeti iz baze podataka nakon izvršenja operacije.
     *
     * @return List Lista objekata tipa Citalac.
     */
    public List<Citalac> getCitaoci() {
        return citaoci;
    }
}