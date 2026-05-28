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
        this.ime = ime;
        this.prezime = prezime;
        this.brojTel = brojTel;
        this.mesto = mesto;
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
     */
    public void setIme(String ime) {
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
     */
    public void setPrezime(String prezime) {
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
     */
    public void setBrojTel(String brojTel) {
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
     */
    public void setMesto(Mesto mesto) {
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