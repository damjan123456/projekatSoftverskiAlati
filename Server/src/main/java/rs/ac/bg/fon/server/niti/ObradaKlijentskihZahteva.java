/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.server.niti;

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
        while(!kraj){
            try {   
                Zahtev zahtev = (Zahtev) primalac.primi();
                if (zahtev == null){
                    prekiniNit();
                    break;
                }
                Odgovor odgovor = new Odgovor();
                switch(zahtev.getOperacija()){
                    case LOGIN -> {
                        Bibliotekar b = (Bibliotekar) zahtev.getParametar();
                        b = Controller.getInstance().login(b);
                        odgovor.setOdgovor(b);
                        }
                    case VRATI_CITAOCE -> {
                        List<Citalac> citaoci = Controller.getInstance().vratiCitaoce();
                        odgovor.setOdgovor(citaoci);
                        }
                    case OBRISI_CITAOCA -> {
                        try{
                            Citalac citalac = (Citalac) zahtev.getParametar(); 
                            Controller.getInstance().obrisiCitaoca(citalac);
                            odgovor.setOdgovor(null);
                        }catch(Exception e){odgovor.setOdgovor(e);}
                        }
                    case UNESI_SERTIFIKAT -> {
                        try{
                            Sertifikat sertifikat = (Sertifikat) zahtev.getParametar();
                            Controller.getInstance().dodajSertifikat(sertifikat);
                            odgovor.setOdgovor(null);
                        }catch(Exception e){odgovor.setOdgovor(e);}
                        }
                    case VRATI_MESTA -> {
                        List<Mesto> mesta = Controller.getInstance().vratiMesta();
                        odgovor.setOdgovor(mesta);
                        }
                    case UNESI_CITAOCA ->{
                        try{
                            Citalac citalac = (Citalac) zahtev.getParametar();
                            Controller.getInstance().dodajCitaoca(citalac);
                            odgovor.setOdgovor(null);
                        }catch(Exception e){odgovor.setOdgovor(e);}
                    }
                    case IZMENI_CITAOCA ->{
                        try{
                            Citalac citalac = (Citalac) zahtev.getParametar();
                            Controller.getInstance().izmeniCitaoca(citalac);
                            odgovor.setOdgovor(null);
                        }catch(Exception e){odgovor.setOdgovor(e);}
                    }
                    case VRATI_ZAPISE ->{
                        List<ZapisOIznajmljivanju> zapisi = Controller.getInstance().vratiZapise();
                        odgovor.setOdgovor(zapisi);
                    }
                    case VRATI_ZAPIS ->{
                        ZapisOIznajmljivanju zapis = (ZapisOIznajmljivanju) zahtev.getParametar();
                        zapis = Controller.getInstance().vratiZapis(zapis);
                        odgovor.setOdgovor(zapis);
                    }
                    case VRATI_KNJIGE ->{
                        List<Knjiga> knjige = Controller.getInstance().vratiKnjige();
                        odgovor.setOdgovor(knjige);
                    }
                    case KREIRAJ_ZAPIS ->{
                        try{
                            ZapisOIznajmljivanju zapis = (ZapisOIznajmljivanju) zahtev.getParametar();
                            Controller.getInstance().kreirajZapisOIznajmljivanju(zapis);
                            odgovor.setOdgovor(null);
                        }catch(Exception e){odgovor.setOdgovor(e);}
                    }
                    case IZMENI_ZAPIS ->{
                        try{
                            ZapisOIznajmljivanju zapis = (ZapisOIznajmljivanju) zahtev.getParametar();
                            Controller.getInstance().izmeniZapis(zapis);
                            odgovor.setOdgovor(null);
                        }catch(Exception e){odgovor.setOdgovor(e);}
                    }
                    case VRATI_BIBLIOTEKARE ->{
                        List<Bibliotekar> bibliotekari = Controller.getInstance().vratiBibliotekare();
                        odgovor.setOdgovor(bibliotekari);
                    }
                    default -> System.out.println("TA OPERACIJA NE POSTOJI");
                }
                posiljalac.posalji(odgovor);
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
