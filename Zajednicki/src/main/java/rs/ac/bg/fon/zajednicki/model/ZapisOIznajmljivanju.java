package rs.ac.bg.fon.zajednicki.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Predstavlja domensku klasu ZapisOIznajmljivanju koja mapira glavnu tabelu (Master) u bazi podataka.
 * Beleži zaglavlje dokumenta o iznajmljivanju knjiga čitaocu od strane zaduženog bibliotekara.
 * Implementira ApstraktniDomenskiObjekat interfejs.
 * * @author damja
 */
public class ZapisOIznajmljivanju implements ApstraktniDomenskiObjekat {
    
    /**
     * Jedinstveni identifikator zapisa o iznajmljivanju.
     */
    private int idZapis;
    
    /**
     * Datum kada je iznajmljivanje knjiga izvršeno.
     */
    private Date datumIznajmljivanja;
    
    /**
     * Ukupan finansijski iznos (suma svih stavki) za dati zapis.
     */
    private double ukupanIznos;
    
    /**
     * Bibliotekar koji je obradio i evidentirao iznajmljivanje.
     */
    private Bibliotekar bibliotekar;
    
    /**
     * Čitalac koji je iznajmio knjige.
     */
    private Citalac citalac;
    
    /**
     * Lista pojedinačnih stavki koje pripadaju ovom zapisu o iznajmljivanju.
     */
    private List<StavkaZapisaOIznajmljivanju> stavke = new ArrayList<>();

    /**
     * Podrazumevani konstruktor koji kreira prazan objekat klase ZapisOIznajmljivanju.
     */
    public ZapisOIznajmljivanju() {
    }

    /**
     * Konstruktor koji inicijalizuje zapis o iznajmljivanju sa svim pripadajućim atributima i listom stavki.
     * @param datumIznajmljivanja Datum kreiranja zapisa.
     * @param ukupanIznos Ukupna vrednost svih stavki.
     * @param bibliotekar Bibliotekar koji izdaje knjige.
     * @param citalac Čitalac koji uzima knjige.
     * @param stavke Lista stavki sa konkretnim knjigama.
     */
    public ZapisOIznajmljivanju(Date datumIznajmljivanja, double ukupanIznos,
            Bibliotekar bibliotekar, Citalac citalac, List<StavkaZapisaOIznajmljivanju> stavke) {
        setDatumIznajmljivanja(datumIznajmljivanja);
        setUkupanIznos(ukupanIznos);
        setBibliotekar(bibliotekar);
        setCitalac(citalac);
        setStavke(stavke);
    }

    /**
     * Vraća jedinstveni identifikator zapisa.
     * @return ID zapisa kao int.
     */
    public int getIdZapis() {
        return idZapis;
    }

    /**
     * Postavlja jedinstveni identifikator zapisa.
     * @param idZapis ID zapisa.
     */
    public void setIdZapis(int idZapis) {
        this.idZapis = idZapis;
    }

    /**
     * Vraća datum iznajmljivanja knjiga.
     * @return Datum iznajmljivanja.
     */
    public Date getDatumIznajmljivanja() {
        return datumIznajmljivanja;
    }

    /**
     * Postavlja datum iznajmljivanja knjiga.
     * @param datumIznajmljivanja Datum iznajmljivanja.
     * @throws java.lang.IllegalArgumentException Ako je datum iznajmljivanja null, baca se izuzetak.
     */
    public void setDatumIznajmljivanja(Date datumIznajmljivanja) {
        if (datumIznajmljivanja == null) {
            throw new IllegalArgumentException("Datum iznajmljivanja ne sme biti null.");
        }
        this.datumIznajmljivanja = datumIznajmljivanja;
    }

    /**
     * Vraća ukupan iznos za ceo zapis.
     * @return Ukupan iznos kao double.
     */
    public double getUkupanIznos() {
        return ukupanIznos;
    }

    /**
     * Postavlja ukupan iznos za ceo zapis.
     * @param ukupanIznos Ukupan novčani iznos.
     * @throws java.lang.IllegalArgumentException Ako je ukupan iznos negativan, baca se izuzetak.
     */
    public void setUkupanIznos(double ukupanIznos) {
        if (ukupanIznos < 0) {
            throw new IllegalArgumentException("Ukupan iznos ne sme biti negativan.");
        }
        this.ukupanIznos = ukupanIznos;
    }

    /**
     * Vraća bibliotekara koji je kreirao zapis.
     * @return Bibliotekar koji je zadužio čitaoca.
     */
    public Bibliotekar getBibliotekar() {
        return bibliotekar;
    }

    /**
     * Postavlja bibliotekara koji kreira zapis.
     * @param bibliotekar Angažovani bibliotekar.
     */
    public void setBibliotekar(Bibliotekar bibliotekar) {
        if (bibliotekar == null) {
            throw new IllegalArgumentException("Bibliotekar ne sme biti null.");
        }
        this.bibliotekar = bibliotekar;
    }

    /**
     * Vraća čitaoca na koga glasi zapis.
     * @return Čitalac koji zadužuje knjige.
     */
    public Citalac getCitalac() {
        return citalac;
    }

    /**
     * Postavlja čitaoca na koga glasi zapis.
     * @param citalac Član biblioteke.
     * @throws java.lang.IllegalArgumentException Ako je čitalac null, baca se izuzetak.
     */
    public void setCitalac(Citalac citalac) {
        if (citalac == null) {
            throw new IllegalArgumentException("Citalac ne sme biti null.");
        }
        this.citalac = citalac;
    }

    /**
     * Vraća listu svih stavki koje se nalaze unutar ovog zapisa.
     * @return Lista stavki zapisa o iznajmljivanju.
     */
    public List<StavkaZapisaOIznajmljivanju> getStavke() {
        return stavke;
    }

    /**
     * Postavlja listu stavki za ovaj zapis o iznajmljivanju.
     * @param stavke Lista stavki.
     * @throws java.lang.IllegalArgumentException Ako je lista stavki null baca se izuzetak.
     */
    public void setStavke(List<StavkaZapisaOIznajmljivanju> stavke) {
        if (stavke == null) {
            throw new IllegalArgumentException("Lista stavki ne sme biti null.");
        }
        this.stavke = stavke;
    }

    /**
     * Vraća detaljan tekstualni prikaz zapisa o iznajmljivanju sa svim informacijama.
     * @return Tekstualni opis objekta.
     */
    @Override
    public String toString() {
        return "idZapis=" + idZapis + ", datumIznajmljivanja=" + datumIznajmljivanja + ", ukupanIznos=" + ukupanIznos + ", bibliotekar=" + bibliotekar + ", citalac=" + citalac + ", stavke=" + stavke + '}';
    }

    @Override
    public String vratiNazivTabele() {
        return "zapisoiznajmljivanju";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()){
            ZapisOIznajmljivanju z = new ZapisOIznajmljivanju();
            z.setIdZapis(rs.getInt("zapisoiznajmljivanju.idZapis"));
            z.setDatumIznajmljivanja(rs.getDate("zapisoiznajmljivanju.datumIznajmljivanja"));
            z.setUkupanIznos(rs.getDouble("zapisoiznajmljivanju.ukupanIznos"));
            
            Citalac c = new Citalac();
            c.setIdCitalac(rs.getInt("citalac.idCitalac"));
            c.setIme(rs.getString("citalac.ime"));
            c.setPrezime(rs.getString("citalac.prezime"));
            c.setBrojTel(rs.getString("citalac.brojTel"));
            
            Mesto m = new Mesto();
            m.setIdMesto(rs.getInt("mesto.idMesto"));
            m.setNaziv(rs.getString("mesto.naziv"));
            c.setMesto(m);
            z.setCitalac(c);
            
            Bibliotekar b = new Bibliotekar();
            b.setIdBibliotekar(rs.getInt("bibliotekar.idBibliotekar"));
            b.setIme(rs.getString("bibliotekar.ime"));
            b.setPrezime(rs.getString("bibliotekar.prezime"));
            b.setBrojTel(rs.getString("bibliotekar.brojTel"));
            b.setKorisnickoIme(rs.getString("bibliotekar.korisnickoIme"));
            b.setSifra(rs.getString("bibliotekar.sifra"));
            z.setBibliotekar(b);
            
            z.setStavke(new ArrayList<>());
            
            lista.add(z);
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "datumIznajmljivanja,ukupanIznos,idCitalac,idBibliotekar";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        java.sql.Date sqlDatum = new java.sql.Date(datumIznajmljivanja.getTime());
        return "('" + sqlDatum + "'," + ukupanIznos + "," + citalac.getIdCitalac() + "," + bibliotekar.getIdBibliotekar() + ")";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "zapisoiznajmljivanju.idZapis=" + idZapis;
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
        java.sql.Date sqlDatum = new java.sql.Date(datumIznajmljivanja.getTime());
        return "datumIznajmljivanja='" + sqlDatum + "',ukupanIznos=" + ukupanIznos + ",idCitalac=" + citalac.getIdCitalac() + ",idBibliotekar=" + bibliotekar.getIdBibliotekar();
    }
}
