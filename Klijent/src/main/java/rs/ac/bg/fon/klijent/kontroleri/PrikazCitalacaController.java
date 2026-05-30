package rs.ac.bg.fon.klijent.kontroleri;

import rs.ac.bg.fon.klijent.forme.PrikazCitalacaForma;
import rs.ac.bg.fon.klijent.forme.model.ModelTabeleCitaoci;
import rs.ac.bg.fon.klijent.glavnikontroler.GlavniKontroler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.klijent.komunikacija.Komunikacija;
import rs.ac.bg.fon.zajednicki.model.Citalac;
import rs.ac.bg.fon.zajednicki.model.Mesto;

/**
 * Kontroler zadužen za upravljanje formom za prikaz, pretragu i brisanje čitalaca.
 * Koordinira rad između tabelarnog prikaza na formi PrikazCitalacaForma, modela tabele i mrežne komunikacije sa serverom.
 * * @author Damjan
 */
public class PrikazCitalacaController {
    
    /**
     * Grafička forma za prikaz i pretragu čitalaca.
     */
    private final PrikazCitalacaForma pcf;

    /**
     * Konstruktor koji inicijalizuje formu, postavlja podrazumevanu operaciju zatvaranja prozora i registruje akcione osluškivače.
     * * @param pcf Grafička forma za prikaz čitalaca.
     */
    public PrikazCitalacaController(PrikazCitalacaForma pcf) {
        this.pcf = pcf;
        pcf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addActionListener();
    }

    /**
     * Priprema podatke na formi (učitava listu iz baze i puni komponente) i čini formu vidljivom korisniku.
     */
    public void otvoriFormu() {
        pripremiFormu();
        pcf.setVisible(true);
    }

    /**
     * Preuzima osveženu listu čitalaca sa servera, inicijalizuje model tabele, 
     * postavlja ga u tabelu forme i ponovo puni padajući meni sa mestima.
     */
    private void pripremiFormu() {
        List<Citalac> citaoci = new ArrayList<>();
        try {
            citaoci = Komunikacija.getInstanca().vratiCitaoce();
        } catch (Exception ex) {
            Logger.getLogger(PrikazCitalacaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ModelTabeleCitaoci mtc = new ModelTabeleCitaoci(citaoci);
        pcf.getjTableCitaoci().setModel(mtc);
        napuniCombo();
    }

    /**
     * Registruje osluškivače događaja za sve akcije na formi: brisanje, izmenu, pretragu i prikaz detalja o čitaocu.
     */
    private void addActionListener() {
        pcf.addDugmeObrisiActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pcf.getjTableCitaoci().getSelectedRow();
                if (red == -1)
                    JOptionPane.showMessageDialog(pcf, "Sistem ne moze da obrise citaoca", "GRESKA", JOptionPane.ERROR_MESSAGE);
                else{
                    ModelTabeleCitaoci mtc = (ModelTabeleCitaoci) pcf.getjTableCitaoci().getModel();
                    Citalac c = mtc.getLista().get(red);
                    try {
                        Komunikacija.getInstanca().obrisiCitaoca(c);
                        JOptionPane.showMessageDialog(pcf, "Sistem je obrisao citaoca", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                        pripremiFormu();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(pcf, "Sistem ne moze da obrise citaoca", "GRESKA", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        pcf.addDugmeIzmeniActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pcf.getjTableCitaoci().getSelectedRow();
                if (red == -1)
                    JOptionPane.showMessageDialog(pcf, "Sistem ne moze da izmeni citaoca", "GRESKA", JOptionPane.ERROR_MESSAGE);
                else{
                    try{
                        ModelTabeleCitaoci mtc = (ModelTabeleCitaoci) pcf.getjTableCitaoci().getModel();
                        Citalac c = mtc.getLista().get(red);
                        GlavniKontroler.getInstanca().dodajParametar("Citalac", c);
                        JOptionPane.showMessageDialog(pcf, "Sistem je nasao citaoca", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                        GlavniKontroler.getInstanca().otvoriIzmeniCitaocaFormu();
                        pcf.getjTextFieldIme().setText("");
                        pcf.getjTextFieldPrezime().setText("");
                        pcf.getjComboBoxMesta().setSelectedIndex(0);
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(pcf, "Sistem ne moze da nadje citaoca", "GRESKA", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        pcf.addDugmePretraziActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                osvezi();
                String ime = pcf.getjTextFieldIme().getText().strip();
                String prezime = pcf.getjTextFieldPrezime().getText().strip();
                Mesto mesto = (Mesto) pcf.getjComboBoxMesta().getSelectedItem();
                
                ModelTabeleCitaoci mtc = (ModelTabeleCitaoci) pcf.getjTableCitaoci().getModel();
                boolean nasao = mtc.pretrazi(ime, prezime, mesto);
                if (nasao)
                    JOptionPane.showMessageDialog(pcf, "Sisem je nasao citaoce po zadatim kriterijumima", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                else 
                    JOptionPane.showMessageDialog(pcf, "Sisem ne moze da nadje citaoce po zadatim kriterijumima", "GRESKA", JOptionPane.ERROR_MESSAGE);
            }
        });

        pcf.addDugmeDetaljiActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pcf.getjTableCitaoci().getSelectedRow();
                if (red == -1)
                    JOptionPane.showMessageDialog(pcf, "Sistem ne moze da nadje citaoca", "GRESKA", JOptionPane.ERROR_MESSAGE);
                else{
                    try{
                        ModelTabeleCitaoci mtc = (ModelTabeleCitaoci) pcf.getjTableCitaoci().getModel();
                        Citalac c = mtc.getLista().get(red);
                        GlavniKontroler.getInstanca().dodajParametar("Citalac", c);
                        JOptionPane.showMessageDialog(pcf, "Sistem je nasao citaoca", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                        GlavniKontroler.getInstanca().otvoriDetaljiCitaocaFormu();
                        pcf.getjTextFieldIme().setText("");
                        pcf.getjTextFieldPrezime().setText("");
                        pcf.getjComboBoxMesta().setSelectedIndex(0);
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(pcf, "Sistem ne moze da nadje citaoca", "GRESKA", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    /**
     * Osvežava tabelarni prikaz čitalaca tako što ponovo poziva metodu za pripremu forme.
     */
    public void osvezi() {
        pripremiFormu();
    }
    
    /**
     * Preuzima listu svih mesta sa servera i puni padajući meni, dodajući praznu (null) vrednost na početak radi lakšeg poništavanja filtera pretrage.
     */
    private void napuniCombo(){
        List<Mesto> mesta = new ArrayList<>();
        try {
            mesta = Komunikacija.getInstanca().vratiMesta();
        } catch (Exception ex) {
            Logger.getLogger(PrikazCitalacaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pcf.getjComboBoxMesta().addItem(null);
        for (Mesto m : mesta) {
            pcf.getjComboBoxMesta().addItem(m);
        }
    }
}