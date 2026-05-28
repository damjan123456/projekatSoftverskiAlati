/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author damja
 */
public class Komunikacija {
    private Socket soket;
    private Posiljalac posiljalac;
    private Primalac primalac;
    private static Komunikacija instanca;
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    public static Komunikacija getInstanca(){
        if (instanca == null)
            instanca = new Komunikacija();
        return instanca;
    }
    
    public void konekcija(){
        try {
            soket = new Socket("localhost",9000);
            posiljalac = new Posiljalac(soket);
            primalac = new Primalac(soket);
        } catch (IOException ex) {
            System.out.println("Server nije povezan");
        }
    }
    
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
