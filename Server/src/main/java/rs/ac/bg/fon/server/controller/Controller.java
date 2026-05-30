package rs.ac.bg.fon.server.controller;

import java.util.List;
import rs.ac.bg.fon.zajednicki.model.Bibliotekar;
import rs.ac.bg.fon.zajednicki.model.Citalac;
import rs.ac.bg.fon.zajednicki.model.Knjiga;
import rs.ac.bg.fon.zajednicki.model.Mesto;
import rs.ac.bg.fon.zajednicki.model.Sertifikat;
import rs.ac.bg.fon.zajednicki.model.ZapisOIznajmljivanju;
import rs.ac.bg.fon.server.operacija.bibliotekar.VratiListuSviBibliotekar;
import rs.ac.bg.fon.server.operacija.zapis.VratiListuZapisOIznajmljivanju;
import rs.ac.bg.fon.server.operacija.citalac.UbaciCitalac;
import rs.ac.bg.fon.server.operacija.citalac.PromeniCitalac;
import rs.ac.bg.fon.server.operacija.citalac.ObrisiCitalac;
import rs.ac.bg.fon.server.operacija.citalac.VratiListuSviCitalac;
import rs.ac.bg.fon.server.operacija.knjiga.VratiListuSviKnjiga;
import rs.ac.bg.fon.server.operacija.login.PrijaviBibliotekar;
import rs.ac.bg.fon.server.operacija.mesto.VratiListuSviMesto;
import rs.ac.bg.fon.server.operacija.sertifikat.UbaciSertifikat;
import rs.ac.bg.fon.server.operacija.zapis.PromeniZapisOIznajmljivanju;
import rs.ac.bg.fon.server.operacija.zapis.KreirajZapisOIznajmljivanju;
import rs.ac.bg.fon.server.operacija.zapis.PretraziZapisOIznajmljivanju;

/**
 * Centralni kontroler aplikacione logike na serverskoj strani.
 * Implementiran je kao Singleton i služi kao posrednik između serverskih niti zaduženih za 
 * komunikaciju sa klijentima i konkretnih sistemskih operacija koje izvršavaju transakcije nad bazom.
 *
 * @author Damjan
 */
public class Controller {
    
    /**
     * Jedinstvena statička instanca klase Controller.
     */
    private static Controller instance;

    /**
     * Podrazumevani konstruktor klase Controller.
     */
    public Controller() {
    }

    /**
     * Vraća jedinstvenu instancu klase Controller. Ukoliko instanca ne postoji, 
     * inicijalizuje je.
     *
     * @return Controller Jedinstvena instanca serverskog kontrolera.
     */
    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }

    /**
     * Pokreće sistemsku operaciju za prijavu bibliotekara na sistem.
     *
     * @param b Objekat klase Bibliotekar sa unetim kredencijalima.
     * @return Bibliotekar Objekat sa punim podacima o uspešno prijavljenom bibliotekaru.
     * @throws java.lang.Exception Ako prijava ne uspe ili dođe do greške tokom izvršavanja operacije.
     */
    public Bibliotekar login(Bibliotekar b) throws Exception {
        PrijaviBibliotekar operacija = new PrijaviBibliotekar();
        operacija.izvrsi(b);
        System.out.println("Klasa Controller: " + operacija.getBibliotekar());
        return operacija.getBibliotekar();
    }

    /**
     * Pokreće sistemsku operaciju koja preuzima i vraća listu svih čitalaca iz baze podataka.
     *
     * @return List Lista svih registovanih čitalaca.
     * @throws java.lang.Exception Ako dođe do greške prilikom čitanja podataka.
     */
    public List<Citalac> vratiCitaoce() throws Exception {
        VratiListuSviCitalac operacija = new VratiListuSviCitalac();
        operacija.izvrsi(null);
        System.out.println("KLasa Controller: " + operacija.getCitaoci());
        return operacija.getCitaoci();
    }

    /**
     * Pokreće sistemsku operaciju za logičko ili fizičko brisanje čitaoca iz baze.
     *
     * @param par Objekat klase Citalac koji identifikuje zapis namenjen brisanju.
     * @throws java.lang.Exception Ako sistem ne uspe da obriše izabranog čitaoca.
     */
    public void obrisiCitaoca(Citalac par) throws Exception {
        ObrisiCitalac operacija = new ObrisiCitalac();
        operacija.izvrsi(par);
    }

    /**
     * Pokreće sistemsku operaciju za dodavanje i registrovanje novog sertifikata u bazi podataka.
     *
     * @param sertifikat Objekat klase Sertifikat koji se unosi.
     * @throws java.lang.Exception Ako dođe do greške pri upisu sertifikata.
     */
    public void dodajSertifikat(Sertifikat sertifikat) throws Exception {
        UbaciSertifikat operacija = new UbaciSertifikat();
        operacija.izvrsi(sertifikat);
    }

    /**
     * Pokreće sistemsku operaciju koja vraća listu svih mesta definisanih u sistemu.
     *
     * @return List Lista objekata tipa Mesto.
     * @throws java.lang.Exception Ako dođe do greške pri čitanju mesta iz baze.
     */
    public List<Mesto> vratiMesta() throws Exception {
        VratiListuSviMesto operacija = new VratiListuSviMesto();
        operacija.izvrsi(null);
        System.out.println("Klasa Controller: " + operacija.getMesta());
        return operacija.getMesta();
    }

    /**
     * Pokreće sistemsku operaciju za kreiranje i perzistenciju novog čitaoca.
     *
     * @param citalac Objekat klase Citalac koji se registruje u sistemu.
     * @throws java.lang.Exception Ako dodavanje čitaoca ne uspe.
     */
    public void dodajCitaoca(Citalac citalac) throws Exception {
        UbaciCitalac operacija = new UbaciCitalac();
        operacija.izvrsi(citalac);
    }

    /**
     * Pokreće sistemsku operaciju za trajno ažuriranje podataka postojećeg čitaoca u bazi podataka.
     *
     * @param citalac Objekat klase Citalac sa izmenjenim vrednostima.
     * @throws java.lang.Exception Ako izmena podataka nad čitaocem propadne.
     */
    public void izmeniCitaoca(Citalac citalac) throws Exception {
        PromeniCitalac operacija = new PromeniCitalac();
        operacija.izvrsi(citalac);
    }

    /**
     * Pokreće sistemsku operaciju koja učitava i vraća sve zapise o iznajmljivanju knjiga iz baze podataka.
     *
     * @return List Lista svih istorijskih i aktivnih zapisa o iznajmljivanju.
     * @throws java.lang.Exception Ako dođe do greške pri preuzimanju zapisa.
     */
    public List<ZapisOIznajmljivanju> vratiZapise() throws Exception {
        VratiListuZapisOIznajmljivanju operacija = new VratiListuZapisOIznajmljivanju();
        operacija.izvrsi(null);
        System.out.println("KLasa Controller: " + operacija.getZapisi());
        return operacija.getZapisi();    
    }

    /**
     * Pokreće sistemsku operaciju koja preuzima i vraća listu svih raspoloživih knjiga iz fonda biblioteke.
     *
     * @return List Lista svih knjiga zabeleženih u sistemu.
     * @throws java.lang.Exception Ako preuzimanje liste knjiga ne uspe.
     */
    public List<Knjiga> vratiKnjige() throws Exception {
        VratiListuSviKnjiga operacija = new VratiListuSviKnjiga();
        operacija.izvrsi(null);
        System.out.println("Klasa Controller: " + operacija.getKnjige());
        return operacija.getKnjige();
    }

    /**
     * Pokreće kompleksnu sistemsku operaciju za kreiranje novog krovnog zapisa o iznajmljivanju i njegovih pripadajućih stavki.
     *
     * @param zapis Objekat klase ZapisOIznajmljivanju koji sadrži i listu stavki za skladištenje.
     * @throws java.lang.Exception Ako kreiranje celokupnog zapisa padne na nivou transakcije.
     */
    public void kreirajZapisOIznajmljivanju(ZapisOIznajmljivanju zapis) throws Exception {
        KreirajZapisOIznajmljivanju operacija = new KreirajZapisOIznajmljivanju();
        operacija.izvrsi(zapis);
    }

    /**
     * Pokreće sistemsku operaciju za izmenu i ažuriranje postojećeg zapisa o iznajmljivanju i modifikaciju njegovih stavki.
     *
     * @param zapis ZapisOIznajmljivanju sa novim podacima spreman za obradu u bazi.
     * @throws java.lang.Exception Ako dođe do greške pri modifikovanju zapisa.
     */
    public void izmeniZapis(ZapisOIznajmljivanju zapis) throws Exception {
        PromeniZapisOIznajmljivanju operacija = new PromeniZapisOIznajmljivanju();
        operacija.izvrsi(zapis);
    }

    /**
     * Pokreće sistemsku operaciju koja preuzima i vraća listu svih bibliotekara registrovanih na sistemu.
     *
     * @return List Lista svih administratora/bibliotekara.
     * @throws java.lang.Exception Ako vađenje podataka o bibliotekarima iz baze ne uspe.
     */
    public List<Bibliotekar> vratiBibliotekare() throws Exception {
        VratiListuSviBibliotekar operacija = new VratiListuSviBibliotekar();
        operacija.izvrsi(null);
        System.out.println("Klasa Controller: " + operacija.getBibliotekari());
        return operacija.getBibliotekari();
    }

    /**
     * Pokreće sistemsku operaciju detaljne pretrage i ugovaranja objekta zapisa, popunjavajući ga sa svim povezanim zavisnim objektima (stavkama).
     *
     * @param zapis Objekat zapisa koji služi kao filter ili sadrži ID za pretragu.
     * @return ZapisOIznajmljivanju Detaljan i kompletno učitan objekat zapisa o iznajmljivanju sa pripadajućim stavkama.
     * @throws java.lang.Exception Ako pretraga i sklapanje objekta zapisa ne uspeju.
     */
    public ZapisOIznajmljivanju vratiZapis(ZapisOIznajmljivanju zapis) throws Exception {
        PretraziZapisOIznajmljivanju operacija = new PretraziZapisOIznajmljivanju();
        operacija.izvrsi(zapis);
        System.out.println("Klasa Controller: " + operacija.getZapis());
        return operacija.getZapis(); 
    }
}