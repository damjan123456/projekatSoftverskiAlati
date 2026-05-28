/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.klijent.kontroleri;

import rs.ac.bg.fon.klijent.forme.DodajCitaocaForma;
import rs.ac.bg.fon.klijent.forme.FormaModovi;
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
 *
 * @author damja
 */
public class DodajCitaocaController {
    private final DodajCitaocaForma dcf;
    private int id;

    public DodajCitaocaController(DodajCitaocaForma dcf) {
        this.dcf = dcf;
        addActionListener();
       
    }

    public void otvoriFormu(FormaModovi mod) {
        pripremiFormu(mod);
        dcf.setVisible(true);
        dcf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }

    private void pripremiFormu(FormaModovi mod) {
        switch (mod) {
            case DODAJ:
                dcf.getjButtonIzmeni().setVisible(false);
                dcf.getjButtonDodaj().setEnabled(true);
                napuniCombo();
                dcf.getjTextFieldIme().setText("");
                dcf.getjTextFieldPrezime().setText("");
                dcf.getjTextFieldBrojTel().setText("");
                dcf.getjComboBoxMesta().setSelectedIndex(-1);
                break;
            case DETALJI:
                {
                    dcf.getjButtonIzmeni().setVisible(false);
                    dcf.getjButtonDodaj().setVisible(false);
                    Citalac c = (Citalac) GlavniKontroler.getInstanca().vratiParametar("Citalac");
                    napuniCombo();
                    dcf.getjTextFieldIme().setText(c.getIme());
                    dcf.getjTextFieldPrezime().setText(c.getPrezime());
                    dcf.getjTextFieldBrojTel().setText(c.getBrojTel());
                    dcf.getjComboBoxMesta().setSelectedItem(c.getMesto());
                    dcf.getjTextFieldIme().setEditable(false);
                    dcf.getjTextFieldPrezime().setEditable(false);
                    dcf.getjTextFieldBrojTel().setEditable(false);
                    dcf.getjComboBoxMesta().setEnabled(false);
                    break;
                }
            default:
                {
                    dcf.getjButtonIzmeni().setEnabled(true);
                    dcf.getjButtonDodaj().setVisible(false);
                    Citalac c = (Citalac) GlavniKontroler.getInstanca().vratiParametar("Citalac");
                    dcf.getjTextFieldIme().setText(c.getIme());
                    dcf.getjTextFieldPrezime().setText(c.getPrezime());
                    dcf.getjTextFieldBrojTel().setText(c.getBrojTel());
                    id = c.getIdCitalac();
                    napuniCombo();
                    dcf.getjComboBoxMesta().setSelectedItem(c.getMesto());
//            List<Mesto> mesta = Komunikacija.getInstanca().vratiMesta();
//            for (Mesto m : mesta) {
//                if (m.equals(c.getMesto())){
//                    dcf.getjComboBoxMesta().setSelectedItem(m);
//                    break;
//                }
//            }
                    break;
                }
        }
        
    }

    private void addActionListener() {
        dcf.dodajActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String ime =  dcf.getjTextFieldIme().getText().strip();
                String prezime = dcf.getjTextFieldPrezime().getText().strip();
                String brojTel = dcf.getjTextFieldBrojTel().getText().strip();
                Mesto mesto = (Mesto) (dcf.getjComboBoxMesta().getSelectedItem());
                Citalac citalac = new Citalac(ime,prezime,brojTel,mesto);
                
                try{
                    Komunikacija.getInstanca().unesiCitaoca(citalac);
                    JOptionPane.showMessageDialog(dcf, "Sistem je zapamtio citaoca","USPEH",JOptionPane.INFORMATION_MESSAGE);
                    FormaModovi mod = FormaModovi.DODAJ;
                    pripremiFormu(mod);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(dcf, "Sistem ne moze da zapamti citaoca","GRESKA",JOptionPane.ERROR_MESSAGE);
                }
            }    
        });
        
        dcf.izmeniActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String ime =  dcf.getjTextFieldIme().getText().strip();
                String prezime = dcf.getjTextFieldPrezime().getText().strip();
                String brojTel = dcf.getjTextFieldBrojTel().getText().strip();
                Mesto mesto = (Mesto) (dcf.getjComboBoxMesta().getSelectedItem());
                Citalac citalac = new Citalac(ime,prezime,brojTel,mesto);
                citalac.setIdCitalac(id);
                
                try{
                    Komunikacija.getInstanca().izmeniCitaoca(citalac);
                    JOptionPane.showMessageDialog(dcf, "Sistem je zapamtio citaoca","USPEH",JOptionPane.INFORMATION_MESSAGE);
                    dcf.dispose();
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(dcf, "Sistem ne moze da zapamti citaoca","GRESKA",JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });
    }
    
    private void napuniCombo(){
        List<Mesto> mesta = new ArrayList<>();
        try {
            mesta = Komunikacija.getInstanca().vratiMesta();
        } catch (Exception ex) {
            Logger.getLogger(DodajCitaocaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Mesto m : mesta) {
            dcf.getjComboBoxMesta().addItem(m);
        }
    }
}
