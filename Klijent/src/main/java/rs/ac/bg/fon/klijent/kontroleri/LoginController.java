package rs.ac.bg.fon.klijent.kontroleri;

import rs.ac.bg.fon.klijent.forme.LoginForma;
import rs.ac.bg.fon.klijent.glavnikontroler.GlavniKontroler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.klijent.komunikacija.Komunikacija;
import rs.ac.bg.fon.zajednicki.model.Bibliotekar;

/**
 * Kontroler zadužen za autentifikaciju i autorizaciju korisnika (bibliotekara) na sistem.
 * Inicira mrežnu konekciju, vrši validaciju unetih kredencijala i otvara glavni prozor aplikacije.
 * * @author Damjan
 */
public class LoginController {
    
    /**
     * Grafički prozor sa poljima za prijavu.
     */
    private final LoginForma lf;

    /**
     * Konstruktor koji preuzima instancu login forme i vezuje osluškivač na akciju prijave.
     * * @param lf Grafička login forma.
     */
    public LoginController(LoginForma lf) {
        this.lf = lf;
        addActionListener();
    }

    /**
     * Registruje osluškivač događaja na dugme za prijavu unutar login forme.
     */
    private void addActionListener() {
        lf.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prijava(e);
            }

            /**
             * Prikuplja uneti tekst iz tekstualnih polja, inicira mrežnu konekciju, vrši proveru kredencijala
             * i na osnovu rezultata otvara glavnu aplikaciju ili izbacuje poruku o grešci.
             */
            private void prijava(ActionEvent e) {
                String korisnickoIme = lf.getjTextFieldKorisnickoIme().getText().trim();
                String sifra = String.valueOf(lf.getjPasswordFieldSifra().getPassword());
                
                Komunikacija.getInstanca().konekcija();
                Bibliotekar ulogovani = null;
                try{
                    ulogovani = Komunikacija.getInstanca().login(korisnickoIme, sifra);
                }catch(Exception exc){
                    JOptionPane.showMessageDialog(lf, "Ne moze da se otvori glavna forma i meni", "GRESKA", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (ulogovani == null){
                    JOptionPane.showMessageDialog(lf, "Korisnicko ime i sifra nisu ispravni", "GRESKA", JOptionPane.ERROR_MESSAGE);
                }else{
                    GlavniKontroler.getInstanca().setUlogovani(ulogovani);
                    JOptionPane.showMessageDialog(lf, "Korisnicko ime i sifra su ispravni", "USPEH", JOptionPane.INFORMATION_MESSAGE);
                    try{
                        GlavniKontroler.getInstanca().otvoriGlavnuFormu();
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(lf, "Ne moze da se otvori glavna forma i meni", "GRESKA", JOptionPane.ERROR_MESSAGE);
                    }
                    lf.dispose();
                }
            }
        });
    }

    /**
     * Čini prozor za prijavu na sistem vidljivim korisniku.
     */
    public void otvoriFormu() {
        lf.setVisible(true);
    }
}