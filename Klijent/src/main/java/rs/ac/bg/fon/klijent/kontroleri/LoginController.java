/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.klijent.kontroleri;

import rs.ac.bg.fon.klijent.forme.LoginForma;
import rs.ac.bg.fon.klijent.glavnikontroler.GlavniKontroler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.klijent.komunikacija.Komunikacija;
import rs.ac.bg.fon.zajednicki.model.Bibliotekar;

/**
 *
 * @author damja
 */
public class LoginController {
    private final LoginForma lf;

    public LoginController(LoginForma lf) {
        this.lf = lf;
        addActionListener();
    }

    private void addActionListener() {
        lf.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prijava(e);
            }

            private void prijava(ActionEvent e) {
                String korisnickoIme = lf.getjTextFieldKorisnickoIme().getText().trim();
                String sifra = String.valueOf(lf.getjPasswordFieldSifra().getPassword());
                
                Komunikacija.getInstanca().konekcija();
                Bibliotekar ulogovani = null;
                try{
                    ulogovani = Komunikacija.getInstanca().login(korisnickoIme,sifra);
                }catch(Exception exc){JOptionPane.showMessageDialog(lf, "Ne moze da se otvori glavna forma i meni","GRESKA",JOptionPane.ERROR_MESSAGE);return;}
                if (ulogovani == null){
                    JOptionPane.showMessageDialog(lf, "Korisnicko ime i sifra nisu ispravni","GRESKA",JOptionPane.ERROR_MESSAGE);
                }else{
                    GlavniKontroler.getInstanca().setUlogovani(ulogovani);
                    JOptionPane.showMessageDialog(lf, "Korisnicko ime i sifra su ispravni","USPEH",JOptionPane.INFORMATION_MESSAGE);
                    try{
                        GlavniKontroler.getInstanca().otvoriGlavnuFormu();
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(lf, "Ne moze da se otvori glavna forma i meni","GRESKA",JOptionPane.ERROR_MESSAGE);
                    }
                    lf.dispose();
                }
            }
        });
    }

    public void otvoriFormu() {
        lf.setVisible(true);
    }
    
    
}
