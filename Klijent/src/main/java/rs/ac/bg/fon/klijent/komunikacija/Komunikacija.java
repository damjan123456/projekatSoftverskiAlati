package rs.ac.bg.fon.klijent.komunikacija;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import rs.ac.bg.fon.klijent.glavnikontroler.GlavniKontroler;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.zajednicki.komunikacija.Odgovor;
import rs.ac.bg.fon.zajednicki.komunikacija.Operacija;
import rs.ac.bg.fon.zajednicki.komunikacija.Posiljalac;
import rs.ac.bg.fon.zajednicki.komunikacija.Primalac;
import rs.ac.bg.fon.zajednicki.komunikacija.Zahtev;
import rs.ac.bg.fon.zajednicki.model.Bibliotekar;
import rs.ac.bg.fon.zajednicki.model.Citalac;
import rs.ac.bg.fon.zajednicki.model.Knjiga;
import rs.ac.bg.fon.zajednicki.model.Mesto;
import rs.ac.bg.fon.zajednicki.model.Sertifikat;
import rs.ac.bg.fon.zajednicki.model.ZapisOIznajmljivanju;

/**
 * Klasa zadužena za ostvarivanje mrežne komunikacije između klijentske aplikacije i servera.
 * Implementira Singleton obrazac i koristi TCP sokete za slanje zahteva i primanje odgovora.
 * Podaci se serijalizuju i deserijalizuju u JSON format pomoću Google Gson biblioteke.
 * * @author Damjan
 */
public class Komunikacija {
    
    /**
     * TCP soket preko kojeg se vrši mrežna komunikacija sa serverskom aplikacijom.
     */
    private Socket soket;
    
    /**
     * Komponenta zadužena za slanje podataka na soket u tekstualnom obliku.
     */
    private Posiljalac posiljalac;
    
    /**
     * Komponenta zadužena za čitanje pristiglih podataka sa soketa.
     */
    private Primalac primalac;
    
    /**
     * Jedinstvena statička instanca klase Komunikacija.
     */
    private static Komunikacija instanca;
    
    /**
     * Gson objekat sa predefinisanim formatom datuma ("yyyy-MM-dd") za konverziju objekata u JSON i obrnuto.
     */
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    
    /**
     * Privatni konstruktor usklađen sa Singleton obrascem radi sprečavanja instanciranja van klase.
     */
    private Komunikacija() {}

    /**
     * Vraća jedinstvenu instancu klase Komunikacija. 
     * Ukoliko instanca ne postoji, inicijalizuje se.
     * @return Jedinstvena instanca klase Komunikacija.
     */
    public static Komunikacija getInstanca(){
        if (instanca == null)
            instanca = new Komunikacija();
        return instanca;
    }
    
    /**
     * Pokreće povezivanje na server putem TCP soketa na lokalnoj adresi i portu 9000.
     * Inicijalizuje objekte za slanje i primanje poruka u slučaju uspešnog povezivanja.
     */
    public void konekcija(){
        try {
            soket = new Socket("localhost", 9000);
            posiljalac = new Posiljalac(soket);
            primalac = new Primalac(soket);
        } catch (IOException ex) {
            System.out.println("Server nije povezan");
        }
    }
    
    /**
     * Šalje zahtev za prijavu bibliotekara na sistem i obrađuje odgovor sa servera.
     * @param korisnickoIme Korisničko ime bibliotekara.
     * @param sifra Lozinka bibliotekara.
     * @return Objekat klase Bibliotekar ukoliko su kredencijali ispravni, inače null.
     * @throws Exception Ukoliko server vrati grešku tokom autentifikacije ili komunikacija pukne.
     */
    public Bibliotekar login(String korisnickoIme, String sifra) throws Exception {
        Bibliotekar b = new Bibliotekar(korisnickoIme, sifra);
        Zahtev zahtev = new Zahtev(Operacija.LOGIN, b);
        posiljalac.posalji(gson.toJson(zahtev));
        
        String jsonOdgovor = (String) primalac.primi();
        Odgovor odgovor = gson.fromJson(jsonOdgovor, Odgovor.class);
        
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        }
        if (odgovor.getOdgovor() == null) return null;
        
        String jsonObjekatText = gson.toJson(odgovor.getOdgovor());
        JsonObject jsonObject = JsonParser.parseString(jsonObjekatText).getAsJsonObject();
        return gson.fromJson(jsonObject, Bibliotekar.class);
    }

    /**
     * Šalje zahtev serveru za vraćanje svih čitalaca evidentiranih u sistemu.
     * @return Lista objekata klase Citalac.
     * @throws java.lang.Exception Ukoliko dođe do greške pri čitanju ili izvršavanju operacije na serveru.
     */
    public List<Citalac> vratiCitaoce() throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.VRATI_CITAOCE, null);
        posiljalac.posalji(gson.toJson(zahtev));
        
        String jsonOdgovor = (String) primalac.primi();
        Odgovor odgovor = gson.fromJson(jsonOdgovor, Odgovor.class);
        
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        }
        
        List<Citalac> lista = new ArrayList<>();
        if (odgovor.getOdgovor() != null) {
            String jsonListaText = gson.toJson(odgovor.getOdgovor());
            JsonArray jsonArray = JsonParser.parseString(jsonListaText).getAsJsonArray();
            for (JsonElement element : jsonArray) {
                lista.add(gson.fromJson(element, Citalac.class));
            }
        }
        return lista;
    }

    /**
     * Šalje zahtev za logičko ili fizičko brisanje izabranog čitaoca iz baze podataka.
     * @param c Objekat klase Citalac koji se briše.
     * @throws Exception Ukoliko čitalac ne može biti obrisan.
     */
    public void obrisiCitaoca(Citalac c) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_CITAOCA, c);
        posiljalac.posalji(gson.toJson(zahtev));
        
        String jsonOdgovor = (String) primalac.primi();
        Odgovor odgovor = gson.fromJson(jsonOdgovor, Odgovor.class);
        
        if (odgovor.getGreska() != null) {
            System.out.println("Citalac nije obrisan");
            throw odgovor.getGreska();
        }
        System.out.println("Citalac je obrisan");
    }

    /**
     * Šalje zahtev za evidentiranje i čuvanje novog sertifikata u sistemu.
     * @param sertifikat Objekat klase Sertifikat koji se unosi.
     * @throws Exception Ukoliko unos baze podataka ne uspe ili validacija propadne.
     */
    public void unesiSertifikat(Sertifikat sertifikat) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.UNESI_SERTIFIKAT, sertifikat);
        posiljalac.posalji(gson.toJson(zahtev));
        
        String jsonOdgovor = (String) primalac.primi();
        Odgovor odgovor = gson.fromJson(jsonOdgovor, Odgovor.class);
        
        if (odgovor.getGreska() != null) {
            System.out.println("Sertifikat nije dodat");
            throw odgovor.getGreska();
        }
        System.out.println("Sertifikat je dodat");
    }

    /**
     * Potražuje od servera kompletnu listu mesta učitanih iz baze podataka.
     * @return Lista objekata klase Mesto.
     * @throws Exception Ukoliko nastane problem u komunikaciji sa serverom.
     */
    public List<Mesto> vratiMesta() throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.VRATI_MESTA, null);
        posiljalac.posalji(gson.toJson(zahtev));
        
        String jsonOdgovor = (String) primalac.primi();
        Odgovor odgovor = gson.fromJson(jsonOdgovor, Odgovor.class);
        
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        }
        
        List<Mesto> lista = new ArrayList<>();
        if (odgovor.getOdgovor() != null) {
            String jsonListaText = gson.toJson(odgovor.getOdgovor());
            JsonArray jsonArray = JsonParser.parseString(jsonListaText).getAsJsonArray();
            for (JsonElement element : jsonArray) {
                lista.add(gson.fromJson(element, Mesto.class));
            }
        }
        return lista;
    }

    /**
     * Šalje zahtev za kreiranje i perzistenciju novog čitaoca.
     * @param citalac Objekat novog čitaoca sa unesenim parametrima.
     * @throws Exception Ukoliko server odbije kreiranje usled dupliranih podataka ili greške na bazi.
     */
    public void unesiCitaoca(Citalac citalac) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.UNESI_CITAOCA, citalac);
        posiljalac.posalji(gson.toJson(zahtev));
        
        String jsonOdgovor = (String) primalac.primi();
        Odgovor odgovor = gson.fromJson(jsonOdgovor, Odgovor.class);
        
        if (odgovor.getGreska() != null) {
            System.out.println("Citalac nije dodat");
            throw odgovor.getGreska();
        }
        System.out.println("Citalac je dodat");
    }

    /**
     * Šalje ažurirane podatke o čitaocu serveru i osvežava lokalni prikaz formi nakon uspešne izmene.
     * @param citalac Objekat klase Citalac sa izmenjenim podacima.
     * @throws Exception Ukoliko modifikacija podataka u bazi nije uspešno sprovedena.
     */
    public void izmeniCitaoca(Citalac citalac) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_CITAOCA, citalac);
        posiljalac.posalji(gson.toJson(zahtev));
        
        String jsonOdgovor = (String) primalac.primi();
        Odgovor odgovor = gson.fromJson(jsonOdgovor, Odgovor.class);
        
        if (odgovor.getGreska() != null) {
            System.out.println("Citalac nije izmenjen");
            throw odgovor.getGreska();
        }
        System.out.println("Citalac je izmenjen");
        GlavniKontroler.getInstanca().osveziFormu();
    }

    /**
     * Šalje zahtev za preuzimanje svih zapisa o iznajmljivanju knjiga iz baze podataka.
     * @return Lista svih zapisa o iznajmljivanju.
     * @throws Exception Ukoliko dođe do neuspeha prilikom mrežnog prenosa.
     */
    public List<ZapisOIznajmljivanju> vratiZapise() throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.VRATI_ZAPISE, null);
        posiljalac.posalji(gson.toJson(zahtev));
        
        String jsonOdgovor = (String) primalac.primi();
        Odgovor odgovor = gson.fromJson(jsonOdgovor, Odgovor.class);
        
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        }
        
        List<ZapisOIznajmljivanju> lista = new ArrayList<>();
        if (odgovor.getOdgovor() != null) {
            String jsonListaText = gson.toJson(odgovor.getOdgovor());
            JsonArray jsonArray = JsonParser.parseString(jsonListaText).getAsJsonArray();
            for (JsonElement element : jsonArray) {
                lista.add(gson.fromJson(element, ZapisOIznajmljivanju.class));
            }
        }
        return lista;
    }

    /**
     * Potražuje sa servera celokupan spisak knjiga raspoloživih u fondu biblioteke.
     * @return Lista objekata klase Knjiga.
     * @throws Exception Ukoliko obrada sistemske operacije na serveru puca.
     */
    public List<Knjiga> vratiKnjige() throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.VRATI_KNJIGE, null);
        posiljalac.posalji(gson.toJson(zahtev));
        
        String jsonOdgovor = (String) primalac.primi();
        Odgovor odgovor = gson.fromJson(jsonOdgovor, Odgovor.class);
        
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        }
        
        List<Knjiga> lista = new ArrayList<>();
        if (odgovor.getOdgovor() != null) {
            String jsonListaText = gson.toJson(odgovor.getOdgovor());
            JsonArray jsonArray = JsonParser.parseString(jsonListaText).getAsJsonArray();
            for (JsonElement element : jsonArray) {
                lista.add(gson.fromJson(element, Knjiga.class));
            }
        }
        return lista;
    }

    /**
     * Šalje zahtev za kreiranje novog krovnog zapisa o iznajmljivanju zajedno sa pripadajućim stavkama.
     * @param zapis Glavni objekat zapisa o iznajmljivanju koji se kreira.
     * @throws Exception Ukoliko validacija transakcije na serverskoj strani zakaže.
     */
    public void kreirajZapis(ZapisOIznajmljivanju zapis) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.KREIRAJ_ZAPIS, zapis);
        posiljalac.posalji(gson.toJson(zahtev));
        
        String jsonOdgovor = (String) primalac.primi();
        Odgovor odgovor = gson.fromJson(jsonOdgovor, Odgovor.class);
        
        if (odgovor.getGreska() != null) {
            System.out.println("Zapis o iznajmljivanju nije dodat");
            throw odgovor.getGreska();
        }
        System.out.println("Zapis o iznajmljivanju je dodat");
    }

    /**
     * Šalje izmenjeni zapis o iznajmljivanju (i njegove ažurirane stavke) na trajno čuvanje.
     * @param zapis Modifikovani objekat klase ZapisOIznajmljivanju.
     * @throws Exception Ukoliko nastane greška pri ažuriranju podataka u skladištu.
     */
    public void izmeniZapis(ZapisOIznajmljivanju zapis) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_ZAPIS, zapis);
        posiljalac.posalji(gson.toJson(zahtev));
        
        String jsonOdgovor = (String) primalac.primi();
        Odgovor odgovor = gson.fromJson(jsonOdgovor, Odgovor.class);
        
        if (odgovor.getGreska() != null) {
            System.out.println("Zapis nije promenjen");
            throw odgovor.getGreska();
        }
        System.out.println("Zapis je promenjen");
    }

    /**
     * Potražuje listu svih registrovanih bibliotekara u sistemu.
     * @return Lista objekata klase Bibliotekar.
     * @throws Exception Ukoliko operacija ne prođe uspešno na serverskoj strani.
     */
    public List<Bibliotekar> vratiBibliotekare() throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.VRATI_BIBLIOTEKARE, null);
        posiljalac.posalji(gson.toJson(zahtev));
        
        String jsonOdgovor = (String) primalac.primi();
        Odgovor odgovor = gson.fromJson(jsonOdgovor, Odgovor.class);
        
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        }
        
        List<Bibliotekar> lista = new ArrayList<>();
        if (odgovor.getOdgovor() != null) {
            String jsonListaText = gson.toJson(odgovor.getOdgovor());
            JsonArray jsonArray = JsonParser.parseString(jsonListaText).getAsJsonArray();
            for (JsonElement element : jsonArray) {
                lista.add(gson.fromJson(element, Bibliotekar.class));
            }
        }
        return lista;
    }

    /**
     * Šalje delimično popunjen objekat zapisa radi detaljne pretrage i učitavanja svih njegovih zavisnih stavki sa servera.
     * @param z Objekat zapisa koji služi kao kriterijum pretrage (obično sadrži ID).
     * @return Kompletan i detaljno učitan objekat klase ZapisOIznajmljivanju sa svim stavkama, ili null.
     * @throws Exception Ukoliko dođe do greške na serveru prilikom sklapanja objekta iz baze.
     */
    public ZapisOIznajmljivanju pretraziZapis(ZapisOIznajmljivanju z) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.VRATI_ZAPIS, z);
        posiljalac.posalji(gson.toJson(zahtev));
        
        String jsonOdgovor = (String) primalac.primi();
        Odgovor odgovor = gson.fromJson(jsonOdgovor, Odgovor.class);
        
        if (odgovor.getGreska() != null) {
            throw odgovor.getGreska();
        }
        if (odgovor.getOdgovor() == null) return null;
        
        String jsonObjekatText = gson.toJson(odgovor.getOdgovor());
        JsonObject jsonObject = JsonParser.parseString(jsonObjekatText).getAsJsonObject();
        return gson.fromJson(jsonObject, ZapisOIznajmljivanju.class);
    }
}