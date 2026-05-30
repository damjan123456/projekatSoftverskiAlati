package rs.ac.bg.fon.klijent.kontroleri;

import rs.ac.bg.fon.klijent.forme.DodajSertifikatForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.klijent.komunikacija.Komunikacija;
import rs.ac.bg.fon.zajednicki.model.Sertifikat;

/**
 * Kontroler zadužen za upravljanje formom za unos novih sertifikata u sistem.
 * * @author Damjan
 */
public class DodajSertifikatController {
    
    /**
     * Grafička forma za unos novog sertifikata.
     */
    private final DodajSertifikatForma dsf;
    
    /**
     * Konstruktor koji inicijalizuje formu, podešava operaciju zatvaranja prozora i postavlja osluškivače događaja.
     * * @param dsf Grafička forma za dodavanje sertifikata.
     */
    public DodajSertifikatController(DodajSertifikatForma dsf) {
        this.dsf = dsf;
        dsf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addActionListener();
    }
    
    /**
     * Resetuje tekstualna polja forme nakon uspešnog unosa podataka.
     */
    private void pripremi(){
        dsf.getjTextFieldInstitucija().setText("");
        dsf.getjTextFieldNaziv().setText("");
    }
    
    /**
     * Registruje akcioni osluškivač na dugme za potvrdu unosa sertifikata.
     */
    private void addActionListener() {
        dsf.dodajActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ime = dsf.getjTextFieldNaziv().getText().strip();
                String institucija = dsf.getjTextFieldInstitucija().getText().strip();
                Sertifikat sertifikat = new Sertifikat(ime, institucija);
                try{
                    Komunikacija.getInstanca().unesiSertifikat(sertifikat);
                    JOptionPane.showMessageDialog(dsf, "Sistem je zapamtio sertifikat");
                    pripremi();
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(dsf, "Sistem ne moze da zapamti sertifikat", "GRESKA", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Čini formu za unos sertifikata vidljivom korisniku.
     */
    public void otvoriFormu() {
        dsf.setVisible(true);
    }
}