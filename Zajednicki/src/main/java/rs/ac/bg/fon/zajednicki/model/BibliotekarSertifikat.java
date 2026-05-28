package rs.ac.bg.fon.zajednicki.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja asocijativnu klasu koja povezuje klase Bibliotekar
 * i Sertifikat. Sadrži podatak o datumu izdavanja konkretnog sertifikata određenom bibliotekaru.
 * Implementira ApstraktniDomenskiObjekat interfejs.
 * * @author Damjan
 */
public class BibliotekarSertifikat implements ApstraktniDomenskiObjekat {
    
    /**
     * Bibliotekar na koga se sertifikat odnosi.
     */
    private Bibliotekar bibliotekar;
    
    /**
     * Sertifikat koji je dodeljen bibliotekaru.
     */
    private Sertifikat sertifikat;
    
    /**
     * Datum kada je sertifikat izdat bibliotekaru.
     */
    private Date datumIzdavanja;

    /**
     * Podrazumevani konstruktor koji kreira prazan objekat klase BibliotekarSertifikat.
     */
    public BibliotekarSertifikat() {
    }

    /**
     * Konstruktor koji inicijalizuje objekat asocijativne klase sa konkretnim vrednostima.
     * @param bibliotekar Objekat bibliotekara kome se dodeljuje sertifikat.
     * @param sertifikat Objekat sertifikata koji se dodeljuje.
     * @param datumIzdavanja Datum izdavanja sertifikata.
     */
    public BibliotekarSertifikat(Bibliotekar bibliotekar, Sertifikat sertifikat, Date datumIzdavanja) {
        this.bibliotekar = bibliotekar;
        this.sertifikat = sertifikat;
        this.datumIzdavanja = datumIzdavanja;
    }

    /**
     * Vraća objekat bibliotekara.
     * @return Bibliotekar povezan sa ovim sertifikatom.
     */
    public Bibliotekar getBibliotekar() {
        return bibliotekar;
    }

    /**
     * Postavlja objekat bibliotekara.
     * @param bibliotekar Objekat bibliotekara.
     */
    public void setBibliotekar(Bibliotekar bibliotekar) {
        this.bibliotekar = bibliotekar;
    }

    /**
     * Vraća objekat sertifikata.
     * @return Sertifikat koji poseduje bibliotekar.
     */
    public Sertifikat getSertifikat() {
        return sertifikat;
    }

    /**
     * Postavlja objekat sertifikata.
     * @param sertifikat Objekat sertifikata.
     */
    public void setSertifikat(Sertifikat sertifikat) {
        this.sertifikat = sertifikat;
    }

    /**
     * Vraća datum izdavanja sertifikata.
     * @return Datum izdavanja kao Date.
     */
    public Date getDatumIzdavanja() {
        return datumIzdavanja;
    }

    /**
     * Postavlja datum izdavanja sertifikata.
     * @param datumIzdavanja Datum izdavanja.
     */
    public void setDatumIzdavanja(Date datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }

    /**
     * Vraća tekstualni prikaz veze između bibliotekara i njegovog sertifikata.
     * @return Podaci o vezi kao String.
     */
    @Override
    public String toString() {
        return bibliotekar + ", sertifikat=" + sertifikat + ", datumIzdavanja=" + datumIzdavanja;
    }

    /**
     * Generiše hash code vrednost za objekat BibliotekarSertifikat.
     * @return Hash code vrednost.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    /**
     * Poredi dva objekta klase BibliotekarSertifikat na osnovu bibliotekara,
     * sertifikata i datuma izdavanja.
     * @param obj Objekat sa kojim se poredi.
     * @return true ukoliko su svi pomenuti atributi identični, inače false.
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
        final BibliotekarSertifikat other = (BibliotekarSertifikat) obj;
        if (!Objects.equals(this.bibliotekar, other.bibliotekar)) {
            return false;
        }
        if (!Objects.equals(this.sertifikat, other.sertifikat)) {
            return false;
        }
        return Objects.equals(this.datumIzdavanja, other.datumIzdavanja);
    }

    @Override
    public String vratiNazivTabele() {
        return "bibliotekarsertifikat";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()){
            BibliotekarSertifikat bs = new BibliotekarSertifikat();
            bs.setDatumIzdavanja(rs.getDate("bibliotekarsertifikat.datumIzdavanja"));
            
            Bibliotekar b = new Bibliotekar();
            b.setIdBibliotekar(rs.getInt("bibliotekar.idBibliotekar"));
            b.setIme(rs.getString("bibliotekar.ime"));
            b.setPrezime(rs.getString("bibliotekar.prezime"));
            b.setBrojTel(rs.getString("bibliotekar.brojTel"));
            b.setKorisnickoIme(rs.getString("bibliotekar.korisnickoIme"));
            b.setSifra(rs.getString("bibliotekar.sifra"));
            bs.setBibliotekar(b);
            
            Sertifikat s = new Sertifikat();
            s.setIdSertifikat(rs.getInt("sertifikat.idSertifikat"));
            s.setNaziv(rs.getString("sertifikat.naziv"));
            s.setInstitucija(rs.getString("sertifikat.institucija"));
            bs.setSertifikat(s);
            
            lista.add(bs);
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "idBibliotekar,idSertifikat,datumIzdavanja";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "(" + bibliotekar.getIdBibliotekar() + "," + sertifikat.getIdSertifikat() + ",'" + datumIzdavanja + "')";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "bibliotekarsertifikat.idBibliotekar=" + bibliotekar.getIdBibliotekar() + " AND bibliotekarsertifikat.idSertifikat=" + sertifikat.getIdSertifikat();
    }

    /**
     * @throws java.lang.UnsupportedOperationException Metoda još uvek nije implementirana u ovoj klasi.
     */
    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "idBibliotekar=" + bibliotekar.getIdBibliotekar() + ",idSertifikat=" + sertifikat.getIdSertifikat() + ",datumIzdavanja='" + datumIzdavanja + "'";
    }
}