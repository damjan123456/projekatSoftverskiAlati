package rs.ac.bg.fon.server.operacija.citalac;

import rs.ac.bg.fon.zajednicki.model.Citalac;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 * Konkretna sistemska operacija koja realizuje brisanje postojećeg čitaoca iz baze podataka.
 * Nasleđuje ApstraktnaGenerickaOperacija i sprovodi korake validacije i brisanja 
 * kroz mehanizam šablonske metode.
 *
 * @author Damjan
 */
public class ObrisiCitalac extends ApstraktnaGenerickaOperacija {

    /**
     * Podrazumevani konstruktor klase ObrisiCitalac.
     */
    public ObrisiCitalac() {
    }

    /**
     * Proverava preduslove za izvršenje operacije brisanja čitaoca.
     * Zahteva da prosleđeni parametar ne bude null i da bude instanca klase Citalac.
     *
     * @param param Objekat nad kojim se proveravaju preduslovi.
     * @throws java.lang.Exception Ako je parametar null ili nije instanca klase Citalac.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Citalac)){
            throw new Exception("Sistem ne moze da obrise citaoca");
        }
    }

    /**
     * Izvršava bazičnu brokersku operaciju brisanja zapisa o čitaocu iz baze podataka.
     *
     * @param objekat Objekat klase Citalac koji se briše.
     * @throws java.lang.Exception Ako dođe do greške na nivou baze podataka prilikom brisanja.
     */
    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        broker.delete((Citalac)objekat);
    }
}