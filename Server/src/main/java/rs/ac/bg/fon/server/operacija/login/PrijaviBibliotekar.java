package rs.ac.bg.fon.server.operacija.login;

import java.util.List;
import rs.ac.bg.fon.zajednicki.model.Bibliotekar;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 * Konkretna sistemska operacija zadužena za autentifikaciju i prijavu bibliotekara na sistem.
 * Preuzima sve zapise iz baze podataka i vrši identifikaciju poklapanjem kredencijala.
 *
 * @author Damjan
 */
public class PrijaviBibliotekar extends ApstraktnaGenerickaOperacija {
    
    /**
     * Objekat bibliotekara koji je uspešno identifikovan nakon prijave, ili null ako prijava ne uspe.
     */
    private Bibliotekar bibliotekar;

    /**
     * Podrazumevani konstruktor klase PrijaviBibliotekar.
     */
    public PrijaviBibliotekar() {
    }

    /**
     * Konstruktor za potrebe testiranja
     */
    public PrijaviBibliotekar(rs.ac.bg.fon.server.broker.DBBrokerInterfejs broker) {
        super(broker);
    }

    /**
     * Proverava da li je prosleđeni objekat sa kredencijalima validan i spreman za dalju obradu.
     */
    @Override
    protected void preduslovi(Object param) throws Exception {
        if (param == null || !(param instanceof Bibliotekar))
            throw new Exception("Prijava nije moguca");
    }

    /**
     * Dobavlja sve bibliotekare iz baze podataka i iteracijom kroz listu pronalazi 
     * onog koji odgovara prosleđenim parametrima.
     */
    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        List<Bibliotekar> bibliotekari = broker.getAll((Bibliotekar)param, null);
        System.out.println("KLASA LoginOperacija SO" + bibliotekari);
        
        for (Bibliotekar b : bibliotekari) {
            if(b.equals((Bibliotekar)param)){
                bibliotekar = b;
                return;
            }
        }
        bibliotekar = null;
    }

    /**
     * Vraća autentifikovanog bibliotekara sa njegovim punim podacima iz baze podataka.
     *
     * @return Bibliotekar Objekat uspešno prijavljenog bibliotekara, ili {@code null}.
     */
    public Bibliotekar getBibliotekar() {
        return bibliotekar;
    }
}