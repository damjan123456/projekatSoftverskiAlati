package rs.ac.bg.fon.zajednicki.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja domensku klasu Mesto koja mapira geografska mesta stanovanja u bazi podataka.
 * Koristi se kao šifrarnik za povezivanje čitalaca sa odgovarajućim lokacijama.
 * Implementira ApstraktniDomenskiObjekat interfejs.
 * * @author Damjan
 */
public class Mesto implements ApstraktniDomenskiObjekat {
    
    /**
     * Jedinstveni identifikator geografskog mesta.
     */
    private int idMesto;
    
    /**
     * Naziv geografskog mesta.
     */
    private String naziv;

    /**
     * Podrazumevani konstruktor koji kreira prazan objekat klase Mesto.
     */
    public Mesto() {
    }

    /**
     * Konstruktor koji inicijalizuje objekat mesta sa definisanim ID-jem i nazivom.
     * @param idMesto Jedinstveni ID mesta.
     * @param naziv Naziv mesta.
     */
    public Mesto(int idMesto, String naziv) {
        this.idMesto = idMesto;
        this.naziv = naziv;
    }

    /**
     * Vraća jedinstveni identifikator mesta.
     * @return ID mesta kao int.
     */
    public int getIdMesto() {
        return idMesto;
    }

    /**
     * Postavlja jedinstveni identifikator mesta.
     * @param idMesto ID mesta.
     */
    public void setIdMesto(int idMesto) {
        this.idMesto = idMesto;
    }

    /**
     * Vraća naziv mesta.
     * @return Naziv mesta kao String.
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja naziv mesta.
     * @param naziv Naziv mesta.
     */
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    /**
     * Vraća tekstualni prikaz mesta (Naziv mesta).
     * @return Naziv mesta kao String.
     */
    @Override
    public String toString() {
        return naziv;
    }

    /**
     * Generiše hash code vrednost za objekat Mesto.
     * @return Hash code vrednost.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    /**
     * Poredi dva objekta mesta na osnovu njihovog naziva.
     * @param obj Objekat sa kojim se vrši poređenje.
     * @return true ukoliko mesta imaju isti naziv, inače false.
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
        final Mesto other = (Mesto) obj;
        return Objects.equals(this.naziv, other.naziv);
    }

    @Override
    public String vratiNazivTabele() {
        return "mesto";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()){
            Mesto m = new Mesto();
            m.setIdMesto(rs.getInt("mesto.idMesto"));
            m.setNaziv(rs.getString("mesto.naziv"));
            
            lista.add(m);
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "naziv";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "('" + naziv + "')";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "mesto.idMesto=" + idMesto;
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
        return "naziv='" + naziv + "'";
    }

    /**
     * @throws java.lang.UnsupportedOperationException Ukoliko metoda još uvek nije potpuno razvijena.
     */
    public boolean equalsIgnoreCase(String naziv) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}