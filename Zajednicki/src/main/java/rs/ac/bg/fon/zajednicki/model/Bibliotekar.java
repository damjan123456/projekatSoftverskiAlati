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
 * * @author Damjan
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
        setKorisnickoIme(korisnickoIme);
        setSifra(sifra);
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
        setIdBibliotekar(idBibliotekar);
        setIme(ime);
        setPrezime(prezime);
        setBrojTel(brojTel);
        setKorisnickoIme(korisnickoIme);
        setSifra(sifra);
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
     * @throws IllegalArgumentException Ako je ime null ili prazno.
     * @throws IllegalArgumentException Ako ime ima 5 ili manje karaktera.
     */
    public void setIme(String ime) {
        if (ime == null || ime.trim().isEmpty()) {
            throw new IllegalArgumentException("Ime ne sme biti null ili prazno.");
        }
        if (ime.trim().length() <= 2){
            throw new IllegalArgumentException("Ime mora imati više od 2 karaktera.");
        }
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
     * @throws IllegalArgumentException Ako je prezime null ili prazno.
     * @throws IllegalArgumentException Ako prezime ima 5 ili manje karaktera.
     */
    public void setPrezime(String prezime) {
        if (prezime == null || prezime.trim().isEmpty()) {
            throw new IllegalArgumentException("Ime ne sme biti null ili prazno.");
        }
        if (prezime.trim().length() <= 2){
            throw new IllegalArgumentException("Ime mora imati više od 2 karaktera.");
        }
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
     * @throws java.lang.IllegalArgumentException Ako je broj telefona null ili prazan.
     * @throws java.lang.IllegalArgumentException Ako broj telefona nema 9 ili 10 cifara.
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
     * Vraća korisničko ime bibliotekara.
     * @return Korisničko ime kao String.
     */
    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    /**
     * Postavlja korisničko ime bibliotekara.
     * @param korisnickoIme Korisničko ime za sistem.
     * @throws java.lang.IllegalArgumentException Ako je korisničko ime null ili prazno.
     */
    public void setKorisnickoIme(String korisnickoIme) {
        if (korisnickoIme == null || korisnickoIme.trim().isEmpty()) {
            throw new IllegalArgumentException("Korisničko ime ne sme biti null niti prazno.");
        }
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
    * @throws java.lang.IllegalArgumentException Ako je šifra null.
        * @throws java.lang.IllegalArgumentException Ako šifra ima manje od 5 karaktera.
     */
    public void setSifra(String sifra) {
        if (sifra == null) {
            throw new IllegalArgumentException("Sifra ne sme biti null.");
        }
        if (sifra.length() < 5) {
            throw new IllegalArgumentException("Sifra mora da sadrzati najmanje 5 karaktera.");
        }
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
