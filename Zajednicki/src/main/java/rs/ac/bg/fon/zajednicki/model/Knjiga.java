package rs.ac.bg.fon.zajednicki.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja domensku klasu Knjiga koja mapira odgovarajuću tabelu u bazi podataka.
 * Sadrži osnovne podatke o knjizi iz bibliotečkog fonda, uključujući naslov, autora
 * i cenu kazne u slučaju nepovraćaja knjige.
 * Implementira ApstraktniDomenskiObjekat interfejs.
 * * @author Damjan
 */
public class Knjiga implements ApstraktniDomenskiObjekat {
    
    /**
     * Jedinstveni identifikator knjige.
     */
    private int idKnjiga;
    
    /**
     * Naslov knjige.
     */
    private String naslov;
    
    /**
     * Autor knjige.
     */
    private String autor;
    
    /**
     * Novčani iznos koji se naplaćuje ukoliko čitalac trajno izgubi ili ne vrati knjigu.
     */
    private double cenaZaNepovracaj;

    /**
     * Podrazumevani konstruktor koji kreira prazan objekat klase Knjiga.
     */
    public Knjiga() {
    }

    /**
     * Konstruktor koji inicijalizuje knjigu sa svim pripadajućim atributima.
     * @param idKnjiga Jedinstveni identifikator knjige.
     * @param naslov Naslov knjige.
     * @param autor Autor knjige.
     * @param cenaZaNepovracaj Cena za nepovraćaj knjige.
     */
    public Knjiga(int idKnjiga, String naslov, String autor, double cenaZaNepovracaj) {
        this.idKnjiga = idKnjiga;
        this.naslov = naslov;
        this.autor = autor;
        this.cenaZaNepovracaj = cenaZaNepovracaj;
    }

    /**
     * Vraća jedinstveni identifikator knjige.
     * @return ID knjige kao int.
     */
    public int getIdKnjiga() {
        return idKnjiga;
    }

    /**
     * Postavlja jedinstveni identifikator knjige.
     * @param idKnjiga ID knjige.
     */
    public void setIdKnjiga(int idKnjiga) {
        this.idKnjiga = idKnjiga;
    }

    /**
     * Vraća naslov knjige.
     * @return Naslov knjige kao String.
     */
    public String getNaslov() {
        return naslov;
    }

    /**
     * Postavlja naslov knjige.
     * @param naslov Naslov knjige.
     */
    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    /**
     * Vraća autora knjige.
     * @return Ime i prezime autora kao String.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Postavlja autora knjige.
     * @param autor Autor knjige.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Vraća cenu za nepovraćaj knjige.
     * @return Cena kao double.
     */
    public double getCenaZaNepovracaj() {
        return cenaZaNepovracaj;
    }

    /**
     * Postavlja cenu za nepovraćaj knjige.
     * @param cenaZaNepovracaj Iznos kazne za nepovraćaj.
     */
    public void setCenaZaNepovracaj(double cenaZaNepovracaj) {
        this.cenaZaNepovracaj = cenaZaNepovracaj;
    }

    /**
     * Vraća kompletan tekstualni opis knjige (Naslov i autor).
     * @return Spojen naslov i autor kao String.
     */
    @Override
    public String toString() {
        return naslov + " " + autor;
    }

    /**
     * Generiše hash code vrednost za objekat Knjiga.
     * @return Hash code vrednost.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    /**
     * Poredi dve knjige na osnovu njihovog naslova i autora.
     * @param obj Objekat sa kojim se poredi.
     * @return true ukoliko su naslov i autor identični, inače false.
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
        final Knjiga other = (Knjiga) obj;
        if (!Objects.equals(this.naslov, other.naslov)) {
            return false;
        }
        return Objects.equals(this.autor, other.autor);
    }

    @Override
    public String vratiNazivTabele() {
        return "knjiga";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()){
            Knjiga k = new Knjiga();
            k.setIdKnjiga(rs.getInt("knjiga.idKnjiga"));
            k.setNaslov(rs.getString("knjiga.naslov"));
            k.setAutor(rs.getString("knjiga.autor"));
            k.setCenaZaNepovracaj(rs.getDouble("knjiga.cenaZaNepovracaj"));
            
            lista.add(k);
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "naslov,autor,cenaZaNepovracaj";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "('" + naslov + "','" + autor + "'," + cenaZaNepovracaj + ")";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "knjiga.idKnjiga=" + idKnjiga;
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
        return "naslov='" + naslov + "',autor='" + autor + "',cenaZaNepovracaj=" + cenaZaNepovracaj;
    }
}