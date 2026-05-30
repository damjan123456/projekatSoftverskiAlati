package rs.ac.bg.fon.server.operacija.zapis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rs.ac.bg.fon.zajednicki.model.StavkaZapisaOIznajmljivanju;
import rs.ac.bg.fon.zajednicki.model.ZapisOIznajmljivanju;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 * Konkretna sistemska operacija zadužena za izmenu postojećeg zapisa o iznajmljivanju.
 * Pored ažuriranja krovnog dokumenta, klasa sprovodi naprednu sinhronizaciju stavki 
 * poređenjem trenutnog stanja u bazi sa novim stanjem (dodavanje novih, 
 * ažuriranje izmenjenih i brisanje uklonjenih stavki).
 *
 * @author damja
 */
public class PromeniZapisOIznajmljivanju extends ApstraktnaGenerickaOperacija {

    /**
     * Podrazumevani konstruktor klase PromeniZapisOIznajmljivanju.
     */
    public PromeniZapisOIznajmljivanju() {
    }

    /**
     * Proverava preduslove za izmenu zapisa o iznajmljivanju.
     * Zahteva ispravnost datuma, ukupnog iznosa, asociranog bibliotekara i čitaoca.
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
     * Sinhronizuje stanje zapisa i stavki u bazi sa prosleđenim stanjem.
     * Učitava stare stavke, mapira ih, a zatim kroz iteraciju novih stavki odlučuje 
     * koje stavke idu na UPDATE, koje na INSERT, dok preostale stare stavke briše.
     */
    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        ZapisOIznajmljivanju zapis = (ZapisOIznajmljivanju)objekat;
        broker.edit(zapis);

        String uslov = " JOIN knjiga ON stavkazapisaoiznajmljivanju.idKnjiga=knjiga.idKnjiga WHERE stavkazapisaoiznajmljivanju.idZapis=" + zapis.getIdZapis();
        List<StavkaZapisaOIznajmljivanju> stareStavke = broker.getAll(new StavkaZapisaOIznajmljivanju(), uslov);

        Map<String, StavkaZapisaOIznajmljivanju> mapaStarih = new HashMap<>();
        for (StavkaZapisaOIznajmljivanju s : stareStavke) {
            String key = s.getZapis() + "-" + s.getRb(); 
            mapaStarih.put(key, s);
        }

        for (StavkaZapisaOIznajmljivanju nova : zapis.getStavke()) {
            nova.setZapis(zapis.getIdZapis());
            String key = nova.getZapis() + "-" + nova.getRb();
            if (mapaStarih.containsKey(key)) {
                broker.edit(nova);
                mapaStarih.remove(key);
            } else {
                broker.add(nova);
            }
        }

        for (StavkaZapisaOIznajmljivanju s : mapaStarih.values()) {
            broker.delete(s);
        }
    }
}