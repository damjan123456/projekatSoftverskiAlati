package rs.ac.bg.fon.server.operacija.sertifikat;

import rs.ac.bg.fon.zajednicki.model.Sertifikat;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 * Konkretna sistemska operacija koja omogućava trajno skladištenje i unos novog 
 * sertifikata u bazu podataka.
 *
 * @author Damjan
 */
public class UbaciSertifikat extends ApstraktnaGenerickaOperacija {

    /**
     * Podrazumevani konstruktor klase UbaciSertifikat.
     */
    public UbaciSertifikat() {
    }

    /**
     * Validira strukturu i tekstualne atribute objekta sertifikata.
     * Zahteva prisustvo institucije izdavaoca i naziva samog sertifikata.
     */
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Sertifikat)){
            throw new Exception("Sistem ne moze da zapamti sertifikat");
        }
        Sertifikat s = (Sertifikat) objekat;
        if (s.getInstitucija() == null || s.getInstitucija().isEmpty())
            throw new Exception("GRESKA INSTITUCIJA");
        if (s.getNaziv()== null || s.getNaziv().isEmpty())
            throw new Exception("GRESKA NAZIV");
    }

    /**
     * Poziva db brokera radi umetanja validiranog objekta sertifikata u bazu podataka.
     */
    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        broker.add((Sertifikat)objekat);
    }
}