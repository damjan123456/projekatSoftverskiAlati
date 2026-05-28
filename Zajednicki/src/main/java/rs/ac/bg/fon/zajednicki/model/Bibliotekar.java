package rs.ac.bg.fon.zajednicki.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja domensku klasu Bibliotekar koja mapira tabelu u bazi podataka.
 * Sadrži informacije o zaposlenom u biblioteci, uključujući lične podatke
 * i kredencijale za pristup klijentskoj aplikaciji.
 * Implementira ApstraktniDomenskiObjekat interfejs.
 * * @author damja
 */
public class Bibliotekar implements ApstraktniDomenskiObjekat {
    
    /**
     * Jedinstveni identifikator bibliotekara.
     */
    private int idBibliotekar;
    
    /**
     * Ime bibliotekara.
     */
    private String ime;
    
    /**
     * Prezime bibliotekara.
     */
    private String prezime;
    
    /**
     * Kontakt telefon bibliotekara.
     */
    private String brojTel;
    
    /**
     * Korisničko ime koje se koristi prilikom prijave na sistem.
     */
    private String korisnickoIme;
    
    /**
     * Lozinka koja se koristi prilikom prijave na sistem.
     */
    private String sifra;

    /**
     * Podrazumevani konstruktor koji kreira prazan objekat klase Bibliotekar.
     */
    public Bibliotekar() {
    }

    /**
     * Konstruktor koji postavlja korisničko ime i šifru bibliotekara.
     * @param korisnickoIme Korisničko ime za prijavu.
     * @param sifra Lozinka za prijavu.
     */
    public Bibliotekar(String korisnickoIme, String sifra) {
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
    }
 
    /**
     * Konstruktor koji inicijalizuje objekat Bibliotekar sa svim atributima.
     * @param idBibliotekar Jedinstveni ID bibliotekara.
     * @param ime Ime bibliotekara.
     * @param prezime Prezime bibliotekara.
     * @param brojTel Kontakt telefon.
     * @param korisnickoIme Korisničko ime.
     * @param sifra Lozinka.
     */
    public Bibliotekar(int idBibliotekar, String ime, String prezime, String brojTel, String korisnickoIme, String sifra) {
        this.idBibliotekar = idBibliotekar;
        this.ime = ime;
        this.prezime = prezime;
        this.brojTel = brojTel;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
    }

    /**
     * Vraća tekstualnu reprezentaciju bibliotekara (Ime i prezime).
     * @return Ime i prezime bibliotekara kao String.
     */
    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    /**
     * Vraća jedinstveni identifikator bibliotekara.
     * * @return ID bibliotekara kao int.
     */
    public int getIdBibliotekar() {
        return idBibliotekar;
    }

    /**
     * Postavlja jedinstveni identifikator bibliotekara.
     * @param idBibliotekar ID bibliotekara.
     */
    public void setIdBibliotekar(int idBibliotekar) {
        this.idBibliotekar = idBibliotekar;
    }

    /**
     * Vraća ime bibliotekara.
     * @return Ime kao String.
     */
    public String getIme() {
        return ime;
    }

    /**
     * Postavlja ime bibliotekara.
     * * @param ime Ime bibliotekara.
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    /**
     * Vraća prezime bibliotekara.
     * @return Prezime kao String.
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Postavlja prezime bibliotekara.
     * @param prezime Prezime bibliotekara.
     */
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    /**
     * Vraća kontakt telefon bibliotekara.
     * @return Broj telefona kao String.
     */
    public String getBrojTel() {
        return brojTel;
    }

    /**
     * Postavlja kontakt telefon bibliotekara.
     * @param brojTel Broj telefona.
     */
    public void setBrojTel(String brojTel) {
        this.brojTel = brojTel;
    }

    /**
     * Vraća korisničko ime bibliotekara.
     * @return Korisničko ime kao String.
     */
    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    /**
     * Postavlja korisničko ime bibliotekara.
     * @param korisnickoIme Korisničko ime za sistem.
     */
    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    /**
     * Vraća lozinku bibliotekara.
     * @return Lozinka kao String.
     */
    public String getSifra() {
        return sifra;
    }

    /**
     * Postavlja lozinku bibliotekara.
     *@param sifra Lozinka za pristup sistemu.
     */
    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    /**
     * Generiše hash code vrednost za objekat Bibliotekar.
     * @return Hash code vrednost.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    /**
     * Poredi dva objekta klase Bibliotekar na osnovu njihovog korisničkog imena i šifre.
     * @param obj Objekat sa kojim se vrši poređenje.
     * @return true ako su objekti isti ili imaju isto korisničko ime i šifru, inače false.
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
        final Bibliotekar other = (Bibliotekar) obj;
        if (!Objects.equals(this.korisnickoIme, other.korisnickoIme)) {
            return false;
        }
        return Objects.equals(this.sifra, other.sifra);
    }

    @Override
    public String vratiNazivTabele() {
        return "bibliotekar";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()){
            Bibliotekar b = new Bibliotekar();
            b.setIdBibliotekar(rs.getInt("bibliotekar.idBibliotekar"));
            b.setIme(rs.getString("bibliotekar.ime"));
            b.setPrezime(rs.getString("bibliotekar.prezime"));
            b.setBrojTel(rs.getString("bibliotekar.brojTel"));
            b.setKorisnickoIme(rs.getString("bibliotekar.korisnickoIme"));
            b.setSifra(rs.getString("bibliotekar.sifra"));
            
            lista.add(b);
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "ime,prezime,brojTel,korisnickoIme,sifra";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "('" + ime + "','" + prezime + "','" + brojTel + "','" + korisnickoIme + "','" + sifra + "')";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "bibliotekar.idBibliotekar=" + idBibliotekar;
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
        return "ime='" + ime + "',prezime='" + prezime + "',brojTel='" + brojTel + "',korisnickoIme='" + korisnickoIme + "',sifra='" + sifra + "'";
    } 
}