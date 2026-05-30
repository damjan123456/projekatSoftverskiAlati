package rs.ac.bg.fon.klijent.glavnikontroler;

import rs.ac.bg.fon.klijent.forme.DodajCitaocaForma;
import rs.ac.bg.fon.klijent.forme.DodajSertifikatForma;
import rs.ac.bg.fon.klijent.forme.FormaModovi;
import rs.ac.bg.fon.klijent.forme.GlavnaForma;
import rs.ac.bg.fon.klijent.forme.LoginForma;
import rs.ac.bg.fon.klijent.forme.PrikazCitalacaForma;
import rs.ac.bg.fon.klijent.forme.PrikazZapisaForma;
import java.util.HashMap;
import java.util.Map;
import rs.ac.bg.fon.klijent.kontroleri.DodajCitaocaController;
import rs.ac.bg.fon.klijent.kontroleri.GlavnaFormaController;
import rs.ac.bg.fon.klijent.kontroleri.LoginController;
import rs.ac.bg.fon.klijent.kontroleri.PrikazCitalacaController;
import rs.ac.bg.fon.klijent.kontroleri.DodajSertifikatController;
import rs.ac.bg.fon.klijent.kontroleri.PrikazZapisaController;
import rs.ac.bg.fon.zajednicki.model.Bibliotekar;

/**
 * Glavni kontroler klijentske aplikacije koji upravlja navigacijom, otvaranjem formi i deljenjem podataka.
 * Implementira Singleton obrazac kako bi obezbedio jedinstvenu tačku pristupa kontrolerima i sesiji ulogovanog korisnika.
 * Takođe sadrži mapu parametara za prenos objekata između različitih ekrana.
 * * @author Damjan
 */
public class GlavniKontroler {
    
    /**
     * Jedinstvena statička instanca klase GlavniKontroler.
     */
    private static GlavniKontroler instanca;
    
    /**
     * Trenutno ulogovani bibliotekar na sistemu.
     */
    private Bibliotekar ulogovani;
    
    /**
     * Kontroler zadužen za upravljanje formom za prijavu na sistem.
     */
    private LoginController loginController;
    
    /**
     * Kontroler zadužen za upravljanje glavnom formom aplikacije.
     */
    private GlavnaFormaController glavnaFormaController;
    
    /**
     * Kontroler zadužen za prikaz i pretragu svih čitalaca iz baze.
     */
    private PrikazCitalacaController prikazCitalacaController;
    
    /**
     * Kontroler zadužen za formu za dodavanje sertifikata.
     */
    private DodajSertifikatController sertifikatController;
    
    /**
     * Kontroler zadužen za unos, izmenu i pregled detalja o čitaocu.
     */
    private DodajCitaocaController dodajCitaocaController;
    
    /**
     * Kontroler zadužen za prikaz i pretragu zapisa o iznajmljivanju.
     */
    private PrikazZapisaController prikazZapisaController;
    
    /**
     * Mapa parametara koja služi za deljenje i prenos podataka između različitih kontrolera.
     */
    private Map<String, Object> parametri;
    
    /**
     * Privatni konstruktor koji inicijalizuje mapu za prenos parametara.
     * Onemogućava instanciranje klase van nje same u skladu sa Singleton obrascem.
     */
    private GlavniKontroler() {
        parametri = new HashMap<>();
    }
    
    /**
     * Vraća jedinstvenu instancu klase GlavniKontroler. 
     * Ukoliko instanca još uvek ne postoji, kreira je.
     * @return Jedinstvena instanca ovog kontrolera.
     */
    public static GlavniKontroler getInstanca(){
        if (instanca == null)
            instanca = new GlavniKontroler();
        return instanca;
    }

    /**
     * Kreira novu formu za prijavu na sistem, inicijalizuje pripadajući kontroler i otvara formu.
     */
    public void otvoriLoginFormu() {
        loginController = new LoginController(new LoginForma());
        loginController.otvoriFormu();
    }

    /**
     * Kreira novu glavnu formu aplikacije, inicijalizuje pripadajući kontroler i otvara formu.
     */
    public void otvoriGlavnuFormu() {
        glavnaFormaController = new GlavnaFormaController(new GlavnaForma());
        glavnaFormaController.otvoriFormu();
    }
    
    /**
     * Kreira i prikazuje formu za pregled, pretragu i filtriranje svih čitalaca u biblioteci.
     */
    public void otvoriPrikazCitalacaFormu() {
        prikazCitalacaController = new PrikazCitalacaController(new PrikazCitalacaForma());
        prikazCitalacaController.otvoriFormu();
    }
    
    /**
     * Kreira i prikazuje formu namenjenu dodavanju i evidentiranju novih sertifikata.
     */
    public void otvoriDodajSertifikatFormu() {
        sertifikatController = new DodajSertifikatController(new DodajSertifikatForma());
        sertifikatController.otvoriFormu();
    }
    
    /**
     * Otvara formu za upravljanje čitaocem u modu za dodavanje (unos novog čitaoca u sistem).
     */
    public void otvoriDodajCitaocaFormu() {
        dodajCitaocaController = new DodajCitaocaController(new DodajCitaocaForma());
        dodajCitaocaController.otvoriFormu(FormaModovi.DODAJ);
    }
    
    /**
     * Otvara formu za upravljanje čitaocem u modu za izmenu postojećih podataka o čitaocu.
     */
    public void otvoriIzmeniCitaocaFormu() {
        dodajCitaocaController = new DodajCitaocaController(new DodajCitaocaForma());
        dodajCitaocaController.otvoriFormu(FormaModovi.IZMENI);
    }
    
    /**
     * Otvara formu za upravljanje čitaocem u modu za detaljan pregled podataka (polja su zaključana za izmenu).
     */
    public void otvoriDetaljiCitaocaFormu() {
        dodajCitaocaController = new DodajCitaocaController(new DodajCitaocaForma());
        dodajCitaocaController.otvoriFormu(FormaModovi.DETALJI);
    }
    
    /**
     * Kreira i prikazuje formu namenjenu pregledu i pretrazi svih zapisa o iznajmljivanju knjiga.
     */
    public void otvoriPrikazZapisaFormu() {
        prikazZapisaController = new PrikazZapisaController(new PrikazZapisaForma());
        prikazZapisaController.otvoriFormu();
    }

    /**
     * Vraća trenutno ulogovanog bibliotekara koji je na sesiji.
     * * @return Objekat klase Bibliotekar koji predstavlja aktivnog korisnika.
     */
    public Bibliotekar getUlogovani() {
        return ulogovani;
    }

    /**
     * Postavlja ulogovanog bibliotekara u sesiju nakon uspešne prijave na sistem.
     * * @param ulogovani Bibliotekar koji se prijavio.
     */
    public void setUlogovani(Bibliotekar ulogovani) {
        this.ulogovani = ulogovani;
    }
    
    /**
     * Smešta određeni objekat u mapu zajedničkih parametara pod jedinstvenim ključem.
     * * @param s Ključ pod kojim se objekat pamti.
     * @param o Objekat koji se prenosi i skladišti.
     */
    public void dodajParametar(String s, Object o){
        parametri.put(s, o);
    }
    
    /**
     * Preuzima objekat iz mape zajedničkih parametara na osnovu prosleđenog ključa.
     * @param s Ključ na osnovu kojeg se pretražuje mapa.
     * @return Objekat mapiran pod tim ključem, ili null ukoliko ključ ne postoji.
     */
    public Object vratiParametar(String s){
        return parametri.get(s);
    }

    /**
     * Poziva osvežavanje prikaza unutar kontrolera za prikaz čitalaca (npr. nakon izmene podataka).
     */
    public void osveziFormu() {
        prikazCitalacaController.osvezi();
    }

    /**
     * Alternativna metoda za otvaranje glavne forme aplikacije sa eksplicitnim prosleđivanjem režima rada forme.
     * * @param formaMod Režim rada koji se postavlja za glavnu formu.
     */
    public void otvoriGlavnuFormu(FormaModovi formaMod) {
        glavnaFormaController = new GlavnaFormaController(new GlavnaForma());
        glavnaFormaController.otvoriFormu(formaMod);
    }
}