/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.klijent.komunikacija;

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

    public Bibliotekar login(String korisnickoIme, String sifra) {
        Bibliotekar b = new Bibliotekar(korisnickoIme,sifra);
        Zahtev zahtev = new Zahtev(Operacija.LOGIN,b);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        b = (Bibliotekar) odgovor.getOdgovor();
        return b;
    }

    public List<Citalac> vratiCitaoce() {
        List<Citalac> citaoci = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.VRATI_CITAOCE,null);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        citaoci = (List<Citalac>) odgovor.getOdgovor();
        return citaoci;
    }

    public void obrisiCitaoca(Citalac c) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_CITAOCA, c);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getOdgovor() == null)
            System.out.println("Citalac je obrisan");
        else {
            System.out.println("Citalac nije obrisan");
            ((Exception)odgovor.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public void unesiSertifikat(Sertifikat sertifikat) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.UNESI_SERTIFIKAT, sertifikat);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getOdgovor() == null)
            System.out.println("Sertifikat je dodat");
        else {
            System.out.println("Sertifikat nije dodat");
            ((Exception)odgovor.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public List<Mesto> vratiMesta() {
        List<Mesto> mesta = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.VRATI_MESTA,null);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        mesta = (List<Mesto>) odgovor.getOdgovor();
        return mesta;
    }

    public void unesiCitaoca(Citalac citalac) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.UNESI_CITAOCA, citalac);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getOdgovor() == null)
            System.out.println("Citalac je dodat");
        else {
            System.out.println("Citalac nije dodat");
            ((Exception)odgovor.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public void izmeniCitaoca(Citalac citalac) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_CITAOCA, citalac);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getOdgovor() == null){
            System.out.println("Citalac je izmenjen");
            GlavniKontroler.getInstanca().osveziFormu();
        }
        else {
            System.out.println("Citalac nije izmenjen");
            ((Exception)odgovor.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public List<ZapisOIznajmljivanju> vratiZapise() {
        List<ZapisOIznajmljivanju> zapisi = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.VRATI_ZAPISE,null);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        zapisi = (List<ZapisOIznajmljivanju>) odgovor.getOdgovor();
        return zapisi;   
    }

    public List<Knjiga> vratiKnjige() {
        List<Knjiga> knjige = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.VRATI_KNJIGE,null);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        knjige = (List<Knjiga>) odgovor.getOdgovor();
        return knjige;
    }

    public void kreirajZapis(ZapisOIznajmljivanju zapis) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.KREIRAJ_ZAPIS, zapis);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getOdgovor() == null)
            System.out.println("Zapis o iznajmljivanju je dodat");
        else {
            System.out.println("Zapis o iznajmljivanju nije dodat");
            ((Exception)odgovor.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }


    public void izmeniZapis(ZapisOIznajmljivanju zapis) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_ZAPIS, zapis);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        if (odgovor.getOdgovor() == null)
            System.out.println("Zapis je promenjen");
        else {
            System.out.println("Zapis nije promenjen");
            ((Exception)odgovor.getOdgovor()).printStackTrace();
            throw new Exception("Greska");
        }
    }

    public List<Bibliotekar> vratiBibliotekare() {
        List<Bibliotekar> bibliotekari = new ArrayList<>();
        Zahtev zahtev = new Zahtev(Operacija.VRATI_BIBLIOTEKARE,null);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        bibliotekari = (List<Bibliotekar>) odgovor.getOdgovor();
        return bibliotekari;
    }

    public ZapisOIznajmljivanju pretraziZapis(ZapisOIznajmljivanju z) {
        ZapisOIznajmljivanju zapis = new ZapisOIznajmljivanju();
        Zahtev zahtev = new Zahtev(Operacija.VRATI_ZAPIS, z);
        posiljalac.posalji(zahtev);
        
        Odgovor odgovor = (Odgovor) primalac.primi();
        zapis = (ZapisOIznajmljivanju) odgovor.getOdgovor();
        return zapis;
    }
}
