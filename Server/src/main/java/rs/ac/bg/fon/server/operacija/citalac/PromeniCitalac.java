package rs.ac.bg.fon.server.operacija.citalac;

import rs.ac.bg.fon.zajednicki.model.Citalac;
import rs.ac.bg.fon.server.broker.DBBrokerInterfejs;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 * Konkretna sistemska operacija zadužena za izmenu i ažuriranje podataka o postojećem čitaocu.
 * Vrši rigoroznu validaciju svih tekstualnih i strukturnih atributa objekta pre perzistencije u bazu.
 *
 * @author Damjan
 */
public class PromeniCitalac extends ApstraktnaGenerickaOperacija {

    /**
     * Podrazumevani konstruktor klase PromeniCitalac.
     */
    public PromeniCitalac() {
    }
    /**
     * Konstruktor za potrebe testiranja
     */
    public PromeniCitalac(DBBrokerInterfejs broker) {
        super(broker);
    }

    /**
     * Validira podatke o čitaocu pre nego što se proslede bazi na ažuriranje.
     * Proverava postojanje objekta, kao i ispravnost imena, prezimena, broja telefona i pripadajućeg mesta.
     *
     * @param objekat Objekat čitaoca čiji se podaci proveravaju.
     * @throws java.lang.Exception Ako objekat nije validan ili ako neki od obaveznih atributa ne ispunjava validacione kriterijume.
     */
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Citalac)){
            throw new Exception("Sistem ne moze da izmeni citaoca");
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
     * Poziva brokera baze podataka da izvrši izmenu nad prosleđenim entitetom čitaoca.
     *
     * @param objekat Objekat klase Citalac sa ažuriranim vrednostima.
     * @throws java.lang.Exception Ako ažuriranje u bazi podataka ne uspe.
     */
    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        broker.edit((Citalac)objekat);
    }
}