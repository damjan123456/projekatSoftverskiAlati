package rs.ac.bg.fon.server.operacija.zapis;

import java.util.List;
import rs.ac.bg.fon.zajednicki.model.StavkaZapisaOIznajmljivanju;
import rs.ac.bg.fon.zajednicki.model.ZapisOIznajmljivanju;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 * Konkretna sistemska operacija zadužena za kreiranje i perzistenciju novog dokumenta/zapisa 
 * o iznajmljivanju knjiga, uključujući i sve pripadajuće stavke unutar jedne transakcije.
 *
 * @author damja
 */
public class KreirajZapisOIznajmljivanju extends ApstraktnaGenerickaOperacija {

    /**
     * Podrazumevani konstruktor klase KreirajZapisOIznajmljivanju.
     */
    public KreirajZapisOIznajmljivanju() {
    }

    /**
     * Konstruktor za potrebe testiranja
     */
    public KreirajZapisOIznajmljivanju(rs.ac.bg.fon.server.broker.DBBrokerInterfejs broker) {
        super(broker);
    }

    /**
     * Proverava strukturne elemente i poslovna pravila nad zapisom o iznajmljivanju.
     * Proverava datum, iznos, validnost asocijacije ka bibliotekaru i klijentu.
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
     * Upisuje krovni zapis u bazu podataka, preuzima generisani auto-increment ključ, 
     * a zatim taj ključ vezuje za svaku pojedinačnu stavku pre upisa u tabelu stavki.
     */
    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        ZapisOIznajmljivanju zapis = (ZapisOIznajmljivanju)objekat;
        int idZapis = broker.addReturnKey(objekat);
        
        List<StavkaZapisaOIznajmljivanju> stavke = zapis.getStavke();
        
        for (StavkaZapisaOIznajmljivanju s : stavke) {
            s.setZapis(idZapis);
            broker.add(s);
        }
    }
}