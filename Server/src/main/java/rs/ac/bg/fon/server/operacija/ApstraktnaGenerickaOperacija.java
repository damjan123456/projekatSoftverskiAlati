package rs.ac.bg.fon.server.operacija;

import rs.ac.bg.fon.server.broker.DBBroker;
import rs.ac.bg.fon.server.broker.DBBrokerInterfejs;

/**
 * Apstraktna bazna klasa za sve sistemske operacije u aplikaciji.
 * Implementira Template Method obrazac dizajniranja koji definisanim algoritmom
 * upravlja životnim ciklusom baze podataka (povezivanje, pokretanje transakcije, izvršavanje, commit i rollback).
 *
 * @author Damjan
 */
public abstract class ApstraktnaGenerickaOperacija {
    
    /**
     * Referenca ka interfejsu brokera baze podataka, dostupna svim konkretnim sistemskim operacijama.
     */
    protected final DBBrokerInterfejs broker;

    /**
     * Inicijalizuje novu sistemsku operaciju i postavlja konkretnu implementaciju 
     * brokera baze podataka DBBroker.
     */
    public ApstraktnaGenerickaOperacija() {
        this.broker = new DBBroker();
    }
    
    /**
     * Šablonska metoda koja definiše fiksni kostur izvršenja svake sistemske operacije.
     * Obezbeđuje atomičnost i transakcionu sigurnost operacije. Ako se desi bilo kakva greška, 
     * transakcija se poništava (rollback).
     *
     * @param objekat Domenski objekat nad kojim se izvršava operacija.
     * @throws java.lang.Exception Ako validacija preduslova ili sama operacija nad bazom baci izuzetak.
     */
    public final void izvrsi(Object objekat) throws Exception{
        try{
            preduslovi(objekat);
            zaponcniTransakciju();
            izvrsiOperaciju(objekat);
            potvrdiTransakciju();  
        }catch(Exception e){
            ponistiTransakciju();
            throw e;
        }
    }

    /**
     * Apstraktna metoda zadužena za validaciju poslovnih pravila i strukture objekta 
     * pre nego što otpočne baza podataka transakcija.
     *
     * @param objekat Domenski objekat koji se validira.
     * @throws java.lang.Exception Ako objekat ne ispunjava validacione zahteve.
     */
    protected abstract void preduslovi(Object objekat) throws Exception;
    
    /**
     * Apstraktna metoda koja sadrži specifičnu SQL/brokersku logiku konkretne sistemske operacije.
     * Izvršava se isključivo unutar aktivne transakcije.
     *
     * @param objekat Domenski objekat nad kojim se vrši upis/izmena/brisanje.
     * @throws java.lang.Exception Ako dođe do SQL greške.
     */
    protected abstract void izvrsiOperaciju(Object objekat) throws Exception;

    /**
     * Pokreće transakciju otvaranjem veze sa bazom podataka preko brokera.
     */
    private void zaponcniTransakciju() throws Exception {
        broker.connect();
    }

    /**
     * Potvrđuje sve izmene izvršene u bazi podataka tokom operacije i zatvara vezu.
     */
    private void potvrdiTransakciju() throws Exception {
        broker.commit();
    }

    /**
     * Poništava sve privremene izmene rađene tokom transakcije ukoliko je došlo do greške.
     */
    private void ponistiTransakciju() throws Exception {
        broker.rollback();
    }
}