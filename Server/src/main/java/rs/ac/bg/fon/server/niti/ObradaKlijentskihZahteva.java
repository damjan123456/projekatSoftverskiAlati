/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.server.niti;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import rs.ac.bg.fon.server.controller.Controller;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.zajednicki.komunikacija.Odgovor;
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
public class ObradaKlijentskihZahteva extends Thread {
    private final Socket socket;
    private final Posiljalac posiljalac;
    private final Primalac primalac;
    private boolean kraj = false;

    public ObradaKlijentskihZahteva(Socket socket) {
        this.socket = socket;
        posiljalac = new Posiljalac(socket);
        primalac = new Primalac(socket);
    }

    @Override
    public void run() {
        Gson gson = new GsonBuilder().setDateFormat("yyy-MM-dd").create();

        while(!kraj){
            try {   
                String jsonZahtev = (String) primalac.primi();
                if (jsonZahtev == null){
                    prekiniNit();
                    break;
                }
                Zahtev zahtev = gson.fromJson(jsonZahtev, Zahtev.class);
                Odgovor odgovor = new Odgovor();

                switch(zahtev.getOperacija()){
                    case LOGIN -> {
                        try {
                            String jsonParam = gson.toJson(zahtev.getParametar());
                            JsonObject jsonObject = JsonParser.parseString(jsonParam).getAsJsonObject();
                            Bibliotekar b = gson.fromJson(jsonObject, Bibliotekar.class);

                            b = Controller.getInstance().login(b);
                            odgovor.setOdgovor(b);
                        } catch (Exception e) {
                            odgovor.setGreska(e);
                        }
                    }
                    case VRATI_CITAOCE -> {
                        try {
                            List<Citalac> citaoci = Controller.getInstance().vratiCitaoce();
                            odgovor.setOdgovor(citaoci);
                        } catch (Exception e) {
                            odgovor.setGreska(e);
                        }
                    }
                    case OBRISI_CITAOCA -> {
                        try {
                            String jsonParam = gson.toJson(zahtev.getParametar());
                            JsonObject jsonObject = JsonParser.parseString(jsonParam).getAsJsonObject();
                            Citalac citalac = gson.fromJson(jsonObject, Citalac.class);

                            Controller.getInstance().obrisiCitaoca(citalac);
                            odgovor.setOdgovor(null);
                        } catch(Exception e) {
                            odgovor.setGreska(e);
                        }
                    }
                    case UNESI_SERTIFIKAT -> {
                        try {
                            String jsonParam = gson.toJson(zahtev.getParametar());
                            JsonObject jsonObject = JsonParser.parseString(jsonParam).getAsJsonObject();
                            Sertifikat sertifikat = gson.fromJson(jsonObject, Sertifikat.class);

                            Controller.getInstance().dodajSertifikat(sertifikat);
                            odgovor.setOdgovor(null);
                        } catch(Exception e) {
                            odgovor.setGreska(e);
                        }
                    }
                    case VRATI_MESTA -> {
                        try {
                            List<Mesto> mesta = Controller.getInstance().vratiMesta();
                            odgovor.setOdgovor(mesta);
                        } catch (Exception e) {
                            odgovor.setGreska(e);
                        }
                    }
                    case UNESI_CITAOCA -> {
                        try {
                            String jsonParam = gson.toJson(zahtev.getParametar());
                            JsonObject jsonObject = JsonParser.parseString(jsonParam).getAsJsonObject();
                            Citalac citalac = gson.fromJson(jsonObject, Citalac.class);

                            Controller.getInstance().dodajCitaoca(citalac);
                            odgovor.setOdgovor(null);
                        } catch(Exception e) {
                            odgovor.setGreska(e);
                        }
                    }
                    case IZMENI_CITAOCA -> {
                        try {
                            String jsonParam = gson.toJson(zahtev.getParametar());
                            JsonObject jsonObject = JsonParser.parseString(jsonParam).getAsJsonObject();
                            Citalac citalac = gson.fromJson(jsonObject, Citalac.class);

                            Controller.getInstance().izmeniCitaoca(citalac);
                            odgovor.setOdgovor(null);
                        } catch(Exception e) {
                            odgovor.setGreska(e);
                        }
                    }
                    case VRATI_ZAPISE -> {
                        try {
                            List<ZapisOIznajmljivanju> zapisi = Controller.getInstance().vratiZapise();
                            odgovor.setOdgovor(zapisi);
                        } catch (Exception e) {
                            odgovor.setGreska(e);
                        }
                    }
                    case VRATI_ZAPIS -> {
                        try {
                            String jsonParam = gson.toJson(zahtev.getParametar());
                            JsonObject jsonObject = JsonParser.parseString(jsonParam).getAsJsonObject();
                            ZapisOIznajmljivanju zapis = gson.fromJson(jsonObject, ZapisOIznajmljivanju.class);

                            zapis = Controller.getInstance().vratiZapis(zapis);
                            odgovor.setOdgovor(zapis);
                        } catch (Exception e) {
                            odgovor.setGreska(e);
                        }
                    }
                    case VRATI_KNJIGE -> {
                        try {
                            List<Knjiga> knjige = Controller.getInstance().vratiKnjige();
                            odgovor.setOdgovor(knjige);
                        } catch (Exception e) {
                            odgovor.setGreska(e);
                        }
                    }
                    case KREIRAJ_ZAPIS -> {
                        try {
                            String jsonParam = gson.toJson(zahtev.getParametar());
                            JsonObject jsonObject = JsonParser.parseString(jsonParam).getAsJsonObject();
                            ZapisOIznajmljivanju zapis = gson.fromJson(jsonObject, ZapisOIznajmljivanju.class);

                            Controller.getInstance().kreirajZapisOIznajmljivanju(zapis);
                            odgovor.setOdgovor(null);
                        } catch(Exception e) {
                            odgovor.setGreska(e);
                        }
                    }
                    case IZMENI_ZAPIS -> {
                        try {
                            String jsonParam = gson.toJson(zahtev.getParametar());
                            JsonObject jsonObject = JsonParser.parseString(jsonParam).getAsJsonObject();
                            ZapisOIznajmljivanju zapis = gson.fromJson(jsonObject, ZapisOIznajmljivanju.class);

                            Controller.getInstance().izmeniZapis(zapis);
                            odgovor.setOdgovor(null);
                        } catch(Exception e) {
                            odgovor.setGreska(e);
                        }
                    }
                    case VRATI_BIBLIOTEKARE -> {
                        try {
                            List<Bibliotekar> bibliotekari = Controller.getInstance().vratiBibliotekare();
                            odgovor.setOdgovor(bibliotekari);
                        } catch (Exception e) {
                            odgovor.setGreska(e);
                        }
                    }
                    default -> System.out.println("TA OPERACIJA NE POSTOJI");
                }

                String jsonOdgovor = gson.toJson(odgovor);
                posiljalac.posalji(jsonOdgovor);

            } catch (Exception ex) {
                Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void prekiniNit(){
        kraj = true;
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
        interrupt();
    }
 
}
