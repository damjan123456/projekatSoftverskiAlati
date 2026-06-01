package rs.ac.bg.fon.server.operacija.zapis;

import java.util.List;
import rs.ac.bg.fon.zajednicki.model.StavkaZapisaOIznajmljivanju;
import rs.ac.bg.fon.zajednicki.model.ZapisOIznajmljivanju;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 * Konkretna sistemska operacija koja pronalazi, učitava i kompletira specifičan zapis 
 * o iznajmljivanju. Vrši SQL JOIN spajanje kako bi povukla sve stavke tog zapisa sa detaljima o knjigama.
 *
 * @author Damjan
 */
public class PretraziZapisOIznajmljivanju extends ApstraktnaGenerickaOperacija {
    
    /**
     * Objekat zapisa o iznajmljivanju čije se stavke kompletiraju i pune podacima.
     */
    private ZapisOIznajmljivanju zapis;

    /**
     * Podrazumevani konstruktor klase PretraziZapisOIznajmljivanju.
     */
    public PretraziZapisOIznajmljivanju() {
    }

    /**
    * Konstruktor za potrebe testiranja
    */
    public PretraziZapisOIznajmljivanju(rs.ac.bg.fon.server.broker.DBBrokerInterfejs broker) {
        super(broker);
    }

    /**
     * Validira parametre pretrage i proverava prisustvo ključnih identifikacionih i opisnih polja zapisa.
     */
    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof ZapisOIznajmljivanju)){
            throw new Exception("Sistem ne moze da doda zapis o iznajmljivanju");
        } 
        ZapisOIznajmljivanju z = (ZapisOIznajmljivanju) objekat;
        if (z.getDatumIznajmljivanja()== null)
            throw new Exception("GRESKA DATUM");
        if (z.getUkupanIznos() <0)
            throw new Exception("GRESKA UKUPAN IZNOS");
        if (z.getBibliotekar()== null || z.getBibliotekar().getIdBibliotekar() < 0)
            throw new Exception("GRESKA BIBLIOTEKAR");
        if (z.getCitalac()== null || z.getCitalac().getIdCitalac() < 0)
            throw new Exception("GRESKA CITALAC");    
    }

    /**
     * Formira SQL JOIN upit sa tabelom knjiga za ID tekućeg zapisa, izvlači sve stavke i 
     * postavlja ih unutar krovnog objekta zapisa.
     */
    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        zapis = (ZapisOIznajmljivanju) objekat;
        String uslov = " JOIN knjiga ON stavkazapisaoiznajmljivanju.idKnjiga=knjiga.idKnjiga WHERE stavkazapisaoiznajmljivanju.idZapis="+ zapis.getIdZapis();
        List<StavkaZapisaOIznajmljivanju> stavke = broker.getAll(new StavkaZapisaOIznajmljivanju(), uslov);
        zapis.setStavke(stavke);
    }

    /**
     * Vraća kompletiran objekat zapisa o iznajmljivanju sa učitanom listom pripadajućih stavki.
     *
     * @return ZapisOIznajmljivanju Objekat zapisa bogat detaljima i stavkama.
     */
    public ZapisOIznajmljivanju getZapis() {
        return zapis;
    }
}