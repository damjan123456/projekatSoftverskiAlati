package rs.ac.bg.fon.zajednicki.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja domensku klasu Sertifikat koja mapira tabelu sertifikata u bazi podataka.
 * Beleži informacije o zvaničnim stručnim sertifikatima koje bibliotekari mogu da poseduju,
 * kao i o institucijama koje su ih izdale.
 * Implementira ApstraktniDomenskiObjekat interfejs.
 * * @author damja
 */
public class Sertifikat implements ApstraktniDomenskiObjekat {
    
    /**
     * Jedinstveni identifikator sertifikata.
     */
    private int idSertifikat;
    
    /**
     * Naziv stručnog sertifikata.
     */
    private String naziv;
    
    /**
     * Naziv izdavačke institucije ili organizacije koja je izdala sertifikat.
     */
    private String institucija;

    /**
     * Podrazumevani konstruktor koji kreira prazan objekat klase Sertifikat.
     */
    public Sertifikat() {
    }

    /**
     * Konstruktor koji kreira objekat sertifikata sa specifičnim nazivom i institucijom.
     * @param naziv Naziv stručnog sertifikata.
     * @param institucija Naziv obrazovne ustanove/organizacije.
     */
    public Sertifikat(String naziv, String institucija) {
        this.naziv = naziv;
        this.institucija = institucija;
    }

    /**
     * Vraća jedinstveni identifikator sertifikata.
     * @return ID sertifikata kao int.
     */
    public int getIdSertifikat() {
        return idSertifikat;
    }

    /**
     * Postavlja jedinstveni identifikator sertifikata.
     * @param idSertifikat ID sertifikata.
     */
    public void setIdSertifikat(int idSertifikat) {
        this.idSertifikat = idSertifikat;
    }

    /**
     * Vraća naziv sertifikata.
     * @return Naziv sertifikata kao String.
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv sertifikata.
     * @param naziv Naziv sertifikata.
     * @throws java.lang.IllegalArgumentException Ukoliko je naziv null ili prazan string.
     */
    public void setNaziv(String naziv) {
        if (naziv == null || naziv.trim().isEmpty()) {
            throw new IllegalArgumentException("Naziv sertifikata ne sme biti null niti prazan.");
        }
        this.naziv = naziv;
    }

    /**
     * Vraća naziv institucije koja je izdala sertifikat.
     * @return Naziv institucije kao String.
     */
    public String getInstitucija() {
        return institucija;
    }

    /**
     * Postavlja naziv institucije koja izdaje sertifikat.
     * @param institucija Naziv izdavaoca sertifikata.
     * @throws java.lang.IllegalArgumentException Ukoliko je naziv institucije null ili prazan string.
     */
    public void setInstitucija(String institucija) {
        if (institucija == null || institucija.trim().isEmpty()) {
            throw new IllegalArgumentException("Institucija sertifikata ne sme biti null niti prazan.");
        }
        this.institucija = institucija;
    }

    /**
     * Vraća tekstualni prikaz sertifikata (Naziv sertifikata).
     * @return Naziv sertifikata kao String.
     */
    @Override
    public String toString() {
        return naziv;
    }

    /**
     * Generiše hash code vrednost za objekat Sertifikat.
     * @return Hash code vrednost.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    /**
     * Poredi dva sertifikata na osnovu njihovog naziva.
     * @param obj Objekat sa kojim se vrši poređenje.
     * @return true ukoliko oba objekta dele isti naziv, inače false.
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
        final Sertifikat other = (Sertifikat) obj;
        return Objects.equals(this.naziv, other.naziv);
    }

    @Override
    public String vratiNazivTabele() {
        return "sertifikat";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()){
            Sertifikat s = new Sertifikat();
            s.setIdSertifikat(rs.getInt("sertifikat.idSertifikat"));
            s.setNaziv(rs.getString("sertifikat.naziv"));
            s.setInstitucija(rs.getString("sertifikat.institucija"));
            
            lista.add(s);
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "naziv,institucija";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "('" + naziv + "','" + institucija + "')";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "sertifikat.idSertifikat=" + idSertifikat;
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
        return "naziv='" + naziv + "',institucija='" + institucija + "'";
    }
}