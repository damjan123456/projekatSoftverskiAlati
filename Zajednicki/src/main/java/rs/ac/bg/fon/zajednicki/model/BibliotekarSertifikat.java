/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.zajednicki.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author damja
 */
public class BibliotekarSertifikat implements ApstraktniDomenskiObjekat{
    private Bibliotekar bibliotekar;
    private Sertifikat sertifikat;
    private Date datumIzdavanja;

    public BibliotekarSertifikat() {
    }

    public BibliotekarSertifikat(Bibliotekar bibliotekar, Sertifikat sertifikat, Date datumIzdavanja) {
        this.bibliotekar = bibliotekar;
        this.sertifikat = sertifikat;
        this.datumIzdavanja = datumIzdavanja;
    }

    public Bibliotekar getBibliotekar() {
        return bibliotekar;
    }

    public void setBibliotekar(Bibliotekar bibliotekar) {
        this.bibliotekar = bibliotekar;
    }

    public Sertifikat getSertifikat() {
        return sertifikat;
    }

    public void setSertifikat(Sertifikat sertifikat) {
        this.sertifikat = sertifikat;
    }

    public Date getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(Date datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }

    @Override
    public String toString() {
        return bibliotekar + ", sertifikat=" + sertifikat + ", datumIzdavanja=" + datumIzdavanja;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

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

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "idBibliotekar=" + bibliotekar.getIdBibliotekar() + ",idSertifikat=" + sertifikat.getIdSertifikat() + ",datumIzdavanja='" + datumIzdavanja + "'";
    }
    
    
    
    
}
