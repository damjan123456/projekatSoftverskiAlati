/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.zajednicki.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author damja
 */
public class ZapisOIznajmljivanju implements ApstraktniDomenskiObjekat {
    private int idZapis;
    private Date datumIznajmljivanja;
    private double ukupanIznos;
    private Bibliotekar bibliotekar;
    private Citalac citalac;
    private List<StavkaZapisaOIznajmljivanju> stavke = new ArrayList<>();

    public ZapisOIznajmljivanju() {
    }

    public ZapisOIznajmljivanju(Date datumIznajmljivanja, double ukupanIznos,
            Bibliotekar bibliotekar,Citalac citalac,List<StavkaZapisaOIznajmljivanju> stavke) {
        this.datumIznajmljivanja = datumIznajmljivanja;
        this.ukupanIznos = ukupanIznos;
        this.bibliotekar = bibliotekar;
        this.citalac = citalac;
        this.stavke = stavke;
    }

    public int getIdZapis() {
        return idZapis;
    }

    public void setIdZapis(int idZapis) {
        this.idZapis = idZapis;
    }

    public Date getDatumIznajmljivanja() {
        return datumIznajmljivanja;
    }

    public void setDatumIznajmljivanja(Date datumIznajmljivanja) {
        this.datumIznajmljivanja = datumIznajmljivanja;
    }

    public double getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(double ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public Bibliotekar getBibliotekar() {
        return bibliotekar;
    }

    public void setBibliotekar(Bibliotekar bibliotekar) {
        this.bibliotekar = bibliotekar;
    }

    public Citalac getCitalac() {
        return citalac;
    }

    public void setCitalac(Citalac citalac) {
        this.citalac = citalac;
    }

    public List<StavkaZapisaOIznajmljivanju> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaZapisaOIznajmljivanju> stavke) {
        this.stavke = stavke;
    }

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
        java.sql.Date sqlDatum =new java.sql.Date(datumIznajmljivanja.getTime());
        return "('" + sqlDatum + "'," + ukupanIznos + "," + citalac.getIdCitalac()+"," + bibliotekar.getIdBibliotekar() + ")";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "zapisoiznajmljivanju.idZapis=" + idZapis;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        java.sql.Date sqlDatum =new java.sql.Date(datumIznajmljivanja.getTime());
        return "datumIznajmljivanja='" + sqlDatum + "',ukupanIznos=" + ukupanIznos + ",idCitalac=" + citalac.getIdCitalac()+",idBibliotekar=" + bibliotekar.getIdBibliotekar();
    }
    
    
    
    
}
