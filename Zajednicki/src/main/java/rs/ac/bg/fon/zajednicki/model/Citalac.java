package rs.ac.bg.fon.zajednicki.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja domensku klasu Čitalac koja mapira istoimenu tabelu u bazi podataka.
 * Sadrži podatke o članu biblioteke, uključujući lične podatke i mesto stanovanja.
 * Implementira ApstraktniDomenskiObjekat interfejs.
 * * @author Damjan
 */
public class Citalac implements ApstraktniDomenskiObjekat {
    
    /**
     * Jedinstveni identifikator čitaoca.
     */
    private int idCitalac;
    
    /**
     * Ime čitaoca.
     */
    private String ime;
    
    /**
     * Prezime čitaoca.
     */
    private String prezime;
    
    /**
     * Kontakt telefon čitaoca.
     */
    private String brojTel;
    
    /**
     * Geografsko mesto u kojem čitalac stanuje (Strani ključ u bazi podataka).
     */
    private Mesto mesto;

    /**
     * Podrazumevani konstruktor koji kreira prazan objekat klase Citalac.
     */
    public Citalac() {
    }

    /**
     * Konstruktor koji inicijalizuje čitaoca sa osnovnim ličnim podacima i mestom.
     * @param ime Ime čitaoca.
     * @param prezime Prezime čitaoca.
     * @param brojTel Kontakt telefon čitaoca.
     * @param mesto Objekat klase Mesto u kome čitalac živi.
     */
    public Citalac(String ime, String prezime, String brojTel, Mesto mesto) {
        setIme(ime);
        setPrezime(prezime);
        setBrojTel(brojTel);
        setMesto(mesto);
    }

    /**
     * Vraća jedinstveni identifikator čitaoca.
     * @return ID čitaoca kao int.
     */
    public int getIdCitalac() {
        return idCitalac;
    }

    /**
     * Postavlja jedinstveni identifikator čitaoca.
     * @param idCitalac ID čitaoca.
     */
    public void setIdCitalac(int idCitalac) {
        this.idCitalac = idCitalac;
    }

    /**
     * Vraća ime čitaoca.
     * @return Ime čitaoca kao String.
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime čitaoca.
     * @param ime Ime čitaoca.
     * @throws java.lang.IllegalArgumentException Ukoliko je prosleđeno ime null ili prazno. 
     * @throws java.lang.IllegalArgumentException Ukoliko je prosleđeno ime kraće od 5 karaktera.
     */
    public void setIme(String ime) {
        if (ime == null || ime.trim().isEmpty()) {
            throw new IllegalArgumentException("Ime čitaoca ne sme biti null niti prazno.");
        }
        if (ime.trim().length() <= 2) {
            throw new IllegalArgumentException("Ime čitaoca mora imati više od 2 karaktera.");
        }
        this.ime = ime;
    }

    /**
     * Vraća prezime čitaoca.
     * @return Prezime čitaoca kao String.
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime čitaoca.
     * @param prezime Prezime čitaoca.
     * @throws java.lang.IllegalArgumentException Ukoliko je prosleđeno prezime null ili prazno.
     * @throws java.lang.IllegalArgumentException Ukoliko je prosleđeno prezime kraće od 2 karaktera.
     */
    public void setPrezime(String prezime) {
        if (prezime == null || prezime.trim().isEmpty()) {
            throw new IllegalArgumentException("Prezime čitaoca ne sme biti null niti prazno.");
        }
        if (prezime.trim().length() <= 2) {
            throw new IllegalArgumentException("Prezime čitaoca mora imati više od 2 karaktera.");
        }
        this.prezime = prezime;
    }

    /**
     * Vraća kontakt telefon čitaoca.
     * @return Broj telefona kao String.
     */
    public String getBrojTel() {
        return brojTel;
    }

    /**
     * Postavlja kontakt telefon čitaoca.
     * @param brojTel Broj telefona čitaoca.
     * @throws java.lang.IllegalArgumentException Ukoliko je prosleđeni broj telefona null ili prazan.
     * @throws java.lang.IllegalArgumentException Ukoliko broj telefona nema 9 ili 10 cifara.
     */
    public void setBrojTel(String brojTel) {
        if (brojTel == null || brojTel.trim().isEmpty()) {
            throw new IllegalArgumentException("Broj telefona ne sme biti null ili prazan.");
        }
        if (brojTel.trim().length() < 9 || brojTel.trim().length() > 10) {
            throw new IllegalArgumentException("Broj mora imati 9 ili 10 cifara.");
        }
        this.brojTel = brojTel;
    }

    /**
     * Vraća geografsko mesto stanovanja čitaoca.
     * @return Mesto u kome čitalac živi.
     */
    public Mesto getMesto() {
        return mesto;
    }

    /**
     * Postavlja geografsko mesto stanovanja čitaoca.
     * @param mesto Objekat mesta koji se dodeljuje čitaocu.
     * @throws java.lang.IllegalArgumentException Ukoliko je prosleđeni objekat mesta null.
     */
    public void setMesto(Mesto mesto) {
        if (mesto == null) {
            throw new IllegalArgumentException("Mesto čitaoca ne sme biti null.");
        }
        this.mesto = mesto;
    }

    /**
     * Generiše hash code vrednost za objekat Citalac.
     * @return Hash code vrednost.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    /**
     * Poredi dva čitaoca na osnovu njihovog broja telefona.
     * @param obj Objekat sa kojim se vrši poređenje.
     * @return true ako objekti imaju isti broj telefona, inače false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Citalac other = (Citalac) obj;
        return Objects.equals(this.brojTel, other.brojTel);
    }

    /**
     * Vraća tekstualnu reprezentaciju čitaoca (Ime i prezime).
     * * @return Ime i prezime čitaoca kao {@link String}.
     */
    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
    public String vratiNazivTabele() {
        return "citalac";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()){
            Citalac c = new Citalac();
            c.setIdCitalac(rs.getInt("citalac.idCitalac"));
            c.setIme(rs.getString("citalac.ime"));
            c.setPrezime(rs.getString("citalac.prezime"));
            c.setBrojTel(rs.getString("citalac.brojTel"));
            
            Mesto m = new Mesto();
            m.setIdMesto(rs.getInt("mesto.idMesto"));
            m.setNaziv(rs.getString("mesto.naziv"));
            c.setMesto(m);
            
            lista.add(c);
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "ime,prezime,brojTel,idMesto";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "('" + ime + "','" + prezime + "','" + brojTel + "'," + mesto.getIdMesto() + ")";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "citalac.idCitalac=" + idCitalac;
    }

    /**
     * @throws java.lang.UnsupportedOperationException Metoda još uvek nije podržana/implementirana u ovoj klasi.
     */
    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "ime='" + ime + "',prezime='" + prezime + "',brojTel='" + brojTel + "',idMesto=" + mesto.getIdMesto();
    }
}