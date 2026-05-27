/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author damja
 */
public class GlavniKontroler {
    private static GlavniKontroler instanca;
    private Bibliotekar ulogovani;
    private LoginController loginController;
    private GlavnaFormaController glavnaFormaController;
    private PrikazCitalacaController prikazCitalacaController;
    private DodajSertifikatController sertifikatController;
    private DodajCitaocaController dodajCitaocaController;
    private PrikazZapisaController prikazZapisaController;
    private Map<String,Object> parametri;
    
    private GlavniKontroler() {
        parametri = new HashMap<>();
    }
    public static GlavniKontroler getInstanca(){
        if (instanca == null)
            instanca = new GlavniKontroler();
        return instanca;
    }

    public void otvoriLoginFormu() {
        loginController = new LoginController(new LoginForma());
        loginController.otvoriFormu();
                
    }

    public void otvoriGlavnuFormu() {
        glavnaFormaController = new GlavnaFormaController(new GlavnaForma());
        glavnaFormaController.otvoriFormu();
    }
    
    public void otvoriPrikazCitalacaFormu() {
        prikazCitalacaController = new PrikazCitalacaController(new PrikazCitalacaForma());
        prikazCitalacaController.otvoriFormu();
    }
    
    public void otvoriDodajSertifikatFormu() {
        sertifikatController = new DodajSertifikatController(new DodajSertifikatForma());
        sertifikatController.otvoriFormu();
    }
    
    public void otvoriDodajCitaocaFormu() {
        dodajCitaocaController = new DodajCitaocaController(new DodajCitaocaForma());
        dodajCitaocaController.otvoriFormu(FormaModovi.DODAJ);
    }
    
    public void otvoriIzmeniCitaocaFormu() {
        dodajCitaocaController = new DodajCitaocaController(new DodajCitaocaForma());
        dodajCitaocaController.otvoriFormu(FormaModovi.IZMENI);
    }
    
    public void otvoriDetaljiCitaocaFormu() {
        dodajCitaocaController = new DodajCitaocaController(new DodajCitaocaForma());
        dodajCitaocaController.otvoriFormu(FormaModovi.DETALJI);
    }
    
    public void otvoriPrikazZapisaFormu() {
        prikazZapisaController = new PrikazZapisaController(new PrikazZapisaForma());
        prikazZapisaController.otvoriFormu();
    }


    public Bibliotekar getUlogovani() {
        return ulogovani;
    }

    public void setUlogovani(Bibliotekar ulogovani) {
        this.ulogovani = ulogovani;
    }
    
    public void dodajParametar(String s, Object o){
        parametri.put(s, o);
    }
    
    public Object vratiParametar(String s){
        return parametri.get(s);
    }

    public void osveziFormu() {
        prikazCitalacaController.osvezi();
    }

    public void otvoriGlavnuFormu(FormaModovi formaMod) {
        glavnaFormaController = new GlavnaFormaController(new GlavnaForma());
        glavnaFormaController.otvoriFormu(formaMod);
    }

    

}
