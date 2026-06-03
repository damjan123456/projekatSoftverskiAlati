package rs.ac.bg.fon.zajednicki.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Predstavlja domensku klasu StavkaZapisaOIznajmljivanju koja mapira slab objekat (Detail) u bazi podataka.
 * Odnosi se na pojedinačnu stavku unutar zapisa o iznajmljivanju i povezuje ga sa konkretnom iznajmljenom knjigom,
 * prateći rokove i statuse vraćanja.
 * Implementira ApstraktniDomenskiObjekat interfejs.
 * * @author damja
 */
public class StavkaZapisaOIznajmljivanju implements ApstraktniDomenskiObjekat {
    
    /**
     * Redni broj stavke u okviru jednog konkretnog zapisa o iznajmljivanju.
     */
    private int rb;
    
    /**
     * Identifikator matičnog zapisa o iznajmljivanju kome ova stavka pripada.
     */
    private int idZapis;
    
    /**
     * Količina iznajmljenih primeraka određene knjige na ovoj stavci.
     */
    private int kolicina;
    
    /**
     * Finansijski iznos obračunat za ovu stavku.
     */
    private double iznos;
    
    /**
     * Stvarni datum kada je čitalac vratio knjigu iz ove stavke.
     */
    private Date datumVracanja;
    
    /**
     * Krajnji rok do kojeg je čitalac u obavezi da vrati knjigu.
     */
    private Date maxDatumVracanja;
    
    /**
     * Status koji pokazuje da li je knjiga vraćena pre ili u okviru dozvoljenog roka.
     */
    private boolean vracenoNaVreme;
    
    /**
     * Cena kazne koja se naplaćuje ako knjiga iz stavke bude nepovratno oštećena ili izgubljena.
     */
    private double cenaZaNepovracaj;
    
    /**
     * Konkretna knjiga na koju se odnosi ova stavka zapisa.
     */
    private Knjiga knjiga;

    /**
     * Podrazumevani konstruktor koji kreira prazan objekat klase StavkaZapisaOIznajmljivanju.
     */
    public StavkaZapisaOIznajmljivanju() {
    }

    /**
     * Konstruktor koji inicijalizuje stavku zapisa o iznajmljivanju sa svim pripadajućim atributima.
     * @param idZapis ID matičnog zapisa.
     * @param rb Redni broj stavke.
     * @param kolicina Broj iznajmljenih primeraka.
     * @param iznos Finansijski iznos stavke.
     * @param datumVracanja Stvarni datum vraćanja knjige.
     * @param maxDatumVracanja Krajnji rok za vraćanje knjige.
     * @param vracenoNaVreme Logički status blagovremenog vraćanja.
     * @param cenaZaNepovracaj Vrednost kazne za gubitak knjige.
     * @param knjiga Konkretan objekat knjige.
     */
    public StavkaZapisaOIznajmljivanju(int idZapis, int rb, int kolicina, double iznos, Date datumVracanja, 
            Date maxDatumVracanja, boolean vracenoNaVreme, double cenaZaNepovracaj, Knjiga knjiga) {
        setZapis(idZapis);
        setRb(rb);
        setKolicina(kolicina);
        setIznos(iznos);
        setDatumVracanja(datumVracanja);
        setMaxDatumVracanja(maxDatumVracanja);
        setVracenoNaVreme(vracenoNaVreme);
        setCenaZaNepovracaj(cenaZaNepovracaj);
        setKnjiga(knjiga);
    }

    /**
     * Vraća identifikator matičnog zapisa o iznajmljivanju.
     * @return ID zapisa kao int.
     */
    public int getZapis() {
        return idZapis;
    }

    /**
     * Postavlja identifikator matičnog zapisa o iznajmljivanju.
     * @param zapis ID zapisa.
     * @throws java.lang.IllegalArgumentException Ukoliko je ID zapisa negativan ili nula.
     */
    public void setZapis(int zapis) {
        if (zapis <= 0) {
            throw new IllegalArgumentException("ID zapisa mora biti pozitivan broj.");
        }
        this.idZapis = zapis;
    }

    /**
     * Vraća redni broj stavke.
     * @return Redni broj kao int.
     */
    public int getRb() {
        return rb;
    }

    /**
     * Postavlja redni broj stavke.
     * @param rb Redni broj stavke.
     * @throws java.lang.IllegalArgumentException Ukoliko je redni broj negativan ili nula.
     */
    public void setRb(int rb) {
        if (rb <= 0) 
            throw new IllegalArgumentException("Redni broj stavke mora biti pozitivan.");
        this.rb = rb;
    }

    /**
     * Vraća iznajmljenu količinu knjiga.
     * @return Količina kao int.
     */
    public int getKolicina() {
        return kolicina;
    }

    /**
     * Postavlja iznajmljenu količinu knjiga.
     * @param kolicina Broj primeraka knjige.
     * @throws java.lang.IllegalArgumentException Ukoliko je količina negativna, nula ili veća od 10.
     */
    public void setKolicina(int kolicina) {
        if (kolicina <= 0 || kolicina > 10) {
            throw new IllegalArgumentException("Količina mora biti veća od nule ili manja od 11.");
        }
        this.kolicina = kolicina;
    }

    /**
     * Vraća finansijski iznos stavke.
     * @return Iznos stavke kao double.
     */
    public double getIznos() {
        return iznos;
    }

    /**
     * Postavlja finansijski iznos stavke.
     * @param iznos Vrednost stavke.
     * @throws java.lang.IllegalArgumentException Ukoliko je iznos negativan.
     */
    public void setIznos(double iznos) {
        if (iznos < 0) {
            throw new IllegalArgumentException("Iznos ne sme biti negativan.");
        }
        this.iznos = iznos;
    }

    /**
     * Vraća stvarni datum kada je knjiga iz stavke vraćena.
     * @return Stvarni datum vraćanja.
     */
    public Date getDatumVracanja() {
        return datumVracanja;
    }

    /**
     * Postavlja stvarni datum kada je knjiga iz stavke vraćena.
     * @param datumVracanja Stvarni datum vraćanja.
     */
    public void setDatumVracanja(Date datumVracanja) {
        this.datumVracanja = datumVracanja;
    }

    /**
     * Vraća maksimalni dozvoljeni rok za vraćanje knjige.
     * @return Krajnji rok za vraćanje.
     */
    public Date getMaxDatumVracanja() {
        return maxDatumVracanja;
    }

    /**
     * Postavlja maksimalni dozvoljeni rok za vraćanje knjige.
     * @param maxDatumVracanja Krajnji rok.
     * @throws java.lang.IllegalArgumentException Ukoliko je maksimalni datum vraćanja null.
     */
    public void setMaxDatumVracanja(Date maxDatumVracanja) {
        if (maxDatumVracanja == null) {
            throw new IllegalArgumentException("Maksimalni datum vraćanja ne sme biti null.");
        }
        this.maxDatumVracanja = maxDatumVracanja;
    }

    /**
     * Vraća podatak o tome da li je knjiga vraćena u roku.
     * @return true ukoliko je knjiga blagovremeno vraćena, inače false.
     */
    public boolean isVracenoNaVreme() {
        return vracenoNaVreme;
    }

    /**
     * Postavlja logičku vrednost blagovremenog vraćanja knjige.
     * @param vracenoNaVreme Status vraćanja na vreme.
     */
    public void setVracenoNaVreme(boolean vracenoNaVreme) {
        this.vracenoNaVreme = vracenoNaVreme;
    }

    /**
     * Vraća cenu kazne za nepovraćaj knjige iz ove stavke.
     * @return Cena kazne kao double.
     */
    public double getCenaZaNepovracaj() {
        return cenaZaNepovracaj;
    }

    /**
     * Postavlja cenu kazne za nepovraćaj knjige iz ove stavke.
     * @param cenaZaNepovracaj Iznos kazne.
     * @throws java.lang.IllegalArgumentException Ukoliko je cena kazne negativna.
     */
    public void setCenaZaNepovracaj(double cenaZaNepovracaj) {
        if (cenaZaNepovracaj < 0) {
            throw new IllegalArgumentException("Cena za nepovraćaj ne sme biti negativna.");
        }
        this.cenaZaNepovracaj = cenaZaNepovracaj;
    }

    /**
     * Vraća konkretnu knjigu na koju se stavka odnosi.
     * @return Objekat klase Knjiga.
     */
    public Knjiga getKnjiga() {
        return knjiga;
    }

    /**
     * Postavlja knjigu za ovu stavku zapisa.
     * @param knjiga Objekat knjige.
     * @throws java.lang.IllegalArgumentException Ukoliko je knjiga null.
     */
    public void setKnjiga(Knjiga knjiga) {
        if (knjiga == null) {
            throw new IllegalArgumentException("Knjiga na stavci ne sme biti null.");
        }
        this.knjiga = knjiga;
    }

    /**
     * Vraća tekstualni opis stavke (Naslov i autor knjige).
     * @return Tekstualna reprezentacija stavke.
     */
    @Override
    public String toString() {
        return knjiga.toString();
    }

    @Override
    public String vratiNazivTabele() {
        return "stavkazapisaoiznajmljivanju";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()){
            StavkaZapisaOIznajmljivanju stavka = new StavkaZapisaOIznajmljivanju();
            stavka.setZapis(rs.getInt("stavkazapisaoiznajmljivanju.idZapis"));
            stavka.setRb(rs.getInt("stavkazapisaoiznajmljivanju.rb"));
            stavka.setDatumVracanja(rs.getDate("stavkazapisaoiznajmljivanju.datumVracanja"));
            stavka.setMaxDatumVracanja(rs.getDate("stavkazapisaoiznajmljivanju.maxDatumVracanja"));
            stavka.setKolicina(rs.getInt("stavkazapisaoiznajmljivanju.kolicina"));
            stavka.setIznos(rs.getDouble("stavkazapisaoiznajmljivanju.iznos"));
            stavka.setCenaZaNepovracaj(rs.getDouble("stavkazapisaoiznajmljivanju.cenaZaNepovracaj"));
            stavka.setVracenoNaVreme(rs.getBoolean("stavkazapisaoiznajmljivanju.vracenoNaVreme"));
            
            Knjiga k = new Knjiga();
            k.setIdKnjiga(rs.getInt("knjiga.idKnjiga"));
            k.setNaslov(rs.getString("knjiga.naslov"));
            k.setAutor(rs.getString("knjiga.autor"));
            k.setCenaZaNepovracaj(rs.getDouble("knjiga.cenaZaNepovracaj"));
            
            stavka.setKnjiga(k);
            
            lista.add(stavka);
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "rb,idZapis,datumVracanja,maxDatumVracanja,kolicina,iznos,cenaZaNepovracaj,vracenoNaVreme,idKnjiga";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        java.sql.Date sqlDatum1 = new java.sql.Date(datumVracanja.getTime());
        java.sql.Date sqlDatum2 = new java.sql.Date(maxDatumVracanja.getTime());

        return "(" + rb + "," + idZapis + ",'" + sqlDatum1 + "','" + sqlDatum2 + "'," + kolicina + "," + iznos + "," + cenaZaNepovracaj + "," + vracenoNaVreme + "," + knjiga.getIdKnjiga() + ")";    
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "rb=" + rb + " AND idZapis=" + idZapis;
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
        java.sql.Date sqlDatum1 = new java.sql.Date(datumVracanja.getTime());
        java.sql.Date sqlDatum2 = new java.sql.Date(maxDatumVracanja.getTime());
        return "rb=" + rb + ",idZapis=" + idZapis + ",datumVracanja='" + sqlDatum1 + "',maxDatumVracanja='" + sqlDatum2 + "',kolicina=" + kolicina + ",iznos=" + iznos + ",cenaZaNepovracaj=" + cenaZaNepovracaj + ",vracenoNaVreme=" + vracenoNaVreme;    
    }

    /**
     * Generiše hash code vrednost za stavku na osnovu fiksnog multiplikatora.
     * @return Hash code vrednost stavke.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    /**
     * Poredi dve stavke zapisa na osnovu rednog broja i identifikatora matičnog zapisa.
     * @param obj Objekat sa kojim se vrši poređenje.
     * @return true ukoliko stavke pripadaju istom zapisu i imaju isti redni broj, inače false.
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
        final StavkaZapisaOIznajmljivanju other = (StavkaZapisaOIznajmljivanju) obj;
        if (this.rb != other.rb) {
            return false;
        }
        return this.idZapis == other.idZapis;
    }
}