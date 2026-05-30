package rs.ac.bg.fon.klijent.kontroleri;

import rs.ac.bg.fon.klijent.forme.FormaModovi;
import rs.ac.bg.fon.klijent.forme.GlavnaForma;
import rs.ac.bg.fon.klijent.forme.model.ModelTabeleStavke;
import rs.ac.bg.fon.klijent.glavnikontroler.GlavniKontroler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.klijent.komunikacija.Komunikacija;
import rs.ac.bg.fon.zajednicki.model.Bibliotekar;
import rs.ac.bg.fon.zajednicki.model.Citalac;
import rs.ac.bg.fon.zajednicki.model.Knjiga;
import rs.ac.bg.fon.zajednicki.model.StavkaZapisaOIznajmljivanju;
import rs.ac.bg.fon.zajednicki.model.ZapisOIznajmljivanju;

/**
 * Kontroler zadužen za upravljanje centralnim ekranom aplikacije (GlavnaForma).
 * Pokriva rad sa stavkama dokumenata, tabelarnim prikazom, kalkulacijama suma i perzistencijom složenog zapisa o iznajmljivanju.
 * * @author Damjan
 */
public class GlavnaFormaController {
    
    /**
     * Centralna grafička forma aplikacije sa menijem i radnim prostorom za stavke.
     */
    private final GlavnaForma gf;
    
    /**
     * Konstruktor koji inicijalizuje kontroler i vezuje akcione osluškivače za komponente forme.
     * * @param glavnaForma Centralna grafička forma.
     */
    public GlavnaFormaController(GlavnaForma glavnaForma) {
        gf = glavnaForma;
        addActionListeners();
    }

    /**
     * Otvara glavnu formu, postavlja oznaku trenutno ulogovanog korisnika na sesiji i inicijalizuje podatke.
     */
    public void otvoriFormu() {
        gf.setVisible(true);
        gf.getjLabelKorisnicko().setText(GlavniKontroler.getInstanca().getUlogovani().toString());
        pripremiPodatke();
    }

    /**
     * Registruje sve osluškivače događaja za kreiranje stavki, brisanje stavki, unos i izmenu krovnog zapisa.
     */
    private void addActionListeners() {
        gf.addDodajActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gf.getjComboBoxKnjiga().getSelectedItem()==null){
                    JOptionPane.showMessageDialog(gf, "NISTE IZBRALI KNJIGU");
                    return;
                }
                StavkaZapisaOIznajmljivanju stavka = new StavkaZapisaOIznajmljivanju();
                Knjiga k = (Knjiga)gf.getjComboBoxKnjiga().getSelectedItem();
                String datum1 = gf.getjTextFieldMaksDatumVracanja().getText().strip();
                String datum2 = gf.getjTextFieldDatumVracanja().getText().strip();
                Date maxDatumVracanja = null;
                Date datumVracanja = null;
                try {
                    maxDatumVracanja = (new SimpleDateFormat("dd.MM.yyyy.")).parse(datum1);
                    datumVracanja = (new SimpleDateFormat("dd.MM.yyyy.")).parse(datum2);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(gf, "LOS DATUM");
                    return;
                }
                int kolicina = Integer.parseInt((String) gf.getjComboBoxKolicina().getSelectedItem());
                stavka.setDatumVracanja(datumVracanja);
                stavka.setMaxDatumVracanja(maxDatumVracanja);
                stavka.setKolicina(kolicina);
                stavka.setKnjiga(k);
                boolean vracenoNaVreme = true;
                if (maxDatumVracanja.getTime() - datumVracanja.getTime() < 0)
                    vracenoNaVreme = false;
                stavka.setVracenoNaVreme(vracenoNaVreme);
                stavka.setCenaZaNepovracaj(vracenoNaVreme ? 0 : k.getCenaZaNepovracaj());
                stavka.setIznos(kolicina * stavka.getCenaZaNepovracaj());
               
                ModelTabeleStavke mts = (ModelTabeleStavke) gf.getjTableStavke().getModel();
                mts.dodajStavku(stavka);
                resetujStavke();
                gf.getjTextFieldUkupanIznos().setText(vratiUkupanIznos() + "");
            }
        });
        
        gf.addObrisiActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = gf.getjTableStavke().getSelectedRow();
                if (red == -1)
                    JOptionPane.showMessageDialog(gf, "Sistem ne moze da obrise stavku", "GRESKA", JOptionPane.ERROR_MESSAGE);
                else{
                    ModelTabeleStavke mts = (ModelTabeleStavke) gf.getjTableStavke().getModel();
                    mts.obrisiStavku(red);
                    gf.getjTextFieldUkupanIznos().setText(vratiUkupanIznos() + "");
                }
            }
        });
        
        gf.addKreirajActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String datum = gf.getjTextFieldDatum().getText().strip();
                Date datumIznajmljivanja = null;
                try {
                    datumIznajmljivanja = (new SimpleDateFormat("dd.MM.yyyy.")).parse(datum);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(gf, "LOS DATUM");
                    return;
                }
                Bibliotekar b = (Bibliotekar) gf.getjComboBoxBibliotekari().getSelectedItem();
                Citalac c = (Citalac) gf.getjComboBoxCitalac().getSelectedItem();
                ModelTabeleStavke mts = (ModelTabeleStavke) gf.getjTableStavke().getModel();
                List<StavkaZapisaOIznajmljivanju> stavke = mts.getLista();
                if (stavke.isEmpty()){
                    JOptionPane.showMessageDialog(gf, "Sistem ne moze da zapamti zapis o iznajmljivanju jer nema stavki", "GRESKA", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double ukupanIznos = Double.parseDouble(gf.getjTextFieldUkupanIznos().getText());
                ZapisOIznajmljivanju zapis = new ZapisOIznajmljivanju(datumIznajmljivanja, ukupanIznos, b, c, stavke);
                try{
                    Komunikacija.getInstanca().kreirajZapis(zapis);
                    JOptionPane.showMessageDialog(gf, "Sistem je zapamtio zapis o iznajmljivanju", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                    resetujZapis();
                    resetujStavke();
                    mts.setLista(new ArrayList<>());
                    mts.fireTableDataChanged();
                }catch(Exception exc){
                    JOptionPane.showMessageDialog(gf, "Sistem ne moze da zapamti zapis o iznajmljivanju", "GRESKA", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        gf.addIzmeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String datum = gf.getjTextFieldDatum().getText().strip();
                Date datumIznajmljivanja = null;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
                    datumIznajmljivanja = sdf.parse(datum);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(gf, "LOS DATUM");
                    return;
                }
                Bibliotekar b = (Bibliotekar) gf.getjComboBoxBibliotekari().getSelectedItem();
                Citalac c = (Citalac) gf.getjComboBoxCitalac().getSelectedItem();
                ModelTabeleStavke mts = (ModelTabeleStavke) gf.getjTableStavke().getModel();
                List<StavkaZapisaOIznajmljivanju> stavke = mts.getLista();
                if (stavke.isEmpty()){
                    JOptionPane.showMessageDialog(gf, "Sistem ne moze da zapamti zapis o iznajmljivanju jer nema stavki", "GRESKA", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double ukupanIznos = Double.parseDouble(gf.getjTextFieldUkupanIznos().getText());
                ZapisOIznajmljivanju zapis = new ZapisOIznajmljivanju(datumIznajmljivanja, ukupanIznos, b, c, stavke);
                ZapisOIznajmljivanju zapisStari = (ZapisOIznajmljivanju) GlavniKontroler.getInstanca().vratiParametar("ZapisIzmena");
                zapis.setIdZapis(zapisStari.getIdZapis());
                try{
                    Komunikacija.getInstanca().izmeniZapis(zapis);
                    JOptionPane.showMessageDialog(gf, "Sistem je zapamtio zapis o iznajmljivanju", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                    gf.dispose();
                }catch(Exception exc){
                    JOptionPane.showMessageDialog(gf, "Sistem ne moze da zapamti zapis o iznajmljivanju", "GRESKA", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Pokreće inicijalno popunjavanje svih combo box-eva podacima i inicijalizuje prazan model tabele stavki.
     */
    private void pripremiPodatke() {
        napuniComboKnjige();
        napuniComboCitaoci();
        napuniComboBibliotekari();
        gf.getjButtonIzmeniZapis().setVisible(false);
        
        ModelTabeleStavke mts = new ModelTabeleStavke(new ArrayList<>());
        gf.getjTableStavke().setModel(mts);
    }

    /**
     * Puni padajući meni podacima o svim dostupnim knjigama iz baze.
     */
    private void napuniComboKnjige() {
        List<Knjiga> knjige = new ArrayList<>();
        try {
            knjige = Komunikacija.getInstanca().vratiKnjige();
        } catch (Exception ex) {
            Logger.getLogger(GlavnaFormaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Knjiga k : knjige) {
            gf.getjComboBoxKnjiga().addItem(k);
        }
        gf.getjComboBoxKnjiga().setSelectedIndex(-1);
    }

    /**
     * Puni padajući meni podacima o svim registrovanim čitaocima iz baze.
     */
    private void napuniComboCitaoci() {
        List<Citalac> citaoci = new ArrayList<>();
        try {
            citaoci = Komunikacija.getInstanca().vratiCitaoce();
        } catch (Exception ex) {
            Logger.getLogger(GlavnaFormaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Citalac c : citaoci) {
            gf.getjComboBoxCitalac().addItem(c);
        }
        gf.getjComboBoxCitalac().setSelectedIndex(-1);
    }
    
    /**
     * Puni padajući meni podacima o svim registrovanim bibliotekarima iz baze.
     */
    private void napuniComboBibliotekari() {
        List<Bibliotekar> bibliotekari = new ArrayList<>();
        try {
            bibliotekari = Komunikacija.getInstanca().vratiBibliotekare();
        } catch (Exception ex) {
            Logger.getLogger(GlavnaFormaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Bibliotekar b : bibliotekari) {
            gf.getjComboBoxBibliotekari().addItem(b);
        }
        gf.getjComboBoxBibliotekari().setSelectedIndex(-1);
    }

    /**
     * Resetuje ulazna polja forme namenjena formiranju pojedinačne stavke.
     */
    private void resetujStavke() {
        gf.getjComboBoxKnjiga().setSelectedIndex(-1);
        gf.getjTextFieldMaksDatumVracanja().setText("");
        gf.getjTextFieldDatumVracanja().setText("");
        gf.getjComboBoxKolicina().setSelectedIndex(0);
    }
    
    /**
     * Iterira kroz trenutne stavke u tabeli i kalkuliše zbir njihovih pojedinačnih iznosa.
     * * @return Suma svih stavki tipa double.
     */
    private double vratiUkupanIznos(){
        ModelTabeleStavke mts = (ModelTabeleStavke) gf.getjTableStavke().getModel();
        List<StavkaZapisaOIznajmljivanju> stavke = mts.getLista();
        double iznos = 0;
        for (StavkaZapisaOIznajmljivanju s : stavke) {
            iznos += s.getIznos();
        }
        return iznos;
    }
    
    /**
     * Resetuje sva polja vezana za krovne informacije o samom zapisu iznajmljivanja.
     */
    private void resetujZapis(){
        gf.getjTextFieldDatum().setText("");
        gf.getjComboBoxCitalac().setSelectedIndex(-1);
        gf.getjComboBoxBibliotekari().setSelectedIndex(-1);
        gf.getjTextFieldUkupanIznos().setText("");
    }

    /**
     * Alternativno otvaranje glavne forme koje u slučaju režima IZMENI učitava selektovani zapis i popunjava elemente na osnovu sesije.
     * * @param formaModovi Režim u kojem se otvara glavna forma (DODAJ ili IZMENI).
     */
    public void otvoriFormu(FormaModovi formaModovi) {
        napuniComboCitaoci();
        napuniComboKnjige();
        napuniComboBibliotekari();
        Bibliotekar b = GlavniKontroler.getInstanca().getUlogovani();
        String imePrezime = b.getIme() + " " + b.getPrezime();
        gf.setVisible(true);
        gf.getjLabelKorisnicko().setText(imePrezime);
        ModelTabeleStavke mts = new ModelTabeleStavke(new ArrayList<>());
        gf.getjTableStavke().setModel(mts);
        if (formaModovi == FormaModovi.IZMENI){
            gf.getjButtonKreiraj().setVisible(false);
            ZapisOIznajmljivanju zapis = (ZapisOIznajmljivanju) GlavniKontroler.getInstanca().vratiParametar("ZapisIzmena");
            mts.setLista(zapis.getStavke());
            mts.fireTableDataChanged();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
            gf.getjTextFieldDatum().setText(sdf.format(zapis.getDatumIznajmljivanja()));
            gf.getjComboBoxBibliotekari().setSelectedItem(zapis.getBibliotekar());
            gf.getjTextFieldUkupanIznos().setText(zapis.getUkupanIznos() + "");
            gf.getjComboBoxCitalac().setSelectedItem(zapis.getCitalac());
        }
    }
}