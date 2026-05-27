/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.klijent.kontroleri;

import rs.ac.bg.fon.klijent.forme.PrikazCitalacaForma;
import rs.ac.bg.fon.klijent.forme.model.ModelTabeleCitaoci;
import rs.ac.bg.fon.klijent.glavnikontroler.GlavniKontroler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.klijent.komunikacija.Komunikacija;
import rs.ac.bg.fon.zajednicki.model.Citalac;
import rs.ac.bg.fon.zajednicki.model.Mesto;

/**
 *
 * @author damja
 */
public class PrikazCitalacaController {
    private final PrikazCitalacaForma pcf;

    public PrikazCitalacaController(PrikazCitalacaForma pcf) {
        this.pcf = pcf;
        pcf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addActionListener();
       
    }

    public void otvoriFormu() {
        pripremiFormu();
        pcf.setVisible(true);
    }

    private void pripremiFormu() {
        List<Citalac> citaoci = Komunikacija.getInstanca().vratiCitaoce();
        ModelTabeleCitaoci mtc = new ModelTabeleCitaoci(citaoci);
        pcf.getjTableCitaoci().setModel(mtc);
        napuniCombo();
        
    }

    private void addActionListener() {
        pcf.addDugmeObrisiActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pcf.getjTableCitaoci().getSelectedRow();
                if (red == -1)
                    JOptionPane.showMessageDialog(pcf, "Sistem ne moze da obrise citaoca","GRESKA", JOptionPane.ERROR_MESSAGE);
                else{
                    ModelTabeleCitaoci mtc = (ModelTabeleCitaoci) pcf.getjTableCitaoci().getModel();
                    Citalac c = mtc.getLista().get(red);
                    try {
                        Komunikacija.getInstanca().obrisiCitaoca(c);
                        JOptionPane.showMessageDialog(pcf, "Sistem je obrisao citaoca","USPEH", JOptionPane.INFORMATION_MESSAGE);
                        pripremiFormu();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(pcf, "Sistem ne moze da obrise citaoca","GRESKA", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            
        });
        
        pcf.addDugmeIzmeniActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pcf.getjTableCitaoci().getSelectedRow();
                if (red == -1)
                    JOptionPane.showMessageDialog(pcf, "Sistem ne moze da izmeni citaoca","GRESKA", JOptionPane.ERROR_MESSAGE);
                else{
                    try{
                        ModelTabeleCitaoci mtc = (ModelTabeleCitaoci) pcf.getjTableCitaoci().getModel();
                        Citalac c = mtc.getLista().get(red);
                        GlavniKontroler.getInstanca().dodajParametar("Citalac", c);
                        JOptionPane.showMessageDialog(pcf, "Sistem je nasao citaoca","USPEH", JOptionPane.INFORMATION_MESSAGE);
                        GlavniKontroler.getInstanca().otvoriIzmeniCitaocaFormu();
                        pcf.getjTextFieldIme().setText("");
                        pcf.getjTextFieldPrezime().setText("");
                        pcf.getjComboBoxMesta().setSelectedIndex(0);
                    }catch(Exception ex){JOptionPane.showMessageDialog(pcf, "Sistem ne moze da nadje citaoca","GRESKA", JOptionPane.ERROR_MESSAGE);}
                }
            }
            
        });
        
        
        pcf.addDugmePretraziActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                osvezi();
                String ime =  pcf.getjTextFieldIme().getText().strip();
                String prezime = pcf.getjTextFieldPrezime().getText().strip();
                Mesto mesto = (Mesto) pcf.getjComboBoxMesta().getSelectedItem();
                
                ModelTabeleCitaoci mtc = (ModelTabeleCitaoci) pcf.getjTableCitaoci().getModel();
                boolean nasao = mtc.pretrazi(ime,prezime,mesto);
                if (nasao)
                    JOptionPane.showMessageDialog(pcf, "Sisem je nasao citaoce po zadatim kriterijumima","USPEH",JOptionPane.INFORMATION_MESSAGE);
                else JOptionPane.showMessageDialog(pcf, "Sisem ne moze da nadje citaoce po zadatim kriterijumima","GRESKA",JOptionPane.ERROR_MESSAGE);
                
            }
            
        });
        pcf.addDugmeDetaljiActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int red = pcf.getjTableCitaoci().getSelectedRow();
                if (red == -1)
                    JOptionPane.showMessageDialog(pcf, "Sistem ne moze da nadje citaoca","GRESKA", JOptionPane.ERROR_MESSAGE);
                else{
                    try{
                        ModelTabeleCitaoci mtc = (ModelTabeleCitaoci) pcf.getjTableCitaoci().getModel();
                        Citalac c = mtc.getLista().get(red);
                        GlavniKontroler.getInstanca().dodajParametar("Citalac", c);
                        JOptionPane.showMessageDialog(pcf, "Sistem je nasao citaoca","USPEH", JOptionPane.INFORMATION_MESSAGE);
                        GlavniKontroler.getInstanca().otvoriDetaljiCitaocaFormu();
                        pcf.getjTextFieldIme().setText("");
                        pcf.getjTextFieldPrezime().setText("");
                        pcf.getjComboBoxMesta().setSelectedIndex(0);
                    }catch(Exception ex){JOptionPane.showMessageDialog(pcf, "Sistem ne moze da nadje citaoca","GRESKA", JOptionPane.ERROR_MESSAGE);}
                }
            }
            
        });
    }

    public void osvezi() {
        pripremiFormu();
    }
    
    private void napuniCombo(){
        List<Mesto> mesta = Komunikacija.getInstanca().vratiMesta();
        pcf.getjComboBoxMesta().addItem(null);
        for (Mesto m : mesta) {
            pcf.getjComboBoxMesta().addItem(m);
        }
    }
    
    
    
}
