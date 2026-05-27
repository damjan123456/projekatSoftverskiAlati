/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author damja
 */
public class Controller {
    private static Controller instance;

    public Controller() {
    }

    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }

    public Bibliotekar login(Bibliotekar b) throws Exception {
        PrijaviBibliotekar operacija = new PrijaviBibliotekar();
        operacija.izvrsi(b);
        System.out.println("Klasa Controller: " + operacija.getBibliotekar());
        return operacija.getBibliotekar();
    }

    public List<Citalac> vratiCitaoce() throws Exception {
        VratiListuSviCitalac operacija = new VratiListuSviCitalac();
        operacija.izvrsi(null);
        System.out.println("KLasa Controller: " + operacija.getCitaoci());
        return operacija.getCitaoci();
    }

    public void obrisiCitaoca(Citalac par) throws Exception {
        ObrisiCitalac operacija = new ObrisiCitalac();
        operacija.izvrsi(par);
        
    }

    public void dodajSertifikat(Sertifikat sertifikat) throws Exception {
        UbaciSertifikat operacija = new UbaciSertifikat();
        operacija.izvrsi(sertifikat);
        
    }

    public List<Mesto> vratiMesta() throws Exception {
        VratiListuSviMesto operacija = new VratiListuSviMesto();
        operacija.izvrsi(null);
        System.out.println("Klasa Controller: " + operacija.getMesta());
        return operacija.getMesta();
    }

    public void dodajCitaoca(Citalac citalac) throws Exception {
        UbaciCitalac operacija = new UbaciCitalac();
        operacija.izvrsi(citalac);
    }

    public void izmeniCitaoca(Citalac citalac) throws Exception {
        PromeniCitalac operacija = new PromeniCitalac();
        operacija.izvrsi(citalac);
    }

    public List<ZapisOIznajmljivanju> vratiZapise() throws Exception {
        VratiListuZapisOIznajmljivanju operacija = new VratiListuZapisOIznajmljivanju();
        operacija.izvrsi(null);
        System.out.println("KLasa Controller: " + operacija.getZapisi());
        return operacija.getZapisi();    
    }


    public List<Knjiga> vratiKnjige() throws Exception {
        VratiListuSviKnjiga operacija = new VratiListuSviKnjiga();
        operacija.izvrsi(null);
        System.out.println("Klasa Controller: " + operacija.getKnjige());
        return operacija.getKnjige();
    }

    public void kreirajZapisOIznajmljivanju(ZapisOIznajmljivanju zapis) throws Exception {
        KreirajZapisOIznajmljivanju operacija = new KreirajZapisOIznajmljivanju();
        operacija.izvrsi(zapis);
    }

    public void izmeniZapis(ZapisOIznajmljivanju zapis) throws Exception {
        PromeniZapisOIznajmljivanju operacija = new PromeniZapisOIznajmljivanju();
        operacija.izvrsi(zapis);
    }

    public List<Bibliotekar> vratiBibliotekare() throws Exception {
        VratiListuSviBibliotekar operacija = new VratiListuSviBibliotekar();
        operacija.izvrsi(null);
        System.out.println("Klasa Controller: " + operacija.getBibliotekari());
        return operacija.getBibliotekari();
    }

    public ZapisOIznajmljivanju vratiZapis(ZapisOIznajmljivanju zapis) throws Exception {
        PretraziZapisOIznajmljivanju operacija = new PretraziZapisOIznajmljivanju();
        operacija.izvrsi(zapis);
        System.out.println("Klasa Controller: " + operacija.getZapis());
        return operacija.getZapis(); 
    }
    
}
