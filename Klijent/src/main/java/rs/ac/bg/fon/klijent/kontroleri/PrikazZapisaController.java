/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.klijent.kontroleri;

import rs.ac.bg.fon.klijent.forme.FormaModovi;
import rs.ac.bg.fon.klijent.forme.PrikazZapisaForma;
import rs.ac.bg.fon.klijent.forme.model.ModelTabeleStavke;
import rs.ac.bg.fon.klijent.forme.model.ModelTabeleZapisi;
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
import rs.ac.bg.fon.zajednicki.model.StavkaZapisaOIznajmljivanju;
import rs.ac.bg.fon.zajednicki.model.ZapisOIznajmljivanju;

/**
 *
 * @author damja
 */
public class PrikazZapisaController {
    private final PrikazZapisaForma pzf;

    public PrikazZapisaController(PrikazZapisaForma pzf) {
        this.pzf = pzf;
        pzf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addActionListener();
        
        
       
    }

    public void otvoriFormu() {
        pripremiFormu();
        pzf.setVisible(true);
    }

    private void pripremiFormu() {
        pzf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        List<ZapisOIznajmljivanju> zapisi = new ArrayList<>();
        try {
            zapisi = Komunikacija.getInstanca().vratiZapise();
        } catch (Exception ex) {
            Logger.getLogger(PrikazZapisaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ModelTabeleZapisi mtz = new ModelTabeleZapisi(zapisi);
        pzf.getjTableZapisi().setModel(mtz);
        
        List<StavkaZapisaOIznajmljivanju> stavke = new ArrayList<>();
        ModelTabeleStavke mts = new ModelTabeleStavke(stavke);
        pzf.getjTableStavke().setModel(mts);
        
    }

    private void addActionListener() {
        pzf.addDetaljiActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pzf.getjTableZapisi().getSelectedRow();
                if (red != -1){
                    ModelTabeleZapisi mtz = (ModelTabeleZapisi) pzf.getjTableZapisi().getModel();
                    ZapisOIznajmljivanju z = mtz.getLista().get(red);
                    
                    try{
                        ZapisOIznajmljivanju noviZapis = Komunikacija.getInstanca().pretraziZapis(z);
                        List<StavkaZapisaOIznajmljivanju> stavke = noviZapis.getStavke();
                        ModelTabeleStavke mts = new ModelTabeleStavke(stavke);
                        pzf.getjTableStavke().setModel(mts);
                        JOptionPane.showMessageDialog(pzf, "Sistem je nasao zapis o iznajmljivanju","USPEH",JOptionPane.INFORMATION_MESSAGE);
                    }catch(Exception exc){JOptionPane.showMessageDialog(pzf, "Sistem ne moze da nadje zapis o iznajmljivanju","GRESKA",JOptionPane.ERROR_MESSAGE);}
                }
                else {JOptionPane.showMessageDialog(pzf, "Sistem ne moze da nadje zapis o iznajmljivanju","GRESKA",JOptionPane.ERROR_MESSAGE);}
                
            }
        });
        pzf.addIzmeniActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pzf.getjTableZapisi().getSelectedRow();
                if (red == -1)
                    JOptionPane.showMessageDialog(pzf, "Sistem ne moze da nadje zapis o iznajmljivanju","GRESKA",JOptionPane.ERROR_MESSAGE);
                else{
                    ModelTabeleZapisi mtz = (ModelTabeleZapisi) pzf.getjTableZapisi().getModel();
                    ZapisOIznajmljivanju zapis = mtz.getLista().get(red);
                    ZapisOIznajmljivanju zapisIzmena;
                    try{
                        zapisIzmena = Komunikacija.getInstanca().pretraziZapis(zapis);
                        JOptionPane.showMessageDialog(pzf, "Sistem je nasao zapis o iznajmljivanju","USPEH",JOptionPane.INFORMATION_MESSAGE);
                    }catch(Exception ex){JOptionPane.showMessageDialog(pzf, "Sistem ne moze da nadje zapis o iznajmljivanju","GRESKA",JOptionPane.ERROR_MESSAGE);return;}
                    
                    GlavniKontroler.getInstanca().dodajParametar("ZapisIzmena", zapisIzmena);
                    GlavniKontroler.getInstanca().otvoriGlavnuFormu(FormaModovi.IZMENI);
                }
            }
            
        
        });
        pzf.addDugmePretraziActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                osvezi();
                String citalac =  pzf.getjTextFieldCitalac().getText().strip();
                String bibliotekar = pzf.getjTextFieldBibliotekar().getText().strip();
                
                ModelTabeleZapisi mtz = (ModelTabeleZapisi) pzf.getjTableZapisi().getModel();
                boolean nasao = mtz.pretrazi(citalac,bibliotekar);
                if (nasao)
                    JOptionPane.showMessageDialog(pzf, "Sisem je nasao zapise o iznajmljivanju po zadatim kriterijumima","USPEH",JOptionPane.INFORMATION_MESSAGE);
                else JOptionPane.showMessageDialog(pzf, "Sisem ne moze da nadje zapise o iznajmljivanju po zadatim kriterijumima","GRESKA",JOptionPane.ERROR_MESSAGE);
                List<StavkaZapisaOIznajmljivanju> stavke = new ArrayList<>();
                ModelTabeleStavke mts = new ModelTabeleStavke(stavke);
                pzf.getjTableStavke().setModel(mts);
            }

            private void osvezi() {
                List<ZapisOIznajmljivanju> zapisi = new ArrayList<>();
                try {
                    zapisi = Komunikacija.getInstanca().vratiZapise();
                } catch (Exception ex) {
                    Logger.getLogger(PrikazZapisaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                ModelTabeleZapisi mtz = new ModelTabeleZapisi(zapisi);
                pzf.getjTableZapisi().setModel(mtz);
            }
            
        });
        
    }
    
}
