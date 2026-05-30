package rs.ac.bg.fon.server.operacija.citalac;

import rs.ac.bg.fon.zajednicki.model.Citalac;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 * Konkretna sistemska operacija koja omogućava registraciju i unos novog čitaoca u sistem.
 * Validira atribute entiteta pre poziva brokerske metode za dodavanje zapisa.
 *
 * @author Damjan
 */
public class UbaciCitalac extends ApstraktnaGenerickaOperacija {

    /**
     * Podrazumevani konstruktor klase UbaciCitalac.
     */
    public UbaciCitalac() {
    }

    /**
     * Validira atribute novog čitaoca pre upisa u bazu podataka.
     * Proverava tip objekta, prisustvo imena, prezimena, ispravnost dužine broja telefona, kao i postojanje objekta mesta.
     *
     * @param objekat Objekat čitaoca koji se unosi u sistem.
     * @throws java.lang.Exception Ako podaci o čitaocu nisu kompletni ili narušavaju poslovna pravila.
     */
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Citalac)){
            throw new Exception("Sistem ne moze da doda citaoca");
        } 
        Citalac c = (Citalac) objekat;
        if (c.getIme() == null || c.getIme().isEmpty())
            throw new Exception("GRESKA IME");
        if (c.getPrezime()== null || c.getPrezime().isEmpty())
            throw new Exception("GRESKA PREZIME");
        if (c.getBrojTel()== null || c.getBrojTel().isEmpty() || c.getBrojTel().length() >10)
            throw new Exception("GRESKA BROJ TELEFONA");
        if (c.getMesto()== null)
            throw new Exception("GRESKA MESTO");
    }

    /**
     * Prosleđuje validirani objekat čitaoca brokeru radi trajnog skladištenja u bazi podataka.
     *
     * @param objekat Objekat klase Citalac koji se unosi.
     * @throws java.lang.Exception Ako upis u bazu podataka generiše grešku.
     */
    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        broker.add((Citalac)objekat);
    }
}